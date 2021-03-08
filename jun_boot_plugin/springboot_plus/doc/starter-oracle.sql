prompt PL/SQL Developer import file
prompt Created on 2018年3月12日 by Administrator
set feedback off
set define off
prompt Dropping CMS_BLOG...
drop table CMS_BLOG cascade constraints;
prompt Dropping CORE_AUDIT...
drop table CORE_AUDIT cascade constraints;
prompt Dropping CORE_DICT...
drop table CORE_DICT cascade constraints;
prompt Dropping CORE_FILE...
drop table CORE_FILE cascade constraints;
prompt Dropping CORE_FILE_TAG...
drop table CORE_FILE_TAG cascade constraints;
prompt Dropping CORE_FUNCTION...
drop table CORE_FUNCTION cascade constraints;
prompt Dropping CORE_MENU...
drop table CORE_MENU cascade constraints;
prompt Dropping CORE_ORG...
drop table CORE_ORG cascade constraints;
prompt Dropping CORE_ROLE...
drop table CORE_ROLE cascade constraints;
prompt Dropping CORE_ROLE_FUNCTION...
drop table CORE_ROLE_FUNCTION cascade constraints;
prompt Dropping CORE_ROLE_MENU...
drop table CORE_ROLE_MENU cascade constraints;
prompt Dropping CORE_USER...
drop table CORE_USER cascade constraints;
prompt Dropping CORE_USER_ROLE...
drop table CORE_USER_ROLE cascade constraints;

CREATE SEQUENCE AUDIT_SEQ INCREMENT BY 1 MINVALUE 3000 NOCYCLE NOCACHE NOORDER  ;
CREATE SEQUENCE CORE_SEQ INCREMENT BY 1 MINVALUE 3000 NOCYCLE NOCACHE NOORDER  ;


