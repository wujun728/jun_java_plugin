$(function() {
	$("#form").form({
		url : "dbBackUpController.do?method=schedule",
		onSubmit : function() {
			parent.$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			var isValid = $(this).form('validate');
			if (!isValid) {
				parent.$.messager.progress('close');
			}
			return isValid;
		},
		success : function(result) {
			parent.$.messager.progress('close');
			result = $.parseJSON(result);
			if (result.status) {
				parent.reload;
				parent.$.modalDialog.openner.datagrid('reload');// 之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为role.jsp页面预定义好了
				parent.$.modalDialog.handler.dialog('close');
				parent.$.messager.show({
					title : result.title,
					msg : result.message,
					timeout : 1000 * 2
				});
			} else {
				parent.$.messager.show({
					title : result.title,
					msg : result.message,
					timeout : 1000 * 2
				});
			}
		}
	});
});
