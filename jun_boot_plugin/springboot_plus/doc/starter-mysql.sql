-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: starter
-- ------------------------------------------------------
-- Server version	5.6.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cms_blog`
--

DROP TABLE IF EXISTS `cms_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_blog`
--

LOCK TABLES `cms_blog` WRITE;
/*!40000 ALTER TABLE `cms_blog` DISABLE KEYS */;
INSERT INTO `cms_blog` VALUES (1,'hello','我的博客，内容是。。。','2018-02-22 09:53:05',1,'F0'),(2,'cccc','过年回家','2018-02-13 10:30:01',1,'F0');
/*!40000 ALTER TABLE `cms_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_audit`
--

DROP TABLE IF EXISTS `core_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_audit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FUNCTION_CODE` varchar(45) DEFAULT NULL,
  `FUNCTION_NAME` varchar(45) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  `USER_NAME` varchar(45) DEFAULT NULL,
  `IP` varchar(45) DEFAULT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  `SUCCESS` tinyint(4) DEFAULT NULL,
  `MESSAGE` varchar(250),
  `ORG_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_audit`
--

LOCK TABLES `core_audit` WRITE;
/*!40000 ALTER TABLE `core_audit` DISABLE KEYS */;
INSERT INTO `core_audit` VALUES (1,'org.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 19:58:50.876000',1,'',NULL),(2,'org.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 19:58:51.377000',1,'',NULL),(3,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:00:10.972000',1,'',NULL),(4,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:00:11.130000',1,'',NULL),(5,'user.add','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:00:39.562000',1,'',NULL),(6,'user.edit','用户编辑',1,'超级管理员','172.16.49.65','2018-02-06 20:10:15.399000',1,'',NULL),(7,'user.query','用户列表',1,'超级管理员','172.16.49.65','2018-02-06 20:10:15.846000',1,'',NULL),(8,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:10:16.882000',1,'',NULL),(9,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:10:17.056000',1,'',NULL),(10,'user.edit','用户编辑',1,'超级管理员','172.16.49.65','2018-02-06 20:14:46.789000',0,'java.sql.SQLException: Error on delete of \'C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\#sql978_2c3_6.MYI\' (Errcode: 13 - Permission denied)',NULL),(11,'user.edit','用户编辑',1,'超级管理员','172.16.49.65','2018-02-06 20:15:12.861000',1,'',NULL),(12,'user.query','用户列表',1,'超级管理员','172.16.49.65','2018-02-06 20:15:13.294000',1,'',NULL),(13,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:15:14.636000',1,'',NULL),(14,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:15:14.876000',1,'',NULL),(15,'audit','未定义',1,'超级管理员','172.16.49.65','2018-02-06 20:16:23.824000',1,'',NULL),(16,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:42:58.091000',1,'',NULL),(17,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:42:58.394000',1,'',NULL),(18,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:11.745000',1,'',NULL),(19,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:11.943000',1,'',NULL),(20,'user.add','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:13.058000',1,'',NULL),(21,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:28.999000',1,'',NULL),(22,'role.add','角色添加',1,'超级管理员','172.16.49.65','2018-02-07 09:53:29.162000',1,'',NULL),(23,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:43.017000',1,'',NULL),(24,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:43.148000',1,'',NULL),(25,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:53:45.338000',1,'',NULL),(26,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:03.185000',1,'',NULL),(27,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:03.646000',1,'',NULL),(28,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:06.264000',1,'',NULL),(29,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:07.508000',1,'',NULL),(30,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:09.524000',1,'',NULL),(31,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 09:56:10.738000',1,'',NULL),(32,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:02:00.590000',1,'',NULL),(33,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:02:00.803000',1,'',NULL),(34,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:02:02.991000',1,'',NULL),(35,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:05:40.367000',1,'',NULL),(36,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:05:40.496000',1,'',NULL),(37,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:05:42.173000',1,'',NULL),(38,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:06:02.218000',1,'',NULL),(39,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:07:45.273000',1,'',NULL),(40,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:07:45.943000',1,'',NULL),(41,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:07:47.715000',1,'',NULL),(42,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:03.378000',1,'',NULL),(43,'role.update','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:03.484000',1,'',NULL),(44,'role.edit','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:16.535000',1,'',NULL),(45,'role.query','未定义',1,'超级管理员','172.16.49.65','2018-02-07 10:08:16.691000',1,'',NULL);
/*!40000 ALTER TABLE `core_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_dict`
--

DROP TABLE IF EXISTS `core_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `VALUE` varchar(16) NOT NULL,
  `NAME` varchar(128) NOT NULL COMMENT '名称',
  `TYPE` varchar(64) NOT NULL COMMENT '字典编码',
  `TYPE_NAME` varchar(64) NOT NULL COMMENT '类型描述',
  `SORT` int(6) DEFAULT NULL COMMENT '排序',
  `PARENT` int(64) DEFAULT NULL COMMENT '父id',
  `DEL_FLAG` int(6) DEFAULT NULL COMMENT '删除标记',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `idx_code` (`TYPE`),
  KEY `idx_pid` (`PARENT`),
  KEY `idx_value` (`VALUE`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_dict`
--

LOCK TABLES `core_dict` WRITE;
/*!40000 ALTER TABLE `core_dict` DISABLE KEYS */;
INSERT INTO `core_dict` VALUES (1,'DA0','查看自己','data_access_type','数据权限',1,NULL,0,'11111111111111111123',NULL),(2,'DA1','查看本公司','data_access_type','数据权限',3,NULL,0,'hello,go',NULL),(3,'DA2','查看同机构','data_access_type','数据权限',3,NULL,0,NULL,NULL),(4,'DA3','查看本部门','data_access_type','数据权限',4,NULL,0,NULL,NULL),(5,'DA4','查看集团','data_access_type','数据权限',5,NULL,0,NULL,NULL),(6,'DA5','查看母公司','data_access_type','数据权限',6,NULL,0,NULL,'2017-10-14 11:45:02.000000'),(7,'FN0','普通功能','function_type','功能点类型',2,NULL,0,NULL,'2017-10-23 10:18:03.000000'),(8,'FN1','含数据权限','function_type','功能点类型',1,NULL,0,NULL,'2017-10-23 10:20:05.000000'),(9,'JT_01','管理岗位','job_type','岗位类型',1,NULL,0,NULL,NULL),(10,'JT_02','技术岗位','job_type','岗位类型',2,NULL,0,NULL,NULL),(11,'JT_S_01','董事会','job_sub_managment_type','管理岗位子类型',1,9,0,NULL,NULL),(12,'JT_S_02','秘书','job_sub_managment_type','管理岗位子类型',2,9,0,NULL,NULL),(13,'JT_S_03','技术经理','job_dev_sub_type','技术岗位子类型',1,10,0,NULL,NULL),(14,'JT_S_04','程序员','job_dev_sub_type','技术岗位子类型',2,10,0,NULL,NULL),(15,'MENU_M','菜单','menu_type','菜单类型',3,NULL,0,NULL,NULL),(16,'MENU_N','导航','menu_type','菜单类型',2,NULL,0,NULL,NULL),(17,'MENU_S','系统','menu_type','菜单类型',1,NULL,0,NULL,NULL),(18,'ORGT0','集团总部','org_type','机构类型',1,NULL,0,NULL,NULL),(19,'ORGT1','分公司','org_type','机构类型',2,NULL,0,NULL,NULL),(20,'ORGT2','部门','org_type','机构类型',3,NULL,0,NULL,NULL),(21,'ORGT3','小组','org_type','机构类型',4,NULL,0,NULL,NULL),(22,'R0','操作角色','role_type','数据权限',1,NULL,0,NULL,NULL),(23,'R1','工作流角色','role_type','用户角色',2,NULL,0,NULL,NULL),(24,'S0','禁用','user_state','用户状态',2,NULL,0,NULL,NULL),(25,'S1','启用','user_state','用户状态',1,NULL,0,NULL,NULL),(26,'昂按','随碟附送分','kkkk','水电费水电费',NULL,NULL,0,'','2018-02-28 15:43:34.447000'),(27,'昂按','随碟附送分','kkkk','水电费水电费',NULL,NULL,0,'','2018-02-28 15:46:08.035000'),(28,'sdf','sdfsdf','sfsdf','sdfsdf',1,NULL,1,'','2018-02-28 15:47:56.384000'),(29,'asas','sdfsd','sd','sd',NULL,NULL,1,'','2018-02-28 15:50:32.214000'),(30,'asas','sdfsd','sd','sd',NULL,NULL,1,'','2018-02-28 15:50:50.241000'),(31,'1','男','gender','性别',NULL,NULL,0,'','2018-03-10 11:36:19.930000'),(32,'2','女','gender','性别',NULL,NULL,0,NULL,'2018-03-10 11:36:20.001000');
/*!40000 ALTER TABLE `core_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_file`
--

DROP TABLE IF EXISTS `core_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_file` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL COMMENT '文件名称',
  `PATH` varchar(255) DEFAULT NULL COMMENT '路径',
  `BIZ_ID` varchar(128) DEFAULT NULL COMMENT '业务ID',
  `USER_ID` int(20) DEFAULT NULL COMMENT '上传人id',
  `CREATE_TIME` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `ORG_ID` int(20) DEFAULT NULL,
  `BIZ_TYPE` varchar(128) DEFAULT NULL,
  `FILE_BATCH_ID` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_file`
--

LOCK TABLES `core_file` WRITE;
/*!40000 ALTER TABLE `core_file` DISABLE KEYS */;
INSERT INTO `core_file` VALUES (19,'dict_upload_template.xls','20180311/dict_upload_template.xls.8caa38fb-52ef-4c73-85ea-abfb1f5c5dc4',NULL,1,'2018-03-11 15:36:58.906000',1,NULL,'18c0dd67-e334-47ba-8969-915bcf77c544'),(20,'dict_upload_template.xls','20180311/dict_upload_template.xls.f50b7f0f-d376-4a95-be6a-14f5aa4a81e6',NULL,1,'2018-03-11 15:37:15.884000',1,NULL,'335a583a-9c74-462d-be0a-5a82d427b1aa'),(21,'dict_upload_template.xls','20180311/dict_upload_template.xls.b0b9434d-e367-43ef-b8ac-366cf7b018b6',NULL,1,'2018-03-11 15:38:52.600000',1,NULL,'a5b887c6-101c-46e8-b9e2-b3b448edff34'),(22,'dict_upload_template.xls','20180311/dict_upload_template.xls.15f02d15-2dd0-4cb7-b2e5-4f7d72fb497d',NULL,1,'2018-03-11 15:39:02.091000',1,NULL,'833d96bc-797c-403f-aa2e-00e2b5a3cd71'),(23,'dict_upload_template.xls','20180311/dict_upload_template.xls.f12350bc-31da-4875-a78e-0135f512fb4c',NULL,1,'2018-03-11 15:41:52.323000',1,NULL,'0b2a66a3-8aa8-46b7-8bf0-ce9f68041cd8'),(24,'dict_upload_template.xls','20180311/dict_upload_template.xls.5bf626e5-2152-45a5-a432-fc2e9fcb7903',NULL,1,'2018-03-11 15:43:18.066000',1,NULL,'813725ab-2c07-4e48-a766-7ebbe3083212'),(25,'dict_upload_template.xls','20180311/dict_upload_template.xls.3cd3eb95-aab9-4105-8d28-76a723274709',NULL,1,'2018-03-11 15:43:58.096000',1,NULL,'4216455c-20d7-4912-bfc8-c8cca7e70e9f'),(26,'dict_upload_template.xls','20180311/dict_upload_template.xls.d3dc557b-1e77-4955-a3be-7a8b4f86407c',NULL,1,'2018-03-11 15:45:02.882000',1,NULL,'e42dc975-edd5-4d16-8529-fa69b56a5eb5'),(34,'dict_upload_template.xls','20180311/dict_upload_template.xls.d50f8245-ec3e-4de1-9742-0c5c12105f27','175',1,'2018-03-11 16:30:36.191000',1,NULL,'79b294da-8792-4bfd-9d84-e3f989b88cdf'),(37,'副本 功能列表.xlsx','20180311/副本 功能列表.xlsx.bc7554e3-2a30-4667-aa61-0e280340b2be','175',1,'2018-03-11 18:53:41.504000',1,'User','79b294da-8792-4bfd-9d84-e3f989b88cdf'),(38,'副本 功能列表.xlsx','20180311/副本 功能列表.xlsx.d991eb1f-24a9-4db1-87c1-7ef9d2b8a40a','175',1,'2018-03-11 22:10:57.873000',1,'User','79b294da-8792-4bfd-9d84-e3f989b88cdf');
/*!40000 ALTER TABLE `core_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_file_tag`
--

DROP TABLE IF EXISTS `core_file_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_file_tag` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `KEY` varchar(64) NOT NULL COMMENT 'key，关键字',
  `VALUE` varchar(255) NOT NULL COMMENT '关键字对应的值',
  `FILE_ID` int(20) NOT NULL COMMENT 'sys_file的id，文件id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文件标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_file_tag`
--

LOCK TABLES `core_file_tag` WRITE;
/*!40000 ALTER TABLE `core_file_tag` DISABLE KEYS */;
INSERT INTO `core_file_tag` VALUES (1,'customer','12332',1),(2,'type','crdit',2);
/*!40000 ALTER TABLE `core_file_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_function`
--

DROP TABLE IF EXISTS `core_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_function` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(250),
  `NAME` varchar(16) DEFAULT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  `ACCESS_URL` varchar(250),
  `PARENT_ID` int(65) DEFAULT NULL,
  `TYPE` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_function`
--

LOCK TABLES `core_function` WRITE;
/*!40000 ALTER TABLE `core_function` DISABLE KEYS */;
INSERT INTO `core_function` VALUES (1,'user','用户功能',NULL,'/admin/user/index.do',0,'FN0'),(2,'user.query','用户列表',NULL,NULL,1,'FN1'),(3,'user.edit','用户编辑',NULL,NULL,1,'FN0'),(6,'org','组织机构',NULL,'/admin/org/index.do',0,'FN0'),(7,'role','角色管理',NULL,'/admin/role/index.do',0,'FN0'),(8,'menu','菜单管理',NULL,'/admin/menu/index.do',0,'FN0'),(9,'function','功能点管理',NULL,'/admin/function/index.do',0,'FN0'),(10,'roleFunction','角色功能授权',NULL,'/admin/role/function.do',0,'FN0'),(11,'roleDataAccess','角色数据授权',NULL,'/admin/role/data.do',0,'FN0'),(12,'code','代码生成',NULL,'/core/codeGen/index.do',0,'FN0'),(13,'dict','字典管理',NULL,'/admin/dict/index.do',0,'FN0'),(18,'trace','审计查询',NULL,'/admin/audit/index.do',0,'FN0'),(19,'file','相关文档',NULL,'/trade/interAndRelate/file.do',0,'FN0'),(91,'test','测试','2017-10-11 16:59:01.000000','/test/test.do',6,'FN0'),(161,'role.add','角色添加','2017-10-23 09:45:05.000000',NULL,7,'FN0'),(167,'workflow.admin','工作流监控',NULL,'/admin/workflow/index.do',0,'FN0'),(180,'code.query','代码生成测试',NULL,NULL,12,'FN0'),(181,'blog.query','博客查询功能',NULL,'',182,'FN0'),(182,'blog','博客测试',NULL,'/admin/blog/index.do',0,'FN0'),(183,'code.project','项目生成','2018-03-01 09:38:17.068000','/core/codeGen/project.do',12,'FN0');
/*!40000 ALTER TABLE `core_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_menu`
--

DROP TABLE IF EXISTS `core_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_menu` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(16) DEFAULT NULL,
  `NAME` varchar(16) DEFAULT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  `FUNCTION_ID` int(6) DEFAULT NULL,
  `TYPE` varchar(6) DEFAULT NULL COMMENT '1,系统，2 导航 3 菜单项（对应某个功能点）',
  `PARENT_MENU_ID` int(65) DEFAULT NULL,
  `SEQ` int(65) DEFAULT NULL,
  `ICON` varchar(255) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_menu`
--

LOCK TABLES `core_menu` WRITE;
/*!40000 ALTER TABLE `core_menu` DISABLE KEYS */;
INSERT INTO `core_menu` VALUES (8,'系统管理','系统管理',NULL,NULL,'MENU_S',0,1,NULL),(10,'用户管理','用户管理',NULL,1,'MENU_M',18,1,NULL),(11,'组织机构管理','组织机构管理',NULL,6,'MENU_M',18,2,NULL),(12,'角色管理','角色管理',NULL,7,'MENU_M',18,3,NULL),(13,'菜单项','菜单项',NULL,8,'MENU_M',18,4,NULL),(14,'功能点管理','功能点管理',NULL,9,'MENU_M',18,5,NULL),(15,'字典数据管理','字典数据管理',NULL,13,'MENU_M',18,6,NULL),(16,'审计查询','审计查询',NULL,18,'MENU_M',19,7,NULL),(17,'代码生成','代码生成',NULL,12,'MENU_M',24,8,''),(18,'基础管理','基础管理',NULL,NULL,'MENU_N',8,1,NULL),(19,'监控管理','监控管理',NULL,NULL,'MENU_N',8,2,NULL),(20,'流程监控','流程监控',NULL,167,'MENU_M',19,3,NULL),(21,'角色功能授权','角色功能授权',NULL,10,'MENU_M',18,8,NULL),(22,'角色数据授权','角色数据授权',NULL,11,'MENU_M',18,9,NULL),(23,'博客测试','博客测试1',NULL,182,'MENU_M',19,9,''),(24,'代码生成导航','代码生成','2018-03-01 09:39:31.096000',NULL,'MENU_N',8,1,''),(25,'子系统生成','子系统生成','2018-03-01 09:42:35.839000',183,'MENU_M',24,1,'');
/*!40000 ALTER TABLE `core_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_org`
--

DROP TABLE IF EXISTS `core_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_org` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(16) NOT NULL,
  `NAME` varchar(16) NOT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  `PARENT_ORG_ID` int(20) DEFAULT NULL,
  `TYPE` varchar(6) NOT NULL COMMENT '1 公司，2 部门，3 小组',
  `DEL_FLAG` tinyint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_org`
--

LOCK TABLES `core_org` WRITE;
/*!40000 ALTER TABLE `core_org` DISABLE KEYS */;
INSERT INTO `core_org` VALUES (1,'集团公司','集团','2018-02-02 17:18:50.000000',NULL,'ORGT0',0),(3,'信息科技部门','信息科技部门',NULL,1,'ORGT2',0),(4,'贵州银行','贵州银行','2018-02-02 17:18:56.000000',1,'ORGT1',0),(5,'贵州银行金科','贵州银行金融科技开发公司',NULL,4,'ORGT1',0),(6,'金科研发','金科研发',NULL,5,'ORGT2',0),(7,'金科研发部门','金科研发部门','2018-02-05 13:49:52.754000',6,'ORGT2',0),(8,'金科研发2部','金科研发2部','2018-02-05 13:50:43.901000',6,'ORGT2',0);
/*!40000 ALTER TABLE `core_org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_role`
--

DROP TABLE IF EXISTS `core_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_role` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(16) DEFAULT NULL COMMENT '角色编码',
  `NAME` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `CREATE_TIME` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `TYPE` varchar(6) DEFAULT NULL COMMENT '1 可以配置 2 固定权限角色',
  PRIMARY KEY (`ID`),
  KEY `code_idx` (`CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_role`
--

LOCK TABLES `core_role` WRITE;
/*!40000 ALTER TABLE `core_role` DISABLE KEYS */;
INSERT INTO `core_role` VALUES (1,'DEPT_MANAGER','部门管理员',NULL,'R0'),(2,'CEO','公司CEO',NULL,'R0'),(3,'ASSIST','助理',NULL,'R0'),(12,'111','2324324','2017-09-06 04:08:00.000000','R0'),(13,'1111','哈哈','2017-09-06 04:09:05.000000','R0'),(15,'admin','ivy','2017-09-06 05:35:04.000000','R0'),(17,'123','我','2017-09-06 21:23:03.000000','R0'),(18,'23','234','2017-09-06 21:41:03.000000','R0'),(19,'132484','1','2017-09-06 21:42:02.000000','R0'),(173,'dept.admin','部门辅助管理员',NULL,'R0');
/*!40000 ALTER TABLE `core_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_role_function`
--

DROP TABLE IF EXISTS `core_role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_role_function` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(65) DEFAULT NULL,
  `FUNCTION_ID` int(65) DEFAULT NULL,
  `DATA_ACCESS_TYPE` tinyint(65) DEFAULT NULL,
  `DATA_ACCESS_POLICY` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_role_function`
--

LOCK TABLES `core_role_function` WRITE;
/*!40000 ALTER TABLE `core_role_function` DISABLE KEYS */;
INSERT INTO `core_role_function` VALUES (1,1,1,5,NULL),(2,1,2,4,NULL),(3,1,3,5,NULL),(4,2,2,2,NULL),(5,3,2,5,NULL),(6,3,3,5,NULL),(162,1,6,NULL,NULL),(164,1,91,NULL,NULL),(174,173,1,NULL,NULL),(176,173,2,5,NULL),(177,173,3,NULL,NULL),(178,173,167,NULL,NULL),(192,3,1,NULL,NULL),(194,3,12,NULL,NULL),(196,3,180,3,NULL),(197,NULL,1,NULL,NULL),(198,NULL,2,NULL,NULL),(199,NULL,3,NULL,NULL),(200,NULL,6,NULL,NULL),(201,NULL,91,NULL,NULL),(202,NULL,8,NULL,NULL),(205,1,182,NULL,NULL),(206,1,181,NULL,NULL);
/*!40000 ALTER TABLE `core_role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_role_menu`
--

DROP TABLE IF EXISTS `core_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_role_menu` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(65) DEFAULT NULL,
  `MENU_ID` int(65) DEFAULT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_role_menu`
--

LOCK TABLES `core_role_menu` WRITE;
/*!40000 ALTER TABLE `core_role_menu` DISABLE KEYS */;
INSERT INTO `core_role_menu` VALUES (1,1,10,NULL),(163,1,11,NULL),(175,173,10,NULL),(193,3,10,NULL),(195,3,17,NULL),(196,NULL,10,NULL),(197,NULL,11,NULL),(198,NULL,13,NULL),(200,1,23,NULL);
/*!40000 ALTER TABLE `core_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_user`
--

DROP TABLE IF EXISTS `core_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_user` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(16) DEFAULT NULL,
  `NAME` varchar(16) DEFAULT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  `ORG_ID` int(65) DEFAULT NULL,
  `STATE` varchar(16) DEFAULT NULL COMMENT '用户状态 1:启用 0:停用',
  `JOB_TYPE1` varchar(16) DEFAULT NULL,
  `DEL_FLAG` tinyint(6) DEFAULT NULL COMMENT '用户删除标记 0:未删除 1:已删除',
  `update_Time` datetime DEFAULT NULL,
  `JOB_TYPE0` varchar(16) DEFAULT NULL,
  `attachment_id` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_user`
--

LOCK TABLES `core_user` WRITE;
/*!40000 ALTER TABLE `core_user` DISABLE KEYS */;
INSERT INTO `core_user` VALUES (1,'admin','超级管理员1','123456','2017-09-13 09:21:03.000000',1,'S1','JT_S_01',0,'2017-09-13 09:21:03','JT_01',NULL),(171,'lixx','李小小',NULL,'2018-01-28 11:21:20.914000',3,'S1','JT_S_04',0,NULL,'JT_02',NULL),(172,'lixx2','李xx2','123456','2018-01-28 11:22:38.814000',4,'S1','JT_S_02',0,NULL,'JT_01',NULL),(173,'test1','test1','123','2018-01-28 14:44:55.407000',5,'S1','JT_S_04',0,NULL,'JT_02',NULL),(174,'hank250','李小熊',NULL,'2018-02-16 11:36:41.438000',4,'S1','JT_S_04',0,NULL,'JT_02',NULL),(175,'test123','test12344',NULL,'2018-03-11 16:19:21.675000',3,'S1','JT_S_04',0,NULL,'JT_02','79b294da-8792-4bfd-9d84-e3f989b88cdf');
/*!40000 ALTER TABLE `core_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `core_user_role`
--

DROP TABLE IF EXISTS `core_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `core_user_role` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(20) DEFAULT NULL,
  `ROLE_ID` int(20) DEFAULT NULL,
  `ORG_ID` int(20) DEFAULT NULL,
  `CREATE_TIME` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `core_user_role`
--

LOCK TABLES `core_user_role` WRITE;
/*!40000 ALTER TABLE `core_user_role` DISABLE KEYS */;
INSERT INTO `core_user_role` VALUES (1,3,1,4,NULL),(2,4,2,5,NULL),(3,75,3,6,'2017-09-21 18:03:05.000000'),(35,1,1,1,'2017-09-06 01:12:02.000000'),(36,1,3,6,'2017-09-06 03:33:05.000000'),(38,1,1,3,'2017-09-06 03:35:02.000000'),(41,1,1,5,'2017-09-06 03:58:02.000000'),(42,3,3,1,'2017-09-06 04:01:00.000000'),(47,47,3,1,'2017-09-06 22:00:01.000000'),(49,5,3,4,'2017-09-06 22:01:00.000000'),(52,47,2,1,'2017-09-07 01:12:02.000000'),(53,48,3,4,'2017-09-07 01:14:04.000000'),(55,68,2,3,'2017-09-07 21:42:03.000000'),(125,74,1,4,'2017-10-17 09:37:02.000000'),(144,74,3,NULL,'2017-10-17 16:55:00.000000'),(145,67,3,NULL,'2017-10-17 16:55:01.000000'),(146,73,3,NULL,'2017-10-17 16:55:02.000000'),(147,22,3,NULL,'2017-10-17 16:55:04.000000'),(148,68,3,NULL,'2017-10-17 16:56:00.000000'),(168,72,1,3,'2017-10-24 14:40:04.000000'),(169,41,1,NULL,'2017-10-25 08:58:01.000000'),(171,170,1,5,'2017-10-25 09:08:05.000000'),(172,171,1,4,'2018-02-02 09:36:40.967000');
/*!40000 ALTER TABLE `core_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-12  9:08:56
