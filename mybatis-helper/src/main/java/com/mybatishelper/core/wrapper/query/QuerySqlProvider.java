package com.mybatishelper.core.wrapper.query;

import com.mybatishelper.core.base.Page;
import com.mybatishelper.core.base.Primary;
import com.mybatishelper.core.base.field.OrderField;
import com.mybatishelper.core.base.field.SelectField;
import com.mybatishelper.core.base.meta.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.ICountWrapper;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.seg.LinkOrderSeg;
import com.mybatishelper.core.wrapper.seg.LinkSelectSeg;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 后期记得加缓存
 */
@Slf4j
public class QuerySqlProvider extends AbsSqlProvider {
    public String selectItemByPrimaryKey(ProviderContext context, Primary primary) {
        TableMetaInfo tableMetaInfo = TableInfoHelper.getTableInfoByProviderContext(context);
        Assert.notNull(tableMetaInfo.getPrimary(),String.format("table {0} do not have any primaryKey!", tableMetaInfo.getTableName()));
        StringBuilder sb = new StringBuilder("select ");

        Iterator<Map.Entry<String, String>> it = tableMetaInfo.getFieldWithColumns().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb
                    .append(entry.getValue())
                    .append(ConstValue.BLANK)
                    .append(ConstValue.BACKTRICKS)
                    .append(entry.getKey())
                    .append(ConstValue.BACKTRICKS)
                    .append(ConstValue.COMMA)
            ;
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" from ")
        .append(tableMetaInfo.getTableName())
        .append(" where ")
        .append(tableMetaInfo.getPrimary())
        .append(" = #{value}")
        .toString();
        return sb.toString();
    }
    public String selectOne(ProviderContext context, ISelectorWrapper wrapper) {
        return select(context, wrapper,false) + " limit 1";
    }

    public String selectList(ProviderContext context, ISelectorWrapper wrapper) {
        String sql = select(context,wrapper,false);
        Page page = wrapper.getPage();
        if (page != null) {
            return sql + " limit " + (page.getPageSize() * (page.getPageIndex() - 1)) + "," + page.getPageSize();
        }
        return sql;
    }
    public String selectPageList(ProviderContext context, ISelectorWrapper wrapper) {
        String sql = select(context,wrapper,true);
        Page page = wrapper.getPage();
        if (page != null) {
            sql += " limit " + (page.getPageSize() * (page.getPageIndex() - 1)) + "," + page.getPageSize();
        }
        //
        sql += ";SELECT FOUND_ROWS();";
        return sql;
    }
    private String select(ProviderContext context, ISelectorWrapper wrapper,boolean SQL_CALC_FOUND_ROWS){
        SelectWrapper selectWrapper = (SelectWrapper)wrapper;
        Map<String, TableMetaInfo> tableMetaInfoMap = checkAndReturnFromTables(context,selectWrapper);
        boolean selectOwn = selectWrapper.selectMain;
        List<SelectField> selectFields = selectWrapper.selectItems;
        StringBuilder selectSql = new StringBuilder("select ");
        if(SQL_CALC_FOUND_ROWS){
            selectSql.append(" SQL_CALC_FOUND_ROWS ");
        }
        //#region select items
        //select selectItems first
        if(!CollectionUtils.isEmpty(selectFields)){
            selectSql.append(LinkSelectSeg.valueOf(selectFields.toArray(new SelectField[0])).createSql(selectWrapper));
        }
        //select own next
        if (selectOwn || CollectionUtils.isEmpty(selectFields)) {
            TableMetaInfo mainMeta = tableMetaInfoMap.get(ConstValue.MAIN_ALIAS);
            Iterator<Map.Entry<String, String>> it = mainMeta.getFieldWithColumns().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                selectSql
                        .append(ConstValue.MAIN_ALIAS)
                        .append(ConstValue.DOT)
                        .append(entry.getValue())
                        .append(ConstValue.BLANK)
                        .append(ConstValue.BACKTRICKS)
                        .append(entry.getKey())
                        .append(ConstValue.BACKTRICKS)
                        .append(ConstValue.COMMA)
                ;
            }
        }
        selectSql.deleteCharAt(selectSql.length() - 1);
        //#endregion
        selectSql.append(" from ");
        //from tables
        createFromTableSql(selectSql,selectWrapper);

        //join infos
        createJoinInfoSql(selectSql,selectWrapper);

        //conditions
        createWhereSql(selectSql,selectWrapper);

        //order
        List<OrderField> sortItems = selectWrapper.orderItems;
        if(!sortItems.isEmpty()){
            selectSql.append(" order by ");
            selectSql.append(LinkOrderSeg.valueOf(sortItems.toArray(new OrderField[0])).createSql(selectWrapper));
            selectSql.deleteCharAt(selectSql.length() - 1);
        }

        return selectSql.toString();
    }

    public String selectCount(ProviderContext context, ICountWrapper wrapper) {
        CountWrapper countWrapper = (CountWrapper)wrapper;
        checkAndReturnFromTables(context,countWrapper);
        StringBuilder countSql = new StringBuilder("select count(1) from ");

        //from tables
        createFromTableSql(countSql,countWrapper);

        //join infos
        createJoinInfoSql(countSql,countWrapper);

        //conditions
        createWhereSql(countSql,countWrapper);

        return countSql.toString();
    }

}
