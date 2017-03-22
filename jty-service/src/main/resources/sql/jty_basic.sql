/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : jty_basic

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-03-21 16:56:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `crfw_system_i18n`
-- ----------------------------
DROP TABLE IF EXISTS `crfw_system_i18n`;
CREATE TABLE `crfw_system_i18n` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crfw_system_i18n
-- ----------------------------
INSERT INTO `crfw_system_i18n` VALUES ('1', 'home', 'zh_CN', '主页', '', '', '2017-03-20 09:32:40', '2017-03-20 09:32:45');
INSERT INTO `crfw_system_i18n` VALUES ('2', 'home', 'en_US', 'Home', '', '', '2017-03-20 09:33:19', '2017-03-20 09:33:22');
INSERT INTO `crfw_system_i18n` VALUES ('3', 'userList', 'zh_CN', '用户列表', '', '', '2017-03-20 09:46:10', '2017-03-20 09:46:13');
INSERT INTO `crfw_system_i18n` VALUES ('4', 'userList', 'en_US', 'User List', '', '', '2017-03-20 09:46:58', '2017-03-20 09:47:04');
INSERT INTO `crfw_system_i18n` VALUES ('5', 'language', 'zh_CN', '语言', '', '', '2017-03-20 09:55:40', '2017-03-20 09:55:43');
INSERT INTO `crfw_system_i18n` VALUES ('6', 'language', 'en_US', 'language', '', '', '2017-03-20 09:56:08', '2017-03-20 09:56:10');

-- ----------------------------
-- Table structure for `jty_pub_company_db`
-- ----------------------------
DROP TABLE IF EXISTS `jty_pub_company_db`;
CREATE TABLE `jty_pub_company_db` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `com_id` bigint(64) DEFAULT NULL,
  `table_id` bigint(64) DEFAULT NULL,
  `module` int(16) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jty_pub_company_db
-- ----------------------------

-- ----------------------------
-- Table structure for `jty_pub_database`
-- ----------------------------
DROP TABLE IF EXISTS `jty_pub_database`;
CREATE TABLE `jty_pub_database` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `db_type` int(16) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `port` int(16) DEFAULT NULL,
  `module` int(16) DEFAULT NULL,
  `db_status` int(1) DEFAULT NULL,
  `db_level` int(16) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jty_pub_database
-- ----------------------------

-- ----------------------------
-- Table structure for `jty_pub_database_instance`
-- ----------------------------
DROP TABLE IF EXISTS `jty_pub_database_instance`;
CREATE TABLE `jty_pub_database_instance` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `db_id` bigint(64) DEFAULT NULL,
  `db_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `db_status` int(1) DEFAULT NULL,
  `db_level` int(16) DEFAULT NULL,
  `module` int(16) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jty_pub_database_instance
-- ----------------------------

-- ----------------------------
-- Table structure for `jty_pub_database_table`
-- ----------------------------
DROP TABLE IF EXISTS `jty_pub_database_table`;
CREATE TABLE `jty_pub_database_table` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `db_ins_id` bigint(64) DEFAULT NULL,
  `db_status` int(1) DEFAULT NULL,
  `db_level` int(16) DEFAULT NULL,
  `module` int(16) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jty_pub_database_table
-- ----------------------------
