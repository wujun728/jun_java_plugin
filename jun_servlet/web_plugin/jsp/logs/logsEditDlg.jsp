<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function() {
		$("#form").form({
			url :"logsController.do?method=persistenceLogs",
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
					parent.$.modalDialog.openner.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为role.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
					parent.$.messager.show({
						title : result.title,
						msg : result.message,
						timeout : 1000 * 2
					});
				}else{
					parent.$.messager.show({
						title :  result.title,
						msg : result.message,
						timeout : 1000 * 2
					});
				}
			}
		});
	});
</script>
<style>
	.easyui-textbox{
		height: 18px;
		width: 170px;
		line-height: 16px;
	    /*border-radius: 3px 3px 3px 3px;*/
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
	}
	
	textarea:focus, input[type="text"]:focus{
	    border-color: rgba(82, 168, 236, 0.8);
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
	    outline: 0 none;
		}
		table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
	}

	fieldset {
	    border: 0 none;
	    margin: 0;
	    padding: 0;
	}
	legend {
	    -moz-border-bottom-colors: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    border-color: #E5E5E5;
	    border-image: none;
	    border-style: none none solid;
	    border-width: 0 0 1px;
	    color: #999999;
	    line-height: 20px;
	    display: block;
	    margin-bottom: 10px;
	    padding: 0;
	    width: 100%;
	}
	input, textarea {
	    font-weight: normal;
	}
	table ,th,td{
		text-align:left;
		padding: 6px;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="form" method="post">
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 公司编辑</legend>
				<input name="logId" id="logId"  type="hidden"/>
				 <table>
					 <tr>
					    <th>日志名称</th>
						<td><input name="name" id="name" placeholder="请输入日志名称" class="easyui-textbox easyui-validatebox" type="text"/></td>
						<th>日志类型</th>
						<td><select id="type" class="easyui-combobox" name="type" style="width:171px;" data-options="required:true">
								<option value="1">安全日志</option>
								<option value="2">操作日志</option>
							</select></td>
					 </tr>
					 <tr>
					    <th>操作名称</th>
						<td><input name="eventName" id="eventName" type="text" class="easyui-textbox easyui-validatebox" required="required"/></td>
						<th></th>
						<td><input name="logDate" id="logDate"  type="hidden"/></td>
					 </tr>
					 <tr>
						<th>操作描述</th>
						<td colspan="3"><textarea class="easyui-textbox" name="eventRecord"  style="width: 420px;height: 100px;"></textarea></td>
					</tr>
				 </table>
			</fieldset>
		</form>
	</div>
</div>
