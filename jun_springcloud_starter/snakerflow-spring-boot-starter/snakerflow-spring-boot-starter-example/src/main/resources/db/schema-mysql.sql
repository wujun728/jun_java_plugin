CREATE TABLE wf_process (
    id                VARCHAR(32) PRIMARY KEY NOT NULL comment '主键ID',
    name              VARCHAR(100) comment '流程名称',
    display_Name      VARCHAR(200) comment '流程显示名称',
    type              VARCHAR(100) comment '流程类型',
    instance_Url      VARCHAR(200) comment '实例url',
    state             TINYINT(1) comment '流程是否可用',
    content           LONGBLOB comment '流程模型定义',
    version           INT(2) comment '版本',
    create_Time       VARCHAR(50) comment '创建时间',
    creator           VARCHAR(50) comment '创建人'
)comment='流程定义表';

CREATE TABLE wf_order (
    id                VARCHAR(32) NOT NULL PRIMARY KEY comment '主键ID',
    parent_Id         VARCHAR(32) comment '父流程ID',
    process_Id        VARCHAR(32) NOT NULL comment '流程定义ID',
    creator           VARCHAR(100) comment '发起人',
    create_Time       VARCHAR(50) NOT NULL comment '发起时间',
    expire_Time       VARCHAR(50) comment '期望完成时间',
    last_Update_Time  VARCHAR(50) comment '上次更新时间',
    last_Updator      VARCHAR(100) comment '上次更新人',
    priority          TINYINT(1) comment '优先级',
    parent_Node_Name  VARCHAR(100) comment '父流程依赖的节点名称',
    order_No          VARCHAR(100) comment '流程实例编号',
    variable          VARCHAR(2000) comment '附属变量json存储',
    version           INT(3) comment '版本'
)comment='流程实例表';

CREATE TABLE wf_task (
    id                VARCHAR(32) NOT NULL PRIMARY KEY comment '主键ID',
    order_Id          VARCHAR(32) NOT NULL comment '流程实例ID',
    task_Name         VARCHAR(100) NOT NULL comment '任务名称',
    display_Name      VARCHAR(200) NOT NULL comment '任务显示名称',
    task_Type         TINYINT(1) NOT NULL comment '任务类型',
    perform_Type      TINYINT(1) comment '参与类型',
    operator          VARCHAR(100) comment '任务处理人',
    create_Time       VARCHAR(50) comment '任务创建时间',
    finish_Time       VARCHAR(50) comment '任务完成时间',
    expire_Time       VARCHAR(50) comment '任务期望完成时间',
    action_Url        VARCHAR(200) comment '任务处理的url',
    parent_Task_Id    VARCHAR(100) comment '父任务ID',
    variable          VARCHAR(2000) comment '附属变量json存储',
    version           TINYINT(1) comment '版本'
)comment='任务表';

CREATE TABLE wf_task_actor (
    task_Id           VARCHAR(32) not null comment '任务ID',
    actor_Id          VARCHAR(100) not null comment '参与者ID'
)comment='任务参与者表';

create table wf_hist_order (
    id                VARCHAR(32) not null primary key comment '主键ID',
    process_Id        VARCHAR(32) not null comment '流程定义ID',
    order_State       TINYINT(1) not null comment '状态',
    creator           VARCHAR(100) comment '发起人',
    create_Time       VARCHAR(50) not null comment '发起时间',
    end_Time          VARCHAR(50) comment '完成时间',
    expire_Time       VARCHAR(50) comment '期望完成时间',
    priority          TINYINT(1) comment '优先级',
    parent_Id         VARCHAR(32) comment '父流程ID',
    order_No          VARCHAR(100) comment '流程实例编号',
    variable          VARCHAR(2000) comment '附属变量json存储'
)comment='历史流程实例表';

create table wf_hist_task (
    id                VARCHAR(32) not null primary key comment '主键ID',
    order_Id          VARCHAR(32) not null comment '流程实例ID',
    task_Name         VARCHAR(100) not null comment '任务名称',
    display_Name      VARCHAR(200) not null comment '任务显示名称',
    task_Type         TINYINT(1) not null comment '任务类型',
    perform_Type      TINYINT(1) comment '参与类型',
    task_State        TINYINT(1) not null comment '任务状态',
    operator          VARCHAR(100) comment '任务处理人',
    create_Time       VARCHAR(50) not null comment '任务创建时间',
    finish_Time       VARCHAR(50) comment '任务完成时间',
    expire_Time       VARCHAR(50) comment '任务期望完成时间',
    action_Url        VARCHAR(200) comment '任务处理url',
    parent_Task_Id    VARCHAR(32) comment '父任务ID',
    variable          VARCHAR(2000) comment '附属变量json存储'
)comment='历史任务表';

create table wf_hist_task_actor (
    task_Id           VARCHAR(32) not null comment '任务ID',
    actor_Id          VARCHAR(100) not null comment '参与者ID'
)comment='历史任务参与者表';

create index IDX_PROCESS_NAME on wf_process (name);
create index IDX_ORDER_PROCESSID on wf_order (process_Id);
create index IDX_ORDER_NO on wf_order (order_No);
create index IDX_TASK_ORDER on wf_task (order_Id);
create index IDX_TASK_TASKNAME on wf_task (task_Name);
create index IDX_TASK_PARENTTASK on wf_task (parent_Task_Id);
create index IDX_TASKACTOR_TASK on wf_task_actor (task_Id);
create index IDX_HIST_ORDER_PROCESSID on wf_hist_order (process_Id);
create index IDX_HIST_ORDER_NO on wf_hist_order (order_No);
create index IDX_HIST_TASK_ORDER on wf_hist_task (order_Id);
create index IDX_HIST_TASK_TASKNAME on wf_hist_task (task_Name);
create index IDX_HIST_TASK_PARENTTASK on wf_hist_task (parent_Task_Id);
create index IDX_HIST_TASKACTOR_TASK on wf_hist_task_actor (task_Id);

/**
测试表
 */
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province_id` int(11) NOT NULL,
  `city_name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ;

