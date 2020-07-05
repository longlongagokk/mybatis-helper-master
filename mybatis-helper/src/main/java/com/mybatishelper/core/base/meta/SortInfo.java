package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.Order;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SortInfo implements Serializable {
    private Order order;//default
    private Item value;

    private SortInfo(String fullInfo){
        fullInfo = fullInfo.trim();
        int blankIndex = fullInfo.indexOf(ConstValue.BLANK);
        if(blankIndex != -1){
            this.value = FieldItem.valueOf(fullInfo.substring(0,blankIndex));
            if(Order.DESC.getValue().equals(fullInfo.substring(blankIndex+1))) {
                this.order = Order.DESC;
            }else{
                this.order = Order.ASC;
            }
        }else{
            this.value = FieldItem.valueOf(fullInfo);
            this.order = Order.ASC;
        }
    }
    private SortInfo(Item item,Order order){
        this.value = item;
        this.order = order;
    }
    public static SortInfo withField(String fullInfo){
        return new SortInfo(fullInfo);
    }
    public static SortInfo withField(String fieldWithAlias,Order order){
        return new SortInfo(FieldItem.valueOf(fieldWithAlias),order);
    }
    public static SortInfo withOriginal(String fullInfo){
        return new SortInfo(ValueItem.valueOf(fullInfo),null);
    }
}
