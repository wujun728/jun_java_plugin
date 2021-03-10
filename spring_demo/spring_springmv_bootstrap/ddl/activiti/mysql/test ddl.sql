create table `a_leavebill` (
  `id` bigint(20) primary key not null auto_increment,
  `days` int(11) default null,
  `content` varchar(255) default null,
  `remark` varchar(255) default null,
  `leavedate` datetime default null,
  `state` int(11) default null,
  `user_id` varchar(32) default null
) engine=innodb default charset=utf8;