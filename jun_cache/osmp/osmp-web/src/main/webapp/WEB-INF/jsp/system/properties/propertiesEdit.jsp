<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="UpdateForm" method="POST">
		<input type="hidden" name="id" value="${pro.id}" />
		<table>
			<tr>
				<th>名称:</th>
				<td><input name="name" type="text" value="${pro.name}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>KEY:</th>
				<td><input name="prokey" type="text" value="${pro.prokey}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>VALUE:</th>
				<td><input name="provalue" type="text" value="${pro.provalue}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>备注:</th>
				<td><input name="remark" type="text" value="${pro.remark}" />
				</td>
			</tr>
		</table>
	</form>
</div>
