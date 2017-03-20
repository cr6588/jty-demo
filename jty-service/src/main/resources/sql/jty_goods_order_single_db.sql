DROP DATABASE IF EXISTS `jty_goods_order_single_db`;
create DATABASE jty_goods_order_single_db;
USE jty_goods_order_single_db;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_0
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `SKU` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `FK74F24F25EC3B6AA8` (`goods_id`),
  KEY `FK74F24F25B7B1C7A8` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA0C0C3C3DF8E2401` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;