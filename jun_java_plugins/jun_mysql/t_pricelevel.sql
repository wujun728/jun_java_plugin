/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `t_pricelevel` (
	`id` int ,
	`priceLevel` int ,
	`price` float ,
	`description` varchar (300)
); 
insert into `t_pricelevel` (`id`, `priceLevel`, `price`, `description`) values('1','1','80.00','价格贵的书');
insert into `t_pricelevel` (`id`, `priceLevel`, `price`, `description`) values('2','2','60.00','价格适中的书');
insert into `t_pricelevel` (`id`, `priceLevel`, `price`, `description`) values('3','3','40.00','价格便宜的书');
