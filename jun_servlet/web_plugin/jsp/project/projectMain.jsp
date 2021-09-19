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
    <title>项目管理</title>
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
					url : "projectController.do?method=findProjectList",
					width : 'auto',
					height : $(this).height()-85,
					pagination:true,
					rownumbers:true,
					border:true,
					striped:true,
					singleSelect:true,
					columns : [ [ {field : 'name',title : '项目名称',width : parseInt($(this).width()*0.1)},
					              {field : 'myid',title : '项目编码',width : parseInt($(this).width()*0.1)},
					              {field : 'setupDate',title : '立项日期',width : parseInt($(this).width()*0.05)},
					              {field : 'className',title : '项目类型',width : parseInt($(this).width()*0.1)},
					              {field : 'customerMyid',title : '客户名称',width : parseInt($(this).width()*0.05),align : 'left'},
					              {field : 'contacts',title : '联系人',width : parseInt($(this).width()*0.05),align : 'left'},
					              {field : 'startTime',title : '开始时间',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'endTime',title : '结束时间',width :parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'managerName',title : '项目负责人',width : parseInt($(this).width()*0.05),align : 'left'},
					              {field : 'description',title : '备注',width : parseInt($(this).width()*0.2),align : 'left'}
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
								url:"projectController.do?method=delProject",
								data: "projectId="+row.projectId,
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
						title : '编辑项目',
						width : 900,
						height :550,
						href : "jsp/project/projectEditDlg.jsp?tempId="+row.projectId,
						onLoad:function(){
							row.managerId=(typeof(row.managerId)=="undefined")?row.managerId:"0"+row.managerId;
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
			//弹窗增加
			function addRowsOpenDlg() {
				parent.$.modalDialog({
					title : '添加项目',
					width : 900,
					height :550,
					href : "jsp/project/projectEditDlg.jsp",
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
					在此你可以对<span class="label-info"><strong>项目</strong></span>进行编辑!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="projectAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="projectEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="projectDel">
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
				<div name="name">项目名称</div>
				<div name="myid">项目编码</div>
		</div>
		<table id="dg" title="项目管理"></table>
  	</div>	
  </body>
</html>
