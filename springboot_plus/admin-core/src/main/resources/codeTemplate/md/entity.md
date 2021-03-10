queryByCondition
===


    select 
    \@pageTag(){
    t.*
    \@}
    from ${entity.tableName} t
    where 1=1  
    \@//数据权限，该sql语句功能点,如果不考虑数据权限，可以删除此行  
    and #function("${entity.code}.query")#
    @for(attr in entity.list){
    		@if(attr.showInQuery){
    		    @if(attr.dateRange || attr.dateTimeRange){
    \@if(!isEmpty(${strutil.replace (attr.name,"Range","")}Start)){
        and  t.${attr.colName} > #${strutil.replace (attr.name,"Range","")}Start#
    \@}
    \@if(!isEmpty(${strutil.replace (attr.name,"Range","")}End)){
        and  t.${attr.colName} < #${strutil.replace (attr.name,"Range","")}End#
    \@}
                @} else {
    \@if(!isEmpty(${attr.name})){
        and  t.${attr.colName} =#${attr.name}#
    \@}
                @}
    		@}
    @}
    
    
    

batchDel${entity.name}ByIds
===

* 批量逻辑删除

    update ${entity.tableName} set del_flag = 1 where ${entity.idAttribute.colName}  in( #join(ids)#)
    
