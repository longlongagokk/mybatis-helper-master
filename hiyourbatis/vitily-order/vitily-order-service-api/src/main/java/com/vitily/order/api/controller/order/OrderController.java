package com.vitily.order.api.controller.order;

import club.yourbatis.hi.wrapper.query.SelectWrapper;
import com.vitily.common.module.Result;
import com.vitily.order.service.OrderDetailService;
import com.vitily.order.service.OrderFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    OrderFormService orderFormService;
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping(value = "list")
    public Result list(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String[] ids = new String[]{"1","2","3"};
        return Result.success(orderFormService.selectOne(SelectWrapper.build()
//                new MultiTableQueryWrapper<TbOrderForm>()
//                        .leftJoin(ClassAssociateTableInfo.valueOf(TbOrderDetail.class,"o"),
//                                x->x.eqc(FieldField.valueOf("o.orderId","e.id"))
//                                .eq(FieldValue.valueOf("o.productId",300L)
//                            )
//                        )
//                        .eqc(CompareAlias.valueOf(TsOrderForm.Fields.orderNo,"e"),
//                                CompareAlias.valueOf(TsOrderForm.Fields.orderTypeStr,"e")
//                        )
////
//                        .eq(CompareAlias.valueOf(TsOrderForm.Fields.amountPaid,"e"),req.getUserId())
//                        .or(
//                                x->
//                                        x.eqc(CompareAlias.valueOf(TsOrderForm.Fields.amountPay,"e"),
//                                                CompareAlias.valueOf(TsOrderForm.Fields.call,"e")
//                                        )
//                                                .eq(CompareAlias.valueOf(TsOrderForm.Fields.deliveryId,"e"),req.getUserId())
//                                .in(CompareAlias.valueOf(TsOrderForm.Fields.id,"e"),
//                                        StringUtil.StringsToLongList(ids)
//                                        )
//                                .eqc(CompareAlias.valueOf(TsOrderForm.Fields.area,"e"),
//                                        //EnumFieldCol.valueOf(TsOrderForm.Fields.phone,"e"),
//                                        CompareAlias.valueOf(TsOrderForm.Fields.postCode,"e")
//                                        )
//                                .betweenc(CompareAlias.valueOf(TsOrderForm.Fields.area,"e"),
//                                        CompareAlias.valueOf(TsOrderForm.Fields.phone,"e"),
//                                        CompareAlias.valueOf(TsOrderForm.Fields.postCode,"e")
//                                )
//                                .between(CompareAlias.valueOf(TsOrderForm.Fields.receiver,"e"),
//                                        3l,
//                                        19l
//                                )
//                        )
//                        .page(PageInfo.valueOf(1,4))
                )
        );
    }
    @GetMapping(value = "detail-list")
    public Result detailList(HttpServletRequest request, HttpServletResponse response)throws Exception{
        return Result.success(orderDetailService.selectPageList(SelectWrapper.build()));
    }
    @GetMapping(value = "detail/{id}")
    public Result list(@PathVariable long id)throws Exception{
        //return Result.success(orderDetailService.selectOne(new QueryWrapper<TbOrderDetail>().eq(TsOrderDetail.Fields.orderId,id)));
        return Result.success(orderFormService.selectOne(id));
    }
}
