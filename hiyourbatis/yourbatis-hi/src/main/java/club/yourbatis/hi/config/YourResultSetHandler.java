package club.yourbatis.hi.config;

import club.yourbatis.hi.base.meta.PageList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.IntegerTypeHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * copy from mybatis link {@link org.apache.ibatis.executor.resultset.DefaultResultSetHandler}
 */
@Slf4j
public class YourResultSetHandler extends DefaultResultSetHandler {

    private final boolean pager;
    private final MappedStatement mappedStatement;
    private final YourConfiguration configuration;
    public YourResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {

        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.mappedStatement = mappedStatement;
        this.configuration = (YourConfiguration) mappedStatement.getConfiguration();
        this.pager = this.mappedStatement.getId().contains(this.configuration.getPageMethodName());
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
