create user TEST identified by 123456 default tablespace users;




create user TEST identified by 123456 default tablespace users;

grant create session to TEST;

grant create table to TEST;

select * from dba_sys_privs;

create user TEST2 identified by 123456 default tablespace users;

grant create session,create table to TEST with admin option;

revoke create session,create table from TEST;

create user TEST2 identified by 123456 default tablespace users;

grant create session to TEST2;

grant create table to TEST2;



grant create session to TEST;

grant create table to TEST;


select * from sys.aa ;

授权

grant select on AA to TEST;

update sys.AA set name='喝喝';

delete from sys.AA ;

grant all on AA to TEST;

传播性

grant select on sys.AA to TEST2;


grant select on AA to TEST with grant option;

select * from dba_tab_privs where grantee='TEST'


revoke update on AA from TEST;


角色：
select * from dba_roles;


grant select, update,insert ,delete on AA to role_AA;


revoke all on AA from TEST,TEST2;

grant role_AA to TEST;

