<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include>
<jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {

		userForm = $('#userForm').form();

		passwordInput = userForm.find('[name=password]').validatebox({
			required : true
		});

		$('[name=roleId]').combotree({
			url : 'roleController.do?tree',
			animate : false,
			lines : !sy.isLessThanIe8(),
			checkbox : true,
			multiple : true,
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			}
		});

		userRoleDialog = $('#userRoleDialog').show().dialog({
			modal : true,
			title : '批量编辑用户角色',
			buttons : [ {
				text : '编辑',
				handler : function() {
					userRoleForm.submit();
				}
			} ]
		}).dialog('close');

		userRoleForm = $('#userRoleForm').form({
			url : 'userController.do?editUsersRole',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d) {
					userRoleDialog.dialog('close');
					$.messager.show({
						msg : '批量修改用户角色成功！',
						title : '提示'
					});
					datagrid.datagrid('reload');
				}
			}
		});

		userDialog = $('#userDialog').show().dialog({
			modal : true,
			title : '用户信息',
			buttons : [ {
				text : '确定',
				handler : function() {
					if (userForm.find('[name=id]').val() != '') {
						userForm.form('submit', {
							url : 'userController.do?edit',
							success : function(data) {
								userDialog.dialog('close');
								$.messager.show({
									msg : '用户编辑成功！',
									title : '提示'
								});
								datagrid.datagrid('reload');
							}
						});
					} else {
						userForm.form('submit', {
							url : 'userController.do?add',
							success : function(data) {
								try {
									var d = $.parseJSON(data);
									if (d) {
										userDialog.dialog('close');
										$.messager.show({
											msg : '用户创建成功！',
											title : '提示'
										});
										datagrid.datagrid('reload');
									}
								} catch (e) {
									$.messager.show({
										msg : '用户名称已存在！',
										title : '提示'
									});
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');

		datagrid = $('#datagrid').datagrid({
			url : 'userController.do?datagrid',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				checkbox : true
			}, {
				field : 'name',
				title : '用户名称',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'password',
				title : '密码',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '******';
				}
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150,
				sortable : true
			}, {
				field : 'roleText',
				title : '角色',
				width : 200
			}, {
				field : 'roleId',
				title : '角色',
				width : 200,
				hidden : true
			} ] ],
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

	});

	function append() {
		userDialog.dialog('open');
		passwordInput.validatebox({
			required : true
		});
		userForm.find('[name=name]').removeAttr('readonly');
		userForm.form('clear');
	}

	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length != 1 && rows.length != 0) {
			var names = [];
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
			}
			$.messager.show({
				msg : '只能择一个用户进行编辑！您已经选择了【' + names.join(',') + '】' + rows.length + '个用户',
				title : '提示'
			});
		} else if (rows.length == 1) {
			passwordInput.validatebox({
				required : false
			});
			userForm.find('[name=name]').attr('readonly', 'readonly');
			userDialog.dialog('open');
			userForm.form('clear');
			userForm.form('load', {
				id : rows[0].id,
				name : rows[0].name,
				password : '',
				createdatetime : rows[0].createdatetime,
				modifydatetime : rows[0].modifydatetime,
				roleId : sy.getList(rows[0].roleId)
			});
		}
	}

	function editRole() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			userRoleForm.find('input[name=userIds]').val(ids.join(','));
			userRoleDialog.dialog('open');
		} else {
			$.messager.alert('提示', '请选择要编辑的记录！', 'error');
		}
	}

	function remove() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : 'userController.do?del',
						data : {
							ids : ids.join(',')
						},
						cache : false,
						dataType : "json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
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

	function searchFun() {
		datagrid.datagrid('load', {
			name : $('#toolbar input[name=name]').val(),
			createdatetimeStart : $('#toolbar input[comboname=createdatetimeStart]').datetimebox('getValue'),
			createdatetimeEnd : $('#toolbar input[comboname=createdatetimeEnd]').datetimebox('getValue'),
			modifydatetimeStart : $('#toolbar input[comboname=modifydatetimeStart]').datetimebox('getValue'),
			modifydatetimeEnd : $('#toolbar input[comboname=modifydatetimeEnd]').datetimebox('getValue')
		});
	}
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
			<fieldset>
				<legend>筛选</legend>
				<table class="tableForm">
					<tr>
						<th>用户名称</th>
						<td colspan="2"><input name="name" style="width: 305px;" />
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td colspan="2"><input name="createdatetimeStart" class="easyui-datetimebox" editable="false" style="width: 150px;" />-<input name="createdatetimeEnd" class="easyui-datetimebox" editable="false" style="width: 150px;" />
						</td>
					</tr>
					<tr>
						<th>最后修改时间</th>
						<td><input name="modifydatetimeStart" class="easyui-datetimebox" editable="false" style="width: 150px;" />-<input name="modifydatetimeEnd" class="easyui-datetimebox" editable="false" style="width: 150px;" /></td>
						<td><a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchFun();" href="javascript:void(0);">查找</a><a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="clearFun();" href="javascript:void(0);">清空</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<div>
				<a class="easyui-linkbutton" iconCls="icon-add" onclick="append();" plain="true" href="javascript:void(0);">增加</a> <a class="easyui-linkbutton" iconCls="icon-remove" onclick="remove();" plain="true" href="javascript:void(0);">删除</a> <a class="easyui-linkbutton" iconCls="icon-edit" onclick="edit();" plain="true" href="javascript:void(0);">编辑</a> <a class="easyui-linkbutton" iconCls="icon-edit" onclick="editRole();" plain="true" href="javascript:void(0);">批量更改角色</a> <a class="easyui-linkbutton" iconCls="icon-undo" onclick="datagrid.datagrid('unselectAll');" plain="true" href="javascript:void(0);">取消选中</a>
			</div>
		</div>
		<table id="datagrid"></table>
	</div>

	<div id="userDialog" style="display: none;overflow: hidden;">
		<form id="userForm" method="post">
			<table class="tableForm">
				<tr>
					<th>用户ID</th>
					<td><input name="id" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>登录名称</th>
					<td><input name="name" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr title="如果不更改密码,请留空!">
					<th>登录密码</th>
					<td><input name="password" type="password" /></td>
				</tr>
				<tr>
					<th>创建时间</th>
					<td><input name="createdatetime" class="easyui-datetimebox" style="width: 156px;" /></td>
				</tr>
				<tr>
					<th>最后修改时间</th>
					<td><input name="modifydatetime" class="easyui-datetimebox" style="width: 156px;" /></td>
				</tr>
				<tr>
					<th>所属角色</th>
					<td><select name="roleId" style="width: 156px;"></select></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="userRoleDialog" style="display: none;overflow: hidden;">
		<form id="userRoleForm" method="post">
			<table class="tableForm">
				<input type="hidden" name="userIds" />
				<tr>
					<th>所属角色</th>
					<td><select name="roleId" style="width: 156px;"></select>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
</body>
</html>