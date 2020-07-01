# IConditioner
> `IConditioner` 接口提供了 不同的查询语句方法，比如等于、不等于、大于、小于、in、notin 等待。
## eq
> `eq(L left,R right)`，如以 `PropertyConditionWrapper` 为例，`eq("orderNo","123456")`
```java
        //sql:e.order_no = ?
        //Parameters: 123456(String)
        Consumer<PropertyConditionWrapper> c = x-> x.eq("e.orderNo","123456");
```
> `eq(FieldValue fv)`，`=`操作符左边为dto对象属性对应的列，右边为自定义，如：
```java
    Consumer<PropertyConditionWrapper> c = x-> x
            //左边属性，右边是原值：右边有sql注入风险，等同 e.order_no = '1'
            .eq(FieldWithValue.withOriginalValue("e.orderNo","'1'"))

            //左边属性，右边是参数化传值,等同 e.order_no = ? (?传 2(String类别))
            .eq(FieldWithValue.withParamValue("e.orderNo","2"))

            //左边属性，右边是原值，右边有sql注入风险，等同 e.order_no = 3
            .eq(FieldWithValue.valueOf("e.orderNo",ValueItem.valueOf("3")))

            //左边属性，右边是参数化传值,等同 e.order_no = ? (?传 4(String类别))
            .eq(FieldWithValue.valueOf("e.orderNo",ParamItem.valueOf("4")))

            //左边属性，右边也是属性,等同 e.order_no = e.member_id
            .eq(FieldWithValue.valueOf("e.orderNo",FieldItem.valueOf("e.memberId")))
            ;
```
## gt
> `gt(L left,R right)`，大于操作符
- `PropertyConditionWrapper` 例子：`gt("orderNo","123456")` ==> sql：`order_no > 123456`

> `gt(FieldValue fv)`，[参考eq](#api2##eq)

## lt
> `lt(L left,R right)`，小于操作符
- `PropertyConditionWrapper` 例子：`lt("orderNo","123456")` ==> sql：`order_no < 123456`

> `lt(FieldValue fv)`，[参考eq](#api2##eq)

## ge
> `ge(L left,R right)`，大于等于操作符
- `PropertyConditionWrapper` 例子：`ge("orderNo","123456")` ==> sql：`order_no >= 123456`

> `ge(FieldValue fv)`，[参考eq](#api2##eq)

## le
> `le(L left,R right)`，小于等于操作符
- `PropertyConditionWrapper` 例子：`le("orderNo","123456")` ==> sql：`order_no <= 123456`

> `le(FieldValue fv)`，[参考eq](#api2##eq)

## neq
> `neq(L left,R right)`，不等于操作符
- `PropertyConditionWrapper` 例子：`neq("orderNo","123456")` ==> sql：`order_no <> 123456`

> `neq(FieldValue fv)`，[参考eq](#api2##eq)

## like
> `like(L left,R right)`，like 操作符
- `PropertyConditionWrapper` 例子：`like("orderNo","123456")` ==> sql：`order_no like 123456`
- `PropertyConditionWrapper` 模糊查询：`like("orderNo","%123456%")` ==> sql：`order_no like %123456%`
```java
    String likeWhat = "%" + "123456" + "%";
    Consumer<PropertyConditionWrapper> c = x-> x.like("e.orderNo",likeWhat);
```
> `like(FieldValue fv)`，[参考eq](#api2##eq)

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

> `le(FieldValue fv)`，[参考eq](#api2##eq)

## le
> `le(L left,R right)`，小于等于操作符
- 例子：`le("orderNo","123456")` ==> sql：`order_no <= 123456`

> `le(FieldValue fv)`，[参考eq](#api2##eq)

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
- `FlexibleConditionWrapper` 例子：
```java
 test
```

## or嵌套
> [参考eq](#api2##and嵌套)

