<#assign base=request.contextPath /> 
<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <p>
            <label>用户手机：</label>${bean.userPhone}
        </p>
        <p>
            <label>用户邮箱：</label>${bean.userEmail}
        </p>
        <p>
            <label>真实姓名：</label>${bean.userRealname}
        </p>
        <p>
            <label>用户昵称：</label>${bean.userNickname}
        </p>
        <p>
            <label>性别：</label>${bean.userSex}
        </p>
        <p>
            <label>用户状态：</label>${bean.userStatus}
        </p>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
        </ul>
    </div>
</div>
