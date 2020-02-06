package club.yourbatis.hi.wrapper.delete;

import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractQueryWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;

public class DeleteWrapper<L,R,C extends AbstractConditionWrapper<L,R,C>> extends AbstractQueryWrapper<L,R,C,DeleteWrapper<L,R,C>>
        implements IDeleteWrapper<DeleteWrapper<L,R,C>,C> {
    public DeleteWrapper(C where){
        super(where);
    }

    public static IDeleteWrapper<DeleteWrapper<String,Object,StringConditionWrapper>,StringConditionWrapper> build(){
        return new DeleteWrapper<>(new StringConditionWrapper());
    }
    @Override
    protected void addAliasTable(String alias, TableMetaInfo tb) {
        super.addAliasTable(alias, tb);
    }

    @Override
    protected String getConditionSql() {
        return super.getConditionSql();
    }
}
