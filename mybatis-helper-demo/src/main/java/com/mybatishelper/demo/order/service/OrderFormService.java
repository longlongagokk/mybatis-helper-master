package com.mybatishelper.demo.order.service;

import com.mybatishelper.demo.basicservice.BasicService;
import com.mybatishelper.demo.order.module.entity.TbOrderForm;
import com.mybatishelper.demo.order.module.view.TvOrderForm;

public interface OrderFormService extends BasicService<TbOrderForm, TvOrderForm> {
    TvOrderForm selectOne(Long id);
}