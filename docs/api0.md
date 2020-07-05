# SQL包装器
> sql包装器是使用最频繁的包装类/接口，可以根据包装器组装成不同的sql执行语句，非常灵活、简单。
## IQueryWrapper
> `IQueryWrapper<C>` 是个简单的模板查询接口，简单的条件查询包装比如查询是否存在、查询符合条件的数量可以使用该包装器，默认实现类QueryWrapper

| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| C getWhere() | 获取一个SQL包装器初始化后的查询条件 | 如果不使用内置分页、可以使用该参数作为count的查询条件QueryWrapper queryWrapper1 = new QueryWrapper<>(queryWrapper.getWhere(),queryWrapper) |
| Map<String, TableMetaInfo> getAliasTableMetaInfos() | 获取当前已经加入的查询表信息（不可变） | - |
| S from(Class<?> tbClass,String alias) | 从dto类对应的表操作，alias为表别名，别名重复的话会覆盖之前的操作 | queryWrapper1.from(TbOrderForm.class,"order");==>sql:xxx from tb_order_form order; |
| S from(Class<?> tbClass) | 同上，不过别名默认为"e"; | queryWrapper1.from(TbOrderForm.class);==>sql:xxx from tb_order_form e; |
| S join(Class<?> tbClass,String alias, Consumer<C> consumer) | join 从dto类对应的表，alias为别名，consumer消费内部条件构造器； | queryWrapper1.join(TbOrderDetail.class,"od",x->x.eq('od.orderId','123');==>sql:xxx join tb_order_detail od on od.order_id = ?;Parameters:123 |
| S leftJoin(Class<?> tbClass,String alias, Consumer<C> consumer) | left join 操作，参考 `join` | - |
| S rightJoin(Class<?> tbClass,String alias, Consumer<C> consumer) | right join 操作，参考 `join` | - |
| S innerJoin(Class<?> tbClass,String alias, Consumer<C> consumer) | inner join 操作，参考 `join`  | - |
| S where(Consumer<C> consumer) | 条件筛选，消费SQL包装器初始化的那个条件构造器  | - |
> 示例：
```java

    //构建一个简单的属性查询包装器
    QueryWrapper<PropertyConditionWrapper> queryWrapper = SqlWrapperFactory.prop4Query();
    
    //选择 TbOrderForm类对应的表，查询别名 使用默认值 e
    queryWrapper.from(TbOrderForm.class);
    
    // where e.pay_state >= 1
    queryWrapper.where(w->w
        .ge("e.payState",1)
    );

    int paidOrderCount = staticBoundMapper.selectCount(queryWrapper);
```
> sql打印：
```mysql
select count(1) from `mybatis-helper-demo`.`tb_order_form` e where e.`pay_state` >= ?
```
> _[SqlWrapperFactory](#SqlWrapperFactory) 类生产了不同的包装器，可以根据具体情况采用不同的包装器生成器来执行sql操作_

## ISelectorWrapper
> `ISelectorWrapper<C>` 是一个模板查询包装接口，包含查询列、分页等操作；默认实现类 `SelectWrapper`，继承 [IOrder](#IOrder),[IPager](#IPager)

| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| Collection<SelectInfo> selects() | 获取主动查询的所有列信息 | - |
| S select(SelectInfo... fields) | 查询 [SelectInfo](#SelectInfo)类所描述对应的列或者信息 | select(SelectInfo.withField("e.orderNo"))==>sql:select e.order_no; |
| S select(String fields) | 根据表别名和dto属性查询对应的列 | select("e.id,e.orderNo")==>sql:select e.id,e.order_no; |
| S selectMain(boolean selectMain) | 是否强制查询主表的所有列，true的话强制查询，可以覆盖之前设置的值。 | - |
| S lock(boolean lock) | 锁操作（暂未更新） | - |
| <N extends S> N back() | 返回继承SelectWrapper的对象 | Obj obj = selectWrapper.back(); |

> 示例：
```java
        //构建普通查询包装器
        SelectWrapper<PropertyConditionWrapper> selectWrapper = SqlWrapperFactory.prop4Select();

        //选择 TbOrderForm类对应的表，查询别名为of（）
        selectWrapper.from(TbOrderForm.class,"of");

        //select of.id,of.member_id,of.order_no from `mybatis-helper-demo`.`tb_order_form`;
        //在类TbOrderForm中，属性id、memberId、orderNo分别对应字段 id、member_id、order_no（类的属性注解@Column）
        selectWrapper.select("of.id,of.memberId,of.orderNo");

        //使用原生sql查询(假设存在 tb_order_comment 表)
        selectWrapper.select(SelectInfo.withOriginal("(select count(1)from tb_order_comment where order_id=of.id) as commentCounts"));

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

> ## IOrder  
    
| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| S orderBy(SortInfo... items) | 构建以排序对象[SortInfo](#SortInfo)的排序项 | orderBy(SortInfo.withField("e.orderNo desc"))==>order by e.order_no desc; |
| S orderBy(String strings) | 以","分隔的查询对象窜，每个对象均为SortInfo.withField(splitStr) | orderBy("e.id asc,e.orderNo desc,e.memberId")==>sql:order by e.id asc,e.order_no desc,e.member_id; |

- ### SortInfo
    > 排序项，排序方式大小写敏感，asc请传“asc”，否则传“desc”

| 构造方法 | 描述 | 示例 |
| :-----| ----: | :----: |
| SortInfo withField(String fullInfo) | 构建以FieldItem为值的对象，可以自定义排序方式 | withField("e.id desc")==>sql:order by e.id desc; |
| SortInfo withField(String fieldWithAlias, Order order) | 构建以FieldItem为值的对象，可以自定义排序方式 | withField("e.orderNo",Order.DESC)==>sql:order by e.order_no desc; |
| SortInfo withOriginal(String fullInfo) | 构建以ValueItem为值的对象，sql注入 | withOriginal("min(id) desc")==>sql:order by min(id) desc;|


> ## IPager

| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| Page getPage() | 获得分页[Page](#page)对象 | - |
| S page(Page page) | 分页查询 | page(new PageInfo.valueOf(2,10))==>sql:select xxx limit 20,10; |

- ### Page
    > 分页接口。

| 方法 | 描述 | 示例 |
| :-----| ----: | :----: |
| int getPageSize() | 每页多少条信息 | - |
| int getPageIndex() | 当前第几页，从1开始 | - |

- ### PageList
    > 分页查询返回的对象

| 属性 | 描述 | 示例 |
| :-----| ----: | :----: |
| int count | 总记录 | - |
| List<T> list | 数据列表 | - |

- ### SelectInfo
    > 

| 构造方法 | 描述 | 示例 |
| :-----| ----: | :----: |
| SelectInfo withField(String fullInfo) | 构建以FieldItem为值的对象，可以自定义别名 | withField("e.id orderId")==>sql:select e.id orderId; |
| SelectInfo withField(String alias, String fieldName, String columnAlias) | 构建以FieldItem为值的对象，可以自定义别名 | withField("e","orderNo","ono")==>sql:select e.order_no `ono`; |
| SelectInfo withOriginal(String fullInfo) | 构建以ValueItem为值的对象，sql注入 | withOriginal("max(member_id) mid")==>sql:select max(member_id) mid;|

## IUpdateWrapper
> `IUpdateWrapper<C>` 是一个根据条件更新部分字段的sql包装器，默认实现为 `UpdateWrapper`

| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| S set(UpdateInfo... items) | 更新哪些字段，[UpdateInfo](#UpdateInfo) 可以根据不同情况生成不同的修改sql | set(UpdateInfo.withFieldField("e.orderNo","e.memberId") ==>sql:set e.order_id = e.member_id; |
| S set(String fieldWithAlias, Object value) | 更新对应dto属性对应的数据列，value为传参形式，无sql注入 | set("e.orderNo","12345678") ==>sql:set e.order_no=?;Parameters:12345678(String); |
> 示例：
```java

//        //创建一个根据条件修改的修改包装器
        UpdateWrapper<PropertyConditionWrapper> updateWrapper = SqlWrapperFactory.prop4Update();

        //update from tb_order_form
        updateWrapper
                .from(TbOrderForm.class,"of")

                //1，根据属性匹配列更新，参数化：（of.`pay_state` = ?）
                .set("of.payState",1)
                //2，构造一个UpdateInfo更新字段
                .set(UpdateInfo.withField("of.dealStatus",ValueItem.valueOf("1")))

                // where of.id = 3
                .where(w->w.eq("of.id",3))
        ;
        effects = staticBoundMapper.updateSelectItem(updateWrapper);
```
> sql打印：
```mysql
2020-7-4 13:56:18 DEBUG Fetching JDBC Connection from DataSource [org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection,114] 
2020-7-4 13:56:18 DEBUG JDBC Connection [HikariProxyConnection@2142455485 wrapping com.mysql.cj.jdbc.ConnectionImpl@1d948b6e] will not be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-7-4 13:56:18 DEBUG ==>  Preparing: update `mybatis-helper-demo`.`tb_order_form` of set of.`pay_state` = ?,of.`deal_status` = 1 where of.`id` = ?  [com.mybatishelper.demo.common.mapper.StaticBoundMapper.updateSelectItem.debug,159] 
2020-7-4 13:56:18 DEBUG ==> Parameters: 1(Integer), 3(Integer) [com.mybatishelper.demo.common.mapper.StaticBoundMapper.updateSelectItem.debug,159] 
2020-7-4 13:56:18 DEBUG <==    Updates: 1 [com.mybatishelper.demo.common.mapper.StaticBoundMapper.updateSelectItem.debug,159] 

```

- ### UpdateInfo
    > 该对象根据不同参数构造了不同处理方式的更新字段方式

| 构造方法 | 描述 | 示例 |
| :-----| ----: | :----: |
| UpdateInfo<String> withFieldField(String fieldWithAlias0, String fieldWithAlias1) | 赋值操作符两边均为dto属性对应的列 | withFieldField("orderNo","memberId")==>sql:order_no=member_id; |
| <T> UpdateInfo<T> withFieldValue(String fieldWithAlias0, T value) | 赋值操作符左边为dto属性对应列，右边为原值（sql注入） | withFieldValue("orderNo","8888")==>sql:order_no=8888; |
| <T> UpdateInfo<T> withFieldParam(String fieldWithAlias0, T value) | 赋值操作符左边为dto属性对应列，右边为参数化传值 | withFieldParam("orderNo","9999")==>sql:order_no=?;Parameters:9999(String); |
| UpdateInfo<String> withColumnField(String columnWithAlias, String fieldWithAlias) | 赋值操作符左边为列名，右边为属性对应的列 | withColumnField("order_no","memberId")==>sql:order_no=member_id; |
| <T> UpdateInfo<T> withColumnValue(String columnWithAlias, T value) | 赋值操作符左边为列名，右边为原值（sql注入） | withColumnValue("order_no","888888")==>sql:order_no=888888; |
| <T> UpdateInfo<T> withColumnParam(String columnWithAlias, T value) | 赋值操作符左边为列名，右边为参数化传值 | withColumnParam("order_no","999")==>sql:order_no=?;Parameters:999(String); |

## IDeleteWrapper
> `IDeleteWrapper<C>` 是一个根据条件删除数据的sql包装器，默认实现为 `DeleteWrapper`，继承[IQueryWrapper](#IQueryWrapper),

| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| S delete(String alias) | 删除那个表对应的别名 | - |
> 示例：
```java

        DeleteWrapper<PropertyConditionWrapper> deleteWrapper = SqlWrapperFactory.prop4Delete();
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

## SqlWrapperFactory
> `SqlWrapperFactory` 是一个生产不同sql操作场合下各自所需的SQL包装器工厂。其中 模板参数N分别继承相应的方法对应参数

| 方法名称 | 描述 | 示例 |
| :-----| ----: | :----: |
| N prop4Query() | 产生一个以属性条件构造器的查询类sql包装器`QueryWrapper<PropertyConditionWrapper>` | 常用作selectCount、selectExist等方法；基础查询 |
| N flex4Query() | 产生一个以弹性条件构造器的查询类sql包装器`QueryWrapper<FlexibleConditionWrapper>` | 同上 |
| N prop4Select() | 产生一个以属性条件构造器的查询类sql包装器`SelectWrapper<PropertyConditionWrapper>` | 常用作selectList、selectOne、selectPageList等方法的参数 |
| N flex4Select() | 产生一个以弹性条件构造器的查询类sql包装器`SelectWrapper<FlexibleConditionWrapper>` | 同上 |
| N prop4Update() | 产生一个以属性条件构造器的修改类sql包装器`UpdateWrapper<PropertyConditionWrapper>` | 供updateSelectItem使用 |
| N flex4Update() | 产生一个以弹性条件构造器的修改类sql包装器`UpdateWrapper<FlexibleConditionWrapper>` | 同上 |
| N prop4Delete() | 产生一个以属性条件构造器的删除类sql包装器`DeleteWrapper<PropertyConditionWrapper>` | 供delete使用 |
| N flex4Delete() | 产生一个以弹性条件构造器的删除类sql包装器`DeleteWrapper<FlexibleConditionWrapper>` | 同上 |