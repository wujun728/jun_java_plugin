/*
 Navicat Premium Data Transfer

 Source Server         : CentOS虚拟机
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 192.168.113.6:3306
 Source Schema         : seata_storage

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 13/03/2020 10:06:21
*/

SET NAMES utf8mb4;

-- ----------------------------
-- Table structure for seata_storage
-- ----------------------------
DROP TABLE IF EXISTS `seata_storage`;
CREATE TABLE `t_storage`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) NULL DEFAULT NULL COMMENT '产品id',
  `total` int(11) NULL DEFAULT NULL COMMENT '库存',
  `used` int(11) NULL DEFAULT NULL COMMENT '已用库存',
  `residue` int(11) NULL DEFAULT NULL COMMENT '剩余库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 ;

