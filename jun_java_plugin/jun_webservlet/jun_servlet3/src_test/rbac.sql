/*
Navicat MySQL Data Transfer

Source Server         : localMysql
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : rbac

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-08-05 15:59:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authName` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `moduleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `moduleId` (`moduleId`),
  CONSTRAINT `auth_ibfk_1` FOREIGN KEY (`moduleId`) REFERENCES `module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of auth
-- ----------------------------
INSERT INTO `auth` VALUES ('1', '查看人员', 'employeeServlet', 'list', '1');
INSERT INTO `auth` VALUES ('2', '删除人员', 'employeeServlet', 'delete', '1');
INSERT INTO `auth` VALUES ('3', '分配角色', 'employeeServlet', 'assignRole', '1');
INSERT INTO `auth` VALUES ('4', '实际授权', 'employeeServlet', 'assign', '1');
INSERT INTO `auth` VALUES ('5', '查看角色', 'roleServlet', 'list', '2');
INSERT INTO `auth` VALUES ('6', '删除角色', 'roleServlet', 'delete', '2');
INSERT INTO `auth` VALUES ('7', '分配权限', 'roleServlet', 'assignAuth', '2');
INSERT INTO `auth` VALUES ('8', '角色授权', 'roleServlet', 'assign', '2');
INSERT INTO `auth` VALUES ('9', '添加角色', 'roleServlet', 'add', '2');
INSERT INTO `auth` VALUES ('10', '保存角色', 'roleServlet', 'save', '2');
INSERT INTO `auth` VALUES ('11', '查看权限', 'authServlet', 'list', '3');
INSERT INTO `auth` VALUES ('12', '添加单个权限', 'authServlet', 'add', '3');
INSERT INTO `auth` VALUES ('13', '保存权限', 'authServlet', 'save', '3');
INSERT INTO `auth` VALUES ('14', '搜索员工', 'employeeServlet', 'search', '1');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `empName` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', 'root', '1', '广东');
INSERT INTO `employee` VALUES ('2', '2', '2', '2');
INSERT INTO `employee` VALUES ('3', '3', '3', '3');

-- ----------------------------
-- Table structure for emp_role
-- ----------------------------
DROP TABLE IF EXISTS `emp_role`;
CREATE TABLE `emp_role` (
  `roleId` int(11) DEFAULT NULL,
  `empId` int(11) DEFAULT NULL,
  KEY `empId` (`empId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `emp_role_ibfk_2` FOREIGN KEY (`empId`) REFERENCES `employee` (`id`),
  CONSTRAINT `emp_role_ibfk_3` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of emp_role
-- ----------------------------
INSERT INTO `emp_role` VALUES ('1', '1');
INSERT INTO `emp_role` VALUES ('1', '3');

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` int(11) DEFAULT NULL,
  `moduleName` varchar(50) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('1', '员工管理');
INSERT INTO `module` VALUES ('2', '角色管理');
INSERT INTO `module` VALUES ('3', '权限管理');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');

-- ----------------------------
-- Table structure for role_auth
-- ----------------------------
DROP TABLE IF EXISTS `role_auth`;
CREATE TABLE `role_auth` (
  `roleId` int(11) DEFAULT NULL,
  `authId` int(11) DEFAULT NULL,
  KEY `roleId` (`roleId`),
  KEY `authId` (`authId`),
  CONSTRAINT `role_auth_ibfk_3` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `role_auth_ibfk_4` FOREIGN KEY (`authId`) REFERENCES `auth` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_auth
-- ----------------------------
INSERT INTO `role_auth` VALUES ('1', '1');
INSERT INTO `role_auth` VALUES ('1', '2');
INSERT INTO `role_auth` VALUES ('1', '3');
INSERT INTO `role_auth` VALUES ('1', '4');
INSERT INTO `role_auth` VALUES ('1', '5');
INSERT INTO `role_auth` VALUES ('1', '6');
INSERT INTO `role_auth` VALUES ('1', '7');
INSERT INTO `role_auth` VALUES ('1', '8');
INSERT INTO `role_auth` VALUES ('1', '9');
INSERT INTO `role_auth` VALUES ('1', '10');
INSERT INTO `role_auth` VALUES ('1', '11');
INSERT INTO `role_auth` VALUES ('1', '12');
INSERT INTO `role_auth` VALUES ('1', '13');
