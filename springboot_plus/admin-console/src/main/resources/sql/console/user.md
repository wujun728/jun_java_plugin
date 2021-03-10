
queryByCondtion
===

    select 
    @pageTag(){
    u.*,o.name org_name
    @}
    from core_user u left join core_org o on u.org_id=o.id where 1=1 and u.del_flag = 0 
    @//数据权限，该sql语句功能点  
    and #function("user.query")#
    @if(!isEmpty(orgId)){
        and  u.org_id =#orgId#
    @}
     @if(!isEmpty(orgId)){
        and  u.org_id =#orgId#
    @}
    @if(!isEmpty(code)){
        and  u.code like #"%"+code+"%"#
    @}
    @if(!isEmpty(name)){
        and  u.name like #"%"+name+"%"#
    @}
    @if(!isEmpty(state)){
        and  u.state = #state#
    @}
    @if(!isEmpty(jobType0)){
        and  u.job_type0= #jobType0#
    @}
    @if(!isEmpty(jobType1)){
        and  u.job_type1= #jobType1#
    @}
    @if(!isEmpty(createDateMin)){
        and  u.create_time>= #createDateMin#
    @}
    @if(!isEmpty(createDateMax)){
        and  u.create_time< #nextDay(createDateMax)#
    @}
    
    

batchDelUserByIds
===
    update core_user set del_flag = 1 where id in( #join(ids)#)
    
batchUpdateUserState
===
    update core_user set state = #state# where id in( #join(ids)#)
    
queryUserRole
===

* 查询用户所有权限

    select	
    ur.*, u.code as user_code,
    u.name as user_name,
    org.name as org_name, role.name as role_name
    from core_user_role ur
    left join core_org org on org.id = ur.org_id
    left join core_user u on u.id = ur.user_id
    left join core_role role on role.id = ur.role_id
    where u.id=#id# 
    @if(isNotEmpty(orgId)){
    	and org.id=#orgId#
    @}
    @if(isNotEmpty(roleId)){
    	and role.id=#roleId#
    @}
