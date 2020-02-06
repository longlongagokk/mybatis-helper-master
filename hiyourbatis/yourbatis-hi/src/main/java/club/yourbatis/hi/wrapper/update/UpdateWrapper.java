package club.yourbatis.hi.wrapper.update;

import club.yourbatis.hi.base.FieldValue;
import club.yourbatis.hi.base.meta.FieldWithValue;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.util.Assert;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractJoinerWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateWrapper<L,R,C extends AbstractConditionWrapper<L,R,C>>
        extends AbstractJoinerWrapper<L,R,C,UpdateWrapper<L,R,C>>
        implements IUpdateWrapper<UpdateWrapper<L,R,C>,C> {
    List<FieldValue> updateItems;
    public UpdateWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.updateItems = new ArrayList<>(1<<3);
    }
    public static DefaultUpdateWrapper build(){
        return new DefaultUpdateWrapper();
    }
    public static class DefaultUpdateWrapper extends UpdateWrapper
    <String,Object,StringConditionWrapper>
    {
        public DefaultUpdateWrapper(){
            super(new StringConditionWrapper());
        }
    }
    @Override
    public UpdateWrapper<L,R,C> set(FieldValue... items) {
        Assert.notNull(items,"update items can not be empty");
        Collections.addAll(updateItems, items);
        return this;
    }
    @Override
    public UpdateWrapper<L,R,C> set(String fieldWithAlias, Object value) {
        return set(FieldWithValue.withParamValue(fieldWithAlias,value));
    }

    @Override
    public UpdateWrapper<L,R,C> set(Enum field, Object value) {
        return set(field.name(),value);
    }

    @Override
    protected String getJoinerSql(){
        return super.getJoinerSql();
    }
    @Override
    protected String getConditionSql() {
        return super.getConditionSql();
    }
    @Override
    protected void addAliasTable(String alias, TableMetaInfo tb){
        super.addAliasTable(alias,tb);
    }
}
