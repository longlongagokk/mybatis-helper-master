package com.mybatishelper.demo.order.controller;

import com.google.common.primitives.UnsignedLong;
import com.mybatishelper.core.base.meta.SimplePrimary;
import com.mybatishelper.core.util.Assert;
import com.mybatishelper.core.util.CollectionUtils;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.core.wrapper.update.UpdateWrapper;
import com.mybatishelper.demo.common.module.Result;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        SelectWrapper.DefaultSelectWrapper defaultSelectWrapper = SelectWrapper.build().select("id").back();
        List<TbOrderForm> orderList = orderFormService.selectList(defaultSelectWrapper.where(w -> w.eq("memberId", memberId)));
        return Result.success(orderList);
    }

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
}
