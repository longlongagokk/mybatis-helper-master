# 条件构造器
> 仔细就会发现，四个SQL包装器都是带有模板参数的接口，`SqlWrapperFactory` 工厂类 生产了`PropertyConditionWrapper`、`FlexibleConditionWrapper` 
这两个类作为模板参数的包装器，那么，`PropertyConditionWrapper`是什么呢？
其实就是一个条件构造器。除了`PropertyConditionWrapper`，框架还提供了另外一个条件构造器`FlexibleConditionWrapper`，该构造器非常灵活,两者的区别是
`PropertyConditionWrapper` 是 `FlexibleConditionWrapper`的特殊版本，是根据数据dto的属性映射列来生成条件，查询方式为参数化查询。
## PropertyConditionWrapper
> `PropertyConditionWrapper`,属性构造器 是根据数据dto模型类中的属性进行条件转化查询，比如有以下实体类：   
 - ### TbOrderForm
```java
    @Table("`mybatis-helper-demo`.`tb_order_form`")
    @Data()
    @Accessors(chain = true)
    public class TbOrderForm extends BaseEntity<TbOrderForm> {
        /**
         * 配送方式id
         */
        @Column("`delivery_id`")
        private Long deliveryId;
    
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
    
        /**
         * 支付状态
         */
        @Column("`pay_state`")
        private Integer payState;
    
        /**
         * 收货人
         */
        @Column("`receiver`")
        private String receiver;
    
        /**
         * 省市区
         */
        @Column("`area`")
        private String area;
    
        /**
         * 详细地址
         */
        @Column("`address`")
        private String address;
    
        /**
         * 邮编
         */
        @Column("`post_code`")
        private String postCode;
    
        /**
         * 手机号码
         */
        @Column("`phone`")
        private String phone;
    
        /**
         * 固话
         */
        @Column("`call`")
        private String call;
    
        /**
         * 收货人邮箱
         */
        @Column("`email`")
        private String email;
    
        /**
         * 指定送货时间
         */
        @Column("`send_date`")
        private String sendDate;
    
        /**
         * 产品总额
         */
        @Column("`pro_amount`")
        private BigDecimal proAmount;
    
        /**
         * 运费
         */
        @Column("`delivery_cost`")
        private BigDecimal deliveryCost;
    
        /**
         * 订单总金额
         */
        @Column("`amount_pay`")
        private BigDecimal amountPay;
    
        /**
         * 已支付金额
         */
        @Column("`amount_paid`")
        private BigDecimal amountPaid;
    
        /**
         * 支付方式id
         */
        @Column("`pay_way_id`")
        private Long payWayId;
    
        /**
         * 发票抬头
         */
        @Column("`vote_title`")
        private String voteTitle;
    
        /**
         * 单位名称
         */
        @Column("`vote_company`")
        private String voteCompany;
    
        /**
         * 发票内容
         */
        @Column("`vote_content`")
        private String voteContent;
    
        /**
         * 物流状态
         */
        @Column("`delivery_status`")
        private Integer deliveryStatus;
    
        /**
         * 订单类型id组合
         */
        @Column("`order_type_str`")
        private String orderTypeStr;
    
        /**
         * 订单留言
         */
        @Column("`leave_message`")
        private String leaveMessage;
    
        /**
         * 处理状态
         */
        @Column("`deal_status`")
        private Integer dealStatus;
    
        /**
         * 下单时间
         */
        @Column("`order_date`")
        private Date orderDate;
    }
```

> 使用属性构造器构造某些条件结果如下：

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
```mysql
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
- ### `Item` 
> `FlexibleConditionWrapper`的模板参数为Item，有不同的实现类，分别代表不同的sql实现方式,
>需要注意的是，ValueItem为原值输入，有sql注入风险：

    <table>
     <tr>
         <th>实现类</th>
         <th>描述</th>
         <th>初始化</th>
         <th>示例</th>
     </tr>
     <tr>
         <td>ValueItem</td>
         <td>原值格式化sql</td>
         <th>valueOf(T value)</th>
         <td>`eq(ValueItem.valueOf(123),ValueItem.valueOf(456))` ==> sql: `123 = 456`</td>
     </tr>
     <tr>
         <td rowSpan=2>ParamItem</td>
         <td rowSpan=2>参数传值</td>
         <th>valueOf(T value)</th>
         <td>`eq(ParamItem.valueOf(123),ParamItem.valueOf(456))` ==> sql: `? = ?`(Parameters:123(Integer),456(Integer))</td>
     </tr>
     <tr>
         <th>valueOf(T value,int index)</th>
         <td>只在特殊情况下使用，比如用户自定义provider的情况就可以使用定制参数</td>
     </tr>
     <tr>
         <td rowSpan=2>FieldItem</td>
         <td rowSpan=2>通过dto类属性转化为@Column注解值</td>
         <th>valueOf(String fieldWithAlias)</th>
         <td> 别名(可为空) + "." + 属性名：如 FieldItem.valueOf("e.orderId") ==> e.order_id；</td>
     </tr>
     <tr>
         <th>valueOf(String alias,String name)</th>
         <td> alias + "." + 属性名name：如 FieldItem.valueOf("e","orderId") ==> e.order_id；</td>
     </tr>
     </table>
     
    >_参考 [TbOrderForm](###tborderform)_











