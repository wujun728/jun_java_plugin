<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="z" uri="/osmp-tags"%>
<div align="center">
	<form id="UserAddForm" method="POST">
		<input type="hidden" name="type" value="add" />
		<table>
			<tr>
				<th>用户名:</th>
				<td><input name="name" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>密码:</th>
				<td><input name="password" type="password" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>真实姓名:</th>
				<td><input name="realName" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>昵称:</th>
				<td><input name="nickName" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>是否可用:</th>
				<td><z:select id="status" type="REDIO" dictCode="status" name="status"></z:select>
				</td>
			</tr>
		</table>
	</form>
</div>
