<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/extend/extend.js"></script>
<script type="text/javascript" src="${ctx}/js/dbmap.js"></script>
<script type="text/javascript" src="${ctx}/js/core.js"></script>


<script type="text/javascript" charset="utf-8">
	function logout(b) {
		/*$.post('systemAction!logout.action', function() {
			if (b) {
				if (jqueryUtil.isLessThanIe8()) {
					loginAndRegDialog.dialog('open');
				} else {
						location.replace('login.jsp');
				}
			} else {
				loginAndRegDialog.dialog('open');
			}
		});*/

		$.messager.confirm("提示", "确认退出吗?", function(r) {
			if (r) {
				$.ajax({
					async : false,
					cache : false,
					type : "POST",
					url : "systemController.do?method=logout",
					error : function() {
					},
					success : function(json) {
						location.replace("login.jsp");
					}
				});
			}
		});

	}

	var userInfoWindow;
	function showUserInfo() {
		userInfoWindow = $('<div/>').window({
			modal : true,
			title : '当前用户信息',
			width : 350,
			height : 300,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			//href : 'userAction!showUserInfo.action',
			onClose : function() {
				$(this).window('destroy');
			}
		});
	}
</script>

<div class="c_m_navtitle"></div>
<div style="position: absolute; right: 5px; top: 10px;">
	<a href="javascript:void(0);" id="i_u_sys_btn">更换系统</a> 欢迎您！用户名:[<strong id="i_u_username"></strong>]角色：[<strong id="i_u_userrole"></strong><strong
		style="display: none;" id="i_u_userid"></strong>] IP:[<strong id="i_u_userip"></strong>] 现在:[<strong id="i_u_now"></strong>]
</div>
<div style="position: absolute; right: 200px; bottom: 0px;">
	<span id="i_m_topmenu"></span> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-user_suit',plain:true"
		onclick="userEditFun('${ctx}');">个人信息</a> <a href="javascript:void(0);" class="easyui-menubutton"
		data-options="menu:'#layout_north_pfMenu',iconCls:'icon-wand'">界面风格</a> <a href="javascript:void(0);" class="easyui-linkbutton" onclick="changeLayout();"
		data-options="iconCls:'icon-layers',plain:true">更换布局</a> <a href="javascript:void(0);" class="easyui-linkbutton" onclick="sysChart();" id="syschatbtn"
		data-options="iconCls:'icon-standard-comment',plain:true">即时通讯</a> <a href="javascript:void(0);" class="easyui-linkbutton" onclick="sysInfo();"
		data-options="iconCls:'icon-page_white_star',plain:true">关于</a> <a href="javascript:void(0);" class="easyui-linkbutton" onclick="logout('${ctx}');"
		data-options="iconCls:'icon-exclamation',plain:true">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeTheme_('${ctx}','black');">黑色</div>
	<div onclick="changeTheme_('${ctx}','bootstrap');">银蓝</div>
	<div onclick="changeTheme_('${ctx}','default');">天空蓝</div>
	<div onclick="changeTheme_('${ctx}','gray');">银色</div>
	<div onclick="changeTheme_('${ctx}','sunny');">阳光</div>
	<div onclick="changeTheme_('${ctx}','cupertino');">清泉</div>
	<div onclick="changeTheme_('${ctx}','pepper-grinder');">古典</div>
	<div onclick="changeTheme_('${ctx}','metro');">美俏</div>
	<div onclick="changeTheme_('${ctx}','metro-blue');">美俏-蓝</div>
	<div onclick="changeTheme_('${ctx}','metro-gray');">美俏-灰</div>
	<div onclick="changeTheme_('${ctx}','metro-green');">美俏-绿</div>
	<div onclick="changeTheme_('${ctx}','metro-orange');">美俏-橙</div>
	<div onclick="changeTheme_('${ctx}','metro-red');">美俏-红</div>
</div>




<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a> <a href="javascript:void(0);"
		class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-logout">注销</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="showUserInfo();">个人信息</div>
	<div class="menu-sep"></div>
	<div>
		<span>更换主题</span>
		<div style="width: 120px;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('cupertino');">cupertino</div>
			<div onclick="changeTheme('dark-hive');">dark-hive</div>
			<div onclick="changeTheme('pepper-grinder');">pepper-grinder</div>
			<div onclick="changeTheme('sunny');">sunny</div>
		</div>
	</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="loginAndRegDialog.dialog('open');">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logout();">重新登录</div>
	<div onclick="logout(true);">退出系统</div>
