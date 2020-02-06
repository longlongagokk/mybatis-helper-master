package club.yourbatis.hi.base.field;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.consts.ConstValue;
import org.springframework.util.StringUtils;

public class SimpleField implements Field {
    protected String alias;
    protected String name;
    String fullName;
    SimpleField(){}
    SimpleField(String alias, String name) {
        this.alias = alias;
        this.name = name;
        if(StringUtils.isEmpty(alias)){
            this.fullName = name;
        }else{
            this.fullName = alias + ConstValue.DOT + name;
        }
    }
    SimpleField(String fullName) {
        int dotIndex = fullName.indexOf(ConstValue.DOT);
        if(dotIndex == -1){
            name = fullName;
        }else{
            alias = fullName.substring(0,dotIndex);
            name = fullName.substring(dotIndex + 1);
        }
        this.fullName = fullName;
    }

    public static Field valueOf(String fullName){
        return new SimpleField(fullName);
    }
    public static Field valueOf(String alias,String name){
        return new SimpleField(alias,name);
    }
    public static Field valueOf(Enum em){
        return valueOf(em.name());
    }
    public static Field valueOf(String alias,Enum em){
        return valueOf(alias,em.name());
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public int hashCode() {
        return getFullName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getFullName().equals(((SimpleField)obj).getFullName());
    }
}
