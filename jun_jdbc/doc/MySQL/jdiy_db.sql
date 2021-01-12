-- MySQL dump 10.13  Distrib 5.1.61, for debian-linux-gnu (i486)
--
-- Host: localhost    Database: jdiy_db
-- ------------------------------------------------------
-- Server version	5.1.61-2

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `t0` varchar(128) NOT NULL,
  `id` char(10) CHARACTER SET ascii NOT NULL,
  `tid` char(10) CHARACTER SET ascii NOT NULL,
  `frame` varchar(255) CHARACTER SET ascii NOT NULL,
  `isc` bit(1) NOT NULL DEFAULT b'0',
  `sort` int(11) unsigned NOT NULL DEFAULT '100',
  `dt1` datetime NOT NULL DEFAULT '1900-01-01 00:00:27',
  `dt2` datetime NOT NULL DEFAULT '1900-01-01 00:00:27',
  `hits` int(11) unsigned NOT NULL DEFAULT '0',
  `owner` char(10) CHARACTER SET ascii NOT NULL DEFAULT '0',
  `upf` text,
  `t1` varchar(128) DEFAULT NULL,
  `t2` varchar(128) DEFAULT NULL,
  `t3` varchar(128) DEFAULT NULL,
  `t4` varchar(128) DEFAULT NULL,
  `t5` varchar(128) DEFAULT NULL,
  `t6` varchar(128) DEFAULT NULL,
  `t7` varchar(128) DEFAULT NULL,
  `t8` varchar(128) DEFAULT NULL,
  `t9` varchar(128) DEFAULT NULL,
  `t10` varchar(128) DEFAULT NULL,
  `t11` varchar(128) DEFAULT NULL,
  `t12` varchar(128) DEFAULT NULL,
  `t13` varchar(128) DEFAULT NULL,
  `t14` varchar(128) DEFAULT NULL,
  `t15` mediumtext,
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`),
  KEY `sort` (`sort`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES ('新闻中心','y0gw0te8no','0000000000','.','',20,'2011-12-11 00:03:53','2011-12-11 00:15:14',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('热点新闻','y0gw0tejir','y0gw0te8no','.y0gw0te8no.','',100,'2011-12-11 00:04:07','2011-12-11 00:04:07',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('国际新闻','y0gw0tenil','y0gw0te8no','.y0gw0te8no.','',100,'2011-12-11 00:04:12','2011-12-11 00:04:12',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('国内新闻','y0gw0tes4a','y0gw0te8no','.y0gw0te8no.','',100,'2011-12-11 00:04:18','2011-12-11 00:04:18',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('财经新闻','y0gw0tew2d','y0gw0te8no','.y0gw0te8no.','',100,'2011-12-11 00:04:23','2011-12-11 00:04:23',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('子栏目','y0gw0tgje2','y0gw0tejir','.y0gw0te8no.y0gw0tejir.','',100,'2011-12-11 00:05:40','2011-12-11 00:05:40',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('单篇文章栏目','y0gw0thasx','0000000000','.','',30,'2011-12-11 00:06:16','2011-12-11 00:14:45',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('总裁致辞','y0gw0tiwvk','y0gw0thasx','.y0gw0thasx.','',20,'2011-12-11 00:07:31','2012-02-26 22:57:22',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'<div>\r\n<div>欢迎使用JDiy智能开发平台!</div>\r\n<br />\r\n<div>&nbsp;</div>\r\n<div><span style=\"color: rgb(0, 0, 255);\"><span style=\"font-size: larger;\">左边这些管理菜单都可由您在&quot;<strong>系统配置</strong>&quot;中动态创建生成哦~~</span></span></div>\r\n</div>'),('示例档案','y0gw105rib','y0gw0tpcl6','.y0gw0tpcl6.','\0',100,'2011-12-11 03:13:15','2011-12-11 03:25:57',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('关于我们','y0gw0tjm62','y0gw0thasx','.y0gw0thasx.','',10,'2011-12-11 00:08:04','2012-02-26 22:55:04',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'<div>欢迎使用JDiy智能开发平台!</div>\r\n<br />\r\n<div>&nbsp;</div>\r\n<div><span style=\"color: rgb(0, 0, 255);\"><span style=\"font-size: larger;\">左边这些管理菜单都可由您在&quot;<strong>系统配置</strong>&quot;中动态创建生成哦~~</span></span></div>'),('联系我们','y0gw0tjop7','y0gw0thasx','.y0gw0thasx.','',40,'2011-12-11 00:08:07','2012-02-26 22:57:32',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'<div>\r\n<div>欢迎使用JDiy智能开发平台!</div>\r\n<br />\r\n<div>&nbsp;</div>\r\n<div><span style=\"color: rgb(0, 0, 255);\"><span style=\"font-size: larger;\">左边这些管理菜单都可由您在&quot;<strong>系统配置</strong>&quot;中动态创建生成哦~~</span></span></div>\r\n</div>'),('这些均为演示,根据实际需要设置节点','y0gw0tknkk','0000000000','.','',10,'2011-12-11 00:08:52','2011-12-11 00:08:52',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('这仅仅是一个演示.','y0gw0usadj','y0gw0tejir','.y0gw0te8no.y0gw0tejir.','\0',10,'2011-12-11 00:42:48','2012-02-26 22:57:40',0,'0','','3','JDiy,JSer','jdiy.net','cmd,mutipic,index',NULL,NULL,NULL,'子秋',NULL,'0',NULL,NULL,NULL,NULL,'<div>这仅仅是一个演示.</div>\r\n<div>&nbsp;</div>\r\n<div>\r\n<div>欢迎使用JDiy智能开发平台!</div>\r\n<br />\r\n<div>&nbsp;</div>\r\n<div><span style=\"color: rgb(0, 0, 255);\"><span style=\"font-size: larger;\">左边这些管理菜单都可由您在&quot;<strong>系统配置</strong>&quot;中动态创建生成哦~~</span></span></div>\r\n</div>'),('资料下载','y0gw0tpcl6','0000000000','.','',100,'2011-12-11 00:12:31','2011-12-11 00:14:15',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('示例档案2','y0gw10i4yb','y0gw0tpcl6','.y0gw0tpcl6.','\0',100,'2011-12-11 03:22:52','2011-12-11 03:25:23',0,'0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jdiy_mm`
