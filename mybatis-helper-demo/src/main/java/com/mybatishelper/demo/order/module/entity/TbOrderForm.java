package com.mybatishelper.demo.order.module.entity;

import com.mybatishelper.core.annotation.Column;
import com.mybatishelper.core.annotation.Table;
import com.mybatishelper.demo.common.module.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description : 订单主表
 * @author      : lether
 * @createDate  : 2020-02-20
 */
@Table(value = "`mybatis-helper-demo`.`tb_order_form`")
@Data()
@Accessors(chain = true)
public class TbOrderForm extends BaseEntity<TbOrderForm> {
    /**
     * 配送方式id
     */
    private Long deliveryId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 订单号
     */
    @Column("order_no")
    private String orderNo;

    /**
     * 支付状态
     */
    private Integer payState;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 省市区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 固话
     */
    private String call;

    /**
     * 收货人邮箱
     */
    private String email;

    /**
     * 指定送货时间
     */
    private String sendDate;

    /**
     * 产品总额
     */
    private BigDecimal proAmount;

    /**
     * 运费
     */
    private BigDecimal deliveryCost;

    /**
     * 订单总金额
     */
    private BigDecimal amountPay;

    /**
     * 已支付金额
     */
    private BigDecimal amountPaid;

    /**
     * 支付方式id
     */
    private Long payWayId;

    /**
     * 发票抬头
     */
    private String voteTitle;

    /**
     * 单位名称
     */
    private String voteCompany;

    /**
     * 发票内容
     */
    private String voteContent;

    /**
     * 物流状态
     */
    private Integer deliveryStatus;

    /**
     * 订单类型id组合
     */
    private String orderTypeStr;

    /**
     * 订单留言
     */
    private String leaveMessage;

    /**
     * 处理状态
     */
    private Integer dealStatus;

    /**
     * 下单时间
     */
    private Date orderDate;

    /**
     * 下单用户
     */
    private String userName;

    /**
     * 物理方式名称
     */
    private String deliveryWayName;

    /**
     * 支付方式名字
     */
    private String payWayName;
}