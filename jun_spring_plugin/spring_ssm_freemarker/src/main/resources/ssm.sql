-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.1.20-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 ssm 的数据库结构
CREATE DATABASE IF NOT EXISTS `ssm` /*!40100 DEFAULT CHARACTER SET gbk */;
USE `ssm`;

-- 导出  表 ssm.t_user 结构
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `age` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- 正在导出表  ssm.t_user 的数据：~26 rows (大约)
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `name`, `password`, `age`) VALUES
	(3, '杨志', '111', 11),
	(4, '林冲', '11', 111),
	(5, '王伦', '111', 11),
	(6, '杜迁', '111', 12),
	(7, '宋万', '111', 13),
	(8, '武松', NULL, 25),
	(9, '鲁智深', NULL, 30),
	(10, '阮小二', '555', 30),
	(11, '阮小五', '666', 27),
	(15, '李天一', NULL, 15),
	(18, '孙二娘', '123', 50),
	(19, '菜园子', '666', 55),
	(20, '扈三娘', '777', 10),
	(22, '老干妈', '444', 90),
	(23, '武二郎', NULL, 25),
	(24, '镇关西', NULL, 30),
	(25, '明教教主', '222', 50),
	(26, '李世民', NULL, 30),
	(27, '李建成', NULL, 40),
	(28, '李元吉', '222', 45),
	(29, '李渊', '222', 60),
	(30, '李元霸', NULL, 15),
	(33, '宇文成都', NULL, 30),
	(34, '裴元庆', NULL, 13),
	(35, '雄阔海', NULL, 45),
	(36, '吴天喜', NULL, 25);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
