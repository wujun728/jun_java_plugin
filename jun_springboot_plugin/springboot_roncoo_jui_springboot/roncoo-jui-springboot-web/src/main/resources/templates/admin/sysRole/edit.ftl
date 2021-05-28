<#assign base=request.contextPath /> 
<div class="pageContent">
	<form action="${base}/admin/sysRole/update" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
            <input type="hidden" name="id" value="${bean.id}"/>
    		<p>
                <label>名称：</label>
                <input type="text" name="roleName" value="${bean.roleName}" placeholder="名称" size="20">
            </p>
            <p>
                <label>备注：</label>
                <input type="text" name="remark" value="${bean.remark!}" placeholder="备注" size="20">
            </p>
            <p>
                <label>排序：</label>
                <input type="text" name="sort" value="${bean.sort}" alt="打开方式" size="20" class="required"/>
            </p>
            <p>
                <label>状态：</label>
                <#list statusIdEnums as statusIdEnum>
                <input type="radio" name="statusId" value="${statusIdEnum.code}" <#if bean.statusId == statusIdEnum.code>checked="checked"</#if>/> ${statusIdEnum.desc}
                </#list>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
	</form>
</div>
