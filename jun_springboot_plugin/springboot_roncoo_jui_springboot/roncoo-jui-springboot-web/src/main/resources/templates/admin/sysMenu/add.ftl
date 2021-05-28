<#assign base=request.contextPath />
<div class="pageContent">
	<form action="${base}/admin/sysMenu/save" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="58">
            <input type="hidden" name="parentId" value="${bean.parentId}"/>
    		<p>
                <label>菜单名称：</label>
                <input type="text" name="menuName" value="" placeholder="菜单名称" size="20">
            </p>
    		<p>
                <label>菜单路径：</label>
                <input type="text" name="menuUrl" value="" placeholder="菜单路径" size="20">
            </p>
    		<p>
                <label>目标名称：</label>
                <input type="text" name="targetName" value="" placeholder="目标名称" size="20">
            </p>
    		<p>
                <label>菜单图标：</label>
                <input type="text" name="menuIcon" value="" placeholder="菜单图标" size="20">
            </p>
             <p>
                <label>排序：</label>
                <input type="text" name="sort" value="" placeholder="排序" size="20">
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
