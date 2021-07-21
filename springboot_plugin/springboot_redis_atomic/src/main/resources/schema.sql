
CREATE SCHEMA `spring_boot_ci_demo` ;

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键ID',
  `login_name` varchar(32) NOT NULL COMMENT '登录账号',
  `email` varchar(50) NOT NULL COMMENT '注册邮箱地址',
  `delete_enum` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0：未删除，1已删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `lock_version` bigint(20) DEFAULT '0' COMMENT 'JPA乐观锁标识字段，必须有值',
  PRIMARY KEY (`id`)
)  ;

