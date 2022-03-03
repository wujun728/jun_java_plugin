-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('城市资料', '', '1', '/system/city', 'C', '0', 'system:city:view', '#', 'admin', sysdate(), '', null, '城市资料菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('城市资料查询', @parentId, '1',  '#',  'F', '0', 'system:city:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('城市资料新增', @parentId, '2',  '#',  'F', '0', 'system:city:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('城市资料修改', @parentId, '3',  '#',  'F', '0', 'system:city:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('城市资料删除', @parentId, '4',  '#',  'F', '0', 'system:city:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('城市资料导出', @parentId, '5',  '#',  'F', '0', 'system:city:export',       '#', 'admin', sysdate(), '', null, '');
