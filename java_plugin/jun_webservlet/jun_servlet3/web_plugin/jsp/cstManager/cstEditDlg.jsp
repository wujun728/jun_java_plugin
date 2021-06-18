<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var $dg;
	var $tree;
	$(function() {
		$("#tt").tabs({
			border:false,
			width:'auto',
			height:$(this).height()-120
		});
		$("#saleId").combotree({
			width:171,
			url:"cstController.do?method=findSaleNameList",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		 	onBeforeSelect:function(node){
		 		if(node.attributes.status=="o"){
		 			return false;
		 		}else{
		 			$("#saleName").val(node.text);
		 		}
		 	}
		});
		
		$("#cityId").combotree({
			width:171,
			url:"areaController.do?method=findCities",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		 	onBeforeSelect:function(node){
		 		if(node.attributes.status=="p"){
		 			return false;
		 		}else{
		 			$("#cityName").val(node.text);
		 		}
		 	}
		});
		var typedata=[{"type":"M","typeName":"男"},{"type":"F","typeName":"女"}];
		 $dg = $("#dg");
		 $grid=$dg.datagrid({
			url : "cstContactController.do?method=findCustomerContactList&customerId="+$("#tempId").val(),
			width : 'auto',
			height : $(this).height()-120,
			rownumbers:true,
			border:false,
			singleSelect:true,
			columns : [ [ {field : 'name',title : '联系人名称',width : parseInt($(this).width()*0.07),editor : {type:'validatebox',options:{required:true}}},
			              {field : 'sexName',title : '性别',width : parseInt($(this).width()*0.03), 
							formatter:function(value,row){
		            		  if("F"==row.sexName)
									return "<font color=red>女<font>";
			            		  else
			            			return "男";  
							},
							editor:{
								type:'combobox',
								options:{
									valueField:'type',
									textField:'typeName',
									data:typedata,
									required:true
								}
							}},
			              {field : 'duty',title : '职务',width : parseInt($(this).width()*0.07),editor : "text"},
			              {field : 'tel',title : '电话',width : parseInt($(this).width()*0.05),editor : "validatebox"},
			              {field : 'email',title : '电子邮箱',width : parseInt($(this).width()*0.09),align : 'left',editor:{
			          		type:'validatebox',
			        		options:{
			        			validType:'email'
			        		}
			        		}},
			              {field : 'qq',title : 'QQ',width : parseInt($(this).width()*0.07),align : 'left',editor : "text"},
			              {field : 'birthday',title : '生日',width : parseInt($(this).width()*0.05),align : 'left',editor : "datebox"},
			              {field : 'description',title : '备注',width : parseInt($(this).width()*0.08),align : 'left',editor : "text"}
			              ] ],toolbar:'#tb'
		});
		
		$("#classId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerClass",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#className").val(value.name);
			}
		});
		
		$("#levelId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerLevel",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#levelName").val(value.name);
			}
		});
		
		$("#industryId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=industry",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#industryName").val(value.name);
			}
		});
		
		$("#sizeId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=companySize",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#sizeName").val(value.name);
			}
		});
		
		$("#creditId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=credit",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#creditName").val(value.name);
			}
		});
		
		$("#natureId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerNature",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#natureName").val(value.name);
			}
		});
		
		$("#currencyId").combobox({
			width:171,
			url:"currencyController.do?method=findCurrencyList",
			valueField: 'id',
			textField: 'name',
			onSelect:function(value){
				$("#currencyName").val(value.name);
			}
		});
		
		$("#form").form({
			url :"cstController.do?method=persistenceCustomer",
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
		$dg.datagrid('appendRow', {});
		var rows = $dg.datagrid('getRows');
		$dg.datagrid('beginEdit', rows.length - 1);
	}
	function editRows(){
		var rows = $dg.datagrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $dg.datagrid('getRowIndex', row);
				$dg.datagrid('beginEdit', rowIndex);
			}
		});
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
				/*$.post("companyInfo/companyInfoAction!persistenceCompanyInfo.action", effectRow, function(rsp) {
					if(rsp.status){
						$dg.datagrid('acceptChanges');
					}
					$.messager.alert(rsp.title, rsp.message);
				}, "JSON").error(function() {
					$.messager.alert("提示", "提交错误了！");
				});*/
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
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="">
	<input name="tempId" id="tempId"  type="hidden" value="<%=request.getParameter("tempId")==null?"":request.getParameter("tempId")%>"/>
		<form id="form" method="post">
			<div id="tt">
				<div title="基本资料" data-options="iconCls:'icon-cstbase'" style="padding:10px">
						<fieldset>
							<legend>基本资料编辑</legend>
							<input name="customerId" id="customerId"  type="hidden"/>
							<input name="created" id="created"  type="hidden"/>
							<input name="creater" id="creater"  type="hidden"/>
							<input name="status" id="status"  type="hidden"/>
							<input name="inserted" id="inserted"  type="hidden"/>
							<input name="updated" id="updated"  type="hidden"/>
							<input name="deleted" id="deleted"  type="hidden"/>
							 <table class="table">
								 <tr>
								    <th>客户名称</th>
									<td><input name="name" id="name" placeholder="请输入客户名称" class="easyui-textbox easyui-validatebox" type="text" data-options="required:true"/></td>
									<th>客户编码</th>
									<td><input name="myid" id="myid" type="text"  class="easyui-textbox easyui-validatebox" data-options="required:true"/></td>
									<th>客户类型</th>
									<td><input name="classId" id="classId" type="text" class="easyui-textbox easyui-validatebox" /><input name="className" id="className" type="hidden" /></td>
								 </tr>
								 <tr>
									<th>客户级别</th>
									<td><input id="levelId" name="levelId" type="text" class="easyui-textbox easyui-validatebox" /><input id="levelName" name="levelName" type="hidden"/></td>
								    <th>销售代表</th>
									<td><input name="saleId" id="saleId" type="text" class="easyui-textbox easyui-validatebox" /><input name="saleName" id="saleName" type="hidden"/></td>
								    <th>公司法人</th>
									<td><input id="corporation" name="corporation" type="text" class="easyui-textbox easyui-validatebox" /></td>
								 </tr>
								  <tr>
									<th>城市</th>
									<td><input id="cityId" name="cityId" type="text" class="easyui-textbox easyui-validatebox"/><input id="cityName" name="cityName" type="hidden"/><img src="css/extend/area.png" style="margin-left:2px;margin-bottom: -5px;cursor: pointer;" onclick="addAreaOpenDlg();"/></td>
									<th>公司电话</th>
									<td><input id="tel" name="tel" type="text" class="easyui-textbox easyui-validatebox"/></td>
									 <th>公司传真</th>
									<td><input id="fax" name="fax" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
								 <tr>
									<th>地址</th>
									<td colspan="5"><textarea class="easyui-textbox" id="address" name="address"  style="width: 710px;height: 80px;"></textarea></td>
								 </tr>
								 <tr>
									<th>备注</th>
									<td colspan="5"><textarea class="easyui-textbox" id="description" name="description"  style="width: 710px;height: 80px;"></textarea></td>
								</tr>
							   </table>
						</fieldset>
				</div>
				<div title="营业资料" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>营业资料编辑</legend>
						 <table class="table">
							   <tr>
								    <th>公司网站</th>
									<td><input name="url" id="url" class="easyui-textbox easyui-validatebox" type="text"/></td>
									<th>公司邮箱</th>
									<td><input name="email" id="email" type="text"  class="easyui-textbox easyui-validatebox" /></td>
									<th>公司性质</th>
									<td><input name="natureId" id="natureId" type="text" class="easyui-textbox easyui-validatebox"/><input name="natureName" id="natureName" type="hidden"/></td>
								 </tr>
								 <tr>
									<th>行业名称</th>
									<td><input id="industryId" name="industryId" type="text" class="easyui-textbox easyui-validatebox"/><input id="industryName" name="industryName" type="hidden" /></td>
								    <th>主营业务</th>
									<td><input name="mainBusiness" id="mainBusiness" type="text" class="easyui-textbox easyui-validatebox" /></td>
									<th>公司规模</th>
									<td><input id="sizeId" name="sizeId" type="text" class="easyui-textbox easyui-validatebox" /><input id="sizeName" name="sizeName" type="hidden"/></td>
								 </tr>
								 <tr>
								    <th>开业年份</th>
									<td><input id="setupDate" name="setupDate" type="text" class="easyui-textbox easyui-datebox"/></td>
									<th>注册资金</th>
									<td><input id="capital" name="capital" type="text" class="easyui-textbox easyui-validatebox" /></td>
								    <th>员工人数</th>
									<td><input id="empCount" name="empCount" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
								 <tr>
								    <th>营业总额</th>
									<td><input id="totalSales" name="totalSales" type="text" class="easyui-textbox easyui-validatebox"/></td>
									<th>交易状态</th>
									<td><select id="customerStatus" class="easyui-combobox" name="customerStatus" style="width:171px;">
											<option value="T">交易中</option>
											<option value="S">禁用</option>
										</select>
									</td>
								 </tr>
						</table>
						</fieldset>
				</div>
				<div title="账款资料" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>账款资料编辑</legend>
						 <table class="table">
							 <tr>
								    <th>账款币别</th>
									<td><input name="currencyId" id="currencyId" class="easyui-textbox easyui-validatebox" type="text"/><input name="currencyName" id="currencyName" type="hidden"/></td>
									<th>信用等级</th>
									<td><input name="creditId" id="creditId" type="text"  class="easyui-textbox easyui-validatebox" /><input name="creditName" id="creditName" type="hidden"/></td>
									<th>开户银行</th>
									<td><input name="bank" id="bank" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
								 <tr>
									<th>银行账号</th>
									<td><input id="account" name="account" type="text" class="easyui-textbox easyui-validatebox"/></td>
								    <th>税号</th>
									<td><input name="taxno" id="taxno" type="text" class="easyui-textbox easyui-validatebox"/></td>
									<th>扣税类别</th>
									<td><select id="deductionTax" class="easyui-combobox" name="deductionTax" style="width:171px;">
											<option value="Y">含税</option>
											<option value="N">未税</option>
										</select>
									</td>
								 </tr>
								 <tr>
								    <th>应用税率</th>
									<td><input id="tax" name="tax" type="text" class="easyui-textbox easyui-validatebox"/></td>
									<th>立账方式</th>
									<td><input id="setupAccount" name="setupAccount" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
						</table>
						</fieldset>
				</div>
				<div title="客户联系人" data-options="iconCls:'icon-help'">
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
							
				<div title="交易方式" data-options="iconCls:'icon-help'" style="padding:10px">
					<fieldset>
							<legend>交易方式编辑</legend>
							<!--   -->
							<table class="table">
							 <tr>
								    <th></th>
									<td><input name="currencyName1" id="currencyName1"  type="radio"/></td>
									<th>现金交易</th>
									<td></td>
									<th></th>
									<td></td>
								 </tr>
								 <tr>
									<th></th>
									<td><input name="currencyName1" id="currencyName2"  type="radio"/></td>
								    <th>款到发货</th>
									<td></td>
									<th></th>
									<td></td>
								 </tr>
								 <tr>
								    <th></th>
									<td><input name="currencyName1" id="currencyName3"  type="radio"/></td>
									<th>收付票据</th>
									<td></td>
									<th>票期</th>
									<td><input name="asdasd" id="sdasdasd"  class="easyui-textbox easyui-validatebox" type="text"/></td>
								 </tr>
								  <tr>
								    <th></th>
									<td><input name="currencyName1"  id="currencyName4"  type="radio"/></td>
									<th>货到付款</th>
									<td></td>
									<th>票期</th>
									<td><input name="a11sdasd" class="easyui-textbox easyui-validatebox" id="s1dasdasd"  type="text"/></td>
								 </tr>
								 <tr>
								    <th></th>
									<td><input name="currencyName1"  id="currencyName4"  type="radio"/></td>
									<th>其他</th>
									<td></td>
									<th></th>
									<td><input name="a11sda11sd" class="easyui-textbox easyui-validatebox" id="s1dasdasd"  type="text"/></td>
								 </tr>
								 <tr>
								    <th></th>
									<td></td>
									<th>帐期</th>
									<td></td>
									<th></th>
									<td></td>
								 </tr>
						</table>
						</fieldset>
				</div>
		  </div>
		</form>
	</div>
</div>
