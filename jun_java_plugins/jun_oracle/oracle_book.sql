prompt PL/SQL Developer import file
prompt Created on 2015年10月22日星期四 by Administrator
set feedback off
set define off
prompt Creating T_BOOKTYPE...
create table T_BOOKTYPE
(
  id       NUMBER not null,
  typename VARCHAR2(20),
  num      NUMBER
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
alter table T_BOOKTYPE
  add constraint IDP primary key (ID)
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

prompt Creating T_BOOK...
create table T_BOOK
(
  id       NUMBER not null,
  bookname VARCHAR2(20),
  typeid   NUMBER
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
alter table T_BOOK
  add constraint IDPP primary key (ID)
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
alter table T_BOOK
  add constraint TYPEIDF foreign key (TYPEID)
  references T_BOOKTYPE (ID);

prompt Creating T_BOOK_LOG...
create table T_BOOK_LOG
(
  actionuser VARCHAR2(20),
  actionname VARCHAR2(20),
  actiontime DATE
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

prompt Disabling triggers for T_BOOKTYPE...
alter table T_BOOKTYPE disable all triggers;
prompt Disabling triggers for T_BOOK...
alter table T_BOOK disable all triggers;
prompt Disabling triggers for T_BOOK_LOG...
alter table T_BOOK_LOG disable all triggers;
prompt Disabling foreign key constraints for T_BOOK...
alter table T_BOOK disable constraint TYPEIDF;
prompt Deleting T_BOOK_LOG...
delete from T_BOOK_LOG;
commit;
prompt Deleting T_BOOK...
delete from T_BOOK;
commit;
prompt Deleting T_BOOKTYPE...
delete from T_BOOKTYPE;
commit;
prompt Loading T_BOOKTYPE...
insert into T_BOOKTYPE (id, typename, num)
values (1, '计算机类', 3);
insert into T_BOOKTYPE (id, typename, num)
values (2, '生物类', 1);
commit;
prompt 2 records loaded
prompt Loading T_BOOK...
insert into T_BOOK (id, bookname, typeid)
values (1, 'java编程思想', 1);
insert into T_BOOK (id, bookname, typeid)
values (2, '一头扎进Java', 1);
insert into T_BOOK (id, bookname, typeid)
values (3, '生物起源', 2);
insert into T_BOOK (id, bookname, typeid)
values (4, 'xx3', 1);
commit;
prompt 4 records loaded
prompt Loading T_BOOK_LOG...
insert into T_BOOK_LOG (actionuser, actionname, actiontime)
values ('SCOTT', 'insert', to_date('22-10-2015 08:11:13', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_BOOK_LOG (actionuser, actionname, actiontime)
values ('SCOTT', 'update', to_date('22-10-2015 08:12:19', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_BOOK_LOG (actionuser, actionname, actiontime)
values ('SCOTT', 'delete', to_date('22-10-2015 08:12:37', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_BOOK_LOG (actionuser, actionname, actiontime)
values ('SCOTT', 'insert', to_date('22-10-2015 08:17:09', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_BOOK_LOG (actionuser, actionname, actiontime)
values ('SCOTT', 'delete', to_date('22-10-2015 08:18:50', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_BOOK_LOG (actionuser, actionname, actiontime)
values ('SCOTT', 'update', to_date('22-10-2015 08:21:02', 'dd-mm-yyyy hh24:mi:ss'));
commit;
prompt 6 records loaded
prompt Enabling foreign key constraints for T_BOOK...
alter table T_BOOK enable constraint TYPEIDF;
prompt Enabling triggers for T_BOOKTYPE...
alter table T_BOOKTYPE enable all triggers;
prompt Enabling triggers for T_BOOK...
alter table T_BOOK enable all triggers;
prompt Enabling triggers for T_BOOK_LOG...
alter table T_BOOK_LOG enable all triggers;
set feedback on
set define on
prompt Done.
