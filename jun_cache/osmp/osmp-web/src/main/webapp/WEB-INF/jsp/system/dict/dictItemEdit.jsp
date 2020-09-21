<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="DictItemUpdateForm" method="POST">
		<input type="hidden" name="parentCode" value="${code}" />
		<table>
			<tr>
				<th>编码:</th>
				<td><input name="code" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>名称:</th>
				<td><input name="name" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
		</table>
	</form>
</div>
