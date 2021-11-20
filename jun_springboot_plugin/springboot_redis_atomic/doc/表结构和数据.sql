
CREATE DATABASE spring_boot_ci_demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键ID',
  `login_name` varchar(32) NOT NULL COMMENT '登录账号',
  `email` varchar(50) NOT NULL COMMENT '注册邮箱地址',
  `delete_enum` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0：未删除，1已删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `lock_version` bigint(20) DEFAULT '0' COMMENT 'JPA乐观锁标识字段，必须有值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

insert  into `sys_user`(`id`,`login_name`,`email`,`delete_enum`,`create_date`,`lock_version`) values ('374933329427959808','admin','judas.n@qq.com','0','2016-06-20 11:49:53',3);
insert  into `sys_user`(`id`,`login_name`,`email`,`delete_enum`,`create_date`,`lock_version`) values ('374933329427959809','judasn','363379444@qq.com','0','2017-01-05 14:59:05',2);