</div>


	<!-- chart start -->
	<div id="sy_chart_dialog" class="easyui-dialog"  
		data-options="closed:true,title:'即时通讯',iconCls:'icon-standard-comment'"  style="width: 400px; height: 400px;" align="center">
		<div class="easyui-layout" fit=true>
		<div data-options="region:'north',split:true" style="height:320px;">
		<div id="chartcontent" class="easyui-panel"     
        style="background:#fafafa;"  
        data-options="closable:false,   
                collapsible:false,minimizable:false,maximizable:false,fit:true"> 
 		</div>
		</div>
		<div data-options="region:'center'" style="padding:5px;background:#eee;">
		<input type="text" id="chatmsg" style="width:60%" /><input type="button" value="发送" id="chatbut" /><input type="button" value="清空" id="chatclear" />
		</div>  
		</div>
	</div>
	<!-- chart end -->
	
		<!-- sys start -->
	<div id="i_u_sys" style="display: none;">
	</div>
	<!-- sys end -->
	
	<!-- useredit start -->
	<!-- <div id="sy_user_edit_dialog" class="easyui-dialog"
		data-options="closed:true,title:'个人信息',buttons:[{
			text : '提交',
			iconCls : 'icon-save',
			handler : userdia
		}]"
		style="width: 400px; height: 210px;" align="center">
		<form id="sy_user_edit_form" method="post">
			<div style="padding: 10px 100px 10px 5px">
				<table width="100%">
					<tr>
						<td align="left" width="20%">登录名:</td>
						<td width="70%" style="text-align: left" align="left"><input
							name="syUser.userid" type="text" readonly="readonly"
							style="width: 70%"></input></td>
					</tr>
					<tr>
						<td align="left" width="20%">用户名：</td>
						<td width="70%" style="text-align: left" align="left"><input
							name="syUser.username" type="text" class="easyui-validatebox"
							data-options="required:true" style="width: 70%"></input></td>
					</tr>
					<tr>
						<td align="left" width="20%">旧密码:</td>
						<td width="70%" style="text-align: left" align="left"><input
							class="easyui-validatebox" type="password" name="oldPassword"
							data-options="required:true" style="width: 70%"></input></td>
					</tr>
					<tr>
						<td align="left" width="20%">新密码:</td>
						<td width="70%" style="text-align: left" align="left"><input
							type="password" name="syUser.password" style="width: 70%"></input>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div> -->
	<!-- useredit end -->
		
		
