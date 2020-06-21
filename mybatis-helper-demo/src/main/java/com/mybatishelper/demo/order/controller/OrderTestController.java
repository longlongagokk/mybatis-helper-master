package com.mybatishelper.demo.order.controller;

import com.mybatishelper.core.base.field.OrderField;
import com.mybatishelper.core.base.field.SelectField;
import com.mybatishelper.core.base.meta.FieldWithValue;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.enums.Order;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.demo.common.mapper.StaticBoundMapper;
import com.mybatishelper.demo.common.module.Result;
import com.mybatishelper.demo.common.util.PageInfo;
import com.mybatishelper.demo.order.module.entity.TbOrderDetail;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.module.query.TsOrderForm;
import com.mybatishelper.demo.order.service.OrderDetailService;
import com.mybatishelper.demo.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("order-test")
@Slf4j
public class OrderTestController {

    final
    OrderFormService orderFormService;
    final
    OrderDetailService orderDetailService;

    public OrderTestController(OrderFormService orderFormService, OrderDetailService orderDetailService, StaticBoundMapper staticBoundMapper) {
        this.orderFormService = orderFormService;
        this.orderDetailService = orderDetailService;
        this.staticBoundMapper = staticBoundMapper;
    }

    @GetMapping(value = "list")
    public Result list(HttpServletRequest request, HttpServletResponse response, BigDecimal amountPaid)throws Exception{
        Collection<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        Result.success(orderFormService.selectList(SelectWrapper.build()
                .select(("e.memberId,e.memberId userInfo"))
                .leftJoin(TbOrderDetail.class,"od", od->
                        od
                                .eq(FieldWithValue.valueOf("e.id", FieldItem.valueOf("od.orderId")))
                                .eq("od.productId",300L)
                        )
                .where(w->
                        w
                                .eq(FieldWithValue.valueOf("e.orderNo",FieldItem.valueOf("e.orderTypeStr")))
                                .eq("e.amountPaid",amountPaid)
                        .or(x->
                                x
                                        .eq(FieldWithValue.valueOf("e.amountPay",FieldItem.valueOf("e.call")))
                                        .eq("e.deliveryId",234)
                                        .in("e.id",ids)
                                        .f()
                                        .between(FieldItem.valueOf("e.memberId"),
                                                FieldItem.valueOf("e", TsOrderForm.Fields.phone),
                                                ParamItem.valueOf("e.id")
                                        )
                                        .d()
                                        .between("e.receiver",
                                                3l,
                                                19l
                                        )
                                )
                        )
                        .orderBy(OrderField.valueOf("e.payWayId")
                        )
                        .page(PageInfo.valueOf(1,4))
                )
        );
        return Result.success(orderFormService.selectPageListV(SelectWrapper.build()
                        .select(("e.memberId,e.memberId userInfo"))
                        .leftJoin(TbOrderDetail.class,"od",od->od
                                        .eq(FieldWithValue.valueOf("e.id", FieldItem.valueOf("od.orderId")))
                                        .eq("od.productId",300L)
                        )
                        .orderBy(OrderField.valueOf("e.payWayId")
                        )
                        .page(PageInfo.valueOf(1,4))
                )
        );
    }
    @GetMapping(value = "detail-list")
    public Result detailList(HttpServletRequest request, HttpServletResponse response)throws Exception{
        return Result.success(orderDetailService.selectPageList(SelectWrapper.build()
                .select("e.id,e.orderId orderId,e.id bid")
                .select(
                        SelectField.valueOf("e.id"),
                        SelectField.valueOf("e.id id0"),
                        SelectField.valueOf("e.order_id",true),
                        SelectField.valueOf("e.order_id kbs",true)
                )
        .where(x->x
                .le(FieldWithValue.withParamValue("e.createDate",new Date()))
                )
        .page(new PageInfo())
                .orderBy("e.id,e.orderId,of.memberId desc")
                .orderBy(
                        OrderField.valueOf("of.create_date",true)
                        ,
                        OrderField.valueOf("of.create_date asc",true)
                        ,
                        OrderField.valueOf("of.create_date desc",true)
                        ,
                        OrderField.valueOf("of.createDate",false)
                        ,
                        OrderField.valueOf("of.updateDate", Order.DESC)

                )
        .leftJoin(TbOrderForm.class,"of", of->
                of.eq(FieldWithValue.valueOf("e.orderId",FieldItem.valueOf("of.id")))
        )
        .leftJoin(TbOrderForm.class,"ff",of->
                of
                        .eq(FieldWithValue.valueOf("e.orderId",FieldItem.valueOf("ff.id")))
                .or(x->
                        x.ge(FieldWithValue.valueOf("e.orderId",ParamItem.valueOf("ff.id")))
                                .neq(FieldWithValue.valueOf("e.orderId", ValueItem.valueOf("ff.id")))
                        )
        )

                )
        );
    }
    @GetMapping(value = "detail/{id}")
    public Result list(@PathVariable long id,String ricefair,String ecommerce)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        log.info(ecommerce);

        return Result.success(orderFormService.selectOne(id));
    }
    private final StaticBoundMapper staticBoundMapper;
    @GetMapping(value = "add/{orderNo}/{memberId}")
    public Result list(@PathVariable String orderNo,@PathVariable Long memberId)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        TbOrderForm orderForm = new TbOrderForm();
        orderForm.setMemberId(memberId);
        orderForm.setOrderNo(orderNo);
        return Result.success(orderFormService.insert(orderForm));
    }



    @GetMapping(value = "create")
    public Result create()throws Exception{
        TbOrderForm order = new TbOrderForm();
        order.setOrderNo(new Date().getTime() + "S");
        //order.setId(125L);
        int result = orderFormService.insertSelective(order);
        System.out.println(result);
        return Result.success(order);

        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
//        trOrderMapper.selectList(SelectWrapper.DefaultSelectWrapper.build()
//                .from(TbOrderForm.class)
//                .select("e.id orderId,e.orderNo,e.memberId userName")
//                        .where(x->x.eq("e.id",100))
//        );
//        return Result.success(trOrderMapper.selectCount(QueryWrapper.build()
//                .from(TbOrderForm.class)
//                .from(TbOrderDetail.class,"od")
//                .where(x->x.eq("e.id",100))
//                .where(x->x.eq("od.id",330))
//                .where(x->x.eq(FieldWithValue.valueOf("od.orderId",FieldItem.valueOf("e.id"))))
//        ));
    }


}
