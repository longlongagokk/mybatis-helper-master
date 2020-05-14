package com.mybatishelper.core.base.field;

import com.mybatishelper.core.base.Field;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.StringUtils;
import lombok.Getter;

@Getter
public abstract class AbsField implements Field {
    protected boolean original;
    protected String alias;
    protected String name;
    protected String fullName;
    protected AbsField(){}
    protected AbsField(String alias,String name,boolean original){
        this.alias = alias;
        this.name = name;
        this.original = original;
        if(StringUtils.isEmpty(alias)){
            this.fullName = name;
        }else{
            this.fullName = alias + ConstValue.DOT + name;
        }
    }
    void setNameAndAlias(String nameWithAlias){
        int dotIndex = nameWithAlias.indexOf(ConstValue.DOT);
        if(dotIndex == -1){
            name = nameWithAlias;
        }else{
            alias = nameWithAlias.substring(0,dotIndex);
            name = nameWithAlias.substring(dotIndex + 1);
        }
    }
}
