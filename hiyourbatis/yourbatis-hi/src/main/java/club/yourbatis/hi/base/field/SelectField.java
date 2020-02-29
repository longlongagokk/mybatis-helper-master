package club.yourbatis.hi.base.field;

import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.util.StringUtils;
import lombok.Getter;

@Getter
public class SelectField extends AbsField {
    private String columnAlias;
    private String fullPath;
    private SelectField(String alias, String name,String columnAlias,boolean original) {
        super(alias,name,original);
        this.columnAlias = columnAlias;
        if(!original){
            fullPath = getFullName() + ConstValue.BLANK + ConstValue.BACKTRICKS + this.columnAlias + ConstValue.BACKTRICKS;
        }
    }
    private SelectField(String fullInfo, boolean original){
        this.original = original;
        if(original){
            this.fullPath = fullInfo.trim();
            return;
        }

        fullInfo = fullInfo.trim();
        int blankIndex = fullInfo.indexOf(ConstValue.BLANK);
        if(blankIndex != -1){
            this.fullName = fullInfo.substring(0,blankIndex);
            this.columnAlias = fullInfo.substring(blankIndex+1).trim();
            if(!StringUtils.isNormalSql(this.columnAlias)){
                this.columnAlias = null;
            }
        }else{
            this.fullName = fullInfo;
        }
        //fullName not with columnAlias
        setNameAndAlias(this.fullName);
        if(this.columnAlias == null){
            this.columnAlias = this.name;
        }
    }
    public static SelectField valueOf(String itemWithAlias, boolean original){
        return new SelectField(itemWithAlias,original);
    }
    public static SelectField valueOf(String itemWithAlias){
        return valueOf(itemWithAlias,false);
    }
    public static SelectField valueOf(String alias,String name,String columnAlias,boolean original){
        return new SelectField(alias,name,columnAlias,original);
    }
    public static SelectField valueOf(String alias, Enum item){
            return valueOf(alias,item.name(),item.name(),false);
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public String toString(){
        return "alias="+this.getAlias() + ",name=" + this.getName()
                + ",fullName=" + this.getFullName()
                + ",columnAlias=" + this.getColumnAlias();
    }
//    public static void main(String[] args){
//        boolean iso = false;
//        System.out.println(valueOf("id",iso));
//        System.out.println(valueOf("id  ",iso));
//        System.out.println(valueOf("  id",iso));
//        System.out.println(valueOf("  id   ",iso));
//
//        System.out.println(("------------------------------------------------"));
//
//        System.out.println(valueOf("a.id",iso));
//        System.out.println(valueOf("a.id   ",iso));
//        System.out.println(valueOf("   a.id",iso));
//        System.out.println(valueOf("   a.id   ",iso));
//
//        System.out.println(("------------------------------------------------"));
//
//
//        System.out.println(valueOf("a.id    cxk",iso));
//        System.out.println(valueOf("a.id    cxk   ",iso));
//        System.out.println(valueOf("   a.id    cxk",iso));
//        System.out.println(valueOf("   a.id    cxk   ",iso));
//
//        System.out.println(("------------------------------------------------"));
//
//        System.out.println(valueOf("id    cxk",iso));
//        System.out.println(valueOf("id    cxk    ",iso));
//        System.out.println(valueOf("    id    cxk",iso));
//
//    }
}
