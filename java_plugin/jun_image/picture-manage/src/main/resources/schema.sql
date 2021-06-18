/*CREATE TABLE if not exists `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar (50) NOT NULL COMMENT '文件编号',
  `path` text NOT NULL COMMENT '文件路径',
  `size` varchar(10) NOT NULL COMMENT '文件大小',
  `file_type` varchar(10) NOT NULL COMMENT '文件类型',
  `folder_id` int(11) NOT NULL COMMENT '所属文件夹',

  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` datetime  COMMENT '修改时间',
  PRIMARY KEY (`id`)
) COMMENT='图片表';


CREATE TABLE if not exists `folderEntity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id (1 为root根目录 /)',
  `dir_name` varchar (50) NOT NULL COMMENT '文件名称',
  `path` text NOT NULL COMMENT '文件路径',
  `parent_id` int(11) NOT NULL COMMENT '父文件夹路径 (-1为根目录)',

  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime  COMMENT '修改时间',
  PRIMARY KEY (`id`)
) COMMENT='文件夹表';*/

CREATE TABLE if not exists `conf` (
  `key` varchar(255) NOT NULL  COMMENT '键',
  `value` text DEFAULT NULL  COMMENT '值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime  COMMENT '修改时间',
  PRIMARY KEY (`key`)
) COMMENT='配置表';