<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="z" uri="/osmp-tags"%>
<div align="center">
	<form id="UserUpdateForm" method="POST">
		<input type="hidden" name="id" value="${user.id}" />
		<input type="hidden" name="type" value="edit" />
		<table>
			<tr>
				<th>用户名:</th>
				<td><input name="name" type="text" value="${user.name}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>新密码:</th>
				<td><input name="password" type="password" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>真实姓名:</th>
				<td><input name="realName" type="text" value="${user.realName}" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>昵称:</th>
				<td><input name="nickName" type="text" value="${user.nickName}" class="easyui-validatebox" data-options="required:true" />
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
