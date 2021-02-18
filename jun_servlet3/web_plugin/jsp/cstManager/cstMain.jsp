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
    <title>客户管理</title>
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
					url : "cstController.do?method=findCustomerList",
					width : 'auto',
					height : $(this).height()-85,
					pagination:true,
					rownumbers:true,
					border:true,
					striped:true,
					singleSelect:true,
					columns : [ [ {field : 'name',title : '客户名称',width : parseInt($(this).width()*0.1)},
					              {field : 'myid',title : '客户编码',width : parseInt($(this).width()*0.1)},
					              {field : 'customerStatus',title : '客户状态',width : parseInt($(this).width()*0.1), 
					            	  formatter:function(value,row){
				            		  if("T"==row.customerStatus)
											return "<font color=green>交易中<font>";
					            		  else
					            			return "<font color=red>禁用<font>";  
									}},
					              {field : 'tel',title : '电话',width : parseInt($(this).width()*0.1),align : 'left'},
					              {hidden : 'cityId',title : '城市',width : parseInt($(this).width()*0.1),align : 'left',
					            	  formatter:function(value,row){
					            			return "0"+row.cityId;  
									}},
					              {hidden : 'saleId',title : '销售',width : parseInt($(this).width()*0.1),align : 'left',
										formatter:function(value,row){
					            			return "0"+row.saleId;  
									}},
					              {field : 'fax',title : '传真',width :parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'email',title : '邮箱',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'address',title : '地址',width : parseInt($(this).width()*0.3),align : 'left'}
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
		
			//删除
			function delRows(){
				var row = $dg.datagrid('getSelected');
				if(row){
					var rowIndex = $dg.datagrid('getRowIndex', row);
					parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
					    if (r){  
					    	$dg.datagrid('deleteRow', rowIndex);
					    	$.ajax({
								url:"cstController.do?method=delCustomer",
								data: "customerId="+row.customerId,
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
						title : '编辑客户',
						width : 900,
						height :550,
						href : "jsp/cstManager/cstEditDlg.jsp?tempId="+row.customerId,
						onLoad:function(){
							var f = parent.$.modalDialog.handler.find("#form");
							row.saleId=(typeof(row.saleId)=="undefined")?row.saleId:"0"+row.saleId;
							row.cityId=(typeof(row.cityId)=="undefined")?row.cityId:"0"+row.cityId;
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
			//弹窗增加
			function addRowsOpenDlg() {
				parent.$.modalDialog({
					title : '添加客户',
					width : 900,
					height :550,
					href : "jsp/cstManager/cstEditDlg.jsp",
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
		</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>客户和客户联系人</strong></span>进行编辑!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="cstAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="cstEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="cstDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
						</shiro:hasPermission>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:void(0);">高级查询</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="name">客户名称</div>
				<div name="myid">客户编码</div>
		</div>
		<table id="dg" title="客户管理"></table>
  	</div>	
  </body>
</html>
