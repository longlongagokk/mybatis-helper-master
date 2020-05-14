package com.mybatishelper.core.base.field;

import lombok.Getter;

@Getter
public class CompareField extends AbsField{
    private CompareField(String alias, String name,boolean original) {
        super(alias,name,original);
    }
    private CompareField(String fullInfo, boolean original){
        this.original = original;
        if(original){
            this.fullName = fullInfo.trim();
            return;
        }
        this.fullName = fullInfo.trim();
        setNameAndAlias(this.fullName);

    }
    public static CompareField valueOf(String alias,String name, boolean original){
        return new CompareField(alias,name,original);
    }
    public static CompareField valueOf(String fullName, boolean original){
        return new CompareField(fullName,original);
    }
    public static CompareField valueOf(String fullName){
        return valueOf(fullName,false);
    }

    @Override
    public String getFullPath() {
        return getFullName();
    }

    @Override
    public String toString(){
        return "alias="+this.getAlias() + ",name=" + this.getName()
                + ",fullName=" + this.getFullName()
                ;
    }
}
