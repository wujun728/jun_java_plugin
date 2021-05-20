-- 此 CLP 文件是使用 DB2LOOK 版本 "9.7" 创建的 
-- 时间戳记: 2012-4-24 21:22:20
-- 数据库名称: JDIY_DB        
-- 数据库管理器版本: DB2/NT Version 9.7.5          
-- 数据库代码页: 1208
-- 数据库整理顺序为: IDENTITY

------------------------------------
-- BUFFERPOOLS 的 DDL 语句 --
------------------------------------
 
CREATE BUFFERPOOL "JDIY_CLUB_BUFFER"  SIZE 250 PAGESIZE 32768;

------------------------------------
-- TABLESPACES 的 DDL 语句 --
------------------------------------


CREATE LARGE TABLESPACE "JDIY_CLOB" IN DATABASE PARTITION GROUP IBMDEFAULTGROUP 
	 PAGESIZE 32768 MANAGED BY AUTOMATIC STORAGE 
	 AUTORESIZE YES 
	 INITIALSIZE 32 M 
	 MAXSIZE NONE 
	 EXTENTSIZE 16
	 PREFETCHSIZE 16
	 BUFFERPOOL JDIY_CLUB_BUFFER
	 OVERHEAD 10.500000
	 TRANSFERRATE 0.140000 
	 NO FILE SYSTEM CACHING  
	 DROPPED TABLE RECOVERY ON;


-- 模拟表空间

ALTER TABLESPACE SYSCATSPACE
      PREFETCHSIZE AUTOMATIC
      OVERHEAD 7.500000
      NO FILE SYSTEM CACHING 
      AUTORESIZE YES 
      TRANSFERRATE 0.060000;


ALTER TABLESPACE TEMPSPACE1
      PREFETCHSIZE AUTOMATIC
      OVERHEAD 7.500000
      FILE SYSTEM CACHING 
      TRANSFERRATE 0.060000;


ALTER TABLESPACE USERSPACE1
      PREFETCHSIZE AUTOMATIC
      OVERHEAD 7.500000
      NO FILE SYSTEM CACHING 
      AUTORESIZE YES 
      TRANSFERRATE 0.060000;




------------------------------------------------
-- 表的 DDL 语句 "JDIY_VI"
------------------------------------------------
 

CREATE TABLE "JDIY_VI"  (
		  "ID" CHAR(10) NOT NULL , 
		  "TB" VARCHAR(48) NOT NULL , 
		  "TBPK" VARCHAR(64) , 
		  "DMPK" SMALLINT NOT NULL , 
		  "TIT" VARCHAR(64) NOT NULL , 
		  "TYPE" VARCHAR(32) NOT NULL WITH DEFAULT 'input0' , 
		  "PRM" VARCHAR(3500) )   
		 IN "USERSPACE1" ; 


-- 表上主键的 DDL 语句 "JDIY_VI"

ALTER TABLE "JDIY_VI" 
	ADD CONSTRAINT "CC1335120940362" PRIMARY KEY
		("ID");



------------------------------------------------
-- 表的 DDL 语句 "JDIY_MM"
------------------------------------------------
 

CREATE TABLE "JDIY_MM"  (
		  "ID" CHAR(10) NOT NULL , 
		  "TID" CHAR(10) NOT NULL , 
		  "CID" CHAR(10) NOT NULL , 
		  "TB" VARCHAR(48) , 
		  "RID" VARCHAR(10) , 
		  "CTRL" VARCHAR(10) , 
		  "TIT" VARCHAR(64) NOT NULL , 
		  "SNAME" VARCHAR(64) , 
		  "URL" VARCHAR(255) , 
		  "SORT" INTEGER WITH DEFAULT 100 , 
		  "PRM" VARCHAR(128) NOT NULL )   
		 IN "USERSPACE1" ; 


-- 表上主键的 DDL 语句 "JDIY_MM"

ALTER TABLE "JDIY_MM" 
	ADD CONSTRAINT "CC1335121707445" PRIMARY KEY
		("ID");



------------------------------------------------
-- 表的 DDL 语句 "JDIY_TB"
------------------------------------------------
 

CREATE TABLE "JDIY_TB"  (
		  "ID" CHAR(10) NOT NULL , 
		  "TIT" VARCHAR(64) NOT NULL , 
		  "TB" VARCHAR(48) NOT NULL , 
		  "SORT" INTEGER NOT NULL WITH DEFAULT 100 , 
		  "FLAG" SMALLINT NOT NULL WITH DEFAULT 1 )   
		 IN "USERSPACE1" ; 


-- 表上主键的 DDL 语句 "JDIY_TB"

ALTER TABLE "JDIY_TB" 
	ADD CONSTRAINT "CC1335123400219" PRIMARY KEY
		("ID");



------------------------------------------------
-- 表的 DDL 语句 "JDIY_SYS"
------------------------------------------------
 

CREATE TABLE "JDIY_SYS"  (
		  "T0" VARCHAR(128) NOT NULL , 
		  "ID" CHAR(10) NOT NULL , 
		  "TID" CHAR(10) NOT NULL , 
		  "FRAME" VARCHAR(255) NOT NULL , 
		  "ISC" SMALLINT NOT NULL WITH DEFAULT 0 , 
		  "SORT" INTEGER NOT NULL WITH DEFAULT 100 , 
		  "DT1" TIMESTAMP NOT NULL , 
		  "DT2" TIMESTAMP NOT NULL , 
		  "HITS" INTEGER NOT NULL WITH DEFAULT 0 , 
		  "STATUS" VARCHAR(16) NOT NULL WITH DEFAULT '0' , 
		  "OWNER" CHAR(10) NOT NULL WITH DEFAULT '0' , 
		  "UPF" VARCHAR(1000) , 
		  "T1" VARCHAR(128) , 
		  "T2" VARCHAR(128) , 
		  "T3" VARCHAR(128) , 
		  "T4" VARCHAR(128) , 
		  "T5" VARCHAR(128) , 
		  "T6" VARCHAR(128) , 
		  "T7" VARCHAR(128) , 
		  "T8" VARCHAR(128) , 
		  "T9" VARCHAR(128) , 
		  "T10" VARCHAR(128) , 
		  "T11" VARCHAR(128) , 
		  "T12" VARCHAR(128) , 
		  "T13" VARCHAR(128) , 
		  "T14" VARCHAR(128) , 
		  "T15" CLOB(1048576) LOGGED NOT COMPACT )   
		 IN "JDIY_CLOB" ; 


