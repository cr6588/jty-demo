/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : jty_order

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-03-15 17:05:05
*/

DROP DATABASE IF EXISTS `jty_order_0`;
create DATABASE jty_order_0;
USE jty_order_0;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_goods_0
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_COMPANY_MARK`;
CREATE TABLE `order_goods_COMPANY_MARK` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `FK74F24F25EC3B6AA8` (`goods_id`),
  KEY `FK74F24F25B7B1C7A8` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_COMPANY_MARK`;
CREATE TABLE `t_order_COMPANY_MARK` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0C0C3C3DF8E2401` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `goods_COMPANY_MARK`;
CREATE TABLE `goods_COMPANY_MARK` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `SKU` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
