package club.yourbatis.hi.wrapper.query;

import club.yourbatis.hi.base.Field;
import club.yourbatis.hi.base.Page;
import club.yourbatis.hi.base.Sortable;
import club.yourbatis.hi.base.field.SelectField;
import club.yourbatis.hi.base.field.SimpleField;
import club.yourbatis.hi.base.meta.Sorter;
import club.yourbatis.hi.base.meta.TableMetaInfo;
import club.yourbatis.hi.enums.Order;
import club.yourbatis.hi.wrapper.IOrder;
import club.yourbatis.hi.wrapper.IPager;
import club.yourbatis.hi.wrapper.ISelectorWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractConditionWrapper;
import club.yourbatis.hi.wrapper.condition.AbstractJoinerWrapper;
import club.yourbatis.hi.wrapper.factory.StringConditionWrapper;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class SelectWrapper<L,R,C extends AbstractConditionWrapper<L,R,C>>
        extends AbstractJoinerWrapper<L,R,C, SelectWrapper<L,R,C>>
        implements ISelectorWrapper<SelectWrapper<L,R,C>,C>,
        IPager<SelectWrapper<L,R,C>>, IOrder<SelectWrapper<L,R,C>>
{
    /**
     * 选取的field字段
     */
    Set<SelectField> selectItems;
    Set<Sortable> sortItems;
    @Getter
    Page page;
    protected boolean selectMain;
    protected boolean lock;
    public SelectWrapper(C where){
        super(where,new ArrayList<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE));
        this.selectItems = new LinkedHashSet<>(AbstractConditionWrapper.DEFAULT_CONDITION_ELEMENTS_SIZE);
        this.selectMain = false;
        sortItems = new LinkedHashSet<>(1<<3);
    }
    public static DefaultSelectWrapper build(){
        return new DefaultSelectWrapper();
    }
    public static class DefaultSelectWrapper
    extends SelectWrapper<String,Object,StringConditionWrapper>
    {
        public DefaultSelectWrapper(){
            super(new StringConditionWrapper());
        }
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
    @Override
    public Collection<SelectField> selects() {
        return this.selectItems;
    }

    @Override
    public SelectWrapper<L,R,C> select0(SelectField... fields) {
        Assert.notEmpty(fields,"items can not be empty");
        Collections.addAll(selectItems, fields);
        return this;
    }
    @Override
    public SelectWrapper<L,R,C> select1(Enum... fields) {
        Assert.notEmpty(fields,"items can not be empty");
        for(Enum e:fields){
            selectItems.add(SelectField.valueOf(e.name()));
        }
        return this;
    }
    @Override
    public SelectWrapper<L,R,C> select(String fields) {
        if(StringUtils.isEmpty(fields)){
            return this;
        }
        String[] split = fields.split(",");
        for (String s : split) {
            this.selectItems.add(SelectField.valueOf(s));
        }
        return this;
    }

    @Override
    public SelectWrapper<L,R,C> selectMain(boolean selectMain) {
        this.selectMain = selectMain;
        return this;
    }

    @Override
    public SelectWrapper<L,R,C> orderBy(Order order, Field ...fields){
        this.sortItems.add(Sorter.valueOf(order,fields));
        return this;
    }
    @Override
    public SelectWrapper<L,R,C> orderBy(Order order, String strings){
        if(StringUtils.isEmpty(strings)){
            return this;
        }
        String[] fstr = strings.split(",");
        Field[] fields = new Field[fstr.length];
        for(int i = 0;i<fields.length;++i){
            fields[i] = SimpleField.valueOf(fstr[i]);
        }
        return orderBy(order,fields);
    }
    @Override
    public SelectWrapper<L,R,C> page(Page page){
        this.page = page;
        return this;
    }
    @Override
    public SelectWrapper<L,R,C> lock(boolean lock){
        this.lock = lock;
        return this;
    }
}
