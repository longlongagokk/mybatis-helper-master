package club.yourbatis.hi.wrapper.join;

import club.yourbatis.hi.base.TableInfo;
import club.yourbatis.hi.enums.JoinType;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.factory.FlexibleConditionWrapper;
import lombok.Getter;

@Getter
public class JoinWrapper<C extends AbstractConditionWrapper> {
    private JoinType joinType;
    private TableInfo tableInfo;
    private C where;
    public JoinWrapper(JoinType joinType,TableInfo tableInfo,C where){
        this.joinType = joinType;
        this.tableInfo = tableInfo;
        this.where = where;
    }

}
