<#include "/macro/base.ftl" />
<form id="pagerForm" method="post" action="${base}/admin/sysUser/list">
    <@pagerForm />
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/admin/sysUser/list" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>手机：</label>
                    <input type="text" name="userPhone" value="${(bean.userPhone)!}"/>
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
            <@shiro.hasPermission name="/admin/sysUser/add">
            <li class="line">line</li>
            <li><a class="add" href="${base}/admin/sysUser/add" target="dialog"><span>添加</span></a></li>
            <li class="line">line</li>
            </@shiro.hasPermission>
        </ul>
    </div>
    <div id="w_list_print">
        <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
            <thead>
                <tr>
                <th width="30">序号</th>
                <th>用户手机</th>
                <th>用户邮箱</th>
                <th>真实姓名</th>
                <th>用户昵称</th>
                <th>性别</th>
                <th>用户状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#if page??>
                <#list page.list as bean>
                <tr>
                    <td align="center">${bean_index+1}</td>
                    <td>${bean.userPhone}</td>
                    <td>${bean.userEmail}</td>
                    <td>${bean.userRealname}</td>
                    <td>${bean.userNickname}</td>
                    <td>
                    <#list userSexEnums as userSex>
                        <#if bean.userSex == userSex.code>${userSex.desc}</#if>
                    </#list>
                    </td>
                    <td>
                    <#list userStatusEnums as userStatus>
                        <#if bean.userStatus == userStatus.code>${userStatus.desc}</#if>
                    </#list>
                    </td>
                    <td>
                        <@shiro.hasPermission name="/admin/sysUser/view">
                        <a title="查看" target="dialog" href="${base}/admin/sysUser/view?id=${bean.id}" class="btnView">查看 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/sysUser/edit">
                        <a title="编辑" target="dialog" href="${base}/admin/sysUser/edit?id=${bean.id}" class="btnEdit">修改 </a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/sysUser/delete">
                        <a title="确定要删除吗？" target="ajaxTodo" href="${base}/admin/sysUser/delete?id=${bean.id}" class="btnDel">删除</a>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="/admin/sysRoleUser/set">
                        <a title="设置角色" target="dialog" href="${base}/admin/sysRoleUser/set?userId=${bean.id}">【设置角色】 </a>
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
