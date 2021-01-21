/*

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : zlinks_analysis

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 10/07/2018 18:04:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_element
-- ----------------------------
DROP TABLE IF EXISTS `auth_element`;
CREATE TABLE `auth_element` (
  `element_id` bigint(20) DEFAULT NULL,
  `auth_id` bigint(20) DEFAULT NULL,
  KEY `auth_id` (`auth_id`),
  KEY `auth_element_ibfk_2` (`element_id`),
  CONSTRAINT `auth_element_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `auth_info` (`auth_id`),
  CONSTRAINT `auth_element_ibfk_2` FOREIGN KEY (`element_id`) REFERENCES `element` (`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for auth_info
-- ----------------------------
DROP TABLE IF EXISTS `auth_info`;
CREATE TABLE `auth_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_id` bigint(20) NOT NULL,
  `parent_auth_id` bigint(20) DEFAULT NULL,
  `auth_type` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'MENU:菜单；ELEMENT:元素',
  PRIMARY KEY (`id`),
  KEY `auth_id` (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `auth_menu`;
CREATE TABLE `auth_menu` (
  `menu_id` bigint(20) DEFAULT NULL,
  `auth_id` bigint(20) DEFAULT NULL,
  KEY `auth_id` (`auth_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `auth_menu_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `auth_info` (`auth_id`),
  CONSTRAINT `auth_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for element
-- ----------------------------
DROP TABLE IF EXISTS `element`;
CREATE TABLE `element` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `element_id` bigint(20) NOT NULL,
  `parent_element_id` bigint(20) DEFAULT NULL,
  `element_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `element_uri_frontend` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `element_uri_backend` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `sort_no` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `element_id` (`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for game_info
-- ----------------------------
DROP TABLE IF EXISTS `game_info`;
CREATE TABLE `game_info` (
  `id` bigint(20) NOT NULL,
  `game_id` bigint(20) NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  `name_zh` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `game_id` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL,
  `parent_menu_id` bigint(20) DEFAULT NULL,
  `menu_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `menu_uri_frontend` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `menu_uri_backend` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `sort_no` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for role_auth
-- ----------------------------
DROP TABLE IF EXISTS `role_auth`;
CREATE TABLE `role_auth` (
  `role_id` bigint(20) DEFAULT NULL,
  `auth_id` bigint(20) DEFAULT NULL,
  KEY `role_id` (`role_id`),
  KEY `auth_id` (`auth_id`),
  CONSTRAINT `role_auth_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`role_id`),
  CONSTRAINT `role_auth_ibfk_2` FOREIGN KEY (`auth_id`) REFERENCES `auth_info` (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `role_name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_game_role
-- ----------------------------
DROP TABLE IF EXISTS `user_game_role`;
CREATE TABLE `user_game_role` (
  `user_id` bigint(20) DEFAULT NULL,
  `game_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  KEY `game_id` (`game_id`),
  CONSTRAINT `user_game_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`),
  CONSTRAINT `user_game_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`role_id`),
  CONSTRAINT `user_game_role_ibfk_3` FOREIGN KEY (`game_id`) REFERENCES `game_info` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `account` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `birth` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `sex` varchar(6) COLLATE utf8mb4_bin DEFAULT NULL,
  `mobile` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `nick_name` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL,
  `admin_flag` varchar(3) COLLATE utf8mb4_bin DEFAULT NULL,
  `uid` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for usr_register_invite
-- ----------------------------
DROP TABLE IF EXISTS `usr_register_invite`;
CREATE TABLE `usr_register_invite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `mobile` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL,
  `url` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `is_click` varchar(3) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'YES：已点击；NO：未点击',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_valid` varchar(3) COLLATE utf8mb4_bin DEFAULT NULL,
  `operator` bigint(20) DEFAULT NULL,
  `url_md5` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `games` varchar(300) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '游戏id，用“-”连接',
  `password` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
