<#assign base=request.contextPath /> 
<#include "/macro/menu.ftl" />
<div class="pageContent">
    <form method="post" action="${base}/admin/sysMenuRole/setMenu" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="58">
        <input type="hidden" name="roleId" value="${roleId!}">
        
        <ul id="menu" class="tree treeFolder treeCheck expand" oncheck="treeclick">
            <#list menuList as bean>
                <li><a tname="ids" tvalue="${bean.id}" <#if bean.isShow == 1>checked</#if>>${bean.menuName}</a>
                    <#if bean.list?? && bean.list?size gt 0>
                        <@menuListForRole children=bean.list/>
                    </#if>
                </li>
            </#list>
        </ul>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">    
//遍历被选中CheckBox元素的集合 得到Value值    
function treeclick()  {    
    var ids=""; 
    $("#menu input:checked").each(function(i,a){    
        ids += a.value + ',';  
    });    
    console.log(ids);    
}    
</script>