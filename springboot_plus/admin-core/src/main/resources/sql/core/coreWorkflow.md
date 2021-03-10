queryUsersByRole
===

* 根据角色和组织机构查询用，注意到用户兼职情况，因此实际部门应该是work_org_id

	 select u.*,ur.org_id work_org_id from CORE_USER_ROLE ur,CORE_User u 
	 where ur.role_id = #roleId# and ur.org_id in( #join(orgs)#) and ur.user_id=u.id