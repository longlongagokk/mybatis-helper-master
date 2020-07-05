package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.StringUtils;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SelectInfo implements Serializable {
    private String columnAlias;
    private Item value;

    private SelectInfo(String fullInfo){
        fullInfo = fullInfo.trim();
        int blankIndex = fullInfo.indexOf(ConstValue.BLANK);
        if(blankIndex != -1){
            this.value = FieldItem.valueOf(fullInfo.substring(0,blankIndex));
            this.columnAlias = fullInfo.substring(blankIndex+1).trim();
            if(!StringUtils.isNormalSql(this.columnAlias)){
                this.columnAlias = null;
            }
        }else{
            this.value = FieldItem.valueOf(fullInfo);
        }
        if(this.columnAlias == null){
            this.columnAlias = ((FieldItem)value).getName();
        }
    }
    private SelectInfo(Item item,String columnAlias){
        this.value = item;
        this.columnAlias = columnAlias;
    }
    public static SelectInfo withField(String fullInfo){
        return new SelectInfo(fullInfo);
    }
    public static SelectInfo withField(String alias, String fieldName, String columnAlias){
        return new SelectInfo(FieldItem.valueOf(alias,fieldName),columnAlias);
    }
    public static SelectInfo withOriginal(String fullInfo){
        return new SelectInfo(ValueItem.valueOf(fullInfo),null);
    }
}
