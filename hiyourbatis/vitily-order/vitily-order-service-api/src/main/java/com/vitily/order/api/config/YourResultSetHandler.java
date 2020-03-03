package com.vitily.order.api.config;

import club.yourbatis.hi.util.StringUtils;
import com.vitily.order.api.entity.PageList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.result.DefaultResultContext;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.apache.ibatis.type.LongTypeHandler;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * copy from mybatis link {@link org.apache.ibatis.executor.resultset.DefaultResultSetHandler}
 */
@Slf4j
public class YourResultSetHandler extends DefaultResultSetHandler {

    private boolean pager = false;
    private final MappedStatement mappedStatement;
    private final Configuration configuration;
    public YourResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {

        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        if(!StringUtils.isEmpty(this.mappedStatement.getId())){
            this.pager = this.mappedStatement.getId().contains("selectPageList");
        }
    }
    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        if(!pager){
            return super.handleResultSets(stmt);
        }
        List<Object> oriList = super.handleResultSets(stmt);
        PageList<Object> pageList = new PageList<>();
        pageList.setList(oriList);
        ResultSetWrapper rsw = getPageResultSet(stmt);
        if(null != rsw && rsw.getResultSet().next()){
            Integer count = new IntegerTypeHandler().getNullableResult(rsw.getResultSet(),"FOUND_ROWS()");
            pageList.setCount(count);
        }

        List<Object> pp = new ArrayList<>();
        pp.add(pageList);
        return pp;
    }
    private ResultSetWrapper getPageResultSet(Statement stmt) throws SQLException {
        // Making this method tolerant of bad JDBC drivers
        try {
            if (stmt.getConnection().getMetaData().supportsMultipleResultSets()) {
                return new ResultSetWrapper(stmt.getResultSet(), configuration);
            }
        } catch (Exception e) {
            // Intentionally ignored.
        }
        return null;
    }

}
