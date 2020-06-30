package com.mybatishelper.demo.order.controller;

import com.mybatishelper.core.base.FieldValue;
import com.mybatishelper.core.base.field.OrderField;
import com.mybatishelper.core.base.field.SelectField;
import com.mybatishelper.core.base.meta.FieldWithValue;
import com.mybatishelper.core.base.meta.PageList;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.enums.Order;
import com.mybatishelper.core.wrapper.IDeleteWrapper;
import com.mybatishelper.core.wrapper.IQueryWrapper;
import com.mybatishelper.core.wrapper.ISelectorWrapper;
import com.mybatishelper.core.wrapper.delete.DeleteWrapper;
import com.mybatishelper.core.wrapper.factory.FlexibleConditionWrapper;
import com.mybatishelper.core.wrapper.factory.PropertyConditionWrapper;
import com.mybatishelper.core.wrapper.query.QueryWrapper;
import com.mybatishelper.core.wrapper.query.SelectWrapper;
import com.mybatishelper.core.wrapper.update.UpdateWrapper;
import com.mybatishelper.demo.common.mapper.StaticBoundMapper;
import com.mybatishelper.demo.common.module.Result;
import com.mybatishelper.demo.common.module.TvPageList;
import com.mybatishelper.demo.common.util.PageInfo;
import com.mybatishelper.demo.order.module.entity.TbOrderDetail;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.module.query.TsOrderForm;
import com.mybatishelper.demo.order.module.view.TvOrderForm;
import com.mybatishelper.demo.order.service.OrderDetailService;
import com.mybatishelper.demo.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("order-test")
@Slf4j
public class OrderTestController {

    final
    OrderFormService orderFormService;
    final
    OrderDetailService orderDetailService;
    @Autowired
    private StaticBoundMapper staticBoundMapper;

    public OrderTestController(OrderFormService orderFormService, OrderDetailService orderDetailService) {
        this.orderFormService = orderFormService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping(value = "list")
    public Result list(HttpServletRequest request, HttpServletResponse response, BigDecimal amountPaid)throws Exception{

//        //构建一个简单的查询包装器
//        QueryWrapper<PropertyConditionWrapper> queryWrapper = QueryWrapper.build();
//
//        //选择 TbOrderForm类对应的表，查询别名 使用默认值 e
//        queryWrapper.from(TbOrderForm.class);
//
//        // where e.pay_state > 1
//        queryWrapper.where(w->w
//            .ge("e.payState",1)
//        );
//
//        int paidOrderCount = staticBoundMapper.selectCount(queryWrapper);
//        if(true){
//            return Result.success(paidOrderCount);
//        }

//        //构建查询包装器
//        SelectWrapper<PropertyConditionWrapper> selectWrapper = SelectWrapper.build();
//
//        //选择 TbOrderForm类对应的表，查询别名为of（）
//        selectWrapper.from(TbOrderForm.class,"of");
//
//        //select of.id,of.member_id,of.order_no from `mybatis-helper-demo`.`tb_order_form`;
//        //在类TbOrderForm中，属性id、memberId、orderNo分别对应字段 id、member_id、order_no（类的属性注解@Column）
//        selectWrapper.select("of.id,of.memberId,of.orderNo");
//
//        //使用原生sql查询(假设存在 tb_order_comment 表)
//        selectWrapper.select(SelectField.valueOf("(select count(1)from tb_order_comment where order_id=of.id) as commentCounts",true));
//
//        // where of.pay_state = 1
//        selectWrapper.where(w->w
//            .eq("of.payState",1)
//        );
//
//        // limit 0,10
//        selectWrapper.page(PageInfo.valueOf(1,10));
//
//        //执行查询
//        PageList<TvOrderForm> orders = orderFormService.selectPageListV(selectWrapper);
//
//        if(true){
//            return Result.success(orders);
//        }

//        //创建一个根据条件修改的修改包装器
//        UpdateWrapper<PropertyConditionWrapper> updateWrapper = UpdateWrapper.build();
//
//        //update from tb_order_form
//        updateWrapper
//                .from(TbOrderForm.class,"of")
//
//                //1，根据属性匹配列更新，参数化：（of.`pay_state` = ?）
//                .set("of.payState",1)
//
//                //2，值为原生sql，非参数化：（of.`deal_status` = of.pay_state）（*有sql注入风险）
//                .set(FieldWithValue.withOriginalValue("of.dealStatus","of.pay_state"))
//
//                //3，同1，参数化：（of.`leave_message` = ?）
//                .set(FieldWithValue.withParamValue("of.leaveMessage","微辣"))
//
//                //4，同2，非参数化：（of.`delivery_status` = 4）（*有sql注入风险）
//                .set(FieldWithValue.valueOf("of.deliveryStatus",ValueItem.valueOf(4)))
//
//                //5,同1，参数化：（of.`leave_message` = ?）
//                .set(FieldWithValue.valueOf("of.voteTitle",ParamItem.valueOf("大梅沙")))
//
//                //6,//根据属性匹配列之间传值，（of.`vote_content` = of.`vote_title`）
//                .set(FieldWithValue.valueOf("of.voteContent",FieldItem.valueOf("of.voteTitle")))
//
//                // where of.id = 3
//                .where(w->w.eq("of.id",3))
//        ;
//        int effects = staticBoundMapper.updateSelectItem(updateWrapper);
//        if(true){
//            return Result.success(effects);
//        }
        Consumer<FlexibleConditionWrapper> lc = f->
                f.eq(FieldItem.valueOf("of.id"),FieldItem.valueOf("od.orderId"))
                ;
        Consumer<FlexibleConditionWrapper> c = w->
                w.gt(FieldItem.valueOf("od.proPrice"),ValueItem.valueOf(9.9))
                ;
        SelectWrapper<FlexibleConditionWrapper>
                selectWrapper = new SelectWrapper<>(new FlexibleConditionWrapper())
                .select("od.orderId,of.dealStatus")
                .from(TbOrderDetail.class,"od")
                .leftJoin(TbOrderForm.class,"of",lc)
                .where(c)
                ;
        List effects = orderDetailService.selectList(selectWrapper);
        if(true){
            return Result.success(effects);
        }


























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
    @GetMapping(value = "add/{orderNo}/{memberId}")
    public Result add(@PathVariable String orderNo,@PathVariable Long memberId)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        TbOrderForm orderForm = new TbOrderForm();
        orderForm.setMemberId(memberId);
        orderForm.setOrderNo(orderNo);
        staticBoundMapper.insertSelective(orderForm);
        return Result.success(orderForm);
    }
    @GetMapping(value = "update/{orderId}/{orderDealStatus}")
    public Result update(@PathVariable Long orderId,@PathVariable Integer orderDealStatus)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        TbOrderForm orderForm = new TbOrderForm();
        orderForm.setDealStatus(orderDealStatus);
        orderForm.setId(orderId);
        return Result.success(staticBoundMapper.updateSelectiveByPrimaryKey(orderForm));
    }



    @GetMapping(value = "create")
    public Result create()throws Exception{
        TbOrderForm order = new TbOrderForm();
        order.setOrderNo(new Date().getTime() + "S");
        //order.setId(1125L);
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
