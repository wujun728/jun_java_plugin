<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include>
<jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	var editRow;
	var treegrid;
	var iconData = [ {
		iconcls : '',
		text : '默认图标'
	}, {
		iconcls : 'icon-add',
		text : 'icon-add'
	}, {
		iconcls : 'icon-edit',
		text : 'icon-edit'
	}, {
		iconcls : 'icon-remove',
		text : 'icon-remove'
	}, {
		iconcls : 'icon-save',
		text : 'icon-save'
	}, {
		iconcls : 'icon-cut',
		text : 'icon-cut'
	}, {
		iconcls : 'icon-ok',
		text : 'icon-ok'
	}, {
		iconcls : 'icon-no',
		text : 'icon-no'
	}, {
		iconcls : 'icon-cancel',
		text : 'icon-cancel'
	}, {
		iconcls : 'icon-reload',
		text : 'icon-reload'
	}, {
		iconcls : 'icon-search',
		text : 'icon-search'
	}, {
		iconcls : 'icon-print',
		text : 'icon-print'
	}, {
		iconcls : 'icon-help',
		text : 'icon-help'
	}, {
		iconcls : 'icon-undo',
		text : 'icon-undo'
	}, {
		iconcls : 'icon-redo',
		text : 'icon-redo'
	}, {
		iconcls : 'icon-back',
		text : 'icon-back'
	}, {
		iconcls : 'icon-sum',
		text : 'icon-sum'
	}, {
		iconcls : 'icon-tip',
		text : 'icon-tip'
	} ];
	$(function() {

		treegrid = $('#treegrid').treegrid({
			url : 'menuController.do?treegrid',
			toolbar : [ {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('expandAll', node.id);
					} else {
						treegrid.treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('collapseAll', node.id);
					} else {
						treegrid.treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					editRow = undefined;
					treegrid.treegrid('reload');
				}
			}, '-', {
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
						treegrid.treegrid('endEdit', editRow.id);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					if (editRow) {
						treegrid.treegrid('cancelEdit', editRow.id);
						editRow = undefined;
					}
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					treegrid.treegrid('unselectAll');
				}
			}, '-' ],
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : true,
			nowrap : true,
			animate : false,
			border : false,
			idField : 'id',
			treeField : 'text',
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				hidden : true
			}, {
				field : 'text',
				title : '菜单名称',
				width : 200,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [ {
				field : 'iconCls',
				title : '菜单图标',
				width : 150,
				editor : {
					type : 'combobox',
					options : {
						valueField : 'iconcls',
						textField : 'text',
						panelHeight : '200',
						data : iconData,
						formatter : function(v) {
							return sy.fs('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.iconcls, v.text);
						}
					}
				}
			}, {
				field : 'src',
				title : '菜单地址',
				width : 200,
				editor : {
					type : 'text'
				}
			}, {
				field : 'seq',
				title : '排序',
				width : 50,
				editor : {
					type : 'numberbox',
					options : {
						min : 0,
						max : 999,
						required : true
					}
				}
			}, {
				field : 'parentId',
				title : '上级菜单',
				width : 200,
				formatter : function(value, rowData, rowIndex) {
					return rowData.parentText;
				},
				editor : {
					type : 'combotree',
					options : {
						url : 'menuController.do?tree',
						animate : false,
						lines : !sy.isLessThanIe8(),
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
					}
				}
			}, {
				field : 'parentText',
				title : '上级菜单',
				width : 80,
				hidden : true
			} ] ],
			onDblClickRow : function(row) {
				if (editRow) {
					$.messager.show({
						msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
						title : '提示'
					});
				} else {
					treegrid.treegrid('beginEdit', row.id);
					editRow = row;
				}
			},
			onAfterEdit : function(row, changes) {
				if (row.parentId != row.id) {
					$.ajax({
						url : 'menuController.do?edit',
						data : row,
						cache : false,
						dataType : "json",
						success : function(r) {
							if (r.success) {
								$.messager.show({
									msg : r.msg,
									title : '提示'
								});
								treegrid.treegrid('reload');
								parent.tree.tree('reload');
								editRow = undefined;
							} else {
								$.messager.show({
									msg : '保存菜单失败!',
									title : '提示'
								});
							}
						}
					});
				} else {
					$.messager.show({
						msg : '保存失败，上级菜单不可以是自己!',
						title : '提示'
					});
				}
			},
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function(row, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.treegrid('expandAll');
						}
					});
				}
			},
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});

	});

	function edit() {
		if (editRow) {
			$.messager.show({
				msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
				title : '提示'
			});
		} else {
			var node = treegrid.treegrid('getSelected');
			if (node && node.id) {
				treegrid.treegrid('beginEdit', node.id);
				editRow = node;
			}
		}
	}
	function append() {
		if (editRow) {
			$.messager.show({
				msg : '您没有结束之前编辑的数据，请先保存或取消编辑！',
				title : '提示'
			});
		} else {
			var node = treegrid.treegrid('getSelected');
			var data = [ {
				id : sy.UUID(),
				text : '新菜单',
				src : '',
				seq : 999,
				parentId : (node ? node.id : '')
			} ];
			var opts = {
				parent : data[0].parentId,
				data : data
			};
			$.ajax({
				url : 'menuController.do?add',
				data : data[0],
				cache : false,
				dataType : "json",
				success : function(r) {
					if (r.success) {
						treegrid.treegrid('append', opts);

						treegrid.treegrid('beginEdit', data[0].id);
						editRow = data[0];
					} else {
						$.messager.show({
							msg : '添加菜单失败!',
							title : '提示'
						});
					}
				}
			});
		}
	}
	function remove() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			if (node.id == '0') {
				$.messager.show({
					msg : '不能删除根节点!',
					title : '提示'
				});
				return;
			}
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】菜单？', function(b) {
				if (b) {
					$.ajax({
						url : 'menuController.do?del',
						data : {
							id : node.id
						},
						cache : false,
						dataType : "json",
						success : function(r) {
							if (r.success) {
								treegrid.treegrid('reload');
								parent.tree.tree('reload');
								$.messager.show({
									msg : r.msg,
									title : '提示'
								});
								editRow = undefined;
							} else {
								$.messager.show({
									msg : '删除菜单失败!',
									title : '提示'
								});
							}
						}
					});
				}
			});
		}
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="treegrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" iconCls="icon-add">增加</div>
		<div onclick="remove();" iconCls="icon-remove">删除</div>
		<div onclick="edit();" iconCls="icon-edit">编辑</div>
	</div>
</body>
</html>