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

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_goods_0
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_0`;
CREATE TABLE `order_goods_0` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `FK74F24F25EC3B6AA8` (`goods_id`),
  KEY `FK74F24F25B7B1C7A8` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_goods_1
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_1`;
CREATE TABLE `order_goods_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `FK74F24F25EC3B6AA8` (`goods_id`),
  KEY `FK74F24F25B7B1C7A8` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_goods_2
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_2`;
CREATE TABLE `order_goods_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `FK74F24F25EC3B6AA8` (`goods_id`),
  KEY `FK74F24F25B7B1C7A8` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_goods_3
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_3`;
CREATE TABLE `order_goods_3` (
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
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0C0C3C3DF8E2401` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0C0C3C3DF8E2401` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order_2
-- ----------------------------
DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0C0C3C3DF8E2401` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order_3
-- ----------------------------
DROP TABLE IF EXISTS `t_order_3`;
CREATE TABLE `t_order_3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0C0C3C3DF8E2401` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for truncateOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `truncateOrder`;
DELIMITER ;;
CREATE DEFINER=`dev`@`%` PROCEDURE `truncateOrder`()
BEGIN
	TRUNCATE t_order_1;
	TRUNCATE t_order_2;
	TRUNCATE t_order_3;
	TRUNCATE t_order_0;
	TRUNCATE order_goods_0;
	TRUNCATE order_goods_1;
	TRUNCATE order_goods_2;
	TRUNCATE order_goods_3;
END
;;
DELIMITER ;
