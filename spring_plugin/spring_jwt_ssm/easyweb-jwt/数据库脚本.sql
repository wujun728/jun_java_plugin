/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.34-log : Database - easyweb-jwt
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`easyweb-jwt` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `easyweb-jwt`;

/*Table structure for table `oauth_token` */

DROP TABLE IF EXISTS `oauth_token`;

CREATE TABLE `oauth_token` (
  `token_id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(128) NOT NULL,
  `user_id` varchar(128) NOT NULL,
  `permissions` varchar(512) DEFAULT NULL,
  `roles` varchar(512) DEFAULT NULL,
  `refresh_token` varchar(128) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_token` */

/*Table structure for table `oauth_token_key` */

DROP TABLE IF EXISTS `oauth_token_key`;

CREATE TABLE `oauth_token_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token_key` varchar(128) NOT NULL COMMENT '生成token时的key',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `oauth_token_key` */

/*Table structure for table `sys_authorities` */

DROP TABLE IF EXISTS `sys_authorities`;

CREATE TABLE `sys_authorities` (
  `authority` varchar(128) NOT NULL COMMENT '授权标识',
  `authority_name` varchar(128) NOT NULL COMMENT '名称',
  `parent_name` varchar(128) DEFAULT NULL COMMENT '模块',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限';

/*Data for the table `sys_authorities` */

insert  into `sys_authorities`(`authority`,`authority_name`,`parent_name`,`sort`,`create_time`) values ('delete:/v1/authorities/role','移除角色权限','权限管理',3,'2019-03-29 13:07:48'),('delete:/v1/menu/{id}','删除菜单','菜单管理',8,'2019-03-29 13:07:48'),('delete:/v1/role/{id}','删除角色','角色管理',12,'2019-03-29 13:07:48'),('delete:/v1/user/{id}','删除用户','用户管理',22,'2019-03-29 13:07:48'),('get:/v1/authorities','查询所有权限','权限管理',1,'2019-03-29 13:07:48'),('get:/v1/menu','查询所有菜单','菜单管理',5,'2019-03-29 13:07:48'),('get:/v1/role','查询所有角色','角色管理',9,'2019-03-29 13:07:48'),('get:/v1/user','查询所有用户','用户管理',13,'2019-03-29 13:07:48'),('get:/v1/user/info','获取个人信息','个人信息',16,'2019-03-29 13:07:48'),('get:/v1/user/menu','获取所有菜单','个人信息',18,'2019-03-29 13:07:48'),('post:/v1/authorities/role','给角色添加权限','权限管理',2,'2019-03-29 13:07:48'),('post:/v1/authorities/sync','同步权限','权限管理',4,'2019-03-29 13:07:48'),('post:/v1/menu','添加菜单','菜单管理',6,'2019-03-29 13:07:48'),('post:/v1/role','添加角色','角色管理',10,'2019-03-29 13:07:48'),('post:/v1/user','添加用户','用户管理',14,'2019-03-29 13:07:48'),('post:/v1/user/login','用户登录','个人信息',17,'2019-03-29 13:07:48'),('put:/v1/menu','修改菜单','菜单管理',7,'2019-03-29 13:07:48'),('put:/v1/role','修改角色','角色管理',11,'2019-03-29 13:07:48'),('put:/v1/user','修改用户','用户管理',15,'2019-03-29 13:07:48'),('put:/v1/user/psw','修改自己密码','用户管理',19,'2019-03-29 13:07:48'),('put:/v1/user/psw/{id}','重置密码','用户管理',20,'2019-03-29 13:07:48'),('put:/v1/user/state','修改用户状态','用户管理',21,'2019-03-29 13:07:48');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '父级id',
  `menu_name` varchar(200) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单url',
  `menu_icon` varchar(200) DEFAULT NULL COMMENT '菜单图标',
  `sort_number` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `authority` varchar(200) DEFAULT NULL COMMENT '对应权限',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`parent_id`,`menu_name`,`menu_url`,`menu_icon`,`sort_number`,`authority`,`create_time`,`update_time`) values (1,-1,'系统管理','','layui-icon layui-icon-set',1,'','2019-03-26 13:19:08','2019-03-26 16:18:08'),(2,1,'用户管理','#/system/user','',3,'get:/v1/user','2019-03-26 16:21:17','2019-03-29 13:59:13'),(3,1,'角色管理','#/system/role','',4,'get:/v1/role','2019-03-26 16:22:03','2019-03-29 13:59:13'),(4,1,'权限管理','#/system/authorities','',5,'get:/v1/authorities','2019-03-26 16:22:28','2019-03-29 13:59:14'),(5,1,'菜单管理','#/system/menu','',6,'get:/v1/menu','2019-03-26 16:28:14','2019-03-29 13:59:14'),(6,-1,'系统功能','','layui-icon layui-icon-engine',7,'','2019-03-28 16:44:19','2019-03-29 13:59:15'),(7,6,'Druid监控','#/tpl/iframe/id=druid','',8,'','2019-03-28 16:45:34','2019-03-29 13:59:19'),(8,6,'接口文档','#/tpl/iframe/id=swagger','',9,'','2019-03-28 16:47:56','2019-03-29 13:59:20');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `comments` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`comments`,`create_time`,`update_time`) values (1,'管理员','管理员','2018-12-19 23:11:29','2018-12-19 23:11:29'),(2,'普通用户','普通用户','2018-12-19 23:12:09','2018-12-19 23:12:09');

