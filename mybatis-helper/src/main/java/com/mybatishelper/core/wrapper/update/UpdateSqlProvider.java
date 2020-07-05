package com.mybatishelper.core.wrapper.update;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.meta.UpdateInfo;
import com.mybatishelper.core.base.param.ValueItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Assert.notEmpty(updateWrapper.updateInfos,"update elements can not be empty !");
        updateWrapper.updateItems = new ArrayList(updateWrapper.updateInfos);
        //已经是非重复且按顺序的了
        List<UpdateInfo> fvs = updateWrapper.updateItems;

        checkAndReturnFromTables(context,updateWrapper);

        StringBuilder updateSql = new StringBuilder("update ");

        //from tables
        createFromTableSql(updateSql,updateWrapper);

        //join infos
        createJoinInfoSql(updateSql,updateWrapper);

        updateSql.append(" set ");
        for(int i = 0; i < fvs.size(); ++i){
            UpdateInfo info = fvs.get(i);
            Item value = info.getValue();
            if(value.getType() == ItemType.PARAM){
                value = ValueItem.valueOf("#{updateItems["+i+"].value.value}");
            }
            updateSql.append(SimpleConditionSeg.valueOf(ConditionType.EQ,info.getKey(),value).createSql(updateWrapper)).append(ConstValue.COMMA);
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        //conditions
        if(!createWhereSql(updateSql,updateWrapper)){
            throw new IllegalArgumentException("could not update table with no conditions!");
        }
        return updateSql.toString();
    }

}
