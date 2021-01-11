<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<form method="post" action="/room/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input hidden="true" name="navTabId" value="roomPage">
		<div class="pageFormContent" layoutH="50">
			<p>
				<label>会议室名称：</label>
				<input name="name" class="required" type="text" size="30" alt="请输入名称" remote="/room/isUnique"/>
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