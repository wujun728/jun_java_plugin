queryByCondtion
===

    select 
    @pageTag(){
    f.*
    @}
    from core_function f where 1=1 
    @if(!isEmpty(functionIds)){
        and  f.id in ( #join(functionIds)#)
    @}
    @if(!isEmpty(code)){
        and  f.code like #"%"+code+"%"#
    @}
    @if(!isEmpty(name)){
        and  f.name like #"%"+name+"%"#
    @}
    @if(!isEmpty(accessUrl)){
        and  f.access_url like #"%"+accessUrl+"%"#
    @}
    @if(!isEmpty(parentFunctionId)){
        and  f.parent_id = #parentFunctionId#
    @}
    @pageIgnoreTag(){
		   order by id
		@}
	
