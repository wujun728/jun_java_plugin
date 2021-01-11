<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="/user/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input hidden="true" name="navTabId" value="userPage">
		<div class="pageFormContent" layoutH="50">
			<p>
				<label>真实姓名：</label>
				<input name="name" class="required" type="text" size="30" alt="请输入真实姓名"/>
			</p>
			<p>
				<label>登录名：</label>
				<input name="loginName" class="required" type="text" size="30" alt="请输入登录名" remote="/user/isUnique"/>
			</p>
			<p>
				<label>密码：</label>
				<input id="w_validation_pwd" type="password" name="password" class="required alphanumeric" minlength="6" maxlength="16" size="30"/>
			</p>
			<p>
				<label>确认密码：</label>
				<input type="password" name="repassword" class="required" equalto="#w_validation_pwd" size="30"/>
			</p>
			<p>
				<label>TEL：</label>
				<input name="tel" class="required phone" type="text" size="30" alt="请输入电话号码"/>
			</p>
			<p>
				<label>邮箱：</label>
				<input name="email" class="required email" type="text" size="30" alt="请输入邮箱地址"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>