-- ##################
-- postgresql 10.3
-- create by li
-- 2018-05-04 
-- #################

-- ↓↓↓初始化后还需一下两步:↓↓↓

-- ①修改datasource
-- spring.datasource.url=jdbc:postgresql://ip:port/YOUR-DATABASE 
-- spring.datasource.username=YOUR-NAME 
-- spring.datasource.password=YOUR-PASSWD 
-- spring.datasource.driverClassName=org.postgresql.Driver 

-- ②修改beetlsql.dbStyle
-- beetlsql.dbStyle=org.beetl.sql.core.db.PostgresStyle


DROP TABLE IF EXISTS cms_blog;
CREATE TABLE cms_blog (
  id serial PRIMARY key ,
  title varchar(255) DEFAULT NULL,
  content varchar(512) DEFAULT NULL,
  create_time TIMESTAMP DEFAULT NOW(),
  create_user_id int4 DEFAULT NULL,
  type varchar(255) DEFAULT NULL
) ;

--
-- 
--

INSERT INTO cms_blog VALUES
    (3,'hello','我的博客，内容是。。。','2018-02-22 09:53:05',1,'F0'),(2,'hi','五四青年节','2018-05-04 18:30:01',1,'F0');


--
-- Table structure for table core_audit
--

DROP TABLE IF EXISTS core_audit;
CREATE TABLE core_audit (
  ID serial PRIMARY KEY ,
  FUNCTION_CODE varchar(45) DEFAULT NULL,
  FUNCTION_NAME varchar(45) DEFAULT NULL,
  USER_ID int4 DEFAULT NULL,
  USER_NAME varchar(45) DEFAULT NULL,
  IP varchar(45) DEFAULT NULL,
  CREATE_TIME timestamp DEFAULT NULL,
  SUCCESS int4 DEFAULT NULL,
  MESSAGE text,
  ORG_ID varchar(45) DEFAULT NULL
  );

--
-- Dumping data for table core_audit
--

