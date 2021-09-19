<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var editRow;
	var editType;/*add or edit or undefined*/
	var treegrid;
	$(function() {

		treegrid = $('#treegrid').treegrid({
			url : 'authAction!treegrid.action',
			toolbar : [ {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('expandAll', node.cid);
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
						treegrid.treegrid('collapseAll', node.cid);
					} else {
						treegrid.treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					editRow = undefined;
					editType = undefined;
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
					if (editRow != undefined) {
						treegrid.treegrid('endEdit', editRow.cid);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					if (editRow) {
						treegrid.treegrid('cancelEdit', editRow.cid);
						var p = treegrid.treegrid('getParent', editRow.cid);
						if (p) {
							treegrid.treegrid('reload', p.cid);
						} else {
							treegrid.treegrid('reload');
						}
						editRow = undefined;
						editType = undefined;
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
			fitColumns : false,
			nowrap : false,
			animate : false,
			border : false,
			idField : 'cid',
			treeField : 'cname',
			frozenColumns : [ [ {
				title : 'cid',
				field : 'cid',
				width : 150,
				hidden : true
			}, {
				field : 'cname',
				title : '权限名称',
				width : 200,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				},
				formatter : function(value) {
					if (value) {
						return sy.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			} ] ],
			columns : [ [ {
				field : 'curl',
				title : '权限地址',
				width : 250,
				editor : {
					type : 'text'
				},
				formatter : function(value) {
					if (value) {
						return sy.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				field : 'cdesc',
				title : '描述',
				width : 200,
				editor : {
					type : 'text'
				},
				formatter : function(value) {
					if (value) {
						return sy.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				field : 'cseq',
				title : '排序',
				width : 150,
				editor : {
					type : 'numberbox',
					options : {
						min : 0,
						max : 999,
						required : true
					}
				}
			}, {
				field : 'cpid',
				title : '上级权限',
				width : 150,
				formatter : function(value, rowData, rowIndex) {
					return rowData.cpname;
				},
				editor : {
					type : 'combotree',
					options : {
						url : 'authAction!authTreeRecursive.action?ctype=02',
						animate : false,
						lines : !sy.isLessThanIe8(),
						onLoadSuccess : function(row, data) {
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
				field : 'cpname',
				title : '上级权限',
				width : 150,
				hidden : true
			} ] ],
			onDblClickRow : function(row) {
				if (editRow != undefined) {
					treegrid.treegrid('endEdit', editRow.cid);
				}

				if (editRow == undefined) {
					treegrid.treegrid('beginEdit', row.cid);
					editRow = row;
					editType = 'edit';
					treegrid.treegrid('unselectAll');
				}
			},
			onAfterEdit : function(row, changes) {
				if (editType == undefined) {
					editRow = undefined;
					treegrid.treegrid('unselectAll');
					return;
				}

				var url = '';
				if (editType == 'add') {
					url = 'authAction!add.action';
				}
				if (editType == 'edit') {
					url = 'authAction!edit.action';
				}

				$.ajax({
					url : url,
					data : row,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							treegrid.treegrid('reload');

							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							editRow = undefined;
							editType = undefined;
						} else {
							/*datagrid.datagrid('rejectChanges');*/
							treegrid.treegrid('beginEdit', editRow.cid);
							$.messager.alert('错误', r.msg, 'error');
						}
						treegrid.treegrid('unselectAll');
					}
				});

			},
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.cid);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function(row, data) {
				/*var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.treegrid('expandAll');
						}
					});
				}*/
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
		var node = treegrid.treegrid('getSelected');
		if (node && node.cid) {
			if (editRow != undefined) {
				treegrid.treegrid('endEdit', editRow.cid);
			}

			if (editRow == undefined) {
				treegrid.treegrid('beginEdit', node.cid);
				editRow = node;
				editType = 'edit';
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改！',
				title : '错误'
			});
		}
	}
	function append() {
		if (editRow != undefined) {
			treegrid.treegrid('endEdit', editRow.cid);
		}

		if (editRow == undefined) {
			var node = treegrid.treegrid('getSelected');
			var row = [ {
				cid : sy.UUID(),
				cname : '权限名称',
				curl : '',
				cseq : 10,
				cdesc : '描述信息',
				cpid : node == null ? '' : node.cid
			} ];
			treegrid.treegrid('append', {
				parent : node == null ? '' : node.cid,
				data : row
			});

			editRow = row[0];
			editType = 'add';
			treegrid.treegrid('select', editRow.cid);
			treegrid.treegrid('beginEdit', editRow.cid);
		}
	}
	function remove() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.cname + '】？', function(b) {
				if (b) {
					$.ajax({
						url : 'authAction!delete.action',
						data : {
							cid : node.cid
						},
						cache : false,
						dataType : "json",
						success : function(r) {
							if (r.success) {
								treegrid.treegrid('remove', r.obj);
								$.messager.show({
									msg : r.msg,
									title : '提示'
								});
								editRow = undefined;
							} else {
								$.messager.show({
									msg : '删除失败!',
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