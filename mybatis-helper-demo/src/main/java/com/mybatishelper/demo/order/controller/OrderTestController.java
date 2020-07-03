package com.mybatishelper.demo.order.controller;

import com.mybatishelper.core.base.field.CompareField;
import com.mybatishelper.core.base.field.OrderField;
import com.mybatishelper.core.base.field.SelectField;
import com.mybatishelper.core.base.meta.ItemPar;
import com.mybatishelper.core.base.param.FieldItem;
import com.mybatishelper.core.base.param.ParamItem;
import com.mybatishelper.core.base.param.ValueItem;
import com.mybatishelper.core.enums.Order;
import com.mybatishelper.core.wrapper.factory.FlexibleConditionWrapper;
import com.mybatishelper.core.wrapper.factory.SqlWrapperFactory;
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
import org.apache.ibatis.annotations.Param;
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

////        //构建一个简单的查询包装器
//        QueryWrapper<PropertyConditionWrapper> queryWrapper = SqlWrapperFactory.prop4Query();
////
//        //选择 TbOrderForm类对应的表，查询别名 使用默认值 e
//        queryWrapper.from(TbOrderForm.class);
//
//        // where e.pay_state >= 1
//        queryWrapper.where(w->w
//            .ge("e.payState",1)
//        );
//
//        int paidOrderCount = staticBoundMapper.selectCount(queryWrapper);
//        if(true){
//            return Result.success(paidOrderCount);
//        }

//        //构建查询包装器
//        SelectWrapper<PropertyConditionWrapper> selectWrapper = SqlWrapperFactory.prop4Select();
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
//        UpdateWrapper<PropertyConditionWrapper> updateWrapper = SqlWrapperFactory.prop4Update();
//
//        //update from tb_order_form
//        updateWrapper
//                .from(TbOrderForm.class,"of")
//
//                //1，根据属性匹配列更新，参数化：（of.`pay_state` = ?）
//                .set("of.payState",1)
//
//                // where of.id = 3
//                .where(w->w.eq("of.id",3))
//        ;
//        int effects = staticBoundMapper.updateSelectItem(updateWrapper);
//        if(true){
//            return Result.success(effects);
//        }

//        DeleteWrapper<PropertyConditionWrapper> deleteWrapper = SqlWrapperFactory.prop4Delete();
//        //delete of from tb_order_form of where of.id = 3
//        deleteWrapper
//                .delete("of")
//                .from(TbOrderForm.class,"of")
//                .where(w->w
//                        .eq("of.id",389)
//                );
//        int effects = staticBoundMapper.delete(deleteWrapper);
//        if(true){
//            return Result.success(effects);
//        }

//
//        Consumer<PropertyConditionWrapper> c = x-> x
//                ;
////
//        DeleteWrapper<PropertyConditionWrapper> deleteWrapper = SqlWrapperFactory.prop4Delete();
//        //delete from tb_order_form where e.id = 123456 and e.member_id = 8888
//        deleteWrapper
//                .from(TbOrderForm.class)
//                .where(c);
//        int effects = staticBoundMapper.delete(deleteWrapper);

        Object effects = null;
//        Consumer<FlexibleConditionWrapper> c = x-> x
//                //直接sql替换，等同 LENGTH(member_id) is null
//                .isNull(ValueItem.valueOf("LENGTH(member_id)"))
//
//                //判断某个值是否为空，传参查询，一般用不上，等同 null is null
//                .isNull(ParamItem.valueOf(null))
//
//                //判断字段是否为空 等同：e.order_no is null
//                .isNull(FieldItem.valueOf("e.orderNo"))
//                ;
//        Consumer<FlexibleConditionWrapper> c = x-> x
//                //sql：e.member_id between 1 and ?;Parameters:5(Integer)
//                .between(FieldItem.valueOf("e.memberId"),ValueItem.valueOf(1),ParamItem.valueOf(5))
//
//                //sql：2 between e.member_id and ?;Parameters:6(Integer)
//                .between(ValueItem.valueOf(2),FieldItem.valueOf("e.memberId"),ParamItem.valueOf(6))
//
//                //sql：? between e.member_id and ?;Parameters:7(Integer)
//                .between(ParamItem.valueOf(3),FieldItem.valueOf("e.memberId"),ParamItem.valueOf(7))
//                ;
//
//        effects = staticBoundMapper.selectCount(SqlWrapperFactory.flex4Query().from(TbOrderForm.class).where(c));
        // select orderNo from tb_order_form where id = 1 and pay_state != 1 and   (id = 123 or deal_status in (1,2,3) or   (id = 456 and deal_status = 4)   )   and deltag = false;
        SelectWrapper<FlexibleConditionWrapper> selectWrapper =
                SqlWrapperFactory.flex4Select()
                        .select("e.id")
                        .where(w->w
//                        .where(ConditionType.DO_NOTHING,ValueItem.valueOf("EXISTS (select 1 from tb_order_detail where order_id = e.id and deltag = 0) "),Collections.EMPTY_LIST)
//                        .eq(ValueItem.valueOf(123),ValueItem.valueOf(456))
                        .eq(ParamItem.valueOf(123,2),ParamItem.valueOf(456))
                        );
        effects = orderFormService.selectList(selectWrapper);
        FieldItem.valueOf(CompareField.valueOf("e.id"));
        //ConditionType.EQ
