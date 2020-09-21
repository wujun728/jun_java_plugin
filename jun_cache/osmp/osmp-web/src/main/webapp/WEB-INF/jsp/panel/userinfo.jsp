<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${PROJECT_NAME}</title>
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
	<style type="text/css">
		.infoInput{
			width:300px;
			height:22px;
			border-top: 2px solid #DDD;
			border-color: #DDD;
		}
		#infoTable th,#infoTable td{
			font-size: 14px;
			font-weight: bold;
		}
	</style>
  </head>
  <body>
  	<div class="easyui-panel" title="用户信息" fit="true">
		<table id="infoTable" cellpadding="10" cellspacing="0" style="margin-left: 100px;margin-top: 50px" >
			<tr><th align="right">用户名:</th><td align="left">${user.username}</td></tr>
			<tr><th align="right">旧密码:</th><td align="left"><input type="password" id="oldPass" class="infoInput" /></td></tr>
			<tr><th align="right">新密码:</th><td align="left"><input type="password" id="newPass" class="infoInput" /></td></tr>
			<tr><th align="right">确认密码:</th><td align="left"><input type="password" id="checkPass" class="infoInput" /></td></tr>
			<tr><th align="right">姓名:</th><td align="left"><input id="cname" class="infoInput" value="${user.cName}"/></td></tr>
			<tr><th align="right">联系电话:</th><td align="left"><input id="tel" class="infoInput" value="${user.tel}"/></td></tr>
			<tr><th align="right">邮箱:</th><td align="left"><input id="email"  class="infoInput" value="${user.email}"/></td></tr>
			<tr><td colspan="2" style="padding-left: 50px"><button onclick="saveDo()">保存</button></td></tr>
		</table>
	</div>
    <script type="text/javascript">
    	var cnameFirst = '${user.cName}';
    	var telFirst = '${user.tel}';
    	var emailFirst = '${user.email}';
    	function saveDo(){
    			if(passCheck()&&!notChange()){
    				$.ajax({
    					url:'${BASE_PATH}/user/updateUserContact.do',
    					data:'id=${user.id}&cname='+$.trim($('#cname').val())+'&tel='+$.trim($('#tel').val())+'&email='+$.trim($('#email').val())+'&oldPass='+$.trim($('#oldPass').val())+'&newPass='+$.trim($('#newPass').val()),
    					type:'POST',
    					success:function(result){
    						result = result.replace(/\"/g,'');
    						if(result == 'error'){
    							//$.messager.alert('错误','输入的旧密码错误','error');
    							alert('输入的旧密码错误');
    						}else{
    							//$.messager.alert('提示','修改成功');
    							alert('修改成功');
    							window.parent.location.href="../";
    						}
    					}
    				});
    			}
    	}
    	function passCheck(){
    		var oldP = $.trim($('#oldPass').val());
    		var newP = $.trim($('#newPass').val());
    		var checkP = $.trim($('#checkPass').val()); 
    		if(oldP == ''){
    			if(newP != '' || checkP != '' ){
    				alert('请输入旧密码');
    				//$.messager.alert('警告','请输入旧密码');
    				return false;
    			}
    		}else{
    			if(newP == ''){
    				alert('请输入新密码');
    				//$.messager.alert('警告','请输入新密码');
    				return false;
    			}
    			if(checkP == ''){
    				alert('请输入确认密码');
    				//$.messager.alert('警告','请输入确认密码');
    				return false;
    			}
    			if(newP != checkP){
    				alert('新密码2次输入不一致');
    				//$.messager.alert('警告','新密码2次输入不一致');
    				return false;
    			}
    			if(oldP == newP){
    				alert('新密码不能与旧密码一致');
    				//$.messager.alert('警告','新密码不能与旧密码一致');
    				return false;
    			}
    		}
    		return true;
    	}
    	function notChange(){
    		return ($.trim($('#cname').val()) == cnameFirst && $.trim($('#tel').val()) == telFirst && $.trim($('#email').val()) == emailFirst
    					&& $.trim($('#oldPass').val()) == '');
    	}
    </script>
  </body>
</html>
