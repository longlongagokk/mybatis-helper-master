/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : mybatis-helper-demo

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-05-14 21:20:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_order_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_comment`;
CREATE TABLE `tb_order_comment` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `deltag` tinyint(1) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `state` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for `tb_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近一次更新时间',
  `deltag` tinyint(1) DEFAULT NULL COMMENT '删除位',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `pro_price` decimal(20,2) unsigned DEFAULT NULL COMMENT '产品单价',
  `pro_pic_list` varchar(4096) DEFAULT NULL COMMENT '产品图片',
  `pro_name` varchar(2048) DEFAULT NULL COMMENT '产品描述',
  `pro_code` varchar(64) DEFAULT NULL COMMENT '商品编号',
  `purchase_quantity` int(20) DEFAULT NULL COMMENT '购买数量',
  `pro_sp_id` bigint(20) DEFAULT NULL COMMENT '产品规格关联id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情';

-- ----------------------------
-- Records of tb_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_order_form`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_form`;
CREATE TABLE `tb_order_form` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近一次更新时间',
  `deltag` tinyint(1) DEFAULT NULL COMMENT '删除位',
  `delivery_id` bigint(20) DEFAULT NULL COMMENT '配送方式id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `pay_state` int(4) DEFAULT NULL COMMENT '支付状态',
  `receiver` varchar(64) DEFAULT NULL COMMENT '收货人',
  `area` varchar(256) DEFAULT NULL COMMENT '省市区',
  `address` varchar(128) DEFAULT NULL COMMENT '详细地址',
  `post_code` varchar(16) DEFAULT NULL COMMENT '邮编',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `call` varchar(32) DEFAULT NULL COMMENT '固话',
  `email` varchar(128) DEFAULT NULL COMMENT '收货人邮箱',
  `send_date` varchar(128) DEFAULT NULL COMMENT '指定送货时间',
  `pro_amount` decimal(30,8) unsigned DEFAULT NULL COMMENT '产品总额',
  `delivery_cost` decimal(30,8) unsigned DEFAULT NULL COMMENT '运费',
  `amount_pay` decimal(30,8) unsigned DEFAULT NULL COMMENT '订单总金额',
  `amount_paid` decimal(30,8) unsigned DEFAULT NULL COMMENT '已支付金额',
  `pay_way_id` bigint(20) DEFAULT NULL COMMENT '支付方式id',
  `vote_title` varchar(128) DEFAULT NULL COMMENT '发票抬头',
  `vote_company` varchar(128) DEFAULT NULL COMMENT '单位名称',
  `vote_content` varchar(128) DEFAULT NULL COMMENT '发票内容',
  `delivery_status` int(4) DEFAULT NULL COMMENT '物流状态',
  `order_type_str` varchar(256) DEFAULT NULL COMMENT '订单类型id组合',
  `leave_message` varchar(512) DEFAULT NULL COMMENT '订单留言',
  `deal_status` int(2) DEFAULT NULL COMMENT '处理状态',
  `order_date` datetime DEFAULT NULL COMMENT '下单时间',
  `user_name` varchar(255) DEFAULT NULL COMMENT '下单用户',
  `delivery_way_name` varchar(255) DEFAULT NULL COMMENT '物理方式名称',
  `pay_way_name` varchar(255) DEFAULT NULL COMMENT '支付方式名字',
  PRIMARY KEY (`id`),
  KEY `id_index` (`id`) USING BTREE,
  KEY `member_id_index` (`member_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8 COMMENT='订单主表';

-- ----------------------------
-- Records of tb_order_form
-- ----------------------------
INSERT INTO `tb_order_form` VALUES ('1', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('2', '2020-02-28 01:06:02', null, null, null, '456', '23', '2', null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('3', '2020-02-28 01:06:02', null, null, null, '888', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('4', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('5', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('6', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('7', '2020-02-28 01:06:02', null, null, null, '888', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('8', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('9', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('10', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, '2', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('15', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('16', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('17', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('18', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('19', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('20', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('21', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('22', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('23', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('24', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('27', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('28', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('29', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('30', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('31', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('32', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('35', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('36', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('37', '2020-02-28 01:06:02', null, null, null, '123', 'absfwsfdwef', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_order_form` VALUES ('38', '2020-02-28 01:06:02', null, null, null, '456', 'ddfea', null, null, null, null, null, null, null, null, null, null, null, null, '100.00000000', null, null, null, null, null, null, null, null, null, null, null, null);