<script type="text/javascript">
	$(function() {
		$('#sy_user_edit_dialog').dialog('close');
		$('#sy_chart_dialog').dialog({onClose:function(){
			$('#syschatbtn').css("background-color",$('#i_m_header').css('background-color'));
		}});
		$('#chatclear').click(function(){
			$('#chartcontent').panel({
				content:''
			});
			$('.g_chart').remove();
		});
		$("#chatbut").click(function(){
			var msg=$("#chatmsg").val();
			msg="<div style='font-weight:700' class='g_chart'><span style='color:green'>"+getCurrentUserName()+'</span>:'+msg+"</div>";
			if(msg){SendMsg.sendMsg(msg);$("#chatmsg").val('')}
		});
		setSys(null);
		$('#i_u_username').text('JUN');
		$('#i_u_userid',top.document).text('00000000');
		$('#i_u_userrole',top.document).text('超级管理员');
		$('#i_u_userip',top.document).text('127.0.0.1');
		$('#i_u_now',top.document).text(new Date());
	});
	
	
	function setSys(d){
		var d=[
			{"checked":false,"children":[],"id":1,"projectInfo":"<p>JUN统一开发平台</p>","projectModule":"示例中心,平台管理,开发视图","projectName":"JUN-统一业务开发平台","projectUsed":1,"state":"open"},
			{"checked":false,"children":[],"id":2,"projectInfo":"","projectModule":"研发管理","projectName":"JUN-项目管理系统","state":"open"},
			{"checked":false,"children":[],"id":3,"projectInfo":"","projectModule":"研发管理","projectName":"JUN-企业资源管理系统","state":"open"},
			{"checked":false,"children":[],"id":4,"projectInfo":"","projectModule":"研发管理","projectName":"JUN-客户关系管理系统","state":"open"},
			{"checked":false,"children":[],"id":4,"projectInfo":"","projectModule":"研发管理","projectName":"JUN-人力资源管理系统","state":"open"}
			];
		var obj=$('#i_u_sys');
		var html='';
		var defproject_id=null;
		for(var i=0;i<d.length;i++){
			var project=d[i];
			if(project.projectUsed==1){
				defproject_id=project.projectName+'_sys';
			}
			html+='<div id="'+project.projectName+'_sys" onclick="changeSys(\''+project.projectName+'\',\''+project.projectModule+'\');">'+project.projectName+'</div>';
		}
		obj.html(html);
		$('#i_u_sys_btn').menubutton({   
		    iconCls: 'icon-database_go',   
		    menu: '#i_u_sys'  
		});
		if(defproject_id){$('#'+defproject_id).click();}
	}
	
	//初始化左侧
	var g_menuData = null;
	var g_sysmenuData={};
	function changeSys(text,modules){
		logt( '系统版本:0.6 beta</br>发布时间:2016/11/30</br>提示：<strong style="color:green">功能开发中</strong>', 3);
		/* main.layout('panel', 'west').panel({
			title: text
		});
		modules=','+modules+',';
		g_sysmenuData['menus']=new Array();
		g_sysmenuData['pid']=g_menuData.pid;
		$.each(g_menuData.menus,
				function(i, n) {
				if(modules.indexOf(','+n.menuname+',')!=-1){
					g_sysmenuData['menus'].push(n);
				}
		});
		if(!top.b_isleft) {topmenu(null);topmenu(g_sysmenuData);} return leftMenu(g_sysmenuData); */
	}
	

	function sysInfo() {
		logt( '系统版本:0.1 beta</br>发布时间:2014/5/23</br>使用建议：<strong style="color:green">建议使用IE8以上版本</strong>', 3);
	}
	
	function sysChart(){
		$('#syschatbtn').css("background-color",$('#i_m_header').css('background-color'));
		$('#sy_chart_dialog').dialog('open');
	}
	
	var b_isleft = true;
	function changeLayout() {
		if (b_isleft) {
			$('#main').layout('collapse', 'west');
			topmenu(g_sysmenuData);
			b_isleft = false;
		} else {
			$('#main').layout('expand', 'west');
			topmenu(null);
			b_isleft = true;
		}
		$('.tabs-selected').click();
	}
	
	

	function topmenu(data) {
		if (data == null) {
			if (g_menuData != null) {
				$.each(g_menuData.menus,
				function(i, n) {
					var obj=$('#' + n.menuid);
					if(obj.length==1){obj.menubutton('destroy');}
				});
			}
		}else{
			var obj = $('#i_m_topmenu');
			if(!data.menus) return;
			$.each(data.menus,
			function(i, n) {
				var menulist = '';
				menulist += '<a id="' + n.menuid + '" ref="' + n.menuid + '" type="nav_foot"  href="javascript:void(0);" class="easyui-menubutton" data-options="menu:\'#' + n.menuid + '_reflink\',iconCls:\'' + n.icon + '\'">' + n.menuname + '</a>';
				menulist += '<div id="' + n.menuid + '_reflink" style="width: 120px; display: none;">';
				$.each(n.menus,
				function(j, o) {
					menulist += '<a onclick="topmenuclick(this)" name="' + o.menuname + '" ref="' + o.menuid + '" type="nav_foot" cmshref="' + o.url + '" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:\'' + o.icon + '\',plain:true">' + o.menuname + '</a>';
				});
				menulist += '</div>';
				obj.append(menulist);
			});
			$.parser.parse(obj);
		}
	}

	function topmenuclick(obj) {
		var classId = 'index';
		var subtitle = $(obj).attr('name');
		var url = $(obj).attr('cmshref');
		var icon = $(obj).linkbutton('options').iconCls;
		//更新内容到右侧的tabs内容区
		if (!$('#i_u_tabs_' + classId).tabs('exists', subtitle)) {
			$('#i_u_tabs_' + classId).tabs('addIframeTab', {
				tab: {
					title: subtitle,
					closable: true,
					icon: icon,
					tools: [{
						iconCls: 'icon-mini-refresh',
						handler: function() {
							updateTab(classId, subtitle);
						}
					}]
				},
				iframe: {
					src: url
				}
			});
			top.currentTabTitle = subtitle;
			$('#i_u_tabs_' + classId).tabs('addEventParam');
			return false;
		} else {
			updateTab(classId, subtitle);
			return false;
		}
	}
	
	
	function userEditFun(path) {
		$("input[name='syUser.userid']").val(getCurrentUserId());
		$("input[name='syUser.username']").val(getCurrentUserName());
		$("input[name='oldPassword']").val('');
		$("input[name='syUser.password']").val('');
		$('#sy_user_edit_dialog').dialog({
			left: $(window).width() - 400,
			top: 60
		});
		$('#sy_user_edit_dialog').dialog('open');
	}
	
	
	function userdia() {
		/* $('#sy_user_edit_form').form('submit', {
			url : '${ctx}'+actionUrl('/sys/','syUser','Update_'),
			success : function(r) {
			if(r=='') return;
				var obj = jQuery.parseJSON(r);
				//setWeb();
				$('#sy_user_edit_dialog').dialog('close');
				//log(obj.info);
			}
		}); */
	}
	
</script>


