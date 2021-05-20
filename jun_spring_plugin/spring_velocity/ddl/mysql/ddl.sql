drop table if exists t_news;

create table `t_news` (
  `id` varchar(255) not null,
  `address` varchar(255) default null,
  `create_time` datetime default null,
  `description` varchar(255) default null,
  `news_time` datetime default null,
  `title` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8;
