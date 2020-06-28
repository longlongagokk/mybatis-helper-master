package com.mybatishelper.core.wrapper.update;

import com.mybatishelper.core.base.FieldValue;
import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.cache.EntryFieldInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.enums.ItemType;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IUpdateWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.seg.SimpleConditionSeg;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Slf4j
public class UpdateSqlProvider extends AbsSqlProvider {

    public String updateByPrimary(Object entity)throws Exception{
        return _updateByEntity(entity,false);
    }
    public String updateSelectiveByPrimaryKey(Object entity)throws Exception{
        return _updateByEntity(entity,true);
    }
    private String _updateByEntity(Object entity,boolean skipNull)throws Exception{
        Assert.notNull(entity,"entity could not be null!");
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoFromEntityClass(entity.getClass());
        EntryFieldInfo primary = tableMetaInfo.getPrimary();
        Assert.notNull(primary,String.format("table {0} do not have any primaryKey!", tableMetaInfo.getTableName()));
        StringBuilder updateSql = new StringBuilder("update ").append(tableMetaInfo.getTableName());
        StringBuilder setSql = new StringBuilder();
        for(Map.Entry<String, EntryFieldInfo> entry:tableMetaInfo.getFieldInfos().entrySet()){
            EntryFieldInfo fieldInfo = entry.getValue();
            fieldInfo.getField().setAccessible(true);
            if (skipNull && fieldInfo.getField().get(entity) == null) {
                continue;
            }
            if (!fieldInfo.isPrimaryKey()) {
                setSql.append(fieldInfo.getColumn()).append(" = #{").append(entry.getKey()).append("},");
            }
        }
        if(setSql.length() == 0){
            throw new IllegalArgumentException("no field to update ！");
        }
        setSql.deleteCharAt(setSql.length() - 1);
        return updateSql.append(" set ").append(setSql).append(" where ").append(primary.getColumn()).append(" = #{").append(primary.getField().getName()).append("}").toString();
    }

    public String updateSelectItem(ProviderContext context, IUpdateWrapper wrapper) {
        UpdateWrapper updateWrapper = (UpdateWrapper)wrapper;
        List fv = updateWrapper.updateItems;
        Assert.notEmpty(fv,"update elements can not be empty !");

        checkAndReturnFromTables(context,updateWrapper);

        StringBuilder updateSql = new StringBuilder("update ");

        //from tables
        createFromTableSql(updateSql,updateWrapper);

        //join infos
        createJoinInfoSql(updateSql,updateWrapper);

        updateSql.append(" set ");
        Set<String> keyWithAlias = new HashSet<>(fv.size()<<1);
        //reverse
        for(int i = fv.size()-1;i>=0;--i){
            FieldValue fieldValue = (FieldValue) fv.get(i);
            if(!keyWithAlias.add(fieldValue.getLeft().toString())){
                continue;
            }
            //如果右边是param
            Item value = fieldValue.getRight();
            if(value.getType() == ItemType.PARAM){
                value = ParamItem.valueOf("updateItems",i);
            }
            updateSql.append(SimpleConditionSeg.valueOf(ConditionType.EQ,fieldValue.getLeft(),value).createSql(updateWrapper)).append(ConstValue.COMMA);
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        //conditions
        if(!createWhereSql(updateSql,updateWrapper)){
            throw new IllegalArgumentException("could not update table with no conditions!");
        }
        return updateSql.toString();
    }

}
