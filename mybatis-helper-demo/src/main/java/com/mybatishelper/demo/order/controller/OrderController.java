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
