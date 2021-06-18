<#assign base=request.contextPath /> 
<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <p>
            <label>创建时间：</label>${bean.gmtCreate}
        </p>
        <p>
            <label>修改时间：</label>${bean.gmtModified}
        </p>
        <p>
            <label>状态：</label>${bean.statusId}
        </p>
        <p>
            <label>LOGO：</label>${bean.siteLogo}
        </p>
        <p>
            <label>名字：</label>${bean.siteName}
        </p>
        <p>
            <label>描述：</label>${bean.siteDesc}
        </p>
        <p>
            <label>URL：</label>${bean.siteUrl}
        </p>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
        </ul>
    </div>
</div>
