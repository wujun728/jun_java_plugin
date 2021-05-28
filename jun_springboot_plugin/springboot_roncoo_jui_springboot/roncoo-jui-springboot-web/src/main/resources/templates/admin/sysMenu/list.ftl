<#include "/macro/base.ftl" />
<#include "/macro/menu.ftl" />
<form id="pagerForm" method="post" action="${base}/admin/sysMenu/list">
    <@pagerForm />
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/admin/sysMenu/list" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>菜单名称：</label>
                    <input type="text" name="menuName" value="${(bean.menuName)!}"/>
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
            <@shiro.hasPermission name="/admin/sysMenu/add">
            <li class="line">line</li>
            <li><a class="add" href="${base}/admin/sysMenu/add?parentId=0" target="dialog"><span>添加</span></a></li>
            <li class="line">line</li>
            </@shiro.hasPermission>
        </ul>
    </div>
    <div id="w_list_print">
        <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
            <thead>
                <tr>
                <th width="30">序号</th>
                <th>菜单名称</th>
                <th>菜单路径</th>
                <th>目标名称</th>
                <th>菜单图标</th>
                <th>排序</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#if page??>
                <#list page.list as bean>
                <tr>
                    <td align="center">${bean_index+1}</td>
                    <td>${bean.menuName}</td>
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
                        <@shiro.hasPermission name="/admin/sysMenu/view">
                        <a title="查看" target="dialog" href="${base}/admin/sysMenu/view?id=${bean.id}" class="btnView">查看 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/sysMenu/edit">
                        <a title="编辑" target="dialog" href="${base}/admin/sysMenu/edit?id=${bean.id}" class="btnEdit">修改 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/sysMenu/delete">
                        <a title="确定要删除吗？" target="ajaxTodo" href="${base}/admin/sysMenu/delete?id=${bean.id}" class="btnDel">删除</a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/sysMenu/add">
                        <a title="添加" target="dialog" href="${base}/admin/sysMenu/add?parentId=${bean.id}" class="btnAdd">添加 </a>
                        </@shiro.hasPermission>
                    </td>
                </tr>
                
                <#if bean.list?? && bean.list?size gt 0>
                    <@listMenu children=bean.list label="&nbsp;&nbsp;|--"/>
                </#if>
                
                </#list>
                </#if>
            </tbody>
        </table>
    </div>
    <@pages />
</div>
