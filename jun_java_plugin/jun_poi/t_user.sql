/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community : Database - db_easyui
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_easyui` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_easyui`;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`name`,`phone`,`email`,`qq`) values (1,'张三12233','12345672233','1234567@qq2233.com','12345672233'),(7,'张三7','1234567','1234567@qq.com','1234567'),(9,'张三9','1234567','1234567@qq.com','1234567'),(12,'张三12','1234567','1234567@qq.com','1234567'),(13,'张三13','1234567','1234567@qq.com','1234567'),(14,'张三14','1234567','1234567@qq.com','1234567'),(15,'张三15','1234567','1234567@qq.com','1234567'),(16,'d2233','212133','111321@121331.com','212133'),(19,'是3444','2344','21@q33.com','23123333'),(20,'二哥22','12312','231@qq2.com','3213122222'),(21,'1','1','12345672233@qq.com','1'),(23,'211','2121','321@11.com','21'),(26,'是','是','是','是'),(27,'是','是','是','是'),(28,'1.0','2.0','2.0','2.0'),(29,'我们谁','312.0','321@qq.com','321321.0'),(30,'321.0','321.0','321@qq.com','321.0'),(31,'我们谁','312.0','321@qq.com','321321.0'),(32,'321.0','321.0','321@qq.com','321.0'),(33,'我们谁','312.0','321@qq.com','321321.0'),(34,'321.0','321.0','321@qq.com','321.0'),(35,'我们谁','312.0','321@qq.com','321321.0'),(36,'321.0','321.0','321@qq.com','321.0'),(37,'21.0','21.0','21.0','111.0'),(38,'12.0','我','32.0','321.0'),(39,'12.0','我','32.0','321'),(40,'12.0','我','32.0','321'),(41,'111','21','1221@qq.com','12'),(42,'21','21','1221@qq.com','211111'),(43,'2100','2100','122100@qq.com','211111000'),(44,'21001','21001','1221010@qq.com','2111110001'),(45,'210012','210012','1221010@qq.com','2111110001'),(46,'210012我1','21001221','1221010@qq.com','211111000121'),(48,'21','21','1221@qq.com','21'),(53,'王八1','123.0','123@123.com','123.0'),(54,'王八2','124.0','123@124.com','124.0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
