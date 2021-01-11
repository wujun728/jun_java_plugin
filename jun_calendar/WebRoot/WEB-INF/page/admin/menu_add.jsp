<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="/menu/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input hidden="true" name="navTabId" value="menuPage">
		<div class="pageFormContent" layoutH="50">
			<p>
				<label>菜品名称：</label>
				<input name="name" class="required" type="text" maxlength="10" size="20" alt="请输入菜名" remote="/menu/isUnique"/>
			</p>
			<p>
				<label>价格：</label>
				<input name="price" class="required" type="number" size="30" alt="请输入价格"/>
			</p>
			<p>
				<label>餐别：</label>
				<select class="combox" name="state">
					<option value="1">午餐</option>
					<option value="2">晚餐</option>
					<option value="3" selected="selected">午餐&#38;晚餐</option>
				</select>
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