package com.mybatishelper.demo.order.controller;

import com.mybatishelper.core.base.Page;
import com.mybatishelper.core.base.meta.InsertInfo;
import com.mybatishelper.core.base.meta.SelectInfo;
import com.mybatishelper.core.base.meta.SimplePrimary;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.enums.ConditionType;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.StringUtility;
import com.mybatishelper.core.util.StringUtils;
import com.mybatishelper.core.wrapper.factory.SqlWrapperFactory;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.insert.InsertWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.core.wrapper.update.UpdateWrapper;
import com.mybatishelper.demo.common.module.Result;
import com.mybatishelper.demo.common.util.PageInfo;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public Result list(long memberId){
        SelectWrapper<PropertyConditionWrapper> defaultSelectWrapper = SqlWrapperFactory.prop4Select()
                .select("id")
                .back();
        List<TbOrderForm> orderList = orderFormService.selectList(defaultSelectWrapper.where(w -> w.eq("memberId", memberId)));
        return Result.success(orderList);
    }

    /**
     * 根据条件查询订单
     */
    @GetMapping(value = "query")
    public Result<List<TbOrderForm>> query(Long memberId){
        SelectWrapper<PropertyConditionWrapper> defaultSelectWrapper = SqlWrapperFactory.prop4Select();
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

    /**
     * 根据订单id删除某条记录
     */
    @DeleteMapping("remove/{orderId}")
    public Result<Boolean> remove(@PathVariable long orderId){
        int effects = orderFormService.deleteByPrimaryKey(SimplePrimary.valueOf(orderId));
        return Result.success(effects > 0);
    }
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
    /**
     * 将某条订单的状态改成已支付（假设 1为已支付，0为未支付）
     */
    @PutMapping("i-pay/{orderId}")
    public Result<Boolean> payWithIdempotent(@PathVariable long orderId){
        UpdateWrapper<PropertyConditionWrapper> upOrderWrapper = SqlWrapperFactory.prop4Update();
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

    /**
     * 根据条件创建订单
     * 这样插入是有危险的，务必确定插入的是一条而不是多条，因此pageInfo是必要的。
     */
    @PostMapping("createIfSent/{memberId}")
    public Result<Integer> createIfSent(@PathVariable Long memberId){
        String orderNo = String.valueOf(new Random().nextLong());
        InsertWrapper<PropertyConditionWrapper> insertWrapper = SqlWrapperFactory.prop4Insert()
                .insertInto(TbOrderForm.class)
                .fieldWithValue("memberId",memberId)
                .fieldWithValue("orderNo",orderNo)
                .from(TbOrderForm.class)
                .where(w->w.f().where(ConditionType.DO_NOTHING, ValueItem.valueOf(" NOT EXISTS(select 1 from tb_order_form x where x.order_no = '"+orderNo+"')"), Collections.emptyList()))
                .page(PageInfo.valueOf(0,1))
                ;
        int insertCount = orderFormService.insertSelectItem(insertWrapper);
        return Result.success(insertCount);
    }

    /**
     * 复制某个订单给另外一个用户，只复制 产品总额
     */
    @PostMapping("copy/{sourceOrderNo}/{memberId}")
    public Result<Integer> copyOrder(@PathVariable Long sourceOrderNo,@PathVariable Long memberId){
        String orderNo = String.valueOf(new Random().nextLong());
        InsertWrapper<PropertyConditionWrapper> insertWrapper = SqlWrapperFactory.prop4Insert()
                .insertInto(TbOrderForm.class)
                //复制来源订单的金额
                .select(InsertInfo.withFieldField("proAmount","source.proAmount"))
                //复制到哪个用户
                .fieldWithValue("memberId",memberId)
                //新订单号
                .fieldWithValue("orderNo",orderNo)
                //订单来源
                .from(TbOrderForm.class,"source")//
                //订单来源 -> 条件
                .where(w->w.eq("source.orderNo",sourceOrderNo+""))
                //判断订单号是否重复
                .where(w->w.f().where(ConditionType.DO_NOTHING, ValueItem.valueOf(" NOT EXISTS(select 1 from tb_order_form x where x.order_no = '"+orderNo+"')"), Collections.emptyList()))
                //直插入一条
                .page(PageInfo.valueOf(0,1))
                ;
        int insertCount = orderFormService.insertSelectItem(insertWrapper);
        return Result.success(insertCount);
    }


    /**
     * 创建多个用户的订单：batch insert
     * @param memberIds
     * @return
     */
    @PostMapping("create")
    public Result<Integer> create(String memberIds){
        if(StringUtils.isEmpty(memberIds)){
            return Result.miss("302","参数不符合要求",null);
        }
        String[] memIds = memberIds.split(",");
        List<TbOrderForm> orderForms = new ArrayList<>(memIds.length<<1);
        for(String memId:memIds){
            TbOrderForm orderForm = new TbOrderForm();
            orderForm.setMemberId(Long.valueOf(memId));
            orderForm.setOrderNo(String.valueOf(new Random().nextLong()));
            orderForm.setDeltag(Boolean.FALSE);
            orderForms.add(orderForm);
        }
        return Result.success(orderFormService.batchInsert(orderForms));
    }

    /**
     * 创建多个用户的订单：只插入部分字段
     * @param memberIds
     * @return
     */
    @PostMapping("creative")
    public Result<Integer> creative(String memberIds){
        if(StringUtils.isEmpty(memberIds)){
            return Result.miss("302","参数不符合要求",null);
        }
        String[] memIds = memberIds.split(",");
        List<TbOrderForm> orderForms = new ArrayList<>(memIds.length<<1);
        for(String memId:memIds){
            TbOrderForm orderForm = new TbOrderForm();
            orderForm.setMemberId(Long.valueOf(memId));
            orderForm.setOrderNo(String.valueOf(new Random().nextLong()));
            orderForms.add(orderForm);
        }
        return Result.success(orderFormService.batchInsertSelective(orderForms,Arrays.asList("orderNo","memberId")));
    }

}
