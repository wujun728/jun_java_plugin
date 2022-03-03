/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50719
Source Host           : 127.0.0.1:3306
Source Database       : springrain

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-11-02 17:43:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_auditlog_history_2017
-- ----------------------------
DROP TABLE IF EXISTS `t_auditlog_history_2017`;
CREATE TABLE `t_auditlog_history_2017` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `operationType` varchar(50) DEFAULT NULL COMMENT '操作类型',
  `operatorName` varchar(50) DEFAULT NULL COMMENT '操作人姓名',
  `preValue` text COMMENT '旧值',
  `curValue` text COMMENT '新值',
  `operationTime` datetime DEFAULT NULL COMMENT '操作时间',
  `operationClass` varchar(500) DEFAULT NULL COMMENT '操作类',
  `operationClassID` varchar(50) DEFAULT NULL COMMENT '记录ID',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作记录';

-- ----------------------------
-- Records of t_auditlog_history_2017
-- ----------------------------

-- ----------------------------
-- Table structure for t_auditlog_history_2018
-- ----------------------------
DROP TABLE IF EXISTS `t_auditlog_history_2018`;
CREATE TABLE `t_auditlog_history_2018` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `operationType` varchar(50) DEFAULT NULL COMMENT '操作类型',
  `operatorName` varchar(50) DEFAULT NULL COMMENT '操作人姓名',
  `preValue` text COMMENT '旧值',
  `curValue` text COMMENT '新值',
  `operationTime` datetime DEFAULT NULL COMMENT '操作时间',
  `operationClass` varchar(500) DEFAULT NULL COMMENT '操作类',
  `operationClassID` varchar(50) DEFAULT NULL COMMENT '记录ID',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作记录';

-- ----------------------------
-- Records of t_auditlog_history_2018
-- ----------------------------

