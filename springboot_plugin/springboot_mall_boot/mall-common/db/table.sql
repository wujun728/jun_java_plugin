/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-20 21:23:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods_brand
-- ----------------------------
DROP TABLE IF EXISTS `goods_brand`;
CREATE TABLE `goods_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '品牌名称',
  `code` varchar(100) DEFAULT NULL COMMENT '品牌编码',
  `icon` varchar(100) DEFAULT NULL COMMENT '品牌图标',
  `descripe` varchar(255) DEFAULT NULL COMMENT '品牌描述',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_brand
-- ----------------------------
INSERT INTO `goods_brand` VALUES ('1', '华为', null, '2018/0914/1730.jpg', '华为						\r\n                    ', '2018-09-14 22:21:21', null, '10');
INSERT INTO `goods_brand` VALUES ('2', 'c', null, '2018/0914/2734.jpg', 'oppo   ', '2018-09-14 22:21:46', null, '10');

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '类别名称',
  `code` varchar(100) DEFAULT NULL COMMENT '分类编码',
  `parent_id` int(11) DEFAULT '0',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `show_index_flag` tinyint(1) DEFAULT '0' COMMENT '是否首页展示',
  `level` int(11) DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES ('2', '水果', null, '0', '0', '2018-08-11 21:47:13', null, '2018/0811/8306.jpg', '1', '1', null);
INSERT INTO `goods_category` VALUES ('3', '手机', null, '0', '0', '2018-09-14 22:25:49', null, '2018/0914/5930.png', '1', '1', '手机');

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_category_id` int(11) NOT NULL COMMENT '商品类别id',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '商品总量',
  `sold_count` int(11) DEFAULT '0',
  `description` text COMMENT '商品描述',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `goods_code` varchar(100) DEFAULT NULL,
  `goods_brand_id` int(11) NOT NULL,
  `stock` int(255) NOT NULL DEFAULT '0',
  `sales_volume` int(11) DEFAULT '0',
  `show_index_flag` tinyint(1) DEFAULT '0' COMMENT '是否首页展示',
  `detail` text,
  `goods_unit_id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `thumb` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES ('15', '3', 'vivoy75s手机', '0', '0', 'vivoy75s手机', '0.00', null, '1', '100', '0', '0', '<p>颠三倒四多</p>', '1', '10010', '2018-10-06 22:44:25', '2018-10-06 22:44:25', '2018/1006/0794.png', '2018/1006/8930.png');
INSERT INTO `goods_info` VALUES ('16', '2', 'dsd', '0', '0', 'dsd', '0.00', null, '1', '0', '0', '0', '<p>dsdsdsd</p>', '1', 'dsd', '2018-10-07 10:00:18', '2018-10-07 10:00:18', '2018/1007/8024.png', '2018/1007/3850.png');
INSERT INTO `goods_info` VALUES ('17', '2', 'ffd', '0', '0', 'fdfd', '0.00', null, '0', '0', '0', '0', '<p>fdf</p>', '1', 'fdfdf', '2018-10-07 10:02:02', '2018-10-07 10:02:02', '2018/1007/7450.png', '2018/1007/9650.jpg');
INSERT INTO `goods_info` VALUES ('18', '3', 'dsds', '0', '0', 'dsd', '0.00', null, '1', '0', '0', '0', null, '1', 'dsd', '2018-10-07 10:03:46', '2018-10-07 10:03:46', '2018/1007/1253.png', '2018/1007/1724.png');

-- ----------------------------
-- Table structure for goods_info_property
-- ----------------------------
DROP TABLE IF EXISTS `goods_info_property`;
CREATE TABLE `goods_info_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_info_id` int(11) NOT NULL,
  `goods_property_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_info_property
-- ----------------------------

