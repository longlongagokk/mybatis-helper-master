package com.vitily.order.mapper;

import com.vitily.common.mapper.CommonBasicMapper;
import club.yourbatis.hi.mapper.BasicMapper;
import com.vitily.order.module.entity.TbOrderForm;
import com.vitily.order.module.view.TvOrderForm;

public interface OrderFormMapper extends CommonBasicMapper<TbOrderForm, TvOrderForm>, BasicMapper<TbOrderForm, TvOrderForm> {
}