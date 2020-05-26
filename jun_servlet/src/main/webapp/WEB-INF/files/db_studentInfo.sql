/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community : Database - db_studentinfo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_studentinfo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_studentinfo`;

/*Table structure for table `t_grade` */

DROP TABLE IF EXISTS `t_grade`;

CREATE TABLE `t_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gradeName` varchar(20) DEFAULT NULL,
  `gradeDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `t_grade` */

insert  into `t_grade`(`id`,`gradeName`,`gradeDesc`) values (1,'08计本','08计算机本科'),(2,'09计本','09计算机本科'),(29,'我们的唉','啊');

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `stuId` int(11) NOT NULL AUTO_INCREMENT,
  `stuNo` varchar(20) DEFAULT NULL,
  `stuName` varchar(10) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gradeId` int(11) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `stuDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`stuId`),
  KEY `FK_t_student` (`gradeId`),
  CONSTRAINT `FK_t_student` FOREIGN KEY (`gradeId`) REFERENCES `t_grade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `t_student` */

insert  into `t_student`(`stuId`,`stuNo`,`stuName`,`sex`,`birthday`,`gradeId`,`email`,`stuDesc`) values (2,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(3,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(4,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(5,'080606110','张三','女','1989-11-03',1,'31321@qq.com','Good'),(9,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(10,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(11,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(12,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(13,'080606110','张三','男','1988-11-03',1,'31321@qq.com','Good'),(14,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(15,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(16,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(17,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(18,'090606119','王小美','女','1990-11-03',1,'3112121@qq.com','Good Girls11'),(19,'090606119','王小美','女','1990-11-03',2,'3112121@qq.com','Good Girls'),(20,'090606119','王小美','女','1990-11-03',1,'3112121@qq.com','Good Girls'),(21,'090606119','王小美','女','1990-11-03',2,'3112121@qq.com','Good Girls23'),(25,'312','321','男','2013-05-23',1,'321@11.com','312'),(26,'12','231','男','2013-05-07',1,'321@11.com','312'),(27,'213','312','男','2013-05-14',1,'321@11.com','31222'),(28,'111111','曹小小','女','2013-05-30',1,'111321@11.com','312111\r\n112232'),(29,'1111112','曹小小2','男','2013-05-31',29,'111321@112.com','312111\r\n1122\r\n122'),(30,'1','1','男','2013-04-30',2,'321@11.com','123'),(31,'1','1','男','2013-04-30',2,'321@11.com','123'),(32,'1','1','男','2013-04-30',2,'321@11.com','123'),(33,'321','231','男','2013-05-01',1,'321@11.com','321'),(34,'0901101222222','王靶档22','女','2013-05-01',2,'wanba@12222.com','王八222');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`password`) values (1,'java1234','123456');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
