show databases;#查看系统中所有的数据库
drop database if exists db_sql;
create database db_sql;#创建数据库
select database(),verson(),now();
use db_sql;
set names gbk;#设置编码
show tables;
drop table if exists t_food;
create table t_food(id int primary key auto_increment,name varchar(30) ,price int );
desc t_food;
#CRUD
insert into t_food(name,price) value ("实物"，30);
insert into t_food(name,price) values ("实物"，30),("实物"，30);
delete from t_food where id=2;
update t_food set name="美食" where id=2;
select * from t_food;
#\.路径-----执行数据库脚本文件
#select *
#from 表名
#where 条件
#group by 分组
#having 分组条件
#order by 排序
# limit 分页
#数据库中的约束:"尽可能保证"存入数据库的数据是正确的
create table t_food(
id int primary key auto_increment,#主键约束
name varchar(30) unique not null,#唯一 不空
price int default '8888',#默认约束
birthday date,
createDatetime TIMSTAMP default current_timestamp
);
desc t_food;

alter table t_food add column remark varchar(2);

select id from t_food where id>2;
select id from t_food where id>2 and id<6;
select id from t_food where id between 1 and 6;#[1,6]
select id from t_food where in(1,6);

#模糊查询
select name from t_food where name="zs";
select name from t_food where name like '';#%任意多个字符----而_代表任意一个字符

#内置函数
select count(id),avg(id),max(id) from t_food;
#外键
create table t_user(id int ,name varchar(30) , foreign key(id) references t_food(id) );