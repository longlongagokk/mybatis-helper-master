package com.mybatishelper.demo.order.module.entity;

import com.mybatishelper.core.annotation.Column;
import com.mybatishelper.core.annotation.Table;
import com.mybatishelper.demo.common.module.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description : 订单详情
 * @author      : lether
 * @createDate  : 2020-05-10
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