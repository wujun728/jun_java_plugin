getUserByRole
===

	select u.* from core_user u,sys_role  r,core_user_role ur 
	where r.code= #roleCode# and r.ID=ur.ROLE_ID and ur.USER_ID=u.ID
	
	
	
	
	