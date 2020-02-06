package club.yourbatis.hi.wrapper.delete;

import club.yourbatis.hi.base.Primary;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.consts.ConstValue;
import club.yourbatis.hi.util.Assert;
import club.yourbatis.hi.util.TableInfoHelper;
import club.yourbatis.hi.wrapper.IDeleteWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

@Slf4j
public class DeleteWrapProvider {

    public String deleteByPrimaryKey(ProviderContext context, Primary primary) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoByProviderContext(context);
        Assert.notNull(tableMetaInfo.getPrimary(),String.format("table {0} do not have any primaryKey!", tableMetaInfo.getTableName()));
        return new StringBuilder("delete from ")
                .append(tableMetaInfo.getTableName())
                .append(" where ")
                .append(tableMetaInfo.getPrimary().getName())
                .append(" = #{value}")
                .toString();
    }

    public String delete(ProviderContext context, IDeleteWrapper wrapper) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoByProviderContext(context);
        DeleteWrapper deleteWrapper = (DeleteWrapper)wrapper;
        deleteWrapper.addAliasTable(null,tableMetaInfo);
        deleteWrapper.addAliasTable(ConstValue.MAIN_ALIAS,tableMetaInfo);
        String conditionSql = deleteWrapper.getConditionSql();
        Assert.notEmpty(conditionSql,"could not delete data with no conditions!");
        return new StringBuilder("delete from ")
                .append(tableMetaInfo.getTableName())
                .append(" where ")
                .append(conditionSql)
                .toString();
    }
}
