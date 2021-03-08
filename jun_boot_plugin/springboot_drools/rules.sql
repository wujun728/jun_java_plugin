/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : localhost
 Source Database       : rules

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : utf-8

 Date: 12/14/2016 16:06:02 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `rules`
-- ----------------------------
DROP TABLE IF EXISTS `rules`;
CREATE TABLE `rules` (
  `id` int(11) NOT NULL,
  `rules` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `rules`
-- ----------------------------
BEGIN;
INSERT INTO `rules` VALUES ('1', 'package com.us.drools; import com.us.drools.bean.Message; rule \"Hello World abel \" when message:Message (status == \"0\") then System.out.println(\"hello, Drools abel2   !\"); end');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
