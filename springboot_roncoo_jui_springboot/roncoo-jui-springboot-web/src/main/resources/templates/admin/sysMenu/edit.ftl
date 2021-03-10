<#assign base=request.contextPath /> 
<div class="pageContent">
	<form method="post" action="${base}/admin/sysMenu/update" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)";>
		<div class="pageFormContent" layoutH="58">
            <input type="hidden" name="id" value="${bean.id}"/>
    		<p>
                <label>菜单名称：</label>
                <input type="text" name="menuName" value="${bean.menuName}" alt="菜单名称" size="20" class="required"/>
            </p>
    		<p>
                <label>菜单路径：</label>
                <input type="text" name="menuUrl" value="${bean.menuUrl}" alt="菜单路径" size="20"/>
            </p>
    		<p>
                <label>目标名称：</label>
                <input type="text" name="targetName" value="${bean.targetName}" alt="目标名称" size="20"/>
            </p>
    		<p>
                <label>菜单图标：</label>
                <input type="text" name="menuIcon" value="${bean.menuIcon}" alt="菜单图标" size="20"/>
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
            <p>
                <label>备注：</label>
                <input type="text" name="remark" value="${bean.remark!}" alt="备注" size="20"/>
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
