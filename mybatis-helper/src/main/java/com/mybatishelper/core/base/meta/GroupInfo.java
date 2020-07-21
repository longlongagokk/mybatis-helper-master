package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ValueItem;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class GroupInfo implements Serializable {
    private Item value;
    private GroupInfo(Item item){
        this.value = item;
    }
    public static GroupInfo withField(String fieldWithAlias){
        return new GroupInfo(FieldItem.valueOf(fieldWithAlias));
    }
    public static GroupInfo withOriginal(String fullInfo){
        return new GroupInfo(ValueItem.valueOf(fullInfo));
    }
}
