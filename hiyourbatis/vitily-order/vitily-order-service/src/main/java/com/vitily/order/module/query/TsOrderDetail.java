package com.vitily.order.module.query;

import com.vitily.common.module.QueryInfo;
import com.vitily.order.module.entity.TbOrderDetail;
import lombok.Data;

@Data
public class TsOrderDetail extends QueryInfo<TbOrderDetail> {

    public enum Fields {
        id,
        createDate,
        updateDate,
        deltag,
        productId,
        proPrice,
        proPicList,
        proName,
        proCode,
        purchaseQuantity,
        proSpId,
        orderId;
    }
}