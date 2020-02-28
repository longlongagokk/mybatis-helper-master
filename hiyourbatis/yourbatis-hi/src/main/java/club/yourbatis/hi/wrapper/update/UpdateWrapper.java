package club.yourbatis.hi.wrapper.update;

import club.yourbatis.hi.base.FieldValue;
import club.yourbatis.hi.base.meta.FieldWithValue;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.util.Assert;
import club.yourbatis.hi.wrapper.IUpdateWrapper;
import club.yourbatis.hi.wrapper.bridge.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.bridge.AbstractJoinerWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateWrapper<C extends AbstractConditionWrapper>
        extends AbstractJoinerWrapper<C,UpdateWrapper<C>>
        implements IUpdateWrapper<UpdateWrapper<C>,C> {
    List<FieldValue> updateItems;
    public UpdateWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.updateItems = new ArrayList<>(1<<3);
    }
    public static DefaultUpdateWrapper build(){
        return new DefaultUpdateWrapper();
    }
    public static class DefaultUpdateWrapper extends UpdateWrapper<StringConditionWrapper>
    {
        public DefaultUpdateWrapper(){
            super(new StringConditionWrapper());
        }
    }
    @Override
    public UpdateWrapper<C> set(FieldValue... items) {
        Assert.notNull(items,"update items can not be empty");
        Collections.addAll(updateItems, items);
        return this;
    }
    @Override
    public UpdateWrapper<C> set(String fieldWithAlias, Object value) {
        return set(FieldWithValue.withParamValue(fieldWithAlias,value));
    }

    @Override
    public UpdateWrapper<C> set(Enum field, Object value) {
        return set(field.name(),value);
    }
}
