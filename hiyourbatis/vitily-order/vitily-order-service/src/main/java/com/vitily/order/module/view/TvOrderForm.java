package com.vitily.order.module.view;

import com.vitily.order.module.entity.TbOrderForm;
import lombok.Data;

import java.util.List;

@Data
public class TvOrderForm extends TbOrderForm {
    private List<TvOrderDetail> orderDetails;
}