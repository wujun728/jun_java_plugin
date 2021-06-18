<#assign base=request.contextPath /> 
<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <p>
            <label>标题：</label>${bean.urlName}
        </p>
        <p>
            <label>内网：</label>${bean.inNet}
        </p>
        <p>
            <label>外网：</label>${bean.outNet}
        </p>
        <p>
            <label>描述：</label>${bean.urlDesc}
        </p>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
        </ul>
    </div>
</div>
