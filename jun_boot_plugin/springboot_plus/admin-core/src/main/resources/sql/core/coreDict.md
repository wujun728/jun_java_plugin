findAllList
===
	select * from core_dict where del_flag = 0
	@if(!isEmpty(type)){
	    and  type = #type#
	@}
	ORDER BY type, sort DESC

findTypeList
===
    SELECT
       DISTINCT (type),type_name
    FROM core_dict
    WHERE del_flag = #delFlag#
    ORDER BY type



findChildByParent
===
    SELECT
        *
    FROM core_dict
    WHERE
    parent = #id# and del_flag = 0 order by sort DESC


bathDelByValue
===
    update core_dict set del_flag =1 where value in in ( #join(values)#)
   