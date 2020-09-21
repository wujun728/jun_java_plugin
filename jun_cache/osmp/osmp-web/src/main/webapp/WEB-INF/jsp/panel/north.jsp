<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="z" uri="/osmp-tags"%>
<div style="min-width:1180px; height:80px;">
	<div  style="width:100%; height:80px; font-size: 16px; background: url('${BASE_PATH}/images/page/top_background.png') repeat-x center">
		<div style="float: left;margin-top:15px;margin-left:15px;">
			osmp-web DEMO
		</div>
		<div style="float: right;margin-right:20px;margin-top:20px;text-align: right;">
			<div>
				<span style="color:#FFFFFF;font-size:11px">您好<a href="#" style="text-decoration:underline;color:red">${username}</a>,欢迎使用!</span>
			</div>
			<div>
			
				<a href="#" class="easyui-menubutton" style="color:#595757;font:12px '宋体'" menu="#mm1"	iconCls="icon-set">控制面板</a>
				<a href="javascript:void(0)" onclick="doLogout();" class="m-btn l-btn l-btn-plain" style="color:#595757;font:12px '宋体'">
					<span class="l-btn-left"><span class="l-btn-text icon-logout" style="padding-left: 20px;">退出系统</span></span>
				</a>
				
			</div>
		</div>
	</div>
</div>

<div id="mm1" style="width: 150px;">
	<div iconCls="icon-edit"><a onclick="grxx()">个人信息</a></div>
	<div class="menu-sep"></div>
<%--	<div iconCls="icon-undo"><a onclick="ghmm()">更换密码</a></div>--%>
	<div iconCls="icon-reload">
		<span>更换主题</span>
		<div style="width: 150px;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('cupertino');">cupertino</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('metro');">metro</div>
			<div onclick="changeTheme('dark-hive');">dark-hive</div>
			<div onclick="changeTheme('sunny');">sunny</div>
			<div onclick="changeTheme('pepper-grinder');">pepper-grinder</div>
		</div>
	</div>
	
</div>

<script type="text/javascript">
	function ghmm(){
		OpenPlainWindow('${BASE_PATH}/panel/updatePassword.do','350','200','修改密码','updateWindow');
	}
	
	function grxx(){
		addTab('个人信息','${BASE_PATH}/user/userinfo.do','');
	}
	
	function doLogout(){
		$.messager.confirm('提示','<b>确认退出系统?</b>',function(r){
			if(r) location.href='logout.do'
		});
	}
</script>