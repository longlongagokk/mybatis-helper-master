package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.util.Assert;
import lombok.Getter;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

@Getter
public class ItemPar<T0,T1> implements Serializable {
    Item<T0> key;
    Item<T1> value;
    ItemPar(Item<T0> key, Item<T1> value){
        this.key = key;
        this.value = value;
    }
    public static ItemPar<String,String> withFieldField(String fieldWithAlias0, String fieldWithAlias1){
        Assert.notNull(fieldWithAlias0,"key can not be null");
        return new ItemPar<>(FieldItem.valueOf(fieldWithAlias0),FieldItem.valueOf(fieldWithAlias1));
    }
    public static <T> ItemPar<String,T> withFieldValue(String fieldWithAlias0, T value){
        Assert.notNull(fieldWithAlias0,"key can not be null");
        return new ItemPar<>(FieldItem.valueOf(fieldWithAlias0), ValueItem.valueOf(value));
    }
    public static <T> ItemPar<String,T> withFieldParam(String fieldWithAlias0, T value){
        Assert.notNull(fieldWithAlias0,"key can not be null");
        return new ItemPar<>(FieldItem.valueOf(fieldWithAlias0), ParamItem.valueOf(value));
    }

    public static <T0,T1> ItemPar<T0,T1> withValueField(T0 key, String fieldWithAlias1){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(ValueItem.valueOf(key),FieldItem.valueOf(fieldWithAlias1));
    }
    public static <T0,T1> ItemPar<T0,T1> withValueValue(T0 key, T1 value){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(ValueItem.valueOf(key),ValueItem.valueOf(value));
    }
    public static <T0,T1> ItemPar<T0,T1> withValueParam(T0 key, T1 value){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(ValueItem.valueOf(key), ParamItem.valueOf(value));
    }

    public static <T0,T1> ItemPar<T0,T1> withParamField(T0 key, String fieldWithAlias1){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(ParamItem.valueOf(key),FieldItem.valueOf(fieldWithAlias1));
    }
    public static <T0,T1> ItemPar<T0,T1> withParamValue(T0 key, T1 value){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(ParamItem.valueOf(key),ValueItem.valueOf(value));
    }
    public static <T0,T1> ItemPar<T0,T1> withParamParam(T0 key, T1 value){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(ParamItem.valueOf(key),ParamItem.valueOf(value));
    }

    @Override
    public int hashCode(){
        return key.hashCode();
    }
    @Override
    public String toString(){
        return key.toString();
    }
    @Override
    public boolean equals(Object o){
        if(!(o instanceof ItemPar)){
            return false;
        }
        return key.equals(((ItemPar) o) .key);
    }
}
