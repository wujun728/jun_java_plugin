create database day12 character set utf8 collate utf8_general_ci;

use day12;

create table users(
	id int primary key auto_increment,
	name varchar(40),
	password varchar(40),
	email varchar(60),
	birthday date
)character set utf8 collate utf8_general_ci;

insert into users(name,password,email,birthday) values('zs','123456','zs@sina.com','1980-12-04');
insert into users(name,password,email,birthday) values('lisi','123456','lisi@sina.com','1981-12-04');
insert into users(name,password,email,birthday) values('wangwu','123456','wangwu@sina.com','1979-12-04');