package com.mybatishelper.demo.order.module.view;

import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import lombok.Data;

import java.util.List;

@Data
public class TvOrderForm extends TbOrderForm {
    private String userInfo;
    private List<TvOrderDetail> orderDetails;
}