package com.mybatishelper.core.base.field;

import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.enums.Order;
import com.mybatishelper.core.util.StringUtils;
import lombok.Getter;

@Getter
public class OrderField extends AbsField{
    private Order order = Order.ASC;//default
    private String fullPath;
    private OrderField(String alias, String name,Order order,boolean original) {
        super(alias,name,original);
        this.order = order;
        if(!original){
            fullPath = getFullName() + ConstValue.BLANK + order;
        }
    }
    private OrderField(String fullInfo, boolean original){
        this.original = original;
        if(original){
            if(!StringUtils.isNormalSql(fullInfo)) {
                throw new IllegalArgumentException("fullName["+fullInfo+"] is not a normal sql");
            }
            this.fullPath = fullInfo.trim();
            return;
        }
        fullInfo = fullInfo.trim();
        int blankIndex = fullInfo.indexOf(ConstValue.BLANK);
        if(blankIndex != -1){
            this.fullName = fullInfo.substring(0,blankIndex);
            if(Order.DESC.getValue().equals(fullInfo.substring(blankIndex+1))) {
                this.order = Order.DESC;
            }
        }else{
            this.fullName = fullInfo;
        }
        //fullName not with order info
        setNameAndAlias(this.fullName);

    }

    private OrderField(String fullName,Order order){
        //fullName not with order info
        this.fullName = fullName;
        setNameAndAlias(this.fullName);
        this.order = order;
    }

    public static OrderField valueOf(String fullInfo, boolean original){
        return new OrderField(fullInfo,original);
    }
    public static OrderField valueOf(String fullInfo){
        return valueOf(fullInfo,false);
    }
    public static OrderField valueOf(String fullName,Order order){
        return new OrderField(fullName,order);
    }
    public static OrderField valueOf(String alias,String name,Order order,boolean original){
        return new OrderField(alias,name,order,original);
    }
    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public String toString(){
        return "alias="+this.getAlias() + ",name=" + this.getName()
                + ",fullName=" + this.getFullName()
                + ",order=" + this.getOrder();
    }
}
