package com.mybatishelper.core.wrapper.query;

import com.mybatishelper.core.base.Page;
import com.mybatishelper.core.base.Primary;
import com.mybatishelper.core.base.meta.GroupInfo;
import com.mybatishelper.core.base.meta.SelectInfo;
import com.mybatishelper.core.base.meta.SortInfo;
import com.mybatishelper.core.cache.EntryFieldInfo;
import com.mybatishelper.core.cache.TableMetaInfo;
import com.mybatishelper.core.consts.ConstValue;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.CollectionUtils;
import com.mybatishelper.core.util.TableInfoHelper;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.bridge.AbsSqlProvider;
import com.mybatishelper.core.wrapper.seg.LinkGroupBySeg;
import com.mybatishelper.core.wrapper.seg.LinkHavingSeg;
import com.mybatishelper.core.wrapper.seg.LinkOrderSeg;
import com.mybatishelper.core.wrapper.seg.LinkSelectSeg;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

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
        StringBuilder selectSql = new StringBuilder("select ");

        for(Map.Entry<String, EntryFieldInfo> entry : tableMetaInfo.getFieldInfos().entrySet()){
            selectSql
                    .append(entry.getValue().getColumn())
                    .append(ConstValue.BLANK)
                    .append(ConstValue.BACK_TRICKS)
                    .append(entry.getKey())
                    .append(ConstValue.BACK_TRICKS)
                    .append(ConstValue.COMMA)
            ;
        }
        selectSql.deleteCharAt(selectSql.length()-1);
        selectSql.append(" from ")
        .append(tableMetaInfo.getTableName())
        .append(" where ")
        .append(tableMetaInfo.getPrimary().getColumn())
        .append(" = #{value}")
        .toString();
        return selectSql.toString();
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
        List<SelectInfo> selectInfos = selectWrapper.selectItems;
        StringBuilder selectSql = new StringBuilder("select ");
        if(SQL_CALC_FOUND_ROWS){
            selectSql.append(" SQL_CALC_FOUND_ROWS ");
        }
        //#region select items
        //select selectItems first
        if(!CollectionUtils.isEmpty(selectInfos)){
            selectSql.append(LinkSelectSeg.valueOf(selectInfos.toArray(new SelectInfo[0])).createSql(selectWrapper));
        }
        //select own next
        if (selectOwn || CollectionUtils.isEmpty(selectInfos)) {
            TableMetaInfo mainMeta = tableMetaInfoMap.get(ConstValue.MAIN_ALIAS);
            Assert.notNull(mainMeta,"alias e can not found in aliasTables，or you have none field to select");
            for(Map.Entry<String, EntryFieldInfo> entry : mainMeta.getFieldInfos().entrySet()){
                selectSql
                        .append(ConstValue.MAIN_ALIAS)
                        .append(ConstValue.DOT)
                        .append(entry.getValue().getColumn())
                        .append(ConstValue.BLANK)
                        .append(ConstValue.BACK_TRICKS)
                        .append(entry.getKey())
                        .append(ConstValue.BACK_TRICKS)
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

        //group bys
        selectSql.append(LinkGroupBySeg.valueOf(selectWrapper.groupBys).createSql(selectWrapper));

        //having
        selectSql.append(LinkHavingSeg.valueOf(selectWrapper.havingInfo).createSql(selectWrapper));

        //order
        List<SortInfo> sortItems = selectWrapper.sortItems;
        if(!sortItems.isEmpty()){
            selectSql.append(LinkOrderSeg.valueOf(sortItems.toArray(new SortInfo[0])).createSql(selectWrapper));
        }

        return selectSql.toString();
    }

    public String selectCount(ProviderContext context, IQueryWrapper wrapper) {
        QueryWrapper countWrapper = (QueryWrapper)wrapper;
        checkAndReturnFromTables(context,countWrapper);
        StringBuilder countSql = new StringBuilder("select count(1) from ");

        //from tables
        createFromTableSql(countSql,countWrapper);

        //join infos
        createJoinInfoSql(countSql,countWrapper);

        //conditions
        createWhereSql(countSql,countWrapper);

        //group bys
        countSql.append(LinkGroupBySeg.valueOf(countWrapper.groupBys).createSql(countWrapper));

        //having
        countSql.append(LinkHavingSeg.valueOf(countWrapper.havingInfo).createSql(countWrapper));

        return countSql.toString();
    }

    public String selectExists(ProviderContext context, IQueryWrapper wrapper) {
        QueryWrapper queryWrapper = (QueryWrapper)wrapper;
        checkAndReturnFromTables(context,queryWrapper);
        StringBuilder existsSql = new StringBuilder("select case when exists(select 1 from ");

        //from tables
        createFromTableSql(existsSql,queryWrapper);

        //join infos
        createJoinInfoSql(existsSql,queryWrapper);

        //conditions
        createWhereSql(existsSql,queryWrapper);

        //group bys
        existsSql.append(LinkGroupBySeg.valueOf(queryWrapper.groupBys).createSql(queryWrapper));

        //having
        existsSql.append(LinkHavingSeg.valueOf(queryWrapper.havingInfo).createSql(queryWrapper));

        existsSql.append(") then 1 else 0 end as exists_record from dual");
        return existsSql.toString();
    }

}
