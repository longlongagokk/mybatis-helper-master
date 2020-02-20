package club.yourbatis.hi.wrapper.query;

import club.yourbatis.hi.base.TableInfo;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.util.TableInfoHelper;
import club.yourbatis.hi.wrapper.ICountWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractJoinerWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;
import club.yourbatis.hi.wrapper.join.JoinWrapper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class CountWrapper<L,R,C extends AbstractConditionWrapper<L,R,C>>
        extends AbstractJoinerWrapper<L,R,C,CountWrapper<L,R,C>>
    implements ICountWrapper<CountWrapper<L,R,C>,C>
{
    public CountWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
    }
    public CountWrapper(C where,AbstractJoinerWrapper absWrapper){
        super(where,absWrapper);
    }
    public static ICountWrapper<CountWrapper<String,Object,StringConditionWrapper>,StringConditionWrapper> build(){
        return build(null);
    }
    public static ICountWrapper<CountWrapper<String,Object,StringConditionWrapper>,StringConditionWrapper> build(TableInfo tableInfo){
        CountWrapper countWrapper = new CountWrapper<>(new StringConditionWrapper());
        if(null != tableInfo){
            countWrapper.mainTableMetaInfo = TableInfoHelper.getTableInfoFromEntityClass(tableInfo.getTableClass());
        }
        return countWrapper;
    }
    @Override
    protected String getJoinerSql(){
        return super.getJoinerSql();
    }
    @Override
    protected String getConditionSql() {
        return super.getConditionSql();
    }
    @Override
    protected void addAliasTable(String alias, TableMetaInfo tb){
        super.addAliasTable(alias,tb);
    }
}
