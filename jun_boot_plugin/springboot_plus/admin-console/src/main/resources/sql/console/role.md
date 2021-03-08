queryByCondtion
===

	select
	@pageTag(){
	    r.*
	@}
	from core_role r where 1=1
	@if(!isEmpty(code)){
	    and  r.code like #"%"+code+"%"#
	@}
	@if(!isEmpty(name)){
	    and  r.name like #"%"+name+"%"#
	@}
	
	@if(!isEmpty(type)){
	    and  r.type = #type#
	@}
	
	@pageIgnoreTag(){
	   order by id desc
	@}
	
queryUser
===

	select
	@pageTag(){
	    u.*,ur.ORG_ID org_id_1
	@}
	from core_user u,core_user_role ur,core_role r
	where r.ID = ur.ROLE_ID and ur.USER_ID=u.ID 
	and u.DEL_FLAG=0 
	and r.id=#roleId#
	@if(!isEmpty(userCode)){
	    and  u.code like #"%"+userCode+"%"#
	@}
	@if(!isEmpty(userName)){
	    and  u.name like #"%"+userName+"%"#
	@}
	@pageIgnoreTag(){
	   order by u.id desc
	@}



batchDelByIds
===

* 批量删除角色，同时也参考batchDeleteRoleFunction，batchDeleteRoleMenu等方法删除其他关联数据
	
	delete from core_role  where id in( #join(ids)#)

batchDeleteRoleFunction
===
	delete from core_role_function  where role_id in( #join(ids)#)
	
batchDeleteRoleMenu
===
	delete from core_role_menu  where role_id in( #join(ids)#)
	
batchDeleteUserRole
===
	delete from core_user_role  where role_id in( #join(ids)#)
	

queryAllByDelflag
=================
    select * from core_role r where r.del_flag = #delFlag#
