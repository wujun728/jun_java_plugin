<#assign base=request.contextPath /> 
<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <p>
            <label>菜单名称：</label>${bean.menuName}
        </p>
        <p>
            <label>菜单路径：</label>${bean.menuUrl}
        </p>
        <p>
            <label>目标名称：</label>${bean.targetName}
        </p>
        <p>
            <label>菜单图标：</label>${bean.menuIcon}
        </p>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
        </ul>
    </div>
</div>
