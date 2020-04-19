package com.vitily.order.service;

import com.vitily.basicservice.BasicService;
import com.vitily.order.module.entity.TbOrderForm;
import com.vitily.order.module.view.TvOrderForm;

public interface OrderFormService extends BasicService<TbOrderForm,TvOrderForm> {
    TvOrderForm selectOne(Long id);
}