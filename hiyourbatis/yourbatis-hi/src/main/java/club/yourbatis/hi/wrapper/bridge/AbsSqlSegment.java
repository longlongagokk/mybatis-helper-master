package club.yourbatis.hi.wrapper.bridge;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.enums.ConditionType;
import club.yourbatis.hi.util.StringUtils;
import club.yourbatis.hi.wrapper.ISqlSegment;

public abstract class AbsSqlSegment<T> implements ISqlSegment {
    protected ConditionType type;
    protected T[] items;

    @SafeVarargs
    protected AbsSqlSegment(ConditionType type, T... items) {
        this.type = type;
        this.items = items;
    }

//    @Override
//    public String createSql(AbstractQueryWrapper wrapper) {
//        if (items == null || items.length == 0) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder(wrapSql(items[0],wrapper));
//        sb.append(type.getOpera());
//        for(int i = 1;i<items.length;++i){
//            sb.append(wrapSql(items[i],wrapper));
//        }
//        return sb.toString();
//    }
//    protected String wrapSql(Item it, AbstractQueryWrapper<?,?> wrapper){
//        if(it.getType() == ItemType.FIELD){
//            Field fieldItem = (Field)it.getValue();
//            if(fieldItem.isOriginal()){
//                return fieldItem.getFullPath();
//            }
//            TableMetaInfo tb = wrapper.aliases.get(fieldItem.getAlias());
//            //if tb is null then some alias not in sql,please check sql's alias
//            String column = tb.getFieldWithColumns().get(fieldItem.getName());
//            if(StringUtils.isEmpty(column)){
//                throw new IllegalArgumentException("column can't find in " + tb.getTableName() + " with field [" + fieldItem.getFullName()+"]");
//            }
//            return wrapField(fieldItem,column).getFullPath();
//        }
//        return it.toString();
//    }

    protected String wrapSql(Field oriField, AbstractQueryWrapper<?,?> wrapper){
        if(oriField.isOriginal()){
            return oriField.getFullPath();
        }
        TableMetaInfo tb = wrapper.aliases.get(oriField.getAlias());
        //if tb is null then some alias not in sql,please check sql's alias
        String column = tb.getFieldWithColumns().get(oriField.getName());
        if(StringUtils.isEmpty(column)){
            throw new IllegalArgumentException("column can't find in " + tb.getTableName() + " with field [" + oriField.getFullName()+"]");
        }
        return wrapField(oriField,column).getFullPath();
    }

    protected abstract Field wrapField(Field oriField,String column);
}
