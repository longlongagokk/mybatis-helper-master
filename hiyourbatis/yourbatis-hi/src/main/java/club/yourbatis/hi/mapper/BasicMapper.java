package club.yourbatis.hi.mapper;

import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.base.meta.PageList;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.bridge.AbsSqlProvider;
import club.yourbatis.hi.wrapper.delete.DeleteSqlProvider;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import club.yourbatis.hi.wrapper.update.UpdateSqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * all code see https://github.com/longlongagokk/mybatis-helper
 * @param <T> the entity of table
 */
public interface BasicMapper<T> {

    /**
     * delete by primary key,if not exists primaryKey,throw an exception
     *
     * @param primary set primary key value
     * @return effect row count,expected 1
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = AbsSqlProvider.deleteByPrimaryKey)
    int deleteByPrimaryKey(Primary primary);

    /**
     * delete by query,if don't have anny condition,throw an exception
     *
     * @param wrapper query condition to how to delete
     * @return effect row count
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = AbsSqlProvider.delete)
    int delete(IDeleteWrapper wrapper);

    /**
     * select the row with primary key,if not exists primary key then throw an exception
     * note:make sure that the table only have one primary key
     *
     * @param primary wrap select columns to query and set primary key value
     *                if no columns selected,it will select all columns
     * @return if exists row then return one entity with map else null
     */
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectItemByPrimaryKey)
    T selectItemByPrimaryKey(Primary primary);

    /**
     * select the single row from table with query condition
     *
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return if exists row then return one entity with map else null
     */
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectOne)
    T selectOne(ISelectorWrapper wrapper);
    /**
     * select list from table with query condition
     *
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return if exists row then return list entity with map else empty list
     */
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectList)
    List<T> selectList(ISelectorWrapper wrapper);

    /**
     * select page count and list query
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return page and list
     */
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectPageList)
    PageList<T> selectPageList(ISelectorWrapper wrapper);

    /**
     * select list and rowCount
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return pageInfo
     */
    @SelectProvider(type = QuerySqlProvider.class, method = AbsSqlProvider.selectCount)
    //@ResultMap("BaseResultMap")
    int selectCount(ICountWrapper wrapper);

    /**
     * update select item by query
     *
     * @param wrapper select columns to update and how to update by condition
     *                if no columns selected or no condition set,will throw an exception
     * @return update rows
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = AbsSqlProvider.updateSelectItem)
    int updateSelectItem(IUpdateWrapper wrapper);
}
