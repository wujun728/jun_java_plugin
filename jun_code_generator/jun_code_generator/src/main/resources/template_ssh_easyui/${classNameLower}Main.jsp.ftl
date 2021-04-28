<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>公司管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
			var $dg;
			var $grid;
			$(function() {
				 $dg = $("#dg");
				 $grid=$dg.datagrid({
					url : "companyInfo/companyInfoAction!findAllCompanyInfoList.action",
					width : 'auto',
					height : $(this).height()-85,
					pagination:true,
					rownumbers:true,
					border:true,
					striped:true,
					singleSelect:true,
					columns : [ [ {field : 'name',title : '公司名称',width : parseInt($(this).width()*0.1),editor : {type:'validatebox',options:{required:true}}},
					              {field : 'tel',title : '公司电话',width : parseInt($(this).width()*0.1),editor : "validatebox"},
					              {field : 'fax',title : '传真',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"},
					              {field : 'address',title : '地址',width :parseInt($(this).width()*0.1),align : 'left',editor : "text"},
					              {field : 'zip',title : '邮政编码',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"},
					              {field : 'email',title : '邮箱',width : parseInt($(this).width()*0.1),align : 'left',editor : {type:'validatebox',options:{required:true,validType:'email'}}},
					              {field : 'contact',title : '联系人',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"},
					              {field : 'description',title : '描述',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"}
					              ] ],toolbar:'#tb'
				});
				//搜索框
				$("#searchbox").searchbox({ 
					 menu:"#mm", 
					 prompt :'模糊查询',
				    searcher:function(value,name){   
				    	var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
			            var obj = eval('('+str+')');
			            $dg.datagrid('reload',obj); 
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
						$.post("companyInfo/companyInfoAction!persistenceCompanyInfo.action", effectRow, function(rsp) {
							if(rsp.status){
								$dg.datagrid('acceptChanges');
							}
							$.messager.alert(rsp.title, rsp.message);
						}, "JSON").error(function() {
							$.messager.alert("提示", "提交错误了！");
						});
					}
				}else{
					$.messager.alert("提示", "字段验证未通过!请查看");
				}
			}
			//删除
			function delRows(){
				var row = $dg.datagrid('getSelected');
				if(row){
					var rowIndex = $dg.datagrid('getRowIndex', row);
					parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
					    if (r){  
					    	$dg.datagrid('deleteRow', rowIndex);
							$.ajax({
								url:"companyInfo/companyInfoAction!delCompanyInfo.action",
								data: "companyId="+row.companyId,
								success: function(rsp){
									parent.$.messager.show({
										title : rsp.title,
										msg : rsp.message,
										timeout : 1000 * 2
									});
								}
							});
					    }  
					});
				}else{
					parent.$.messager.show({
						title : "提示",
						msg :"请选择一行记录!",
						timeout : 1000 * 2
					});
				}
			}
			//弹窗修改
			function updRowsOpenDlg() {
				var row = $dg.datagrid('getSelected');
				if (row) {
					parent.$.modalDialog({
						title : '编辑公司',
						width : 600,
						height : 400,
						href : "jsp/company/companyEditDlg.jsp",
						onLoad:function(){
							var f = parent.$.modalDialog.handler.find("#form");
							f.form("load", row);
						},			
						buttons : [ {
							text : '编辑',
							iconCls : 'icon-ok',
							handler : function() {
								parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
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
						}
						]
					});
				}else{
					parent.$.messager.show({
						title :"提示",
						msg :"请选择一行记录!",
						timeout : 1000 * 2
					});
				}
			}
			//弹窗增加公司
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
							parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
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
					}
					]
				});
			}
			
			//高级搜索 删除 row
			function tbCompanySearchRemove(curr) {
					$(curr).closest('tr').remove();
			}
			//高级查询
			function tbsCompanySearch() {
				jqueryUtil.gradeSearch($dg,"#tbCompanySearchFm","jsp/company/companySearchDlg.jsp");
			}
			//excel导出
			function exportExcel(){
				var rows = $dg.datagrid("getRows");
				if(rows.length){
					var isCheckedIds=[];
					$.each(rows,function(i,row){
						if (row) {
							isCheckedIds.push(row.companyId);
						}
					});
					window.location.href="<%=basePath%>excel/excelAction!CompanyInfoExcelExport.action?isCheckedIds="+isCheckedIds;
				}else{
					parent.$.messager.show({
						title :"提示",
						msg :"暂无导出数据!",
						timeout : 1000 * 2
					});
				}
			}
		</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>公司管理</strong></span>进行编辑!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="compAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="compEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="compDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
						</shiro:hasPermission>
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="exportExcel();">导出Excel</a>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tbsCompanySearch();">高级查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="name">公司名称</div>
				<div name="tel">公司电话</div>
				<div name="fax">传真</div>
				<div name="address">地址</div>
				<div name="zip">邮政编码</div>
				<div name="email">邮箱</div>
				<div name="contact">联系人</div>
				<div name="description">描述</div>
		</div>
		<table id="dg" title="公司管理"></table>
  	</div>	
  </body>
</html>