/*Table structure for table `sys_role_authorities` */

DROP TABLE IF EXISTS `sys_role_authorities`;

CREATE TABLE `sys_role_authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `authority` varchar(128) NOT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_pm` (`authority`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  CONSTRAINT `sys_role_authorities_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限';

/*Data for the table `sys_role_authorities` */

insert  into `sys_role_authorities`(`id`,`role_id`,`authority`,`create_time`) values (1,1,'delete:/v1/role/{id}','2018-12-19 23:11:36'),(2,1,'get:/v1/menu','2019-03-29 12:49:52'),(3,1,'post:/v1/menu','2019-03-29 12:49:53'),(4,1,'put:/v1/menu','2019-03-29 12:49:53'),(5,1,'delete:/v1/user/{id}','2019-03-29 12:50:04'),(6,1,'delete:/v1/menu/{id}','2019-03-29 12:50:44'),(7,1,'get:/v1/user','2019-03-29 12:50:45'),(8,1,'get:/v1/user/info','2019-03-29 13:47:08'),(9,1,'post:/v1/user/login','2019-03-29 13:47:08'),(10,1,'get:/v1/user/menu','2019-03-29 13:47:08'),(11,1,'delete:/v1/authorities/role','2018-12-26 10:32:09'),(12,1,'put:/v1/user/state','2018-12-19 23:11:50'),(13,1,'post:/v1/authorities/sync','2018-12-19 23:11:42'),(14,1,'get:/v1/role','2018-12-19 23:11:40'),(15,1,'get:/v1/authorities','2018-12-19 23:11:37'),(16,1,'post:/v1/role','2018-12-19 23:11:43'),(17,1,'post:/v1/user','2018-12-19 23:11:44'),(18,1,'put:/v1/role','2018-12-19 23:11:46'),(19,1,'put:/v1/user','2018-12-19 23:11:46'),(20,1,'put:/v1/user/psw','2018-12-19 23:11:47'),(21,1,'put:/v1/user/psw/{id}','2018-12-19 23:11:47'),(22,1,'post:/v1/authorities/role','2018-12-19 23:11:41'),(23,2,'get:/v1/user/info','2019-03-29 13:47:39'),(24,2,'get:/v1/menu','2019-03-29 13:07:36'),(25,2,'get:/v1/user','2019-03-29 12:50:56'),(26,2,'get:/v1/authorities','2018-12-19 23:12:35'),(27,2,'get:/v1/role','2018-12-19 23:13:06'),(28,2,'get:/v1/user/menu','2019-03-29 13:47:40');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `email` varchar(256) DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(1) DEFAULT '0' COMMENT '邮箱是否验证，0未验证，1已验证',
  `true_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`username`),
  KEY `FK_sys_user` (`true_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`username`,`password`,`nick_name`,`avatar`,`sex`,`phone`,`email`,`email_verified`,`true_name`,`id_card`,`birthday`,`department_id`,`state`,`create_time`,`update_time`) values (1,'admin','21232f297a57a5a743894a0e4a801fc3','管理员',NULL,'男','12345678901',NULL,0,NULL,NULL,NULL,NULL,0,'2018-12-19 23:30:05','2019-03-29 13:06:48'),(2,'user01','e10adc3949ba59abbe56e057f20f883e','用户01',NULL,'男','12345678901',NULL,0,NULL,NULL,NULL,NULL,0,'2018-12-19 23:31:25','2019-03-29 13:54:53');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_user_role` (`user_id`),
  KEY `FK_sys_user_role_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`create_time`) values (1,1,1,'2018-12-19 23:30:06'),(2,2,2,'2019-03-29 13:13:35');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