//        Consumer<FlexibleConditionWrapper> lc = f->
//                f.eq(FieldItem.valueOf("of.id"),FieldItem.valueOf("od.orderId"))
//                ;
//        Consumer<FlexibleConditionWrapper> c = w->
//                w.gt(FieldItem.valueOf("od.proPrice"),ValueItem.valueOf(9.9))
//                ;
//        SelectWrapper<FlexibleConditionWrapper>
//                selectWrapper = SqlWrapperFactory.flex4Select()
//                .select("od.orderId,of.dealStatus")
//                .from(TbOrderDetail.class,"od")
//                .leftJoin(TbOrderForm.class,"of",lc)
//                .where(c)
//                ;
//        List effects = orderDetailService.selectList(selectWrapper);
//
//
        if(true){
            return Result.success(effects);
        }


























        Collection<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        Result.success(orderFormService.selectList(SqlWrapperFactory.prop4Select()
                .select(("e.memberId,e.memberId userInfo"))
                .leftJoin(TbOrderDetail.class,"od", od->
                        od
                                .eq(ItemPar.valueOf(FieldItem.valueOf("e.id"), FieldItem.valueOf("od.orderId")))
                                .eq("od.productId",300L)
                        )
                .where(w->
                        w
                                .eq(ItemPar.valueOf(FieldItem.valueOf("e.id"),FieldItem.valueOf("e.orderTypeStr")))
                                .eq("e.amountPaid",amountPaid)
                        .or(x->
                                x
                                        .eq(ItemPar.valueOf(FieldItem.valueOf("e.id"),FieldItem.valueOf("e.call")))
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
        return Result.success(orderFormService.selectPageListV(SqlWrapperFactory.prop4Select()
                        .select(("e.memberId,e.memberId userInfo"))
                        .leftJoin(TbOrderDetail.class,"od",od->od
                                        .eq(ItemPar.valueOf(FieldItem.valueOf("e.id"), FieldItem.valueOf("od.orderId")))
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
        return Result.success(orderDetailService.selectPageList(SqlWrapperFactory.prop4Select()
                .select("e.id,e.orderId orderId,e.id bid")
//                .select(
//                        SelectField.valueOf("e.id"),
//                        SelectField.valueOf("e.id id0"),
//                        SelectField.valueOf("e.order_id",true),
//                        SelectField.valueOf("e.order_id kbs",true)
//                )
                .page(new PageInfo())
//                .orderBy("e.id,e.orderId,of.memberId desc")
//                .orderBy(
//                        OrderField.valueOf("of.create_date",true)
//                        ,
//                        OrderField.valueOf("of.create_date asc",true)
//                        ,
//                        OrderField.valueOf("of.create_date desc",true)
//                        ,
//                        OrderField.valueOf("of.createDate",false)
//                        ,
//                        OrderField.valueOf("of.updateDate", Order.DESC)
//
//                )
        .leftJoin(TbOrderForm.class,"of", of->
                of.eq(ItemPar.valueOf(ParamItem.valueOf("e.orderId"),FieldItem.valueOf("of.id")))
        )
        .leftJoin(TbOrderForm.class,"ff",of->
                of
                        .eq(ItemPar.valueOf(FieldItem.valueOf("e.orderId"),FieldItem.valueOf("ff.id")))
                .or(x->
                        x.ge(ItemPar.valueOf(FieldItem.valueOf("e.orderId"),ParamItem.valueOf("ff.id")))
                                .neq(ItemPar.valueOf(FieldItem.valueOf("e.orderId"), ValueItem.valueOf("ff.id")))
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
//        ));
    }


}
