/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Version : 50616
 Source Host           : localhost
 Source Database       : springwind

 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 05/11/2016 22:38:17 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID',
  `title` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `type` smallint(6) NOT NULL DEFAULT '0' COMMENT '类型 0、菜单 1、功能',
  `state` smallint(6) NOT NULL DEFAULT '0' COMMENT '状态 0、正常 1、禁用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `permCode` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '权限编码',
  `icon` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `description` varchar(80) COLLATE utf8_bin NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

-- ----------------------------
--  Records of `permission`
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES ('2', '0', '权限管理', '0', '0', '2', '/perm/', '2000', 'fa fa-eye', '权限管理'), ('1', '0', '功能管理', '0', '0', '1', '/sys/', '1000', 'fa fa-magic', '功能管理'), ('3', '0', '系统监控', '0', '0', '3', '/monitor/', '3000', 'fa fa-bar-chart-o', '系统监控'), ('4', '0', '操作日志', '0', '0', '4', '/log/', '4000', 'fa fa-bug', '操作日志'), ('11', '1', '发送邮件测试', '0', '0', '1', '/sys/mail/send.html', '1001', null, '发送邮件测试'), ('12', '1', 'Echarts 测试', '0', '0', '2', '/sys/echarts/map.html', '1002', null, '暂无2'), ('21', '2', '用户管理', '0', '0', '1', '/perm/user/list.html', '2001', null, '用户管理'), ('22', '2', '角色管理', '0', '0', '2', '/perm/role/list.html', '2002', null, '角色管理'), ('23', '2', '权限管理', '0', '0', '3', '/perm/permission/list.html', '2003', null, '权限管理'), ('31', '3', '警告列表', '0', '0', '1', 'abc.html', '3001', null, '警告列表'), ('32', '3', '实时监控', '0', '0', '2', '/monitor/realTime.html', '3002', null, '实时监控'), ('41', '4', '日志列表', '0', '0', '1', '/log/list.html', '4001', null, '日志列表');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '角色',
  `sort` smallint(6) NOT NULL DEFAULT '0' COMMENT '排序',
  `description` varchar(60) NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', '管理员', '1', '系统管理员'), ('2', '普通会员', '2', '普通会员');
COMMIT;

-- ----------------------------
--  Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `rid` bigint(20) NOT NULL COMMENT '角色ID',
  `pid` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';

-- ----------------------------
--  Records of `role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3'), ('4', '1', '4'), ('5', '1', '11'), ('6', '1', '12'), ('7', '1', '21'), ('8', '1', '22'), ('9', '1', '23'), ('10', '1', '31'), ('11', '1', '32'), ('12', '1', '41'), ('13', '2', '2'), ('14', '2', '21'), ('15', '2', '3'), ('16', '2', '31'), ('17', '2', '32');
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `content` varchar(600) NOT NULL DEFAULT '' COMMENT '日志内容',
  `operation` varchar(250) DEFAULT NULL COMMENT '用户操作',
  `crTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=424538725716852737 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
--  Records of `sys_log`
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES ('423827573609332736', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=abc&,[IP]:10.10.10.10', '登录', '2016-05-09 21:55:10'), ('423827889272651776', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-09 21:56:23'), ('423828945075437568', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=acbbb&repassword=acbbb&loginName=acbbb&,[IP]:10.10.10.10', '登录', '2016-05-09 22:00:37'), ('423828960116211712', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:insertSelective,[参数]:password=acbbb&repassword=acbbb&loginName=acbbb&,[IP]:10.10.10.10', '添加用户', '2016-05-09 22:00:40'), ('423829494923526144', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-09 22:02:48'), ('423829536409387008', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-09 22:02:58'), ('423829555678019584', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-09 22:03:02'), ('423829597029662720', '1', '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:deleteUser,[参数]:null,[IP]:10.10.10.10', '删除用户', '2016-05-09 22:03:12'), ('423829597029662721', '1', '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:deleteUser,[参数]:null,[IP]:10.10.10.10', '删除用户', '2016-05-09 22:03:12'), ('424204895713755136', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&,[IP]:10.10.10.10', '登录', '2016-05-10 22:54:30'), ('424204946615828480', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-10 22:54:42'), ('424204971169284096', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-10 22:54:48'), ('424212917953495040', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&,[IP]:10.10.10.10', '登录', '2016-05-10 23:26:23'), ('424213509627183104', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&,[IP]:10.10.10.10', '登录', '2016-05-10 23:28:44'), ('424213551029157888', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-10 23:28:54'), ('424538597211766784', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=abc&,[IP]:10.10.10.10', '登录', '2016-05-11 21:00:31'), ('424538650328432640', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-11 21:00:44'), ('424538669534150656', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=abc&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-11 21:00:48'), ('424538690912518144', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&loginName=admin&,[IP]:10.10.10.10', '登录', '2016-05-11 21:00:53'), ('424538709661057024', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&,[IP]:10.10.10.10', '登录', '2016-05-11 21:00:58'), ('424538725716852736', null, '[类名]:com.baomidou.springwind.service.impl.UserServiceImpl,[方法]:selectByLoginName,[参数]:password=123&,[IP]:10.10.10.10', '登录', '2016-05-11 21:01:02');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `loginName` varchar(30) NOT NULL COMMENT '登录名称',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `type` smallint(6) NOT NULL DEFAULT '0' COMMENT '0、普通用户 1、管理员',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '0、禁用 1、正常',
  `crTime` datetime NOT NULL COMMENT '创建时间',
  `lastTime` datetime NOT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', 'b9140469ac5d2a70d86a583e9095ad6f', null, '0', '1', '2016-04-17 18:52:26', '2016-04-17 18:52:26'), ('2', 'test', 'd227f9edb541169068b11221869a014a', null, '0', '1', '2016-04-14 18:22:58', '2016-04-14 18:22:58'), ('415112286823251968', 'abc', 'c4c99e1a5bd140d138ad0863bf59acb3', null, '0', '1', '2016-04-15 20:43:43', '2016-04-15 20:43:43'), ('415112325381488640', 'def', '94e5398201cd9452b1e667044debcd10', null, '0', '1', '2016-04-15 20:43:53', '2016-04-15 20:43:53');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `rid` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1', '1', '1'), ('2', '2', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
