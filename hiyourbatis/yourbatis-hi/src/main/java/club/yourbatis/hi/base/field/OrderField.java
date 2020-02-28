package club.yourbatis.hi.base.field;

import club.yourbatis.hi.util.StringUtils;
import lombok.Getter;

@Getter
public class OrderField extends SimpleField{
    private boolean original;
    public OrderField(String fullName, boolean original){
        super(fullName);
        //use original sql
        this.original = original;
        if(original && StringUtils.isNormalSql(fullName)){
            throw new IllegalArgumentException("fullName is not a normal sql");
        }
    }
    public OrderField(String fullName){
        this(fullName,false);
    }
    public static OrderField valueOf(String fullName, boolean original){
        return new OrderField(fullName,original);
    }
    public static OrderField valueOf(String fullName){
        return valueOf(fullName,false);
    }
}
