# SQL包装器
> sql包装器是使用最频繁的包装类/接口，可以根据包装器组装成不同的sql执行语句，非常灵活、简单。
## IQueryWrapper
> `IQueryWrapper<C>` 是个简单的模板查询接口，简单的条件查询包装比如查询是否存在、查询符合条件的数量可以使用该包装器，默认实现类QueryWrapper
```java

    //构建一个简单的属性查询包装器
    QueryWrapper<PropertyConditionWrapper> queryWrapper = QueryWrapper.build();
    
    //选择 TbOrderForm类对应的表，查询别名 使用默认值 e
    queryWrapper.from(TbOrderForm.class);
    
    // where e.pay_state > 1
    queryWrapper.where(w->w
        .ge("e.payState",1)
    );

    int paidOrderCount = staticBoundMapper.selectCount(queryWrapper);
```
> sql打印：
```mysql
select count(1) from `mybatis-helper-demo`.`tb_order_form` e where e.`pay_state` >= ?
```
## ISelectorWrapper
> `ISelectorWrapper<C>` 也是一个模板查询包装接口，包含查询列、分页等操作；默认实现类 `SelectWrapper`,使用方式：
```java
        //构建查询包装器
        SelectWrapper<PropertyConditionWrapper> selectWrapper = SelectWrapper.build();

        //选择 TbOrderForm类对应的表，查询别名为of（）
        selectWrapper.from(TbOrderForm.class,"of");

        //select of.id,of.member_id,of.order_no from `mybatis-helper-demo`.`tb_order_form`;
        //在类TbOrderForm中，属性id、memberId、orderNo分别对应字段 id、member_id、order_no（类的属性注解@Column）
        selectWrapper.select("of.id,of.memberId,of.orderNo");

        //使用原生sql查询(假设存在 tb_order_comment 表)
        selectWrapper.select(SelectField.valueOf("(select count(1)from tb_order_comment where order_id=of.id) as commentCounts",true));

        // where of.pay_state = 1
        selectWrapper.where(w->w
            .eq("of.payState",1)
        );

        // limit 0,10
        selectWrapper.page(PageInfo.valueOf(1,10));

        //执行查询
        PageList<TvOrderForm> orders = orderFormService.selectPageListV(selectWrapper);
```
> 打印sql：
```mysql
select SQL_CALC_FOUND_ROWS of.`id` `id`,of.`member_id` `memberId`,of.`order_no` `orderNo`,(select count(1)from tb_order_comment where order_id=of.id) as commentCounts from `mybatis-helper-demo`.`tb_order_form` of where of.`pay_state` = ? limit 0,10;SELECT FOUND_ROWS();
```
> 框架使用的分页方式为单次查询返回所影响的数量；关键字`SQL_CALC_FOUND_ROWS`；短语：`SELECT FOUND_ROWS()`; 需要mysql支持多语句查询
## IUpdateWrapper
> `IUpdateWrapper<C>` 是一个根据条件更新部分字段的sql包装器，默认实现为 `UpdateWrapper`
```java

        //创建一个根据条件修改的修改包装器
        UpdateWrapper<PropertyConditionWrapper> updateWrapper = UpdateWrapper.build();

        //update from tb_order_form
        updateWrapper
                .from(TbOrderForm.class,"of")
                
                //1，根据属性匹配列更新，参数化：（of.`pay_state` = ?）
                .set("of.payState",1)
                
                //2，值为原生sql，非参数化：（of.`deal_status` = of.pay_state）（*有sql注入风险）
                .set(FieldWithValue.withOriginalValue("of.dealStatus","of.pay_state"))
                
                //3，同1，参数化：（of.`leave_message` = ?）
                .set(FieldWithValue.withParamValue("of.leaveMessage","微辣"))
                
                //4，同2，非参数化：（of.`delivery_status` = 4）（*有sql注入风险）
                .set(FieldWithValue.valueOf("of.deliveryStatus",ValueItem.valueOf(4)))
                
                //5,同1，参数化：（of.`leave_message` = ?）
                .set(FieldWithValue.valueOf("of.voteTitle",ParamItem.valueOf("大梅沙")))
                
                //6,//根据属性匹配列之间传值，（of.`vote_content` = of.`vote_title`）
                .set(FieldWithValue.valueOf("of.voteContent",FieldItem.valueOf("of.voteTitle")))
                
                // where of.id = 3
                .where(w->w.eq("of.id",3))
        ;
        int effects = staticBoundMapper.updateSelectItem(updateWrapper);
```
> sql打印：
```mysql
update `mybatis-helper-demo`.`tb_order_form` of set of.`vote_content` = of.`vote_title`,of.`vote_title` = ?,of.`delivery_status` = 4,of.`leave_message` = ?,of.`deal_status` = of.pay_state,of.`pay_state` = ? where of.`id` = ?
Parameters: 大梅沙(String), 微辣(String), 1(Integer), 3(Integer)
```
## IDeleteWrapper
> `IDeleteWrapper<C>` 是一个根据条件删除数据的sql包装器，默认实现为 `DeleteWrapper`
```java

        //delete of from tb_order_form of where of.id = 3
        deleteWrapper
                .delete("of")
                .from(TbOrderForm.class,"of")
                .where(w->w
                    .eq("of.id",389)
                );
        int effects = staticBoundMapper.delete(deleteWrapper);
```
> sql打印：
```mysql
delete of from `mybatis-helper-demo`.`tb_order_form` of where of.`id` = ?
Parameters: 389(Integer)
```