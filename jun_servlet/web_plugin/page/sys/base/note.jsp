<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
    var searchForm;
	var datagrid;
	var noteAddDialog;
	var noteAddForm;
	var cdescAdd;
	var noteEditDialog;
	var noteEditForm;
	var cdescEdit;
	var showCdescDialog;
	var iframeDialog;
	$(function() {
	    //查询列表	
	    searchForm = $('#searchForm').form();
		datagrid = $('#datagrid').datagrid({
			url : 'noteAction!datagrid.action',
			title : '公告管理列表',
			iconCls : 'icon-save',
			pagination : true,
			pagePosition : 'bottom',
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'obid',
			sortName : 'createDt',
			sortOrder : 'desc',
			columns : [ [ 
			{field:'ck',checkbox:true,
						formatter:function(value,row,index){
							return row.obid;
						}
					},
			   {field:'title',title:'标题',align:'center',sortable:true,
					formatter:function(value,row,index){
						return row.title;
					}
				},				
			   {field:'content',title:'内容',align:'center',sortable:true,
					formatter:function(value,row,index){
						return '<span class="icon-search" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span><a href="javascript:void(0);" onclick="showCdesc(' + index + ');">查看详细</a>';
					}
				}				
			 ] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					add();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					del();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-' ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});

		noteAddForm = $('#noteAddForm').form({
			url : 'noteAction!add.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json && json.success) {
					$.messager.show({
						title : '成功',
						msg : json.msg
					});
					datagrid.datagrid('reload');
					noteAddDialog.dialog('close');
				} else {
					$.messager.show({
						title : '失败',
						msg : '操作失败！'
					});
				}
			}
		});

		noteAddDialog = $('#noteAddDialog').show().dialog({
			title : '添加公告管理',
			modal : true,
			closed : true,
			maximizable : true,
			buttons : [ {
				text : '添加',
				handler : function() {
					noteAddForm.submit();
				}
			} ]
		});

		cdescAdd = $('#cdescAdd').xheditor({
			tools : 'mini',
			html5Upload : true,
			upMultiple : 4,
			upLinkUrl : 'noteAction!upload.action',
			upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
			upImgUrl : 'noteAction!upload.action',
			upImgExt : 'jpg,jpeg,gif,png'
		});

		noteEditForm = $('#noteEditForm').form({
			url : 'noteAction!edit.action',
			success : function(data) {
				var json = $.parseJSON(data);
				if (json && json.success) {
					$.messager.show({
						title : '成功',
						msg : json.msg
					});
					datagrid.datagrid('reload');
					noteEditDialog.dialog('close');
				} else {
					$.messager.show({
						title : '失败',
						msg : '操作失败！'
					});
				}
			}
		});

		noteEditDialog = $('#noteEditDialog').show().dialog({
			title : '编辑公告管理',
			modal : true,
			closed : true,
			maximizable : true,
			buttons : [ {
				text : '编辑',
				handler : function() {
					noteEditForm.submit();
				}
			} ]
		});

		cdescEdit = $('#cdescEdit').xheditor({
			tools : 'mini',
			html5Upload : true,
			upMultiple : 4,
			upLinkUrl : 'noteAction!upload.action',
			upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
			upImgUrl : 'noteAction!upload.action',
			upImgExt : 'jpg,jpeg,gif,png'
		});

		showCdescDialog = $('#showCdescDialog').show().dialog({
			title : '公告管理描述',
			modal : true,
			closed : true,
			maximizable : true
		});

		iframeDialog = $('#iframeDialog').show().dialog({
			title : 'POP弹出页面',
			modal : true,
			closed : true,
			maximizable : true
		});
	});

	function _search() {
		datagrid.datagrid('load', sy.serializeObject(searchForm));
	}
	function cleanSearch() {
		datagrid.datagrid('load', {});
		searchForm.find('input').val('');
	}
	function add() {
		noteAddForm.find('input,textarea').val('');
		$('div.validatebox-tip').remove();
		noteAddDialog.dialog('open');
	}
	function del() {
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].obid);
					}
					$.ajax({
						url : 'noteAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(response) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : '删除成功！'
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			$.messager.progress({
				text : '数据加载中....',
				interval : 100
			});
			$.ajax({
				url : 'noteAction!showDesc.action',
				data : {
					obid : rows[0].obid
				},
				dataType : 'json',
				cache : false,
				success : function(response) {
					noteEditForm.find('input,textarea').val('');
					noteEditForm.form('load', response);
					$('div.validatebox-tip').remove();
					noteEditDialog.dialog('open');
					$.messager.progress('close');
				}
			});
		} else {
			$.messager.alert('提示', '请选择一项要编辑的记录！', 'error');
		}
	}
	function showCdesc(index) {
		var rows = datagrid.datagrid('getRows');
		var row = rows[index];
		$.messager.progress({
			text : '数据加载中....',
			interval : 100
		});
		$.ajax({
			url : 'noteAction!showDesc.action',
			data : {
				obid : row.obid
			},
			dataType : 'json',
			cache : false,
			success : function(response) {
				if (response && response.content) {
					showCdescDialog.find('div[name=cdesc]').html(response.content);
					showCdescDialog.dialog('open');
				} else {
					$.messager.alert('提示', '没有公告管理描述！', 'error');
				}
				$.messager.progress('close');
			}
		});
		datagrid.datagrid('unselectAll');
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" title="过滤条件" collapsed="true"  style="height: 60px;overflow: hidden;display: none;" align="left">
		<form id="searchForm">
			<table class="tableForm datagrid-toolbar" style="width: 100%;height: 100%;">
				<tr>
					<td>标题</td>
					<td><input name="title" style="width:120px;" /></td>
					<td>内容</td>
					<td><input name="content" style="width:120px;" /><td>
				    <td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div region="center" border="false">
		<table id="datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" iconCls="icon-add">增加</div>
		<div onclick="del();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>

	<div id="noteAddDialog" style="display: none;width: 500px;height: 300px;" align="center">
		<form id="noteAddForm" method="post">
			<table class="tableForm">
							<tr>
								<td>标题</td>
								<td>
									<input name="title" type="text" maxlength="100" class="easyui-validatebox" data-options="required:true" missingMessage="请填写标题"  style="width: 155px;"/>
								</td>
							</tr>
							<tr>
								<td>内容</td>
								<td>
									<textarea name="content" id="cdescAdd"></textarea>
								</td>
							</tr>
			</table>
		</form>
	</div>

	<div id="noteEditDialog" style="display: none;width: 500px;height: 300px;" align="center">
		<form id="noteEditForm" method="post">
			<input type="hidden" name="obid" />
			<table class="tableForm">
							<tr>
								<td>标题</td>
								<td>
								<input name="title" type="text" maxlength="100" class="easyui-validatebox" data-options="required:true" missingMessage="请填写标题"  style="width: 155px;"/>
								</td>
							</tr>
							<tr>
								<td>内容</td>
								<td>
									<textarea name="content" id="cdescEdit"></textarea>
								</td>
							</tr>
			</table>
		</form>
	</div>

	<div id="showCdescDialog" style="display: none;overflow: auto;width: 500px;height: 400px;">
		<div name="cdesc"></div>
	</div>
	
	<div id="iframeDialog" style="display: none;overflow: auto;width: 600px;height: 400px;">
	<iframe name="iframe" id="iframe" src="#"  scrolling="auto" frameborder="0" style="width:100%;height:100%;">
    </iframe>
</div>
</body>
</html>