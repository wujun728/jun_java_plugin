/*
Navicat MySQL Data Transfer

Source Server         : LocalMySql
Source Server Version : 50624
Source Host           : 172.25.9.99:3306
Source Database       : sanxia

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-06-14 10:12:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(8) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456');
INSERT INTO `user` VALUES ('2', 'test', '456');
INSERT INTO `user` VALUES ('3', 'admin', 'admin');
INSERT INTO `user` VALUES ('4', 'test', 'test');
INSERT INTO `user` VALUES ('7', 'test', 'test');
INSERT INTO `user` VALUES ('8', 'test', 'test');
INSERT INTO `user` VALUES ('9', 'test', '875');
INSERT INTO `user` VALUES ('10', 'admin', '456');
INSERT INTO `user` VALUES ('11', 'admin', '456');
INSERT INTO `user` VALUES ('12', 'admin', '456');
INSERT INTO `user` VALUES ('13', 'admin', '456');
