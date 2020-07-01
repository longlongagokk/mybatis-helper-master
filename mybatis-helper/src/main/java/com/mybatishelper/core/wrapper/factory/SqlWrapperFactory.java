package com.mybatishelper.core.wrapper.factory;

import com.mybatishelper.core.wrapper.delete.DeleteWrapper;
import com.mybatishelper.core.wrapper.query.QueryWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.core.wrapper.update.UpdateWrapper;

@SuppressWarnings("unchecked")
public abstract class SqlWrapperFactory {
    public static <N extends QueryWrapper<PropertyConditionWrapper>> N prop4Query(){
        return (N) new QueryWrapper(new PropertyConditionWrapper());
    }
    public static <N extends QueryWrapper<FlexibleConditionWrapper>> N flex4Query(){
        return (N) new QueryWrapper(new FlexibleConditionWrapper());
    }
    public static <N extends SelectWrapper<PropertyConditionWrapper>> N prop4Select(){
        return (N) new SelectWrapper(new PropertyConditionWrapper());
    }
    public static <N extends SelectWrapper<FlexibleConditionWrapper>> N flex4Select(){
        return (N) new SelectWrapper(new FlexibleConditionWrapper());
    }
    public static <N extends UpdateWrapper<PropertyConditionWrapper>> N prop4Update(){
        return (N) new UpdateWrapper(new PropertyConditionWrapper());
    }
    public static <N extends UpdateWrapper<FlexibleConditionWrapper>> N flex4Update(){
        return (N) new UpdateWrapper<>(new FlexibleConditionWrapper());
    }
    public static <N extends DeleteWrapper<PropertyConditionWrapper>> N prop4Delete(){
        return (N) new DeleteWrapper(new PropertyConditionWrapper());
    }
    public static <N extends DeleteWrapper<FlexibleConditionWrapper>> N flex4Delete(){
        return (N) new DeleteWrapper(new FlexibleConditionWrapper());
    }
}
