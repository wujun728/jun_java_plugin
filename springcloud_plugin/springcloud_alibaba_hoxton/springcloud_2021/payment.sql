/*
 Navicat Premium Data Transfer

 Source Server         : CentOS
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.40.3:3306
 Source Schema         : db2019

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 05/03/2020 19:49:50
*/

SET NAMES utf8mb4;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `serial` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;