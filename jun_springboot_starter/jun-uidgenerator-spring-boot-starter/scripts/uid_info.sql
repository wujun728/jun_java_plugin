create table uid_info
(
    id          bigint(11) UNSIGNED not null primary key auto_increment comment '主键',
    uid         bigint UNSIGNED     not null default 0 comment 'uid',
    create_time timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)
    DEFAULT CHARSET = utf8 comment 'uid表';
create index idx_uid on uid_info (uid);