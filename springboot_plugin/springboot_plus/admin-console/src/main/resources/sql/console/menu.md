
queryByCondtion
===============
* 根据条件查询

	select 
	@pageTag(){
	   m.*,f.NAME function_name,f.ACCESS_URL ,
	   p.name parent_menu_name
	@}
	from core_menu m left join core_function f on m.FUNCTION_ID=f.id  left join core_menu p on m.parent_menu_id = p.id
	where 1=1
	@if(!isEmpty(url)){
	    and  f.access_url like #'%'+url+"%"#
	@}
	
	@if(!isEmpty(code)){
	    and  m.code like #'%'+code+"%"#
	@}
	
	@if(!isEmpty(name)){
	    and  m.name like #'%'+name+"%"#
	@}
	
	@if(!isEmpty(parentMenuId)){
	    and  m.parent_menu_id = #parentMenuId#
	@}
	
	@pageIgnoreTag(){
	   order by m.id
	@}
	


