<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var $dg;
	var $tree;
	$(function() {
		$("#tt").tabs({
			border:false,
			width:'auto',
			height:$(this).height()-340
		});
		$("#managerId").combotree({
			width:171,
			url:"cstController.do?method=findSaleNameList",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		 	onBeforeSelect:function(node){
		 		if(node.attributes.status=="o"){
		 			return false;
		 		}else{
		 			$("#managerName").val(node.text);
		 		}
		 	}
		});
		$("#customerId").combobox({
			width:171,
			url:"projectController.do?method=findCustomers",
			valueField: 'customerId',
			textField: 'name',
			onSelect:function(value){
				$("#customerMyid").val(value.name);
			}
		});
		
		$("#classId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=projectClass",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#className").val(value.name);
			}
		});
		 $("#sourceId").combobox({
				width:171,
				url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=projectSource",
				valueField: 'codeId',
				textField: 'name',
				onSelect:function(value){
					$("#sourceName").val(value.name);
				}
			});
	 
		
		var typedata=[{"type":"M","typeName":"男"},{"type":"F","typeName":"女"}];
		 $dg = $("#dg");
		 $grid=$dg.datagrid({
			url : "projectController.do?method=findProjectFollowsList&projectId="+$("#tempId").val(),
			width : 'auto',
			height : $(this).height()-380,
			rownumbers:true,
			border:false,
			singleSelect:true,
			columns : [ [ {field : 'contcatDate',title : '联系人日期',width : parseInt($(this).width()*0.09),editor : {type:'datebox',options:{required:true}}},
			              {field : 'contcat',title : '联系人',width : parseInt($(this).width()*0.05),editor : "text"},
			              {field : 'followClass',title : '跟踪类型',width : parseInt($(this).width()*0.07), 
			            	  formatter:function(value,row){
			            	  	return row.className;  
							},
							editor:{
								type:'combobox',
								options:{
									 url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=followClass",
									 valueField: 'codeId',
									 textField: 'name',
								 	 onSelect:updCellTree,
									 required:true
								}}
					  		}, 
			              {field : 'followStatus',title : '跟踪状态',width : parseInt($(this).width()*0.05),
					  			formatter:function(value,row){
			            	  	return row.statusName;  
							},
							editor:{
								type:'combobox',
								options:{
									 url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=followStatus",
									 valueField: 'codeId',
									 textField: 'name',
								 	 onSelect:updCellTree2,
									 required:true
								}}
					  		},
			              {field : 'progress',title : '进度比率',width : parseInt($(this).width()*0.09),align : 'left',editor:{
			            	 type:'numberbox',
			            	 options:{
			            		 min:0,
			            		 max:100
			            	 }
			              },
					  	  formatter:formatProgress
					  	  },
			              {field : 'content',title : '主题',width : parseInt($(this).width()*0.07),align : 'left',editor : "text"},
			              {field : 'description',title : '备注',width : parseInt($(this).width()*0.08),align : 'left',editor : "text"}
			              ] ],toolbar:'#tb'
		});
		
		 
		$("#form").form({
			url :"projectController.do?method=persistenceProject",
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
					parent.$.modalDialog.openner.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为页面预定义好了
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
	function formatProgress(value){
    	if (value){
	    	var s = '<div style="width:100%;border:1px solid #ccc">' +
	    			'<div style="width:' + value + '%;background:green;color:#fff">' + value + '%' + '</div>'
	    			'</div>';
	    	return s;
    	} else {
	    	return '';
    	}
	}
	function updCellTree(record){
		var rowIdx = $dg.datagrid('getRowIndex',$dg.datagrid('getSelected'));
		var ed = $dg.datagrid('getEditor', {index:rowIdx,field:'followClass'});
		var className = $(ed.target).combobox('getText');
		$dg.datagrid('getRows')[rowIdx]['className'] = className;
		//$('#dg').datagrid('endEdit', editIndex);
	}
	
	function updCellTree2(record){
		var rowIdx = $dg.datagrid('getRowIndex',$dg.datagrid('getSelected'));
		var ed = $dg.datagrid('getEditor', {index:rowIdx,field:'followStatus'});
		var statusName = $(ed.target).combobox('getText');
		$dg.datagrid('getRows')[rowIdx]['statusName'] = statusName;
		//$('#dg').datagrid('endEdit', editIndex);
	}
	function endEdit(){
		var flag=true;
		var rows = $dg.datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			$dg.datagrid('endEdit', i);
			var temp=$dg.datagrid('validateRow', i);
			if(!temp){flag=false;}
		}
		return flag;
	}
	function addRows(){
		if(endEdit()){
			$dg.datagrid('appendRow', {});
			var rows = $dg.datagrid('getRows');
			$dg.datagrid('beginEdit', rows.length - 1);
			$dg.datagrid('selectRow', rows.length - 1);
		}else{
			$.messager.alert("提示", "编辑行数据未完成!");
		}
	}
	function editRows(){
		if(endEdit()){
			var rows = $dg.datagrid('getSelections');
			$.each(rows,function(i,row){
				if (row) {
					var rowIndex = $dg.datagrid('getRowIndex', row);
					$dg.datagrid('beginEdit', rowIndex);
				}
			});
		}else{
			$.messager.alert("提示", "编辑行数据未完成!");
		}
	}
	function removeRows(){
		var rows = $dg.datagrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $dg.datagrid('getRowIndex', row);
				$dg.datagrid('deleteRow', rowIndex);
			}
		});
	}
	function saveRows(){
		if(endEdit()){
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
				$("#inserted").val(effectRow["inserted"]);
				$("#deleted").val(effectRow["deleted"]);
				$("#updated").val(effectRow["updated"]);
			}
		}else{
			$.messager.alert("提示", "字段验证未通过!请查看");
		}
	}
	
	//弹窗增加区域
	function addAreaOpenDlg() {
		$("<div/>").dialog({
			href : "jsp/cstManager/areaEditDlg.jsp",
			width : 600,
			height : 400,
			modal : true,
			title : '添加区域',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$("#form1").form({
						url : "areaController.do?method=addCities",
						onSubmit : function() {
							parent.parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});
							var isValid = $(this).form('validate');
							if (!isValid) {
								parent.parent.$.messager.progress('close');
							}
							return isValid;
						},
						success : function(result) {
							try {
								parent.$.messager.progress('close');
								result = $.parseJSON(result);
								if (result.status) {
									$("#cityId").combotree("reload");
									d.dialog('destroy');
									parent.parent.$.messager.show({
										title : result.title,
										msg : result.message,
										timeout : 1000 * 2
									});
								}else{
									parent.parent.$.messager.show({
										title :  result.title,
										msg : result.message,
										timeout : 1000 * 2
									});
								}
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
					$("#form1").submit();
				}
			},{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					 $(this).closest('.window-body').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
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
	.table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
	}
	
	.table{
		text-align:left;
		padding: 6px 10px 6px 10px;
	}
	.table th{
		text-align:left;
		padding: 6px 10px 6px 10px;
	}
	.table td{
		text-align:left;
		padding: 6px 10px 6px 10px;
	}
</style>
	<div data-options="region:'center',border:false" title="">
	<input name="tempId" id="tempId"  type="hidden" value="<%=request.getParameter("tempId")==null?"":request.getParameter("tempId")%>"/>
		<form id="form" method="post">
			<div id="tt">
				<div title="基本资料" data-options="iconCls:'icon-cstbase'" style="padding:10px">
						<fieldset>
							<legend>基本资料编辑</legend>
							<input name="projectId" id="projectId"  type="hidden"/>
							<input name="created" id="created"  type="hidden"/>
							<input name="creater" id="creater"  type="hidden"/>
							<input name="status" id="status"  type="hidden"/>
							<input name="inserted" id="inserted"  type="hidden"/>
							<input name="updated" id="updated"  type="hidden"/>
							<input name="deleted" id="deleted"  type="hidden"/>
							 <table class="table">
								 <tr>
								    <th>项目名称</th>
									<td><input name="name" id="name" placeholder="请输入项目名称" class="easyui-textbox easyui-validatebox" type="text" data-options="required:true"/></td>
									<th>项目编码</th>
									<td><input name="myid" id="myid" type="text"  class="easyui-textbox easyui-validatebox" data-options="required:true"/></td>
									<th>立项日期</th>
									<td><input name="setupDate" id="setupDate" type="text" class="easyui-textbox easyui-datebox" /></td>
								 </tr>
								 <tr>
									<th>项目类型</th>
									<td><input id="classId" name="classId" type="text" class="easyui-textbox easyui-validatebox" /><input id="className" name="className" type="hidden"/></td>
								    <th>客户名称</th>
									<td><input name="customerId" id="customerId" type="text" class="easyui-textbox easyui-validatebox" /><input id="customerMyid" name="customerMyid" type="hidden"/></td>
								    <th>联系人</th>
									<td><input id="contacts" name="contacts" type="text" class="easyui-textbox easyui-validatebox" /></td>
								 </tr>
								  <tr>
									<th>联系人电话</th>
									<td><input id="telNo" name="telNo" type="text" class="easyui-textbox easyui-validatebox"/></td>
									<th>手机</th>
									<td><input id="mobile" name="mobile" type="text" class="easyui-textbox easyui-validatebox"/></td>
									 <th>项目来源</th>
									<td><input id="sourceId" name="sourceId" type="text" class="easyui-textbox easyui-validatebox"/><input id="sourceName" name="sourceName" type="hidden"/></td>
								 </tr>
								 <tr>
									<th>项目预算</th>
									<td><input name="budget" id="budget" type="text" class="easyui-textbox easyui-validatebox" /></td>
									<th>开始时间</th>
									<td><input id="startTime" name="startTime" type="text" class="easyui-textbox easyui-datebox"/></td>
									<th>结束时间</th>
									<td><input id="endTime" name="endTime" type="text" class="easyui-textbox easyui-datebox"/></td>
								 </tr>
								 <tr>
									<th>项目负责人</th>
									<td><input name="managerId" id="managerId" type="text" class="easyui-textbox easyui-validatebox" /><input id="managerName" name="managerName" type="hidden"/></td>
								 </tr>
								 <tr>
									<th>备注</th>
									<td colspan="5"><textarea class="easyui-textbox" id="description" name="description"  style="width: 700px;height: 80px;"></textarea></td>
								</tr>
							   </table>
						</fieldset>
				</div>
				<div title="项目进度记录" data-options="iconCls:'icon-help'">
						<table id="dg"></table>
							<div id="tb" style="padding:2px 0">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td style="padding-left:2px">
											<shiro:hasPermission name="cstConEdit">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRows();">添加</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRows();">编辑</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-end" plain="true" onclick="endEdit();">结束编辑</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRows();">删除</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRows();">保存</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</table>
							</div>
				</div>
		  </div>
		</form>
	</div>
