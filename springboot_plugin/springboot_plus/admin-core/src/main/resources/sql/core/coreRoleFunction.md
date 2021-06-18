getRoleFunction
===

* 查询指定的登录用户是否能访问某个功能

	select * from core_role_function where role_id in (
		select role_id  from core_user_role where user_id =#userId# and org_id=#orgId#
	) and FUNCTION_ID = (select id from core_function where code=#code#)



getRoleChildrenFunction
===

* 查询指定角色和所在机构的人某个功能下的子功能列表

	select sf.code from core_role_function  rf left join core_function sf on rf.function_id=sf.id where rf.role_id in (
		select role_id  from core_user_role where user_id =#userId# and org_id=#orgId#
	) and rf.FUNCTION_ID in (select id from core_function where parent_id=  #parentId#	)
	



