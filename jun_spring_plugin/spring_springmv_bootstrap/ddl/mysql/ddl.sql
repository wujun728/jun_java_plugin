drop table if exists t_news;
drop table if exists t_sys_permission;
drop table if exists t_sys_role;
drop table if exists t_sys_role_permission;
drop table if exists t_sys_user;
drop table if exists t_sys_user_role;
drop table if exists d_open_survey;
drop table if exists t_import_excel;

create table `d_open_survey` (
  `f_id` varchar(32) not null,
  `f_allowreport` int(11) default null,
  `f_createtime` datetime default null,
  `f_description` varchar(255) default null,
  `f_name` varchar(255) default null,
  `f_pagecount` int(11) default null,
  `f_questioncount` int(11) default null,
  `f_samplecount` int(11) default null,
  `f_samplesum` int(11) default null,
  `f_show` int(11) default null,
  `f_surveyurl` varchar(255) default null,
  `f_tag` varchar(255) default null,
  `f_type` int(11) default null,
  `f_typename` varchar(255) default null,
  `f_uid` varchar(255) default null,
  `f_usersurveycount` int(11) default null,
  `f_viewersum` int(11) default null,
  primary key (`f_id`)
) engine=innodb default charset=utf8mb4;

create table `t_news` (
  `id` varchar(255) not null,
  `address` varchar(255) default null,
  `create_time` datetime default null,
  `description` varchar(255) default null,
  `news_time` datetime default null,
  `title` varchar(255) default null,
  `user_id` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

create table `t_sys_permission` (
  `id` varchar(32) not null,
  `hide` int(11) default null,
  `skey` varchar(255) default null,
  `name` varchar(255) default null,
  `parent_key` varchar(255) default null,
  `sort` int(11) default null,
  `url` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

create table `t_sys_role` (
  `id` varchar(32) not null,
  `code` varchar(255) default null,
  `name` varchar(255) default null,
  `remark` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

create table `t_sys_role_permission` (
  `id` varchar(32) not null,
  `permission_id` varchar(255) default null,
  `role_id` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

create table `t_sys_user` (
  `id` varchar(32) not null,
  `create_time` datetime default null,
  `email` varchar(255) default null,
  `last_login_time` datetime default null,
  `modify_time` datetime default null,
  `name` varchar(255) default null,
  `organize_id` varchar(255) default null,
  `password` varchar(255) default null,
  `salt` varchar(255) default null,
  `status` int(11) default null,
  `true_name` varchar(255) default null,
  primary key (`id`),
  unique key `uk_jhdc5ipoa6kdxy91r16g3df4i` (`email`),
  unique key `uk_mocatd9fh3tj7mv0815baepmq` (`name`),
  unique key `uk_ar8vsmvija6p1mlnw7vkw7aql` (`true_name`)
) engine=innodb default charset=utf8mb4;

create table `t_sys_user_role` (
  `id` varchar(32) not null,
  `role_id` varchar(255) default null,
  `user_id` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

create table `t_import_excel` (
  `id` varchar(32) primary key not null comment '主键',
  `user_name` varchar(255) default null comment '员工名字',
  `start_date` varchar(255) default null comment '开始日期',
  `end_date` varchar(255) default null comment '结束日期',
  `start_time` datetime default null comment '开始时间',
  `end_time` datetime default null comment '结束时间',
  `year` varchar(255) default null comment '月',
  `remark` varchar(255) default null comment '备注',
  `is_modify` int(11) default null comment '修改备注：1=补开始日期与开始时间；2=补结束日期与结束时间；3=再次补结束日期与结束时间；'
) engine=innodb default charset=utf8mb4;
