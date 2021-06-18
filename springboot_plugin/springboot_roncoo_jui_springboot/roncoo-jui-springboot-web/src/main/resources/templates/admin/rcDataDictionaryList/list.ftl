<#include "/macro/base.ftl" />

<form id="pagerForm" method="post" action="${base}/admin/rcDataDictionaryList/list">
	<@pagerForm />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${base}/admin/rcDataDictionaryList/list" method="post">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>字段KEY：</label>
					<input type="text" name="fieldKey" value="${(bean.fieldKey)!}"/>
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
            <@shiro.hasPermission name="/admin/rcDataDictionaryList/add">
			<li class="line">line</li>
			<li><a class="add" href="${base}/admin/rcDataDictionaryList/add?fieldCode=${fieldCode!}" target="dialog"><span>添加</span></a></li>
			<li class="line">line</li>
			</@shiro.hasPermission>
            <@shiro.hasPermission name="/admin/rcDataDictionaryList/delete">
			<li><a class="delete" href="${base}/admin/rcDataDictionaryList/delete?id={sid}" target="ajaxTodo" title="确定要删除吗？" warn="请选中要删除的数据"><span>删除</span></a></li>
			<li class="line">line</li>
			</@shiro.hasPermission>
            <@shiro.hasPermission name="/admin/rcDataDictionaryList/edit">
			<li><a class="edit" href="${base}/admin/rcDataDictionaryList/edit?id={sid}" target="dialog" warn="请选中要修改的数据"><span>修改</span></a></li>
			<li class="line">line</li>
		    </@shiro.hasPermission>
		</ul>
	</div>

	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
			<thead>
				<tr>
					<th colspan="7" align="center">基本信息</th>
					<th colspan="1">操作</th>
				</tr>
				<tr>
					<th align="center" width="50">序号</th>
					<th>CODE值</th>
					<th>字段KEY</th>
					<th>字段VALUE</th>
					<th>排序</th>
					<th>备注</th>
					<th>更新时间</th>
					<th width="200">操作</th>
				</tr>
			</thead>
			<tbody>
				<#if (page)??>
					<#list page.list as bean>
						<tr target="sid" rel="${bean.id}">
							<td align="center">${bean_index+1}</td>
							<td>${bean.fieldCode}</td>
							<td>${bean.fieldKey}</td>
							<td>${bean.fieldValue}</td>
							<td>${bean.sort}</td>
							<td>${bean.remark}</td>
							<td>${bean.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
							<td>
                                <@shiro.hasPermission name="/admin/rcDataDictionaryList/delete">
                                <a title="确定要删除吗？" target="ajaxTodo" href="${base}/admin/rcDataDictionaryList/delete?id=${bean.id}" class="btnDel">删除</a>
								</@shiro.hasPermission>
                                <@shiro.hasPermission name="/admin/rcDataDictionaryList/edit">
                                <a title="编辑" target="dialog" href="${base}/admin/rcDataDictionaryList/edit?id=${bean.id}" class="btnEdit">编辑</a>
								</@shiro.hasPermission>
							</td>
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
