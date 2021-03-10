<#assign base=request.contextPath />
<div class="pageContent">
	<form action="${base}/admin/sysUser/save" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="58">
    		<p>
                <label>用户手机：</label>
                <input type="text" name="userPhone" value="" placeholder="用户手机" size="20">
            </p>
    		<p>
                <label>用户邮箱：</label>
                <input type="text" name="userEmail" value="" placeholder="用户邮箱" size="20">
            </p>
    		<p>
                <label>真实姓名：</label>
                <input type="text" name="userRealname" value="" placeholder="真实姓名" size="20">
            </p>
    		<p>
                <label>用户昵称：</label>
                <input type="text" name="userNickname" value="" placeholder="用户昵称" size="20">
            </p>
    		<p>
                <label>性别：</label>
                <#list userSexEnums as userSexEnum>
                 <input type="radio" name="userSex" value="${userSexEnum.code}" /> ${userSexEnum.desc}
                </#list>
            </p>
    		<p>
                <label>用户密码：</label>
                <input type="text" name="pwd" value="" placeholder="用户密码" size="20">
            </p>
    		<p>
                <label>备注：</label>
                <input type="text" name="remark" value="" placeholder="备注" size="20">
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
	</form>
</div>
