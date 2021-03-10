<#include "/macro/base.ftl" />

<form id="pagerForm" method="post" action="${base}/admin/rcReport/list">
	<@pagerForm />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/admin/rcReport/list" method="post">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>用户邮箱：</label>
					<input type="text" name="userEmail" value="${(bean.userEmail)!}"/>
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
			<li class="line">line</li>
			<@shiro.hasPermission name="/admin/rcReport/download">
			<li><a class="icon" href="${base}/admin/rcReport/download" target="dwzExport" targetType="navTab" title="确定要导出这些记录吗?"><span>导出 EXCEL</span></a></li>
		    </@shiro.hasPermission>
		</ul>
	</div>

	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
			<thead>
				
				<tr>
					<th align="center" width="50">序号</th>
					<th>用户邮箱</th>
					<th>用户昵称</th>
					<th>排序</th>
					<th>创建时间</th>
					<th>更新时间</th>
				</tr>
			</thead>
			<tbody>
				<#if (page)??>
					<#list page.list as bean>
						<tr target="sid" rel="${bean.id}">
							<td align="center">${bean_index+1}</td>
							<td>${bean.userEmail!}</td>
							<td>${bean.userNickname!}</td>
							<td>${bean.sort!}</td>
							<td>${bean.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
							<td>${bean.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
						</tr>
					</#list>
				<#else>
					<tr>
						<td colspan="7" align="center">暂没记录</td>
					</tr>
				</#if>
			</tbody>
		</table>
	</div>
	<@pages />
</div>
