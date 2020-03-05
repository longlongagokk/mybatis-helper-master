package club.yourbatis.hi.config;

import club.yourbatis.hi.base.meta.PageList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class YourConfiguration extends Configuration {
    private final Class<?> pageClass = PageList.class;
    private final Map<String, ResultMap> pageResultMaps = new HashMap<>();
    @Override
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler,
                                                ResultHandler resultHandler, BoundSql boundSql) {
        ResultSetHandler resultSetHandler = new YourResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
        return resultSetHandler;
    }
    public ResultMap getPageResultMap(MappedStatement mappedStatement){
        return pageResultMaps.get(mappedStatement.getId());
    }
    @Override
    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
        // put return PageList method into container
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            if(returnType == pageClass){
                final String mappedStatementId = type.getName() + "." + method.getName();
                ResultMap ori = getMappedStatement(mappedStatementId).getResultMaps().get(0);
                Class<?> actualType = getReturnType(method,type);
                ResultMap resultMap = new ResultMap.Builder(this, mappedStatementId, actualType, ori.getResultMappings(), ori.getAutoMapping())
                        .discriminator(ori.getDiscriminator())
                        .build();
                addPageResultMap(resultMap);
            }
        }

    }

    private Class<?> getReturnType(Method method,Type srcType) {
        Class<?> returnType = method.getReturnType();
        Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, srcType);
        if (resolvedReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) resolvedReturnType;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if (PageList.class.isAssignableFrom(rawType) || Cursor.class.isAssignableFrom(rawType)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    Type returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class<?>) {
                        returnType = (Class<?>) returnTypeParameter;
                    } else if (returnTypeParameter instanceof ParameterizedType) {
                        // (gcode issue #443) actual type can be a also a parameterized type
                        returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
                    } else if (returnTypeParameter instanceof GenericArrayType) {
                        Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
                        // (gcode issue #525) support List<byte[]>
                        returnType = Array.newInstance(componentType, 0).getClass();
                    }
                }
            }
        }

        return returnType;
    }
    private void addPageResultMap(ResultMap rm){
        pageResultMaps.put(rm.getId(), rm);
        checkLocallyForDiscriminatedNestedResultMaps(rm);
        checkGloballyForDiscriminatedNestedResultMaps(rm);
    }
}
