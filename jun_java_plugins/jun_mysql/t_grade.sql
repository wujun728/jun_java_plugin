/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `t_grade` (
	`id` int ,
	`stuName` varchar (60),
	`course` varchar (60),
	`score` int 
); 
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('1','张三','语文','91');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('2','张三','数学','90');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('3','张三','英语','87');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('4','李四','语文','79');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('5','李四','数学','95');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('6','李四','英语','80');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('7','王五','语文','77');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('8','王五','数学','81');
insert into `t_grade` (`id`, `stuName`, `course`, `score`) values('9','王五','英语','89');
