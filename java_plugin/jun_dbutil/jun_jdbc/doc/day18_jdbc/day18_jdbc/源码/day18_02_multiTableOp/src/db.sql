use day18;
create table customers(
	id int primary key,
	name varchar(100),
	city varchar(100)
);
create table orders(
	id int primary key,
	num varchar(100),
	price float(10,2),
	customer_id int,
	constraint customer_id_fk foreign key(customer_id) references customers(id)
);

create table teachers(
	id int primary key,
	name varchar(100),
	salary float(8,2)
);
create table students(
	id int primary key,
	name varchar(100),
	grade varchar(10)
);
create table teachers_students(
	t_id int,
	s_id int,
	primary key(t_id,s_id),
	constraint t_id_fk foreign key(t_id) references teachers(id),
	constraint s_id_fk foreign key(s_id) references students(id)
);

create table persons(
	id int primary key,
	name varchar(100)
);
create table id_card(
	id int primary key,
	num varchar(100),
	constraint person_id_fk foreign key(id) references persons(id)
);
