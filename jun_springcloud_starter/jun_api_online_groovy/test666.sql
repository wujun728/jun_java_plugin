/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 10.4.24-MariaDB : Database - test666
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `api_config` */

DROP TABLE IF EXISTS `api_config`;

CREATE TABLE `api_config` (
  `id` varchar(255) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `params` text DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `datasource_id` varchar(255) DEFAULT NULL,
  `previlege` int(11) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `cache_plugin` varchar(255) DEFAULT NULL,
  `cache_plugin_params` varchar(255) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `content_type` varchar(50) DEFAULT NULL,
  `open_trans` int(11) DEFAULT NULL,
  `json_param` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `api_config` */

insert  into `api_config`(`id`,`path`,`name`,`note`,`params`,`status`,`datasource_id`,`previlege`,`group_id`,`cache_plugin`,`cache_plugin_params`,`create_time`,`update_time`,`content_type`,`open_trans`,`json_param`) values ('1','test','test','test','[{\'name\':\'id\',\'type\':\'string\'}]',1,'11',1,'1','','1',NULL,NULL,'application/x-www-form-urlencoded',1,NULL);

/*Table structure for table `api_datasource` */

DROP TABLE IF EXISTS `api_datasource`;

CREATE TABLE `api_datasource` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `driver` varchar(100) DEFAULT NULL,
  `table_sql` varchar(255) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `api_datasource` */

insert  into `api_datasource`(`id`,`name`,`note`,`type`,`url`,`username`,`password`,`driver`,`table_sql`,`create_time`,`update_time`) values ('11','master','master','mysql','jdbc:mysql://localhost:3306/test666?useUnicode=true&characterEncoding=utf-8&useSSL=false','root',NULL,'com.mysql.jdbc.Driver',NULL,NULL,NULL);
 