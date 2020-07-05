package com.mybatishelper.core.wrapper.update;

import com.mybatishelper.core.base.meta.UpdateInfo;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.wrapper.IUpdateWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
import com.mybatishelper.core.wrapper.bridge.AbstractQueryWrapper;

import java.util.*;

public class UpdateWrapper<C extends AbstractConditionWrapper>
        extends AbstractQueryWrapper<C,UpdateWrapper<C>>
        implements IUpdateWrapper<UpdateWrapper<C>,C> {
    List<UpdateInfo> updateItems;
    Set<UpdateInfo> updateInfos;
    public UpdateWrapper(C where){
        super(where,new ArrayList<>(1<<1));
        this.updateInfos = new LinkedHashSet<>(1<<3);
    }
    @Override
    public final UpdateWrapper<C> set(UpdateInfo... updateInfos) {
        Assert.notNull(updateInfos,"update items can not be empty");
        Collections.addAll(this.updateInfos, updateInfos);
        return this;
    }
    @Override
    public UpdateWrapper<C> set(String fieldWithAlias, Object value) {
        return set(UpdateInfo.withFieldParam(fieldWithAlias,value));
    }
}
