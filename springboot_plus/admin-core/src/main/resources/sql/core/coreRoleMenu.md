queryMenuByUser
===

* 根据用户和登录机构来获取能访问的菜单列表

	select menu_id from core_role_menu  rm where rm.role_id in (select role_id from core_user_role where user_id=#userId# and org_id=#orgId#)
	
deleteRoleMenu
===

* 删除菜单对应的角色关系

	delete from core_role_menu where menu_id in ( #join(ids)# )
	