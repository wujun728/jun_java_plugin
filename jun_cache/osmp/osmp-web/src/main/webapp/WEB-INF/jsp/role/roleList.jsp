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
		function openPrivilegeWindow(title, href, width, height, modal, minimizable, maximizable) {

		    var $win;
		    $win = $('#myWindow').dialog({
		        title : title,
		        width : width === undefined ? 600 : width,
		        height : height === undefined ? 400 : height,
		        top : ($(window).height() - 420) * 0.5,
		        left : ($(window).width() - 350) * 0.5,

		        content : '<iframe id="zTreeMenu" name="zTreeMenu" scrolling="yes" frameborder="0" src="' + href + '" style="width:100%;height:98%;"></iframe>',
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
		        buttons : [ {
		        	text : '保存',
		            iconCls : "icon-save",
		            handler : function() {
		            	var $treeDemo = zTreeMenu.window;
		            	var param = $treeDemo.getCheckedMenu();
		            	var menuIds = param.menuIds;
		            	var rpIds = param.rpIds;
		            	var arr = [];
		            	for(var s in menuIds){
		            	    for(var x in rpIds){
		            	        if(menuIds[s]==rpIds[x]){
		            	            arr.push(menuIds[s]);
		            	        }
		            	    }
		            	}
		            	if(menuIds.length == rpIds.length &&arr.length == menuIds.length){
		            		$("#myWindow").dialog("close");
	                        $.messager.show({
	                            title : '提示',
	                            msg : '修改权限成功!'
	                        });
	                        return false;
		            	}
		    			$.getJSON(BASE_PATH+'/role/ajaxAddPrivilege.do?id='+param.roleId+'&menuIds='+param.menuIds,function(result){
		    				if (result.success) {
		    					$("#myWindow").dialog("close");
		                        $.messager.show({
		                            title : '提示',
		                            msg : '修改权限成功!'
		                        });
		                    } else {
		                        $.messager.show({
		                            title : '提示',
		                            msg : '修改权限失败!'
		                        });
		                    }
		    			}) ;
		            }
		        },{
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
		<z:dataGrid dataGridType="datagrid" name="roleList" actionUrl="${BASE_PATH}/role/roleList.do" title="角色列表" checkbox="false" showPageList="true">
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/role/toAdd.do','roleList','datagrid',500,300,'RoleAddForm','${BASE_PATH}/role/saveOrUpdate.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="角色ID" width="100" ></z:dataGridColumn>
			<z:dataGridColumn field="name" title="角色名称" width="50" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="status" title="状态" width="50" replace="正常_1,禁止_0"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="编辑" jsName="Edit('${BASE_PATH}/role/toEditRole.do?id=&quot;+rowData.id+&quot;','roleList','datagrid',300,200,'RoleUpdateForm','${BASE_PATH}/role/saveOrUpdate.do')"></z:dataGridOpt>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/role/deleteRole.do?id=&quot;+rowData.id+&quot;','roleList','datagrid')"></z:dataGridOpt>
			<z:dataGridOpt name="添加权限" jsName="openPrivilegeWindow('添加权限','${BASE_PATH}/role/toAddPrivilege.do?id=&quot;+rowData.id+&quot;','300','400','false','false')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
