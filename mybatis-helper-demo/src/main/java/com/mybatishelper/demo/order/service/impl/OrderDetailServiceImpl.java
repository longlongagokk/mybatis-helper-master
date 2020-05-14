package com.mybatishelper.demo.order.service.impl;

import com.mybatishelper.demo.basicservice.impl.BasicServiceImpl;
import com.mybatishelper.demo.order.mapper.OrderDetailMapper;
import com.mybatishelper.demo.order.module.entity.TbOrderDetail;
import com.mybatishelper.demo.order.module.view.TvOrderDetail;
import com.mybatishelper.demo.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends BasicServiceImpl<TbOrderDetail, TvOrderDetail, OrderDetailMapper> implements OrderDetailService {
}