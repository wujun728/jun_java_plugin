

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wx_cpconfig
-- ----------------------------
DROP TABLE IF EXISTS `wx_cpconfig`;
CREATE TABLE `wx_cpconfig` (
  `id` varchar(50) NOT NULL,
  `siteId` varchar(50) NOT NULL COMMENT '站点Id',
  `appId` varchar(500) DEFAULT NULL COMMENT '开发者Id',
  `appSecret` varchar(500) DEFAULT NULL COMMENT '应用密钥',
  `token` varchar(500) DEFAULT NULL COMMENT '开发者Id',
  `encodingAESKey` varchar(500) DEFAULT NULL COMMENT '消息加解密密钥',
  `wxId` varchar(500) DEFAULT NULL COMMENT '原始ID',
  `oauth2redirectUri` varchar(500) DEFAULT NULL COMMENT '微信重定向地址',
  `httpProxyHost` varchar(500) DEFAULT NULL COMMENT 'http代理地址',
  `httpProxyPort` int(11) DEFAULT NULL COMMENT 'http代理端口',
  `httpProxyUsername` varchar(500) DEFAULT NULL COMMENT 'http代理账号',
  `httpProxyPassword` varchar(500) DEFAULT NULL COMMENT 'http代理密码',
  `certificateFile` varchar(500) DEFAULT NULL COMMENT '证书地址',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信号需要的配置信息';

-- ----------------------------
-- Records of wx_cpconfig
-- ----------------------------

-- ----------------------------
-- Table structure for wx_menu
-- ----------------------------
DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE `wx_menu` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `type` varchar(50) DEFAULT NULL COMMENT '菜单类型',
  `keyword` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `pid` varchar(50) DEFAULT NULL COMMENT '上级菜单id',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `siteId` varchar(50) DEFAULT NULL COMMENT '站点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wx_menu
-- ----------------------------

-- ----------------------------
-- Table structure for wx_mpconfig
-- ----------------------------
DROP TABLE IF EXISTS `wx_mpconfig`;
CREATE TABLE `wx_mpconfig` (
  `id` varchar(50) NOT NULL,
  `siteId` varchar(50) NOT NULL COMMENT '站点Id',
  `appId` varchar(500) DEFAULT NULL COMMENT '开发者Id',
  `secret` varchar(500) DEFAULT NULL COMMENT '应用密钥',
  `token` varchar(500) DEFAULT NULL COMMENT '开发者Id',
  `aesKey` varchar(500) DEFAULT NULL COMMENT '消息加解密密钥',
  `wxId` varchar(500) DEFAULT NULL COMMENT '原始ID',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  `partnerId` varchar(500) DEFAULT NULL,
  `partnerKey` varchar(500) DEFAULT NULL,
  `oauth2` int(11) DEFAULT 1 COMMENT '是否支持微信oauth2.0协议,0是不支持,1是支持',
  `httpProxyHost` varchar(500) DEFAULT NULL COMMENT 'http代理地址',
  `httpProxyPort` int(11) DEFAULT NULL COMMENT 'http代理端口',
  `httpProxyUsername` varchar(500) DEFAULT NULL COMMENT 'http代理账号',
  `httpProxyPassword` varchar(500) DEFAULT NULL COMMENT 'http代理密码',
  `certificateFile` varchar(500) DEFAULT NULL COMMENT '证书地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信号需要的配置信息';

-- ----------------------------
-- Records of wx_mpconfig
-- ----------------------------
INSERT INTO `wx_mpconfig` VALUES ('s_10006', 's_10006', 'wx', 'abc', 'abc', '1', 'gh', '1', null, null, 1, null, null, null, null, null);
