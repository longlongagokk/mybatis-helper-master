package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.base.param.ValueItem;
import lombok.Getter;

@Getter
public class HavingInfo {
    private Item[] params;
    private String havingSql;
    private HavingInfo(String havingSql,Object...params){
        this.havingSql = havingSql;
        if(null != params){
            this.params = new Item[params.length];
            for(int i = 0;i<params.length;++i){
                this.params[i] = ValueItem.valueOf(params[i]);
            }
        }
    }
    public static HavingInfo valueOf(String havingSql,Object...params){
        return new HavingInfo(havingSql,params);
    }
}
