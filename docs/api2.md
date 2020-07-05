# IConditioner
> `IConditioner` 接口提供了 不同的查询语句方法，比如等于、不等于、大于、小于、in、notin 等待。
## eq
> `eq(L left,R right)`，如以 `PropertyConditionWrapper` 为例，`eq("orderNo","123456")`
```java
        //sql:e.order_no = ?
        //Parameters: 123456(String)
        Consumer<PropertyConditionWrapper> c = x-> x.eq("e.orderNo","123456");
```
> `eq(ItemPar par)`，以一个键值对ItemPar为条件项，key为操作符左边，value为操作符右边，如：
```java
    Consumer<PropertyConditionWrapper> c = x-> x
            //左边属性，右边是原值：右边有sql注入风险，等同 e.order_no = '1'
            .eq(ItemPar.withFieldValue("e.orderNo","'1'"))

            //左边属性，右边是参数化传值,等同 e.order_no = ? (Parameters: 2(String))
            .eq(ItemPar.withFieldParam("e.orderNo","2"))

            //左边属性，右边也是属性,等同 e.order_no = e.member_id
            .eq(ItemPar.withFieldField("e.orderNo","e.memberId"))
            ;
```

- ### `ItemPar` 
> 一个以[item](api1#item)为键和值的键值对，常用在查询条件处，其中属性key为键，即左操作项目，value为右操作项目。

    <table>
     <tr>
         <th>构造方法</th>
         <th>描述</th>
         <th>示例</th>
     </tr>
     <tr>
         <td>withFieldField(String, String)</td>
         <td>以两个属性关联的方式进行查询</td>
         <td>`eq(ItemPar.withFieldField("e.memberId","e.orderNo")` ==> sql: `e.member_id = e.order_no`</td>
     </tr>
     <tr>
         <td>withFieldValue(String, T)</td>
         <td>键为属性、值为原值</td>
         <td>`eq(ItemPar.withFieldValue("e.memberId",12345)` ==> sql: `e.member_id = 12345`</td>
     </tr>
     <tr>
         <td>withFieldParam(String, T)</td>
         <td>键为属性、值为参数化替换</td>
         <td>`eq(ItemPar.withFieldParam("e.memberId",12345)` ==> sql: `e.member_id = ?`;Parameters:12345(Integer);</td>
     </tr>
     <tr>
         <td>withValueField(T, String)</td>
         <td>键为原值、值为可替换列的属性对象</td>
         <td>`eq(ItemPar.withValueField("e.member_id","e.orderNo")` ==> sql: `e.member_id = e.order_no`;</td>
     </tr>
     <tr>
         <td>withValueValue(T0, T1)</td>
         <td>键为原值、值也为原值</td>
         <td>`eq(ItemPar.withValueValue("e.member_id",12345)` ==> sql: `e.member_id = 12345`;</td>
     </tr>
     <tr>
         <td>withValueParam(T0, T1)</td>
         <td>键为原值、值为参数化替换</td>
         <td>`eq(ItemPar.withValueParam("e.member_id",12345)` ==> sql: `e.member_id = ?`;Parameters:12345(Integer);</td>
     </tr>
     <tr>
         <td>withParamField(T, String)</td>
         <td>键为参数化替换、值为可替换列的属性对象</td>
         <td>`eq(ItemPar.withParamField("e.member_id","e.orderNo")` ==> sql: `? = e.order_no`;Parameters:e.member_id(String)</td>
     </tr>
     <tr>
         <td>withParamValue(T0, T1)</td>
         <td>键为参数化替换、值为原值</td>
         <td>`eq(ItemPar.withParamValue("e.member_id",12345)` ==> sql: `? = 12345`;Parameters:e.member_id(String)</td>
     </tr>
     <tr>
         <td>withParamParam(T0, T1)</td>
         <td>键为参数化替换、值也为参数化替换</td>
         <td>`eq(ItemPar.withParamParam("e.member_id",12345)` ==> sql: `? = ?`;Parameters:e.member_id(String),12345(Integer);</td>
     </tr>
     </table>
     
    >_参考 [TbOrderForm](api1#tborderform)_


## gt
> `gt(L left,R right)`，大于操作符
- `PropertyConditionWrapper` 例子：`gt("orderNo","123456")` ==> sql：`order_no > 123456`

> `gt(ItemPar par)`，[参考eq](#api2##eq)

## lt
> `lt(L left,R right)`，小于操作符
- `PropertyConditionWrapper` 例子：`lt("orderNo","123456")` ==> sql：`order_no < 123456`

> `lt(ItemPar par)`，[参考eq](#api2##eq)

## ge
> `ge(L left,R right)`，大于等于操作符
- `PropertyConditionWrapper` 例子：`ge("orderNo","123456")` ==> sql：`order_no >= 123456`

> `ge(ItemPar par)`，[参考eq](#api2##eq)

## le
> `le(L left,R right)`，小于等于操作符
- `PropertyConditionWrapper` 例子：`le("orderNo","123456")` ==> sql：`order_no <= 123456`

> `le(ItemPar parfv)`，[参考eq](#api2##eq)

## neq
> `neq(L left,R right)`，不等于操作符
- `PropertyConditionWrapper` 例子：`neq("orderNo","123456")` ==> sql：`order_no <> 123456`

> `neq(ItemPar par)`，[参考eq](#api2##eq)

## like
> `like(L left,R right)`，like 操作符
- `PropertyConditionWrapper` 例子：`like("orderNo","123456")` ==> sql：`order_no like 123456`
- `PropertyConditionWrapper` 模糊查询：`like("orderNo","%123456%")` ==> sql：`order_no like %123456%`
```java
    String likeWhat = "%" + "123456" + "%";
    Consumer<PropertyConditionWrapper> c = x-> x.like("e.orderNo",likeWhat);
```
> `like(ItemPar par)`，[参考eq](#api2##eq)

## isNull
> `isNull(L left)`，判断为空操作符：生成的sql为：xxx is null
- `PropertyConditionWrapper` 例子：`isNull("orderNo")` ==> sql：`order_no is null`
- `FlexibleConditionWrapper` 例子：
```java
    Consumer<FlexibleConditionWrapper> c = x-> x
            //直接sql替换，等同 LENGTH(member_id) is null
            .isNull(ValueItem.valueOf("LENGTH(member_id)"))

            //判断某个值是否为空，传参查询，一般用不上，等同 null is null
            .isNull(ParamItem.valueOf(null))

            //判断字段是否为空 等同：e.order_no is null
            .isNull(FieldItem.valueOf("e.orderNo"))
            ;
```

## notNull
> `notNull(L left)`，判断不为空操作符：生成的sql为：xxx is not null
- `PropertyConditionWrapper` 例子：`notNull("orderNo")` ==> sql：`order_no is not null`
- `FlexibleConditionWrapper` 例子：[参考isNull](#api2##isnull)

## in
> `in(L left, Collection<?> values)`，in 操作符
- `PropertyConditionWrapper` 例子：`in("memberId",Arrays.asList(1,2,3))` ==> sql：`member_id in(?,?,?)`,Parameters:1(Integer),2(Integer),3(Integer)
- `FlexibleConditionWrapper` 例子：
```java
    Consumer<FlexibleConditionWrapper> c = x-> x
            //等同于 e.member_id in(1,2),有sql注入风险（所有ValueItem均有sql注入风险，请谨慎使用）
            .in(FieldItem.valueOf("e.memberId"),Arrays.asList(ValueItem.valueOf(1),ValueItem.valueOf(2)))

            //等同于 e.member_id in(?,4),Parameters:3(Integer)
            .in(FieldItem.valueOf("e.memberId"),Arrays.asList(ParamItem.valueOf(3),ValueItem.valueOf(4)))

            //等同于 e.member_id in(5,e.order_no)
            .in(FieldItem.valueOf("e.memberId"),Arrays.asList(ValueItem.valueOf(5),FieldItem.valueOf("e.orderNo")))
            ;
```
> _当使用 `FlexibleConditionWrapper` 构造器时，要保证in操作符右边参数集合不为空且类型应该为Item，否则会抛出类型不匹配错误_

## notIn
> `notIn(L left, Collection<?> values)`，not in 操作符
- `PropertyConditionWrapper` 例子：`notIn("memberId",Arrays.asList(1,2,3))` ==> sql：`member_id not in(?,?,?)`,Parameters:1(Integer),2(Integer),3(Integer)
- `FlexibleConditionWrapper` 例子：[参考in](#api2##in)

## between
> `between(L left, R r0, R r1)`，between 操作符
- `PropertyConditionWrapper` 例子：`between("id",1,5)` ==> sql：`id between 1 and 5`
- `FlexibleConditionWrapper` 例子：
```java
    Consumer<FlexibleConditionWrapper> c = x-> x
            //sql：e.member_id between 1 and ?;Parameters:5(Integer)
            .between(FieldItem.valueOf("e.memberId"),ValueItem.valueOf(1),ParamItem.valueOf(5))

            //sql：2 between e.member_id and ?;Parameters:6(Integer)
            .between(ValueItem.valueOf(2),FieldItem.valueOf("e.memberId"),ParamItem.valueOf(6))

            //sql：? between e.member_id and ?;Parameters:7(Integer)
            .between(ParamItem.valueOf(3),FieldItem.valueOf("e.memberId"),ParamItem.valueOf(7))
            ;

    Object effects = staticBoundMapper.selectCount(SqlWrapperFactory.flex4Query().from(TbOrderForm.class).where(c));
```
> sql打印：
```mysql
select count(1) from `mybatis-helper-demo`.`tb_order_form` e where ( e.`member_id` BETWEEN 1 AND ? ) AND ( 2 BETWEEN e.`member_id` AND ? ) AND ( ? BETWEEN e.`member_id` AND ? )
Parameters: 5(Integer), 6(Integer), 3(Integer), 7(Integer)
```

>

> `le(ItemPar par)`，[参考eq](#api2##eq)

## where
> `where(ConditionType type, L left, Collection<?> right)`，按 ConditionType 枚举罗列的条件进行查询，比如type 值为ConditionType.EQ时，等同 `eq(L left,right)`

- `PropertyConditionWrapper` 例子：
```java
        SqlWrapperFactory.prop4Select().where(w->w.
                // sql:e.id = ?;Parameter:2(Integer)
                where(ConditionType.EQ,"e.id",Arrays.asList(2))
                );
```
    > _当type = OR/AND/CLOSURE/LEFT_WRAPPER/RIGHT_WRAPPER时，该查询条件不做任何处理_    

    > _当type = DO_NOTHING时，抛出异常_

    >_当type = EQ/NEQ/GE/GT/LE/LT/LIKE时，要确保第三个参数只会有1个数据项，否则会出现意想不到的查询 参考[eq](#api2##eq)_

    >_当type = ISNULL/NOTNULL时，要确保第三项参数为空（但是不能为null）参考[isNull/notNull](#api2##isnull)_

    >_当type = IN/NOT_IN时，确保第三个参数至少有一项数据，参考[in/notIn](#api2##in)_

    >_当type = BETWEEN时，确保第三个参数要有两个数据项,参考[between](#api2##between)_

- `FlexibleConditionWrapper` SQL包装器的模板参数为弹性构造器时，可以灵活设置查询条件：
    > _当type = OR/AND/CLOSURE/LEFT_WRAPPER/RIGHT_WRAPPER时，该查询条件不做任何处理_    

    > _当type = DO_NOTHING时，该条件非常灵活，比如需要用到动态sql条件时，就可以把第二个参数设置为ValueItem,即可
    进行sql注入，比如 `EXISTS (select 1 from tb_order_detail where order_id = e.id and deltag = 0) `,可以如下表示：_
    ```java
          SelectWrapper<FlexibleConditionWrapper> selectWrapper =
                SqlWrapperFactory.flex4Select().where(w->w
                        .where(ConditionType.DO_NOTHING
                      ,ValueItem.valueOf("EXISTS (select 1 from tb_order_detail where order_id = e.id and deltag = 0) ")
                      ,Collections.EMPTY_LIST)
                        );
        List<TbOrderForm> effects = orderFormService.selectList(selectWrapper);
    ```
    >_sql打印：_
    ```mysql
2020-7-2 22:53:54 DEBUG Fetching JDBC Connection from DataSource [org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection,114] 
2020-7-2 22:53:54 DEBUG JDBC Connection [HikariProxyConnection@1707815299 wrapping com.mysql.cj.jdbc.ConnectionImpl@41d76141] will not be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-7-2 22:53:54 DEBUG ==>  Preparing: select e.`pay_way_name` `payWayName`,e.`update_date` `updateDate`,e.`send_date` `sendDate`,e.`vote_company` `voteCompany`,e.`deltag` `deltag`,e.`pay_state` `payState`,e.`pro_amount` `proAmount`,e.`pay_way_id` `payWayId`,e.`delivery_id` `deliveryId`,e.`amount_paid` `amountPaid`,e.`leave_message` `leaveMessage`,e.`order_type_str` `orderTypeStr`,e.`id` `id`,e.`email` `email`,e.`member_id` `memberId`,e.`create_date` `createDate`,e.`area` `area`,e.`order_no` `orderNo`,e.`address` `address`,e.`receiver` `receiver`,e.`vote_content` `voteContent`,e.`vote_title` `voteTitle`,e.`deal_status` `dealStatus`,e.`user_name` `userName`,e.`call` `call`,e.`delivery_way_name` `deliveryWayName`,e.`phone` `phone`,e.`post_code` `postCode`,e.`amount_pay` `amountPay`,e.`order_date` `orderDate`,e.`delivery_status` `deliveryStatus`,e.`delivery_cost` `deliveryCost` from `mybatis-helper-demo`.`tb_order_form` e where EXISTS (select 1 from tb_order_detail where order_id = e.id and deltag = 0)  [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
2020-7-2 22:53:54 DEBUG ==> Parameters:  [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
2020-7-2 22:53:54 DEBUG <==      Total: 0 [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
```

    >_当type = EQ/NEQ/GE/GT/LE/LT/LIKE时，要确保第三个参数只会有1个数据项，否则会出现意想不到的查询 参考[eq](#api2##eq)_

    >_当type = ISNULL/NOTNULL时，要确保第三项参数为空（但是不能为null）参考[isNull/notNull](#api2##isnull)_

    >_当type = IN/NOT_IN时，确保第三个参数至少有一项数据，参考[in/notIn](#api2##in)_

    >_当type = BETWEEN时，确保第三个参数要有两个数据项，参考[between](#api2##between)_

## and嵌套
> 嵌套查询在现实中会频繁使用，可以把SQL包装器的where方法当做and查询的最顶级嵌套。需要注意的是，
>and方法包裹的条件构造器里的条件是以`and` 操作符来连接的，比如：
>> `and(x->x.eq("id",1).neq("deltag",false).ge("payState",1))`
表示的sql条件是：` (id = 1 and deltag = false and pay_state = 1)`。
>
>>`or(x->x.eq("id",1).neq("deltag",false).ge("payState",1))`
表示的sql条件是：` (id = 1 or deltag = false or pay_state = 1)`。
>
>> - _框架支持多重嵌套_
- `PropertyConditionWrapper` 例子：
```java
//select orderNo from tb_order_form where id = 1 and pay_state != 1 and   (id = 123 or deal_status in (1,2,3) or   (id = 456 and deal_status = 4)   )   and deltag = false;代码可以如下表示
SelectWrapper<PropertyConditionWrapper> selectWrapper =
                SqlWrapperFactory.prop4Select().select("orderNo").where(w->w
                        .eq("id",1)
                        .neq("payState",1)
                        .or(x->x
                                .eq("id",123)
                                .in("dealStatus",Arrays.asList(1,2,3)))
                                .and(y->y
                                        .eq("id",456)
                                        .eq("dealStatus",4)
                                )
                        .eq("deltag",false)
                        );
        effects = orderFormService.selectList(selectWrapper);
```
> sql打印：
```mysql
Preparing: select `order_no` `orderNo` from `mybatis-helper-demo`.`tb_order_form` e where `id` = ? AND `pay_state` <> ? AND ( `id` = ? OR `deal_status` IN ( ?,?,? ) ) AND ( `id` = ? AND `deal_status` = ? ) AND `deltag` = ?
Parameters: 1(Integer), 1(Integer), 123(Integer), 1(Integer), 2(Integer), 3(Integer), 456(Integer), 4(Integer), false(Boolean)
```
- `FlexibleConditionWrapper` 与 `PropertyConditionWrapper`类似，只不过and的参数改为消费FlexibleConditionWrapper了。详情参考[FlexibleConditionWrapper](api1#FlexibleConditionWrapper)

## or嵌套
> [参考eq](#api2##and嵌套)

