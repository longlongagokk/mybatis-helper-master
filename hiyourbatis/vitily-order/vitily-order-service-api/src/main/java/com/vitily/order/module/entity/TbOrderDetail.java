package com.vitily.order.module.entity;

import club.yourbatis.hi.annotation.Column;
import club.yourbatis.hi.annotation.Table;
import com.vitily.common.module.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description : 订单详情
 * @author      : Administrator
 * @createDate  : 2020-02-29
 */
@Table("`tb_order_detail`")
@Data()
@Accessors(chain = true)
public class TbOrderDetail extends BaseEntity<TbOrderDetail> {
    /**
     * 产品id
     */
    @Column("`product_id`")
    private Long productId;

    /**
     * 产品单价
     */
    @Column("`pro_price`")
    private BigDecimal proPrice;

    /**
     * 产品图片
     */
    @Column("`pro_pic_list`")
    private String proPicList;

    /**
     * 产品描述
     */
    @Column("`pro_name`")
    private String proName;

    /**
     * 商品编号
     */
    @Column("`pro_code`")
    private String proCode;

    /**
     * 购买数量
     */
    @Column("`purchase_quantity`")
    private Integer purchaseQuantity;

    /**
     * 产品规格关联id
     */
    @Column("`pro_sp_id`")
    private Long proSpId;

    /**
     * 订单Id
     */
    @Column("`order_id`")
    private Long orderId;
}