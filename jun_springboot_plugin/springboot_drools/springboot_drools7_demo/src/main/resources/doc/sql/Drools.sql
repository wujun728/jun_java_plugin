-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.0.21-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 bluesky.rule_action_info 结构
CREATE TABLE IF NOT EXISTS `rule_action_info` (
  `ACTION_ID` bigint(20) NOT NULL COMMENT '主键',
  `ACTION_TYPE` int(11) NOT NULL COMMENT '动作类型(1实现2自身)',
  `ACTION_NAME` varchar(200) NOT NULL COMMENT '动作名称',
  `ACTION_DESC` varchar(3000) DEFAULT NULL COMMENT '动作描述',
  `ACTION_CLASS` varchar(200) NOT NULL COMMENT '动作实现类(包路径)',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ACTION_ID`),
  KEY `ACTION_TYPE` (`ACTION_TYPE`),
  KEY `ACTION_NAME` (`ACTION_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则动作定义信息';

-- 正在导出表  bluesky.rule_action_info 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `rule_action_info` DISABLE KEYS */;
INSERT INTO `rule_action_info` (`ACTION_ID`, `ACTION_TYPE`, `ACTION_NAME`, `ACTION_DESC`, `ACTION_CLASS`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 1, '测试实现类', '测试实现类', 'com.sky.bluesky.service.impl.action.TestActionImpl', 1, 1, '2017-07-24 17:12:32', NULL),
	(2, 2, '自身', '测试自身', 'com.sky.bluesky.model.fact.TestRule', 1, 1, '2017-07-27 10:07:03', NULL);
/*!40000 ALTER TABLE `rule_action_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_action_param_info 结构
CREATE TABLE IF NOT EXISTS `rule_action_param_info` (
  `ACTION_PARAM_ID` bigint(20) NOT NULL COMMENT '主键',
  `ACTION_ID` bigint(20) NOT NULL COMMENT '动作id',
  `ACTION_PARAM_NAME` varchar(200) NOT NULL COMMENT '参数名称',
  `ACTION_PARAM_DESC` varchar(3000) DEFAULT NULL COMMENT '参数描述',
  `PARAM_IDENTIFY` varchar(200) NOT NULL COMMENT '标识',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ACTION_PARAM_ID`),
  KEY `ACTION_ID` (`ACTION_ID`),
  KEY `ACTION_PARAM_NAME` (`ACTION_PARAM_NAME`),
  KEY `PARAM_IDENTIFY` (`PARAM_IDENTIFY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作参数信息表';

-- 正在导出表  bluesky.rule_action_param_info 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `rule_action_param_info` DISABLE KEYS */;
INSERT INTO `rule_action_param_info` (`ACTION_PARAM_ID`, `ACTION_ID`, `ACTION_PARAM_NAME`, `ACTION_PARAM_DESC`, `PARAM_IDENTIFY`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 1, '测试', '测试', 'message', 1, 1, '2017-07-24 18:24:28', NULL),
	(2, 2, '测试', '测试', 'score', 1, 1, '2017-07-17 10:07:46', NULL);
/*!40000 ALTER TABLE `rule_action_param_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_action_param_value_info 结构
CREATE TABLE IF NOT EXISTS `rule_action_param_value_info` (
  `ACTION_PARAM_VALUE_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_ACTION_REL_ID` bigint(20) NOT NULL COMMENT '动作规则关系主键',
  `ACTION_PARAM_ID` bigint(20) NOT NULL COMMENT '动作参数',
  `PARAM_VALUE` varchar(200) NOT NULL COMMENT '参数值',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ACTION_PARAM_VALUE_ID`),
  KEY `RULE_ACTION_REL_ID` (`RULE_ACTION_REL_ID`),
  KEY `ACTION_PARAM_ID` (`ACTION_PARAM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作参数对应的属性值信息表';

-- 正在导出表  bluesky.rule_action_param_value_info 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `rule_action_param_value_info` DISABLE KEYS */;
INSERT INTO `rule_action_param_value_info` (`ACTION_PARAM_VALUE_ID`, `RULE_ACTION_REL_ID`, `ACTION_PARAM_ID`, `PARAM_VALUE`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 1, 1, 'hello', 1, 1, '2017-07-24 19:29:17', NULL),
	(2, 2, 2, '#3#*5', 1, 1, '2017-07-27 10:10:17', NULL);
/*!40000 ALTER TABLE `rule_action_param_value_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_action_rule_rel 结构
CREATE TABLE IF NOT EXISTS `rule_action_rule_rel` (
  `RULE_ACTION_REL_ID` bigint(20) NOT NULL COMMENT '主键',
  `ACTION_ID` bigint(20) NOT NULL COMMENT '动作',
  `RULE_ID` bigint(20) NOT NULL COMMENT '规则',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`RULE_ACTION_REL_ID`),
  KEY `ACTION_ID` (`ACTION_ID`),
  KEY `RULE_ID` (`RULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作与规则信息关系表';

-- 正在导出表  bluesky.rule_action_rule_rel 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `rule_action_rule_rel` DISABLE KEYS */;
INSERT INTO `rule_action_rule_rel` (`RULE_ACTION_REL_ID`, `ACTION_ID`, `RULE_ID`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 1, 1, 1, 1, '2017-07-24 18:59:11', NULL),
	(2, 2, 1, 1, 1, '2017-07-27 10:08:25', NULL);
/*!40000 ALTER TABLE `rule_action_rule_rel` ENABLE KEYS */;


-- 导出  表 bluesky.rule_condition_info 结构
CREATE TABLE IF NOT EXISTS `rule_condition_info` (
  `CONDITION_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_ID` bigint(20) NOT NULL COMMENT '规则',
  `CONDITION_NAME` varchar(3000) NOT NULL COMMENT '条件名称',
  `CONDITION_EXPRESSION` varchar(3000) NOT NULL COMMENT '条件表达式',
  `CONDITION_DESC` varchar(3000) NOT NULL COMMENT '条件描述',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CONDITION_ID`),
  KEY `RULE_ID` (`RULE_ID`),
  KEY `CONDITION_NAME` (`CONDITION_NAME`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则条件信息表';

-- 正在导出表  bluesky.rule_condition_info 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `rule_condition_info` DISABLE KEYS */;
INSERT INTO `rule_condition_info` (`CONDITION_ID`, `RULE_ID`, `CONDITION_NAME`, `CONDITION_EXPRESSION`, `CONDITION_DESC`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 1, '测试', '$2$==100', '$金额$==100', 1, 1, '2017-07-24 20:04:52', NULL);
/*!40000 ALTER TABLE `rule_condition_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_entity_info 结构
CREATE TABLE IF NOT EXISTS `rule_entity_info` (
  `ENTITY_ID` bigint(20) NOT NULL COMMENT '主键',
  `ENTITY_NAME` varchar(50) NOT NULL COMMENT '名称',
  `ENTITY_DESC` varchar(3000) NOT NULL COMMENT '描述',
  `ENTITY_IDENTIFY` varchar(50) NOT NULL COMMENT '标识',
  `PKG_NAME` varchar(200) NOT NULL COMMENT '包路径',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效(1是0否)',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ENTITY_ID`),
  KEY `ENTITY_IDENTIFY` (`ENTITY_IDENTIFY`),
  KEY `ENTITY_NAME` (`ENTITY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则引擎实体信息表';

-- 正在导出表  bluesky.rule_entity_info 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `rule_entity_info` DISABLE KEYS */;
INSERT INTO `rule_entity_info` (`ENTITY_ID`, `ENTITY_NAME`, `ENTITY_DESC`, `ENTITY_IDENTIFY`, `PKG_NAME`, `CRE_USER_ID`, `CRE_TIME`, `IS_EFFECT`, `REMARK`) VALUES
	(1, '测试规则', '测试规则引擎', 'testRule', 'com.sky.bluesky.model.fact.TestRule', 1, '2017-07-20 11:41:32', 1, NULL);
/*!40000 ALTER TABLE `rule_entity_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_entity_item_info 结构
CREATE TABLE IF NOT EXISTS `rule_entity_item_info` (
  `ITEM_ID` bigint(20) NOT NULL COMMENT '主键',
  `ENTITY_ID` bigint(20) NOT NULL COMMENT '实体ID',
  `ITEM_NAME` varchar(50) NOT NULL COMMENT '字段名称',
  `ITEM_IDENTIFY` varchar(50) NOT NULL COMMENT '字段标识',
  `ITEM_DESC` varchar(50) DEFAULT NULL COMMENT '属性描述',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ITEM_ID`),
  KEY `ENTITY_ID` (`ENTITY_ID`),
  KEY `ITEM_IDENTIFY` (`ITEM_IDENTIFY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体属性信息';

-- 正在导出表  bluesky.rule_entity_item_info 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `rule_entity_item_info` DISABLE KEYS */;
INSERT INTO `rule_entity_item_info` (`ITEM_ID`, `ENTITY_ID`, `ITEM_NAME`, `ITEM_IDENTIFY`, `ITEM_DESC`, `CRE_USER_ID`, `CRE_TIME`, `IS_EFFECT`, `REMARK`) VALUES
	(1, 1, '消息', 'message', '消息信息', 1, '2017-07-20 16:20:56', 1, NULL),
	(2, 1, '金额', 'amount', '消费金额', 1, '2017-07-20 16:21:47', 1, NULL),
	(3, 1, '积分', 'score', '积分', 1, '2017-07-20 16:23:55', 1, NULL);
/*!40000 ALTER TABLE `rule_entity_item_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_info 结构
CREATE TABLE IF NOT EXISTS `rule_info` (
  `RULE_ID` bigint(20) NOT NULL COMMENT '主键',
  `SCENE_ID` bigint(20) NOT NULL COMMENT '场景',
  `RULE_NAME` varchar(50) NOT NULL COMMENT '名称',
  `RULE_DESC` varchar(3000) DEFAULT NULL COMMENT '描述',
  `RULE_ENABLED` int(11) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`RULE_ID`),
  KEY `SCENE_ID` (`SCENE_ID`),
  KEY `RULE_NAME` (`RULE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则信息';

-- 正在导出表  bluesky.rule_info 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `rule_info` DISABLE KEYS */;
INSERT INTO `rule_info` (`RULE_ID`, `SCENE_ID`, `RULE_NAME`, `RULE_DESC`, `RULE_ENABLED`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 1, '测试', NULL, 1, 1, 1, '2017-07-25 10:55:36', NULL);
/*!40000 ALTER TABLE `rule_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_property_info 结构
CREATE TABLE IF NOT EXISTS `rule_property_info` (
  `RULE_PROPERTY_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_PROPERTY_IDENTIFY` varchar(200) NOT NULL COMMENT '标识',
  `RULE_PROPERTY_NAME` varchar(200) NOT NULL COMMENT '名称',
  `RULE_PROPERTY_DESC` varchar(3000) DEFAULT NULL COMMENT '描述',
  `DEFAULT_VALUE` varchar(200) DEFAULT NULL COMMENT '默认值',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`RULE_PROPERTY_ID`),
  KEY `RULE_PROPERTY_IDENTIFY` (`RULE_PROPERTY_IDENTIFY`),
  KEY `RULE_PROPERTY_NAME` (`RULE_PROPERTY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则基础属性信息表';

-- 正在导出表  bluesky.rule_property_info 的数据：~12 rows (大约)
/*!40000 ALTER TABLE `rule_property_info` DISABLE KEYS */;
INSERT INTO `rule_property_info` (`RULE_PROPERTY_ID`, `RULE_PROPERTY_IDENTIFY`, `RULE_PROPERTY_NAME`, `RULE_PROPERTY_DESC`, `DEFAULT_VALUE`, `IS_EFFECT`, `REMARK`) VALUES
	(1, 'salience', '优先级', '用来设置规则执行的优先级，salience 属性的值是一个数字，数字越大执行优先级越高，同时它的值可以是一个负数。默认情况下，规则的ssalience 默认值为0，所以如果我们不手动设置规则的salience 属性，那么它的执行顺序是随机（但是一般都是按照加载顺序。）', '0', 1, NULL),
	(2, 'no-loop', '是否可被重复执行', '是否允许规则多次执行, 值为布尔类型, 默认是false, 即当前的规则只要满足条件, 可以无限次执行; 对当前传入workingMemory中的Fact对象进行修改或者个数的增减时, 就会触发规则重新匹配执行; 设置属性no-loop true, 表示当前规则只执行一次, 即使RHS中更新了当前Fact对象也不会再次执行该规则了. 不过当其他规则里更新了Fact对象时, 即使有no-loop true也会触发, 即no-loop true仅表示本规的RHS中有更新时不重复执行', 'false', 1, NULL),
	(3, 'date-effective', '生效时间', '设置规则的生效时间, 当前系统时间>=date-effective时才会触发执行, 值是一个日期格式的字符串, 推荐用法是手动在java代码中设置当前系统的时间格式, 然后按照格式指定时间. 比如: date-effective "2016-01-31 23:59:59"', NULL, 1, NULL),
	(4, 'date-expires', '失效时间', '设置规则的失效时间, 跟生效时间正好相反', NULL, 1, NULL),
	(5, 'enabled', '是否可用', '表示该规则是否可用, 值为布尔类型, 默认是true, 设置成false则该规则就不会被执行了', 'true', 0, '规则信息上已经有此属性，所以默认当前属性无效'),
	(6, 'dialect', '语言类型', '设置语言类型, 值为字符串, 一般有两种语言,mvel和java, 默认为java', 'java', 1, NULL),
	(7, 'duration', '规则定时', '规则定时, 值为长整型, 单位为毫秒, 如 duration 3000, 表示规则在3秒后执行(另外的线程中执行)', '3000', 1, NULL),
	(8, 'lock-on-active', '确认规则只执行一次', '是no-loop的增强版, 与其他属性配合使用;规则的重复执行不一定是本身触发的, 也可能是其他规则触发的, 当在规则上使用ruleflow-group属性或agenda-group属性时, 将lock-on-active属性值设置为true，可避免因某些Fact对象被修改而使已经执行过的规则再次被激活执行', 'false', 1, NULL),
	(9, 'activation-group', '规则分组', '作用是将规则分组, 值为字符串表示组名,这样在执行的时候,具有相同activation-group属性的规则中只要有一个会被执行,其它的规则都将不再执行。即在具有相同activation-group属性的规则当中,只有一个规则会被执行,其它规则都将不会被执行.相同activation-group属性的规则中哪一个会先执行,则可以用salience之类的属性来实现', NULL, 1, NULL),
	(10, 'agenda-group', '议程分组', 'Agenda Group 是用来在Agenda 的基础之上，对现在的规则进行再次分组，具体的分组方法可以采用为规则添加agenda-group 属性来实现。 \r\nagenda-group 属性的值也是一个字符串，通过这个字符串，可以将规则分为若干个Agenda Group，默认情况下，引擎在调用这些设置了agenda-group 属性的规则的时候需要显示的指定某个Agenda Group 得到Focus（焦点），这样位于该Agenda Group 当中的规则才会触发执行，否则将不执行', NULL, 1, NULL),
	(11, 'ruleflow-group', '规则流分组', '在使用规则流的时候要用到ruleflow-group 属性，该属性的值为一个字符串，作用是用来将规则划分为一个个的组，然后在规则流当中通过使用ruleflow-group 属性的值，从而使用对应的规则', NULL, 1, NULL),
	(12, 'auto-focus', '自动获取焦点', '前面我们也提到auto-focus 属性，它的作用是用来在已设置了agenda-group 的规则上设置该规则是否可以自动独取Focus，如果该属性设置为true，那么在引擎执行时，就不需要显示的为某个Agenda Group 设置Focus，否则需要', 'true', 1, NULL);
/*!40000 ALTER TABLE `rule_property_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_property_rel 结构
CREATE TABLE IF NOT EXISTS `rule_property_rel` (
  `RULE_PRO_REL_ID` bigint(20) NOT NULL COMMENT '主键',
  `RULE_ID` bigint(20) NOT NULL COMMENT '规则',
  `RULE_PROPERTY_ID` bigint(20) NOT NULL COMMENT '规则属性',
  `RULE_PROPERTY_VALUE` varchar(200) NOT NULL COMMENT '规则属性值',
  PRIMARY KEY (`RULE_PRO_REL_ID`),
  KEY `RULE_ID` (`RULE_ID`),
  KEY `RULE_PROPERTY_ID` (`RULE_PROPERTY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则属性配置表';

-- 正在导出表  bluesky.rule_property_rel 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `rule_property_rel` DISABLE KEYS */;
INSERT INTO `rule_property_rel` (`RULE_PRO_REL_ID`, `RULE_ID`, `RULE_PROPERTY_ID`, `RULE_PROPERTY_VALUE`) VALUES
	(1, 1, 1, '1'),
	(2, 1, 2, 'true'),
	(3, 1, 8, 'true');
/*!40000 ALTER TABLE `rule_property_rel` ENABLE KEYS */;


-- 导出  表 bluesky.rule_scene_entity_rel 结构
CREATE TABLE IF NOT EXISTS `rule_scene_entity_rel` (
  `SCENE_ENTITY_REL_ID` bigint(20) NOT NULL COMMENT '主键',
  `SCENE_ID` bigint(20) DEFAULT NULL COMMENT '场景',
  `ENTITY_ID` bigint(20) DEFAULT NULL COMMENT '实体',
  PRIMARY KEY (`SCENE_ENTITY_REL_ID`),
  KEY `SCENE_ID` (`SCENE_ID`),
  KEY `ENTITY_ID` (`ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='场景实体关联表';

-- 正在导出表  bluesky.rule_scene_entity_rel 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `rule_scene_entity_rel` DISABLE KEYS */;
INSERT INTO `rule_scene_entity_rel` (`SCENE_ENTITY_REL_ID`, `SCENE_ID`, `ENTITY_ID`) VALUES
	(1, 1, 1);
/*!40000 ALTER TABLE `rule_scene_entity_rel` ENABLE KEYS */;


-- 导出  表 bluesky.rule_scene_info 结构
CREATE TABLE IF NOT EXISTS `rule_scene_info` (
  `SCENE_ID` bigint(20) NOT NULL COMMENT '主键',
  `SCENE_IDENTIFY` varchar(50) NOT NULL COMMENT '标识',
  `SCENE_TYPE` int(11) DEFAULT NULL COMMENT '类型(暂不使用)',
  `SCENE_NAME` varchar(50) NOT NULL COMMENT '名称',
  `SCENE_DESC` varchar(3000) DEFAULT NULL COMMENT '描述',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`SCENE_ID`),
  KEY `SCENE_IDENTIFY` (`SCENE_IDENTIFY`),
  KEY `SCENE_TYPE` (`SCENE_TYPE`),
  KEY `SCENE_NAME` (`SCENE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则引擎使用场景';

-- 正在导出表  bluesky.rule_scene_info 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `rule_scene_info` DISABLE KEYS */;
INSERT INTO `rule_scene_info` (`SCENE_ID`, `SCENE_IDENTIFY`, `SCENE_TYPE`, `SCENE_NAME`, `SCENE_DESC`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, 'test', NULL, '测试', '测试规则引擎', 1, 1, '2017-07-20 16:56:10', '测试');
/*!40000 ALTER TABLE `rule_scene_info` ENABLE KEYS */;


-- 导出  表 bluesky.rule_variable 结构
CREATE TABLE IF NOT EXISTS `rule_variable` (
  `VARIABLE_ID` bigint(20) NOT NULL COMMENT '主键',
  `VARIABLE_NAME` varchar(200) NOT NULL COMMENT '变量名称',
  `VARIABLE_TYPE` int(11) NOT NULL COMMENT '变量类型（1条件2动作）',
  `DEFAULT_VALUE` varchar(200) NOT NULL COMMENT '默认值',
  `VALUE_TYPE` int(11) NOT NULL COMMENT '数值类型（ 1字符型 2数字型 3 日期型）',
  `VARIABLE_VALUE` varchar(200) NOT NULL COMMENT '变量值',
  `IS_EFFECT` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `CRE_USER_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CRE_TIME` datetime NOT NULL COMMENT '创建时间',
  `REMARK` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`VARIABLE_ID`),
  KEY `VARIABLE_TYPE` (`VARIABLE_TYPE`),
  KEY `VALUE_TYPE` (`VALUE_TYPE`),
  KEY `VARIABLE_NAME` (`VARIABLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则引擎常用变量';

-- 正在导出表  bluesky.rule_variable 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `rule_variable` DISABLE KEYS */;
INSERT INTO `rule_variable` (`VARIABLE_ID`, `VARIABLE_NAME`, `VARIABLE_TYPE`, `DEFAULT_VALUE`, `VALUE_TYPE`, `VARIABLE_VALUE`, `IS_EFFECT`, `CRE_USER_ID`, `CRE_TIME`, `REMARK`) VALUES
	(1, '增加积分100', 2, '100', 2, '100', 1, 1, '2017-07-20 18:38:01', NULL),
	(2, '金额大于100', 1, '100', 2, '100', 1, 1, '2017-07-20 18:39:18', NULL);
/*!40000 ALTER TABLE `rule_variable` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
