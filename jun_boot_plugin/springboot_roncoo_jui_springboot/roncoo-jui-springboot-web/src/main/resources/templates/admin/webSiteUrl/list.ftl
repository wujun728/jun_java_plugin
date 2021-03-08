<#include "/macro/base.ftl" />
<form id="pagerForm" method="post" action="${base}/admin/webSiteUrl/list">
    <@pagerForm />
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/admin/webSiteUrl/list?siteId=${bean.siteId}" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>标题：</label>
                    <input type="text" name="urlName" value="${(bean.urlName)!}"/>
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
            <@shiro.hasPermission name="/admin/webSiteUrl/add">
            <li class="line">line</li>
            <li><a class="add" href="${base}/admin/webSiteUrl/add?siteId=${bean.siteId}" target="dialog"><span>添加</span></a></li>
            <li class="line">line</li>
            </@shiro.hasPermission>
        </ul>
    </div>
    <div id="w_list_print">
        <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
            <thead>
                <tr>
                    <th width="30">序号</th>
                    <th>标题</th>
                    <th>内网</th>
                    <th>外网</th>
                    <th>描述</th>
                    <th>排序</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <#if page??>
                <#list page.list as bean>
                <tr>
                    <td align="center">${bean_index+1}</td>
                    <td>${bean.urlName}</td>
                    <td style="max-width:50px;">${bean.inNet}</td>
                    <td style="max-width:50px;">${bean.outNet}</td>
                    <td style="max-width:200px;">${bean.urlDesc}</td>
                    <td>${bean.sort}</td>
                    <td>
                        <@shiro.hasPermission name="/admin/webSiteUrl/edit">
                        <a title="编辑" target="dialog" href="${base}/admin/webSiteUrl/edit?id=${bean.id}" class="btnEdit">修改 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/webSiteUrl/delete">
                        <a title="确定要删除吗？" target="ajaxTodo" href="${base}/admin/webSiteUrl/delete?id=${bean.id}" class="btnDel">删除</a>
                        </@shiro.hasPermission>
                    </td>
                </tr>
                </#list>
                </#if>
            </tbody>
        </table>
    </div>
    <@pages />
</div>
