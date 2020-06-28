package com.mybatishelper.core.wrapper.join;

import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.enums.JoinType;
import com.mybatishelper.core.wrapper.bridge.AbstractConditionWrapper;
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
