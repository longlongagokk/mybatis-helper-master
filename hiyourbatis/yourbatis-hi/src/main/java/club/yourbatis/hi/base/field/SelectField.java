package club.yourbatis.hi.base.field;

import club.yourbatis.hi.consts.ConstValue;

public class SelectField extends SimpleField {
    private String originalInfo;
    private boolean original;
    /**
     * column's alias
     */
    private String columnAlias;
    public SelectField(String fullInfo, boolean original){
        super();
        //use original sql
        this.original = original;
        if(original){
            this.originalInfo = fullInfo;
            super.fullName = fullInfo;
            return;
        }


        int dotIndex = fullInfo.indexOf(ConstValue.DOT);
        if(dotIndex == -1){
            this.name = fullInfo.trim();
        }else{
            this.alias = fullInfo.substring(0,dotIndex).trim();
            this.name = fullInfo.substring(dotIndex+1).trim();
        }

        //end table alias，set columnAlias
        int bkIndex = this.name.indexOf(ConstValue.BLANK);
        if(bkIndex != -1){
            this.columnAlias = this.name.substring(bkIndex + 1).trim();
            this.name = this.name.substring(0,bkIndex).trim();

            this.fullName = dotIndex != -1 ? this.alias + ConstValue.DOT + this.name : this.name;
        }else{
            this.columnAlias = this.name;
            this.fullName = fullInfo;
        }

    }
    public SelectField(String itemWithAlias){
        this(itemWithAlias,false);
    }
    public static SelectField valueOf(String itemWithAlias, boolean original){
        return new SelectField(itemWithAlias,original);
    }
    public static SelectField valueOf(String itemWithAlias){
        return valueOf(itemWithAlias,false);
    }
    public static SelectField valueOf(String alias, Enum item){
        return valueOf(alias + "." + item,false);
    }
    public String getOriginalInfo() {
        return originalInfo;
    }

    public boolean isOriginal() {
        return original;
    }

    public String getColumnAlias() {
        return columnAlias;
    }
    @Override
    public String toString(){
        return "alias="+this.getAlias() + ",name=" + this.getName()
                + ",fullName=" + this.getFullName()
                + ",columnAlias=" + this.getColumnAlias()
                + ",originalInfo="+ this.getOriginalInfo();
    }
//    public static void main(String[] args){
//        System.out.println(valueOf("id"));
//        System.out.println(valueOf("id  "));
//        System.out.println(valueOf("  id"));
//        System.out.println(valueOf("  id   "));
//
//        System.out.println(("------------------------------------------------"));
//
//        System.out.println(valueOf("a.id"));
//        System.out.println(valueOf("a.id   "));
//        System.out.println(valueOf("   a.id"));
//        System.out.println(valueOf("   a.id   "));
//
//        System.out.println(("------------------------------------------------"));
//
//
//        System.out.println(valueOf("a.id    cxk"));
//        System.out.println(valueOf("a.id    cxk   "));
//        System.out.println(valueOf("   a.id    cxk"));
//        System.out.println(valueOf("   a.id    cxk   "));
//
//        System.out.println(("------------------------------------------------"));
//
//        System.out.println(valueOf("id    cxk"));
//        System.out.println(valueOf("id    cxk    "));
//        System.out.println(valueOf("    id    cxk"));
//        System.out.println(valueOf("    id    cxk    "));
//
//    }
}
