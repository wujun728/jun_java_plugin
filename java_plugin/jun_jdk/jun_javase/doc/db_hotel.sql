drop database if exists db_Hotel;
create database db_Hotel;
use db_Hotel;
set names gbk;
drop table if exists roomRate;
create table roomRate(id int primary key auto_increment,roomType varchar(50) ,rate int);
insert into roomRate(roomType,rate) values ("豪华客房",1500),("行政客房",1800),("高级套房",2500);
select * from roomRate;

drop table if exists book;
create table book(id int primary key auto_increment,roomRateId int,roomCount int ,reachDate datetime,leaveDate datetime,contact varchar(30),phoneNo varchar(15));
select * from book;
alter table book add constraint FK_ID foreign key(roomRateId) REFERENCES roomRate(id);
#select * from book where roomRateId=(select id from roomRate where loginName='zhangfei');
