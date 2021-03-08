<!-- 菜单递归显示 -->
<#macro listMenu children label>
	<#if children??>
		<#list children as bean>
			<tr>
			    <td align="center"></td>
                <td>${label} ${bean.menuName}</td>
                <td>${bean.menuUrl}</td>
                <td>${bean.targetName}</td>
                <td>${bean.menuIcon}</td>
                <td>${bean.sort}</td>
                <td>
                <#list statusIdEnums as statusId>
                    <#if bean.statusId == statusId.code>${statusId.desc}</#if>
                </#list>
                </td>
                <td>
                    <a title="查看" target="dialog" href="${base}/admin/sysMenu/view?id=${bean.id}" class="btnView">查看 </a>
                    <a title="编辑" target="dialog" href="${base}/admin/sysMenu/edit?id=${bean.id}" class="btnEdit">修改 </a>
                    <a title="确定要删除吗？" target="ajaxTodo" href="${base}/admin/sysMenu/delete?id=${bean.id}" class="btnDel">删除</a>
                    <a title="添加" target="dialog" href="${base}/admin/sysMenu/add?parentId=${bean.id}" class="btnAdd">添加 </a>
                </td>
			</tr>
			<@listMenu children=bean.list label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+label/>
		</#list>
	</#if>
</#macro>

<!-- 菜单递归显示 -->
<#macro menuListForRole children>
    <#if children?? && children?size gt 0>
        <ul>
        <#list children as bean>
            <li><a tname="ids" tvalue="${bean.id}" <#if bean.isShow == 1>checked</#if>>${bean.menuName}</a>
                <@menuListForRole children=bean.list/>
            </li>
        </#list>
        </ul>
    </#if>
</#macro>
