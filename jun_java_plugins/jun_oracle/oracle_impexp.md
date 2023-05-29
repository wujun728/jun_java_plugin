
Oracle 版本 11.2.0.1.0
schema：zhuser
使用expdp导出，用impdp导入
表空间：
TS_BUSIIDX_PTX                                                                                                                                                  
TS_BUSI_PTX                                                                                                                                                     
TS_DATA_LOB                                                                                                                                                     
TS_DATA_PTX                                                                                                                                                     
TS_IDX_PTX          


create tablespace TS_BUSIIDX_PTX logging   datafile 'D:\Oracle\TS_BUSIIDX_PTX.dbf'  size 50m   autoextend on   next 50m ;
create tablespace TS_BUSI_PTX logging   datafile 'D:\Oracle\TS_BUSI_PTX.dbf'  size 50m   autoextend on   next 50m ;
create tablespace TS_DATA_LOB logging   datafile 'D:\Oracle\TS_DATA_LOB.dbf'  size 50m   autoextend on   next 50m ;
create tablespace TS_DATA_PTX logging   datafile 'D:\Oracle\TS_DATA_PTX.dbf'  size 50m   autoextend on   next 50m ;
create tablespace TS_IDX_PTX logging   datafile 'D:\Oracle\TS_IDX_PTX.dbf'  size 50m   autoextend on   next 50m ;


create user zhuser identified by zhuser   default tablespace TS_DATA_PTX   temporary tablespace temp;  

grant connect, resource to zhuser; 
grant create session to zhuser; 
grant create table,create session,create view to zhuser ;


grant read,write on directory  DATA_FILE_DIR to zhuser;

create directory dpdata1 as 'd:\Oracle';
impdp scott/tiger DIRECTORY=dpdata1    DUMPFILE=‪expdp_140507_1450.dmp SCHEMAS=zhuser;


impdp system/passsystem directory=dmp_dir dumpfile=user1.dmp REMAP_SCHEMA=user1:user2