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
    <title>系统参数</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
	var $pg;
	$(function() {
		$pg=$("#pg");
		$pg.propertygrid({
			width:'auto',
			height:$(this).height()-90,
			showGroup:false,
			striped:true,
			scrollbarSize:0,
			toolbar:"#tb",
			url:"systemParameterController.do?method=findParameterList",
			columns: [[
			    		{field:'name',title:'参数名称',width:parseInt($(this).width()*0.1),sortable:true,editor:"text",required:true},
			    		{field:'myid',title:'参数编码',width:parseInt($(this).width()*0.1),sortable:true,editor:"text"},
			    		{field:'state',title:'是否启用',width:parseInt($(this).width()*0.05),sortable:true,
			    			formatter:function(value,row){
		            		  if("Y"==row.state)
									return "<font color=green>是<font>";
			            		  else
			            			return "<font color=red>否<font>";  
								},
								editor:{type:'checkbox',options:{on:'Y',off:'N'}}
			    		},
			   		    {field:'value',title:'参数值',width:parseInt($(this).width()*0.05),
									formatter:function(value,row){
		            		  	  if(row.value=="true"){
										return "<font color=green>是<font>";
		            		  	  }else if(row.value=="false"){
		            		  		return "<font color=red>否<font>"; 
		            		  	  }else{
		            		  		  return row.value;
		            		  	  }
								},editor:"text"},
			    		{field:'description',title:'参数描述',width:parseInt($(this).width()*0.2),editor:"text"}//,
//						{field:'editorType',title:'editorType',width:parseInt($(this).width()*0.2),editor:"text"},
//			    		{field:'group',title:'group',width:parseInt($(this).width()*0.2),editor:"text"}
			        ]]
		});
		//搜索框
		/*$("#searchbox").searchbox({ 
			 menu:"#mm", 
			 prompt :'模糊查询',
		    searcher:function(value,name){   
		    	var str="{\"companyName\":\""+name+"\",\"value\":\""+value+"\"}";
	            var obj = eval('('+str+')');
	            $dg.datagrid('reload',obj); 
		    }
		   
		});*/ 
	});

	function endEdit(){
		var flag=true;
		var rows = $pg.propertygrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			$pg.propertygrid('endEdit', i);
			var temp=$pg.propertygrid('validateRow', i);
			if(!temp){flag=false;}
		}
		return flag;
	}
	function addRows(){
		$pg.propertygrid('appendRow', {});
		var rows = $pg.propertygrid('getRows');
		$pg.propertygrid('beginEdit', rows.length - 1);
	}
	function editRows(){
		var rows = $pg.propertygrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $pg.propertygrid('getRowIndex', row);
				$pg.propertygrid('beginEdit', rowIndex);
			}
		});
	}
	function removeRows(){
		var rows = $pg.propertygrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $pg.propertygrid('getRowIndex', row);
				$pg.propertygrid('deleteRow', rowIndex);
			}
		});
	}
	function saveRows(){
		if(endEdit()){
			if ($pg.propertygrid('getChanges').length) {
				var inserted = $pg.propertygrid('getChanges', "inserted");
				var deleted = $pg.propertygrid('getChanges', "deleted");
				var updated = $pg.propertygrid('getChanges', "updated");
				
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
				$.post("systemParameterController.do?method=persistenceParameter", effectRow, function(rsp) {
					if(rsp.status){
						$pg.propertygrid('acceptChanges');
					}
					parent.$.messager.show({
						title :rsp.title,
						msg :rsp.message,
						timeout : 1000 * 2
					});
				}, "JSON").error(function() {
					parent.$.messager.show({
						title :"提示",
						msg :"提交错误了！",
						timeout : 1000 * 2
					});
				});
			}
		}else{
			parent.$.messager.show({
				title :"提示",
				msg :"字段验证未通过!请查看",
				timeout : 1000 * 2
			});
		}
	}
	//删除
	function delRows(){
		var row = $dg.datagrid('getSelected');
		if(row){
			var rowIndex = $dg.datagrid('getRowIndex', row);
			$dg.datagrid('deleteRow', rowIndex);
			$.ajax({
				url:"companyInfo/companyInfoAction!delCompanyInfo.action",
//				url:"companyInfo/companyInfoAction!delCompanyInfo.action",
				data: "companyId="+row.companyId,
				success: function(rsp){
					parent.$.messager.show({
						title : rsp.title,
						msg : rsp.message,
						timeout : 1000 * 2
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

	</script>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>系统参数</strong></span>进行是设置和编辑!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="parEndEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRows();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="parEndEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-end" plain="true" onclick="endEdit();">结束编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="parDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRows();">删除</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="parSave">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRows();">保存修改</a>
						</shiro:hasPermission>
					</td>
					<!--  <td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tbsCompanySearch();">高级查询</a>
					</td>-->
				</tr>
			</table>
		</div>
		<!--  <div id="mm">
				<div name="name">公司名称</div>
				<div name="tel">公司电话</div>
				<div name="fax">传真</div>
				<div name="address">地址</div>
				<div name="zip">邮政编码</div>
				<div name="email">邮箱</div>
				<div name="contact">联系人</div>
				<div name="description">描述</div>
		</div>-->
		<table id="pg" title="参数编辑"></table>
  	</div>	
  </body>
</html>
