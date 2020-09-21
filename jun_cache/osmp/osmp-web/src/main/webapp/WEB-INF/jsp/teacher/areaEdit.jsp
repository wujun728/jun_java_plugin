<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="AreaUpdateForm" method="POST">
		<input type="hidden" name="id" value="${areaR.id}" />
		<table>
			<tr>
				<th>id:</th>
				<td><input name="name" type="text" value="${areaR.name}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>父区域ID:</th>
				<td><input name="pid" type="text" value="${areaR.pid}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>描述信息:</th>
				<td><input name="areaDesc" type="text" value="${areaR.areaDesc}" />
				</td>
			</tr>
			<tr>
				<th>区域K E Y:</th>
				<td><input name="key" type="text" value="${areaR.key}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>区域VALUE:</th>
				<td><input name="value" type="text" value="${areaR.value}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
		</table>
	</form>
</div>
