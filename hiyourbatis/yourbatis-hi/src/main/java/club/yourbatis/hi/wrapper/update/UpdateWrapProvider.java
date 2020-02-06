package club.yourbatis.hi.wrapper.update;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.FieldValue;
import club.yourbatis.hi.base.Item;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.base.param.ParamItem;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.enums.ItemType;
import club.yourbatis.hi.util.Assert;
import club.yourbatis.hi.util.ContextUtil;
import club.yourbatis.hi.util.TableInfoHelper;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.condition.SimpleConditionItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Slf4j
public class UpdateWrapProvider {

    public String updateByPrimary(Object entity)throws Exception{
        return _updateByEntity(entity,false);
    }
    public String updateSelectiveByPrimaryKey(Object entity)throws Exception{
        return _updateByEntity(entity,true);
    }
    public String updateSelectItem(ProviderContext context, IUpdateWrapper wrapper) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoByProviderContext(context);
        UpdateWrapper updateWrapper = (UpdateWrapper)wrapper;
        List<FieldValue> fv = updateWrapper.updateItems;
        Assert.notEmpty(fv,"update elements can not be empty !");
        updateWrapper.addAliasTable(null,tableMetaInfo);
        updateWrapper.addAliasTable(ConstValue.MAIN_ALIAS,tableMetaInfo);

        String conditionSql = updateWrapper.getConditionSql();
        Assert.notEmpty(conditionSql, "could not update table with no conditions!");

        StringBuilder updateSql = new StringBuilder("update ").append(tableMetaInfo.getTableName())
                .append(ConstValue.BLANK).append(ConstValue.MAIN_ALIAS);

        //join and condition
        String joinSql = updateWrapper.getJoinerSql();
        if(!StringUtils.isEmpty(joinSql)){
            updateSql.append(ConstValue.BLANK);
            updateSql.append(joinSql);
        }
        updateSql.append(" set ");
        Set<String> keyWithAlias = new HashSet<>(fv.size()<<1);
        //reverse
        for(int i = fv.size()-1;i>=0;--i){
            FieldValue fieldValue = fv.get(i);
            if(!keyWithAlias.add(fieldValue.getLeft().toString())){
                continue;
            }
            //如果右边是param
            Item value = fieldValue.getRight();
            if(value.getType() == ItemType.PARAM){
                value = ParamItem.valueOf("updateItems",i);
            }
            updateSql.append(SimpleConditionItem.valueOf(ConditionType.EQ,fieldValue.getLeft(),value).createSql(updateWrapper)).append(ConstValue.COMMA);
        }
        updateSql.deleteCharAt(updateSql.length() - 1);
        updateSql.append(" where ").append(conditionSql);
        return updateSql.toString();
    }

    private String _updateByEntity(Object entity,boolean skipNull)throws Exception{
        Assert.notNull(entity,"entity could not be null!");
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoFromEntityClass(entity.getClass());
        Field primary = tableMetaInfo.getPrimary();
        Assert.notNull(primary,String.format("table {0} do not have any primaryKey!", tableMetaInfo.getTableName()));
        java.lang.reflect.Field[] fields = ContextUtil.getAllColumnFieldOfObject(entity.getClass());
        String column, field,keyField = primary.getName();
        StringBuilder updateSql = new StringBuilder("update ").append(tableMetaInfo.getTableName());
        StringBuilder setSql = new StringBuilder();
        for (java.lang.reflect.Field value : fields) {
            field = value.getName();
            column = tableMetaInfo.getFieldWithColumns().get(field);
            value.setAccessible(true);
            if (skipNull && value.get(entity) == null) {
                continue;
            }
            if (primary.getName().equals(column)) {
                keyField = field;
            } else {
                setSql.append(column).append(" = #{").append(field).append("},");
            }
        }
        if(setSql.length() == 0){
            throw new IllegalArgumentException("no field to update ！");
        }
        setSql.deleteCharAt(setSql.length() - 1);
        return updateSql.append(" set ").append(setSql).append(" where ").append(primary.getName()).append(" = #{").append(keyField).append("}").toString();
    }

}