--

DROP TABLE IF EXISTS `jdiy_mm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jdiy_mm` (
  `id` char(10) NOT NULL,
  `tid` char(10) CHARACTER SET ascii NOT NULL DEFAULT '0',
  `cid` varchar(10) CHARACTER SET ascii NOT NULL DEFAULT '0',
  `tb` varchar(48) DEFAULT NULL,
  `rid` varchar(10) DEFAULT NULL,
  `ctrl` varchar(10) DEFAULT NULL,
  `tit` varchar(64) NOT NULL,
  `sName` varchar(64) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sort` int(8) unsigned NOT NULL DEFAULT '50',
  `prm` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`),
  KEY `sort` (`sort`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jdiy_mm`
--

LOCK TABLES `jdiy_mm` WRITE;
/*!40000 ALTER TABLE `jdiy_mm` DISABLE KEYS */;
INSERT INTO `jdiy_mm` VALUES ('z0000000m0','z0000000ma','z0000000c1','jdiy_sys','z0000000s0','SYSROLESLS','角色管理','角色修改','',10,''),('z0000000m8','z0000000ma','z0000000c1','jdiy_sys','z0000000s0','SYSROLESIN','角色添加','','',20,''),('z0000000m9','z0000000ma','z0000000c1','jdiy_sys','z0000000s0','z000000ct2','用户管理','修改用户','',30,''),('z0000000ma','0000000000','z0000000c1','',NULL,'','系统安全管理','','',30,''),('z0000000mb','z0000000ma','z0000000c1','jdiy_sys','z0000000s0','z000000ct1','用户添加','','',40,''),('y0gw0rhmjo','y0gw0qwu7n','z0gf5bief3','vip','','y0gw0r271e','会员手工添加','','',20,'mmOpen'),('y0gw0skw12','y0gw0qwu7n','z0gf5bief3','vip','','y0gw0sjjed','会员管理','修改会员信息','',10,'mmOpen'),('z0gf5bqha5','z0gf5bimaw','z0gf5bief3','article','y0gw0te8no','y0gp0zds4k','文章修改','修改文章','',20,'mmOpen'),('z0gf5bpvmd','z0gf5bimaw','z0gf5bief3','article','y0gw0te8no','y0gp0yw3m6','文章添加','','',10,'mmOpen'),('y0gw0zmdgp','0000000000','z0gf5bief3','','','','图片/附件/水印','','',40,'mmOpen'),('z0gf5bimaw','0000000000','z0gf5bief3','','','','示例2(节点/内容)','','',10,'mmOpen'),('z0000000c1','0000000000','0000000000',NULL,NULL,NULL,'安全管理',NULL,'',500,'isSys'),('z0gf5bief3','0000000000','0000000000',NULL,NULL,NULL,'生成的菜单示例',NULL,'',100,''),('y0gw0qwu7n','0000000000','z0gf5bief3','','','','示例3(自定义表)','','',20,'mmOpen'),('y0gv57f19d','z0gf5bimaw','z0gf5bief3','article','y0gw0te8no','y0gw0w4w8m','文章栏目(树形)','修改文章类别','',30,'mmOpen'),('y0gv57fjrf','z0gf5bimaw','z0gf5bief3','article','y0gw0te8no','y0gw0v91kb','文章审核','文章修改/审核','',25,'mmOpen'),('y0gw0ze9q4','0000000000','z0gf5bief3','','','','示例1(节点单篇)','','',5,'mmOpen'),('y0gw0zfawn','y0gw0ze9q4','z0gf5bief3','article','y0gw0tjm62','y0gw0vsuhg','关于我们','','',10,'editOnly'),('y0gw0qxmm6','y0gw0qwu7n','z0gf5bief3','viptype','','y0gw0qm525','会员类别添加','','',40,'mmOpen'),('y0gw0qykz2','y0gw0qwu7n','z0gf5bief3','viptype','','y0gw0qqx9y','会员类别管理','修改会员类别','',30,'mmOpen'),('y0gw0zfyvu','y0gw0ze9q4','z0gf5bief3','article','y0gw0tjop7','y0gw0vsuhg','联系我们','','',20,'editOnly'),('y0gw0zj4wm','y0gw0ze9q4','z0gf5bief3','article','y0gw0tiwvk','y0gw0vsuhg','总裁致辞 ','','',15,'editOnly'),('y0gw1035xx','y0gw0zmdgp','z0gf5bief3','article','y0gw0tpcl6','y0gw0zopwp','下载档案添加','','',10,'mmOpen'),('y0gw1048cf','y0gw0zmdgp','z0gf5bief3','article','y0gw0tpcl6','y0gw1008nr','下载档案管理','修改档案','',20,'mmOpen'),('y0gw10qiio','0000000000','z0gf5bief3','','','','其它(默认关闭)','','',50,'mmClose'),('y0gw10rbeu','y0gw10qiio','z0gf5bief3','','','','自定义功能页面','','test.jsp',10,'mmOpen');
/*!40000 ALTER TABLE `jdiy_mm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jdiy_sys`
--

DROP TABLE IF EXISTS `jdiy_sys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jdiy_sys` (
  `t0` varchar(128) NOT NULL,
  `id` char(10) CHARACTER SET ascii NOT NULL,
  `tid` char(10) CHARACTER SET ascii NOT NULL,
  `frame` varchar(255) CHARACTER SET ascii NOT NULL,
  `isc` bit(1) NOT NULL DEFAULT b'0',
  `sort` int(11) unsigned NOT NULL DEFAULT '100',
  `dt1` datetime NOT NULL DEFAULT '1900-01-01 00:00:27',
  `dt2` datetime NOT NULL DEFAULT '1900-01-01 00:00:27',
  `hits` int(11) unsigned NOT NULL DEFAULT '0',
  `status` varchar(16) NOT NULL DEFAULT '0',
  `owner` char(10) CHARACTER SET ascii NOT NULL DEFAULT '0',
  `upf` text,
  `t1` varchar(128) DEFAULT NULL,
  `t2` varchar(128) DEFAULT NULL,
  `t3` varchar(128) DEFAULT NULL,
  `t4` varchar(128) DEFAULT NULL,
  `t5` varchar(128) DEFAULT NULL,
  `t6` varchar(128) DEFAULT NULL,
  `t7` varchar(128) DEFAULT NULL,
  `t8` varchar(128) DEFAULT NULL,
  `t9` varchar(128) DEFAULT NULL,
  `t10` varchar(128) DEFAULT NULL,
  `t11` varchar(128) DEFAULT NULL,
  `t12` varchar(128) DEFAULT NULL,
  `t13` varchar(128) DEFAULT NULL,
  `t14` varchar(128) DEFAULT NULL,
  `t15` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jdiy_sys`
--

LOCK TABLES `jdiy_sys` WRITE;
/*!40000 ALTER TABLE `jdiy_sys` DISABLE KEYS */;
INSERT INTO `jdiy_sys` VALUES ('系统角色','z0000000s0','0000000000','.','',10,'2007-09-28 00:00:00','2007-09-28 00:00:00',0,'JC_PROTECTED','0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('系统会员','z0000000s1','0000000000','.','',20,'2007-09-28 00:00:00','2007-09-28 00:00:00',0,'JC_PROTECTED','0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('超级管理员','z0000000s5','z0000000s0','.z0000000s0.','',20,'2010-09-09 21:50:50','2011-12-11 03:37:48',0,'0','0','','拥有全部的管理权限.','','','','','','','','','','','','6D3AE14DB14237652A5EE2AA0E3DDA38Z20T3PUS','CAF7B75037FF41034898675D5548E0D7Z20T3PUS','\'z0000000mb\', \'z0000000m9\', \'z0000000m8\', \'z0000000m0\', \'z0000000ma\', \'z0000000c1\', \'y0gw10rbeu\', \'y0gw10qiio\', \'y0gw1048cf\', \'y0gw1035xx\', \'y0gw0zmdgp\', \'y0gw0qxmm6\', \'y0gw0qykz2\', \'y0gw0rhmjo\', \'y0gw0skw12\', \'y0gw0qwu7n\', \'y0gv57f19d\', \'y0gv57fjrf\', \'z0gf5bqha5\', \'z0gf5bpvmd\', \'z0gf5bimaw\', \'y0gw0zfyvu\', \'y0gw0zj4wm\', \'y0gw0zfawn\', \'y0gw0ze9q4\', \'z0gf5bief3\''),('系统基本配置','z0000000s8','z0000000sa','.z0000000sa.','',40,'2007-09-28 00:00:00','2007-09-28 00:00:00',0,'JC_PROTECTED','0','','','0','','','','','','','','','','','','','/jspceo/JC_Home.jsp'),('系统配置','z0000000sa','0000000000','.','',30,'2007-09-28 00:00:00','2007-09-28 00:00:00',0,'JC_PROTECTED','0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('folier','z0fz2ftpp9','z0000000s5','.z0000000s0.z0000000s5.','\0',100,'2009-09-01 00:00:00','2011-12-11 03:40:10',0,'0','0','','C0B9963CCA3816A39EFF2A947A21CF0B','子秋','男','99','aaa@bbb.cc','示例用户,可以删除','示例用户,可以删除','示例用户,可以删除','325000','','','','','','测试用,可删除.'),('角色示例','y0gv65ljev','z0000000s0','.z0000000s0.','',100,'2011-11-19 13:04:38','2011-12-11 03:38:06',0,'0','0','','xx',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'\'y0gw10rbeu\', \'y0gw10qiio\', \'y0gw1048cf\', \'y0gw1035xx\', \'y0gw0zmdgp\', \'y0gw0qxmm6\', \'y0gw0qykz2\', \'y0gw0rhmjo\', \'y0gw0skw12\', \'y0gw0qwu7n\', \'y0gv57f19d\', \'y0gv57fjrf\', \'z0gf5bqha5\', \'z0gf5bpvmd\', \'z0gf5bimaw\', \'y0gw0zfyvu\', \'y0gw0zj4wm\', \'y0gw0zfawn\', \'y0gw0ze9q4\', \'z0gf5bief3\'');
/*!40000 ALTER TABLE `jdiy_sys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jdiy_tb`
--

DROP TABLE IF EXISTS `jdiy_tb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jdiy_tb` (
  `id` char(10) CHARACTER SET ascii NOT NULL DEFAULT '0',
  `tit` varchar(64) NOT NULL DEFAULT '.',
  `tb` varchar(48) CHARACTER SET ascii NOT NULL,
  `sort` int(11) unsigned DEFAULT '50',
  `flag` bit(1) DEFAULT b'1' COMMENT '0: systb  1: custom tb',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jdiy_tb`
--

LOCK TABLES `jdiy_tb` WRITE;
/*!40000 ALTER TABLE `jdiy_tb` DISABLE KEYS */;
INSERT INTO `jdiy_tb` VALUES ('y0gw0tcb4c','文章(节点表)','article',50,'\0'),('y0gw0qld73','会员(自定义表)','vip',50,''),('y0gw0qlvms','会员类别(自定义表)','viptype',50,'');
/*!40000 ALTER TABLE `jdiy_tb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jdiy_vi`
--

DROP TABLE IF EXISTS `jdiy_vi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jdiy_vi` (
  `id` char(10) CHARACTER SET ascii NOT NULL DEFAULT '0',
  `tb` varchar(48) DEFAULT '0',
  `tbpk` varchar(64) DEFAULT NULL,
  `dmpk` tinyint(1) NOT NULL,
  `tit` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'untitled',
  `type` varchar(32) CHARACTER SET ascii NOT NULL DEFAULT 'input0',
  `prm` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jdiy_vi`
--

LOCK TABLES `jdiy_vi` WRITE;
/*!40000 ALTER TABLE `jdiy_vi` DISABLE KEYS */;
INSERT INTO `jdiy_vi` VALUES ('z000000ct1','0','id',0,'系统用户_输入表单','input0','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>0</JD_sort>\r\n<JD_name>登录账号</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_script>if(fm.isNull(\"t0\",\"用户名不能为空\")) return false;</JD_script>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t1</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>10</JD_sort>\r\n<JD_name>登录密码</JD_name>\r\n<JD_type>password</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t2</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>20</JD_sort>\r\n<JD_name>真实姓名</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t3</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>30</JD_sort>\r\n<JD_name>性别</JD_name>\r\n<JD_type>radio</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_valuelist>男{男}\r\n女{女}\r\n保密{保密}</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t4</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>40</JD_sort>\r\n<JD_name>年龄</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value>99</JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_script>if(!fm.isInt(\"t4\",\'请输入正确的年龄\',10,100)) return false;</JD_script>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t5</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>50</JD_sort>\r\n<JD_name>电子邮件</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t6</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>60</JD_sort>\r\n<JD_name>手机号码</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t7</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>70</JD_sort>\r\n<JD_name>电话号码</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t8</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>80</JD_sort>\r\n<JD_name>联系地址</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_style>style=\"width:400px\"</JD_style>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t9</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_sort>90</JD_sort>\r\n<JD_name>邮编</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>tid</JD_field>\r\n<JD_name>用户角色</JD_name>\r\n<JD_depth>0</JD_depth>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_prm>noFirst,noRepeatTid</JD_prm>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('z000000ct2','0','id',0,'系统用户_列表管理','list','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_pageSize>20</JD_pageSize>\r\n<JD_outType>input0</JD_outType>\r\n<JD_addFilter></JD_addFilter>\r\n<JD_inObj>z000000ct1</JD_inObj>\r\n<JD_imgWH>0,0</JD_imgWH>\r\n<JD_outFields>tid,t0,t2,t3,t5,t6</JD_outFields>\r\n<JD_keyFields></JD_keyFields>\r\n<JD_bat></JD_bat>\r\n<JD_cut>48</JD_cut>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_url></JD_url>\r\n<JD_urlW></JD_urlW>\r\n<JD_urlH></JD_urlH>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('SYSROLESLS','0','',0,'JDiy_系统角色管理','list','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_pageSize>20</JD_pageSize>\r\n<JD_outType>input1</JD_outType>\r\n<JD_addFilter></JD_addFilter>\r\n<JD_inObj>SYSROLESIN</JD_inObj>\r\n<JD_imgWH>0,0</JD_imgWH>\r\n<JD_outFields>sort,t0,t1</JD_outFields>\r\n<JD_keyFields>sort,t0,t1</JD_keyFields>\r\n<JD_bat>-Sort-,-Delete-</JD_bat>\r\n<JD_cut>48</JD_cut>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_url></JD_url>\r\n<JD_urlW></JD_urlW>\r\n<JD_urlH></JD_urlH>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0r271e','vip','id',1,'会员_demo','input0','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>username</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>用户名</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_script>if(JSer(\"#username\").val()==\"\"){\r\n        alert(\"脚本验证DEMO: 会员用户名必填.\");\r\n        JSer(\"#username\").focus();\r\n        return false;\r\n}</JD_script>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>password</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>登录密码</JD_name>\r\n<JD_type>password</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>viptype</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>会员类型</JD_name>\r\n<JD_type>select</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_listTable>viptype</JD_listTable>\r\n<JD_listTxt>name</JD_listTxt>\r\n<JD_listVal>id</JD_listVal>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>realname</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>真实姓名</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sex</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>姓别</JD_name>\r\n<JD_type>radio</JD_type>\r\n<JD_value>保密</JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_valuelist>保密{保密};\r\n男{男};\r\n女{女};</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>qq</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>QQ号码</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>email</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>电子邮件</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>mt</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>手机号码</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>tel</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>联系电话</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>registerDate</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>添加时间</JD_name>\r\n<JD_type>datetime</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>remark</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>个人说明</JD_name>\r\n<JD_type>textarea</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_style>style=\"width:400px;height:80px;\"</JD_style>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('SYSROLESIN','0',NULL,0,'JDiy_系统角色输入','input1','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>角色名称</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sort</JD_field>\r\n<JD_name>排序索引</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_prm>noRepeatTid</JD_prm>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t1</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>简要备注</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_style>style=\"width:99%;\"</JD_style>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t15</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>操作权限</JD_name>\r\n<JD_type>hidden</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0sjjed','vip','id',1,'会员管理_demo','list','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_pageSize>20</JD_pageSize>\r\n<JD_outType>input0</JD_outType>\r\n<JD_addFilter></JD_addFilter>\r\n<JD_inObj>y0gw0r271e</JD_inObj>\r\n<JD_imgWH>0,0</JD_imgWH>\r\n<JD_outFields>viptype,username,realname,sex,qq,registerDate</JD_outFields>\r\n<JD_keyFields>viptype,username,realname,qq,sex,registerDate</JD_keyFields>\r\n<JD_bat>-Delete-</JD_bat>\r\n<JD_cut>48</JD_cut>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_url></JD_url>\r\n<JD_urlW></JD_urlW>\r\n<JD_urlH></JD_urlH>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gp0zds4k','0','id',0,'文章管理_demo','list','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_pageSize>15</JD_pageSize>\r\n<JD_outType>input0</JD_outType>\r\n<JD_addFilter></JD_addFilter>\r\n<JD_inObj>y0gp0yw3m6</JD_inObj>\r\n<JD_imgWH>0,0</JD_imgWH>\r\n<JD_outFields>tid,sort,t0,t1,t2,t8,hits</JD_outFields>\r\n<JD_keyFields>tid,t0,t8,t1,t2</JD_keyFields>\r\n<JD_bat></JD_bat>\r\n<JD_cut>48</JD_cut>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_url></JD_url>\r\n<JD_urlW></JD_urlW>\r\n<JD_urlH></JD_urlH>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gp0yw3m6','0','id',0,'文章录入_demo','input0','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>文章标题</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_style>style=\"width:98%\"</JD_style>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t2</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>关键字</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_style>title=\"多个关键字之间用逗号分隔,例如:\r\nJDiy, JSer, java框架\"</JD_style>\r\n<JD_script>var v=JSer(\"#t2\").val();\r\nv=v.replace(/\\s+/,\"\").replace(/,{2,}/,\",\");\r\nif(v==\'\' || v.indexOf(\"\'\")!=-1){\r\n    alert(\"对不起，文章标签必填，且不能含有单引号。\");\r\n    JSer(\"#t2\").select();\r\n    return false;\r\n}\r\nif(v.charAt(0)==\',\' || v.charAt(v.length-1)==\',\'){\r\n    alert(\"对不起，文章标签输入不合法，必须是如下格式：\\r\\n标签1, 标签2, ...标签n\");\r\n    JSer(\"#t2\").select();\r\n    return false;\r\n}\r\nJSer(\"#t2\").val(v);</JD_script>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t1</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>推荐星级</JD_name>\r\n<JD_type>radio</JD_type>\r\n<JD_value>0</JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_valuelist>无{0};\r\n★{1};\r\n★★{2};\r\n★★★{3};\r\n★★★★{4};\r\n★★★★★{5};</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t8</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>作者</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t3</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>文章来源</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t15</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>文章内容</JD_name>\r\n<JD_type>webeditor</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>f0</JD_field>\r\n<JD_txf>f</JD_txf>\r\n<JD_name>列表页图片</JD_name>\r\n<JD_type>file</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_ext>gif,jpg</JD_ext>\r\n<JD_shortWH>120,120</JD_shortWH>\r\n<JD_shortLock>1</JD_shortLock>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t4</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>发布参数</JD_name>\r\n<JD_type>checkbox</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_valuelist>首页显示{index};\r\n图文{pic};\r\n多图{mutipic};\r\n最新{new};\r\n推荐{cmd};</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>tid</JD_field>\r\n<JD_name>上级栏目</JD_name>\r\n<JD_depth>0</JD_depth>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sort</JD_field>\r\n<JD_name>排序索引</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>dt1</JD_field>\r\n<JD_name>发布时间</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_prm>noFirst</JD_prm>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t10</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>审核状态</JD_name>\r\n<JD_type>hidden</JD_type>\r\n<JD_value>0</JD_value>\r\n<JD_cols>0</JD_cols>\r\n<JD_script>JSer(\"#t10\").val(0);</JD_script>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0qm525','viptype','id',1,'会员类别_demo','input0','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>name</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>会员类别名称</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_script>if(JSer(\"#name\").val()==\"\"){\r\n        alert(\"脚本验证DEMO: 会员类别名称必填.\");\r\n        JSer(\"#name\").focus();\r\n        return false;\r\n}</JD_script>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sort</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>排序索引</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value>100</JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>remark</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>备注说明</JD_name>\r\n<JD_type>textarea</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_style>style=\"width:480px;height:120px;\"</JD_style>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0qqx9y','viptype','id',1,'会员类别管理_demo','list','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_pageSize>20</JD_pageSize>\r\n<JD_outType>input0</JD_outType>\r\n<JD_addFilter></JD_addFilter>\r\n<JD_inObj>y0gw0qm525</JD_inObj>\r\n<JD_imgWH>0,0</JD_imgWH>\r\n<JD_outFields>name,sort,remark</JD_outFields>\r\n<JD_keyFields></JD_keyFields>\r\n<JD_bat>-Delete-</JD_bat>\r\n<JD_cut>100</JD_cut>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_url></JD_url>\r\n<JD_urlW></JD_urlW>\r\n<JD_urlH></JD_urlH>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0v1hkq','0','id',0,'文章录入(审核)_demo','input0','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>文章标题</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_style>style=\"width:98%\"</JD_style>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t2</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>关键字</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_style>title=\"多个关键字之间用逗号分隔,例如:\r\nJDiy, JSer, java框架\"</JD_style>\r\n<JD_script>var v=JSer(\"#t2\").val();\r\nv=v.replace(/\\s+/,\"\").replace(/,{2,}/,\",\");\r\nif(v==\'\' || v.indexOf(\"\'\")!=-1){\r\n    alert(\"对不起，文章标签必填，且不能含有单引号。\");\r\n    JSer(\"#t2\").select();\r\n    return false;\r\n}\r\nif(v.charAt(0)==\',\' || v.charAt(v.length-1)==\',\'){\r\n    alert(\"对不起，文章标签输入不合法，必须是如下格式：\\r\\n标签1, 标签2, ...标签n\");\r\n    JSer(\"#t2\").select();\r\n    return false;\r\n}\r\nJSer(\"#t2\").val(v);</JD_script>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t1</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>推荐星级</JD_name>\r\n<JD_type>radio</JD_type>\r\n<JD_value>0</JD_value>\r\n<JD_cols>2</JD_cols>\r\n<JD_valuelist>无{0};\r\n★{1};\r\n★★{2};\r\n★★★{3};\r\n★★★★{4};\r\n★★★★★{5};</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t8</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>作者</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t3</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>文章来源</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>2</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_prm>noFirst</JD_prm>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>dt1</JD_field>\r\n<JD_name>发布时间</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sort</JD_field>\r\n<JD_name>排序索引</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>tid</JD_field>\r\n<JD_name>上级栏目</JD_name>\r\n<JD_depth>0</JD_depth>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t15</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>文章内容</JD_name>\r\n<JD_type>webeditor</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>f0</JD_field>\r\n<JD_txf>f</JD_txf>\r\n<JD_name>列表页图片</JD_name>\r\n<JD_type>file</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_ext>gif,jpg</JD_ext>\r\n<JD_shortWH>120,120</JD_shortWH>\r\n<JD_shortLock>1</JD_shortLock>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t4</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>发布参数</JD_name>\r\n<JD_type>checkbox</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_valuelist>首页显示{index};\r\n图文{pic};\r\n多图{mutipic};\r\n最新{new};\r\n推荐{cmd};</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t10</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>审核状态</JD_name>\r\n<JD_type>radio</JD_type>\r\n<JD_value>0</JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_valuelist>未审核{0};\r\n已审核{1};</JD_valuelist>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0vrpjs','0','id',0,'栏目','input1','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>名称</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>tid</JD_field>\r\n<JD_name>上级栏目</JD_name>\r\n<JD_depth>0</JD_depth>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sort</JD_field>\r\n<JD_name>排序索引</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_prm>noRepeatTid</JD_prm>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0v91kb','0','id',0,'文章管理(审核)_demo','list','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_pageSize>15</JD_pageSize>\r\n<JD_outType>input0</JD_outType>\r\n<JD_addFilter></JD_addFilter>\r\n<JD_inObj>y0gw0v1hkq</JD_inObj>\r\n<JD_imgWH>0,0</JD_imgWH>\r\n<JD_outFields>tid,sort,t0,hits,t1,t10</JD_outFields>\r\n<JD_keyFields>t10,tid,t1,t0,hits</JD_keyFields>\r\n<JD_bat>t10,t1,tid,-Sort-,-Delete-</JD_bat>\r\n<JD_cut>48</JD_cut>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_url></JD_url>\r\n<JD_urlW></JD_urlW>\r\n<JD_urlH></JD_urlH>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0vsuhg','0','id',0,'栏目文章','input1','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>名称</JD_name>\r\n<JD_type>hidden</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>tid</JD_field>\r\n<JD_name>上级栏目</JD_name>\r\n<JD_depth>0</JD_depth>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_prm>noRepeatTid</JD_prm>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>t15</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>内容</JD_name>\r\n<JD_type>webeditor</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0zopwp','0','id',0,'档案下载','input0','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>t0</JD_field>\r\n<JD_txf>t</JD_txf>\r\n<JD_name>档案名称</JD_name>\r\n<JD_type>text</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>sort</JD_field>\r\n<JD_name>排序索引</JD_name>\r\n<JD_txf>t</JD_txf>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>spic</JD_field>\r\n<JD_txf>f</JD_txf>\r\n<JD_name>缩图</JD_name>\r\n<JD_type>file</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_ext>gif,jpg</JD_ext>\r\n<JD_shortWH>200,200</JD_shortWH>\r\n<JD_shortLock>1</JD_shortLock>\r\n<JD_syImg>/watermask.png</JD_syImg>\r\n<JD_syPosition>5</JD_syPosition>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>file1</JD_field>\r\n<JD_txf>f</JD_txf>\r\n<JD_name>档案附件上传</JD_name>\r\n<JD_type>file</JD_type>\r\n<JD_value></JD_value>\r\n<JD_cols>1</JD_cols>\r\n<JD_ext>zip,rar,pdf,doc,xls,txt</JD_ext>\r\n</JD_ITEM>\r\n\r\n<JD_ITEM>\r\n<JD_field>content</JD_field>\r\n<JD_txf>x</JD_txf>\r\n<JD_name>详细介绍</JD_name>\r\n<JD_type>webeditor</JD_type>\r\n<JD_value>这个字段将写进xml(不放在数据库中)</JD_value>\r\n<JD_cols>1</JD_cols>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw0w4w8m','0','id',0,'栏目树(允许深度为2级)','tree','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_inObj>y0gw0vrpjs</JD_inObj>\r\n<JD_depth>2</JD_depth>\r\n<JD_fd0>t0=名称</JD_fd0>\r\n<JD_fd1></JD_fd1>\r\n<JD_depthAdd>1,2</JD_depthAdd>\r\n<JD_depthEdt>0</JD_depthEdt>\r\n<JD_depthDel>1</JD_depthDel>\r\n<JD_usrTit>栏目</JD_usrTit>\r\n<JD_bat></JD_bat>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n'),('y0gw1008nr','0','id',0,'档案下载管理(图片视图)','image','\r\n\r\n<JD_DATASET>\r\n<JD_ITEM>\r\n<JD_field>prm</JD_field>\r\n<JD_imgW>200</JD_imgW>\r\n<JD_imgH>200</JD_imgH>\r\n<JD_inObj>y0gw0zopwp</JD_inObj>\r\n<JD_img0>spic=缩图</JD_img0>\r\n<JD_rownum>2</JD_rownum>\r\n<JD_pagesize>6</JD_pagesize>\r\n<JD_dbl>edt</JD_dbl>\r\n<JD_outFields>t0,sort</JD_outFields>\r\n<JD_keyFields></JD_keyFields>\r\n<JD_bat>-Sort-,-Delete-</JD_bat>\r\n</JD_ITEM>\r\n\r\n</JD_DATASET>\r\n\r\n');
/*!40000 ALTER TABLE `jdiy_vi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip`
--

DROP TABLE IF EXISTS `vip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `viptype` int(11) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `realname` varchar(128) DEFAULT NULL,
  `sex` varchar(6) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `qq` varchar(64) DEFAULT NULL,
  `mt` varchar(64) DEFAULT NULL,
  `tel` varchar(64) DEFAULT NULL,
  `remark` text,
  `registerdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip`
--

LOCK TABLES `vip` WRITE;
/*!40000 ALTER TABLE `vip` DISABLE KEYS */;
INSERT INTO `vip` VALUES (1,1,'folier','E10ADC3949BA59ABBE56E057F20F883E','子秋','男','aaa@bbbb.cc','39886616','13888888888','12345678','这是一个示例,演示了将用户自定义的数据表添加到JDiy平台中,通过配置视图和后台管理菜单,由JDiy动态地生成管理页面 .','2011-12-10 23:34:00'),(2,2,'test','FCEA920F7412B5DA7BE0CF42B8C93759','李四','女','123456','123456','1234567','234567','会员原本应由前台网页中的jsp表单页注册,这儿的手工添加会员仅仅是提供一个演示,演示将用户自定义的数据表添加到JDiy平台中,通过配置视图和菜单,由JDiy动态地生成这样的管理页面.\r\n','2011-12-10 23:36:57');
/*!40000 ALTER TABLE `vip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viptype`
--

DROP TABLE IF EXISTS `viptype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viptype` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viptype`
--

LOCK TABLES `viptype` WRITE;
/*!40000 ALTER TABLE `viptype` DISABLE KEYS */;
INSERT INTO `viptype` VALUES (1,'普通会员',10,'会员类型, 这只是一个示例,可根据实际需要,给数据表增加字段后再在\"系统配置\"中修改此控件.'),(2,'高级会员',20,'会员类型, 这只是一个示例,可根据实际需要,给数据表增加字段后再在\"系统配置\"中修改此控件.'),(3,'钻石会员',30,'会员类型, 这只是一个示例,可根据实际需要,给数据表增加字段后再在\"系统配置\"中修改此控件.');
/*!40000 ALTER TABLE `viptype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-04-13  9:47:39
