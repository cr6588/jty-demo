DROP DATABASE IF EXISTS `jty_uid_sequence`;
create DATABASE jty_uid_sequence;
use jty_uid_sequence;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_id_sequence
-- ----------------------------
DROP TABLE IF EXISTS `goods_id_sequence`;
CREATE TABLE `goods_id_sequence` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stub` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stub` (`stub`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_id_sequence
-- ----------------------------
INSERT INTO `goods_id_sequence` VALUES ('2', 'a');

-- ----------------------------
-- Table structure for order_id_sequence
-- ----------------------------
DROP TABLE IF EXISTS `order_id_sequence`;
CREATE TABLE `order_id_sequence` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stub` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stub` (`stub`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_id_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for user_id_sequence
-- ----------------------------
DROP TABLE IF EXISTS `user_id_sequence`;
CREATE TABLE `user_id_sequence` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `stub` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stub` (`stub`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_id_sequence
-- ----------------------------
INSERT INTO `user_id_sequence` VALUES ('1', 'a');

-- ----------------------------
-- Procedure structure for truncateAll
-- ----------------------------
/*
DROP PROCEDURE IF EXISTS `truncateAll`;
DELIMITER ;;
CREATE DEFINER=`dev`@`%` PROCEDURE `truncateAll`()
BEGIN
    TRUNCATE jty_order_0.t_order_1;
    TRUNCATE jty_order_0.t_order_2;
    TRUNCATE jty_order_0.t_order_3;
    TRUNCATE jty_order_0.t_order_0;
    TRUNCATE jty_order_0.order_goods_0;
    TRUNCATE jty_order_0.order_goods_1;
    TRUNCATE jty_order_0.order_goods_2;
    TRUNCATE jty_order_0.order_goods_3;

    TRUNCATE jty_order_1.t_order_1;
    TRUNCATE jty_order_1.t_order_2;
    TRUNCATE jty_order_1.t_order_3;
    TRUNCATE jty_order_1.t_order_0;
    TRUNCATE jty_order_1.order_goods_0;
    TRUNCATE jty_order_1.order_goods_1;
    TRUNCATE jty_order_1.order_goods_2;
    TRUNCATE jty_order_1.order_goods_3;

    TRUNCATE jty_user_0.user_4;
    TRUNCATE jty_user_0.user_2;
    TRUNCATE jty_user_0.user_6;
    TRUNCATE jty_user_0.user_0;

    TRUNCATE jty_user_1.user_1;
    TRUNCATE jty_user_1.user_5;
    TRUNCATE jty_user_1.user_3;
    TRUNCATE jty_user_1.user_7;

    TRUNCATE jty_goods_0.goods_1;
    TRUNCATE jty_goods_0.goods_2;
    TRUNCATE jty_goods_0.goods_3;
    TRUNCATE jty_goods_0.goods_0;

    TRUNCATE jty_goods_1.goods_1;
    TRUNCATE jty_goods_1.goods_2;
    TRUNCATE jty_goods_1.goods_3;
    TRUNCATE jty_goods_1.goods_0;

    TRUNCATE order_id_sequence;
    TRUNCATE user_id_sequence;
    TRUNCATE goods_id_sequence;
END
;;
DELIMITER ;

*/