-- ----------------------------
-- Table structure for t_dic_data
-- ----------------------------
DROP TABLE IF EXISTS `t_dic_data`;
CREATE TABLE `t_dic_data` (
  `id` varchar(50) NOT NULL,
  `name` varchar(60) NOT NULL COMMENT '名称',
  `code` varchar(60) DEFAULT NULL COMMENT '编码',
  `pid` varchar(50) DEFAULT NULL COMMENT '父ID',
  `sortno` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(2000) DEFAULT NULL COMMENT '描述',
  `active` int(11) DEFAULT '1' COMMENT '是否有效(0否,1是)',
  `typekey` varchar(20) DEFAULT NULL COMMENT '类型',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共字典';

-- ----------------------------
-- Records of t_dic_data
-- ----------------------------
INSERT INTO `t_dic_data` VALUES ('16b80bfb-f0ee-47a0-ba94-cc256abaed17', '专科', '', null, null, '', '1', 'xueli', null, null, null, null, null);
INSERT INTO `t_dic_data` VALUES ('7ed23330-5538-4943-8678-0c5a2121cf57', '高中', '', null, null, '', '1', 'xueli', null, null, null, null, null);
INSERT INTO `t_dic_data` VALUES ('936db407-ae1-45a7-a657-b60580e2a77a', '汉族', '101', null, null, '', '1', 'minzu', null, null, null, null, null);
INSERT INTO `t_dic_data` VALUES ('936db407-ae2-45a7-a657-b60580e2a77a', '回族', '', null, null, '', '1', 'minzu', null, null, null, null, null);
INSERT INTO `t_dic_data` VALUES ('936db407-ae3-45a7-a657-b60580e2a77a', '一级', '', null, null, '', '1', 'grade', null, null, null, null, null);
INSERT INTO `t_dic_data` VALUES ('936db407-ae4-45a7-a657-b60580e2a77a', '二级', '', null, null, '', '1', 'grade', null, null, null, null, null);
INSERT INTO `t_dic_data` VALUES ('d7d1744b-e69f-48d0-9760-b2eae6af039b', '本科', '', null, null, '', '1', 'xueli', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_fwlog_history_2017
-- ----------------------------
DROP TABLE IF EXISTS `t_fwlog_history_2017`;
CREATE TABLE `t_fwlog_history_2017` (
  `id` varchar(100) NOT NULL COMMENT 'ID',
  `startDate` datetime DEFAULT NULL COMMENT '访问时间',
  `strDate` varchar(100) DEFAULT NULL COMMENT '访问时间(毫秒)',
  `tomcat` varchar(50) DEFAULT NULL COMMENT 'Tomcat',
  `userCode` varchar(300) DEFAULT NULL COMMENT '登陆账号',
  `userName` varchar(200) DEFAULT NULL COMMENT '姓名',
  `sessionId` varchar(300) DEFAULT NULL COMMENT 'sessionId',
  `ip` varchar(200) DEFAULT NULL COMMENT 'IP',
  `fwUrl` varchar(3000) DEFAULT NULL COMMENT '访问菜单',
  `menuName` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `isqx` varchar(2) DEFAULT NULL COMMENT '是否有权限访问',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问日志';

-- ----------------------------
-- Records of t_fwlog_history_2017
-- ----------------------------
INSERT INTO `t_fwlog_history_2017` VALUES ('12cd4ecfa2a34df5bb4d3fcaf7af534f', '2017-10-30 15:09:44', '2017-10-30 15:09:43.0740', null, 'admin', '超级管理员', '722a55f0-1378-4519-9cf1-37a0c5846546', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('14c9fa53dceb4a77aa949782af47bb59', '2017-11-02 11:06:28', '2017-11-02 11:06:27.0653', null, 'admin', '超级管理员', 'd9a847d8-6777-42fd-8d7e-5bb11d2d6766', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('1d1b5b9e991b4393ba5acf4008e5cc6e', '2017-11-02 11:21:10', '2017-11-02 11:21:09.0621', null, 'admin', '超级管理员', 'ddddc89b-d6a2-4214-88e1-4490b9d4a631', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('2a4235155cce4a58b13a1947d9bfafe3', '2017-11-02 14:53:56', '2017-11-02 14:53:56.0347', null, 'admin', '超级管理员', 'aab4a7b8-7b81-43f1-a0f8-300e2cac71c9', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('307a4afbabb34a9494a74dd8618cdd71', '2017-11-02 12:02:59', '2017-11-02 12:02:58.0501', null, 'admin', '超级管理员', 'c89b29b7-31e8-4b08-aa6b-52c4491c080a', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('36d57b7d42cf443398fa36cf4341e1b8', '2017-11-02 14:17:51', '2017-11-02 14:17:50.0501', null, 'admin', '超级管理员', '3b0b4d1a-42d8-4027-8214-36cef2397d5c', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('39a8f8f6e07c4b38b0a3d0ab718e052f', '2017-10-30 16:54:31', '2017-10-30 16:54:30.0782', null, 'admin', '超级管理员', 'eca1821e-0b1e-4578-977a-bb39fbf88612', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('39e6b76262434264af2c41ed8db6b5cb', '2017-11-02 12:02:56', '2017-11-02 12:02:55.0612', null, 'admin', '超级管理员', 'c89b29b7-31e8-4b08-aa6b-52c4491c080a', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('3b21cccad95c402fae036e69fda416ff', '2017-11-01 23:41:35', '2017-11-01 23:41:35.0351', null, 'admin', '超级管理员', '69d2fca1-e7d7-4b85-bb8d-bbe6a3891114', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('52a0ba649aa84ccc9a1d1ce9a5f9f22d', '2017-11-02 00:13:32', '2017-11-02 00:13:31.0718', null, 'admin', '超级管理员', 'fc668252-1412-4bc3-a1b6-da00bf874580', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('5c83cc44e7b741039c144796d24ee8f1', '2017-10-30 16:45:24', '2017-10-30 16:45:23.0853', null, 'admin', '超级管理员', '2528450f-ebcc-4795-a73c-87fbc0a39b4d', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('614e246800a64754aa98f8828730f1c7', '2017-10-30 16:44:29', '2017-10-30 16:44:29.0145', null, 'admin', '超级管理员', 'c4d186a2-c7c7-4dfd-a07c-fbd08fcb43a3', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('74f24086f3dc4482b2fb185fb6c52c0a', '2017-10-24 15:51:29', '2017-10-24 15:51:29.0201', null, 'admin', '超级管理员', '97058259-fcb4-4c2a-8355-2d08fbe34dfc', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('7632a57bd1ed4687a85f7016d0d27ae1', '2017-11-01 23:30:55', '2017-11-01 23:30:54.0824', null, 'admin', '超级管理员', 'e1886967-9403-4505-95d9-d3eeb7abe5ba', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('787510ef92e44642ab2e10ff7070b45f', '2017-11-01 23:44:30', '2017-11-01 23:44:29.0924', null, 'admin', '超级管理员', 'f26f79a9-739c-437c-9776-7677f5d73f77', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('7e4a4abca224420bbd3dd29a9eed747c', '2017-11-02 11:19:45', '2017-11-02 11:19:44.0520', null, 'admin', '超级管理员', '0a363cbc-fcae-4585-8a7d-1645bc055c0a', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('82cf239d784c4cb4a00c4f0115284802', '2017-11-01 23:54:11', '2017-11-01 23:54:11.0482', null, 'admin', '超级管理员', '2e7e128c-b2be-4632-b154-e34b80c93653', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('82eaff6eb19c4bf5bfcff1bbc365ca88', '2017-11-02 00:01:45', '2017-11-02 00:01:44.0875', null, 'admin', '超级管理员', 'c1d0b882-305e-4bcd-8d1f-feb549f89098', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('833d2c66d46049e4a33cb0df5264fc2e', '2017-11-01 23:44:51', '2017-11-01 23:44:50.0791', null, 'admin', '超级管理员', 'b408d619-8837-4ea4-aefe-3b1a20e19f15', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('89208704491b4ee6b81553496d240437', '2017-11-01 23:40:37', '2017-11-01 23:40:36.0524', null, 'admin', '超级管理员', '7908168b-d23a-4520-8b45-5c35cb94b968', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('c2768be601db43c9b0c53f10d454f941', '2017-11-01 23:48:24', '2017-11-01 23:48:23.0659', null, 'admin', '超级管理员', '6e8a6fad-f82c-42a2-9d49-179af85b0592', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('c3576eeff5034d04a0148caa7e4c0355', '2017-11-02 17:15:18', '2017-11-02 17:15:18.0292', null, 'admin', '超级管理员', '58ade597-d8ab-4910-bb82-82f91762439f', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('c7173043c0504107b9617a460563f62d', '2017-11-02 17:40:25', '2017-11-02 17:40:24.0848', null, 'admin', '超级管理员', '0fbad026-9ead-4602-9ee8-9a4f57866995', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('cfa4bcc348b74f8c9541414b9a1b823e', '2017-11-02 00:00:12', '2017-11-02 00:00:12.0312', null, 'admin', '超级管理员', '6c463a69-42cf-4191-a97f-196f879ddae9', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('d442fa2f60a24a4aa3959604b9c4919d', '2017-11-02 17:13:36', '2017-11-02 17:13:36.0431', null, 'admin', '超级管理员', 'cc60746c-977b-4c77-9974-3881cbb16a03', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('ebf321170b9f48f48f6a30db231499c1', '2017-11-02 00:10:58', '2017-11-02 00:10:57.0901', null, 'admin', '超级管理员', '4477b8f9-fc61-4e39-82a0-d46b5c846625', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('f192e9c52ee9439a900e60aca420801a', '2017-11-01 23:56:02', '2017-11-01 23:56:02.0372', null, 'admin', '超级管理员', '3ab221f1-2812-4b58-819b-4e3560a39849', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('f75a8a27e0af4a32bf7e144aa6853032', '2017-11-02 11:20:29', '2017-11-02 11:20:28.0511', null, 'admin', '超级管理员', '3dbce40a-0fb1-40b2-b6bb-4b3e1a765f8b', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);
INSERT INTO `t_fwlog_history_2017` VALUES ('fb9879a32f724e49a163214a391e4a97', '2017-11-02 16:20:43', '2017-11-02 16:20:42.0550', null, 'admin', '超级管理员', '87399434-30c3-49a7-94eb-fac91a82987f', '127.0.0.1', 'http://127.0.0.1:8080/springrain/system/index', null, '否', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_fwlog_history_2018
-- ----------------------------
DROP TABLE IF EXISTS `t_fwlog_history_2018`;
CREATE TABLE `t_fwlog_history_2018` (
  `id` varchar(100) NOT NULL COMMENT 'ID',
  `startDate` datetime DEFAULT NULL COMMENT '访问时间',
  `strDate` varchar(100) DEFAULT NULL COMMENT '访问时间(毫秒)',
  `tomcat` varchar(50) DEFAULT NULL COMMENT 'Tomcat',
  `userCode` varchar(300) DEFAULT NULL COMMENT '登陆账号',
  `userName` varchar(200) DEFAULT NULL COMMENT '姓名',
  `sessionId` varchar(300) DEFAULT NULL COMMENT 'sessionId',
  `ip` varchar(200) DEFAULT NULL COMMENT 'IP',
  `fwUrl` varchar(3000) DEFAULT NULL COMMENT '访问菜单',
  `menuName` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `isqx` varchar(2) DEFAULT NULL COMMENT '是否有权限访问',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问日志';

-- ----------------------------
-- Records of t_fwlog_history_2018
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` varchar(50) NOT NULL,
  `name` varchar(500) DEFAULT NULL,
  `pid` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `pageurl` varchar(3000) DEFAULT NULL,
  `menuType` int(11) DEFAULT NULL COMMENT '0.功能按钮,1.导航菜单',
  `active` int(11) DEFAULT '1' COMMENT '是否有效(0否,1是)',
  `sortno` int(11) DEFAULT NULL,
  `menuIcon` varchar(100) DEFAULT NULL,
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('169815aca9cf41d390e7feb6629d361d', '栏目管理', 'business_manager', '', '/system/cms/channel/list', '1', '1', '4', '&#xe60a;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('3501ed1e23da40219b4f0fa5b7b2749a', '菜单列表', 't_menu_list', '', '/system/menu/list', '0', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('36ab9175f7b7423eadda974ba046be05', '修改密码', 't_user_list', '', '/system/user/modifiypwd/pre', '0', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('4adc1e3e3e244c0991d9dab66c63badf', '目录创建', 'f5203235547342f094a2c126ad4603bb', '', '/system/file/uploadDic', '0', '1', '2', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('5442d67bcdf8401da675bb5e09650f36', '测试用例', 'business_manager', '', null, '1', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('7cd0678633d5407dba2bd6a1553cadce', '文件下载', 'f5203235547342f094a2c126ad4603bb', '', '/system/file/downfile', '0', '1', '3', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('91779a0d304f4b91932b63dec87a8536', '角色管理-系统', 'system_manager', '', '/system/role/list/all', '1', '1', null, '&#xe60a;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('b94392f7b8714f64819c5c0222eb134a', '角色修改-系统', 't_role_list', '', '/system/role/update/admin', '0', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('b9c4e8ecffe949c0b346e1fd0d6b9977', '内容管理', 'business_manager', '内容管理', '/system/cms/content/list', '1', '1', '5', '&#xe63c;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('business_manager', '业务管理', null, null, null, '1', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('ca152df1a7b44d4f81162f34b808934a', '验证老密码', '36ab9175f7b7423eadda974ba046be05', '', '/system/user/modifiypwd/ispwd', '0', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('ca28235dbd234b7585e133e70cc7999a', '文件上传', 'f5203235547342f094a2c126ad4603bb', '', '/system/file/uploadFile', '0', '1', '1', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('d6abe682007849869c3a168215ae40d4', 'WEB-INF文件管理', 'system_manager', '', '/system/file/web/list', '1', '1', '7', '&#xe61d;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('dic_manager', '字典管理', 'system_manager', '', null, '1', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('e51808e351c24a7e9fb4d47392930a2d', '保存新密码', '36ab9175f7b7423eadda974ba046be05', '', '/system/user/modifiypwd/save', '0', '1', null, '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('f41b9f3b4a0d45f5a3b5842ee40e0e96', '站点管理', 'business_manager', '', '/system/cms/site/list', '1', '1', '3', '&#xe641;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('f5203235547342f094a2c126ad4603bb', '文件管理', 'system_manager', '', '/system/file/list', '1', '1', '6', '&#xe61d;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('system_manager', '系统管理', null, null, null, '1', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_delete', '删除Htmlcase', 'tc_htmlcase_list', null, '/selenium/htmlcase/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_deletemore', '批量删除Htmlcase', 'tc_htmlcase_list', null, '/selenium/htmlcase/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_export', '导出Htmlcase', 'tc_htmlcase_list', null, '/selenium/htmlcase/list/export', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_list', '测试用例管理', '5442d67bcdf8401da675bb5e09650f36', '', '/selenium/htmlcase/list', '1', '1', '4', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_look', '查看Htmlcase', 'tc_htmlcase_list', null, '/selenium/htmlcase/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_update', '修改Htmlcase', 'tc_htmlcase_list', null, '/selenium/htmlcase/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlcase_upload', '导入Htmlcase', 'tc_htmlcase_list', null, '/selenium/htmlcase/upload', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_delete', '删除Htmlfield', 'tc_htmlfield_list', null, '/selenium/htmlfield/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_deletemore', '批量删除Htmlfield', 'tc_htmlfield_list', null, '/selenium/htmlfield/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_export', '导出Htmlfield', 'tc_htmlfield_list', null, '/selenium/htmlfield/list/export', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_list', '页面字段管理', '5442d67bcdf8401da675bb5e09650f36', '', '/selenium/htmlfield/list', '1', '1', '2', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_look', '查看Htmlfield', 'tc_htmlfield_list', null, '/selenium/htmlfield/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_update', '修改Htmlfield', 'tc_htmlfield_list', null, '/selenium/htmlfield/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfield_upload', '导入Htmlfield', 'tc_htmlfield_list', null, '/selenium/htmlfield/upload', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_delete', '删除Htmlfunction', 'tc_htmlfunction_list', null, '/selenium/htmlfunction/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_deletemore', '批量删除Htmlfunction', 'tc_htmlfunction_list', null, '/selenium/htmlfunction/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_export', '导出Htmlfunction', 'tc_htmlfunction_list', null, '/selenium/htmlfunction/list/export', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_list', '页面功能管理', '5442d67bcdf8401da675bb5e09650f36', '', '/selenium/htmlfunction/list', '1', '1', '1', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_look', '查看Htmlfunction', 'tc_htmlfunction_list', null, '/selenium/htmlfunction/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_update', '修改Htmlfunction', 'tc_htmlfunction_list', null, '/selenium/htmlfunction/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_htmlfunction_upload', '导入Htmlfunction', 'tc_htmlfunction_list', null, '/selenium/htmlfunction/upload', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_delete', '删除Validaterule', 'tc_validaterule_list', null, '/selenium/validaterule/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_deletemore', '批量删除Validaterule', 'tc_validaterule_list', null, '/selenium/validaterule/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_export', '导出Validaterule', 'tc_validaterule_list', null, '/selenium/validaterule/list/export', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_list', '验证规则管理', '5442d67bcdf8401da675bb5e09650f36', '', '/selenium/validaterule/list', '1', '1', '3', '', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_look', '查看Validaterule', 'tc_validaterule_list', null, '/selenium/validaterule/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_update', '修改Validaterule', 'tc_validaterule_list', null, '/selenium/validaterule/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('tc_validaterule_upload', '导入Validaterule', 'tc_validaterule_list', null, '/selenium/validaterule/upload', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_auditlog_list', '修改日志', 'system_manager', '', '/system/auditlog/list', '1', '1', '1', '&#xe632;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_auditlog_look', '查看修改日志', 't_auditlog_list', null, '/system/auditlog/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_grade_delete', '删除级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_grade_deletemore', '批量删除级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_grade_list', '级别管理', 'dic_manager', '', '/system/dicdata/grade/list', '1', '1', '1', '&#xe630;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_grade_look', '查看级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_grade_tree', '级别树形结构', 't_dic_data_grade_list', null, '/system/dicdata/grade/tree', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_grade_update', '修改级别', 't_dic_data_grade_list', null, '/system/dicdata/grade/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_minzu_delete', '删除民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_minzu_deletemore', '批量删除民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_minzu_list', '民族管理', 'dic_manager', '', '/system/dicdata/minzu/list', '1', '1', '1', '&#xe650;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_minzu_look', '查看民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_minzu_tree', '民族树形结构', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/tree', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_minzu_update', '修改民族', 't_dic_data_minzu_list', null, '/system/dicdata/minzu/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_xueli_delete', '删除学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_xueli_deletemore', '批量删除学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_xueli_list', '学历管理', 'dic_manager', '', '/system/dicdata/xueli/list', '1', '1', '3', '&#xe621;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_xueli_look', '查看学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_xueli_tree', '学历树形结构', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/tree', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_dic_data_xueli_update', '修改学历', 't_dic_data_xueli_list', null, '/system/dicdata/xueli/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_fwlog_list', '访问日志', 'system_manager', '', '/system/fwlog/list', '1', '1', '2', '&#xe62d;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_fwlog_look', '查看访问日志', 't_fwlog_list', null, '/system/fwlog/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_menu_delete', '删除菜单', 't_menu_list', null, '/system/menu/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_menu_deletemore', '批量删除菜单', 't_menu_list', null, '/system/menu/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_menu_list', '菜单管理', 'system_manager', '', '/system/menu/list/all', '1', '1', '3', '&#xe631;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_menu_look', '查看菜单', 't_menu_list', null, '/system/menu/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_menu_tree', '菜单树形结构', 't_menu_list', null, '/system/menu/tree', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_menu_update', '修改菜单', 't_menu_list', null, '/system/menu/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_org_delete', '删除部门', 't_org_list', null, '/system/org/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_org_deletemore', '批量删除部门', 't_org_list', null, '/system/org/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_org_list', '部门管理', 'business_manager', '', '/system/org/list', '1', '1', '1', '&#xe613;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_org_look', '查看部门', 't_org_list', null, '/system/org/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_org_tree', '部门树形结构', 't_org_list', null, '/system/org/tree', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_org_update', '修改部门', 't_org_list', null, '/system/org/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_role_delete', '删除角色', 't_role_list', null, '/system/role/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_role_deletemore', '批量删除角色', 't_role_list', null, '/system/role/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_role_list', '角色管理', 'system_manager', '', '/system/role/list', '1', '1', '4', '&#xe613;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_role_look', '查看角色', 't_role_list', null, '/system/role/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_role_update', '修改角色', 't_role_list', null, '/system/role/update', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_user_delete', '删除用户', 't_user_list', null, '/system/user/delete', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_user_deletemore', '批量删除用户', 't_user_list', null, '/system/user/delete/more', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_user_list', '用户管理', 'business_manager', '', '/system/user/list', '1', '1', '2', '&#xe612;', null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_user_list_export', '导出用户', 't_user_list', null, '/system/user/list/export', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_user_look', '查看用户', 't_user_list', null, '/system/user/look', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `t_menu` VALUES ('t_user_update', '修改用户', 't_user_list', null, '/system/user/update', '0', '1', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(60) DEFAULT NULL COMMENT '名称',
  `comcode` varchar(1000) DEFAULT NULL COMMENT '代码',
  `pid` varchar(50) DEFAULT NULL COMMENT '上级部门ID',
  `orgType` int(11) DEFAULT NULL COMMENT '1.部门,2.虚拟权限组,10站长部门,11微信,12企业号,13pc,14wap,15投票',
  `leaf` int(11) DEFAULT NULL COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `sortno` int(11) DEFAULT NULL COMMENT '排序号',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `active` int(11) DEFAULT '1' COMMENT '是否有效(0否,1是)',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门';

-- ----------------------------
-- Records of t_org
-- ----------------------------
INSERT INTO `t_org` VALUES ('o_10001', '平台', ',o_10001,', null, null, null, '1', '', '1', null, null, null, null, null);
INSERT INTO `t_org` VALUES ('o_10002', '网站', ',o_10001,o_10002,', 'o_10001', null, null, '1', '', '1', null, null, null, null, null);
INSERT INTO `t_org` VALUES ('o_10003', '张三的站', ',o_10001,o_10002,o_10003,', 'o_10002', '10', null, '1', '张三的站', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(50) NOT NULL COMMENT '角色ID',
  `name` varchar(60) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) DEFAULT NULL COMMENT '权限编码',
  `pid` varchar(50) DEFAULT NULL COMMENT '上级角色ID,暂时不实现',
  `roleType` int(11) NOT NULL DEFAULT '0' COMMENT '0系统角色,1部门主管,2业务岗位',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `active` int(11) DEFAULT '1' COMMENT '是否有效(0否,1是)',
  `orgId` varchar(50) DEFAULT NULL COMMENT '归属的部门Id',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('r_10001', '超级管理员', null, null, '0', '', '1', 'o_10001', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `roleId` varchar(50) NOT NULL COMMENT '角色编号',
  `menuId` varchar(50) NOT NULL COMMENT '菜单编号',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_t_role_menu_roleId_t_role_id` (`roleId`),
  KEY `fk_t_role_menu_menuId_t_menu_id` (`menuId`),
  CONSTRAINT `fk_t_role_menu_menuId_t_menu_id` FOREIGN KEY (`menuId`) REFERENCES `t_menu` (`id`),
  CONSTRAINT `fk_t_role_menu_roleId_t_role_id` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单中间表';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('04c6374657fa467fa4e1e39b65cdc540', 'r_10001', 't_user_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('08bd52cc681f473e8437933b49cc2e4c', 'r_10001', 't_dic_data_grade_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('0e980ba47ec44ef0bdd3665454e75667', 'r_10001', 't_role_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('12bb0769ecb44c99b3b4881988b136f3', 'r_10001', '5442d67bcdf8401da675bb5e09650f36', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('12dde4a4100e42b2a629d44828d02bfa', 'r_10001', 'tc_htmlcase_upload', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('12fd5eda7eb54b66ad4248a76253f5ff', 'r_10001', 'e51808e351c24a7e9fb4d47392930a2d', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('159f0bd070434d14a09490d7951dc0f9', 'r_10001', 'tc_htmlcase_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1772178cf8344c25aeda69ba48a97478', 'r_10001', 'tc_validaterule_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1c5da69235944cb38d3579c46715a99a', 'r_10001', 'b9c4e8ecffe949c0b346e1fd0d6b9977', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1d460043d20a44f6b796fd3d40406b43', 'r_10001', 't_user_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1d78194ebe474b1c8105121c1469874c', 'r_10001', 't_dic_data_minzu_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1dc212db5f1f49b5b8aecf2cd8f48221', 'r_10001', 'tc_htmlfield_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1e56660f7f3f4670967d04d83df4fc9d', 'r_10001', '169815aca9cf41d390e7feb6629d361d', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('1f5afc645b60483383506dec73bede9d', 'r_10001', 't_dic_data_xueli_tree', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('25c5acbf4a674f0ba36bf01e8c35249d', 'r_10001', 'tc_htmlfunction_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('26e549a6b7d440dd8849da53bf97dbde', 'r_10001', 'tc_htmlfield_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('2b3530db44c84dab900dc604b456e137', 'r_10001', 'business_manager', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('2d7d8e405fdc4662ad7136d19e985b67', 'r_10001', 'b94392f7b8714f64819c5c0222eb134a', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('32300fc190c044fb8c49a2597d7fbbf6', 'r_10001', 't_auditlog_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('3694d135dbb64c6d821d5c33f55f987e', 'r_10001', 't_user_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('397be28833704941af2310cb6e78e3bd', 'r_10001', 't_role_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('39e75a18829e4f80a62db6fad5a79866', 'r_10001', 't_user_list_export', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('3b8c1ddd70fa45978564b2f9a1444fb9', 'r_10001', 'tc_validaterule_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('42db93fb9247408caf46b15577a65486', 'r_10001', 'tc_validaterule_export', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('45b7af94173440a3a3536455bdaa161b', 'r_10001', 'tc_validaterule_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('4b9f293fbb654ba7a9f239dda93e05af', 'r_10001', 'ca152df1a7b44d4f81162f34b808934a', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('4be79c6abbe344d8ba99960fa30cf9fb', 'r_10001', 't_dic_data_minzu_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('4d8c009ef95344d69f531e4259aad675', 'r_10001', 't_dic_data_minzu_tree', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('50325331393944b88be56b90578dda24', 'r_10001', 'tc_htmlcase_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('52430546b39646ae9381cc502ff19a03', 'r_10001', 'tc_htmlfield_upload', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('52dbb9def6084ea3bc0416661f276647', 'r_10001', 'tc_validaterule_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('56222a2160684861a4ee3ce8cdb8914e', 'r_10001', 'tc_htmlfunction_export', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('591ddf2e13424a47ac79c261ad3b6d5b', 'r_10001', 't_fwlog_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('5bf5f6b332fd48dfaf910ee9ffde85d4', 'r_10001', 't_dic_data_grade_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('6182d6c47ffd45d3b37852ad0033b4f2', 'r_10001', 't_fwlog_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('61c72775b7914aa3981c304d75e8556c', 'r_10001', 't_role_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('645f97afc37344aea304080b73132c20', 'r_10001', 't_user_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('65a66207f892480f9cc7ba1475ce98f1', 'r_10001', 't_dic_data_minzu_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('6bd4be9ec47e4703a23154b106b0807f', 'r_10001', 't_dic_data_grade_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('70a4a16a27ad499d830eaf398fd08d30', 'r_10001', '91779a0d304f4b91932b63dec87a8536', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('7746e5c50c7b45df90298cbb5dffb8da', 'r_10001', 't_menu_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('780161272f86411fa3ddb379683e17da', 'r_10001', 'tc_htmlfield_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('78daa30b20e147dd8b592c74d859aa63', 'r_10001', 't_dic_data_grade_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('7cb46e3b87d34be8bed035f1233c5c7f', 'r_10001', 'tc_htmlfunction_upload', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('7dcaa1c17eee4465bd3060145c77a0a8', 'r_10001', 't_dic_data_xueli_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('80b26df2274c4c38bbbe3ae8e1a1f93b', 'r_10001', 'tc_validaterule_upload', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('821a7d1cd140487095355753cf0827e5', 'r_10001', 'tc_htmlcase_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('85d26913bcac48738978c7deba5761aa', 'r_10001', 't_menu_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('8703ae3e2a874afc918b0662a017ff71', 'r_10001', '7cd0678633d5407dba2bd6a1553cadce', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('87cb2959f13b4dcaaffc99119addbc8d', 'r_10001', 'tc_htmlfunction_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('88938418d1234b11b38d8641e2fe7137', 'r_10001', 'tc_htmlfunction_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('89622a9c08d84463bea38511559dc2c9', 'r_10001', 'tc_htmlcase_export', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('8aeb52b96ab04ff2acf81dc42311006e', 'r_10001', 't_dic_data_xueli_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('8f86acc728af4cfeac428c10c176422a', 'r_10001', 't_org_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('8fb6b66d23344a1e892816cf18d80c6f', 'r_10001', 't_dic_data_xueli_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('906badac56c64a2b8e1bf9aea20a9514', 'r_10001', 't_dic_data_grade_tree', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('9179abad4c6a40b3a4e74778239ece58', 'r_10001', 'f5203235547342f094a2c126ad4603bb', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('91f69a2590754acbb57f8239eb293bac', 'r_10001', 'f41b9f3b4a0d45f5a3b5842ee40e0e96', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('95345e999bd64ac1a7f3c82ac805732b', 'r_10001', 't_org_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('956bb2deb2d64e27b0ad95d048f93197', 'r_10001', 'tc_htmlcase_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('96b8ca884f81425eba930909e8e5c87f', 'r_10001', '36ab9175f7b7423eadda974ba046be05', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('9c953653e70a4b24932414f77fa390d8', 'r_10001', 't_menu_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('9e31793c4fea47d6bb40612a3f5f4227', 'r_10001', 'system_manager', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('a83ea3817d2e4ca8910cd9639ac4c17e', 'r_10001', 'dic_manager', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('ab7c8070f9a941d6818058b4ddaf4036', 'r_10001', 'tc_htmlfield_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('ad3deb1ea8a649acb60c90f3ccfe40aa', 'r_10001', 't_dic_data_xueli_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('ade99a302fa64de287c25b05ca874104', 'r_10001', 't_user_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('b4859f6fbd9c43399c98cdad23c18870', 'r_10001', 't_menu_tree', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('bedb68bd74b4460d9d29c8de86152ac2', 'r_10001', 't_org_tree', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('c34f6bb8e852455386852502aa9a9e87', 'r_10001', 't_menu_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('c80e23716d72431ca849b6075591b6fb', 'r_10001', 't_org_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('d1aff81098d1454897ee42ad7836bcec', 'r_10001', 't_dic_data_grade_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('d1ec5efdfe3745c3bac08df02ed2f600', 'r_10001', 'tc_htmlfunction_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('d556e2419c5d421dad47e7a6aac31ae9', 'r_10001', 'd6abe682007849869c3a168215ae40d4', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('d87b1ac73f164985aed4ad6b0e9e8d0d', 'r_10001', 'tc_htmlcase_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('db6a6f2c62554dc2a5ceb4b7af303dfb', 'r_10001', 't_org_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('dca0449bf1064a5b8feed9dd44aea8fa', 'r_10001', 't_dic_data_xueli_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('dd95534a6e20460fa046041cc6ef7927', 'r_10001', 't_menu_deletemore', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('def8973a3ca14ffab4c9bb378ad18846', 'r_10001', 'tc_htmlfield_export', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('e22788e9784842e5a47a82ef8868428a', 'r_10001', 't_dic_data_minzu_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('e27c4e0ec28e4e6584dedf7ebaa3e01e', 'r_10001', '4adc1e3e3e244c0991d9dab66c63badf', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('e42e2caef87646c7a34fb1b6ec557f91', 'r_10001', 't_role_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('e7d6f1b508d54db5b2bffa0e865e4fd5', 'r_10001', 't_dic_data_minzu_delete', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('e9a0974171564f71b622cd98f611c364', 'r_10001', 'tc_htmlfield_update', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('eb9ee7a6a6d14168aac09746fd6bf5c5', 'r_10001', 't_role_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('ef995c6688b8407bb967fadc43b2c089', 'r_10001', 'tc_validaterule_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('f1142e1f33b74f61801f8df317dc367f', 'r_10001', 't_org_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('f227e6266f804f8d894007e1f9c76a02', 'r_10001', 't_auditlog_list', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('f23147539e274b2b8dd496940746fab3', 'r_10001', 'ca28235dbd234b7585e133e70cc7999a', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('f38161ed87784e3785f0a37d637d2a77', 'r_10001', 'tc_htmlfunction_look', null, null, null, null, null);
INSERT INTO `t_role_menu` VALUES ('fd8c307dd7044796b05c60f459127159', 'r_10001', '3501ed1e23da40219b4f0fa5b7b2749a', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_role_org
-- ----------------------------
DROP TABLE IF EXISTS `t_role_org`;
CREATE TABLE `t_role_org` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `roleId` varchar(50) NOT NULL COMMENT '角色编号',
  `orgId` varchar(50) NOT NULL COMMENT '部门编号',
  `orgType` int(11) DEFAULT NULL COMMENT '0组织机构 ,1.部门,2.虚拟权限组',
  `hasLeaf` int(11) NOT NULL DEFAULT '0' COMMENT '是否包含子部门,0不包含,1包含',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '是否可用,0不可用,1可用',
  `manager` int(11) DEFAULT '0' COMMENT '0:非主管，1:主管',
  PRIMARY KEY (`id`),
  KEY `fk_t_role_org_roleId_t_role_id` (`roleId`),
  KEY `fk_t_role_org_orgId_t_org_id` (`orgId`),
  CONSTRAINT `fk_t_role_org_orgId_t_org_id` FOREIGN KEY (`orgId`) REFERENCES `t_org` (`id`),
  CONSTRAINT `fk_t_role_org_roleId_t_role_id` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门中间表';

-- ----------------------------
-- Records of t_role_org
-- ----------------------------

-- ----------------------------
-- Table structure for t_tableindex
-- ----------------------------
DROP TABLE IF EXISTS `t_tableindex`;
CREATE TABLE `t_tableindex` (
  `id` varchar(50) NOT NULL COMMENT '表名',
  `maxIndex` int(11) NOT NULL DEFAULT '1' COMMENT '表记录最大的行,一直累加',
  `prefix` varchar(50) NOT NULL COMMENT '前缀 单个字母加 _',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录表最大的行记录';

-- ----------------------------
-- Records of t_tableindex
-- ----------------------------
INSERT INTO `t_tableindex` VALUES ('cms_channel', '10000', 'h_', null, null, null, null, null);
INSERT INTO `t_tableindex` VALUES ('cms_content', '100000', 'c_', null, null, null, null, null);
INSERT INTO `t_tableindex` VALUES ('cms_site', '10001', 's_', null, null, null, null, null);
INSERT INTO `t_tableindex` VALUES ('t_org', '10003', 'o_', null, null, null, null, null);
INSERT INTO `t_tableindex` VALUES ('t_user', '10001', 'u_', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sex` varchar(2) DEFAULT '男' COMMENT '性别',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `weixinId` varchar(200) DEFAULT NULL COMMENT '微信Id',
  `userType` int(11) DEFAULT NULL COMMENT '0后台管理员|/system/,1会员用户|/front/,2cms管理员|/cms/houtai/|cms_siteManager,3活动管理员|/huodong/houtai',
  `active` int(11) DEFAULT '1' COMMENT '是否有效(0否,1是)',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('u_10001', '超级管理员', 'admin', '21232f297a57a5a743894a0e4a801fc3', '男', null, null, null, '0', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_user_org
-- ----------------------------
DROP TABLE IF EXISTS `t_user_org`;
CREATE TABLE `t_user_org` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `userId` varchar(50) NOT NULL COMMENT '用户编号',
  `orgId` varchar(50) NOT NULL COMMENT '机构编号',
  `managerType` int(11) NOT NULL DEFAULT '1' COMMENT '是否主管(0会员 10 员工 11主管 12代理主管 13虚拟主管)',
  `hasleaf` int(11) NOT NULL DEFAULT '1' COMMENT '是否包含子部门(0不包含1包含)',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_t_user_org_userId_t_user_id` (`userId`),
  KEY `fk_t_user_org_orgId_t_org_id` (`orgId`),
  CONSTRAINT `fk_t_user_org_orgId_t_org_id` FOREIGN KEY (`orgId`) REFERENCES `t_org` (`id`),
  CONSTRAINT `fk_t_user_org_userId_t_user_id` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户部门中间表';

-- ----------------------------
-- Records of t_user_org
-- ----------------------------
INSERT INTO `t_user_org` VALUES ('1', 'u_10001', 'o_10001', '11', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` varchar(50) NOT NULL COMMENT '编号',
  `userId` varchar(50) NOT NULL COMMENT '用户编号',
  `roleId` varchar(50) NOT NULL COMMENT '角色编号',
  `bak1` varchar(100) DEFAULT NULL,
  `bak2` varchar(100) DEFAULT NULL,
  `bak3` varchar(100) DEFAULT NULL,
  `bak4` varchar(100) DEFAULT NULL,
  `bak5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_t_user_role_userId_t_user_id` (`userId`),
  KEY `fk_t_user_role_roleId_t_role_id` (`roleId`),
  CONSTRAINT `fk_t_user_role_roleId_t_role_id` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`),
  CONSTRAINT `fk_t_user_role_userId_t_user_id` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色中间表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', 'u_10001', 'r_10001', null, null, null, null, null);
