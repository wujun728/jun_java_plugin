/*
Navicat MySQL Data Transfer

Source Server         : mysql89
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : mydbtest

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2018-04-19 09:30:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_client`;
CREATE TABLE `oauth2_client` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_name` varchar(100) default NULL,
  `client_id` varchar(100) default NULL,
  `client_secret` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of oauth2_client
-- ----------------------------
INSERT INTO `oauth2_client` VALUES ('1', 'sssssssss', 'ssssee', 'dddddddddddxc');
INSERT INTO `oauth2_client` VALUES ('2', 'mmm', '435454-ty454-gt-45435-67', '83423-dfr-4354-gb-32432-435');

-- ----------------------------
-- Table structure for oauth2_user
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_user`;
CREATE TABLE `oauth2_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `username` varchar(100) default NULL,
  `password` varchar(100) default NULL,
  `salt` varchar(100) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_eh02nvxgfxel048ur9tkjadrs` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=35435455 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of oauth2_user
-- ----------------------------
INSERT INTO `oauth2_user` VALUES ('1', 'sa', 'aaaa', 'dddddddddddddddddddddddd');
INSERT INTO `oauth2_user` VALUES ('2', '001', '23432432432432423423', null);
INSERT INTO `oauth2_user` VALUES ('35435454', '23432432', '123', '2');
INSERT INTO `oauth2_user` VALUES ('354454', '32432', '123', '1');
