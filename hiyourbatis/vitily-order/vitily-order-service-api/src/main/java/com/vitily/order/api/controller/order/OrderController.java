package com.vitily.order.api.controller.order;

import club.yourbatis.hi.base.field.OrderField;
import club.yourbatis.hi.base.field.SelectField;
import club.yourbatis.hi.base.meta.FieldWithValue;
import club.yourbatis.hi.base.meta.PageList;
import com.vitily.common.util.PageInfo;
import club.yourbatis.hi.base.param.FieldItem;
import club.yourbatis.hi.base.param.ParamItem;
import club.yourbatis.hi.base.param.ValueItem;
import club.yourbatis.hi.enums.Order;
import club.yourbatis.hi.wrapper.query.SelectWrapper;
import com.vitily.common.mapper.StaticBoundMapper;
import com.vitily.common.module.Result;
import com.vitily.order.api.entity.TrOrder;
import com.vitily.order.api.mapper.TrOrderMapper;
import com.vitily.order.module.entity.TbOrderDetail;
import com.vitily.order.module.entity.TbOrderForm;
import com.vitily.order.module.query.TsOrderForm;
import com.vitily.order.service.OrderDetailService;
import com.vitily.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    OrderFormService orderFormService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    private TrOrderMapper<TrOrder> trOrderMapper;

    @GetMapping(value = "list")
    public Result list(HttpServletRequest request, HttpServletResponse response, BigDecimal amountPaid)throws Exception{
        Collection<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        Result.success(orderFormService.selectList(SelectWrapper.build()
                .select(("e.memberId,e.memberId userInfo"))
                .leftJoin(TbOrderDetail.class,"od",od->
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
                                                FieldItem.valueOf("e",TsOrderForm.Fields.phone),
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
                        OrderField.valueOf("of.updateDate",Order.DESC)

                )
        .leftJoin(TbOrderForm.class,"of",of->
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
    public Result list(@PathVariable long id)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        return Result.success(orderFormService.selectOne(id));
    }
    @Autowired
    private StaticBoundMapper staticBoundMapper;
    @GetMapping(value = "add/{orderNo}/{memberId}")
    public Result list(@PathVariable String orderNo,@PathVariable Long memberId)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        TbOrderForm orderForm = new TbOrderForm();
        orderForm.setMemberId(memberId);
        orderForm.setOrderNo(orderNo);
        return Result.success(orderFormService.insert(orderForm));
    }

    @GetMapping(value = "tr-list")
    public Result trList(Long beginId)throws Exception{
        PageList<TrOrder> pageList = trOrderMapper.selectPageList(SelectWrapper.build().from(TbOrderForm.class)
                .select("e.id orderId,e.orderNo orderNo").page(new PageInfo())
                .where(x -> x
                        .ge("e.id", beginId)
                )
        );
        return Result.success(pageList);

        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
//        trOrderMapper.selectList(SelectWrapper.DefaultSelectWrapper.build()
//                .from(TbOrderForm.class)
//                .select("e.id orderId,e.orderNo,e.memberId userName")
//                        .where(x->x.eq("e.id",100))
//        );
//        return Result.success(trOrderMapper.selectCount(CountWrapper.build()
//                .from(TbOrderForm.class)
//                .from(TbOrderDetail.class,"od")
//                .where(x->x.eq("e.id",100))
//                .where(x->x.eq("od.id",330))
//                .where(x->x.eq(FieldWithValue.valueOf("od.orderId",FieldItem.valueOf("e.id"))))
//        ));
    }

    @GetMapping(value = "info")
    public Result info(Long id)throws Exception{
        TrOrder order = trOrderMapper.selectOne(SelectWrapper.build()
                .from(TbOrderForm.class)
                .select("e.id orderId,e.orderNo")
                .where(w->w.eq("e.id",id))
        );
        return Result.success(order);

        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
//        trOrderMapper.selectList(SelectWrapper.DefaultSelectWrapper.build()
//                .from(TbOrderForm.class)
//                .select("e.id orderId,e.orderNo,e.memberId userName")
//                        .where(x->x.eq("e.id",100))
//        );
//        return Result.success(trOrderMapper.selectCount(CountWrapper.build()
//                .from(TbOrderForm.class)
//                .from(TbOrderDetail.class,"od")
//                .where(x->x.eq("e.id",100))
//                .where(x->x.eq("od.id",330))
//                .where(x->x.eq(FieldWithValue.valueOf("od.orderId",FieldItem.valueOf("e.id"))))
//        ));
    }
}
