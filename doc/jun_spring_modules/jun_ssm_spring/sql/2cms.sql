/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50716
Source Host           : 127.0.0.1:3306
Source Database       : springrain

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-03-01 15:09:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_channel
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel`;
CREATE TABLE `cms_channel` (
  `id` varchar(50) NOT NULL,
  `name` varchar(500) DEFAULT NULL COMMENT '名称',
  `pid` varchar(50) DEFAULT NULL COMMENT '父类ID',
  `comcode` varchar(1000) NOT NULL COMMENT '编号 通过 ,, 间隔',
  `siteId` varchar(50) NOT NULL COMMENT '网站ID',
  `positionLevel` int(11) NOT NULL DEFAULT '0' COMMENT '0导航,1-10个级别',
  `keywords` varchar(1000) DEFAULT NULL COMMENT '关键字',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `lookcount` int(11) DEFAULT NULL COMMENT '打开次数',
  `sortno` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='栏目表';

-- ----------------------------
-- Records of cms_channel
-- ----------------------------

-- ----------------------------
-- Table structure for cms_channel_content
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel_content`;
CREATE TABLE `cms_channel_content` (
  `id` varchar(50) NOT NULL,
  `siteId` varchar(50) NOT NULL,
  `channelId` varchar(50) NOT NULL,
  `contentId` varchar(50) NOT NULL,
  `sortno` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='栏目内容中间表';

-- ----------------------------
-- Records of cms_channel_content
-- ----------------------------

-- ----------------------------
-- Table structure for cms_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_comment`;
CREATE TABLE `cms_comment` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(50) DEFAULT NULL COMMENT '评论用户ID',
  `businessId` varchar(50) NOT NULL COMMENT '业务Id',
  `pid` varchar(50) NOT NULL COMMENT '父级评论Id',
  `siteId` int(11) NOT NULL COMMENT '站点ID',
  `createDate` datetime NOT NULL COMMENT '评论时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `content` longtext COMMENT '评论内容',
  `ups` smallint(6) NOT NULL DEFAULT '0' COMMENT '支持数',
  `downs` smallint(6) NOT NULL DEFAULT '0' COMMENT '反对数',
  `checked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否审核',
  `score` int(11) DEFAULT NULL COMMENT '评分',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `userName` varchar(50) DEFAULT NULL COMMENT '姓名',
  `bak1` varchar(255) DEFAULT NULL,
  `bak2` varchar(255) DEFAULT NULL,
  `bak3` varchar(255) DEFAULT NULL,
  `bak4` varchar(255) DEFAULT NULL,
  `bak5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='CMS评论表';

-- ----------------------------
-- Records of cms_comment
-- ----------------------------

-- ----------------------------
-- Table structure for cms_content
-- ----------------------------
DROP TABLE IF EXISTS `cms_content`;
CREATE TABLE `cms_content` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `title` varchar(500) DEFAULT NULL,
  `mintitle` varchar(200) DEFAULT NULL COMMENT '小标题',
  `summary` text COMMENT '摘要',
  `content` text COMMENT '内容',
  `keywords` varchar(1000) DEFAULT NULL COMMENT '关键字',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `lookcount` int(11) DEFAULT NULL COMMENT '打开次数',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '创建人',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `source` varchar(1000) DEFAULT NULL COMMENT '来源',
  `sourceurl` varchar(1000) DEFAULT NULL COMMENT '来源地址',
  `status` int(11) DEFAULT NULL COMMENT '状态  0未审核  1审核通过',
  `active` int(11) DEFAULT NULL COMMENT '是否可用',
  `commentPerm` int(11) DEFAULT NULL COMMENT '评论开关  0不允许评论  1 允许评论',
  `sortno` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容表';

-- ----------------------------
-- Records of cms_content
-- ----------------------------

-- ----------------------------
-- Table structure for cms_friend_site
-- ----------------------------
DROP TABLE IF EXISTS `cms_friend_site`;
CREATE TABLE `cms_friend_site` (
  `id` varchar(50) NOT NULL,
  `siteId` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `linkType` varchar(50) NOT NULL COMMENT '跳出类型,_blank',
  `url` varchar(1000) NOT NULL COMMENT '网站地址',
  `logo` varchar(2000) NOT NULL COMMENT '网站logo',
  `sortno` int(11) DEFAULT NULL COMMENT '排序',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='友情链接';

-- ----------------------------
-- Records of cms_friend_site
-- ----------------------------

-- ----------------------------
-- Table structure for cms_link
-- ----------------------------
DROP TABLE IF EXISTS `cms_link`;
CREATE TABLE `cms_link` (
  `id` varchar(50) NOT NULL,
  `name` varchar(1000) DEFAULT NULL,
  `defaultLink` varchar(1000) NOT NULL COMMENT '默认URL地址',
  `link` varchar(1000) NOT NULL COMMENT '使用的URL',
  `siteId` varchar(50) NOT NULL COMMENT '网站ID',
  `businessId` varchar(50) NOT NULL COMMENT '业务Id',
  `lookcount` int(11) DEFAULT NULL COMMENT '打开次数',
  `modelType` int(11) NOT NULL DEFAULT '0' COMMENT '0site,1channel,2content,3投票 4,站长后台模板(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)',
  `ftlfile` varchar(1000) NOT NULL COMMENT '当前渲染使用的模板路径',
  `nodeftlfile` varchar(1000) DEFAULT NULL COMMENT '子内容使用的ftl模板文件',
  `statichtml` int(11) NOT NULL DEFAULT '0' COMMENT '是否静态化 0否,1是',
  `sortno` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  `jumpType` int(11) DEFAULT NULL COMMENT '跳转方式',
  `loginuser` int(11) DEFAULT NULL COMMENT '是否需要登录访问  0否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务链接表';

-- ----------------------------
-- Records of cms_link
-- ----------------------------

-- ----------------------------
-- Table structure for cms_praise
-- ----------------------------
DROP TABLE IF EXISTS `cms_praise`;
CREATE TABLE `cms_praise` (
  `id` varchar(50) NOT NULL,
  `businessId` varchar(50) NOT NULL COMMENT '业务id',
  `userId` varchar(50) NOT NULL COMMENT '点赞人',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  `bak1` varchar(255) DEFAULT NULL,
  `bak2` varchar(255) DEFAULT NULL,
  `bak3` varchar(255) DEFAULT NULL,
  `bak4` varchar(255) DEFAULT NULL,
  `bak5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Records of cms_praise
-- ----------------------------

-- ----------------------------
-- Table structure for cms_property
-- ----------------------------
DROP TABLE IF EXISTS `cms_property`;
CREATE TABLE `cms_property` (
  `id` varchar(100) NOT NULL COMMENT 'siteId_code,保证一个站点下code唯一',
  `siteId` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL COMMENT '系统级别的编码,一个站点不可重复',
  `inputType` int(11) NOT NULL DEFAULT '0' COMMENT '0text,1date,2datatime,3int,4float,5select,6file,7img,8imgs',
  `businessId` varchar(50) NOT NULL COMMENT '业务Id',
  `modelType` int(11) NOT NULL DEFAULT '0' COMMENT '0site,1channel,2content,3投票(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)',
  `createPerson` varchar(50) NOT NULL COMMENT '创建人',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `defaultValue` varchar(100) DEFAULT NULL COMMENT '默认值',
  `pvalue` varchar(500) DEFAULT NULL,
  `style` varchar(500) DEFAULT NULL COMMENT '样式',
  `leafUse` int(11) NOT NULL DEFAULT '0' COMMENT '自是否可用,0不可用,1可以用',
  `sortno` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自定义属性表';

-- ----------------------------
-- Records of cms_property
-- ----------------------------

-- ----------------------------
-- Table structure for cms_site
-- ----------------------------
DROP TABLE IF EXISTS `cms_site`;
CREATE TABLE `cms_site` (
  `id` varchar(50) NOT NULL,
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `userId` varchar(50) NOT NULL COMMENT '用户Id',
  `name` varchar(500) DEFAULT NULL COMMENT '名称',
  `title` varchar(500) DEFAULT NULL,
  `domainurl` varchar(2000) DEFAULT NULL COMMENT '网站域名',
  `logo` varchar(2000) NOT NULL COMMENT '网站logo',
  `footer` text NOT NULL COMMENT '页脚',
  `qq` varchar(50) NOT NULL COMMENT 'QQ',
  `phone` varchar(50) NOT NULL COMMENT '电话',
  `contacts` varchar(50) NOT NULL COMMENT '联系人',
  `keywords` varchar(1000) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `themeId` varchar(50) DEFAULT NULL COMMENT '主题Id',
  `lookcount` int(11) DEFAULT NULL COMMENT '打开次数',
  `siteType` int(11) NOT NULL DEFAULT '0' COMMENT '11微信,12企业号,13pc,14wap,15投票',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  `springbeanid` varchar(100) DEFAULT NULL COMMENT 'springbeanid',
  `orgId` varchar(50) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`),
  KEY `orgId` (`orgId`),
  CONSTRAINT `orgId` FOREIGN KEY (`orgId`) REFERENCES `t_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站点表';

-- ----------------------------
-- Records of cms_site
-- ----------------------------

-- ----------------------------
-- Table structure for cms_template
-- ----------------------------
DROP TABLE IF EXISTS `cms_template`;
CREATE TABLE `cms_template` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `name` varchar(500) NOT NULL COMMENT '名称',
  `description` varchar(2000) DEFAULT NULL COMMENT '备注',
  `ftlfile` varchar(1000) DEFAULT NULL COMMENT '渲染使用的模板路径',
  `imgfile` varchar(1000) DEFAULT NULL COMMENT '缩略图路径路径',
  `modelType` int(11) NOT NULL DEFAULT '0' COMMENT '0site,1channel,2content,3投票(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)',
  `usecount` int(11) DEFAULT NULL COMMENT '使用次数',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板表';

-- ----------------------------
-- Records of cms_template
-- ----------------------------

-- ----------------------------
-- Table structure for cms_theme
-- ----------------------------
DROP TABLE IF EXISTS `cms_theme`;
CREATE TABLE `cms_theme` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `name` varchar(500) NOT NULL COMMENT '名称',
  `usecount` int(11) DEFAULT NULL COMMENT '使用次数',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0不可用,1可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主题表';

-- ----------------------------
-- Records of cms_theme
-- ----------------------------

-- ----------------------------
-- Table structure for cms_theme_template
-- ----------------------------
DROP TABLE IF EXISTS `cms_theme_template`;
CREATE TABLE `cms_theme_template` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `themeId` varchar(50) NOT NULL COMMENT '主题Id',
  `templateId` varchar(50) NOT NULL COMMENT '模板Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主题和模板中间表';

-- ----------------------------
-- Records of cms_theme_template
-- ----------------------------
