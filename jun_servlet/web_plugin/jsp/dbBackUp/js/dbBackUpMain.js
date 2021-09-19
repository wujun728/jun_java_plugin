var $dg;
var $grid;
$(function() {
	$dg = $("#dg");
	$grid = $dg.datagrid({
		url : "dbBackUpController.do?method=findDbBackUpAllList",
		width : 'auto',
		height : $(this).height() - 85,
		pagination : true,
		rownumbers : true,
		border : true,
		striped : true,
		singleSelect : true,
		columns : [ [ {
			field : 'name',
			title : '操作用户',
			width : parseInt($(this).width() * 0.1)
		}, {
			field : 'ip',
			title : 'IP地址',
			width : parseInt($(this).width() * 0.1)
		}, {
			field : 'mac',
			title : '物理地址',
			width : parseInt($(this).width() * 0.1)
		}, {
			field : 'logDate',
			title : '备份日期',
			width : parseInt($(this).width() * 0.1)
		}, {
			field : 'type',
			title : '日志类型',
			width : parseInt($(this).width() * 0.1),
			align : 'left',
			formatter : function(value, row) {
				if ("1" == row.type)
					return "<font color=green>安全日志<font>";
				else
					return "<font color=red>操作日志<font>";
			}
		}, {
			field : 'objectId',
			title : '备份文件名称',
			width : parseInt($(this).width() * 0.3),
			align : 'left',
			formatter : function(value, row) {
				return "<a href='javascript:void(0);' id='backUpA' onclick='downBackUpFile(\"" + row.objectId + "\")'>" + row.objectId + "</a>";
			}
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

function downBackUpFile(fileName) {
	$.ajax({
		url : "dbBackUpController.do?method=checkBackUp",
		type : "POST",
		data : "fileName=" + fileName,
		beforeSend : function() {
			parent.$.messager.progress({
				title : '提示',
				text : '正在压缩数据，请稍后....'
			});
		},
		complete : function() {
			parent.$.messager.progress('close');
		},
		success : function(res) {
			if (res.status) {
				window.location.href = "<%=basePath%>dbBackUp/dbBackUpAction!downBackUpFile.action?fileName=" + fileName;
			} else {
				parent.$.messager.show({
					title : res.title,
					msg : res.message,
					timeout : 1000 * 2
				});
			}
		}
	});
}
// 删除
function delRows() {
	var row = $dg.datagrid('getSelected');
	if (row) {
		var rowIndex = $dg.datagrid('getRowIndex', row);
		$dg.datagrid('deleteRow', rowIndex);
		$.ajax({
			url : "logsController.do?method=delLogs",
			data : "logId=" + row.logId,
			success : function(rsp) {
				parent.$.messager.show({
					title : rsp.title,
					msg : rsp.message,
					timeout : 1000 * 2
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
	parent.$.modalDialog({
		title : '手动备份',
		width : 600,
		height : 400,
		href : "jsp/dbBackUp/autoEditDlg.jsp",
		buttons : [ {
			text : '备份',
			iconCls : 'icon-start',
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
// 弹窗增加公司
function addRowsOpenDlg() {
	parent.$.modalDialog({
		title : '定时备份',
		width : 600,
		height : 400,
		href : "jsp/dbBackUp/timeEditDlg.jsp",
		onLoad : function() {
			$.ajax({
				url : "dbBackUpController.do?method=getScheduleConfig",
				type : "POST",
				success : function(res) {
					var f = parent.$.modalDialog.handler.find("#form");
					f.form("load", res);
				}
			});

		},
		buttons : [ {
			text : '启动',
			iconCls : 'icon-start',
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
