package com.vitily.order.api.config;

import club.yourbatis.hi.util.StringUtils;
import com.vitily.order.api.entity.PageList;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * copy from mybatis link {@link org.apache.ibatis.executor.resultset.DefaultResultSetHandler}
 */
public class YourResultSetHandler extends DefaultResultSetHandler {

    private boolean pager = false;
    private final MappedStatement mappedStatement;
    public YourResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {
        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.mappedStatement = mappedStatement;
        if(!StringUtils.isEmpty(this.mappedStatement.getId())){
            this.pager = this.mappedStatement.getId().contains("selectPageList");
        }
    }
    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        if(!pager){
            return super.handleResultSets(stmt);
        }
        List<Object> rs = super.handleResultSets(stmt);
        PageList<Object> pageList = new PageList<>();
        pageList.setList((List)rs.get(0));
        pageList.setCount(((List<Integer>) rs.get(1)).get(0));

        List<Object> pp = new ArrayList<>();
        pp.add(pageList);
        return pp;
    }

}
