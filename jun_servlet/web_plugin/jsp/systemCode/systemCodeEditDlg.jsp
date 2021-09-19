<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function() {
		$("#parentId").combotree({
			width:171,
			url:"systemCodeController.do?method=findAllSystemCodeList",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		});
		$("#permissionId").combobox({
			width:171,
			url:"functionController.do?method=findAllFunctionList",
			valueField: 'permissionId',
			textField: 'name',
			onSelect:function(value){
				$("#permissionName").val(value.name+","+value.iconCls);
			}
		});
		
		$("#iconCls").combobox({
			width:171,
			data:$.iconData,
			formatter: function(v){
				return $.formatString('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
			}
		});
		
		
		var temp=$("#tempId").val();
		if(temp!=""&&temp!=null){
			$("#permissionId").combobox("disable");
		}
// 		$("#parentId").combotree("disable");
		$("#form").form({
			url :"systemCodeController.do?method=persistenceSystemCodeDig",
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
					parent.$.modalDialog.openner.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为role.jsp页面预定义好了
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
		padding: 0px;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
	<input name="tempId" id="tempId"  type="hidden" value="<%=request.getParameter("tempId")%>"/>
		<form id="form" method="post">
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 词典编辑</legend>
				<input name="codeId" id="codeId"  type="hidden"/>
				<input name="created" id="created"  type="hidden"/>
				<input name="creater" id="creater"  type="hidden"/>
				<input name="status" id="status"  type="hidden"/>
				<input name="state" id="state"  type="hidden"/>
				<input name="permissionName" id="permissionName"  type="hidden"/>
				<input name="codePid" id="codePid"  type="hidden"/>
				 <table>
					 <tr>
					    <th>词典名称</th>
						<td><input name="name" id="name" placeholder="请输入词典名称" class="easyui-textbox easyui-validatebox" type="text" /></td>
						<th>父项模块</th>
						<td><input id="permissionId"  name="permissionId"  class="easyui-textbox" type="text"/></td>
					 </tr>
					 <tr>
					 	<th>父词典名称</th>
						<td><input id="parentId"  name="parentId"  class="easyui-textbox" type="text"/></td>
						<th>词典编码</th>
						<td><input id="codeMyid" name="codeMyid" type="text" class="easyui-textbox easyui-validatebox"/></td>
					 </tr>
					 <tr>
					 	<th>词典图标</th>
						<td><input id="iconCls" name="iconCls" class="easyui-textbox" type="text"/></td>
					    <th>排序编码</th>
						<td><input name="sort" id="sort" type="text" class="easyui-textbox easyui-validatebox"/></td>
					 </tr>
					 <tr>
						<th>描述</th>
						<td colspan="3"><textarea class="easyui-textbox" name="description"  style="width: 415px;height: 100px;"></textarea></td>
					</tr>
				 </table>
			</fieldset>
		</form>
	</div>
</div>
