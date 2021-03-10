<#assign base=request.contextPath />
<h2 class="contentTitle">网址汇总</h2>
<div class="pageContent">
    <form action="${base}/admin/webSite/update" method="post" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${bean.id}"/>
        <div class="pageFormContent" layoutH="97">
        <fieldset>
            <legend>网站LOGO</legend>
            <div class="upload-wrap">
                <div class="thumbnail">
                    <img src="http://www.roncoo.com/images/logo.png" style="max-width: 80px; max-height: 80px">
                </div>
            </div>
            <div class="upload-wrap">
                <input type="file" name="fileLogo" accept="image/*">
            </div>
        </fieldset>
        <fieldset>
            <legend>网站信息</legend>
            <dl class="nowrap">
                <dt>标题：</dt>
                <dd><input type="text" name="title" value="${bean.title!}" placeholder="名字" size="40" class="required"/></dd>
            </dl>
            <dl class="nowrap">
                <dt>名称：</dt>
                <dd><input type="text" name="siteName" value="${bean.siteName!}" placeholder="名字" size="40" class="required"/></dd>
            </dl>
            <dl class="nowrap">
                <dt>URL：</dt>
                <dd><input type="text" name="siteUrl" value="${bean.siteUrl!}" placeholder="URL" size="60" /></dd>
            </dl>
            <dl class="nowrap">
                <dt>描述：</dt>
                <dd><textarea name="siteDesc" cols="80" rows="2">${bean.siteDesc!}</textarea></dd>
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
