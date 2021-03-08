clearMenuFunction
===

* 当功能点被删除的时候，将菜单对应的功能列表设置为空

	update core_menu set FUNCTION_ID=null where FUNCTION_ID in (#join(functionIds)#)

queryByCondtion
===============
* 根据条件查询



allMenuWithURL
===

* 获得菜单和对应功能点的URL

	select m.*,f.access_url from core_menu m left join core_function  f on m.function_id=f.id 

