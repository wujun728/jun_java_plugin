/*
 Navicat Premium Data Transfer

 Source Server         : mysql-localhost
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : localhost
 Source Database       : mybatis-plus

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 06/25/2017 20:53:49 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `age` int(3) DEFAULT NULL COMMENT '用户年龄',
  `type` int(1) DEFAULT '0' COMMENT '0、禁用 1、正常, 如果使用tinyint(1)，mysql连接没加tinyInt1isBit=false，默认mysql驱动会把值转成boolean',
  `ctime` datetime DEFAULT NULL COMMENT '自定义填充的创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user`(id,name,age,ctime,type) VALUES ('784972358981328902', 'Tom', '24', '2017-06-25 20:53:33', '1');
INSERT INTO `sys_user`(id,name,age,ctime,type) VALUES ('784972358981328903', 'Jammy', '21', '2017-06-25 20:53:37', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
