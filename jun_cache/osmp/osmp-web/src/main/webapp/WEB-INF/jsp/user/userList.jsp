<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
	<script type="text/javascript">
		function dateformat(index,row,ss){
			var temp = zznode.util.dateFormatter(new Date(index),'yyyy-MM-dd hh:mm:ss');
			return temp;
		}
		function openRoleWindow(title, href, width, height, modal, minimizable, maximizable) {

		    var $win;
		    $win = $('#myWindow').dialog({
		        title : title,
		        width : width === undefined ? 600 : width,
		        height : height === undefined ? 400 : height,
		        top : ($(window).height() - 420) * 0.5,
		        left : ($(window).width() - 350) * 0.5,

		        content : '<iframe id="roleDataGrid" name="roleDataGrid" scrolling="yes" frameborder="0" src="' + href + '" style="width:100%;height:98%;"></iframe>',
		        // href : href === undefined ? null : href,
		        modal : modal === undefined ? true : modal,
		        minimizable : minimizable === undefined ? false : minimizable,
		        maximizable : maximizable === undefined ? false : maximizable,
		        shadow : true,
		        cache : false,
		        closed : false,
		        collapsible : false,
		        draggable : true,
		        resizable : true,
		        loadingMessage : '正在加载数据，请稍等片刻......',
		        buttons : [{
		        	text : '确认分配',
		            iconCls : "icon-save",
		            handler : function() {
		            	var $roleDataGrid = roleDataGrid.window;
		            	var params = $roleDataGrid.getCheckedRole();
		            	var ids = params.ids;
		            	var userId = params.userId;
		    			$.getJSON(BASE_PATH+'/user/ajaxAddUserRole.do?id='+params.userId+'&roleIds='+params.ids,function(result){
		    				if (result.success) {
		    					$("#myWindow").dialog("close");
		                        $.messager.show({
		                            title : '提示',
		                            msg : '修改用户角色成功!'
		                        });
		                    } else {
		                        $.messager.show({
		                            title : '提示',
		                            msg : '修改用户角色失败!'
		                        });
		                    }
		    			}) ; 
		            }
		        }, {
		            text : '关闭',
		            iconCls : "icon-add",
		            handler : function() {
		                $("#myWindow").dialog("close");
		            }
		        }]
		    });
		}
	</script>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="userList" actionUrl="${BASE_PATH}/user/userList.do" title="用户列表" checkbox="false" showPageList="true">
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/user/toAdd.do','userList','datagrid',500,300,'UserAddForm','${BASE_PATH}/user/saveOrUpdate.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="用户ID" width="100" ></z:dataGridColumn>
			<z:dataGridColumn field="name" title="用户名" width="30" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="realName" title="真实姓名" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="nickName" title="昵称" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="status" title="状态" width="20" replace="正常_1,禁止_0"></z:dataGridColumn>
			<z:dataGridColumn field="createTime" title="创建时间" width="50" formatter="dateformat"></z:dataGridColumn>
			<z:dataGridColumn field="lastUpdateTime" title="最新更新时间" width="50" formatter="dateformat"></z:dataGridColumn>
			<z:dataGridColumn field="lastLoginIp" title="最后登录IP" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/user/deleteUser.do?id=&quot;+rowData.id+&quot;','userList','datagrid')"></z:dataGridOpt>
			<z:dataGridOpt name="编辑" jsName="Edit('${BASE_PATH}/user/toEditUser.do?id=&quot;+rowData.id+&quot;','userList','datagrid',300,200,'UserUpdateForm','${BASE_PATH}/user/saveOrUpdate.do')"></z:dataGridOpt>
			<z:dataGridOpt name="赋予角色" jsName="openRoleWindow('赋予角色','${BASE_PATH}/user/toAddRole.do?id=&quot;+rowData.id+&quot;','400','400','false','false')"></z:dataGridOpt>
			<z:dataGridOpt name="拥有角色" jsName="openwindow('拥有的角色','${BASE_PATH}/user/toGetUserRole.do?id=&quot;+rowData.id+&quot;')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
