<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var searchForm;
	var editRow = undefined;
	var datagrid;
	$(function() {

		searchForm = $('#searchForm').form();
		datagrid = $('#datagrid').datagrid({
			url : 'dictParamAction!datagrid.action',
			title : '数据字典列表',
			iconCls : 'icon-save',
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			fit : true,
			fitColumns : false,
			rownumbers : true,
			nowrap : false,
			border : false,
			idField : 'obid',
			sortName : 'paramLevel',
			sortOrder : 'desc',
			columns : [ [ 
				{field:'ck',checkbox:true,
						formatter:function(value,row,index){
							return row.obid;
						}
				},
			   {field:'paramLevelDec',title:'类型描述',align:'center',sortable:false,width : 85,
			  	    editor : {
							type : 'validatebox',
							options : {required:true}
					},
					formatter:function(value,row,index){
						return row.paramLevelDec;
					}
				},				
			   {field:'paramLevel',title:'类型KEY',align:'center',sortable:false,width : 85,
			  	    editor : {
							type : 'validatebox',
							options : {required:true}
					},
					formatter:function(value,row,index){
						return row.paramLevel;
					}
				},				
			   {field:'paramValue',title:'参数值',align:'center',sortable:false,width : 85,
			  	    editor : {
							type : 'validatebox',
							options : {required:true}
					},
					formatter:function(value,row,index){
						return row.paramValue;
					}
				},				
			   {field:'paramName',title:'参数名称',align:'center',sortable:false,width : 85,
			  	    editor : {
							type : 'validatebox',
							options : {required:true}
					},
					formatter:function(value,row,index){
						return row.paramName;
					}
				},				
			   {field:'remark',title:'备注',align:'center',sortable:false,width : 85,
			  	    editor : {
							type : 'validatebox',
							options : {}
					},
					formatter:function(value,row,index){
						return row.remark;
					}
				},				
			   {field:'paramViewOrder',title:'排序',align:'center',sortable:false,width : 85,
			  	    editor : {
							type : 'numberbox',
							options : {}
					},
					formatter:function(value,row,index){
						return row.paramViewOrder;
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
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					if (editRow != undefined) {
						datagrid.datagrid('endEdit', editRow);
					}
				}
			}, '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
					datagrid.datagrid('rejectChanges');
					editRow = undefined;
				}
			}, '-', {
				text : '取消选中',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-' ],
			onDblClickRow : function(rowIndex, rowData) {
				if (editRow != undefined) {
					datagrid.datagrid('endEdit', editRow);
				}

				if (editRow == undefined) {
					changeEditorEditRow();/*改变editor*/
					datagrid.datagrid('beginEdit', rowIndex);
					editRow = rowIndex;
					datagrid.datagrid('unselectAll');
				}
			},
			onAfterEdit : function(rowIndex, rowData, changes) {
				var inserted = datagrid.datagrid('getChanges', 'inserted');
				var updated = datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					datagrid.datagrid('unselectAll');
					return;
				}

				var url = '';
				if (inserted.length > 0) {
					url = 'dictParamAction!add.action';
				}
				if (updated.length > 0) {
					url = 'dictParamAction!edit.action';
				}

				$.ajax({
					url : url,
					data : rowData,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							datagrid.datagrid('acceptChanges');
							$.messager.show({
								msg : r.msg,
								title : '成功'
							});
							editRow = undefined;
							datagrid.datagrid('reload');
						} else {
							/*datagrid.datagrid('rejectChanges');*/
							datagrid.datagrid('beginEdit', editRow);
							$.messager.alert('错误', r.msg, 'error');
						}
						datagrid.datagrid('unselectAll');
					}
				});

			},
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

	function _search() {
		datagrid.datagrid('load', sy.serializeObject(searchForm));
	}
	function cleanSearch() {
		datagrid.datagrid('load', {});
		searchForm.find('input').val('');
	}
	
	function add() {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
		}

		if (editRow == undefined) {
			datagrid.datagrid('unselectAll');
			var row = {
				cid : sy.UUID()
			};
			/*datagrid.datagrid('insertRow', {
				index : 0,
				row : row
			});
			editRow = 0;
			datagrid.datagrid('selectRow', editRow);
			datagrid.datagrid('beginEdit', editRow);*/
			datagrid.datagrid('appendRow', row);
			editRow = datagrid.datagrid('getRows').length - 1;
			datagrid.datagrid('selectRow', editRow);
			datagrid.datagrid('beginEdit', editRow);
		}
	}
	function del() {
		if (editRow != undefined) {
			datagrid.datagrid('endEdit', editRow);
			return;
		}
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].obid);
					}
					$.ajax({
						url : 'dictParamAction!delete.action',
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
			if (editRow != undefined) {
				datagrid.datagrid('endEdit', editRow);
			}

			if (editRow == undefined) {
				editRow = datagrid.datagrid('getRowIndex', rows[0]);
				datagrid.datagrid('beginEdit', editRow);
				datagrid.datagrid('unselectAll');
			}
		} else {
			$.messager.show({
				msg : '请选择一项进行修改！',
				title : '错误'
			});
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" collapsed="true" title="搜索条件" style="height: 60px;overflow: hidden;" align="left">
		<form id="searchForm">
			<table>
				<tr>
					<td>类型KEY<input name="paramLevel" style="width:100px;" />&nbsp;</td>
					<td>创建时间<input name="ccreatedatetimeStart" class="easyui-datetimebox" editable="false" style="width: 100px;" />至<input name="ccreatedatetimeEnd" class="easyui-datetimebox" editable="false" style="width: 100px;" /></td>
					<td>最后修改时间</td>
					<td><input name="cmodifydatetimeStart" class="easyui-datetimebox" editable="false" style="width: 100px;" />至<input name="cmodifydatetimeEnd" class="easyui-datetimebox" editable="false" style="width: 100px;" /><a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">搜索</a><a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">取消</a></td>
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
</body>
</html>