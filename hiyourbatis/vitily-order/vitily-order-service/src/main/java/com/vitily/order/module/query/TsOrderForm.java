package com.vitily.order.module.query;

import com.vitily.common.module.QueryInfo;
import com.vitily.order.module.entity.TbOrderForm;
import lombok.Data;

@Data
public class TsOrderForm extends QueryInfo<TbOrderForm> {

    public enum Fields {
        id,
        createDate,
        updateDate,
        deltag,
        deliveryId,
        memberId,
        orderNo,
        payState,
        receiver,
        area,
        address,
        postCode,
        phone,
        call,
        email,
        sendDate,
        proAmount,
        deliveryCost,
        amountPay,
        amountPaid,
        payWayId,
        voteTitle,
        voteCompany,
        voteContent,
        deliveryStatus,
        orderTypeStr,
        leaveMessage,
        dealStatus,
        orderDate,
        userName,
        deliveryWayName,
        payWayName;
    }
}