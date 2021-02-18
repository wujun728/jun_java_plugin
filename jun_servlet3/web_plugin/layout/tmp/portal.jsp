<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include>
<jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var editRow;
	$(function() {

		datagrid = $('#datagrid').datagrid({
			url : 'portalController.do?datagrid',
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect : true,
			sortName : 'seq',
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (editRow) {
						datagrid.datagrid('endEdit', editRow);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					if (editRow) {
						datagrid.datagrid('cancelEdit', editRow);
						editRow = undefined;
					}
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-' ],
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				hidden : true
			}, {
				field : 'title',
				title : '名称',
				width : 100,
				sortable : true,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [ {
				field : 'src',
				title : '地址',
				width : 200,
				sortable : true,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			}, {
				field : 'height',
				title : '面板高度',
				width : 60,
				sortable : true,
				editor : {
					type : 'numberbox',
					options : {
						min : 0,
						max : 999,
						required : true
					}
				}
			}, {
				field : 'closable',
				title : '关闭按钮',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					if (value == 1) {
						return '显示';
					} else {
						return '不显示';
					}
				},
				editor : {
					type : 'combobox',
					options : {
						valueField : 'id',
						textField : 'text',
						panelHeight : 'auto',
						editable : false,
						data : [ {
							id : '0',
							text : '不显示'
						}, {
							id : '1',
							text : '显示'
						} ]
					}
				}
			}, {
				field : 'collapsible',
				title : '折叠按钮',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					if (value == 1) {
						return '显示';
					} else {
						return '不显示';
					}
				},
				editor : {
					type : 'combobox',
					options : {
						valueField : 'id',
						textField : 'text',
						panelHeight : 'auto',
						editable : false,
						data : [ {
							id : '0',
							text : '不显示'
						}, {
							id : '1',
							text : '显示'
						} ]
					}
				}
			}, {
				field : 'seq',
				title : '排序',
				width : 50,
				sortable : true,
				editor : {
					type : 'numberbox',
					options : {
						min : 0,
						max : 999,
						required : true
					}
				}
			} ] ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onDblClickRow : function(rowIndex, rowData) {
				if (editRow) {
					$.messager.show({
						msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
						title : '提示'
					});
				} else {
					datagrid.datagrid('beginEdit', rowIndex);
					editRow = rowIndex;
				}
			},
			onAfterEdit : function(rowIndex, rowData, changes) {
				$.ajax({
					url : 'portalController.do?edit',
					data : rowData,
					cache : false,
					dataType : "json",
					success : function(r) {
						if (r.success) {
							$.messager.show({
								msg : r.msg,
								title : '提示'
							});
							datagrid.datagrid('reload');
							editRow = undefined;
						} else {
							$.messager.show({
								msg : '保存失败!',
								title : '提示'
							});
						}
					}
				});
			}
		});

	});

	function append() {
		if (editRow) {
			$.messager.show({
				msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
				title : '提示'
			});
		} else {
			var data = {
				id : sy.UUID(),
				seq : 999,
				height : 150,
				src : '地址',
				title : '标题',
				closable : 1,
				collapsible : 1
			};
			$.ajax({
				url : 'portalController.do?add',
				data : data,
				cache : false,
				dataType : "json",
				success : function(response) {
					datagrid.datagrid('appendRow', data);
					editRow = datagrid.datagrid('getRows').length - 1;
					datagrid.datagrid('selectRow', editRow);
					datagrid.datagrid('beginEdit', editRow);
				}
			});
		}
	}

	function edit() {
		if (editRow) {
			$.messager.show({
				msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
				title : '提示'
			});
		} else {
			var rowIndex = datagrid.datagrid('getRowIndex', datagrid.datagrid('getSelected'));
			datagrid.datagrid('beginEdit', rowIndex);
			editRow = rowIndex;
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
						url : 'portalController.do?del',
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
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
</body>
</html>