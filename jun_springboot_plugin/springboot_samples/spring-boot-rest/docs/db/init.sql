create table t_user (
  username varchar(20) unique not null,
  password varchar(20) not null,
  id int unsigned auto_increment,
  nickname varchar(20) not null,
  primary key(id)
);

insert into t_user (username, password, nickname) values ('admin', 'admin123', 'admin' );