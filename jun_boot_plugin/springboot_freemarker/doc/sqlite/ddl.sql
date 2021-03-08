create table t_sys_permission (
  id varchar not null ,
  name varchar ,
  css_class varchar ,
  url varchar ,
  skey varchar ,
  parent_key varchar ,
  hide int(11) ,
  lev int(11) ,
  sort int(11) ,
  primary key (id)
) ;

create table t_sys_role (
  id varchar not null ,
  name varchar  ,
  code varchar  ,
  remark varchar ,
  primary key (id)
) ;

create table t_sys_role_permission (
  id varchar not null  ,
  permission_id varchar ,
  role_id varchar ,
  primary key (id)
) ;

create table t_sys_user (
  id varchar not null  ,
  username varchar   ,
  password varchar  ,
  salt varchar ,
  true_name varchar   ,
  email varchar  ,
  organize_id varchar ,
  status int(11) ,
  last_login_time timestamp  ,
  create_time timestamp  ,
  modify_time timestamp ,
  primary key (id)
) ;

create table t_sys_user_role (
  id varchar not null  ,
  role_id varchar  ,
  user_id varchar ,
  primary key (id)
) ;
