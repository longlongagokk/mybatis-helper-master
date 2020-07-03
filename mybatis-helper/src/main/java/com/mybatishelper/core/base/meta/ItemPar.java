package com.mybatishelper.core.base.meta;

import com.mybatishelper.core.base.Item;
import com.mybatishelper.core.util.Assert;
import lombok.Getter;

@Getter
public class ItemPar<T0,T1> {
    Item<T0> key;
    Item<T1> value;
    private ItemPar(Item<T0> key,Item<T1> value){
        this.key = key;
        this.value = value;
    }
    public static <T0,T1> ItemPar<T0,T1> valueOf(Item<T0> key,Item<T1> value){
        Assert.notNull(key,"key can not be null");
        return new ItemPar<>(key,value);
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
