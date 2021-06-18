
create table d_open_survey (
  f_id varchar2(32) primary key not null,
  f_allowreport number(11) ,
  f_createtime timestamp(6) ,
  f_description varchar2(255) ,
  f_name varchar2(255) ,
  f_pagecount number(11) ,
  f_questioncount number(11) ,
  f_samplecount number(11) ,
  f_samplesum number(11) ,
  f_show number(11) ,
  f_surveyurl varchar2(255) ,
  f_tag varchar2(255) ,
  f_type number(11) ,
  f_typename varchar2(255) ,
  f_uid varchar2(255) ,
  f_usersurveycount number(11) ,
  f_viewersum number(11) 
) ;

create table t_news (
  id varchar2(255) primary key not null,
  address varchar2(255) ,
  create_time timestamp(6) ,
  description varchar2(255) ,
  news_time timestamp(6) ,
  title varchar2(255) ,
  user_id varchar2(255) 
) ;

create table t_sys_permission (
  id varchar2(32) primary key not null,
  hide number(11) ,
  skey varchar2(255) ,
  name varchar2(255) ,
  parent_key varchar2(255) ,
  sort number(11) ,
  url varchar2(255)
) ;

create table t_sys_role (
  id varchar2(32) primary key not null,
  code varchar2(255) ,
  name varchar2(255) ,
  remark varchar2(255) 
) ;

create table t_sys_role_permission (
  id varchar2(32) primary key not null,
  permission_id varchar2(255) ,
  role_id varchar2(255) 
) ;

create table t_sys_user (
  id varchar2(32) primary key not null,
  create_time timestamp(6) ,
  email varchar2(255) ,
  last_login_time timestamp(6) ,
  modify_time timestamp(6) ,
  name varchar2(255) ,
  organize_id varchar2(255) ,
  password varchar2(255) ,
  salt varchar2(255) ,
  status number(11) ,
  true_name varchar2(255) 
) ;

create table t_sys_user_role (
  id varchar2(32) primary key not null,
  role_id varchar2(255) ,
  user_id varchar2(255) 
) ;
