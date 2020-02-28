package club.yourbatis.hi.mapper;

import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.delete.DeleteSqlProvider;
import club.yourbatis.hi.wrapper.insert.InsertSqlProvider;
import club.yourbatis.hi.wrapper.query.QuerySqlProvider;
import club.yourbatis.hi.wrapper.update.UpdateSqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * all code see https://github.com/longlongagokk/mybatis-helper
 * @param <T> the entity of table
 */
public interface BasicMapper<T> {
    /**
     * insert full properties
     *
     * @param entity all the entity's properties will be insert ,the null value property not excluded
     * @return effect row count,expected 1
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "insert")
    //@SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = long.class)
    int insert(T entity);

    /**
     * insert not null properties
     *
     * @param entity with not null property to insert,if the property's value is null,then skip
     * @return effect row count,expected 1
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSelective")
    int insertSelective(T entity);

    /**
     * delete by primary key,if not exists primaryKey,throw an exception
     *
     * @param primary set primary key value
     * @return effect row count,expected 1
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(Primary primary);

    /**
     * delete by query,if don't have anny condition,throw an exception
     *
     * @param wrapper query condition to how to delete
     * @return effect row count
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "delete")
    int delete(IDeleteWrapper wrapper);

    /**
     * select the row with primary key,if not exists primary key then throw an exception
     * note:make sure that the table only have one primary key
     *
     * @param primary wrap select columns to query and set primary key value
     *                if no columns selected,it will select all columns
     * @return if exists row then return one entity with map else null
     */
    @SelectProvider(type = QuerySqlProvider.class, method = "selectItemByPrimaryKey")
    //@ResultMap("BaseResultMap")
    T selectItemByPrimaryKey(Primary primary);

    /**
     * select the single row from table with query condition
     *
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return if exists row then return one entity with map else null
     */
    @SelectProvider(type = QuerySqlProvider.class, method = "selectOne")
    //@ResultMap("BaseResultMap")
    T selectOne(ISelectorWrapper wrapper);
    /**
     * select list from table with query condition
     *
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return if exists row then return list entity with map else empty list
     */
    @SelectProvider(type = QuerySqlProvider.class, method = "selectList")
    //@ResultMap("BaseResultMap")
    List<T> selectList(ISelectorWrapper wrapper);

    /**
     * select list and rowCount
     * @param wrapper select columns to query and how to query by condition
     *                if no columns selected,it will select all columns
     * @return pageInfo
     */
    @SelectProvider(type = QuerySqlProvider.class, method = "selectCount")
    //@ResultMap("BaseResultMap")
    int selectCount(ICountWrapper wrapper);


    /**
     * update full properties by primaryKey,if don't have anny primary key,then throw an exception
     *
     * @param entity all the entity's properties will be update ,the null value property not excluded
     * @return update rows
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateByPrimary")
    int updateByPrimary(T entity);

    /**
     * update not null properties,if don't have anny primary key,then throw an exception
     *
     * @param entity with not null property to update,if the property's value is null,then skip
     * @return update rows
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSelectiveByPrimaryKey")
    int updateSelectiveByPrimaryKey(T entity);

    /**
     * update select item by query
     *
     * @param wrapper select columns to update and how to update by condition
     *                if no columns selected or no condition set,will throw an exception
     * @return update rows
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSelectItem")
    int updateSelectItem(IUpdateWrapper wrapper);
}