-- ----------------------------
-- Table structure for goods_info_property_value
-- ----------------------------
DROP TABLE IF EXISTS `goods_info_property_value`;
CREATE TABLE `goods_info_property_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_property_group_value` varchar(500) NOT NULL,
  `goods_info_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `sale_price` decimal(10,2) DEFAULT '0.00',
  `stock` int(11) DEFAULT '0',
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_info_property_value
-- ----------------------------

-- ----------------------------
-- Table structure for goods_property
-- ----------------------------
DROP TABLE IF EXISTS `goods_property`;
CREATE TABLE `goods_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '类别属性名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '类别属性值',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_property
-- ----------------------------
INSERT INTO `goods_property` VALUES ('1', '颜色', '', null, null);
INSERT INTO `goods_property` VALUES ('2', '尺码', null, null, null);
INSERT INTO `goods_property` VALUES ('3', '网络类型', null, null, null);

-- ----------------------------
-- Table structure for goods_property_value
-- ----------------------------
DROP TABLE IF EXISTS `goods_property_value`;
CREATE TABLE `goods_property_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `goods_property_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updaed_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_property_value
-- ----------------------------
INSERT INTO `goods_property_value` VALUES ('2', '黑色', '1', '2018-08-11 22:06:39', '2018-08-11 22:06:45');
INSERT INTO `goods_property_value` VALUES ('3', '红色', '1', null, null);
INSERT INTO `goods_property_value` VALUES ('4', 'L', '2', null, null);
INSERT INTO `goods_property_value` VALUES ('5', '4G', '3', '2018-09-26 21:48:51', null);
INSERT INTO `goods_property_value` VALUES ('6', '3g', '3', null, null);

-- ----------------------------
-- Table structure for goods_unit
-- ----------------------------
DROP TABLE IF EXISTS `goods_unit`;
CREATE TABLE `goods_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_unit
-- ----------------------------
INSERT INTO `goods_unit` VALUES ('1', '台', null, '2018-09-14 22:28:24', '2018-09-14 22:28:26');

-- ----------------------------
-- Table structure for sms_send_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_send_log`;
CREATE TABLE `sms_send_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_info_id` int(11) DEFAULT '0',
  `mobile` varchar(100) NOT NULL,
  `content` varchar(100) DEFAULT NULL,
  `send_status` tinyint(1) DEFAULT '1',
  `send_remark` varchar(100) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_send_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_admin
-- ----------------------------
DROP TABLE IF EXISTS `system_admin`;
CREATE TABLE `system_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) NOT NULL,
  `real_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `encrypt` varchar(100) NOT NULL,
  `last_login_ip` varchar(100) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  `nack_name` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `admin_type` tinyint(2) DEFAULT '0',
  `disabled_flag` tinyint(1) DEFAULT '0',
  `mail` varchar(100) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT '0',
  `login_error` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_admin
-- ----------------------------
INSERT INTO `system_admin` VALUES ('1', 'admin', null, '9b05732f4b50b86a18e48b982c83bba3', 'd6f6ccb41d6403f2adeb98d6db97814a4b07594c', '0:0:0:0:0:0:0:1', '18296640717', null, null, null, '0', '0', null, '2018-10-23 22:15:18', '55', '0');
INSERT INTO `system_admin` VALUES ('2', 'taoge', null, '9b05732f4b50b86a18e48b982c83bba3', 'd6f6ccb41d6403f2adeb98d6db97814a4b07594c', null, '18296640717', null, null, null, '0', '0', null, null, '0', '0');

