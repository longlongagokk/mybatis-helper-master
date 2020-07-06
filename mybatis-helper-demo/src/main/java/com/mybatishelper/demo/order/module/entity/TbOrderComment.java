package com.mybatishelper.demo.order.module.entity;

import com.mybatishelper.core.annotation.Primary;
import com.mybatishelper.core.annotation.Table;
import com.mybatishelper.demo.common.module.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description :
 * @author      : Administrator
 * @createDate  : 2020-07-06
 */
@Table("`mybatis-helper-demo`.`tb_order_comment`")
@Data()
@Accessors(chain = true)
public class TbOrderComment extends BaseEntity<TbOrderComment> {
    /**
     *
     */
    @Primary()
    private Long orderId;

    /**
     *
     */
    private Long memberId;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private String auditstate;
}