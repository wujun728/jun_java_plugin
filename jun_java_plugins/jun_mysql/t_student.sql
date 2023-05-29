/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `t_student` (
	`id` double ,
	`stuName` varchar (60),
	`age` double ,
	`sex` varchar (30),
	`gradeName` varchar (60)
); 
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('1','张三','23','男','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('2','张三丰','25','男','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('3','李四','23','男','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('4','王五','22','男','三年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('5','珍妮','21','女','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('6','李娜','26','女','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('7','王峰','20','男','三年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('8','梦娜','21','女','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('9','小黑','22','男','一年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('10','追风','25','男','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('11','小小张三','21',NULL,'二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('12','小张三','23','男','二年级');
insert into `t_student` (`id`, `stuName`, `age`, `sex`, `gradeName`) values('13','张三锋小','24',NULL,'二年级');