-- ----------------------------
-- Table structure for system_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `system_admin_role`;
CREATE TABLE `system_admin_role` (
  `id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_admin_role
-- ----------------------------
INSERT INTO `system_admin_role` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oper_name` varchar(100) DEFAULT '',
  `oper_time` datetime DEFAULT NULL,
  `oper_ip` varchar(100) DEFAULT '',
  `oper_desc` varchar(1024) DEFAULT '',
  `login_type` tinyint(4) DEFAULT '0',
  `platform_type` tinyint(4) DEFAULT '0',
  `user_type` tinyint(4) DEFAULT '0',
  `user_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_log
-- ----------------------------
INSERT INTO `system_log` VALUES ('1', 'admin', '2018-08-05 12:42:42', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('2', 'admin', '2018-08-05 14:15:07', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('3', 'admin', '2018-08-05 14:19:51', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('4', 'admin', '2018-08-05 14:20:39', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('5', 'admin', '2018-08-11 21:40:20', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('6', 'admin', '2018-08-24 22:20:17', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('7', 'admin', '2018-08-24 22:46:44', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('8', 'admin', '2018-08-25 20:49:50', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('9', 'admin', '2018-08-26 21:29:53', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('10', 'admin', '2018-08-26 22:17:01', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('11', 'admin', '2018-09-01 22:32:52', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('12', 'admin', '2018-09-02 22:26:35', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('13', 'admin', '2018-09-14 22:10:04', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('14', 'admin', '2018-09-14 22:20:00', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('15', 'admin', '2018-09-14 22:21:03', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('16', 'admin', '2018-09-14 23:02:07', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('17', 'admin', '2018-09-14 23:08:55', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('18', 'admin', '2018-09-15 11:28:27', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('19', 'admin', '2018-09-15 11:35:33', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('20', 'admin', '2018-09-16 22:13:05', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('21', 'admin', '2018-09-16 22:14:14', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('22', 'admin', '2018-09-16 22:25:20', '127.0.0.1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('23', 'admin', '2018-09-17 20:09:25', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('24', 'admin', '2018-09-17 21:34:28', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('25', 'admin', '2018-09-17 21:38:18', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('26', 'admin', '2018-09-17 21:40:15', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('27', 'admin', '2018-09-18 22:18:52', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('28', 'admin', '2018-09-18 22:55:41', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('29', 'admin', '2018-09-20 22:13:18', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('30', 'admin', '2018-09-24 23:24:06', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('31', 'admin', '2018-09-25 20:43:38', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('32', 'admin', '2018-09-25 21:50:23', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('33', 'admin', '2018-09-25 21:51:56', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('34', 'admin', '2018-09-25 22:55:05', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('35', 'admin', '2018-09-26 21:47:07', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('36', 'admin', '2018-09-26 22:02:59', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('37', 'admin', '2018-09-26 22:05:15', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('38', 'admin', '2018-09-26 22:08:54', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('39', 'admin', '2018-09-26 22:43:00', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('40', 'admin', '2018-10-06 18:28:38', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('41', 'admin', '2018-10-06 22:15:16', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('42', 'admin', '2018-10-06 22:24:57', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('43', 'admin', '2018-10-06 22:30:22', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('44', 'admin', '2018-10-07 09:46:12', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('45', 'admin', '2018-10-07 09:56:42', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('46', 'admin', '2018-10-07 09:57:23', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('47', 'admin', '2018-10-07 16:43:00', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('48', 'admin', '2018-10-08 21:17:41', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('49', 'admin', '2018-10-08 23:05:58', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');
INSERT INTO `system_log` VALUES ('50', 'admin', '2018-10-23 22:15:19', '0:0:0:0:0:0:0:1', '登录系统', '1', '0', '0', '1');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) DEFAULT '0',
  `url` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `is_menu` tinyint(1) NOT NULL DEFAULT '1',
  `icon` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu
-- ----------------------------

-- ----------------------------
-- Table structure for system_region
-- ----------------------------
DROP TABLE IF EXISTS `system_region`;
CREATE TABLE `system_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `region_code` varchar(100) NOT NULL COMMENT '地区code',
  `region_level` tinyint(2) NOT NULL COMMENT '地区级别 1:省 2:市 3:区',
  `region_name` varchar(100) NOT NULL COMMENT '地区名称',
  `alias_name` varchar(100) DEFAULT '' COMMENT '地区别名',
  `city_flag` tinyint(1) DEFAULT '0' COMMENT '是否市辖区 0-否 1-是',
  `parent_id` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_region
-- ----------------------------

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `super_flag` tinyint(1) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', 'test', null, '1', null, null);

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(100) NOT NULL,
  `role_id` int(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) NOT NULL,
  `code` varchar(100) NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL,
  `disabled_flag` tinyint(1) DEFAULT '0',
  `encrypt` varchar(100) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
