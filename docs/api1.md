# 条件构造器
> 仔细就会发现，四个SQL包装器都是带有模板参数的接口，`SqlWrapperFactory` 工厂类 生产了`PropertyConditionWrapper`、`FlexibleConditionWrapper` 
这两个类作为模板参数的包装器，那么，`PropertyConditionWrapper`是什么呢？
其实就是一个条件构造器。除了`PropertyConditionWrapper`，框架还提供了另外一个条件构造器`FlexibleConditionWrapper`，该构造器非常灵活,两者的区别是
`PropertyConditionWrapper` 是 `FlexibleConditionWrapper`的特殊版本，是根据数据dto的属性映射列来生成条件，查询方式为参数化查询。
## PropertyConditionWrapper
> `PropertyConditionWrapper`,属性构造器 是根据数据dto模型类中的属性进行条件转化查询，比如有以下实体类：
```java

@Table("`mybatis-helper-demo`.`tb_order_form`")
@Data()
@Accessors(chain = true)
public class TbOrderForm {

    /**
     * 会员id
     */
    @Column("`member_id`")
    private Long memberId;

    /**
     * 订单号
     */
    @Column("`order_no`")
    private String orderNo;
}
```
> 使用属性构造器构造条件结果如下：
```java

    Consumer<PropertyConditionWrapper> c = x-> x
            .eq("e.orderNo","123456")
            .eq("e.memberId","8888");

    DeleteWrapper<PropertyConditionWrapper> deleteWrapper = SqlWrapperFactory.prop4Delete();
    //delete from tb_order_form where e.id = 123456 and e.member_id = 8888
    deleteWrapper
            .from(TbOrderForm.class)
            .where(c);
    int effects = staticBoundMapper.delete(deleteWrapper);
```
> 打印sql：
```mysql
delete e from `mybatis-helper-demo`.`tb_order_form` e where e.`order_no` = ? AND e.`member_id` = ?
Parameters: 123456(String), 8888(String)
```
> 我们发现，属性构造器方法eq的第一个参数为实体类属性，而第二个参数为条件值，比如 `eq("e.orderNo","123456")` 转化为 `e.order_no = ?`,
传参后值为123456（string）

> _注意到我们每个SQL包装器的where方法的参数均为一个Consumer接口包装的条件构造器。_
## FlexibleConditionWrapper
> `FlexibleConditionWrapper`,弹性构造器，可以根据不同场合下使用不用的类型来构造，典型的像join、多表关联查询时使用到属性相关来构造,例如有sql如下：

>`select od.order_id,of.deal_status from tb_order_detail od left join tb_order_form of on of.id = od.order_id where od.pro_price > 9.9`
>> _查询单价大于9.9元的订单id、订单处理状态列表_

>这时我们就可以根据弹性构造器来构建查询了：
>```mysql
    Consumer<FlexibleConditionWrapper> lc = f->
            f.eq(FieldItem.valueOf("of.id"),FieldItem.valueOf("od.orderId"))
            ;
    Consumer<FlexibleConditionWrapper> c = w->
            w.gt(FieldItem.valueOf("od.proPrice"),ValueItem.valueOf(9.9))
            ;
    SelectWrapper<FlexibleConditionWrapper>
            selectWrapper = SqlWrapperFactory.flex4Select()
            .select("od.orderId,of.dealStatus")
            .from(TbOrderDetail.class,"od")
            .leftJoin(TbOrderForm.class,"of",lc)
            .where(c)
            ;
    List effects = orderDetailService.selectList(selectWrapper);
```











