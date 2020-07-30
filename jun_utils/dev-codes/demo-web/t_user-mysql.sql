use stu;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `state` tinyint(4) DEFAULT NULL COMMENT '状态',
  `isdel` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `remark` text COMMENT '备注',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `money` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `left_money` float DEFAULT NULL COMMENT '剩下的钱',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户表';



INSERT  INTO `t_user`(`id`,`username`,`state`,`isdel`,`remark`,`add_time`,`money`,`left_money`) VALUES (1,'王五',0,0,'批量修改备注','2017-02-21 10:37:44','101.10',22.1),(2,'张三',0,0,'批量修改备注','2017-02-21 10:40:11','100.50',22.1),(3,'张三',1,0,'备注','2017-02-21 10:40:11','100.50',22.1),(4,'张三',0,0,'批量修改备注','2017-02-21 10:40:11','100.50',22.1),(5,'张三',0,0,'批量修改备注','2017-02-21 10:40:11','100.50',22.1),(6,'张三',0,0,'批量修改备注','2017-02-21 10:40:11','100.50',22.1);

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT 't_user外键',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `address` varchar(100) DEFAULT NULL COMMENT '街道',
  `status` varchar(4) DEFAULT '0' COMMENT '类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

INSERT INTO `stu`.`user_info` (

  `user_id`,
  `city`,
  `address`,
  `create_time`
) 
SELECT 
t.id
,'杭州'
,'延安路'
,NOW()
FROM t_user t;


DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` varchar(100) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert  into `address`(`id`,`address`) values ('1','aaaa'),('2','bbbb'),('3','vvv'),('4','3333'),('50830c82-8ede-11e7-8bd2-54e1ad3fb014','address。。');