prompt Creating CMS_BLOG...
create table CMS_BLOG
(
  id             NUMBER(11) not null,
  title          VARCHAR2(255),
  content        VARCHAR2(512),
  create_time    DATE,
  create_user_id NUMBER(11),
  type           VARCHAR2(255)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CMS_BLOG
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_AUDIT...
create table CORE_AUDIT
(
  id            NUMBER(11) not null,
  function_code VARCHAR2(45),
  function_name VARCHAR2(45),
  user_id       NUMBER(11),
  user_name     VARCHAR2(45),
  ip            VARCHAR2(45),
  create_time   DATE,
  success       NUMBER(4),
  message       VARCHAR2(256),
  org_id        VARCHAR2(45)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CORE_AUDIT
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_DICT...
create table CORE_DICT
(
  id          NUMBER(11) not null,
  value       VARCHAR2(16) not null,
  name        VARCHAR2(128) not null,
  type        VARCHAR2(64) not null,
  type_name   VARCHAR2(64) not null,
  sort        NUMBER(11),
  parent      VARCHAR2(64),
  del_flag    NUMBER(11),
  remark      VARCHAR2(255),
  create_time DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table CORE_DICT
  is '字典表';
comment on column CORE_DICT.name
  is '名称';
comment on column CORE_DICT.type
  is '字典编码';
comment on column CORE_DICT.type_name
  is '类型描述';
comment on column CORE_DICT.sort
  is '排序';
comment on column CORE_DICT.parent
  is '父id';
comment on column CORE_DICT.del_flag
  is '删除标记';
comment on column CORE_DICT.remark
  is '备注';
comment on column CORE_DICT.create_time
  is '创建时间';
create index idx_code on CORE_DICT (TYPE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index idx_pid on CORE_DICT (PARENT)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index idx_value on CORE_DICT (VALUE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CORE_DICT
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_FILE...
create table CORE_FILE
(
  id            NUMBER(11) not null,
  name          VARCHAR2(64),
  path          VARCHAR2(255),
  biz_id        VARCHAR2(128),
  user_id       NUMBER(11),
  create_time   DATE,
  org_id        NUMBER(11),
  biz_type      VARCHAR2(128),
  file_batch_id VARCHAR2(128)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
alter table CORE_FILE
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt Creating CORE_FILE_TAG...
create table CORE_FILE_TAG
(
  id      NUMBER(20) not null,
  key     VARCHAR2(64) not null,
  value   VARCHAR2(255) not null,
  file_id NUMBER(20) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
alter table CORE_FILE_TAG
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt Creating CORE_FUNCTION...
create table CORE_FUNCTION
(
  id          NUMBER(11) not null,
  code        VARCHAR2(256),
  name        VARCHAR2(64),
  create_time DATE,
  access_url  VARCHAR2(256),
  parent_id   NUMBER(11),
  type        VARCHAR2(4)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CORE_FUNCTION
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_MENU...
create table CORE_MENU
(
  id             NUMBER(11) not null,
  code           VARCHAR2(64),
  name           VARCHAR2(64),
  create_time    DATE,
  function_id    NUMBER(11),
  type           VARCHAR2(6),
  parent_menu_id NUMBER(11),
  seq            NUMBER(11),
  icon           VARCHAR2(255)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column CORE_MENU.type
  is '1,系统，2 导航 3 菜单项（对应某个功能点）';
comment on column CORE_MENU.icon
  is '图标';
alter table CORE_MENU
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_ORG...
create table CORE_ORG
(
  id            NUMBER(11) not null,
  code          VARCHAR2(16) not null,
  name          VARCHAR2(128) not null,
  create_time   DATE,
  parent_org_id NUMBER(11),
  type          VARCHAR2(6) not null,
  del_flag      NUMBER(4)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column CORE_ORG.type
  is '1 公司，2 部门，3 小组';
alter table CORE_ORG
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_ROLE...
create table CORE_ROLE
(
  id          NUMBER(11) not null,
  code        VARCHAR2(16),
  name        VARCHAR2(255),
  create_time DATE,
  type        VARCHAR2(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column CORE_ROLE.code
  is '角色编码';
comment on column CORE_ROLE.name
  is '角色名称';
comment on column CORE_ROLE.create_time
  is '创建时间';
comment on column CORE_ROLE.type
  is '1 可以配置 2 固定权限角色';
create index code_idx on CORE_ROLE (CODE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CORE_ROLE
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_ROLE_FUNCTION...
create table CORE_ROLE_FUNCTION
(
  id                 NUMBER(11) not null,
  role_id            NUMBER(11),
  function_id        NUMBER(11),
  data_access_type   NUMBER(4),
  data_access_policy VARCHAR2(128)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CORE_ROLE_FUNCTION
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_ROLE_MENU...
create table CORE_ROLE_MENU
(
  id          NUMBER(11) not null,
  role_id     NUMBER(11),
  menu_id     NUMBER(11),
  create_time DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table CORE_ROLE_MENU
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_USER...
create table CORE_USER
(
  id          NUMBER(11) not null,
  code        VARCHAR2(16),
  name        VARCHAR2(16),
  password    VARCHAR2(64),
  create_time DATE,
  org_id      NUMBER(11),
  state       VARCHAR2(16),
  job_type1   VARCHAR2(16),
  del_flag    NUMBER(4),
  update_time DATE,
  job_type0   VARCHAR2(16)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column CORE_USER.state
  is '用户状态 1:启用 0:停用';
comment on column CORE_USER.del_flag
  is '用户删除标记 0:未删除 1:已删除';
alter table CORE_USER
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating CORE_USER_ROLE...
create table CORE_USER_ROLE
(
  id          NUMBER(11) not null,
  user_id     NUMBER(11),
  role_id     NUMBER(11),
  org_id      NUMBER(11),
  create_time DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table CORE_USER_ROLE
  is '用户角色关系表';
alter table CORE_USER_ROLE
  add primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for CMS_BLOG...
alter table CMS_BLOG disable all triggers;
prompt Disabling triggers for CORE_AUDIT...
alter table CORE_AUDIT disable all triggers;
prompt Disabling triggers for CORE_DICT...
alter table CORE_DICT disable all triggers;
prompt Disabling triggers for CORE_FILE...
alter table CORE_FILE disable all triggers;
prompt Disabling triggers for CORE_FILE_TAG...
alter table CORE_FILE_TAG disable all triggers;
prompt Disabling triggers for CORE_FUNCTION...
alter table CORE_FUNCTION disable all triggers;
prompt Disabling triggers for CORE_MENU...
alter table CORE_MENU disable all triggers;
prompt Disabling triggers for CORE_ORG...
alter table CORE_ORG disable all triggers;
prompt Disabling triggers for CORE_ROLE...
alter table CORE_ROLE disable all triggers;
prompt Disabling triggers for CORE_ROLE_FUNCTION...
alter table CORE_ROLE_FUNCTION disable all triggers;
prompt Disabling triggers for CORE_ROLE_MENU...
alter table CORE_ROLE_MENU disable all triggers;
prompt Disabling triggers for CORE_USER...
alter table CORE_USER disable all triggers;
prompt Disabling triggers for CORE_USER_ROLE...
alter table CORE_USER_ROLE disable all triggers;
prompt Loading CMS_BLOG...
insert into CMS_BLOG (id, title, content, create_time, create_user_id, type)
values (1, 'hello', '我的博客，内容是。。。', to_date('22-02-2018 09:53:05', 'dd-mm-yyyy hh24:mi:ss'), 1, 'F0');
insert into CMS_BLOG (id, title, content, create_time, create_user_id, type)
values (2, 'cccc', '过年回家', to_date('13-02-2018 10:30:01', 'dd-mm-yyyy hh24:mi:ss'), 1, 'F0');
commit;
prompt 2 records loaded
prompt Loading CORE_AUDIT...
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (1, 'org.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 19:58:50', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (2, 'org.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 19:58:51', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (3, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:00:10', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (4, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:00:11', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (5, 'user.add', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:00:39', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (6, 'user.edit', '用户编辑', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:10:15', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (7, 'user.query', '用户列表', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:10:15', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (8, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:10:16', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (9, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:10:17', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (10, 'user.edit', '用户编辑', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:14:46', 'dd-mm-yyyy hh24:mi:ss'), 0, 'java.sql.SQLException: Error on delete of ''C:\Users\ADMINI~1\AppData\Local\Temp\#sql978_2c3_6.MYI'' (Errcode: 13 - Permission denied)', null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (11, 'user.edit', '用户编辑', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:15:12', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (12, 'user.query', '用户列表', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:15:13', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (13, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:15:14', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (14, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:15:14', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (15, 'audit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('06-02-2018 20:16:23', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (16, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:42:58', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (17, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:42:58', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (18, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:11', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (19, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:11', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (20, 'user.add', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:13', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (21, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:28', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (22, 'role.add', '角色添加', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:29', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (23, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:43', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (24, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:43', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (25, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:53:45', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (26, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:56:03', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (27, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:56:03', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (28, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:56:06', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (29, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:56:07', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (30, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:56:09', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (31, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 09:56:10', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (32, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:02:00', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (33, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:02:00', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (34, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:02:02', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (35, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:05:40', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (36, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:05:40', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (37, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:05:42', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (38, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:06:02', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (39, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:07:45', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (40, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:07:45', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (41, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:07:47', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (42, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:08:03', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (43, 'role.update', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:08:03', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (44, 'role.edit', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:08:16', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
insert into CORE_AUDIT (id, function_code, function_name, user_id, user_name, ip, create_time, success, message, org_id)
values (45, 'role.query', '未定义', 1, '超级管理员', '172.16.49.65', to_date('07-02-2018 10:08:16', 'dd-mm-yyyy hh24:mi:ss'), 1, null, null);
commit;
prompt 45 records loaded
prompt Loading CORE_DICT...
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (1, 'DA0', '查看自己', 'data_access_type', '数据权限', 1, null, 0, '11111111111111111123', null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (2, 'DA1', '查看本公司', 'data_access_type', '数据权限', 3, null, 0, 'hello,go', null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (3, 'DA2', '查看同机构', 'data_access_type', '数据权限', 3, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (4, 'DA3', '查看本部门', 'data_access_type', '数据权限', 4, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (5, 'DA4', '查看集团', 'data_access_type', '数据权限', 5, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (6, 'DA5', '查看母公司', 'data_access_type', '数据权限', 6, null, 0, null, to_date('14-10-2017 11:45:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (7, 'FN0', '普通功能', 'function_type', '功能点类型', 2, null, 0, null, to_date('23-10-2017 10:18:03', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (8, 'FN1', '含数据权限', 'function_type', '功能点类型', 1, null, 0, null, to_date('23-10-2017 10:20:05', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (9, 'JT_01', '管理岗位', 'job_type', '岗位类型', 1, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (10, 'JT_02', '技术岗位', 'job_type', '岗位类型', 2, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (11, 'JT_S_01', '董事会', 'job_sub_managment_type', '管理岗位子类型', 1, '10', 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (12, 'JT_S_02', '秘书', 'job_sub_managment_type', '管理岗位子类型', 2, '10', 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (13, 'JT_S_03', '技术经理', 'job_dev_sub_type', '技术岗位子类型', 1, '11', 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (14, 'JT_S_04', '程序员', 'job_dev_sub_type', '技术岗位子类型', 2, '11', 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (15, 'MENU_M', '菜单', 'menu_type', '菜单类型', 3, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (16, 'MENU_N', '导航', 'menu_type', '菜单类型', 2, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (17, 'MENU_S', '系统', 'menu_type', '菜单类型', 1, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (18, 'ORGT0', '集团总部', 'org_type', '机构类型', 1, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (19, 'ORGT1', '分公司', 'org_type', '机构类型', 2, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (20, 'ORGT2', '部门', 'org_type', '机构类型', 3, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (21, 'ORGT3', '小组', 'org_type', '机构类型', 4, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (22, 'R0', '操作角色', 'role_type', '数据权限', 1, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (23, 'R1', '工作流角色', 'role_type', '用户角色', 2, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (24, 'S0', '禁用', 'user_state', '用户状态', 2, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (25, 'S1', '启用', 'user_state', '用户状态', 1, null, 0, null, null);
insert into CORE_DICT (id, value, name, type, type_name, sort, parent, del_flag, remark, create_time)
values (26, 'sdfsd', 'sdfsdf', 'sdfsdf', 'sdfsdf', 1, null, 1, 'dsfsdf', to_date('18-02-2018 21:31:02', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 26 records loaded
prompt Loading CORE_FILE...
prompt Table is empty
prompt Loading CORE_FILE_TAG...
prompt Table is empty
prompt Loading CORE_FUNCTION...
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (1, 'user', '用户功能', null, '/admin/user/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (2, 'user.query', '用户列表', null, null, 1, 'FN1');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (3, 'user.edit', '用户编辑', null, null, 1, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (6, 'org', '组织机构', null, '/admin/org/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (7, 'role', '角色管理', null, '/admin/role/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (8, 'menu', '菜单管理', null, '/admin/menu/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (9, 'function', '功能点管理', null, '/admin/function/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (10, 'roleFunction', '角色功能授权', null, '/admin/role/function.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (11, 'roleDataAccess', '角色数据授权', null, '/admin/role/data.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (12, 'code', '代码生成', null, '/core/codeGen/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (13, 'dict', '字典管理', null, '/admin/dict/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (18, 'trace', '审计查询', null, '/admin/audit/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (19, 'file', '相关文档', null, '/trade/interAndRelate/file.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (91, 'test', '测试', to_date('11-10-2017 16:59:01', 'dd-mm-yyyy hh24:mi:ss'), '/test/test.do', 6, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (161, 'role.add', '角色添加', to_date('23-10-2017 09:45:05', 'dd-mm-yyyy hh24:mi:ss'), null, 7, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (167, 'workflow.admin', '工作流监控', null, '/admin/workflow/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (180, 'code.query', '代码生成测试', null, null, 12, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (181, 'blog.query', '博客查询功能', null, null, 182, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (182, 'blog', '博客测试', null, '/admin/blog/index.do', 0, 'FN0');
insert into CORE_FUNCTION (id, code, name, create_time, access_url, parent_id, type)
values (1001, '子系统生成', '子系统生成', to_date('12-03-2018 11:52:25', 'dd-mm-yyyy hh24:mi:ss'), '/core/codeGen/project.do', 0, 'FN0');
commit;
prompt 20 records loaded
prompt Loading CORE_MENU...
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (8, '系统管理', '系统管理', null, null, 'MENU_S', 0, 1, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (10, '用户管理', '用户管理', null, 1, 'MENU_M', 18, 1, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (11, '组织机构管理', '组织机构管理', null, 6, 'MENU_M', 18, 2, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (12, '角色管理', '角色管理', null, 7, 'MENU_M', 18, 3, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (13, '菜单项', '菜单项', null, 8, 'MENU_M', 18, 4, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (14, '功能点管理', '功能点管理', null, 9, 'MENU_M', 18, 5, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (15, '字典数据管理', '字典数据管理', null, 13, 'MENU_M', 18, 6, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (16, '审计查询', '审计查询', null, 18, 'MENU_M', 19, 7, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (17, '代码生成', '代码生成', null, 12, 'MENU_M', 1002, 8, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (18, '基础管理', '基础管理', null, null, 'MENU_N', 8, 1, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (19, '监控管理', '监控管理', null, null, 'MENU_N', 8, 2, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (20, '流程监控', '流程监控', null, 167, 'MENU_M', 19, 3, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (21, '角色功能授权', '角色功能授权', null, 10, 'MENU_M', 18, 8, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (22, '角色数据授权', '角色数据授权', null, 11, 'MENU_M', 18, 9, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (23, '博客测试', '博客测试1', null, 182, 'MENU_M', 19, 9, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (1002, '代码生成管理', '代码生成管理', to_date('12-03-2018 11:52:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'MENU_N', 8, 2, null);
insert into CORE_MENU (id, code, name, create_time, function_id, type, parent_menu_id, seq, icon)
values (1003, '子系统生成', '子系统生成', to_date('12-03-2018 13:21:54', 'dd-mm-yyyy hh24:mi:ss'), 1001, 'MENU_M', 1002, 1, null);
commit;
prompt 17 records loaded
prompt Loading CORE_ORG...
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (1, '集团公司', '集团', to_date('02-02-2018 17:18:50', 'dd-mm-yyyy hh24:mi:ss'), null, 'ORGT0', 0);
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (3, '信息科技部门', '信息科技部门', null, 1, 'ORGT2', 0);
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (4, '贵州银行', '贵州银行', to_date('02-02-2018 17:18:56', 'dd-mm-yyyy hh24:mi:ss'), 1, 'ORGT1', 0);
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (5, '贵州银行金科', '贵州银行金融科技开发公司', null, 4, 'ORGT1', 0);
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (6, '金科研发', '金科研发', null, 5, 'ORGT2', 0);
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (7, '金科研发部门', '金科研发部门', to_date('05-02-2018 13:49:52', 'dd-mm-yyyy hh24:mi:ss'), 6, 'ORGT2', 0);
insert into CORE_ORG (id, code, name, create_time, parent_org_id, type, del_flag)
values (8, '金科研发2部', '金科研发2部', to_date('05-02-2018 13:50:43', 'dd-mm-yyyy hh24:mi:ss'), 6, 'ORGT2', 0);
commit;
prompt 7 records loaded
prompt Loading CORE_ROLE...
insert into CORE_ROLE (id, code, name, create_time, type)
values (1, 'DEPT_MANAGER', '部门管理员', null, 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (2, 'CEO', '公司CEO', null, 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (3, 'ASSIST', '助理', null, 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (12, '111', '2324324', to_date('06-09-2017 04:08:00', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (13, '1111', '哈哈', to_date('06-09-2017 04:09:05', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (15, 'admin', 'ivy', to_date('06-09-2017 05:35:04', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (17, '123', '我', to_date('06-09-2017 21:23:03', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (18, '23', '234', to_date('06-09-2017 21:41:03', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (19, '132484', '1', to_date('06-09-2017 21:42:02', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
insert into CORE_ROLE (id, code, name, create_time, type)
values (173, 'dept.admin', '部门辅助管理员', to_date('25-10-2017 10:29:03', 'dd-mm-yyyy hh24:mi:ss'), 'R0');
commit;
prompt 10 records loaded
prompt Loading CORE_ROLE_FUNCTION...
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (1, 1, 1, 5, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (2, 1, 2, 1, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (3, 1, 3, 5, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (4, 2, 2, 2, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (5, 3, 2, 5, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (6, 3, 3, 5, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (162, 1, 6, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (164, 1, 91, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (174, 173, 1, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (176, 173, 2, 5, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (177, 173, 3, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (178, 173, 167, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (192, 3, 1, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (194, 3, 12, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (196, 3, 180, 3, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (197, null, 1, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (198, null, 2, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (199, null, 3, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (200, null, 6, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (201, null, 91, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (202, null, 8, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (205, 1, 182, null, null);
insert into CORE_ROLE_FUNCTION (id, role_id, function_id, data_access_type, data_access_policy)
values (206, 1, 181, null, null);
commit;
prompt 23 records loaded
prompt Loading CORE_ROLE_MENU...
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (1, 1, 10, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (163, 1, 11, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (175, 173, 10, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (193, 3, 10, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (195, 3, 17, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (196, null, 10, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (197, null, 11, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (198, null, 13, null);
insert into CORE_ROLE_MENU (id, role_id, menu_id, create_time)
values (200, 1, 23, null);
commit;
prompt 9 records loaded
prompt Loading CORE_USER...
insert into CORE_USER (id, code, name, password, create_time, org_id, state, job_type1, del_flag, update_time, job_type0)
values (1, 'admin', '超级管理员1', '123456', to_date('13-09-2017 09:21:03', 'dd-mm-yyyy hh24:mi:ss'), 1, 'S1', 'JT_S_01', 0, to_date('13-09-2017 09:21:03', 'dd-mm-yyyy hh24:mi:ss'), 'JT_01');
insert into CORE_USER (id, code, name, password, create_time, org_id, state, job_type1, del_flag, update_time, job_type0)
values (171, 'lixx', '李小小', null, to_date('28-01-2018 11:21:20', 'dd-mm-yyyy hh24:mi:ss'), 3, 'S1', 'JT_S_04', 0, null, 'JT_02');
insert into CORE_USER (id, code, name, password, create_time, org_id, state, job_type1, del_flag, update_time, job_type0)
values (172, 'lixx2', '李xx2', '123456', to_date('28-01-2018 11:22:38', 'dd-mm-yyyy hh24:mi:ss'), 4, 'S1', 'JT_S_02', 0, null, 'JT_01');
insert into CORE_USER (id, code, name, password, create_time, org_id, state, job_type1, del_flag, update_time, job_type0)
values (173, 'test1', 'test1', '123', to_date('28-01-2018 14:44:55', 'dd-mm-yyyy hh24:mi:ss'), 5, 'S1', 'JT_S_04', 0, null, 'JT_02');
insert into CORE_USER (id, code, name, password, create_time, org_id, state, job_type1, del_flag, update_time, job_type0)
values (174, 'hank250', '李小熊', null, to_date('16-02-2018 11:36:41', 'dd-mm-yyyy hh24:mi:ss'), 4, 'S1', 'JT_S_04', 0, null, 'JT_02');
commit;
prompt 5 records loaded
prompt Loading CORE_USER_ROLE...
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (1, 3, 1, 4, null);
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (2, 4, 2, 5, null);
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (3, 75, 3, 6, to_date('21-09-2017 18:03:05', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (35, 1, 1, 1, to_date('06-09-2017 01:12:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (36, 1, 3, 6, to_date('06-09-2017 03:33:05', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (38, 1, 1, 3, to_date('06-09-2017 03:35:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (41, 1, 1, 5, to_date('06-09-2017 03:58:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (42, 3, 3, 1, to_date('06-09-2017 04:01:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (47, 47, 3, 1, to_date('06-09-2017 22:00:01', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (49, 5, 3, 4, to_date('06-09-2017 22:01:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (52, 47, 2, 1, to_date('07-09-2017 01:12:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (53, 48, 3, 4, to_date('07-09-2017 01:14:04', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (55, 68, 2, 3, to_date('07-09-2017 21:42:03', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (125, 74, 1, 4, to_date('17-10-2017 09:37:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (144, 74, 3, null, to_date('17-10-2017 16:55:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (145, 67, 3, null, to_date('17-10-2017 16:55:01', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (146, 73, 3, null, to_date('17-10-2017 16:55:02', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (147, 22, 3, null, to_date('17-10-2017 16:55:04', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (148, 68, 3, null, to_date('17-10-2017 16:56:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (168, 72, 1, 3, to_date('24-10-2017 14:40:04', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (169, 41, 1, null, to_date('25-10-2017 08:58:01', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (171, 170, 1, 5, to_date('25-10-2017 09:08:05', 'dd-mm-yyyy hh24:mi:ss'));
insert into CORE_USER_ROLE (id, user_id, role_id, org_id, create_time)
values (172, 171, 1, 4, to_date('02-02-2018 09:36:40', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 23 records loaded
prompt Enabling triggers for CMS_BLOG...
alter table CMS_BLOG enable all triggers;
prompt Enabling triggers for CORE_AUDIT...
alter table CORE_AUDIT enable all triggers;
prompt Enabling triggers for CORE_DICT...
alter table CORE_DICT enable all triggers;
prompt Enabling triggers for CORE_FILE...
alter table CORE_FILE enable all triggers;
prompt Enabling triggers for CORE_FILE_TAG...
alter table CORE_FILE_TAG enable all triggers;
prompt Enabling triggers for CORE_FUNCTION...
alter table CORE_FUNCTION enable all triggers;
prompt Enabling triggers for CORE_MENU...
alter table CORE_MENU enable all triggers;
prompt Enabling triggers for CORE_ORG...
alter table CORE_ORG enable all triggers;
prompt Enabling triggers for CORE_ROLE...
alter table CORE_ROLE enable all triggers;
prompt Enabling triggers for CORE_ROLE_FUNCTION...
alter table CORE_ROLE_FUNCTION enable all triggers;
prompt Enabling triggers for CORE_ROLE_MENU...
alter table CORE_ROLE_MENU enable all triggers;
prompt Enabling triggers for CORE_USER...
alter table CORE_USER enable all triggers;
prompt Enabling triggers for CORE_USER_ROLE...
alter table CORE_USER_ROLE enable all triggers;
set feedback on
set define on
prompt Done.
