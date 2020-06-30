# 使用示例
## 下载项目
>从[github](https://github.com/longlongagokk/mybatis-helper-master)下载项目,使用idea/eclipse打开。
## 引入资源
```xml
<dependency>
    <groupId>com.mybatishelper</groupId>
    <artifactId>mybatishelper</artifactId>
    <version>1.0.61</version>
</dependency>
```
## 依赖注入
```java
package com.mybatishelper.demo.order.config;

import com.mybatishelper.core.config.YourConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;

/**
 * creator : whh
 * date    : 2019/6/26 18:37
 * desc    :
 **/
@Configuration
@EnableAsync
@ComponentScan({"com.mybatishelper.demo.*.service"})
@MapperScan(basePackages = {"com.mybatishelper.demo.*.mapper"})
public class OrderConfiguration {
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        YourConfiguration conf = new YourConfiguration();
        conf.setUseGeneratedKeys(true);//使用自增长id
        sessionFactory.setConfiguration(conf);
        return sessionFactory.getObject();
    }
}
```
> _**\*** 当不使用mybatishelper内置分页的时候，可以简单注入，无需修改任何现有项目代码，例如：_
```java
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        return sessionFactory.getObject();
    }
```
## 简单使用

> mybatis-helper-demo/src/main/resources 下有一个简单粗糙的 mysql demo库，使用者可以建立一个名为 mybatis-helper-demo 的库然后执行该sql以便测试
```java

package com.mybatishelper.demo.order.controller;

import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.demo.common.module.Result;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    final OrderFormService orderFormService;
    public OrderController(OrderFormService orderFormService) {
        this.orderFormService = orderFormService;
    }

    /**
     * 查询某个会员的订单ID列表
     */
    @GetMapping(value = "list")
    public Result list(Long memberId)throws Exception{
        SelectWrapper.DefaultSelectWrapper defaultSelectWrapper = SelectWrapper.build().select("id").back();
        List<TbOrderForm> orderList = orderFormService.selectList(defaultSelectWrapper.where(w->w.eq("memberId",memberId)));
        return Result.success(orderList);
    }
}
```

> 启动 springboot项目
> 执行 `curl http://localhost:5678/order/list?memberId=888`
> idea 打印的sql如下：
````mysql
2020-6-30 11:02:03 DEBUG Fetching JDBC Connection from DataSource [org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection,114] 
2020-6-30 11:02:03 DEBUG JDBC Connection [HikariProxyConnection@1868548791 wrapping com.mysql.cj.jdbc.ConnectionImpl@7185b1e7] will not be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-6-30 11:02:03 DEBUG ==>  Preparing: select `id` `id` from `mybatis-helper-demo`.`tb_order_form` e where `member_id` = ?  [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
2020-6-30 11:02:03 DEBUG ==> Parameters: 888(Long) [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
2020-6-30 11:02:03 DEBUG <==      Total: 2 [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
````
> 返回json数据：
````json
{
	"code": "200",
	"message": "success",
	"content": [{
		"id": 3
	}, {
		"id": 7
	}]
}
````
>###### 增
```java
    /**
     * 为某个会员创建订单
     */
    @PostMapping("create/{memberId}")
    public Result<TbOrderForm> create(@PathVariable long memberId){
        TbOrderForm orderForm = new TbOrderForm();
        orderForm.setMemberId(memberId);
        orderForm.setOrderNo(String.valueOf(new Random().nextLong()));
        orderForm.setDeltag(Boolean.FALSE);
        //orderFormService.insert(orderForm);//不过滤null属性，全字段插入
        orderFormService.insertSelective(orderForm);//不插入null属性字段。
        return Result.success(orderForm);
    }
```
> 执行 `curl -X POST http://localhost:5678/order/create/865`
> idea 打印的sql 如下：
```mysql
2020-6-30 11:28:00 DEBUG Creating new transaction with name [com.mybatishelper.demo.order.service.impl.OrderFormServiceImpl.insertSelective]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT [org.springframework.jdbc.datasource.DataSourceTransactionManager.getTransaction,372] 
2020-6-30 11:28:00 DEBUG Acquired Connection [HikariProxyConnection@2131395411 wrapping com.mysql.cj.jdbc.ConnectionImpl@71a9f7c8] for JDBC transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,264] 
2020-6-30 11:28:00 DEBUG Switching JDBC Connection [HikariProxyConnection@2131395411 wrapping com.mysql.cj.jdbc.ConnectionImpl@71a9f7c8] to manual commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,281] 
2020-6-30 11:28:00 DEBUG JDBC Connection [HikariProxyConnection@2131395411 wrapping com.mysql.cj.jdbc.ConnectionImpl@71a9f7c8] will be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-6-30 11:28:00 DEBUG ==>  Preparing: insert into `mybatis-helper-demo`.`tb_order_form`(`deltag`,`member_id`,`order_no`) VALUES (?,?,?)  [com.mybatishelper.demo.common.mapper.StaticBoundMapper.insertSelective.debug,159] 
2020-6-30 11:28:00 DEBUG ==> Parameters: false(Boolean), 865(Long), -1309289267593266212(String) [com.mybatishelper.demo.common.mapper.StaticBoundMapper.insertSelective.debug,159] 
2020-6-30 11:28:00 DEBUG <==    Updates: 1 [com.mybatishelper.demo.common.mapper.StaticBoundMapper.insertSelective.debug,159] 
2020-6-30 11:28:00 DEBUG Initiating transaction commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.processCommit,743] 
2020-6-30 11:28:00 DEBUG Committing JDBC transaction on Connection [HikariProxyConnection@2131395411 wrapping com.mysql.cj.jdbc.ConnectionImpl@71a9f7c8] [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCommit,326] 
2020-6-30 11:28:00 DEBUG Releasing JDBC Connection [HikariProxyConnection@2131395411 wrapping com.mysql.cj.jdbc.ConnectionImpl@71a9f7c8] after transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCleanupAfterCompletion,384] 

```
>###### 删
```java

    /**
     * 根据订单id删除某条记录
     */
    @DeleteMapping("remove/{orderId}")
    public Result<Boolean> remove(@PathVariable long orderId){
        int delCount = orderFormService.deleteByPrimaryKey(SimplePrimary.valueOf(orderId));
        return Result.success(delCount > 0);
    }
```
> 执行 `curl -X DELETE http://localhost:5678/order/remove/8887`
> idea 打印的sql 如下：
```mysql
2020-6-30 11:37:49 DEBUG Creating new transaction with name [com.mybatishelper.demo.order.service.impl.OrderFormServiceImpl.deleteByPrimaryKey]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT [org.springframework.jdbc.datasource.DataSourceTransactionManager.getTransaction,372] 
2020-6-30 11:37:49 DEBUG Acquired Connection [HikariProxyConnection@640796324 wrapping com.mysql.cj.jdbc.ConnectionImpl@17f6f400] for JDBC transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,264] 
2020-6-30 11:37:49 DEBUG Switching JDBC Connection [HikariProxyConnection@640796324 wrapping com.mysql.cj.jdbc.ConnectionImpl@17f6f400] to manual commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,281] 
2020-6-30 11:37:49 DEBUG JDBC Connection [HikariProxyConnection@640796324 wrapping com.mysql.cj.jdbc.ConnectionImpl@17f6f400] will be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-6-30 11:37:49 DEBUG ==>  Preparing: delete from `mybatis-helper-demo`.`tb_order_form` where `id` = ?  [com.mybatishelper.demo.order.mapper.OrderFormMapper.deleteByPrimaryKey.debug,159] 
2020-6-30 11:37:49 DEBUG ==> Parameters: 8887(Long) [com.mybatishelper.demo.order.mapper.OrderFormMapper.deleteByPrimaryKey.debug,159] 
2020-6-30 11:37:49 DEBUG <==    Updates: 0 [com.mybatishelper.demo.order.mapper.OrderFormMapper.deleteByPrimaryKey.debug,159] 
2020-6-30 11:37:49 DEBUG Initiating transaction commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.processCommit,743] 
2020-6-30 11:37:49 DEBUG Committing JDBC transaction on Connection [HikariProxyConnection@640796324 wrapping com.mysql.cj.jdbc.ConnectionImpl@17f6f400] [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCommit,326] 
2020-6-30 11:37:49 DEBUG Releasing JDBC Connection [HikariProxyConnection@640796324 wrapping com.mysql.cj.jdbc.ConnectionImpl@17f6f400] after transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCleanupAfterCompletion,384] 

```
>###### 改

> 非幂等性更改：不判断状态
```java

    /**
     * 将某条订单的状态改成已支付（假设 1为已支付，0为未支付）
     */
    @PatchMapping("ni-pay/{orderId}")
    public Result<Boolean> payWithNonIdempotent(@PathVariable long orderId){
        TbOrderForm upOrder = new TbOrderForm();
        upOrder.setId(orderId);
        upOrder.setUpdateDate(new Date());
        upOrder.setPayState(1);//1表示已支付
        //orderFormService.updateByPrimary(upOrder);//该方法会更新所有字段，一般不使用，误用可能使得将已有字段的值设置为null
        int effects = orderFormService.updateSelectiveByPrimaryKey(upOrder);
        return Result.success(effects > 0);
    }
```
> 执行 `curl -X PATCH http://localhost:5678/order/ni-pay/2`
> idea 打印的sql 如下：
```mysql
2020-6-30 11:56:45 DEBUG Creating new transaction with name [com.mybatishelper.demo.order.service.impl.OrderFormServiceImpl.updateSelectiveByPrimaryKey]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT [org.springframework.jdbc.datasource.DataSourceTransactionManager.getTransaction,372] 
2020-6-30 11:56:45 DEBUG Acquired Connection [HikariProxyConnection@1072784595 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] for JDBC transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,264] 
2020-6-30 11:56:45 DEBUG Switching JDBC Connection [HikariProxyConnection@1072784595 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] to manual commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,281] 
2020-6-30 11:56:45 DEBUG JDBC Connection [HikariProxyConnection@1072784595 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] will be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-6-30 11:56:45 DEBUG ==>  Preparing: update `mybatis-helper-demo`.`tb_order_form` set `update_date` = ?,`pay_state` = ? where `id` = ?  [com.mybatishelper.demo.common.mapper.StaticBoundMapper.updateSelectiveByPrimaryKey.debug,159] 
2020-6-30 11:56:45 DEBUG ==> Parameters: 2020-06-30 11:56:45.37(Timestamp), 1(Integer), 2(Long) [com.mybatishelper.demo.common.mapper.StaticBoundMapper.updateSelectiveByPrimaryKey.debug,159] 
2020-6-30 11:56:45 DEBUG <==    Updates: 1 [com.mybatishelper.demo.common.mapper.StaticBoundMapper.updateSelectiveByPrimaryKey.debug,159] 
2020-6-30 11:56:45 DEBUG Initiating transaction commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.processCommit,743] 
2020-6-30 11:56:45 DEBUG Committing JDBC transaction on Connection [HikariProxyConnection@1072784595 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCommit,326] 
2020-6-30 11:56:45 DEBUG Releasing JDBC Connection [HikariProxyConnection@1072784595 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] after transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCleanupAfterCompletion,384] 

```
> 幂等性更改：判断状态
```java

    /**
     * 将某条订单的状态改成已支付（假设 1为已支付，0为未支付）
     */
    @PutMapping("i-pay/{orderId}")
    public Result<Boolean> payWithIdempotent(@PathVariable long orderId){
        UpdateWrapper<PropertyConditionWrapper> upOrderWrapper = UpdateWrapper.build();
        upOrderWrapper
                .set("payState",1)
                .set("updateDate",new Date())
                .where(w->w
                    .eq("e.id",orderId)
                    .eq("payState",0)
                );
        int effects = orderFormService.updateSelectItem(upOrderWrapper);
        return Result.success(effects > 0);
    }
```
> 执行 `curl -X PUT http://localhost:5678/order/i-pay/2`
> idea 打印的sql 如下：
```mysql
2020-6-30 11:59:56 DEBUG Creating new transaction with name [com.mybatishelper.demo.order.service.impl.OrderFormServiceImpl.updateSelectItem]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT [org.springframework.jdbc.datasource.DataSourceTransactionManager.getTransaction,372] 
2020-6-30 11:59:56 DEBUG Acquired Connection [HikariProxyConnection@2114375760 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] for JDBC transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,264] 
2020-6-30 11:59:56 DEBUG Switching JDBC Connection [HikariProxyConnection@2114375760 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] to manual commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin,281] 
2020-6-30 11:59:56 DEBUG JDBC Connection [HikariProxyConnection@2114375760 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] will be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-6-30 11:59:56 DEBUG ==>  Preparing: update `mybatis-helper-demo`.`tb_order_form` e set `update_date` = ?,`pay_state` = ? where e.`id` = ? AND `pay_state` = ?  [com.mybatishelper.demo.order.mapper.OrderFormMapper.updateSelectItem.debug,159] 
2020-6-30 11:59:56 DEBUG ==> Parameters: 2020-06-30 11:59:56.591(Timestamp), 1(Integer), 2(Long), 0(Integer) [com.mybatishelper.demo.order.mapper.OrderFormMapper.updateSelectItem.debug,159] 
2020-6-30 11:59:56 DEBUG <==    Updates: 0 [com.mybatishelper.demo.order.mapper.OrderFormMapper.updateSelectItem.debug,159] 
2020-6-30 11:59:56 DEBUG Initiating transaction commit [org.springframework.jdbc.datasource.DataSourceTransactionManager.processCommit,743] 
2020-6-30 11:59:56 DEBUG Committing JDBC transaction on Connection [HikariProxyConnection@2114375760 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCommit,326] 
2020-6-30 11:59:56 DEBUG Releasing JDBC Connection [HikariProxyConnection@2114375760 wrapping com.mysql.cj.jdbc.ConnectionImpl@7dc9c986] after transaction [org.springframework.jdbc.datasource.DataSourceTransactionManager.doCleanupAfterCompletion,384] 

```
>###### 查
```java
    /**
     * 根据条件查询订单
     */
    @GetMapping(value = "query")
    public Result<List<TbOrderForm>> query(Long memberId){
        SelectWrapper.DefaultSelectWrapper defaultSelectWrapper = SelectWrapper.build();
        Assert.notNull(memberId,"会员id不能为空");
        ArrayList<Integer> payStates = new ArrayList<>();
        payStates.add(0);
        payStates.add(1);
        defaultSelectWrapper.where(w->w
                .eq("e.memberId",memberId)
                .in("e.payState",payStates)
                );
        List<TbOrderForm> orderList = orderFormService.selectList(defaultSelectWrapper);
        return Result.success(orderList);
    }
```
> 执行 `curl  http://localhost:5678/order/query?memberId=888`
> idea 打印的sql 如下：
```mysql
2020-6-30 14:11:55 DEBUG Fetching JDBC Connection from DataSource [org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection,114] 
2020-6-30 14:11:55 DEBUG JDBC Connection [HikariProxyConnection@2112503148 wrapping com.mysql.cj.jdbc.ConnectionImpl@15e30d54] will not be managed by Spring [org.mybatis.spring.transaction.SpringManagedTransaction.openConnection,87] 
2020-6-30 14:11:55 DEBUG ==>  Preparing: select e.`pay_way_name` `payWayName`,e.`update_date` `updateDate`,e.`send_date` `sendDate`,e.`vote_company` `voteCompany`,e.`deltag` `deltag`,e.`pay_state` `payState`,e.`pro_amount` `proAmount`,e.`pay_way_id` `payWayId`,e.`delivery_id` `deliveryId`,e.`amount_paid` `amountPaid`,e.`leave_message` `leaveMessage`,e.`order_type_str` `orderTypeStr`,e.`id` `id`,e.`email` `email`,e.`member_id` `memberId`,e.`create_date` `createDate`,e.`area` `area`,e.`order_no` `orderNo`,e.`address` `address`,e.`receiver` `receiver`,e.`vote_content` `voteContent`,e.`vote_title` `voteTitle`,e.`deal_status` `dealStatus`,e.`user_name` `userName`,e.`call` `call`,e.`delivery_way_name` `deliveryWayName`,e.`phone` `phone`,e.`post_code` `postCode`,e.`amount_pay` `amountPay`,e.`order_date` `orderDate`,e.`delivery_status` `deliveryStatus`,e.`delivery_cost` `deliveryCost` from `mybatis-helper-demo`.`tb_order_form` e where e.`member_id` = ? AND e.`pay_state` IN ( ?,? )  [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
2020-6-30 14:11:55 DEBUG ==> Parameters: 888(Long), 0(Integer), 1(Integer) [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 
2020-6-30 14:11:55 DEBUG <==      Total: 0 [com.mybatishelper.demo.order.mapper.OrderFormMapper.selectList.debug,159] 

```