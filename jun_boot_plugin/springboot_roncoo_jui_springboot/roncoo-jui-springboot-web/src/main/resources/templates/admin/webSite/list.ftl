<#include "/macro/base.ftl" />
<form id="pagerForm" method="post" action="${base}/admin/webSite/list">
    <@pagerForm />
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/admin/webSite/list" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>标题：</label>
                    <input type="text" name="siteName" value="${(bean.siteName)!}"/>
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
            <@shiro.hasPermission name="/admin/webSite/add"> 
            <li class="line">line</li>
            <li><a class="add" href="${base}/admin/webSite/add" target="navTab"><span>添加</span></a></li>
            <li class="line">line</li>
            </@shiro.hasPermission>
        </ul>
    </div>
    <div id="w_list_print">
        <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
            <thead>
                <tr>
                    <th width="30">序号</th>
                    <th width="250">LOGO</th>
                    <th>标题</th>
                    <th>名称</th>
                    <th>描述</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <#if page??>
                <#list page.list as bean>
                <tr>
                    <td align="center">${bean_index+1}</td>
                    <td><img src="http://www.roncoo.com/images/logo.png" alt="龙果学院" width="200"/></td>
                    <td>${bean.title!}</td>
                    <td><a href="${bean.siteUrl!}" target="_blank">${bean.siteName!}</a></td>
                    <td style="max-width:500px;">${bean.siteDesc!}</td>
                    <td>
                        <@shiro.hasPermission name="/admin/webSite/view">
                        <a title="查看" target="navTab" href="${base}/admin/webSite/view?id=${bean.id}" class="btnView">查看 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/webSite/edit">
                        <a title="编辑" target="navTab" href="${base}/admin/webSite/edit?id=${bean.id}" class="btnEdit">修改 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/webSite/delete">
                        <a title="确定要删除吗？" target="ajaxTodo" href="${base}/admin/webSite/delete?id=${bean.id}" class="btnDel">删除</a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/webSiteUrl/list">
                        <a title="设置网址" target="navTab" href="${base}/admin/webSiteUrl/list?siteId=${bean.id}" rel="admin-webSiteUrl">【设置网址】 </a>
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
