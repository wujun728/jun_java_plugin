<#include "/macro/base.ftl" />
<form id="pagerForm" method="post" action="${base}/admin/sysRoleUser/set?userId=${bean.userId}">
    <@pagerForm />
</form>

<div class="pageHeader">
    <form rel="pagerForm" method="post" action="${base}/admin/sysRoleUser/set?userId=${bean.userId}" onsubmit="return dialogSearch(this);">
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label>角色名称:</label>
                <input class="textInput" name="roleName" value="" type="text">
            </li>     
        </ul>
        <div class="subBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="reset">清空重输</button></div></div></li>
             </ul>
        </div>
    </div>
    </form>
</div>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a title="确定要添加选中的角色？" target="selectedTodo" targetType="dialog" rel="ids" href="${base}/admin/sysRoleUser/setRole?userId=${bean.userId!}" class="add"><span>设置角色</span></a></li>
        </ul>
    </div>
    <table class="table" layoutH="138" targetType="dialog"  width="100%">
        <thead>
            <tr>
                <th align="center" width="30"><input type="checkbox" group="ids" class="checkboxCtrl"/></th>
                <th>角色名称</th>
                <th>状态</th>
            </tr>
        </thead>
        <tbody>
            <#if page??>
            <#list page.list as bean>
            <tr>
                <td align="center"><input type="checkbox" name="ids" value="${bean.id}" <#if bean.isShow == 1>checked="checked"</#if> /></td>
                <td>${bean.roleName!}</td>
                <td>
                    <#list statusIdEnums as statusId>
                        <#if statusId.code == bean.statusId>${statusId.desc}</#if>
                    </#list>
                </td>
            </tr>
            </#list>
            </#if>
        </tbody>
    </table>
   <@pagesDialog />
</div>
