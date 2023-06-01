/*
 Navicat Premium Data Transfer

 Source Server         : pass-pbi-manager-service
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 10.138.228.199:32938
 Source Schema         : manager-manager-service

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 10/11/2020 13:04:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `dept_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号(规则：父级关系编码+自己的编码)',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父级id',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '为了维护更深层级关系',
  `dept_manager_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', 'D000001', '总公司', '0', 1, 'D000001', NULL, '小李', '13888888888', '2019-11-07 22:43:33', NULL, 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址URL',
  `target` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'a target属性:_self _blank',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `unable_flag` tinyint(4) NULL DEFAULT NULL COMMENT '状态1:正常 0：禁用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `attr1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1324273040675381249', '权限管理', NULL, NULL, NULL, NULL, '0', 1, 1, 1, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1324273216794206209', '菜单管理', '', '#cosmo-icon-detail', '/menu', NULL, '1324273040675381249', 1, 2, 1, '2020-11-05 16:51:53', '2020-11-05 16:51:53', 1, '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1324273363561291777', '角色管理', '', '#cosmo-icon-switchuser', '/roleList', '', '1324273040675381249', 2, 2, 1, '2020-11-05 16:52:28', '2020-11-09 12:28:39', 1, '1', '773918606676197376', NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1324273497166651393', '用户管理', '', '#cosmo-icon-solution', '/userList', '', '1324273040675381249', 3, 2, 1, '2020-11-05 16:53:00', '2020-11-09 15:25:10', 1, '1', '773211901516578816', NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1325677888968925186', '删除角色', 'sys:role:deleted', '', '', NULL, '1324273363561291777', NULL, 3, 1, '2020-11-09 13:53:33', '2020-11-09 13:53:33', 1, '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1325678863528042497', '菜单删除', 'sys:permission:deleted', '', '', '', '1324273216794206209', NULL, 3, 1, '2020-11-09 13:57:26', '2020-11-09 15:15:23', 1, '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission`(`id`, `name`, `perms`, `icon`, `url`, `target`, `pid`, `order_num`, `type`, `unable_flag`, `create_time`, `update_time`, `deleted`, `create_id`, `update_id`, `attr1`, `attr2`, `attr3`, `remark`) VALUES ('1328609302130327553', '字典管理', '', '#cosmo-icon-gongxiang', '', NULL, '0', 1, 1, 1, '2020-11-17 16:01:57', '2020-11-17 16:01:57', 1, '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission`(`id`, `name`, `perms`, `icon`, `url`, `target`, `pid`, `order_num`, `type`, `unable_flag`, `create_time`, `update_time`, `deleted`, `create_id`, `update_id`, `attr1`, `attr2`, `attr3`, `remark`) VALUES ('1328609814758162433', '字典配置', '', '#cosmo-icon-chixuchuangxin', '/dictionaries', '', '1328609302130327553', -1, 2, 1, '2020-11-17 16:03:59', '2020-11-17 16:06:32', 1, '1', '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unable_flag` tinyint(4) NULL DEFAULT NULL COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `attr1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2020-11-09 14:00:22', 1, NULL, '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1325679603776942081', '1', '1324273216794206209', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1325679603793719298', '1', '1324273363561291777', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1325679603810496514', '1', '1324273497166651393', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1325679603814690817', '1', '1325677888968925186', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1325679603823079426', '1', '1325678863528042497', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1325679603827273730', '1', '1324273040675381249', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1328609302130327530', '1', '1328609302130327553', '2020-11-09 14:00:22');
INSERT INTO `sys_role_permission` VALUES ('1328609814758162432', '1', '1328609814758162433', '2020-11-09 14:00:22');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名称',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱(唯一)',
  `unable_flag` tinyint(4) NULL DEFAULT NULL COMMENT '账户状态(0禁用1启用 )',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(1.男 2.女)',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint(4) NULL DEFAULT NULL COMMENT '创建来源(1.web 2.uuc )',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '324ce32d86224b00a02b', '2102b59a75ab87616b62d0b9432569d0', '13888888888', '1', '超级管理员', '超级管理员', 'xxxxxx@163.com', 1, 2, 1, '1', '1', 1, '2019-09-22 19:38:05', '2020-03-18 09:15:22');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '2020-03-19 02:23:13');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标签名',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '数据值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '描述',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序（升序）',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '父级id',
  `parent_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '父级编码',
  `attr` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩充字段 - 自定义数据格式  String or JSONString',
  `create_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `INDEX_SYSTEM_DICT_DICT_TYPE`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1327078520310112258', '性别', 'sex', 'sex', '1', 1, '0', '0', NULL, '1', '2020-11-13 10:39:10', NULL, '2020-11-13 10:39:10', 1);
INSERT INTO `sys_dict` VALUES ('1327078627994673154', '男', '1', 'sex', '1', 1, '1327078520310112258', 'sex', NULL, '1', '2020-11-13 10:39:36', NULL, '2020-11-13 10:39:36', 1);
INSERT INTO `sys_dict` VALUES ('1327078652292276226', '女', '2', 'sex', '1', 1, '1327078520310112258', 'sex', NULL, '1', '2020-11-13 10:39:41', NULL, '2020-11-13 10:39:41', 1);

SET FOREIGN_KEY_CHECKS = 1;
