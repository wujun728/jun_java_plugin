/*
 navicat premium data transfer

 source server         : localhost
 source server type    : mysql
 source server version : 50716
 source host           : localhost
 source database       : demo

 target server type    : mysql
 target server version : 50716
 file encoding         : utf-8

 date: 03/17/2017 09:30:47 am
*/

set names utf8mb4;
set foreign_key_checks = 0;

-- ----------------------------
--  table structure for `t_news`
-- ----------------------------
drop table if exists `t_news`;
create table `t_news` (
  `id` varchar(255) not null,
  `address` varchar(255) default null,
  `create_time` datetime default null,
  `description` varchar(255) default null,
  `news_time` datetime default null,
  `title` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

set foreign_key_checks = 1;

insert into t_news(id,address,create_time,description,news_time,title) values('8d797e202bd94f8b858d9b73af6b627u','测试多数据源1',now(),'测试多数据源1',now(),'测试多数据源1');
