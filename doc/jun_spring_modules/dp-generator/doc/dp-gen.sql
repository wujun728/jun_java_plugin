/*
Navicat MySQL Data Transfer

Source Server         : mysql192.168.180.134
Source Server Version : 50548
Source Host           : 192.168.180.134:3306
Source Database       : dp-gen

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2017-08-31 09:48:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态，0：禁用，1：正常',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试代码生成';

-- ----------------------------
-- Records of sys_test
-- ----------------------------
