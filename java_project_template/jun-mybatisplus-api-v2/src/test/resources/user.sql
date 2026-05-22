/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50529
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 50529
 File Encoding         : 65001

 Date: 08/01/2020 15:53:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '删除标志0未删 1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '9dc818b4bca3baa7d230fbf96c919638', '土豆', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (2, '2@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-2', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (3, '3@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-3', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (4, '4@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-4', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (5, '5@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-5', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (6, '6@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-6', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (7, '7@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-7', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (8, '8@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-8', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (9, '9@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-9', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (10, '10@qq.com', '9dc818b4bca3baa7d230fbf96c919638', '土豆-10', 1, NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
