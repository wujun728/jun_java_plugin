-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.30 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 ssmbuild 的数据库结构
CREATE DATABASE IF NOT EXISTS `ssmbuild` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ssmbuild`;

-- 导出  表 ssmbuild.books 结构
CREATE TABLE IF NOT EXISTS `books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(100) DEFAULT NULL,
  `book_counts` int(11) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- 正在导出表  ssmbuild.books 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
REPLACE INTO `books` (`book_id`, `book_name`, `book_counts`, `detail`) VALUES
	(1, 'classg', 77, '我讲真，你是我的大佬，萨瓦迪卡'),
	(2, '李白', 19, '我王者第一名'),
	(3, '张三', 3, '我是张三我怕谁'),
	(4, '李四', 88, '我是李四，是张三的哥哥，比他厉害'),
	(7, '马月光', 211, '辣的房间撒恢复了开始带饭');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- 导出  表 ssmbuild.category 结构
CREATE TABLE IF NOT EXISTS `category` (
  `id` varchar(30) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '类型名',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除（1删除，0未删除）默认值为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- 正在导出表  ssmbuild.category 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- 导出  表 ssmbuild.category_copy1 结构
CREATE TABLE IF NOT EXISTS `category_copy1` (
  `id` varchar(30) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '类型名',
  `create_by` varchar(40) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(40) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除（1删除，0未删除）默认值为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- 正在导出表  ssmbuild.category_copy1 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `category_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_copy1` ENABLE KEYS */;

-- 导出  表 ssmbuild.goods 结构
CREATE TABLE IF NOT EXISTS `goods` (
  `id` varchar(30) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `category_id` varchar(30) DEFAULT NULL COMMENT '所属类型',
  `price` int(255) NOT NULL DEFAULT '0' COMMENT '价格',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `is_del` int(1) DEFAULT '0' COMMENT '逻辑删除（0未删除，1删除）默认0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- 正在导出表  ssmbuild.goods 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;

-- 导出  表 ssmbuild.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `username` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `sex` int(1) DEFAULT '1' COMMENT '性别（1：男，2：女）默认男',
  `age` int(255) DEFAULT NULL COMMENT '年龄',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `photo` varchar(255) DEFAULT NULL COMMENT '图像',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除（1删除，0未删除）默认值为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- 正在导出表  ssmbuild.user 的数据：~17 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `name`, `username`, `password`, `sex`, `age`, `email`, `photo`, `phone`, `create_by`, `create_date`, `update_by`, `update_date`, `is_del`) VALUES
	('0692fd436a54fa6e1e5b64c52a5c80b7', '元哥', 'yuange', 'zxcvasdf', 1, 19, 'yuange@163.com', 'yuange', '18273293821', NULL, '2020-08-20 01:29:51', NULL, '2020-08-20 01:29:51', 0),
	('20ac700b15e227506fa330d72b9b0e96', '马超', 'machao', '123qwe', 1, 30, 'machao@163.com', 'machao', '19925929321', NULL, '2020-08-19 10:29:53', NULL, '2020-08-19 10:29:53', 0),
	('232341324', '才1捷', 'classg', '123123', 2, 18, 'class@163.com', '199234324', '123342', 'class', '2020-08-03 11:37:11', 'class', '2020-08-17 11:37:15', 0),
	('23234324', '才捷', 'classg', '123123', 1, 18, 'class@163.com', '199234324', '123342', 'class', '2020-08-03 11:37:11', 'class', '2020-08-17 11:37:15', 0),
	('23234as324', '马哥', 'classg', '123123', 1, 18, 'class@163.com', '199234324', '123342', 'class', '2020-08-03 11:37:11', 'class', '2020-08-17 11:37:15', 0),
	('23234d324', '礼拜', 'classg', '123123', 2, 18, 'class@163.com', '199234324', '123342', 'class', '2020-08-03 11:37:11', 'class', '2020-08-17 11:37:15', 0),
	('26847f03ae6d988b853f34761960705b', '码农', 'manong', NULL, 1, NULL, NULL, NULL, NULL, NULL, '2020-08-19 16:31:29', NULL, '2020-08-19 16:31:27', 0),
	('42e04f7dd14668295f2bcf103a94647e', '赵云', 'zhaoyun', '123123', 1, 19, 'class@gmail.com', '123', '19201291235', NULL, '2020-08-15 10:01:27', NULL, '2020-08-20 02:12:49', 0),
	('57d4063a9c200f915398a955e85e44ec', '张三', 'zhangsan', '23423', 1, 35, 'zhangsan@qq.com', 'zhangsan', '19281928121', NULL, '2020-08-05 09:46:17', NULL, '2020-08-20 02:14:42', 0),
	('66db69ff473ea6ded96d34417fefacca', '妲己', 'daji', 'daji123', 2, 22, 'daji@gmail.com', 'daji', '19381392381', NULL, '2020-08-20 02:14:05', NULL, '2020-08-20 02:14:05', 0),
	('97815a35cfa09028b38f2cffc47a9b36', '李大鹏', 'lidapeng', '123321', 1, 14, 'class@126.com', 'abc', '123123123', NULL, '2020-08-19 08:53:00', NULL, '2020-08-19 08:53:00', 0),
	('asferagagsa2342', '啦啦啦', 'adfa', '23131', 1, NULL, NULL, NULL, NULL, NULL, '2020-08-12 09:45:54', NULL, '2020-08-11 09:45:57', 0),
	('asferasgagsa2342', '小弟', 'adfa', '23131', 1, NULL, NULL, NULL, NULL, NULL, '2020-08-11 16:00:00', NULL, '2020-08-10 16:00:00', 0),
	('asferga1gsa2342', '可爱', 'adfa', '23131', 2, NULL, NULL, NULL, NULL, NULL, '2020-08-11 16:00:00', NULL, '2020-08-10 16:00:00', 0),
	('asfergagsa2342', '马冬梅', 'mdm', 'madongmei123', 2, 49, 'madongmei@gmail.com', 'madongmei', '123123', NULL, '2020-08-11 16:00:00', NULL, '2020-08-20 02:36:42', 0),
	('d2684259373888ecbe776c57b38d6a6f', '小乔', 'xiaoqiao', '12qwe', 2, 22, 'xiaoqiao@126.com', '1', '19284721241', NULL, '2020-08-20 01:28:15', NULL, '2020-08-20 01:28:15', 0),
	('efsf324242sdf', '历史', NULL, '23423', 2, NULL, NULL, NULL, NULL, NULL, '2020-08-04 16:00:00', NULL, '2020-08-17 16:00:00', 0),
	('efsf34242sdf', '李四', NULL, '23423', 1, NULL, NULL, NULL, NULL, NULL, '2020-08-04 16:00:00', NULL, '2020-08-17 16:00:00', 0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
