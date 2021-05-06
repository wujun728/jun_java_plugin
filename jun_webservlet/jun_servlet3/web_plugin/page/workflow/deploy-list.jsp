<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
 	var searchForm;
 	var uploadDeployForm;
 	var uploadDeployDialog;
	var datagrid;
	$(function() {
	    //查询列表	
		datagrid = $('#datagrid').datagrid({
			url : 'jbpm4JeecgAction!datagridDeployList.action',
			title : '流程定义管理',
			iconCls : 'icon-save',
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'PDID',
			sortName : 'PDID',
			sortOrder : 'desc',
			rownumbers: true,
			columns : [ [ 
			{field:'PDID',title:'PDID',align:'center',sortable:true,
					formatter:function(value,row,index){
						return row.PDID;
					}
				},				
			   {field:'PNAME',title:'PNAME',align:'center',sortable:true,
					formatter:function(value,row,index){
						return row.PNAME;
					}
				},				
			   {field:'PID',title:'PID',align:'center',sortable:true,
					formatter:function(value,row,index){
						return row.PID;
					}
				},				
			   {field:'PKEY',title:'PKEY',align:'center',sortable:true,
					formatter:function(value,row,index){
						return row.PKEY;
					}
				}		
			 ] ],
			toolbar : [ {
				text : '发布流程',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			} ] 
		});

	function add() {
		uploadDeployDialog.dialog('open');
	}
	
	 uploadDeployForm = $('#uploadDeployForm').form({
			url : 'jbpm4JeecgAction!deploy.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json && json.success) {
					$.messager.show({
						title : '成功',
						msg : json.msg
					});
					datagrid.datagrid('reload');
					uploadDeployDialog.dialog('close');
				} else {
					$.messager.show({
						title : '失败',
						msg : json.msg
					});
				}
			}
		});

		uploadDeployDialog = $('#uploadDeployDialog').show().dialog({
			title : '上传',
			modal : true,
			closed : true,
			maximizable : true,
			buttons : [ {
				text : '添加',
				handler : function() {
					uploadDeployForm.submit();
				}
			} ]
		});


	});
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" title="过滤条件" collapsed="true"  style="height: 110px;overflow: hidden;display: none;" align="left">
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<th>查询字段需要手工修改</th>
					<td><input name="hotelid" style="width:155px;" /></td>
				</tr>
				<tr>
					<th>创建时间</th>
					<td><input name="ccreatedatetimeStart" class="easyui-datebox" editable="false" style="width: 155px;" />至<input name="ccreatedatetimeEnd" class="easyui-datebox" editable="false" style="width: 155px;" /></td>
				</tr>
				<tr>
					<th>最后修改时间</th>
					<td><input name="cmodifydatetimeStart" class="easyui-datebox" editable="false" style="width: 155px;" />至<input name="cmodifydatetimeEnd" class="easyui-datebox" editable="false" style="width: 155px;" /><a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div region="center" border="false">
		<table id="datagrid"></table>
	</div>
	
	<div id="uploadDeployDialog" style="display: none;width: 500px;height: 300px;" align="center">
		<form id="uploadDeployForm" method="post" enctype="multipart/form-data">
			<table class="tableForm">
				<tr>
					<th>上传文件</th>
					<td><input name="filedata" class="easyui-validatebox" required="true" type="file" missingMessage="请选择上传文件" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	
	<div id="iframeDialog" style="display: none;overflow: auto;width: 600px;height: 400px;">
	<iframe name="iframe" id="iframe" src="#"  scrolling="auto" frameborder="0" style="width:100%;height:100%;">
    </iframe>
</div>
</body>
</html>