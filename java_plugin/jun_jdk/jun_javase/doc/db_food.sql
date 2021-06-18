drop database if exists db_food;
create database db_food;
use db_food;
set names gbk;
drop table if exists t_food;
create table t_food(id int primary key auto_increment,name varchar(30) ,price int );
insert into t_food(name,price) values ("–°≥¥»‚",8),("≈£≈≈",15),("±˘º§¡Ë",8888);
select * from t_food;

