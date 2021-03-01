drop database if exists db_crud;
create database db_crud;
use db_crud;
set names gbk;
drop table if exists t_crud;
create table t_crud(operator_id int primary key auto_increment,name varchar(30) ,password varchar(30) ,status int(11));
select * from t_crud;