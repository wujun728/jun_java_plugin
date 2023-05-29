CREATE TABLE t_bookType(
	id int primary key auto_increment,
	bookTypeName varchar(20),
	bookTypeDesc varchar(200)
);


CREATE TABLE t_book(
	id int primary key auto_increment,
	bookName varchar(20),
	author varchar(10),
	price decimal(6,2),
	bookTypeId int,
	constraint `fk` foreign key (`bookTypeId`) references `t_bookType`(`id`)
);


desc t_bookType;

show create table t_bookType;

alter table t_book rename t_book2;


alter table t_book change bookName bookName2 varchar(20);

alter table t_book add testField int first ;

alter table t_book drop testField;

drop table t_bookType;

drop table t_book;