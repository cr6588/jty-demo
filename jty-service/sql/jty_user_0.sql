/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : jty_user_0

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-03-15 18:11:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_0
-- ----------------------------
DROP TABLE IF EXISTS `user_0`;
CREATE TABLE `user_0` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `realname` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `company` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8 DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `qq` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_2
-- ----------------------------
DROP TABLE IF EXISTS `user_2`;
CREATE TABLE `user_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `realname` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `company` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8 DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `qq` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_4
-- ----------------------------
DROP TABLE IF EXISTS `user_4`;
CREATE TABLE `user_4` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `realname` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `company` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8 DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `qq` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_6
-- ----------------------------
DROP TABLE IF EXISTS `user_6`;
CREATE TABLE `user_6` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `realname` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `company` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8 DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `qq` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
