-- 数据库引擎为InnoDB，只有这样才能支持事务 
create table rpt_mail_address_t
(
   id                   varchar(19) not null,
   mail_type            varchar(20) comment '邮件类型:见com.xjh.report.common.constants.MailAddressEnum.java类',
   to_address           varchar(1000) comment '收件人',
   to_cc                varchar(1000) comment '抄送',
   to_bcc               varchar(1000) comment '秘送',
   is_del               integer not null default 0 comment '删除标识(1、已删除,0、未删除)',
   version_num          bigint not null comment '版本号',
   create_time          timestamp not null comment '创建时间',
   update_time          timestamp comment '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table rpt_mail_address_t comment '收件箱配置表';
alter table rpt_mail_address_t add primary key (id);