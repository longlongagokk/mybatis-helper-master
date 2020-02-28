package club.yourbatis.hi.wrapper.join;

import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.enums.JoinType;
import club.yourbatis.hi.wrapper.bridge.AbstractConditionWrapper;
import lombok.Getter;

@Getter
public class JoinWrapper<C extends AbstractConditionWrapper> {
    private JoinType joinType;
    private TableMetaInfo tableInfo;
    private String alias;
    private C where;
    public JoinWrapper(JoinType joinType,TableMetaInfo tableInfo,String alias,C where){
        this.joinType = joinType;
        this.tableInfo = tableInfo;
        this.alias = alias;
        this.where = where;
    }

}
