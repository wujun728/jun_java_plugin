<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign targetpackage = targetpackage>


<#assign pid="business_manager" />
<#if table.sqlName?starts_with("t_")>
<#assign pid="system_manager" />
</#if>

INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_list','${table.tableAlias}管理', '${pid}', null,'/${targetpackage}/${classNameLowerCase}/list','1',1);
INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_update','修改${table.tableAlias}', '${table.sqlName}_list', null,'/${targetpackage}/${classNameLowerCase}/update','0',1);
INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_look','查看${table.tableAlias}', '${table.sqlName}_list', null,'/${targetpackage}/${classNameLowerCase}/look','0',1);
INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_export','导出${table.tableAlias}', '${table.sqlName}_list', null,'/${targetpackage}/${classNameLowerCase}/list/export','0',1);
INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_deletemore','批量删除${table.tableAlias}', '${table.sqlName}_list', null,'/${targetpackage}/${classNameLowerCase}/delete/more','0',1);
INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_delete','删除${table.tableAlias}', '${table.sqlName}_list', null,'/${targetpackage}/${classNameLowerCase}/delete','0',1);
INSERT INTO t_menu(`id`, `name`, `pid`, `description`, `pageurl`, `menuType`, `active`) values('${table.sqlName}_upload','导入${table.tableAlias}', '${table.sqlName}_list', null,'/${targetpackage}/${classNameLowerCase}/upload','0',1);
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_list_admin', 'r_10001', '${table.sqlName}_list');
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_update_admin', 'r_10001', '${table.sqlName}_update');
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_look_admin', 'r_10001', '${table.sqlName}_look');
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_export_admin', 'r_10001', '${table.sqlName}_export');
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_deletemore_admin', 'r_10001', '${table.sqlName}_deletemore');
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_delete_admin', 'r_10001', '${table.sqlName}_delete');
INSERT INTO `t_role_menu` (`id`, `roleId`, `menuId`) VALUES ('${table.sqlName}_upload_admin', 'r_10001', '${table.sqlName}_upload');
