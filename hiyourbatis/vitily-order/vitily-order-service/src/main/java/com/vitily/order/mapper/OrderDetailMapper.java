package com.vitily.order.mapper;

import com.vitily.common.mapper.CommonBasicMapper;
import club.yourbatis.hi.mapper.BasicMapper;
import com.vitily.order.module.entity.TbOrderDetail;
import com.vitily.order.module.view.TvOrderDetail;

public interface OrderDetailMapper extends CommonBasicMapper<TbOrderDetail, TvOrderDetail>, BasicMapper<TbOrderDetail, TvOrderDetail> {
}