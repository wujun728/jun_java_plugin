/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100432 (10.4.32-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : db_maku

 Target Server Type    : MySQL
 Target Server Version : 100432 (10.4.32-MariaDB)
 File Encoding         : 65001

 Date: 15/09/2025 23:42:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for online_table
-- ----------------------------
DROP TABLE IF EXISTS `online_table`;
CREATE TABLE `online_table`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
  `comments` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表描述',
  `form_layout` tinyint NULL DEFAULT NULL COMMENT '表单布局',
  `tree` tinyint NULL DEFAULT NULL COMMENT '是否树  0：否   1：是',
  `tree_pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树父id',
  `tree_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树展示列',
  `table_type` tinyint NULL DEFAULT NULL COMMENT '表类型  0：单表',
  `status` tinyint NULL DEFAULT NULL COMMENT '是否更新  0：否   1：是',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Online表单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of online_table
-- ----------------------------

-- ----------------------------
-- Table structure for online_table_column
-- ----------------------------
DROP TABLE IF EXISTS `online_table_column`;
CREATE TABLE `online_table_column`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `comments` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段描述',
  `length` int NOT NULL COMMENT '字段长度',
  `point_length` int NOT NULL COMMENT '小数点',
  `default_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `column_pk` tinyint NULL DEFAULT NULL COMMENT '字段主键 0：否  1：是',
  `column_null` tinyint NULL DEFAULT NULL COMMENT '字段为空 0：否  1：是',
  `form_item` tinyint NULL DEFAULT NULL COMMENT '表单项 0：否  1：是',
  `form_required` tinyint NULL DEFAULT NULL COMMENT '表单必填 0：否  1：是',
  `form_input` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单控件',
  `form_default` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单控件默认值',
  `form_dict` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单字典',
  `grid_item` tinyint NULL DEFAULT NULL COMMENT '列表项 0：否  1：是',
  `grid_sort` tinyint NULL DEFAULT NULL COMMENT '列表排序 0：否  1：是',
  `query_item` tinyint NULL DEFAULT NULL COMMENT '查询项 0：否  1：是',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询方式',
  `query_input` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询控件',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Online表单字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of online_table_column
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
