/*
Navicat MySQL Data Transfer

Source Server         : mysqll_127.0.0.1
Source Server Version : 50505
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2021-08-14 00:18:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL DEFAULT '' COMMENT '姓名',
  `password` varchar(40) NOT NULL DEFAULT '',
  `email` varchar(60) NOT NULL DEFAULT '',
  `birthday` date DEFAULT NULL,
  `age` int(11) DEFAULT 0,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'aa1', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('3', 'aa2', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('4', 'aa3', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('5', 'ddd', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('6', 'aa5', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('7', 'aa6', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('8', 'aa7', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('9', 'aa8', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('10', 'aa9', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('11', 'aa0', '123', 'aa@sina.com', '2019-07-04', '0');
INSERT INTO `user` VALUES ('32', '1111', '11122', 'gacl@sina.com', '2019-07-04', '0');
