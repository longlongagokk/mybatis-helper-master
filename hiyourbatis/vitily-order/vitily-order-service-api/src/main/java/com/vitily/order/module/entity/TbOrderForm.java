package com.vitily.order.module.entity;

import club.yourbatis.hi.annotation.Column;
import club.yourbatis.hi.annotation.Table;
import com.vitily.common.module.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description : 订单主表
 * @author      : lether
 * @createDate  : 2020-02-20
 */
@Table("`hiyourbatis-test`.`tb_order_form`")
@Data()
@Accessors(chain = true)
public class TbOrderForm extends BaseEntity<TbOrderForm> {
    /**
     * 配送方式id
     */
    @Column("`delivery_id`")
    private Long deliveryId;

    /**
     * 会员id
     */
    @Column("`member_id`")
    private Long memberId;

    /**
     * 订单号
     */
    @Column("`order_no`")
    private String orderNo;

    /**
     * 支付状态
     */
    @Column("`pay_state`")
    private Integer payState;

    /**
     * 收货人
     */
    @Column("`receiver`")
    private String receiver;

    /**
     * 省市区
     */
    @Column("`area`")
    private String area;

    /**
     * 详细地址
     */
    @Column("`address`")
    private String address;

    /**
     * 邮编
     */
    @Column("`post_code`")
    private String postCode;

    /**
     * 手机号码
     */
    @Column("`phone`")
    private String phone;

    /**
     * 固话
     */
    @Column("`call`")
    private String call;

    /**
     * 收货人邮箱
     */
    @Column("`email`")
    private String email;

    /**
     * 指定送货时间
     */
    @Column("`send_date`")
    private String sendDate;

    /**
     * 产品总额
     */
    @Column("`pro_amount`")
    private BigDecimal proAmount;

    /**
     * 运费
     */
    @Column("`delivery_cost`")
    private BigDecimal deliveryCost;

    /**
     * 订单总金额
     */
    @Column("`amount_pay`")
    private BigDecimal amountPay;

    /**
     * 已支付金额
     */
    @Column("`amount_paid`")
    private BigDecimal amountPaid;

    /**
     * 支付方式id
     */
    @Column("`pay_way_id`")
    private Long payWayId;

    /**
     * 发票抬头
     */
    @Column("`vote_title`")
    private String voteTitle;

    /**
     * 单位名称
     */
    @Column("`vote_company`")
    private String voteCompany;

    /**
     * 发票内容
     */
    @Column("`vote_content`")
    private String voteContent;

    /**
     * 物流状态
     */
    @Column("`delivery_status`")
    private Integer deliveryStatus;

    /**
     * 订单类型id组合
     */
    @Column("`order_type_str`")
    private String orderTypeStr;

    /**
     * 订单留言
     */
    @Column("`leave_message`")
    private String leaveMessage;

    /**
     * 处理状态
     */
    @Column("`deal_status`")
    private Integer dealStatus;

    /**
     * 下单时间
     */
    @Column("`order_date`")
    private Date orderDate;

    /**
     * 下单用户
     */
    @Column("`user_name`")
    private String userName;

    /**
     * 物理方式名称
     */
    @Column("`delivery_way_name`")
    private String deliveryWayName;

    /**
     * 支付方式名字
     */
    @Column("`pay_way_name`")
    private String payWayName;
}