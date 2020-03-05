package com.vitily.common.mapper;

import club.yourbatis.hi.wrapper.bridge.AbsSqlProvider;
import club.yourbatis.hi.wrapper.insert.InsertSqlProvider;
import club.yourbatis.hi.wrapper.update.UpdateSqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * tools-Sql
 */
public interface StaticBoundMapper {
    /**
     * insert full properties
     *
     * @param entity all the entity's properties will be insert ,the null value property not excluded
     * @return effect row count,expected 1
     */
    @InsertProvider(type = InsertSqlProvider.class, method = AbsSqlProvider.insert)
    //@SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    int insert(Object entity);

    /**
     * insert not null properties
     *
     * @param entity with not null property to insert,if the property's value is null,then skip
     * @return effect row count,expected 1
     */
    @InsertProvider(type = InsertSqlProvider.class, method = AbsSqlProvider.insertSelective)
    int insertSelective(Object entity);


    /**
     * update full properties by primaryKey,if don't have anny primary key,then throw an exception
     *
     * @param entity all the entity's properties will be update ,the null value property not excluded
     * @return update rows
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = AbsSqlProvider.updateByPrimary)
    int updateByPrimary(Object entity);

    /**
     * update not null properties,if don't have anny primary key,then throw an exception
     *
     * @param entity with not null property to update,if the property's value is null,then skip
     * @return update rows
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = AbsSqlProvider.updateSelectiveByPrimaryKey)
    int updateSelectiveByPrimaryKey(Object entity);
}