-- 表上主键的 DDL 语句 "JDIY_SYS"

ALTER TABLE "JDIY_SYS" 
	ADD CONSTRAINT "CC1335122489789" PRIMARY KEY
		("ID");



------------------------------------------------
-- 表的 DDL 语句 "TEST"
------------------------------------------------
 

CREATE TABLE "TEST"  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +1  
		    INCREMENT BY +1  
		    MINVALUE +1  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    NO CACHE  
		    NO ORDER ) , 
		  "NAME" VARCHAR(255) , 
		  "AGE" INTEGER )   
		 IN "USERSPACE1" ; 


-- 表上主键的 DDL 语句 "TEST"

ALTER TABLE "TEST" 
	ADD CONSTRAINT "CC1335124281616" PRIMARY KEY
		("ID");



------------------------------------------------
-- 表的 DDL 语句 "VIP"
------------------------------------------------
 

CREATE TABLE "VIP"  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +0  
		    INCREMENT BY +1  
		    MINVALUE +0  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    NO CACHE  
		    NO ORDER ) , 
		  "VIPTYPE" INTEGER NOT NULL , 
		  "USERNAME" VARCHAR(64) , 
		  "PASSWORD" CHAR(32) , 
		  "REALNAME" VARCHAR(128) , 
		  "SEX" VARCHAR(6) , 
		  "EMAIL" VARCHAR(128) , 
		  "QQ" VARCHAR(64) , 
		  "MT" VARCHAR(64) , 
		  "TEL" VARCHAR(64) , 
		  "REMARK" VARCHAR(1000) , 
		  "REGISTERDATE" TIMESTAMP )   
		 IN "USERSPACE1" ; 


-- 表上主键的 DDL 语句 "VIP"

ALTER TABLE "VIP" 
	ADD CONSTRAINT "CC1335124617779" PRIMARY KEY
		("ID");



ALTER TABLE "VIP" ALTER COLUMN "ID" RESTART WITH 1;

------------------------------------------------
-- 表的 DDL 语句 "VIPTYPE"
------------------------------------------------
 

CREATE TABLE "VIPTYPE"  (
		  "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (  
		    START WITH +0  
		    INCREMENT BY +1  
		    MINVALUE +0  
		    MAXVALUE +2147483647  
		    NO CYCLE  
		    NO CACHE  
		    NO ORDER ) , 
		  "NAME" VARCHAR(64) , 
		  "SORT" INTEGER , 
		  "REMARK" VARCHAR(1000) )   
		 IN "USERSPACE1" ; 


-- 表上主键的 DDL 语句 "VIPTYPE"

ALTER TABLE "VIPTYPE" 
	ADD CONSTRAINT "CC1335125056650" PRIMARY KEY
		("ID");



ALTER TABLE "VIPTYPE" ALTER COLUMN "ID" RESTART WITH 1;

------------------------------------------------
-- 表的 DDL 语句 "ARTICLE"
------------------------------------------------
 

CREATE TABLE "ARTICLE"  (
		  "T0" VARCHAR(128) NOT NULL , 
		  "ID" CHAR(10) NOT NULL , 
		  "TID" CHAR(10) NOT NULL , 
		  "FRAME" VARCHAR(255) NOT NULL , 
		  "ISC" SMALLINT NOT NULL WITH DEFAULT 0 , 
		  "SORT" INTEGER NOT NULL WITH DEFAULT 100 , 
		  "DT1" TIMESTAMP NOT NULL , 
		  "DT2" TIMESTAMP NOT NULL , 
		  "HITS" INTEGER NOT NULL WITH DEFAULT 0 , 
		  "OWNER" CHAR(10) NOT NULL WITH DEFAULT 0 , 
		  "UPF" VARCHAR(1000) , 
		  "T1" VARCHAR(128) , 
		  "T2" VARCHAR(128) , 
		  "T3" VARCHAR(128) , 
		  "T4" VARCHAR(128) , 
		  "T5" VARCHAR(128) , 
		  "T6" VARCHAR(128) , 
		  "T7" VARCHAR(128) , 
		  "T8" VARCHAR(128) , 
		  "T9" VARCHAR(128) , 
		  "T10" VARCHAR(128) , 
		  "T11" VARCHAR(128) , 
		  "T12" VARCHAR(128) , 
		  "T13" VARCHAR(128) , 
		  "T14" VARCHAR(128) , 
		  "T15" CLOB(3096000) LOGGED NOT COMPACT )   
		 IN "JDIY_CLOB" ; 


-- 表上主键的 DDL 语句 "ARTICLE"

ALTER TABLE "ARTICLE" 
	ADD CONSTRAINT "CC_1335138195759" PRIMARY KEY
		("ID");









COMMIT WORK;

CONNECT RESET;

TERMINATE;

-- 指定的模式为: ADMINISTRATOR
-- 正在创建表的 DDL

-- 联合部分的模式名被忽略
;