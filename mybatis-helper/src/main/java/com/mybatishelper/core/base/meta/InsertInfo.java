package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.util.Assert;
import lombok.Getter;

@Getter
public class InsertInfo<T> extends ItemPar<String,T>{
    private InsertInfo(Item<String> key, Item<T> value){
        super(key,value);
    }
    public static InsertInfo<String> withFieldField(String fieldWithAlias0, String fieldWithAlias1){
        Assert.notNull(fieldWithAlias0,"key can not be null");
        return new InsertInfo<>(FieldItem.valueOf(fieldWithAlias0),FieldItem.valueOf(fieldWithAlias1));
    }
    public static <T> InsertInfo<T> withFieldValue(String fieldWithAlias0, T value){
        Assert.notNull(fieldWithAlias0,"key can not be null");
        return new InsertInfo<>(FieldItem.valueOf(fieldWithAlias0), ValueItem.valueOf(value));
    }
    public static <T> InsertInfo<T> withFieldParam(String fieldWithAlias0, T value){
        Assert.notNull(fieldWithAlias0,"key can not be null");
        return new InsertInfo<>(FieldItem.valueOf(fieldWithAlias0), ParamItem.valueOf(value));
    }

    public static InsertInfo<String> withColumnField(String columnWithAlias, String fieldWithAlias){
        Assert.notNull(columnWithAlias,"key can not be null");
        return new InsertInfo<>(ValueItem.valueOf(columnWithAlias),FieldItem.valueOf(fieldWithAlias));
    }
    public static <T> InsertInfo<T> withColumnValue(String columnWithAlias, T value){
        Assert.notNull(columnWithAlias,"key can not be null");
        return new InsertInfo<>(ValueItem.valueOf(columnWithAlias),ValueItem.valueOf(value));
    }
    public static <T> InsertInfo<T> withColumnParam(String columnWithAlias, T value){
        Assert.notNull(columnWithAlias,"key can not be null");
        return new InsertInfo<>(ValueItem.valueOf(columnWithAlias), ParamItem.valueOf(value));
    }
}
