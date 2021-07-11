SELECT * FROM spring.test;CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test_name` varchar(45) DEFAULT NULL,
  `test_num` int(11) DEFAULT '0',
  `test_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;