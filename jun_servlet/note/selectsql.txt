drop database if exists stu;
create database stu;
use stu;
set names gbk;
drop table if exists student;
create table student(
sid int primary key auto_increment not null,
sname varchar(20) not null,
sage int ,
ssex varchar(2)
);
insert into student (sname,sage,ssex)values('haohao',23,'m');
insert into student (sname,sage,ssex)values('mingming',22,'m');
insert into student (sname,sage,ssex)values('zq',21,'m');
insert into student (sname,sage,ssex)values('ls',21,'m');
insert into student (sname,sage,ssex)values('ww',23,'w');
insert into student (sname,sage,ssex)values('zl',24,'w');
insert into student (sname,sage,ssex)values('qq',24,'w');
insert into student (sname,sage,ssex)values('sms',25,'m');

drop table if exists teacher;
create table teacher(
tid int primary key auto_increment not null,
tname varchar(20) unique not null
);

insert into teacher (tname)values('gw'),('mengmeng'),('lj'),('zs');
insert into teacher (tname)values('叶平');

drop table if exists course;
create table course(
cid int primary key auto_increment not null,
cname varchar(20) default 'java',
tid int,
foreign key (tid) references teacher(tid) #not null
);

insert into course (tid) values(1);
insert into course (cname,tid) values('.net',1),('jsp',2),('html',3);
insert into course (cname) values('ajax');

drop table if exists sc;
create table sc(
# scid int primary key
sid int ,
cid int,
score int,
primary key (sid,cid)#联合主键
);

insert into sc (sid,cid,score) values(1,1,77);
insert into sc (sid,cid,score) values(1,2,73);
insert into sc (sid,cid,score) values2,1,87);
insert into sc (sid,cid,score) values(2,2,77);
insert into sc (sid,cid,score) values(3,1,71);
insert into sc (sid,cid,score) values(3,2,77);
insert into sc (sid,cid,score) values(3,3,73);
insert into sc (sid,cid,score) values(4,1,70);
insert into sc (sid,cid,score) values(5,1,71);
insert into sc (sid,cid,score) values(5,2,72);
insert into sc (sid,cid,score) values(5,4,73);
insert into sc (sid,cid,score) values(6,3,70);
insert into sc (sid,cid,score) values(6,1,70);
insert into sc (sid,cid,score) values(7,4,80);