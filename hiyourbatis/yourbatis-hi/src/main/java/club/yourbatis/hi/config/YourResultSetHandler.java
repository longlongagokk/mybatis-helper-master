package club.yourbatis.hi.config;

import club.yourbatis.hi.base.meta.PageList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.IntegerTypeHandler;

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

    private final boolean pager;
    private final MappedStatement mappedStatement;
    private final YourConfiguration configuration;
    private final ResultMap pageResultMap;
    private final RowBounds rowBounds;
    private final ObjectFactory objectFactory;
    public YourResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {

        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.mappedStatement = mappedStatement;
        this.configuration = (YourConfiguration) mappedStatement.getConfiguration();
        this.pageResultMap = configuration.getPageResultMap(mappedStatement);
        this.rowBounds = rowBounds;
        this.objectFactory = configuration.getObjectFactory();
        this.pager = null != this.pageResultMap;
    }
    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        if(!pager){
            return super.handleResultSets(stmt);
        }
        PageList<Object> pageList = new PageList<>();

      //  List<Object> oriList = super.handleResultSets(stmt);
        ErrorContext.instance().activity("handling results").object(mappedStatement.getId());
         ResultSetWrapper rsw = getFirstResultSet(stmt);
        DefaultResultHandler defaultResultHandler = new DefaultResultHandler(objectFactory);
        assert rsw != null;
        handleRowValues(rsw, pageResultMap, defaultResultHandler, rowBounds, null);
            closeResultSet(rsw.getResultSet());
        pageList.setList(defaultResultHandler.getResultList());

        rsw = getPageResultSet(stmt);
        if(null != rsw && rsw.getResultSet().next()){
            Integer count = new IntegerTypeHandler().getNullableResult(rsw.getResultSet(),"FOUND_ROWS()");
            pageList.setCount(count);
            closeResultSet(rsw.getResultSet());
        }
        return Collections.singletonList(pageList);
    }

    private ResultSetWrapper getFirstResultSet(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            // move forward to get the first resultSet in case the driver
            // doesn't return the resultSet as the first result (HSQLDB 2.1)
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    // no more results. Must be no resultSet
                    break;
                }
            }
        }
        return rs != null ? new ResultSetWrapper(rs, configuration) : null;
    }
    private ResultSetWrapper getPageResultSet(Statement stmt) throws SQLException {
        // Making this method tolerant of bad JDBC drivers
        try {
            if (stmt.getConnection().getMetaData().supportsMultipleResultSets()) {
                // Crazy Standard JDBC way of determining if there are more results
                if (!(!stmt.getMoreResults() && stmt.getUpdateCount() == -1)) {
                    ResultSet rs = stmt.getResultSet();
                    if (rs == null) {
                        return getPageResultSet(stmt);
                    } else {
                        return new ResultSetWrapper(rs, configuration);
                    }
                }
            }
        } catch (Exception e) {
            // Intentionally ignored.
        }
        return null;
    }
    private void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }
}
