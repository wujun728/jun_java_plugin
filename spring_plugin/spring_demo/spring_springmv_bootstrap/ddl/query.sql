-- 部署对象和流程定义相关的表
select * from act_re_deployment; -- 部署对象表
select * from act_re_procdef;-- 流程定义表
select * from act_ge_bytearray;-- 资源文件表
select * from act_ge_property;-- 主键生成策略表

-- 流程实例，执行对象，任务
select * from act_ru_execution;-- 正在执行的执行对象表
select * from act_hi_procinst;-- 流程实例的历史表
select * from act_ru_task;-- 正在执行的任务表（只有节点是UserTask的时候，该表中存在数据）
select * from act_hi_taskinst;-- 任务历史表（只有节点是UserTask的时候，该表中存在数据）
select * from act_hi_actinst;-- 所有活动节点的历史表

-- 流程变更
select * from act_ru_variable;-- 正在执行的流程变更表
select * from act_hi_varinst;-- 历史流程定义表

select * from act_ru_identitylink;-- 任务表（人个任务，组任务）
select * from act_hi_identitylink;-- 历史任务办理人表（人个任务，组任务）

select * from act_id_group; -- 角色表
select * from act_id_user;-- 用户表
select * from act_id_membership;-- 用户角色关联表

select * from act_evt_log;
select * from act_hi_attachment;
select * from act_hi_comment;
select * from act_hi_detail;
select * from act_id_info;
select * from act_re_model;
select * from act_ru_event_subscr;
select * from act_ru_job;

/*

delete from act_id_group;
delete from act_id_user;
delete from act_id_membership;

**/

select table_name from information_schema.tables where table_schema='longfor_crm';

select * from t_news;
select * from t_sys_permission;
select * from t_sys_role;
select * from t_sys_role_permission;
select * from t_sys_user;
select * from t_sys_user_role;

/*

delete from t_news;
delete from t_sys_permission;
delete from t_sys_role;
delete from t_sys_role_permission;
delete from t_sys_user;
delete from t_sys_user_role;

*/