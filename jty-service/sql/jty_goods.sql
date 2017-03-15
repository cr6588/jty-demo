/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : jty_goods

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-03-15 17:09:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_0
-- ----------------------------
DROP TABLE IF EXISTS `goods_0`;
CREATE TABLE `goods_0` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `SKU` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_1`;
CREATE TABLE `goods_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `SKU` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_2
-- ----------------------------
DROP TABLE IF EXISTS `goods_2`;
CREATE TABLE `goods_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `SKU` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_3
-- ----------------------------
DROP TABLE IF EXISTS `goods_3`;
CREATE TABLE `goods_3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `SKU` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
