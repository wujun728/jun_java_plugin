-- 数据表1
CREATE TABLE `user` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`username` varchar(50)  NOT NULL  COMMENT '用户姓名',
`account` varchar(50)  NOT NULL  COMMENT '用户账号',
`password` varchar(50)  NOT NULL  COMMENT '用户密码',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 数据表2
CREATE TABLE `plan` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`user_id` int(11)  NOT NULL  COMMENT '用户编号',
`plan_name` varchar(50)  COMMENT '计划名称',
`plan` text  COMMENT '计划安排 JSON',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='阅读计划';

-- 数据表3
CREATE TABLE `book_list` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`user_id` int(11)  NOT NULL  COMMENT '用户编号',
`book_id` int(11)  NOT NULL  COMMENT '图书编号',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='我的书单';

-- 数据表4
CREATE TABLE `book_history` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`book_id` int(11)  NOT NULL  COMMENT '图书编号',
`user_id` int(11)  NOT NULL  COMMENT '用户编号',
`keyword` text  NOT NULL  COMMENT '图书KeyWord  JSON',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='阅读历史';

-- 数据表5
CREATE TABLE `book` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`title` varchar(100)  NOT NULL  COMMENT '图书名称',
`info` text  COMMENT '图书描述内容',
`cover` varchar(200)  COMMENT '图书封面',
`kinds` text  COMMENT '图书分类 JSON',
`origin_author` text  COMMENT '图书作者 JSON',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='图书库';

-- 数据表6
CREATE TABLE `keyword_model` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`book_id` int(11)  NOT NULL  COMMENT '图书编号',
`key_word` 保存  COMMENT '关键字',
`score` double  COMMENT '范围值',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='关键字模型';

-- 数据表7
CREATE TABLE `user_keyword` (
`id` int(11)  NOT NULL  AUTO_INCREMENT  COMMENT 'id',
`user_id` int(11)  NOT NULL  COMMENT '用户编号',
`key_word` varchar(50)  COMMENT '关键字',
`count` int(11)  DEFAULT 1 COMMENT '出现次数',
`create_time` datetime  DEFAULT now() COMMENT '创建时间',
`update_time` datetime  COMMENT '更新时间',
`lose_flag` int(1)  DEFAULT 1 COMMENT '是否弃用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='用户关键字';

