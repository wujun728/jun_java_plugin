drop table if exists sys_user;

create table sys_user
(
	id BIGINT PRIMARY key auto_increment,
	username varchar(50),
	create_date datetime
) ENGINE=INNODB DEFAULT CHARSET=utf8;