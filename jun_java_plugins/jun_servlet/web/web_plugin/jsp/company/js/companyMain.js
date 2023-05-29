var $dg;
var $grid;
$(function() {
	$dg = $("#dg");
	$grid = $dg.datagrid({
		url : "companyInfoController.do?method=findAllCompanyInfoList",
		width : 'auto',
		height : $(this).height() - 85,
		pagination : true,
		rownumbers : true,
		border : true,
		striped : true,
		singleSelect : true,
		columns : [ [ {
			field : 'name',
			title : '公司名称',
			width : parseInt($(this).width() * 0.1),
			editor : {
				type : 'validatebox',
				options : {
					required : true
				}
			}
		}, {
			field : 'tel',
			title : '公司电话',
			width : parseInt($(this).width() * 0.1),
			editor : "validatebox"
		}, {
			field : 'fax',
			title : '传真',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			editor : "text"
		}, {
			field : 'address',
			title : '地址',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			editor : "text"
		}, {
			field : 'zip',
			title : '邮政编码',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			editor : "text"
		}, {
			field : 'email',
			title : '邮箱',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : 'email'
				}
			}
		}, {
			field : 'contact',
			title : '联系人',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			editor : "text"
		}, {
			field : 'description',
			title : '描述',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			editor : "text"
		} ] ],
		toolbar : '#tb'
	});
	// 搜索框
	$("#searchbox").searchbox({
		menu : "#mm",
		prompt : '模糊查询',
		searcher : function(value, name) {
			var str = "{\"searchName\":\"" + name + "\",\"searchValue\":\"" + value + "\"}";
			var obj = eval('(' + str + ')');
			$dg.datagrid('reload', obj);
		}

	});
});
function endEdit() {
	var flag = true;
	var rows = $dg.datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		$dg.datagrid('endEdit', i);
		var temp = $dg.datagrid('validateRow', i);
		if (!temp) {
			flag = false;
		}
	}
	return flag;
}
function addRows() {
	$dg.datagrid('appendRow', {});
	var rows = $dg.datagrid('getRows');
	$dg.datagrid('beginEdit', rows.length - 1);
}
function editRows() {
	var rows = $dg.datagrid('getSelections');
	$.each(rows, function(i, row) {
		if (row) {
			var rowIndex = $dg.datagrid('getRowIndex', row);
			$dg.datagrid('beginEdit', rowIndex);
		}
	});
}
function removeRows() {
	var rows = $dg.datagrid('getSelections');
	$.each(rows, function(i, row) {
		if (row) {
			var rowIndex = $dg.datagrid('getRowIndex', row);
			$dg.datagrid('deleteRow', rowIndex);
		}
	});
}
function saveRows() {
	if (endEdit()) {
		if ($dg.datagrid('getChanges').length) {
			var inserted = $dg.datagrid('getChanges', "inserted");
			var deleted = $dg.datagrid('getChanges', "deleted");
			var updated = $dg.datagrid('getChanges', "updated");

			var effectRow = new Object();
			if (inserted.length) {
				effectRow["inserted"] = JSON.stringify(inserted);
			}
			if (deleted.length) {
				effectRow["deleted"] = JSON.stringify(deleted);
			}
			if (updated.length) {
				effectRow["updated"] = JSON.stringify(updated);
			}
			$.post("companyInfoController.do?method=persistenceCompanyInfo", effectRow, function(rsp) {
				if (rsp.status) {
					$dg.datagrid('acceptChanges');
				}
				$.messager.alert(rsp.title, rsp.message);
			}, "JSON").error(function() {
				$.messager.alert("提示", "提交错误了！");
			});
		}
	} else {
		$.messager.alert("提示", "字段验证未通过!请查看");
	}
}
// 删除
function delRows() {
	var row = $dg.datagrid('getSelected');
	if (row) {
		var rowIndex = $dg.datagrid('getRowIndex', row);
		parent.$.messager.confirm("提示", "确定要删除记录吗?", function(r) {
			if (r) {
				$dg.datagrid('deleteRow', rowIndex);
				$.ajax({
					url : "companyInfoController.do?method=delCompanyInfo",
					data : "companyId=" + row.companyId,
					success : function(rsp) {
						parent.$.messager.show({
							title : rsp.title,
							msg : rsp.message,
							timeout : 1000 * 2
						});
					}
				});
			}
		});
	} else {
		parent.$.messager.show({
			title : "提示",
			msg : "请选择一行记录!",
			timeout : 1000 * 2
		});
	}
}
// 弹窗修改
function updRowsOpenDlg() {
	var row = $dg.datagrid('getSelected');
	if (row) {
		parent.$.modalDialog({
			title : '编辑公司',
			width : 600,
			height : 400,
			href : "jsp/company/companyEditDlg.jsp",
			onLoad : function() {
				var f = parent.$.modalDialog.handler.find("#form");
				f.form("load", row);
			},
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-ok',
				handler : function() {
					parent.$.modalDialog.openner = $grid;// 因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find("#form");
					f.submit();
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			} ]
		});
	} else {
		parent.$.messager.show({
			title : "提示",
			msg : "请选择一行记录!",
			timeout : 1000 * 2
		});
	}
}
// 弹窗增加公司
function addRowsOpenDlg() {
	parent.$.modalDialog({
		title : '添加公司',
		width : 600,
		height : 400,
		href : 'jsp/company/companyEditDlg.jsp',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				parent.$.modalDialog.openner = $grid;// 因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
				var f = parent.$.modalDialog.handler.find("#form");
				f.submit();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				parent.$.modalDialog.handler.dialog('destroy');
				parent.$.modalDialog.handler = undefined;
			}
		} ]
	});
}

// 高级搜索 删除 row
function tbCompanySearchRemove(curr) {
	$(curr).closest('tr').remove();
}
// 高级查询
function tbsCompanySearch() {
	jqueryUtil.gradeSearch($dg, "#tbCompanySearchFm", "jsp/company/companySearchDlg.jsp");
}
// excel导出
function exportExcel() {
	var rows = $dg.datagrid("getRows");
	if (rows.length) {
		var isCheckedIds = [];
		$.each(rows, function(i, row) {
			if (row) {
				isCheckedIds.push(row.companyId);
			}
		});
		window.location.href = "<%=basePath%>excelController.do?method=CompanyInfoExcelExport&isCheckedIds=" + isCheckedIds;
	} else {
		parent.$.messager.show({
			title : "提示",
			msg : "暂无导出数据!",
			timeout : 1000 * 2
		});
	}
}
