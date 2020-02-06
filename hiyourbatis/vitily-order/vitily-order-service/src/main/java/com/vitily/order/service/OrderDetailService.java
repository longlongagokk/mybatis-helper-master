package com.vitily.order.service;

import com.vitily.common.service.BasicService;
import com.vitily.order.module.entity.TbOrderDetail;
import com.vitily.order.module.request.TqOrderDetail;
import com.vitily.order.module.view.TvOrderDetail;

public interface OrderDetailService extends BasicService<TbOrderDetail, TqOrderDetail, TvOrderDetail> {
}