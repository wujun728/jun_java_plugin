/*
Navicat MySQL Data Transfer

Source Server         : tyboot@192.168.1.212
Source Server Version : 50636
Source Host           : 192.168.1.212:3306
Source Database       : tyboot

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2019-07-26 09:10:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_cashout_record
-- ----------------------------
DROP TABLE IF EXISTS `account_cashout_record`;
CREATE TABLE `account_cashout_record` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `APPLAY_NO` varchar(32) NOT NULL COMMENT '申请编号',
  `APPLAY_TYPE` varchar(32) DEFAULT NULL COMMENT '申请类型（支付宝|银行）',
  `TRANSFER_ACCOUNT` varchar(32) DEFAULT NULL COMMENT '转账账号',
  `TRANSFER_NAME` varchar(32) DEFAULT NULL COMMENT '转账用户名称',
  `OPEN_BANK` varchar(32) DEFAULT NULL COMMENT '开户行',
  `APPLAY_AMOUNT` decimal(11,4) NOT NULL COMMENT '申请提现金额',
  `OUT_AMOUNT` decimal(11,4) NOT NULL COMMENT '实际提现最终出账金额',
  `POUNDAGE` decimal(11,4) NOT NULL COMMENT '手续费',
  `APPLY_STATUS` varchar(32) NOT NULL COMMENT '申请状态（申请中pending|已确认confirmed|自动确认confirmed_auto|拒绝refuse）',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT '提现完成时间',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现申请表';

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `AGENCY_CODE` varchar(32) NOT NULL COMMENT 'agencycode',
  `ACCOUNT_NO` varchar(32) DEFAULT NULL COMMENT '账户编号',
  `USER_ID` varchar(32) NOT NULL,
  `BALANCE` decimal(11,4) NOT NULL DEFAULT '0.0000' COMMENT '账户余额',
  `ACCOUNT_TYPE` varchar(32) NOT NULL COMMENT '账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）',
  `ACCOUNT_STATUS` varchar(32) NOT NULL COMMENT '账户状态(正常\\冻结\\资金冻结\\失效)',
  `PAYMENT_PASSWORD` varchar(32) NOT NULL DEFAULT '111111' COMMENT '支付密码（md5加密,暂未启用）',
  `CUMULATIVE_BALANCE` decimal(11,4) DEFAULT '0.0000' COMMENT '累计充值总额',
  `UPDATE_VERSION` bigint(20) NOT NULL COMMENT '数据版本',
  `CREATE_TIME` datetime NOT NULL,
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `user_account_index` (`USER_ID`,`ACCOUNT_TYPE`,`AGENCY_CODE`) USING BTREE,
  UNIQUE KEY `INDEX_ACCOUNT_NO` (`ACCOUNT_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户虚拟账户表，记录所有公网用户的虚拟账户，account_no字段预留，用以多账户支持';

-- ----------------------------
-- Table structure for account_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `account_recharge_record`;
CREATE TABLE `account_recharge_record` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '充值账户归属用户id',
  `ACCOUNT_NO` varchar(32) DEFAULT NULL COMMENT '充值账户编号',
  `RECHARGE_TIME` datetime NOT NULL COMMENT ' 充值时间',
  `RECHARGE_AMOUNT` decimal(11,4) NOT NULL COMMENT '充值金额',
  `BILL_NO` varchar(32) NOT NULL COMMENT '充值账单编号',
  `ACCOUNT_TYPE` varchar(32) NOT NULL COMMENT '账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值记录表，只记录交易成功的充值信息';

-- ----------------------------
-- Table structure for account_serial
-- ----------------------------
DROP TABLE IF EXISTS `account_serial`;
CREATE TABLE `account_serial` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '账户归属用户id',
  `ACCOUNT_NO` varchar(32) DEFAULT NULL COMMENT '账户编号(预留字段，暂不启用)',
  `INITIAL_PREFUNDED_BALANCE` decimal(11,4) NOT NULL COMMENT '起始余额',
  `CHANGE_AMOUNT` decimal(11,4) NOT NULL COMMENT '变更金额',
  `FINAL_BALANCE` decimal(11,4) NOT NULL COMMENT '最终余额',
  `OPERATION_TYPE` varchar(32) NOT NULL COMMENT '操作类型（充值recharge，消费consumption(消费类型还可以再分)，提现cash-out）',
  `OPERATE_TIME` datetime NOT NULL COMMENT '操作时间',
  `BILL_NO` varchar(32) NOT NULL COMMENT '交易账单编号',
  `UPDATE_VERSION` bigint(20) NOT NULL COMMENT '账户信息的数据版本',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL,
  `ACCOUNT_TYPE` varchar(32) NOT NULL COMMENT '账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）',
  `BOOKKEEPING` varchar(16) NOT NULL COMMENT '记账类型',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_SERIAL_UPDATE_VERSION` (`UPDATE_VERSION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟账户金额变更记录表，所有针对账户金额的变动操作都要记录到此表中，';

-- ----------------------------
-- Table structure for account_transfer_record
-- ----------------------------
DROP TABLE IF EXISTS `account_transfer_record`;
CREATE TABLE `account_transfer_record` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `BILL_NO` varchar(32) NOT NULL COMMENT '账单编号',
  `SOURCE_ACCOUNT_NO` varchar(50) NOT NULL COMMENT '来源账户编号',
  `TARGET_ACCOUNT_NO` varchar(50) DEFAULT NULL COMMENT '目标账户编号',
  `TRANSFER_TYPE` varchar(32) DEFAULT NULL COMMENT '转账类型(内部账户间主动转账，冻结/解冻)',
  `TRANSFER_AMOUNT` decimal(11,4) NOT NULL COMMENT '转账金额',
  `TRANSFER_STATUS` varchar(16) DEFAULT NULL COMMENT '转账状态(转账中，转账成功、转账失败)',
  `TRANSFER_TIME` datetime NOT NULL COMMENT '转账时间',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `TRANSFER_POSTSCRIPT` varchar(255) NOT NULL COMMENT '转账附言',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `SOURCE_ACCOUNT_TYPE` varchar(32) NOT NULL COMMENT '账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）',
  `TARGET_ACCOUNT_TYPE` varchar(32) NOT NULL COMMENT '账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户转账记录:\r\n特指：内部账户之间主动转账，冻结/解冻资金所引发的转账到冻结账户，内部账户到外部账户的主动转账';

-- ----------------------------
-- Table structure for auth_login_history
-- ----------------------------
DROP TABLE IF EXISTS `auth_login_history`;
CREATE TABLE `auth_login_history` (
  `SEQUENCE_NBR` bigint(32) NOT NULL,
  `AGENCY_CODE` varchar(16) DEFAULT NULL COMMENT '归属机构',
  `LOGIN_ID` varchar(32) NOT NULL COMMENT '登录账号',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户编号',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `USER_TYPE` varchar(32) NOT NULL COMMENT '用户类型（机构用户，公网用户,匿名用户）',
  `ACTION_BY_PRODUCT` varchar(32) NOT NULL COMMENT '来源产品',
  `ACTION_BY_IP` varchar(32) NOT NULL COMMENT '来源IP',
  `SESSION_EXPIRATION` bigint(16) NOT NULL COMMENT '过期时限(秒）',
  `SESSION_STATUS` varchar(16) NOT NULL COMMENT 'session状态：激活，过期',
  `SESSION_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `ACTION_BY_AGENT` varchar(255) DEFAULT NULL,
  `SOURCE_DEVICE_CODE` varchar(255) DEFAULT NULL COMMENT '来源设备串码',
  `SOURCE_OS` varchar(255) DEFAULT NULL COMMENT '来源平台',
  `REC_USER_ID` varchar(32) NOT NULL,
  `REC_DATE` datetime NOT NULL,
  `TOKEN` varchar(64) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录记录';

-- ----------------------------
-- Table structure for auth_login_info
-- ----------------------------
DROP TABLE IF EXISTS `auth_login_info`;
CREATE TABLE `auth_login_info` (
  `SEQUENCE_NBR` bigint(32) NOT NULL,
  `LOGIN_ID` varchar(32) NOT NULL COMMENT '登录ID',
  `USER_ID` varchar(32) NOT NULL COMMENT '系统用户ID',
  `PASSWORD` varchar(64) NOT NULL,
  `SALT` varchar(32) NOT NULL COMMENT '密码随机盐',
  `USER_TYPE` varchar(16) NOT NULL,
  `AGENCY_CODE` varchar(16) NOT NULL,
  `LOCK_STATUS` varchar(16) NOT NULL DEFAULT 'N' COMMENT '数据锁定状态: N :非锁定 / Y: 锁定',
  `LOCK_DATE` datetime DEFAULT NULL COMMENT '数据锁定时间',
  `LOCK_USER_ID` varchar(32) DEFAULT NULL COMMENT '锁定人ID外键',
  `REC_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REC_USER_ID` varchar(32) NOT NULL,
  `ID_TYPE` varchar(16) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_LOGIN_ID` (`LOGIN_ID`),
  UNIQUE KEY `INDEX_LOGIN_INFO` (`USER_ID`,`LOGIN_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for auth_resource_operation
-- ----------------------------
DROP TABLE IF EXISTS `auth_resource_operation`;
CREATE TABLE `auth_resource_operation` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `MODULE_CODE` varchar(32) DEFAULT NULL COMMENT '模块代码，程序中的注解',
  `RESOURCE_CODE` varchar(64) DEFAULT NULL COMMENT '资源代码，程序中的注解',
  `OPRATE_CODE` varchar(32) DEFAULT NULL COMMENT '操作代码，程序中的注解',
  `API_CODE` varchar(32) DEFAULT NULL COMMENT '按约定规则识别操作的唯一的标识FXXXXXXXXXX(F后面8位数字加2位字符)',
  `PARENT_ID` varchar(64) NOT NULL COMMENT '父主键UID',
  `RES_TYPE` varchar(32) NOT NULL COMMENT '资源类型(模块、资源、操作)',
  `LEVEL_CODE` varchar(32) DEFAULT NULL COMMENT '操作级别代码，程序中的注解',
  `MODULE_NAME` varchar(32) DEFAULT NULL COMMENT '模块名称',
  `RESOURCE_NAME` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `OPRATE_NAME` varchar(32) DEFAULT NULL COMMENT '操作名称',
  `OPRATE_LEVEL` varchar(32) DEFAULT NULL COMMENT '操作级别',
  `AGENCY_CODE` varchar(16) DEFAULT NULL COMMENT '机构编码',
  `REQ_URL` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `REQ_METHOD` varchar(16) DEFAULT NULL COMMENT '请求方式get,post,put,delete',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(1) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  PRIMARY KEY (`SEQUENCE_NBR`),
  KEY `INDEX_AGENCY_CODE` (`AGENCY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源操作表';

-- ----------------------------
-- Table structure for auth_sso_sessions
-- ----------------------------
DROP TABLE IF EXISTS `auth_sso_sessions`;
CREATE TABLE `auth_sso_sessions` (
  `SEQUENCE_NBR` bigint(32) NOT NULL,
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '归属机构',
  `LOGIN_ID` varchar(32) NOT NULL COMMENT '登录账号',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户编号',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `USER_TYPE` varchar(32) NOT NULL COMMENT '用户类型',
  `ACTION_BY_PRODUCT` varchar(32) NOT NULL COMMENT '来源产品',
  `ACTION_BY_IP` varchar(32) NOT NULL COMMENT '来源IP',
  `SESSION_EXPIRATION` bigint(16) NOT NULL COMMENT '过期时限(秒）',
  `SESSION_STATUS` varchar(16) NOT NULL COMMENT 'session状态：激活，过期',
  `SESSION_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `ACTION_BY_AGENT` varchar(255) DEFAULT NULL,
  `SOURCE_DEVICE_CODE` varchar(255) DEFAULT NULL COMMENT '来源设备串码',
  `SOURCE_OS` varchar(255) DEFAULT NULL COMMENT '来源平台',
  `REC_USER_ID` varchar(32) NOT NULL,
  `REC_STATUS` varchar(16) NOT NULL,
  `REC_DATE` datetime NOT NULL,
  `TOKEN` varchar(64) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_SESSION` (`AGENCY_CODE`,`LOGIN_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录SESSION';

-- ----------------------------
-- Table structure for order_history
-- ----------------------------
DROP TABLE IF EXISTS `order_history`;
CREATE TABLE `order_history` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `ORDER_SN` varchar(20) NOT NULL COMMENT '订单的唯一编号',
  `BILL_NO` varchar(32) NOT NULL COMMENT '账单编号',
  `AMOUNT` int(11) NOT NULL COMMENT '订单总金额',
  `PRODUCT_AMOUNT` int(11) NOT NULL COMMENT '商品总价',
  `COUPON_DEDUCTION` int(11) DEFAULT NULL COMMENT '优惠抵扣金额',
  `ORDER_TYPE` varchar(16) NOT NULL COMMENT '订单类型（不同的订单类型可能会有不同的主线流程，慎重定义）',
  `ORDER_STATUS` varchar(32) NOT NULL COMMENT '订单状态（订单生命周期内的所有状态标识）',
  `CREATE_TIME` datetime NOT NULL COMMENT '订单生成时间',
  `PAY_TIME` datetime DEFAULT NULL COMMENT '订单支付时间',
  `PAY_METHOD` varchar(20) DEFAULT NULL COMMENT '支付方式（在交易模块定义)',
  `PAY_STATUS` varchar(16) NOT NULL COMMENT '支付状态（0未支付,1已支付）',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `SOURCE` varchar(32) DEFAULT NULL COMMENT '订单来源（PUBLIC:公网, AGENCY:商家）',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_ORDERSN` (`ORDER_SN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单归档表';

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `ORDER_SN` varchar(20) NOT NULL COMMENT '订单的唯一编号',
  `BILL_NO` varchar(32) NOT NULL COMMENT '账单编号',
  `AMOUNT` decimal(11,2) NOT NULL COMMENT '订单总金额',
  `PRODUCT_AMOUNT` decimal(11,2) NOT NULL COMMENT '商品总价',
  `COUPON_DEDUCTION` decimal(11,2) DEFAULT NULL COMMENT '被抵扣掉的金额',
  `ORDER_TYPE` varchar(16) NOT NULL COMMENT '订单类型（不同的订单类型可能会有不同的主线流程，慎重定义）',
  `ORDER_STATUS` varchar(32) NOT NULL COMMENT '订单状态（订单生命周期内的所有状态标识）',
  `CREATE_TIME` datetime NOT NULL COMMENT '订单生成时间',
  `PAY_TIME` datetime DEFAULT NULL COMMENT '订单支付时间',
  `PAY_METHOD` varchar(20) DEFAULT NULL COMMENT '支付方式（在交易模块定义)',
  `PAY_STATUS` varchar(16) NOT NULL COMMENT '支付状态（0未支付,1已支付）',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `SOURCE` varchar(32) DEFAULT NULL COMMENT '订单来源（PUBLIC:公网, AGENCY:商家）',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '评价状态;已评价true，未评价false',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_ORDER_SN` (`ORDER_SN`),
  KEY `INDEX_USER_ID` (`USER_ID`),
  KEY `INDEX_AGENCY_CODE` (`AGENCY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单主表，订单流转所需的主要字段。\r\n在订单流转结束之后将被转移到订单历史表，当前表只保存流转中的订单信息';

-- ----------------------------
-- Table structure for order_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `order_product_relation`;
CREATE TABLE `order_product_relation` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `ORDER_SN` varchar(50) NOT NULL COMMENT '订单编号',
  `PRODUCT_SEQ` bigint(32) NOT NULL COMMENT '商品信息主键',
  `PRODUCT_TYPE` varchar(16) NOT NULL COMMENT '商品信息实体类型',
  `PRODUCT_NAME` varchar(255) NOT NULL COMMENT '商品名称',
  `COUNT` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `PRODUCT_PRICE` int(11) NOT NULL COMMENT '商品当前单价',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`SEQUENCE_NBR`),
  KEY `ORDER_PRODUCT_INDEX` (`ORDER_SN`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单产品关系表';

-- ----------------------------
-- Table structure for privilege_agency_menu
-- ----------------------------
DROP TABLE IF EXISTS `privilege_agency_menu`;
CREATE TABLE `privilege_agency_menu` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `AGENCY_CODE` varchar(32) NOT NULL,
  `MENU_SEQUENCE_NBR` varchar(32) NOT NULL COMMENT '菜单ID',
  `CREATE_USER_ID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `REC_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REC_STATUS` varchar(16) NOT NULL,
  `REC_USER_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单关系表';

-- ----------------------------
-- Table structure for privilege_menu
-- ----------------------------
DROP TABLE IF EXISTS `privilege_menu`;
CREATE TABLE `privilege_menu` (
  `SEQUENCE_NBR` bigint(32) NOT NULL,
  `AGENCY_CODE` varchar(32) NOT NULL,
  `PARENT_ID` varchar(32) NOT NULL COMMENT '父菜单ID',
  `MENU_NAME` varchar(50) NOT NULL COMMENT '菜单名称',
  `MENU_TYPE` varchar(10) NOT NULL COMMENT '菜单类型（目录、菜单、页面）',
  `MENU_ICON` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `MENU_TITLE` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `MENU_LEVEL` int(11) NOT NULL COMMENT '菜单层级',
  `ORDER_NUM` int(11) NOT NULL COMMENT '排序',
  `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REC_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REC_STATUS` varchar(16) NOT NULL,
  `REC_USER_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Table structure for privilege_role
-- ----------------------------
DROP TABLE IF EXISTS `privilege_role`;
CREATE TABLE `privilege_role` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `ROLE_NAME` varchar(32) NOT NULL COMMENT '角色名称',
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL COMMENT '数据状态 :I 非激活 /A  激活',
  `LOCK_STATUS` varchar(16) NOT NULL COMMENT '数据锁定状态: N :非锁定 / Y: 锁定 ',
  `LOCK_DATE` datetime DEFAULT NULL COMMENT '数据锁定时间',
  `LOCK_USER_ID` varchar(32) DEFAULT NULL COMMENT '锁定人ID外键',
  `CREATE_USER_ID` varchar(32) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`SEQUENCE_NBR`),
  KEY `INDEX_AGENCY_CODE` (`AGENCY_CODE`,`ROLE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for privilege_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `privilege_role_menu`;
CREATE TABLE `privilege_role_menu` (
  `SEQUENCE_NBR` bigint(32) NOT NULL,
  `AGENCY_CODE` varchar(32) NOT NULL COMMENT '机构编号',
  `ROLE_SEQUENCE_NBR` varchar(32) NOT NULL COMMENT '角色ID',
  `MENU_SEQUENCE_NBR` varchar(32) NOT NULL COMMENT '菜单ID',
  `CREATE_USER_ID` varchar(32) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `REC_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REC_STATUS` char(1) NOT NULL,
  `REC_USER_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单关系表';

-- ----------------------------
-- Table structure for privilege_user_role
-- ----------------------------
DROP TABLE IF EXISTS `privilege_user_role`;
CREATE TABLE `privilege_user_role` (
  `SEQUENCE_NBR` bigint(32) NOT NULL,
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `USER_ID` varchar(32) NOT NULL COMMENT '机构用户表外键',
  `ROLE_SEQUENCE_NBR` varchar(32) NOT NULL COMMENT '角色表外键',
  `REC_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REC_STATUS` char(1) NOT NULL,
  `REC_USER_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Table structure for public_user_info
-- ----------------------------
DROP TABLE IF EXISTS `public_user_info`;
CREATE TABLE `public_user_info` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `REAL_NAME` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `AGENCY_CODE` varchar(16) DEFAULT NULL,
  `NICK_NAME` varchar(50) DEFAULT NULL COMMENT '昵称',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省份',
  `EAMIL` varchar(255) DEFAULT NULL,
  `CITY` varchar(8) DEFAULT NULL COMMENT '城市',
  `REGION` varchar(8) DEFAULT NULL COMMENT '地区',
  `CONUTRY` varchar(8) DEFAULT NULL COMMENT '国家',
  `MOBILE` varchar(32) DEFAULT NULL COMMENT '联系电话1',
  `GENDER` varchar(1) DEFAULT NULL COMMENT '性别',
  `BIRTH_DATE` date DEFAULT NULL COMMENT '生日',
  `AVATAR` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `REGISTER_DATE` datetime NOT NULL COMMENT '注册日期',
  `SOURCE_DEVICE_CODE` varchar(255) DEFAULT NULL COMMENT '来源设备串码',
  `SOURCE_OS` varchar(255) DEFAULT NULL COMMENT '来源平台',
  `LOCK_STATUS` varchar(1) NOT NULL DEFAULT 'N' COMMENT '数据锁定状态: N :非锁定 / Y: 锁定 ',
  `LOCK_DATE` datetime DEFAULT NULL COMMENT '数据锁定时间',
  `LOCK_USER_ID` varchar(32) DEFAULT NULL COMMENT '锁定人ID外键',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(1) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_PUBLIC_USERID` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公网用户详细信息表';

-- ----------------------------
-- Table structure for systemctl_comment
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_comment`;
CREATE TABLE `systemctl_comment` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  `TEXT_COMMENT` varchar(500) NOT NULL COMMENT '评论文本内容',
  `USER_ID` varchar(32) NOT NULL COMMENT '评论人id',
  `PARENT_ID` varchar(32) NOT NULL COMMENT '上级评论id',
  `COMMENT_TYPE` varchar(16) NOT NULL COMMENT '評論類型：文本，音頻',
  `TARGET_USER_ID` varchar(32) DEFAULT NULL COMMENT '回复的目标用户 ',
  `ENTITY_TYPE` varchar(32) NOT NULL COMMENT '关联对象类型(用户头像，帖子，帖子评论，帖子语音....）',
  `ENTITY_ID` varchar(32) NOT NULL COMMENT '关联实体id',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `SCORE` decimal(11,1) DEFAULT NULL,
  `AGENCY_CODE` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for systemctl_dictionarie
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_dictionarie`;
CREATE TABLE `systemctl_dictionarie` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `DICT_CODE` varchar(16) NOT NULL COMMENT '字典编码 系统中固定不变',
  `DICT_NAME` varchar(100) NOT NULL COMMENT '字典名字',
  `DICT_ALIAS` varchar(100) DEFAULT NULL COMMENT '字典别名',
  `DICT_DESC` varchar(255) DEFAULT NULL COMMENT '字典值描述',
  `BU_TYPE` varchar(16) DEFAULT NULL COMMENT '业务类型',
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL COMMENT '数据状态 :I 非激活 /A  激活',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_DICT_AGENCY_CODE` (`DICT_CODE`,`AGENCY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- ----------------------------
-- Table structure for systemctl_dictionarie_user_value
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_dictionarie_user_value`;
CREATE TABLE `systemctl_dictionarie_user_value` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `DICT_CODE` varchar(16) NOT NULL COMMENT '字典编码(系统中固定不变的)',
  `DICT_DATA_KEY` varchar(32) NOT NULL COMMENT '字典KEY(当前字典中唯一)',
  `DICT_DATA_VALUE` varchar(500) NOT NULL,
  `DICT_DATA_DESC` varchar(255) DEFAULT NULL COMMENT '字典VALUE描述',
  `ORDER_NUM` int(11) DEFAULT '0' COMMENT '排序字段',
  `USER_ID` varchar(32) NOT NULL,
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL COMMENT '数据状态 :I 非激活 /A  激活',
  `LOCK_STATUS` varchar(16) NOT NULL,
  `LOCK_USER_ID` varchar(32) DEFAULT NULL,
  `LOCK_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_KEY` (`DICT_DATA_KEY`,`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户字典值表数据';

-- ----------------------------
-- Table structure for systemctl_dictionarie_value
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_dictionarie_value`;
CREATE TABLE `systemctl_dictionarie_value` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `DICT_CODE` varchar(16) NOT NULL COMMENT '字典编码(系统中固定不变的)',
  `DICT_DATA_KEY` varchar(32) NOT NULL COMMENT '字典KEY(当前字典中唯一)',
  `DICT_DATA_VALUE` varchar(2000) DEFAULT NULL,
  `DICT_DATA_DESC` varchar(255) DEFAULT NULL COMMENT '字典VALUE描述',
  `ORDER_NUM` int(11) DEFAULT '0' COMMENT '排序字段',
  `AGENCY_CODE` varchar(16) NOT NULL COMMENT '机构编码',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL COMMENT '数据状态 :I 非激活 /A  激活',
  `LOCK_STATUS` varchar(16) NOT NULL,
  `LOCK_USER_ID` varchar(32) DEFAULT NULL,
  `LOCK_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典值表数据';

-- ----------------------------
-- Table structure for systemctl_feedback
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_feedback`;
CREATE TABLE `systemctl_feedback` (
  `SEQUENCE_NBR` bigint(20) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '发布人',
  `CONTENT` varchar(1000) NOT NULL,
  `CONTACT` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL COMMENT '数据状态 :I 非激活 /A  激活',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈';

-- ----------------------------
-- Table structure for systemctl_inner_message
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_inner_message`;
CREATE TABLE `systemctl_inner_message` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL COMMENT '数据状态 :INACTIVE非激活 /ACTIVE 激活',
  `TARGET_USER_ID` varchar(32) NOT NULL COMMENT '目标用户id',
  `MSG_CONTENT` varchar(1000) NOT NULL COMMENT '消息内容',
  `MESSAGE_TYPE` varchar(32) NOT NULL COMMENT '消息类型：点赞,评论',
  `MESSAGE_STATUS` varchar(32) NOT NULL COMMENT '消息状态:已读RECREAD 未读UNREAD',
  `CREATE_TIME` datetime NOT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='内部消息';

-- ----------------------------
-- Table structure for systemctl_location_info
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_location_info`;
CREATE TABLE `systemctl_location_info` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `PINYIN_NAME` varchar(32) DEFAULT NULL COMMENT '拼音码',
  `LOCATION_NAME` varchar(100) NOT NULL COMMENT '位置名称',
  `LOCATION_CODE` varchar(32) NOT NULL COMMENT '位置编号',
  `PARENT_CODE` varchar(32) NOT NULL COMMENT '父节点主键;根节点为0',
  `LOCATION_LEVEL` int(11) DEFAULT '0' COMMENT '深度级别;树的层级',
  `ORDER_NUM` int(11) DEFAULT '0' COMMENT '排序字段',
  `LONGITUDE` varchar(50) NOT NULL DEFAULT '0' COMMENT '经度',
  `LATITUDE` varchar(50) NOT NULL DEFAULT '0' COMMENT '纬度',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  `EXTEND1` varchar(32) DEFAULT NULL COMMENT '扩展字段1',
  `EXTEND2` varchar(32) DEFAULT NULL COMMENT '扩展字段2',
  `EXTEND3` varchar(32) DEFAULT NULL COMMENT '扩展字段3',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_LOCATION_CODE` (`LOCATION_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区位置信息表';

-- ----------------------------
-- Table structure for systemctl_media_info
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_media_info`;
CREATE TABLE `systemctl_media_info` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  `ENTITY_TYPE` varchar(32) NOT NULL COMMENT '关联对象类型(用户头像，帖子，帖子评论，帖子语音....）',
  `ENTITY_ID` varchar(255) NOT NULL COMMENT '关联实体id',
  `MEDIA_TYPE` varchar(32) NOT NULL COMMENT '媒体类型(图片，视频，音频...）',
  `MEDIA_FILENAME` varchar(255) NOT NULL COMMENT '媒体文件名称',
  `MEDIA_URL` varchar(255) DEFAULT NULL COMMENT '媒体文件链接地址',
  `MEDIA_ALIAS` varchar(255) DEFAULT NULL COMMENT '媒体别名',
  `ORDER_NUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多媒体信息关联';

-- ----------------------------
-- Table structure for systemctl_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_operation_record`;
CREATE TABLE `systemctl_operation_record` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  `OPERATION_TYPE` varchar(16) NOT NULL COMMENT '操作类型',
  `ENTITY_TYPE` varchar(32) NOT NULL COMMENT '关联对象类型',
  `ENTITY_ID` varchar(255) NOT NULL COMMENT '关联实体id',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '操作用户',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作记录';

-- ----------------------------
-- Table structure for systemctl_operation_times
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_operation_times`;
CREATE TABLE `systemctl_operation_times` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `REC_DATE` datetime NOT NULL COMMENT '变更时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `REC_STATUS` varchar(16) NOT NULL DEFAULT 'A' COMMENT '数据状态 :I 非激活 /A  激活',
  `OPERATION_TYPE` varchar(16) NOT NULL COMMENT '操作类型',
  `OPERATION_TIMES` int(11) NOT NULL COMMENT '操作计数',
  `ENTITY_TYPE` varchar(32) NOT NULL COMMENT '关联对象类型(商户，商品。。。.）',
  `ENTITY_ID` varchar(255) NOT NULL COMMENT '关联实体id',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '操作用户',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作计数';

-- ----------------------------
-- Table structure for systemctl_sms_record
-- ----------------------------
DROP TABLE IF EXISTS `systemctl_sms_record`;
CREATE TABLE `systemctl_sms_record` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `SMS_CODE` varchar(32) NOT NULL COMMENT '短信编号（可以自己生成，也可以第三方复返回，或者模板编号）',
  `MOBILE` varchar(560) NOT NULL COMMENT '电话号码,可以使多个电话号码，最多50个',
  `SMS_TYPE` varchar(32) NOT NULL COMMENT '短信类型;登录验证码短信，修改密码的短信',
  `SMS_PARAMS` varchar(255) NOT NULL DEFAULT '' COMMENT '短信参数json',
  `SEND_TIME` datetime NOT NULL COMMENT '发送时间',
  `SMS_CONTENT` varchar(500) DEFAULT NULL COMMENT '短信完整内容',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  `VERIFICATION_CODE` varchar(16) DEFAULT NULL COMMENT '验证码',
  PRIMARY KEY (`SEQUENCE_NBR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证码发送记录';

-- ----------------------------
-- Table structure for trade_transactions_bill
-- ----------------------------
DROP TABLE IF EXISTS `trade_transactions_bill`;
CREATE TABLE `trade_transactions_bill` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `BILL_NO` varchar(32) NOT NULL COMMENT '账单编号',
  `AMOUNT` decimal(11,4) NOT NULL COMMENT '账单总金额',
  `BILL_TYPE` varchar(32) NOT NULL COMMENT '交易类型：支付商品，商家结算，红包，虚拟账户充值',
  `BILL_STATUS` varchar(32) NOT NULL COMMENT '账单状态；等待结账，已结账',
  `CREATE_TIME` datetime NOT NULL COMMENT '生成时间',
  `CHECKOUT_TIME` datetime DEFAULT NULL COMMENT '结账时间',
  `REFUND_TIME` datetime DEFAULT NULL,
  `REFUND_AMOUNT` decimal(11,4) DEFAULT NULL COMMENT '退款金额',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `AGENCY_CODE` varchar(32) NOT NULL COMMENT '商家编码',
  `REFUND` varchar(10) DEFAULT 'fasle' COMMENT '是否有退款(如果有退款，则需要根据账单号在退款记录表中查看具体的退款信息）',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_BILL_BILL_NO` (`BILL_NO`),
  KEY `INDEX_BILL_AGENCY_CODE` (`AGENCY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易账单表';

-- ----------------------------
-- Table structure for trade_transactions_record
-- ----------------------------
DROP TABLE IF EXISTS `trade_transactions_record`;
CREATE TABLE `trade_transactions_record` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '物理主键',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `BILL_NO` varchar(32) NOT NULL COMMENT '账单编号',
  `SERIAL_NO` varchar(32) NOT NULL COMMENT '本地交易流水号',
  `TRADE_AMOUNT` decimal(11,4) NOT NULL COMMENT '交易金额',
  `AGENCY_CODE` varchar(32) NOT NULL COMMENT '商家编码',
  `FINISHED_TIME` datetime DEFAULT NULL COMMENT '支付完成时间(前端返回交易完成更新此时间)',
  `ASYNC_FINISH_TIME` datetime DEFAULT NULL COMMENT '异步通知时间',
  `TRADE_TYPE` varchar(32) NOT NULL COMMENT '交易类型：payment:支付(用户支付订单金额到系统)',
  `BILL_TYPE` varchar(32) NOT NULL COMMENT '交易类型：支付商品，商家结算，红包，虚拟账户充值',
  `PAY_METHOD` varchar(32) DEFAULT NULL COMMENT '使用的支付方式(支付宝，微信)',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_RECORD_BILL_NO` (`BILL_NO`),
  UNIQUE KEY `INDEX_RECORD_SERIAL_NO` (`SERIAL_NO`) USING BTREE,
  KEY `INDEX_RECORD_AGENCY_CODE` (`AGENCY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易记录表';

-- ----------------------------
-- Table structure for trade_transactions_serial
-- ----------------------------
DROP TABLE IF EXISTS `trade_transactions_serial`;
CREATE TABLE `trade_transactions_serial` (
  `SEQUENCE_NBR` bigint(32) NOT NULL COMMENT '交易流水UUID',
  `SERIAL_NO` varchar(32) NOT NULL COMMENT '本地交易流水号（25位）',
  `BILL_NO` varchar(32) NOT NULL COMMENT '账单编号',
  `AGENCY_CODE` varchar(32) NOT NULL COMMENT '商家编码',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
  `CHANNEL_SERIAL_NO` varchar(32) DEFAULT NULL COMMENT '支付渠道返回的流水号',
  `TRADE_AMOUNT` decimal(11,4) NOT NULL COMMENT '交易金额',
  `DISCNT_FEE` decimal(11,4) DEFAULT NULL COMMENT '减免金额',
  `DISCNT_DESC` varchar(100) DEFAULT NULL COMMENT '减免备注说明',
  `SYNC_FINISH_TIME` datetime NOT NULL COMMENT '即时返回时间',
  `SEND_TIME` datetime DEFAULT NULL COMMENT '系统向支付平台pingxx发起请求时间',
  `RESULT_MESSAGE` varchar(255) DEFAULT NULL COMMENT '支付结果备注信息(支付渠道，pingxx支付平台都有可能返回结果信息,)',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT '支付完成时间(前端返回交易完成更新此时间)',
  `ASYNC_FINISH_TIME` datetime DEFAULT NULL COMMENT '异步通知时间',
  `CLIENT_IP` varchar(32) DEFAULT NULL COMMENT '发起支付的终端ip',
  `CLIENT_PLATFORM` varchar(50) DEFAULT NULL COMMENT '支付终端设备平台类型',
  `PAY_METHOD` varchar(32) NOT NULL COMMENT '使用的支付方式(支付宝，微信)',
  `TRADE_STATUS` varchar(32) NOT NULL COMMENT '交易状态：支付申请;支付提交;已返回凭证;前端返回(成功，失败),pingxx异步返回(成功，失败)交易过期，交易被取消',
  `TRADE_TYPE` varchar(32) NOT NULL COMMENT '交易类型：支付商品，商家结算，红包，虚拟账户充值',
  `BILL_TYPE` varchar(32) NOT NULL COMMENT '交易类型：支付商品，商家结算，红包，虚拟账户充值',
  `REC_DATE` datetime NOT NULL COMMENT '创建时间',
  `REC_USER_ID` varchar(32) NOT NULL COMMENT '创建者',
  PRIMARY KEY (`SEQUENCE_NBR`),
  UNIQUE KEY `INDEX_SERIAL_SERIAL_NO` (`SERIAL_NO`),
  KEY `INDEX_SERIAL_AGENCY_CODE` (`AGENCY_CODE`),
  KEY `INDEX_SERIAL_BILL_NO` (`BILL_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水表';

INSERT INTO `tyboot`.`auth_login_info` (`SEQUENCE_NBR`, `LOGIN_ID`, `USER_ID`, `PASSWORD`, `SALT`, `USER_TYPE`, `AGENCY_CODE`, `LOCK_STATUS`, `LOCK_DATE`, `LOCK_USER_ID`, `REC_DATE`, `REC_USER_ID`, `ID_TYPE`) VALUES ('15648642156456', 'super', 'SUPER', 'A4A96C933B63989E859C0E76A69AC36', 'ASDFGHJKLP', 'SUPER_ADMIN', 'SUPER_ADMIN', 'UNLOCK', '2017-08-21 11:43:34', NULL, '2017-08-21 11:43:39', 'SYSTEM', 'userName');