INSERT INTO core_audit VALUES
(1,'org.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 19:58:50.876000',1,'',NULL),
(2,'org.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 19:58:51.377000',1,'',NULL),
(3,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:00:10.972000',1,'',NULL),
(4,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:00:11.130000',1,'',NULL),
(5,'user.add','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:00:39.562000',1,'',NULL),
(6,'user.edit','用户编辑',1,'超级管理员','172.16.49.65','2018-02-06 20:10:15.399000',1,'',NULL),
(7,'user.query','用户列表',1,'超级管理员','172.16.49.65','2018-02-06 20:10:15.846000',1,'',NULL),
(8,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:10:16.882000',1,'',NULL),
(9,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:10:17.056000',1,'',NULL),
(10,'user.edit','用户编辑',1,'超级管理员','172.16.49.65','2018-02-06 20:14:46.789000',0,'',NULL),
(11,'user.edit','用户编辑',1,'超级管理员','172.16.49.65','2018-02-06 20:15:12.861000',1,'',NULL),
(12,'user.query','用户列表',1,'超级管理员','172.16.49.65','2018-02-06 20:15:13.294000',1,'',NULL),
(13,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:15:14.636000',1,'',NULL),
(14,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:15:14.876000',1,'',NULL),
(15,'audit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:16:23.824000',1,'',NULL),
(16,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:42:58.091000',1,'',NULL),
(17,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:42:58.394000',1,'',NULL),
(18,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:11.745000',1,'',NULL),
(19,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:11.943000',1,'',NULL),
(20,'user.add','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:13.058000',1,'',NULL),
(21,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:28.999000',1,'',NULL),
(22,'role.add','角色添加',1,'超级管理员','172.16.49.65','2018-02-07 09:53:29.162000',1,'',NULL),
(23,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:43.017000',1,'',NULL),
(24,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:43.148000',1,'',NULL),
(25,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:45.338000',1,'',NULL),
(26,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:03.185000',1,'',NULL),
(27,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:03.646000',1,'',NULL),
(28,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:06.264000',1,'',NULL),
(29,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:07.508000',1,'',NULL),
(30,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:09.524000',1,'',NULL),
(31,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:10.738000',1,'',NULL),
(32,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:02:00.590000',1,'',NULL),
(33,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:02:00.803000',1,'',NULL),
(34,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:02:02.991000',1,'',NULL),
(35,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:05:40.367000',1,'',NULL),
(36,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:05:40.496000',1,'',NULL),
(37,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:05:42.173000',1,'',NULL),
(38,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:06:02.218000',1,'',NULL),
(39,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:07:45.273000',1,'',NULL),
(40,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:07:45.943000',1,'',NULL),
(41,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:07:47.715000',1,'',NULL),
(42,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:03.378000',1,'',NULL),
(43,'role.update','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:03.484000',1,'',NULL),
(44,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:16.535000',1,'',NULL),
(45,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:16.691000',1,'',NULL);

--
-- Table structure for table core_dict
--

DROP TABLE IF EXISTS core_dict;
CREATE TABLE core_dict (
  ID serial primary key,
  VALUE varchar(16) NOT NULL,
  NAME varchar(128) NOT NULL,
  TYPE varchar(64) NOT NULL ,
  TYPE_NAME varchar(64) NOT null,
  SORT int4 DEFAULT null,
  PARENT int8 DEFAULT NULL ,
  DEL_FLAG int4 DEFAULT NULL ,
  REMARK varchar(255) default null ,
  CREATE_TIME timestamp DEFAULT null
);

create index idx_code ON core_dict(type);
create index idx_pid on core_dict(PARENT);
create index idx_value on  core_dict(VALUE);
comment on column core_dict.NAME is '名称';
comment on column core_dict.TYPE is '类型描述';
comment on column core_dict.TYPE_NAME is '类型描述';
comment on column core_dict.SORT is '排序';
comment on column core_dict.PARENT is '父id';
comment on column core_dict.DEL_FLAG is '删除标记';
comment on column core_dict.REMARK is '备注';
comment on column core_dict.CREATE_TIME is '创建时间';

--
-- Dumping data for table core_dict
--


INSERT INTO core_dict values
(1,'DA0','查看自己','data_access_type','数据权限',1,NULL,0,'11111111111111111123',NULL),
(2,'DA1','查看本公司','data_access_type','数据权限',3,NULL,0,'hello,go',NULL),
(3,'DA2','查看同机构','data_access_type','数据权限',3,NULL,0,NULL,NULL),
(4,'DA3','查看本部门','data_access_type','数据权限',4,NULL,0,NULL,NULL),
(5,'DA4','查看集团','data_access_type','数据权限',5,NULL,0,NULL,NULL),
(6,'DA5','查看母公司','data_access_type','数据权限',6,NULL,0,NULL,'2017-10-14 11:45:02.000000'),
(7,'FN0','普通功能','function_type','功能点类型',2,NULL,0,NULL,'2017-10-23 10:18:03.000000'),
(8,'FN1','含数据权限','function_type','功能点类型',1,NULL,0,NULL,'2017-10-23 10:20:05.000000'),
(9,'JT_01','管理岗位','job_type','岗位类型',1,NULL,0,NULL,NULL),
(10,'JT_02','技术岗位','job_type','岗位类型',2,NULL,0,NULL,NULL),
(11,'JT_S_01','董事会','job_sub_managment_type','管理岗位子类型',1,9,0,NULL,NULL),
(12,'JT_S_02','秘书','job_sub_managment_type','管理岗位子类型',2,9,0,NULL,NULL),
(13,'JT_S_03','技术经理','job_dev_sub_type','技术岗位子类型',1,10,0,NULL,NULL),
(14,'JT_S_04','程序员','job_dev_sub_type','技术岗位子类型',2,10,0,NULL,NULL),
(15,'MENU_M','菜单','menu_type','菜单类型',3,NULL,0,NULL,NULL),
(16,'MENU_N','导航','menu_type','菜单类型',2,NULL,0,NULL,NULL),
(17,'MENU_S','系统','menu_type','菜单类型',1,NULL,0,NULL,NULL),
(18,'ORGT0','集团总部','org_type','机构类型',1,NULL,0,NULL,NULL),
(19,'ORGT1','分公司','org_type','机构类型',2,NULL,0,NULL,NULL),
(20,'ORGT2','部门','org_type','机构类型',3,NULL,0,NULL,NULL),
(21,'ORGT3','小组','org_type','机构类型',4,NULL,0,NULL,NULL),
(22,'R0','操作角色','role_type','数据权限',1,NULL,0,NULL,NULL),
(23,'R1','工作流角色','role_type','用户角色',2,NULL,0,NULL,NULL),
(24,'S0','禁用','user_state','用户状态',2,NULL,0,NULL,NULL),
(25,'S1','启用','user_state','用户状态',1,NULL,0,NULL,NULL),
(26,'昂按','随碟附送分','kkkk','水电费水电费',NULL,NULL,0,'','2018-02-28 15:43:34.447000'),
(27,'昂按','随碟附送分','kkkk','水电费水电费',NULL,NULL,0,'','2018-02-28 15:46:08.035000'),
(28,'sdf','sdfsdf','sfsdf','sdfsdf',1,NULL,1,'','2018-02-28 15:47:56.384000'),
(29,'asas','sdfsd','sd','sd',NULL,NULL,1,'','2018-02-28 15:50:32.214000'),
(30,'asas','sdfsd','sd','sd',NULL,NULL,1,'','2018-02-28 15:50:50.241000'),
(31,'1','男','gender','性别',NULL,NULL,0,'','2018-03-10 11:36:19.930000'),
(32,'2','女','gender','性别',NULL,NULL,0,NULL,'2018-03-10 11:36:20.001000');

--
-- Table structure for table core_file
--

DROP TABLE IF EXISTS core_file;
CREATE TABLE core_file (
  ID serial primary key,
  NAME varchar(64) DEFAULT NULL ,
  PATH varchar(255) DEFAULT NULL ,
  BIZ_ID varchar(128) DEFAULT NULL ,
  USER_ID int4 DEFAULT NULL ,
  CREATE_TIME timestamp DEFAULT NULL ,
  ORG_ID int4 DEFAULT NULL,
  BIZ_TYPE varchar(128) DEFAULT NULL,
  FILE_BATCH_ID varchar(128) DEFAULT NULL
) ;
comment on column core_file.NAME is '文件名称';
comment on column core_file.PATH is '路径';
comment on column core_file.BIZ_ID is '业务ID';
comment on column core_file.USER_ID is '上传人id';
comment on column core_file.CREATE_TIME is '创建时间';
--
-- Dumping data for table core_file
--


INSERT INTO core_file VALUES 
(19,'dict_upload_template.xls','20180311/dict_upload_template.xls.8caa38fb-52ef-4c73-85ea-abfb1f5c5dc4',NULL,1,'2018-03-11 15:36:58.906000',1,NULL,'18c0dd67-e334-47ba-8969-915bcf77c544'),
(20,'dict_upload_template.xls','20180311/dict_upload_template.xls.f50b7f0f-d376-4a95-be6a-14f5aa4a81e6',NULL,1,'2018-03-11 15:37:15.884000',1,NULL,'335a583a-9c74-462d-be0a-5a82d427b1aa'),
(21,'dict_upload_template.xls','20180311/dict_upload_template.xls.b0b9434d-e367-43ef-b8ac-366cf7b018b6',NULL,1,'2018-03-11 15:38:52.600000',1,NULL,'a5b887c6-101c-46e8-b9e2-b3b448edff34'),
(22,'dict_upload_template.xls','20180311/dict_upload_template.xls.15f02d15-2dd0-4cb7-b2e5-4f7d72fb497d',NULL,1,'2018-03-11 15:39:02.091000',1,NULL,'833d96bc-797c-403f-aa2e-00e2b5a3cd71'),
(23,'dict_upload_template.xls','20180311/dict_upload_template.xls.f12350bc-31da-4875-a78e-0135f512fb4c',NULL,1,'2018-03-11 15:41:52.323000',1,NULL,'0b2a66a3-8aa8-46b7-8bf0-ce9f68041cd8'),
(24,'dict_upload_template.xls','20180311/dict_upload_template.xls.5bf626e5-2152-45a5-a432-fc2e9fcb7903',NULL,1,'2018-03-11 15:43:18.066000',1,NULL,'813725ab-2c07-4e48-a766-7ebbe3083212'),
(25,'dict_upload_template.xls','20180311/dict_upload_template.xls.3cd3eb95-aab9-4105-8d28-76a723274709',NULL,1,'2018-03-11 15:43:58.096000',1,NULL,'4216455c-20d7-4912-bfc8-c8cca7e70e9f'),
(26,'dict_upload_template.xls','20180311/dict_upload_template.xls.d3dc557b-1e77-4955-a3be-7a8b4f86407c',NULL,1,'2018-03-11 15:45:02.882000',1,NULL,'e42dc975-edd5-4d16-8529-fa69b56a5eb5'),
(34,'dict_upload_template.xls','20180311/dict_upload_template.xls.d50f8245-ec3e-4de1-9742-0c5c12105f27','175',1,'2018-03-11 16:30:36.191000',1,NULL,'79b294da-8792-4bfd-9d84-e3f989b88cdf'),
(37,'副本 功能列表.xlsx','20180311/副本 功能列表.xlsx.bc7554e3-2a30-4667-aa61-0e280340b2be','175',1,'2018-03-11 18:53:41.504000',1,'User','79b294da-8792-4bfd-9d84-e3f989b88cdf'),
(38,'副本 功能列表.xlsx','20180311/副本 功能列表.xlsx.d991eb1f-24a9-4db1-87c1-7ef9d2b8a40a','175',1,'2018-03-11 22:10:57.873000',1,'User','79b294da-8792-4bfd-9d84-e3f989b88cdf');

--
-- Table structure for table core_file_tag
--

DROP TABLE IF EXISTS core_file_tag;
CREATE TABLE core_file_tag (
  ID serial PRIMARY KEY,
  KEY varchar(64) NOT NULL,
  VALUE varchar(255) NOT NULL ,
  FILE_ID int4 NOT NULL
);
comment on column core_file_tag.KEY is 'key，关键字';
comment on column core_file_tag.VALUE is '关键字对应的值';
comment on column core_file_tag.FILE_ID is 'sys_file的id，文件id';

--
-- Dumping data for table core_file_tag
--

INSERT INTO core_file_tag VALUES 
	(1,'customer','12332',1),(2,'type','crdit',2);

--
-- Table structure for table core_function
--

DROP TABLE IF EXISTS core_function;
CREATE TABLE core_function (
  ID serial  PRIMARY KEY,
  CODE text,
  NAME varchar(16) DEFAULT NULL,
  CREATE_TIME timestamp DEFAULT NULL,
  ACCESS_URL text,
  PARENT_ID int4 DEFAULT NULL,
  TYPE varchar(4) DEFAULT NULL
) ;

--
-- Dumping data for table core_function
--

INSERT INTO core_function VALUES 
(1,'user','用户功能',NULL,'/admin/user/index.do',0,'FN0'),
(2,'user.query','用户列表',NULL,NULL,1,'FN1'),
(3,'user.edit','用户编辑',NULL,NULL,1,'FN0'),
(6,'org','组织机构',NULL,'/admin/org/index.do',0,'FN0'),
(7,'role','角色管理',NULL,'/admin/role/index.do',0,'FN0'),
(8,'menu','菜单管理',NULL,'/admin/menu/index.do',0,'FN0'),
(9,'function','功能点管理',NULL,'/admin/function/index.do',0,'FN0'),
(10,'roleFunction','角色功能授权',NULL,'/admin/role/function.do',0,'FN0'),
(11,'roleDataAccess','角色数据授权',NULL,'/admin/role/data.do',0,'FN0'),
(12,'code','代码生成',NULL,'/core/codeGen/index.do',0,'FN0'),
(13,'dict','字典管理',NULL,'/admin/dict/index.do',0,'FN0'),
(18,'trace','审计查询',NULL,'/admin/audit/index.do',0,'FN0'),
(19,'file','相关文档',NULL,'/trade/interAndRelate/file.do',0,'FN0'),
(91,'test','测试','2017-10-11 16:59:01.000000','/test/test.do',6,'FN0'),
(161,'role.add','角色添加','2017-10-23 09:45:05.000000',NULL,7,'FN0'),
(167,'workflow.admin','工作流监控',NULL,'/admin/workflow/index.do',0,'FN0'),
(180,'code.query','代码生成测试',NULL,NULL,12,'FN0'),
(181,'blog.query','博客查询功能',NULL,'',182,'FN0'),
(182,'blog','博客测试',NULL,'/admin/blog/index.do',0,'FN0'),
(183,'code.project','项目生成','2018-03-01 09:38:17.068000','/core/codeGen/project.do',12,'FN0');

--
-- Table structure for table core_menu
--

DROP TABLE IF EXISTS core_menu;
CREATE TABLE core_menu (
  ID serial primary key ,
  CODE varchar(16) DEFAULT NULL,
  NAME varchar(16) DEFAULT NULL,
  CREATE_TIME timestamp DEFAULT NULL,
  FUNCTION_ID int4 DEFAULT NULL,
  TYPE varchar(6) DEFAULT NULL ,
  PARENT_MENU_ID int4 DEFAULT NULL,
  SEQ int4 DEFAULT NULL,
  ICON varchar(255) DEFAULT NULL
) ;
comment on column core_menu.TYPE is '1,系统，2 导航 3 菜单项（对应某个功能点）';
comment on column core_menu.ICON is '图标';

--
-- Dumping data for table core_menu
--

INSERT INTO core_menu VALUES 
(8,'系统管理','系统管理',NULL,NULL,'MENU_S',0,1,NULL),
(10,'用户管理','用户管理',NULL,1,'MENU_M',18,1,NULL),
(11,'组织机构管理','组织机构管理',NULL,6,'MENU_M',18,2,NULL),
(12,'角色管理','角色管理',NULL,7,'MENU_M',18,3,NULL),
(13,'菜单项','菜单项',NULL,8,'MENU_M',18,4,NULL),
(14,'功能点管理','功能点管理',NULL,9,'MENU_M',18,5,NULL),
(15,'字典数据管理','字典数据管理',NULL,13,'MENU_M',18,6,NULL),
(16,'审计查询','审计查询',NULL,18,'MENU_M',19,7,NULL),
(17,'代码生成','代码生成',NULL,12,'MENU_M',24,8,''),
(18,'基础管理','基础管理',NULL,NULL,'MENU_N',8,1,NULL),
(19,'监控管理','监控管理',NULL,NULL,'MENU_N',8,2,NULL),
(20,'流程监控','流程监控',NULL,167,'MENU_M',19,3,NULL),
(21,'角色功能授权','角色功能授权',NULL,10,'MENU_M',18,8,NULL),
(22,'角色数据授权','角色数据授权',NULL,11,'MENU_M',18,9,NULL),
(23,'博客测试','博客测试1',NULL,182,'MENU_M',19,9,''),
(24,'代码生成导航','代码生成','2018-03-01 09:39:31.096000',NULL,'MENU_N',8,1,''),
(25,'子系统生成','子系统生成','2018-03-01 09:42:35.839000',183,'MENU_M',24,1,'');

--
-- Table structure for table core_org
--

DROP TABLE IF EXISTS core_org;
CREATE TABLE core_org (
  ID serial primary key,
  CODE varchar(16) NOT NULL,
  NAME varchar(16) NOT NULL,
  CREATE_TIME timestamp DEFAULT NULL,
  PARENT_ORG_ID int4 DEFAULT NULL,
  TYPE varchar(6) NOT NULL ,
  DEL_FLAG int2 DEFAULT NULL
) ;
comment on column core_org.type is '1 公司，2 部门，3 小组';
--
-- Dumping data for table core_org
--

INSERT INTO core_org VALUES 
(1,'集团公司','集团','2018-02-02 17:18:50.000000',NULL,'ORGT0',0),
(3,'信息科技部门','信息科技部门',NULL,1,'ORGT2',0),
(4,'贵州银行','贵州银行','2018-02-02 17:18:56.000000',1,'ORGT1',0),
(5,'贵州银行金科','贵州银行金融科技开发公司',NULL,4,'ORGT1',0),
(6,'金科研发','金科研发',NULL,5,'ORGT2',0),
(7,'金科研发部门','金科研发部门','2018-02-05 13:49:52.754000',6,'ORGT2',0),
(8,'金科研发2部','金科研发2部','2018-02-05 13:50:43.901000',6,'ORGT2',0);


--
-- Table structure for table core_role
--

DROP TABLE IF EXISTS core_role;
CREATE TABLE core_role (
  ID serial primary key,
  CODE varchar(16) DEFAULT NULL ,
  NAME varchar(255) DEFAULT NULL ,
  CREATE_TIME timestamp DEFAULT NULL ,
  TYPE varchar(6) DEFAULT NULL
);
comment on column core_role.CODE is '角色编码';
comment on column core_role.NAME is '创建时间';
comment on column core_role.TYPE is '1 可以配置 2 固定权限角色';
create index code_idx on core_role(CODE);
--
-- Dumping data for table core_role
--

INSERT INTO core_role VALUES 
(1,'DEPT_MANAGER','部门管理员',NULL,'R0'),
(2,'CEO','公司CEO',NULL,'R0'),
(3,'ASSIST','助理',NULL,'R0'),
(12,'111','2324324','2017-09-06 04:08:00.000000','R0'),
(13,'1111','哈哈','2017-09-06 04:09:05.000000','R0'),
(15,'admin','ivy','2017-09-06 05:35:04.000000','R0'),
(17,'123','我','2017-09-06 21:23:03.000000','R0'),
(18,'23','234','2017-09-06 21:41:03.000000','R0'),
(19,'132484','1','2017-09-06 21:42:02.000000','R0'),
(173,'dept.admin','部门辅助管理员',NULL,'R0');

--
-- Table structure for table core_role_function
--

DROP TABLE IF EXISTS core_role_function;
CREATE TABLE core_role_function (
  ID serial primary key,
  ROLE_ID int4 DEFAULT NULL,
  FUNCTION_ID int4 DEFAULT NULL,
  DATA_ACCESS_TYPE int2 DEFAULT NULL,
  DATA_ACCESS_POLICY varchar(128) DEFAULT NULL
);

--
-- Dumping data for table core_role_function
--

INSERT INTO core_role_function VALUES 
(1,1,1,5,NULL),
(2,1,2,4,NULL),
(3,1,3,5,NULL),
(4,2,2,2,NULL),
(5,3,2,5,NULL),
(6,3,3,5,NULL),
(162,1,6,NULL,NULL),
(164,1,91,NULL,NULL),
(174,173,1,NULL,NULL),
(176,173,2,5,NULL),(177,173,3,NULL,NULL),
(178,173,167,NULL,NULL),(192,3,1,NULL,NULL),(194,3,12,NULL,NULL),(196,3,180,3,NULL),
(197,NULL,1,NULL,NULL),(198,NULL,2,NULL,NULL),(199,NULL,3,NULL,NULL),(200,NULL,6,NULL,NULL),
(201,NULL,91,NULL,NULL),(202,NULL,8,NULL,NULL),(205,1,182,NULL,NULL),(206,1,181,NULL,NULL);

--
-- Table structure for table core_role_menu
--

DROP TABLE IF EXISTS core_role_menu;
CREATE TABLE core_role_menu (
  ID serial primary key,
  ROLE_ID int4 DEFAULT NULL,
  MENU_ID int4 DEFAULT NULL,
  CREATE_TIME timestamp DEFAULT NULL
) ;

--
-- Dumping data for table core_role_menu
--

INSERT INTO core_role_menu VALUES 
(1,1,10,NULL),(163,1,11,NULL),(175,173,10,NULL),(193,3,10,NULL),
(195,3,17,NULL),(196,NULL,10,NULL),(197,NULL,11,NULL),(198,NULL,13,NULL),(200,1,23,NULL);

--
-- Table structure for table core_user
--

DROP TABLE IF EXISTS core_user;
CREATE TABLE core_user (
  ID serial primary key,
  CODE varchar(16) DEFAULT NULL,
  NAME varchar(16) DEFAULT NULL,
  PASSWORD varchar(64) DEFAULT NULL,
  CREATE_TIME timestamp DEFAULT NULL,
  ORG_ID int4 DEFAULT NULL,
  STATE varchar(16) DEFAULT NULL , 
  JOB_TYPE1 varchar(16) DEFAULT NULL,
  DEL_FLAG int2 DEFAULT NULL  ,
  update_Time timestamp DEFAULT NULL,
  JOB_TYPE0 varchar(16) DEFAULT NULL,
  attachment_id varchar(128) DEFAULT null
  );
  comment on column core_user.STATE is '用户状态 1:启用 0:停用';
  comment on column core_user.DEL_FLAG is '用户删除标记 0:未删除 1:已删除';

--
-- Dumping data for table core_user
--

INSERT INTO core_user VALUES 
(1,'admin','超级管理员1','123456','2017-09-13 09:21:03.000000',1,'S1','JT_S_01',0,'2017-09-13 09:21:03','JT_01',NULL),
(171,'lixx','李小小',NULL,'2018-01-28 11:21:20.914000',3,'S1','JT_S_04',0,NULL,'JT_02',NULL),
(172,'lixx2','李xx2','123456','2018-01-28 11:22:38.814000',4,'S1','JT_S_02',0,NULL,'JT_01',NULL),
(173,'test1','test1','123','2018-01-28 14:44:55.407000',5,'S1','JT_S_04',0,NULL,'JT_02',NULL),
(174,'hank250','李小熊',NULL,'2018-02-16 11:36:41.438000',4,'S1','JT_S_04',0,NULL,'JT_02',NULL),
(175,'test123','test12344',NULL,'2018-03-11 16:19:21.675000',3,'S1','JT_S_04',0,NULL,'JT_02','79b294da-8792-4bfd-9d84-e3f989b88cdf');

--
-- Table structure for table core_user_role
--

DROP TABLE IF EXISTS core_user_role;
CREATE TABLE core_user_role (
  ID serial primary key,
  USER_ID int4 DEFAULT NULL,
  ROLE_ID int4 DEFAULT NULL,
  ORG_ID int4 DEFAULT NULL,
  CREATE_TIME timestamp NULL
) ;

--
-- Dumping data for table core_user_role
--


INSERT INTO core_user_role VALUES
(1,3,1,4,NULL),(2,4,2,5,NULL),(3,75,3,6,'2017-09-21 18:03:05.000000'),
(35,1,1,1,'2017-09-06 01:12:02.000000'),(36,1,3,6,'2017-09-06 03:33:05.000000'),
(38,1,1,3,'2017-09-06 03:35:02.000000'),(41,1,1,5,'2017-09-06 03:58:02.000000'),
(42,3,3,1,'2017-09-06 04:01:00.000000'),(47,47,3,1,'2017-09-06 22:00:01.000000'),
(49,5,3,4,'2017-09-06 22:01:00.000000'),(52,47,2,1,'2017-09-07 01:12:02.000000'),
(53,48,3,4,'2017-09-07 01:14:04.000000'),(55,68,2,3,'2017-09-07 21:42:03.000000'),
(125,74,1,4,'2017-10-17 09:37:02.000000'),(144,74,3,NULL,'2017-10-17 16:55:00.000000'),
(145,67,3,NULL,'2017-10-17 16:55:01.000000'),(146,73,3,NULL,'2017-10-17 16:55:02.000000'),
(147,22,3,NULL,'2017-10-17 16:55:04.000000'),(148,68,3,NULL,'2017-10-17 16:56:00.000000'),
(168,72,1,3,'2017-10-24 14:40:04.000000'),(169,41,1,NULL,'2017-10-25 08:58:01.000000'),
(171,170,1,5,'2017-10-25 09:08:05.000000'),(172,171,1,4,'2018-02-02 09:36:40.967000');

