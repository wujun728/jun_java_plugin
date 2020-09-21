一、概述
DbUtil是一个非常经量级的ORM工具，不属于一个ORM框架，只提供数据库操作的简单实现，包含增、删、改、查、批量以及事务等操作。

二、功能介绍
采用配置文件properties配置数据库信息；
采用java.util.Properties来加载配置文件；
使用dbcp连接池技术；

三、相关数据表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `inserttime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

