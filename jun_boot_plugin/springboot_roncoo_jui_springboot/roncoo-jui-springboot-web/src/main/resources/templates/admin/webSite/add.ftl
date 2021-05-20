<#assign base=request.contextPath />
<h2 class="contentTitle">网址汇总</h2>
<div class="pageContent">
	<form action="${base}/admin/webSite/save" method="post" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" enctype="multipart/form-data">
        <div class="pageFormContent" layoutH="97">
        <fieldset>
            <legend>网站LOGO</legend>
            <div class="upload-wrap">
                <input type="file" name="fileLogo" accept="image/*">
            </div>
        </fieldset>
        <fieldset>
            <legend>网站信息</legend>
    		<dl class="nowrap">
                <dt>标题：</dt>
                <dd><input type="text" name="title" value="" placeholder="标题" size="40" class="required"/></dd>
            </dl>
    		<dl class="nowrap">
                <dt>名称：</dt>
                <dd><input type="text" name="siteName" value="" placeholder="名字" size="40" class="required"/></dd>
            </dl>
            <dl class="nowrap">
                <dt>URL：</dt>
                <dd><input type="text" name="siteUrl" value="" placeholder="URL" size="60" /></dd>
            </dl>
    		<dl class="nowrap">
                <dt>描述：</dt>
                <dd><textarea name="siteDesc" cols="80" rows="2"></textarea></dd>
            </dl>
        </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
	</form>
</div>
