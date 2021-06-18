<#assign base=request.contextPath /> 
<div class="pageContent">
	<form action="${base}/admin/webSiteUrl/update" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
            <input type="hidden" name="id" value="${bean.id}"/>
    		<p>
                <label>标题：</label>
                <input type="text" name="urlName" value="${bean.urlName!}" placeholder="标题" size="20" />
            </p>
    		<p>
                <label>内网：</label>
                <input type="text" name="inNet" value="${bean.inNet!}" placeholder="内网" size="20" />
            </p>
    		<p>
                <label>外网：</label>
                <input type="text" name="outNet" value="${bean.outNet!}" placeholder="外网" size="20" />
            </p>
    		<p>
                <label>排序：</label>
                <input type="text" name="sort" value="${bean.sort!}" placeholder="排序" size="20" />
            </p>
            <p>
                <label>描述：</label>
                <textarea name="urlDesc" cols="25" rows="5" style="margin: -15px 0 0 130px;">${bean.urlDesc!}</textarea>
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
