-- MySQL dump 10.13  Distrib 5.6.22, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: redis-monitor
-- ------------------------------------------------------
-- Server version	5.6.24-enterprise-commercial-advanced

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
-- Table structure for table `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `REDIS_MONITOR_job_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `REDIS_MONITOR_QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDIS_MONITOR_JOB_QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_CALENDARS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `REDIS_MONITOR_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `REDIS_MONITOR_QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_QRTZ_CRON_TRIGGERS` VALUES ('quartzScheduler','RedisServer监控','4','0 0/10 * * * ?','Asia/Shanghai'),('quartzScheduler','RedisServer集群','4','0 0/14 * * * ?','Asia/Shanghai'),('quartzScheduler','Redis监控平台jvm监控','4','0 0/15 * * * ?','Asia/Shanghai');
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_JOB_DETAILS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_JOB_DETAILS` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_QRTZ_JOB_DETAILS` VALUES ('quartzScheduler','RedisServer监控','4',NULL,'com.opensource.nredis.proxy.monitor.quartz.utils.ConcurrentExecutionJobQuartz','0','0','0','0','??\0sr\0org.quartz.JobDataMap???迩??\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap?????](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap?.?(v\n?\0Z\0dirtyL\0mapt\0Ljava/util/Map;xp\0sr\0java.util.HashMap???`?\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0\0x\0'),('quartzScheduler','RedisServer集群','4',NULL,'com.opensource.nredis.proxy.monitor.quartz.utils.ConcurrentExecutionJobQuartz','0','0','0','0','??\0sr\0org.quartz.JobDataMap???迩??\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap?????](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap?.?(v\n?\0Z\0dirtyL\0mapt\0Ljava/util/Map;xp\0sr\0java.util.HashMap???`?\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0\0x\0'),('quartzScheduler','Redis监控平台jvm监控','4',NULL,'com.opensource.nredis.proxy.monitor.quartz.utils.ConcurrentExecutionJobQuartz','0','0','0','0','??\0sr\0org.quartz.JobDataMap???迩??\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap?????](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap?.?(v\n?\0Z\0dirtyL\0mapt\0Ljava/util/Map;xp\0sr\0java.util.HashMap???`?\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0\0x\0');
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_LOCKS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_LOCKS` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_QRTZ_LOCKS` VALUES ('quartzScheduler','STATE_ACCESS'),('quartzScheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_QRTZ_SCHEDULER_STATE` VALUES ('quartzScheduler','liubingdeMacBook-Pro.local1484469476994',1484478375015,1000);
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `REDIS_MONITOR_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `REDIS_MONITOR_QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `REDIS_MONITOR_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `REDIS_MONITOR_QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `REDIS_MONITOR_qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `REDIS_MONITOR_QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_QRTZ_TRIGGERS`
--

LOCK TABLES `REDIS_MONITOR_QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_TRIGGERS` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_QRTZ_TRIGGERS` VALUES ('quartzScheduler','RedisServer监控','4','RedisServer监控','4',NULL,1484478600000,1484478000000,5,'WAITING','CRON',1484290755000,0,NULL,2,''),('quartzScheduler','RedisServer集群','4','RedisServer集群','4',NULL,1484478840000,1484478000000,5,'WAITING','CRON',1484297202000,0,NULL,2,''),('quartzScheduler','Redis监控平台jvm监控','4','Redis监控平台jvm监控','4',NULL,1484478900000,1484478000000,5,'WAITING','CRON',1484469391000,0,NULL,2,'');
/*!40000 ALTER TABLE `REDIS_MONITOR_QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_qrtz_trigger_info`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_qrtz_trigger_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_qrtz_trigger_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(2) NOT NULL COMMENT 'ä»»åŠ¡ç»„',
  `job_name` varchar(255) NOT NULL COMMENT 'ä»»åŠ¡å',
  `job_cron` varchar(128) NOT NULL COMMENT 'ä»»åŠ¡æ‰§è¡ŒCORN',
  `job_desc` varchar(1000) NOT NULL,
  `job_class` varchar(255) NOT NULL COMMENT 'ä»»åŠ¡æ‰§è¡ŒJobBean',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT 'ä½œè€…',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT 'æŠ¥è­¦é‚®ä»¶',
  `alarm_threshold` int(11) DEFAULT NULL COMMENT 'æŠ¥è­¦é˜€å€¼(è¿žç»­å¤±è´¥æ¬¡æ•°)',
  `create_username` varchar(300) NOT NULL COMMENT 'åˆ›å»ºäºº',
  `class_name` varchar(300) NOT NULL COMMENT 'ç±»å',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_qrtz_trigger_info`
--

LOCK TABLES `REDIS_MONITOR_qrtz_trigger_info` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_qrtz_trigger_info` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_qrtz_trigger_info` VALUES (3,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群','2','2017-01-13 14:20:06','2017-01-13 16:46:42','liubing','liubing@sina.com',5,'liubing','zookeeperQuartz'),(4,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态','2','2017-01-13 14:59:16','2017-01-13 14:59:16','liubing','44@sina.com',5,'liubing','redisProxyQuartzImpl'),(5,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控','2','2017-01-15 16:36:32','2017-01-15 16:36:32','liubing','liubing44@sina.com',5,'liubing','systemApplicationQuartz');
/*!40000 ALTER TABLE `REDIS_MONITOR_qrtz_trigger_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDIS_MONITOR_qrtz_trigger_log`
--

DROP TABLE IF EXISTS `REDIS_MONITOR_qrtz_trigger_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDIS_MONITOR_qrtz_trigger_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(2) NOT NULL COMMENT 'ä»»åŠ¡ç»„',
  `job_name` varchar(255) NOT NULL COMMENT 'ä»»åŠ¡å',
  `job_cron` varchar(128) NOT NULL COMMENT 'ä»»åŠ¡æ‰§è¡ŒCORNè¡¨è¾¾å¼',
  `job_desc` varchar(255) NOT NULL,
  `job_class` int(2) NOT NULL COMMENT 'ä»»åŠ¡æ‰§è¡ŒJobBean',
  `job_data` varchar(512) DEFAULT NULL COMMENT 'ä»»åŠ¡æ‰§è¡Œæ•°æ®',
  `trigger_time` datetime DEFAULT NULL COMMENT 'è°ƒåº¦-æ—¶é—´',
  `trigger_status` varchar(255) DEFAULT NULL COMMENT 'è°ƒåº¦-ç»“æžœ',
  `trigger_msg` varchar(2048) DEFAULT NULL COMMENT 'è°ƒåº¦-æ—¥å¿—',
  `handle_time` datetime DEFAULT NULL COMMENT 'æ‰§è¡Œ-æ—¶é—´',
  `handle_status` varchar(255) DEFAULT NULL COMMENT 'æ‰§è¡Œ-çŠ¶æ€',
  `handle_msg` varchar(2048) DEFAULT NULL COMMENT 'æ‰§è¡Œ-æ—¥å¿—',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDIS_MONITOR_qrtz_trigger_log`
--

LOCK TABLES `REDIS_MONITOR_qrtz_trigger_log` WRITE;
/*!40000 ALTER TABLE `REDIS_MONITOR_qrtz_trigger_log` DISABLE KEYS */;
INSERT INTO `REDIS_MONITOR_qrtz_trigger_log` VALUES (116,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-13 23:22:46','成功','调用成功','2017-01-13 23:22:46','调度成功','调用成功'),(117,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-13 23:26:06','成功','调用成功','2017-01-13 23:26:06','调度成功','调用成功'),(118,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-13 23:30:52','成功','执行成功','2017-01-13 23:30:52','调度成功','执行成功'),(119,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-13 23:30:55','成功','调用成功','2017-01-13 23:30:55','调度成功','调用成功'),(120,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-13 23:37:05','成功','调用成功','2017-01-13 23:37:05','调度成功','调用成功'),(121,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-13 23:40:00','成功','执行成功','2017-01-13 23:40:00','调度成功','执行成功'),(122,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-13 23:42:00','成功','调用成功','2017-01-13 23:42:00','调度成功','调用成功'),(123,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-13 23:50:00','成功','执行成功','2017-01-13 23:50:00','调度成功','执行成功'),(124,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 15:40:00','成功','执行成功','2017-01-14 15:40:00','调度成功','执行成功'),(125,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 16:10:00','成功','执行成功','2017-01-14 16:10:00','调度成功','执行成功'),(126,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 16:14:00','成功','调用成功','2017-01-14 16:14:00','调度成功','调用成功'),(127,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 16:20:00','成功','执行成功','2017-01-14 16:20:00','调度成功','执行成功'),(128,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 20:10:00','成功','执行成功','2017-01-14 20:10:00','调度成功','执行成功'),(129,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 20:14:07','成功','调用成功','2017-01-14 20:14:07','调度成功','调用成功'),(130,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 20:20:00','成功','执行成功','2017-01-14 20:20:00','调度成功','执行成功'),(131,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 20:40:00','成功','执行成功','2017-01-14 20:40:00','调度成功','执行成功'),(132,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 20:42:28','成功','调用成功','2017-01-14 20:42:28','调度成功','调用成功'),(133,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 20:50:20','成功','执行成功','2017-01-14 20:50:20','调度成功','执行成功'),(134,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 20:56:00','成功','调用成功','2017-01-14 20:56:00','调度成功','调用成功'),(135,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 21:00:14','成功','执行成功','2017-01-14 21:00:14','调度成功','执行成功'),(136,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 21:00:14','成功','调用成功','2017-01-14 21:00:14','调度成功','调用成功'),(137,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 21:10:00','成功','执行成功','2017-01-14 21:10:00','调度成功','执行成功'),(138,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 21:14:00','成功','调用成功','2017-01-14 21:14:00','调度成功','调用成功'),(139,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 21:20:00','成功','执行成功','2017-01-14 21:20:00','调度成功','执行成功'),(140,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 21:28:00','成功','调用成功','2017-01-14 21:28:00','调度成功','调用成功'),(141,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-14 21:30:00','成功','执行成功','2017-01-14 21:30:00','调度成功','执行成功'),(142,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-14 22:14:00','成功','调用成功','2017-01-14 22:14:00','调度成功','调用成功'),(143,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 15:50:12','成功','执行成功','2017-01-15 15:50:12','调度成功','执行成功'),(144,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 15:56:54','成功','调用成功','2017-01-15 15:56:54','调度成功','调用成功'),(145,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 16:28:24','成功','调用成功','2017-01-15 16:28:24','调度成功','调用成功'),(146,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 16:40:00','成功','执行成功','2017-01-15 16:40:00','调度成功','执行成功'),(147,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 16:42:00','成功','调用成功','2017-01-15 16:42:00','调度成功','调用成功'),(148,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控',2,' ','2017-01-15 16:45:00','成功','执行成功','2017-01-15 16:45:00','调度成功','执行成功'),(149,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 16:50:00','成功','执行成功','2017-01-15 16:50:00','调度成功','执行成功'),(150,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 16:56:00','成功','调用成功','2017-01-15 16:56:00','调度成功','调用成功'),(151,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 17:00:00','成功','调用成功','2017-01-15 17:00:00','调度成功','调用成功'),(152,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 17:00:00','成功','执行成功','2017-01-15 17:00:00','调度成功','执行成功'),(153,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控',2,' ','2017-01-15 17:00:00','成功','执行成功','2017-01-15 17:00:00','调度成功','执行成功'),(154,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 17:10:00','成功','执行成功','2017-01-15 17:10:00','调度成功','执行成功'),(155,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 17:14:00','成功','调用成功','2017-01-15 17:14:00','调度成功','调用成功'),(156,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控',2,' ','2017-01-15 17:15:00','成功','执行成功','2017-01-15 17:15:00','调度成功','执行成功'),(157,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 17:20:00','成功','执行成功','2017-01-15 17:20:00','调度成功','执行成功'),(158,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 17:28:00','成功','调用成功','2017-01-15 17:28:00','调度成功','调用成功'),(159,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 17:30:00','成功','执行成功','2017-01-15 17:30:00','调度成功','执行成功'),(160,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控',2,' ','2017-01-15 17:30:00','成功','执行成功','2017-01-15 17:30:00','调度成功','执行成功'),(161,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 18:40:00','成功','执行成功','2017-01-15 18:40:00','调度成功','执行成功'),(162,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 18:42:00','成功','调用成功','2017-01-15 18:42:00','调度成功','调用成功'),(163,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控',2,' ','2017-01-15 18:45:00','成功','执行成功','2017-01-15 18:45:00','调度成功','执行成功'),(164,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 18:50:00','成功','执行成功','2017-01-15 18:50:00','调度成功','执行成功'),(165,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 18:56:00','成功','调用成功','2017-01-15 18:56:00','调度成功','调用成功'),(166,4,'RedisServer监控','0 0/10 * * * ?','每隔5分钟监控一次server状态',2,' ','2017-01-15 19:00:00','成功','执行成功','2017-01-15 19:00:00','调度成功','执行成功'),(167,4,'RedisServer集群','0 0/14 * * * ?','每隔10分钟从zookeeper获取RedisServer集群',2,' ','2017-01-15 19:00:00','成功','调用成功','2017-01-15 19:00:00','调度成功','调用成功'),(168,4,'Redis监控平台jvm监控','0 0/15 * * * ?','Redis监控平台 cpu、jvm、线程、tomcat监控',2,' ','2017-01-15 19:00:00','成功','执行成功','2017-01-15 19:00:00','调度成功','执行成功');
/*!40000 ALTER TABLE `REDIS_MONITOR_qrtz_trigger_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ps_dict`
--

DROP TABLE IF EXISTS `ps_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ps_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ps_key` varchar(300) NOT NULL COMMENT 'å­—æ®µåç§°',
  `ps_value` varchar(300) NOT NULL COMMENT 'å­—æ®µå€¼',
  `ps_moudle` varchar(300) NOT NULL COMMENT 'æ¨¡å—',
  `ps_status` int(2) NOT NULL COMMENT 'çŠ¶æ€\n1ï¼šè‰ç¨¿\n2ï¼šå¯ç”¨\n3ï¼šåœç”¨',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ps_dict`
--

LOCK TABLES `ps_dict` WRITE;
/*!40000 ALTER TABLE `ps_dict` DISABLE KEYS */;
INSERT INTO `ps_dict` VALUES (1,'是','01','YESNO',2),(2,'否','02','YESNO',2),(3,'HTTP','1','URL',2),(4,'HTTPS','2','URL',2),(5,'TCP','3','URL',2),(6,'草稿','1','STATUS',2),(7,'启用','2','STATUS',2),(8,'停用','3','STATUS',2),(9,'GET','1','HTTP',2),(10,'POST','2','HTTP',2),(11,'PUT','3','HTTP',2),(12,'DELETE','4','HTTP',2),(13,'TRACE','5','HTTP',2),(14,'开发','1','TESTENV',2),(15,'集成','2','TESTENV',2),(16,'生产','3','TESTENV',2),(17,'无参数无依赖','1','HTTPPARAMTYPE',2),(18,'有参数无依赖','2','HTTPPARAMTYPE',2),(19,'有参数有依赖','3','HTTPPARAMTYPE',2),(20,'无参数有依赖','4','HTTPPARAMTYPE',2),(21,'携带','1','COOKIETYPE',2),(22,'不携带','2','COOKIETYPE',2),(23,'并行','1','OPERATE',2),(24,'串行','2','OPERATE',2),(25,'结算','1','SYS_GROUP',2),(26,'对账','2','SYS_GROUP',2),(27,'支付','3','SYS_GROUP',2),(28,'本地','1','OPRATE_TYPE',2),(29,'远程','2','OPRATE_TYPE',2),(30,'成功','1','FINAL_STATUS',2),(31,'失败','2','FINAL_STATUS',2),(32,'JMX','1','MONITOR_MODULE',2),(33,'老年代','2','MONITOR_MODULE',2),(34,'持久代','3','MONITOR_MODULE',2),(35,'CPU','4','MONITOR_MODULE',2),(36,'TOMCAT','5','MONITOR_MODULE',2),(37,'QUARTZ','6','MONITOR_MODULE',2),(38,'THREAD','7','MONITOR_MODULE',2),(39,'Redis监控平台','4','SYS_GROUP',2),(40,'运行','1','RUNNING_STATUS',2),(41,'停止','2','RUNNING_STATUS',2);
/*!40000 ALTER TABLE `ps_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ps_menu`
--

DROP TABLE IF EXISTS `ps_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ps_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ä¸»é”®',
  `title` varchar(300) NOT NULL COMMENT 'èœå•åç§°',
  `icon` varchar(300) NOT NULL COMMENT 'å›¾æ ‡',
  `href` varchar(300) NOT NULL COMMENT 'è¶…çº§é“¾æŽ¥',
  `parent_id` int(11) NOT NULL COMMENT 'çˆ¶èŠ‚ç‚¹',
  `is_leaf` char(2) NOT NULL COMMENT '01: æ˜¯ï¼›02ï¼šå¦',
  `spread` int(2) NOT NULL COMMENT 'true|false',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ps_menu`
--

LOCK TABLES `ps_menu` WRITE;
/*!40000 ALTER TABLE `ps_menu` DISABLE KEYS */;
INSERT INTO `ps_menu` VALUES (2,'系统管理','fa-cubes',' ',0,'02',2),(4,'用户管理','&#xe63c;','../PsUser/PsUser.html',2,'1',2),(5,'菜单管理','&#xe63c;','../PsMenu/PsMenu.html',2,'1',2),(9,'词典管理','&#xe63c;','../dict/dict.html',2,'01',2),(16,'调度管理','fa-cogs',' ',0,'02',2),(17,'分布式调度','fa-check-square-o',' ../AutoPageSystemQrtzTriggerInfo/AutoPageSystemQrtzTriggerInfo.html',16,'01',2),(18,'调度日志','fa-check-square-o',' ../AutoPageSystemQrtzTriggerLog/AutoPageSystemQrtzTriggerLog.html',16,'01',2),(19,'系统监控管理','fa-cogs',' ',0,'02',2),(20,'系统应用管理','fa-check-square-o',' ../SystemApplication/SystemApplication.html',19,'01',2),(23,'系统组件管理','fa-check-square-o','../SystemComponent/SystemComponent.html',19,'01',2),(24,'监控日志管理','fa-check-square-o','../SystemApplicationMonitor/SystemApplicationMonitor.html',19,'01',2),(25,'Redis监控管理','fa-cogs',' ',0,'02',1),(26,'MasterServer列表管理','fa-check-square-o',' ../RedisClusterMaster/RedisClusterMaster.html',25,'01',2),(27,'SlaveServer列表管理','fa-check-square-o',' ../RedisClusterSlave/RedisClusterSlave.html',25,'01',2),(28,'RedisServer监控日志','fa-check-square-o',' ../RedisClusterMonitorLog/RedisClusterMonitorLog.html',25,'01',2);
/*!40000 ALTER TABLE `ps_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ps_user`
--

DROP TABLE IF EXISTS `ps_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ps_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(300) NOT NULL COMMENT 'ç”¨æˆ·åç§°',
  `password` varchar(300) NOT NULL COMMENT 'ç™»å½•å¯†ç ',
  `user_email` varchar(300) NOT NULL COMMENT 'ç”¨æˆ·é‚®ç®±',
  `create_time` datetime NOT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  `create_user` varchar(300) NOT NULL COMMENT 'åˆ›å»ºäºº',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ps_user`
--

LOCK TABLES `ps_user` WRITE;
/*!40000 ALTER TABLE `ps_user` DISABLE KEYS */;
INSERT INTO `ps_user` VALUES (1,'liubing','liubing','lb18911768832@sina.com','2016-12-13 09:00:00','liubing'),(13,'root','root','root@sina.com','2016-12-22 13:40:06','liubing'),(14,'admin','admin','adin@sina.com','2016-12-22 17:59:55','root');
/*!40000 ALTER TABLE `ps_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ps_user_menu`
--

DROP TABLE IF EXISTS `ps_user_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ps_user_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ps_user_menu`
--

LOCK TABLES `ps_user_menu` WRITE;
/*!40000 ALTER TABLE `ps_user_menu` DISABLE KEYS */;
INSERT INTO `ps_user_menu` VALUES (1,1,2),(2,1,4),(5,1,7),(6,1,8),(8,13,4),(9,13,2),(12,13,7),(13,13,8),(31,1,6),(32,13,5),(33,1,5),(34,1,9),(38,13,13),(39,1,13),(40,13,14),(41,1,14),(42,13,15),(43,1,15),(44,14,18),(45,13,18),(46,1,18),(47,14,17),(48,13,17),(49,1,17),(50,14,16),(51,13,16),(52,1,16),(53,13,19),(54,1,19),(55,13,20),(56,1,20),(57,13,21),(58,1,21),(59,13,22),(60,1,22),(61,13,23),(62,1,23),(63,13,24),(64,1,24),(65,13,25),(66,1,25),(67,13,26),(68,1,26),(69,13,27),(70,1,27),(71,13,28),(72,1,28);
/*!40000 ALTER TABLE `ps_user_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `redis_cluster_master`
--

DROP TABLE IF EXISTS `redis_cluster_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `redis_cluster_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `redis_server_host` varchar(100) NOT NULL COMMENT 'RedisServer主机',
  `redis_server_port` int(11) NOT NULL,
  `runner_status` int(2) NOT NULL COMMENT '运行状态\n1：运行\n2：停止',
  `redis_server_status` int(2) NOT NULL COMMENT 'redis 状态\n1：草稿\n2：启用\n3：停用',
  `create_time` datetime NOT NULL,
  `version` int(11) NOT NULL COMMENT '版本号',
  `oprate_status` int(2) NOT NULL,
  `path` varchar(300) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `HOST_INDEX` (`redis_server_host`,`redis_server_port`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redis_cluster_master`
--

LOCK TABLES `redis_cluster_master` WRITE;
/*!40000 ALTER TABLE `redis_cluster_master` DISABLE KEYS */;
INSERT INTO `redis_cluster_master` VALUES (39,'127.0.0.1',6380,1,2,'2017-01-13 23:37:05',47,1,'/nredis-proxy/command/server/127.0.0.1:6380');
/*!40000 ALTER TABLE `redis_cluster_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `redis_cluster_monitor_log`
--

DROP TABLE IF EXISTS `redis_cluster_monitor_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `redis_cluster_monitor_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `redis_server_host` varchar(300) NOT NULL COMMENT '主服务器',
  `redis_server_port` int(11) NOT NULL COMMENT '端口号',
  `remark` varchar(300) NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redis_cluster_monitor_log`
--

LOCK TABLES `redis_cluster_monitor_log` WRITE;
/*!40000 ALTER TABLE `redis_cluster_monitor_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `redis_cluster_monitor_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `redis_cluster_slave`
--

DROP TABLE IF EXISTS `redis_cluster_slave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `redis_cluster_slave` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `redis_server_host` varchar(100) NOT NULL COMMENT 'RedisServer主机',
  `redis_server_port` int(11) NOT NULL,
  `runner_status` int(2) NOT NULL COMMENT '运行状态\n1：运行\n2：停止',
  `redis_server_status` int(2) NOT NULL COMMENT 'redis 状态\n1：草稿\n2：启用\n3：草稿',
  `create_time` datetime NOT NULL,
  `redis_master_id` int(11) NOT NULL COMMENT '主服务Id',
  `oprate_status` int(2) NOT NULL,
  `version` int(11) NOT NULL,
  `weight` int(2) NOT NULL COMMENT '权重',
  `path` varchar(300) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `HOST_INDEX` (`redis_server_host`,`redis_server_port`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redis_cluster_slave`
--

LOCK TABLES `redis_cluster_slave` WRITE;
/*!40000 ALTER TABLE `redis_cluster_slave` DISABLE KEYS */;
INSERT INTO `redis_cluster_slave` VALUES (24,'127.0.0.1',6381,1,2,'2017-01-13 23:37:05',39,1,47,1,'/nredis-proxy/command/server/127.0.0.1:6380/127.0.0.1:6381');
/*!40000 ALTER TABLE `redis_cluster_slave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_application`
--

DROP TABLE IF EXISTS `system_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_name` varchar(300) NOT NULL COMMENT '应用名称',
  `application_host` varchar(100) NOT NULL COMMENT '应用主机',
  `application_port` int(11) NOT NULL COMMENT '应用端口号',
  `jmx_host` varchar(100) NOT NULL COMMENT 'jmx主机',
  `jmx_port` int(11) NOT NULL COMMENT 'jmx端口号',
  `jmx_user_name` varchar(300) NOT NULL COMMENT 'jmx用户名',
  `jmx_pass_word` varchar(300) NOT NULL COMMENT 'jmx密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user_name` varchar(300) NOT NULL COMMENT '创建用户',
  `version` int(11) NOT NULL COMMENT '版本号，乐观锁',
  `status` int(1) NOT NULL COMMENT '状态\n1：未执行\n2：已执行',
  `jmx_status` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `application_unique` (`application_host`,`application_port`),
  UNIQUE KEY `jmx_unique` (`jmx_host`,`jmx_port`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_application`
--

LOCK TABLES `system_application` WRITE;
/*!40000 ALTER TABLE `system_application` DISABLE KEYS */;
INSERT INTO `system_application` VALUES (1,'redis-monitor-api','127.0.0.1',8080,'127.0.0.1',8999,' ',' ','2016-12-31 08:50:41','liubing',123,1,1),(3,'nredis-proxy','127.0.0.1',0,'127.0.0.1',8998,' ',' ','2017-01-15 15:57:04','liubing',12,1,1);
/*!40000 ALTER TABLE `system_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_application_component`
--

DROP TABLE IF EXISTS `system_application_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_application_component` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `component_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_application_component`
--

LOCK TABLES `system_application_component` WRITE;
/*!40000 ALTER TABLE `system_application_component` DISABLE KEYS */;
INSERT INTO `system_application_component` VALUES (10,1,3),(11,1,2);
/*!40000 ALTER TABLE `system_application_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_application_monitor`
--

DROP TABLE IF EXISTS `system_application_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_application_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monitor_moudle` int(2) NOT NULL COMMENT '监控模块',
  `monitor_application_name` varchar(100) NOT NULL COMMENT '监控应用名称',
  `monitor_status` int(2) NOT NULL COMMENT '1：成功\n2：失败',
  `monitor_host` varchar(50) NOT NULL COMMENT '监控主机',
  `monitor_port` int(2) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_application_monitor`
--

LOCK TABLES `system_application_monitor` WRITE;
/*!40000 ALTER TABLE `system_application_monitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_application_monitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_component`
--

DROP TABLE IF EXISTS `system_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_component` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `component_key` varchar(300) NOT NULL COMMENT '组件名称',
  `component_value` varchar(300) NOT NULL COMMENT '组件值',
  `key_config` varchar(300) NOT NULL COMMENT '关键配置',
  `create_user_name` varchar(300) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_component`
--

LOCK TABLES `system_component` WRITE;
/*!40000 ALTER TABLE `system_component` DISABLE KEYS */;
INSERT INTO `system_component` VALUES (2,'TOMCAT 线程','1','Catalina:type=ThreadPool,*','liubing','2017-01-02 16:15:54'),(3,'QUARTZ定时器','2','quartz:type=QuartzScheduler,*','liubing','2017-01-02 16:17:26');
/*!40000 ALTER TABLE `system_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'redis-monitor'
--

--
-- Dumping routines for database 'redis-monitor'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-15 19:06:15
