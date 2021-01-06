CREATE database if NOT EXISTS `xxl-apm` default character set utf8 collate utf8_general_ci;
use `xxl-apm`;


CREATE TABLE `xxl_apm_common_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL COMMENT '注册Key',
  `data` text NOT NULL COMMENT '注册Value有效数据',
  PRIMARY KEY (`id`),
  UNIQUE KEY `I_k` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `xxl_apm_common_registry_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL COMMENT '注册Key',
  `value` varchar(255) NOT NULL COMMENT '注册Value',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `I_k_v` (`key`,`value`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `xxl_apm_common_registry_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` text NOT NULL COMMENT '消息内容',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `xxl_apm_heartbeat_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appname` varchar(100) NOT NULL,
  `addtime` bigint(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `hostname` varchar(200) NOT NULL,
  `heartbeat_data` mediumblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `xxl_apm_event_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appname` varchar(100) NOT NULL,
  `addtime` bigint(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `hostname` varchar(200) NOT NULL,
  `type` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `total_count` bigint(11) NOT NULL,
  `fail_count` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `I_unique` (`appname`,`addtime`,`address`,`type`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `xxl_apm_transaction_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appname` varchar(100) NOT NULL,
  `addtime` bigint(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `hostname` varchar(200) NOT NULL,
  `type` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `total_count` bigint(11) NOT NULL,
  `fail_count` bigint(11) NOT NULL,
  `time_max` int(11) NOT NULL,
  `time_avg` int(11) NOT NULL,
  `time_tp90` int(11) NOT NULL,
  `time_tp95` int(11) NOT NULL,
  `time_tp99` int(11) NOT NULL,
  `time_tp999` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `I_unique` (`appname`,`addtime`,`address`,`type`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8