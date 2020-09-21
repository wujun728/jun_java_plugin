/*
Navicat MySQL Data Transfer

Source Server         : 10.2.1.46
Source Server Version : 50619
Source Host           : 10.2.1.46:3306
Source Database       : osmp

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2016-08-09 15:45:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dataServiceMapping
-- ----------------------------
DROP TABLE IF EXISTS `dataServiceMapping`;
CREATE TABLE `dataServiceMapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bundle` varchar(100) DEFAULT NULL COMMENT '服务所处bundle名称',
  `version` varchar(20) DEFAULT NULL COMMENT '服务所处bundle版本号',
  `serviceName` varchar(50) DEFAULT NULL COMMENT '服务名称',
  `interfaceName` varchar(50) DEFAULT NULL COMMENT '对应接口名称',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `seq_interfaceName` (`interfaceName`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dataServiceMapping
-- ----------------------------

-- ----------------------------
-- Table structure for dataServices
-- ----------------------------
DROP TABLE IF EXISTS `dataServices`;
CREATE TABLE `dataServices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bundle` varchar(100) DEFAULT NULL COMMENT '所在bundle',
  `version` varchar(20) DEFAULT NULL COMMENT 'bundle版本号',
  `name` varchar(100) DEFAULT NULL COMMENT '服务名称',
  `state` tinyint(1) DEFAULT '0' COMMENT '状态,1正常,0异常',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mark` varchar(1000) DEFAULT NULL,
  `loadIp` char(15) DEFAULT NULL COMMENT '所在负载服务器地址',
  `code` varchar(100) DEFAULT NULL COMMENT 'dataService服务映射接口code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1491 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dataServices
-- ----------------------------
INSERT INTO `dataServices` VALUES ('1490', 'osmp-test-service', '1.0.0', 'servicetest', '1', '2016-08-09 09:22:35', '测试DEMO', '', null);

-- ----------------------------
-- Table structure for errorLog
-- ----------------------------
DROP TABLE IF EXISTS `errorLog`;
CREATE TABLE `errorLog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bundle` varchar(100) DEFAULT NULL COMMENT '日志来源bundle',
  `version` varchar(20) DEFAULT NULL COMMENT '版本号',
  `service` varchar(100) DEFAULT NULL COMMENT '日志来源服务',
  `message` text COMMENT '内容',
  `exception` text COMMENT '异常堆栈信息',
  `occurTime` bigint(15) DEFAULT NULL COMMENT '日志发生时间',
  `insertTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `loadIp` char(15) DEFAULT NULL COMMENT '负载服务器IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of errorLog
-- ----------------------------

-- ----------------------------
-- Table structure for infoLog
-- ----------------------------
DROP TABLE IF EXISTS `infoLog`;
CREATE TABLE `infoLog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bundle` varchar(100) DEFAULT NULL COMMENT '日志来源bundle',
  `version` varchar(20) DEFAULT NULL COMMENT '版本号',
  `service` varchar(100) DEFAULT NULL COMMENT '来源服务',
  `message` varchar(5000) DEFAULT NULL COMMENT '内容',
  `occurTime` bigint(15) DEFAULT NULL COMMENT '日志发生时间',
  `insertTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `loadIp` char(15) DEFAULT NULL COMMENT '负载服务器IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infoLog
-- ----------------------------

-- ----------------------------
-- Table structure for interceptorMapping
-- ----------------------------
DROP TABLE IF EXISTS `interceptorMapping`;
CREATE TABLE `interceptorMapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interceptorBundle` varchar(100) DEFAULT NULL COMMENT '拦截器所处组件版本号',
  `interceptorVersion` varchar(20) DEFAULT NULL COMMENT '拦截器所处组件名称',
  `interceptorName` varchar(50) DEFAULT NULL COMMENT '拦截器名称',
  `serviceBundle` varchar(100) DEFAULT NULL COMMENT '业务服务所处组件名称',
  `serviceVersion` varchar(20) DEFAULT NULL COMMENT '业务服务所处组件版本号',
  `serviceName` varchar(50) DEFAULT NULL COMMENT '业务服务名称',
  `level` int(4) DEFAULT '0' COMMENT '拦截器优先级，值越大级别越高,越先执行',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interceptorMapping
-- ----------------------------

-- ----------------------------
-- Table structure for interceptors
-- ----------------------------
DROP TABLE IF EXISTS `interceptors`;
CREATE TABLE `interceptors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bundle` varchar(100) DEFAULT NULL COMMENT '所在bundle',
  `version` varchar(20) DEFAULT NULL COMMENT '版本号',
  `name` varchar(50) DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mark` varchar(1000) DEFAULT NULL,
  `loadIp` char(15) DEFAULT NULL COMMENT '所在负载服务器地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interceptors
-- ----------------------------

-- ----------------------------
-- Table structure for s_exportconfig
-- ----------------------------
DROP TABLE IF EXISTS `s_exportconfig`;
CREATE TABLE `s_exportconfig` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `code` varchar(50) DEFAULT NULL,
  `filename` varchar(50) DEFAULT NULL,
  `tempname` varchar(50) DEFAULT NULL,
  `paramtype` varchar(500) DEFAULT NULL,
  `paramorder` varchar(500) DEFAULT NULL,
  `sqltype` varchar(10) DEFAULT NULL,
  `sql` varchar(1024) DEFAULT NULL,
  `param` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_exportconfig
-- ----------------------------

-- ----------------------------
-- Table structure for t_condition
-- ----------------------------
DROP TABLE IF EXISTS `t_condition`;
CREATE TABLE `t_condition` (
  `id` int(11) NOT NULL,
  `request_ip` varchar(20) DEFAULT NULL COMMENT '发请求的服务器IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_condition
-- ----------------------------

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` varchar(50) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `tabName` varchar(50) DEFAULT NULL,
  `keyFilde` varchar(50) DEFAULT NULL,
  `valueFilde` varchar(50) DEFAULT NULL,
  `propertiesFile` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('412fc0f6-c826-4aeb-bcf7-9899b95ffd29', 'gz', '广州', '1', '', '', '', '');
INSERT INTO `t_dict` VALUES ('48dd16b7-ffe4-4cf1-bc9c-927564ef9296', 'bj', '北京', '1', '', '', '', '');
INSERT INTO `t_dict` VALUES ('49c94311-3242-4d9f-a733-53b244f85af7', '1001', '性别', '1', '', '', '', '');
INSERT INTO `t_dict` VALUES ('4e98d8b0-c4c2-4c3c-8722-3be97d809c88', 'status', '状态', '1', '', '', '', '');
INSERT INTO `t_dict` VALUES ('4ec6d01f-8b2c-4cbd-90a1-768b37f6018b', 'logical', '逻辑条件', '1', '', '', '', '');
INSERT INTO `t_dict` VALUES ('54fe849b-b0a4-49e5-af39-c27557e1689f', 'roleType', '策略类型', '1', '', '', '', '');
INSERT INTO `t_dict` VALUES ('cb210d52-ebcc-4c30-996b-183ac946d74c', 'area', '区域', '1', '', '', '', '');

-- ----------------------------
-- Table structure for t_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_item`;
CREATE TABLE `t_dict_item` (
  `id` varchar(50) NOT NULL,
  `parentCode` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict_item
-- ----------------------------
INSERT INTO `t_dict_item` VALUES ('10a08455-98e1-46db-a391-55fabd52a22e', 'area', '测试', 'manager');
INSERT INTO `t_dict_item` VALUES ('18743ecf-115f-4ed4-a33a-bdfa95d205a8', 'sc', '成都', 'cd');
INSERT INTO `t_dict_item` VALUES ('265bf382-6d77-48db-8448-58b541e0c36a', 'roleType', '服务名称', 'SERVICE');
INSERT INTO `t_dict_item` VALUES ('33dbe54b-01d4-4734-9b3b-7b0d4efd28b2', 'roleType', 'IP', 'IP');
INSERT INTO `t_dict_item` VALUES ('3506e585-1c6a-4d4a-b83f-8e02b03cb6e8', 'logical', '小于', 'less');
INSERT INTO `t_dict_item` VALUES ('37cc6f84-c1c7-4476-b766-393b924b7184', 'logical', '大于', 'great');
INSERT INTO `t_dict_item` VALUES ('42b6440c-102b-47da-a9f9-c7e9ee92e798', 'logical', '小于等于', 'notgreat');
INSERT INTO `t_dict_item` VALUES ('48adbf3a-846b-4ef5-b753-9dfb9f209b44', 'sc', '绵阳', 'my');
INSERT INTO `t_dict_item` VALUES ('5c69c139-8168-46c5-80fb-cfbfcfe22e8f', 'roleType', '请求的URL', 'URL');
INSERT INTO `t_dict_item` VALUES ('6579afbc-6659-48b5-a9fb-970d842dc45a', 'status', '禁止', '0');
INSERT INTO `t_dict_item` VALUES ('75b2e523-8fac-4f8d-bb52-c2c786e8e54d', 'ss', '男', 'nan');
INSERT INTO `t_dict_item` VALUES ('78b90d62-2528-402f-99f8-9ba6f6f699b3', 'area', '广州', 'gz');
INSERT INTO `t_dict_item` VALUES ('7dc57b33-42ea-4e9b-b1a0-5742161e690f', 'area', '重庆', 'cq');
INSERT INTO `t_dict_item` VALUES ('96501fbe-3cf1-410d-bf81-05f3ccea7060', 'sc', '江油', 'JY');
INSERT INTO `t_dict_item` VALUES ('97d10a78-a533-40d6-949f-292a779ed69c', 'gz', '中山市', 'zsj');
INSERT INTO `t_dict_item` VALUES ('99c4b19f-c54b-4ee4-a694-7f64d885eb76', 'logical', '大于等于', 'notless');
INSERT INTO `t_dict_item` VALUES ('a26e4bdd-03b9-45f1-9af7-fd0c386c899b', '1001', '女', '0');
INSERT INTO `t_dict_item` VALUES ('b25abd04-4a33-45ad-a34a-2bb547750e45', 'roleType', '项目名', 'PROJECT');
INSERT INTO `t_dict_item` VALUES ('b9a53d2d-8979-4446-b95b-a4e6dbd42928', 'area', '北京', 'bj');
INSERT INTO `t_dict_item` VALUES ('bbcf7029-1dae-4484-a0ba-1fccf7a6ec4b', 'bj', '北京市', 'bjs');
INSERT INTO `t_dict_item` VALUES ('c011aca9-6547-4e3c-9950-65bf1d0d8c41', 'logical', '等于', 'eq');
INSERT INTO `t_dict_item` VALUES ('dffe0478-f072-445e-a7e3-091bb2ce1fdb', 'roleType', '参数', 'ARGS');
INSERT INTO `t_dict_item` VALUES ('e2c17a6f-c5b6-46df-bffa-923627e09c32', 'sc', 'santai', 'st');
INSERT INTO `t_dict_item` VALUES ('e93e4135-b0b9-4eb0-b514-dc94cbae67dc', 'status', '正常', '1');
INSERT INTO `t_dict_item` VALUES ('ea7b717e-e2ce-449c-9918-2def8876f12e', 'area', '四川', 'sc');
INSERT INTO `t_dict_item` VALUES ('f131021f-5ed4-402e-b47f-cca25d420090', '1001', '男', '1');
INSERT INTO `t_dict_item` VALUES ('fe567490-a27a-4ff2-b23b-641c408d0060', 'logical', '不等于', 'nq');
INSERT INTO `t_dict_item` VALUES ('ffa9ba07-2d21-4c39-b785-49091eaf109b', 'gz', '白云区', 'byc');

-- ----------------------------
-- Table structure for t_properties
-- ----------------------------
DROP TABLE IF EXISTS `t_properties`;
CREATE TABLE `t_properties` (
  `ID` varchar(50) NOT NULL,
  `PROKEY` varchar(50) DEFAULT NULL,
  `PROVALUE` varchar(200) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `REMARK` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_properties
-- ----------------------------
INSERT INTO `t_properties` VALUES ('4c664790-d8cf-4b2b-b002-0d234022580b', 'bundleList', 'http://10.34.50.53:8081/system/console/bundles.json', '插件列表获取地址', '');
INSERT INTO `t_properties` VALUES ('5b09b6bf-518a-4ac5-8e39-4f99c49c96c2', 'webpassword', 'smx', 'WEB中间件密码', '');
INSERT INTO `t_properties` VALUES ('70e05816-bf8b-4e34-a907-f0004e4943ae', 'configFresh', 'cxf/config/refresh', '插件容器配置刷新接口', '配置修改后通知容器刷新');
INSERT INTO `t_properties` VALUES ('854502bc-4321-4c1b-9088-54e435697547', 'webuser', 'smx', 'WEB中间件用户名', '');
INSERT INTO `t_properties` VALUES ('e1453c8e-60b1-47e2-8e3f-a42ee47e6133', 'bundleInfo', 'http://10.34.50.53:8081/system/console/bundles/', '插件详细获取地址', '');

-- ----------------------------
-- Table structure for t_servers
-- ----------------------------
DROP TABLE IF EXISTS `t_servers`;
CREATE TABLE `t_servers` (
  `ID` varchar(50) NOT NULL,
  `SERVER_NAME` varchar(50) DEFAULT NULL COMMENT '服务器名称',
  `SERVER_IP` varchar(50) DEFAULT NULL COMMENT 'IP',
  `SERVER_PORT` varchar(10) DEFAULT NULL COMMENT 'port',
  `SSH_PORT` varchar(10) DEFAULT NULL COMMENT 'SSH端口',
  `USER` varchar(20) DEFAULT NULL COMMENT '登陆用户名',
  `PASSWORD` varchar(20) DEFAULT NULL COMMENT '密码',
  `MANAGE_URL` varchar(50) DEFAULT NULL COMMENT '管理界面URL',
  `COMMAND_PATH` varchar(100) DEFAULT NULL,
  `STATE` int(5) DEFAULT NULL COMMENT '服务器状态',
  `REMARK` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_servers
-- ----------------------------

-- ----------------------------
-- Table structure for t_strategy
-- ----------------------------
DROP TABLE IF EXISTS `t_strategy`;
CREATE TABLE `t_strategy` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT '',
  `forward_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '转发IP',
  `remark` varchar(200) DEFAULT '',
  `status` int(5) DEFAULT NULL COMMENT '1:正常  0：禁止',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_strategy
-- ----------------------------
INSERT INTO `t_strategy` VALUES ('05349100-6f51-48d5-bad2-472bdc589319', '限制104', '10.34.39.103', 'ff', '1');
INSERT INTO `t_strategy` VALUES ('3873534f-3171-41f1-ba04-dc0a7b6e4b04', '0013', '192.168.1.102', 'dd', '1');
INSERT INTO `t_strategy` VALUES ('3ca499ce-85d2-42e0-ae3f-162835ffa8f3', '10.34.41.138转发', '10.34.41.138', '测试的', '1');
INSERT INTO `t_strategy` VALUES ('93c2d08a-63ae-499f-8719-00b0c19703db', '灰度', '10.34.39.103', '圧', '1');
INSERT INTO `t_strategy` VALUES ('c49ec3aa-5c63-4400-ac52-6028e579d13e', '转到104', '10.34.39.104', 's ', '1');
INSERT INTO `t_strategy` VALUES ('cc9f1a03-41bc-492e-ba1e-5d080d9dfdfc', '秒杀', '10.34.39.103', 'fdf v', '1');

-- ----------------------------
-- Table structure for t_strategy_condition
-- ----------------------------
DROP TABLE IF EXISTS `t_strategy_condition`;
CREATE TABLE `t_strategy_condition` (
  `id` varchar(50) NOT NULL,
  `strategy_id` varchar(50) DEFAULT NULL,
  `type` varchar(50) NOT NULL DEFAULT '',
  `name_key` varchar(50) DEFAULT '',
  `judge` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(200) DEFAULT '' COMMENT '条件',
  `type_remark` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_strategy_condition
-- ----------------------------
INSERT INTO `t_strategy_condition` VALUES ('044f2762-eb3c-422b-b1ce-361eb5617568', '05349100-6f51-48d5-bad2-472bdc589319', 'ARGS', 'area', 'eq', 'sc', '参数');
INSERT INTO `t_strategy_condition` VALUES ('06daec8c-f007-4e81-9f83-36893ba0503a', '3ca499ce-85d2-42e0-ae3f-162835ffa8f3', 'ARGS', 'area', 'eq', '001', '参数');
INSERT INTO `t_strategy_condition` VALUES ('18b60e48-4e79-4cc5-9a3e-7dd6cc785ce0', '3ca499ce-85d2-42e0-ae3f-162835ffa8f3', 'SERVICE', 'serviceName', 'eq', 'olquery', '服务名称');
INSERT INTO `t_strategy_condition` VALUES ('32b39b1d-af4f-4602-b780-ff0db2288a17', '93c2d08a-63ae-499f-8719-00b0c19703db', 'IP', 'IP', 'eq', '10.34.41.41', 'IP');
INSERT INTO `t_strategy_condition` VALUES ('3a447006-3944-42dd-9887-3558cc14d1a9', 'c49ec3aa-5c63-4400-ac52-6028e579d13e', 'IP', 'IP', 'eq', '10.34.39.52', 'IP');
INSERT INTO `t_strategy_condition` VALUES ('695a5270-2ca6-4c9d-91ae-79a2a97ff313', '93c2d08a-63ae-499f-8719-00b0c19703db', 'ARGS', 'AREA', 'eq', '1001', '参数');
INSERT INTO `t_strategy_condition` VALUES ('7c9d0d74-705d-432d-bfdb-048847873fc4', '3873534f-3171-41f1-ba04-dc0a7b6e4b04', 'IP', 'IP', 'eq', '168.13.2.2', 'IP');
INSERT INTO `t_strategy_condition` VALUES ('82c352c4-26ea-415b-a11e-d206caa88e47', '93c2d08a-63ae-499f-8719-00b0c19703db', 'PROJECT', 'FROM', 'nq', '0078', '项目名');
INSERT INTO `t_strategy_condition` VALUES ('8b7a51f0-dbc8-4200-b91a-ed007af86fff', 'c49ec3aa-5c63-4400-ac52-6028e579d13e', 'ARGS', 'snbid', 'eq', 'jswx-196', '参数');
INSERT INTO `t_strategy_condition` VALUES ('a042229f-2f42-4ce8-b250-1464182e3ec4', '3ca499ce-85d2-42e0-ae3f-162835ffa8f3', 'PROJECT', 'serviceName', 'eq', 'olweb1', '项目名');
INSERT INTO `t_strategy_condition` VALUES ('b7947219-6393-4455-9bd9-459978d0bfde', '05349100-6f51-48d5-bad2-472bdc589319', 'ARGS', 'SNBID', 'nq', 'WANGKPAAD', '参数');
INSERT INTO `t_strategy_condition` VALUES ('c8ed58c6-503a-49e6-9cbc-481e9462e26e', '05349100-6f51-48d5-bad2-472bdc589319', 'ARGS', 'area', 'eq', 'sc', '参数');
INSERT INTO `t_strategy_condition` VALUES ('c92f2283-9a75-409c-a63f-3cc7670ad93b', '05349100-6f51-48d5-bad2-472bdc589319', 'IP', 'IP', 'eq', '10.34.39.59', 'IP');
INSERT INTO `t_strategy_condition` VALUES ('cbad36fe-fe9f-401b-bcdb-f8833e0b4c88', '3ca499ce-85d2-42e0-ae3f-162835ffa8f3', 'IP', 'IP', 'eq', '10.35.39.102', 'IP');
INSERT INTO `t_strategy_condition` VALUES ('cd622695-8d63-436e-8eee-1488fbf059cc', '05349100-6f51-48d5-bad2-472bdc589319', 'ARGS', 'snbid', 'eq', 'jswx-196', '参数');
INSERT INTO `t_strategy_condition` VALUES ('e0dc2a9b-1ded-48de-a30b-c92e0c2eac2a', 'cc9f1a03-41bc-492e-ba1e-5d080d9dfdfc', 'ARGS', 'area', 'eq', 'sc', '参数');
INSERT INTO `t_strategy_condition` VALUES ('e6aaffbe-d5b4-4c2e-8528-38e079d55546', '3873534f-3171-41f1-ba04-dc0a7b6e4b04', 'ARGS', 'snbid', 'eq', 'aaaa', '参数');
INSERT INTO `t_strategy_condition` VALUES ('f155ad56-c86d-4654-8f27-f90e636eebc8', 'cc9f1a03-41bc-492e-ba1e-5d080d9dfdfc', 'SERVICE', 'service', 'eq', 'idservice', '服务名称');

-- ----------------------------
-- Table structure for t_sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_privilege`;
CREATE TABLE `t_sys_privilege` (
  `ID` int(10) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `URI` varchar(200) DEFAULT NULL,
  `ICON` varchar(20) DEFAULT NULL,
  `DESCRIPTION` varchar(50) DEFAULT NULL,
  `ORD` int(10) DEFAULT NULL,
  `PID` int(10) DEFAULT NULL,
  `PRSTATE` int(20) DEFAULT NULL,
  `TYPE` int(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_privilege
-- ----------------------------
INSERT INTO `t_sys_privilege` VALUES ('10', '系统管理', '#', 'pencil', '系统管理', '2', '-1', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('20', '平台管理', '#', 'folder', '平台管理', '1', '-1', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('30', '参数设置', '#', 'pie', '系统参数设置', '3', '-1', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('40', '系统监控', '#', 'folder', '系统监控', '4', '-1', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('1001', '用户管理', 'user/toList.do', 'pie', '用户管理', '1', '10', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('1002', '角色管理', 'role/toList.do', 'pie', '角色管理', '2', '10', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('1003', '字典管理', 'dict/toList.do', 'pie', '字典管理', '3', '10', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('1004', '资源数据管理', 'properties/toList.do', 'pie', '资源数据管理', '4', '10', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('1005', '系统配置管理', 'config/toList.do', 'pie', '系统配置管理', '5', '10', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2001', '组件管理', 'bundle/toList.do', 'pie', '组件管理', '1', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2002', '错误日志', 'errorLog/toList.do', 'pie', '错误日志', '2', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2003', 'INFO日志', 'infoLog/toList.do', 'pie', 'INFO日志', '3', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2004', '服务器管理', 'servers/toList.do', 'pie', '服务器管理', '4', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2005', '接口映射', 'sermapping/toList.do', 'pie', '接口映射', '5', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2006', '拦截器映射', 'interceptorMapping/toList.do', 'pie', '拦截器映射', '6', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2007', '缓存管理', 'cache/toList.do', 'pie', '缓存管理', '7', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('2008', '策略管理', 'strategy/toList.do', 'pie', '策略管理', '8', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('3001', '系统设置', 'sys_config/toConfigPage.do', 'pie', '系统设置', '1', '30', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('4001', '服务监控', 'services/toList.do', 'pie', '服务监控', '1', '40', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('4002', '拦截器监控', 'interceptors/toList.do', 'pie', '拦截器监控', '2', '40', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('4003', 'TEST', 'test/toList.do', 'pie', 'TEST', '3', '40', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('4004', '服务管理', 'dataService/toList.do', 'pie', '服务管理', '4', '20', '1', '1');
INSERT INTO `t_sys_privilege` VALUES ('4005', '数据库监控', 'dataBase/toList.do', 'pie', '数据库监控', '5', '40', '4', '4');
INSERT INTO `t_sys_privilege` VALUES ('4006', '性能监控', 'performance/toList.do', 'pie', '性能监控', '6', '40', '1', '1');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` varchar(50) NOT NULL,
  `role_name` varchar(30) NOT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '1:正常  0：禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('0cf81070-90ee-41d8-a82b-ca8337647868', '超级管理', '1');
INSERT INTO `tb_role` VALUES ('692af72f-4f65-465e-b57b-1f5792c4c18b', '测试2', '1');
INSERT INTO `tb_role` VALUES ('d117f5cf-b2fc-44e0-8648-820654f4a7fb', '测试角色', '1');

-- ----------------------------
-- Table structure for tb_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_privilege`;
CREATE TABLE `tb_role_privilege` (
  `id` varchar(50) NOT NULL,
  `privilege_id` int(10) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_privilege
-- ----------------------------
INSERT INTO `tb_role_privilege` VALUES ('1a3ca36d-8ebb-47cc-ba79-a169e6a46b13', '2002', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('1f33ad1a-fff8-4a12-83cf-eebd7291abd4', '10', '692af72f-4f65-465e-b57b-1f5792c4c18b');
INSERT INTO `tb_role_privilege` VALUES ('20070985-a30f-46e0-822c-5ecac5f812cc', '1003', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('219b0dd3-db95-481a-b496-5cb414bda4d0', '10', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('293d6504-ffe1-448b-9445-f41dafe844ac', '2005', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('3869ce36-3faa-4824-bc3c-0c9c79d37a9c', '2001', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('3d94ddc3-03ae-4edb-b007-323ab9ed2967', '1001', '692af72f-4f65-465e-b57b-1f5792c4c18b');
INSERT INTO `tb_role_privilege` VALUES ('4389da4a-b1d9-44b4-a1d8-386c02ab200d', '20', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('6181d96b-f41b-4c14-ba96-5d65c3069783', '4002', '692af72f-4f65-465e-b57b-1f5792c4c18b');
INSERT INTO `tb_role_privilege` VALUES ('b0837534-5445-44b3-9fa6-c9eec576ba09', '2004', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb');
INSERT INTO `tb_role_privilege` VALUES ('d0d31903-37e2-4386-b731-d563de46b083', '4004', '692af72f-4f65-465e-b57b-1f5792c4c18b');
INSERT INTO `tb_role_privilege` VALUES ('d70854b2-09f0-414c-9117-df43afa5edd5', '20', '692af72f-4f65-465e-b57b-1f5792c4c18b');
INSERT INTO `tb_role_privilege` VALUES ('e6c06bc7-b687-4d6d-9230-768619f5d450', '40', '692af72f-4f65-465e-b57b-1f5792c4c18b');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '1 正常\r\n           0禁止登录\r\n            ',
  `create_time` datetime NOT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_login_ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('0cf81070-90ee-41d8-a82b-ca8337647868', 'admin', '827CCB0EEA8A706C4C34A16891F84E7B', '王哥哥', '砖夜攻城狮', '1', '2015-05-05 11:26:26', '2015-05-05 11:35:43', '10.34.43.59');
INSERT INTO `tb_user` VALUES ('fcdcb12e-7463-4e5c-a628-46be539af5b0', 'cs1', '827CCB0EEA8A706C4C34A16891F84E7B', 'cs1', 'cs1', '1', '2015-05-05 12:34:46', '2015-05-06 17:01:20', '127.0.0.1');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` varchar(50) NOT NULL,
  `role_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('0cf81070-90ee-41d8-a82b-ca8337647868', '0cf81070-90ee-41d8-a82b-ca8337647868', '0cf81070-90ee-41d8-a82b-ca8337647868');
INSERT INTO `tb_user_role` VALUES ('8fd4cac5-459f-45e2-9e27-4015d33cd403', 'd117f5cf-b2fc-44e0-8648-820654f4a7fb', 'fcdcb12e-7463-4e5c-a628-46be539af5b0');
INSERT INTO `tb_user_role` VALUES ('fe22dd25-992a-41b9-af93-11b485b39b7e', '692af72f-4f65-465e-b57b-1f5792c4c18b', 'fcdcb12e-7463-4e5c-a628-46be539af5b0');

-- ----------------------------
-- Procedure structure for trunckTable
-- ----------------------------
DROP PROCEDURE IF EXISTS `trunckTable`;
DELIMITER ;;
CREATE DEFINER=`sicent`@`%` PROCEDURE `trunckTable`()
BEGIN
  DECLARE done INT DEFAULT 0;
  DECLARE tname CHAR(50);
  DECLARE cur1 CURSOR FOR SELECT table_name from INFORMATION_SCHEMA.TABLES WHERE table_schema = 'osmp';
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
 
  OPEN cur1;
 
  REPEAT
    FETCH cur1 INTO tname;
    IF NOT done THEN
          set  @str=concat('truncate table ', tname);
      prepare stmt1 from @str;
          execute stmt1;
          deallocate prepare stmt1;
    END IF;
  UNTIL done END REPEAT;
 
  CLOSE cur1;
END
;;
DELIMITER ;
