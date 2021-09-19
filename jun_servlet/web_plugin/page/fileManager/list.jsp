<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
Map map=new HashMap();
map.put("title", "附件管理");
map.put("notice", "附件");

Map dg=new HashMap();
dg.put("title", "附件管理");
dg.put("url", "bugController.do?method=findBugList");
map.put("dg", dg);
request.setAttribute("params", map);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title><c:out value='${params.title}'></c:out></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
	var wj_dg={
// 			url:"bugController.do?method=queryList",
			url:"<%=basePath%>bizServlet?method=getDatagrid&status=file",
			width:"auto",
			height : $(this).height()-85,
			pagination:true,
			rownumbers:true,
			border:true,
			striped:true,
			singleSelect:true 
			};
 
	var $dg;
	var $grid;
	$(function() {
		 $dg = $("#dg");
		 $grid=$dg.datagrid({
			url : wj_dg.url,
			width : wj_dg.width,
			height : wj_dg.height,
			pagination : wj_dg.pagination,
			rownumbers : wj_dg.rownumbers,
			border : wj_dg.border,
			striped : wj_dg.striped,
			singleSelect : wj_dg.singleSelect,
			columns : [ [ 
							{field : 'id',title : 'id',width : parseInt($(this).width()*0.1),hidden:true},
							{field : 'bizid',title : '业务ID',width : parseInt($(this).width()*0.1)},
							{field : 'filename',title : '文件名称',width : parseInt($(this).width()*0.1)},
							{field : 'filepath',title : '文件路径',width : parseInt($(this).width()*0.1)},
							{field : 'filesize',title : '文件大小',width : parseInt($(this).width()*0.1)},
							{field : 'filetype',title : '文件类型',width : parseInt($(this).width()*0.1)},
							{field : 'upload_time',title : '上传时间',width : parseInt($(this).width()*0.1)},
							{field : 'upload_userid',title : '上传用户ID',width : parseInt($(this).width()*0.1)},
			              	{field : 'description',title : '备注及描述',width : parseInt($(this).width()*0.1)},
							{field : 'del_flag',title : '删除标志',width : parseInt($(this).width()*0.1)},
							{field : 'delete_time',title : '删除时间',width : parseInt($(this).width()*0.1)},
							{field : 'download_time',title : '下载次数',width : parseInt($(this).width()*0.1)},
			              ] ],toolbar:'#tb'
		});
		//搜索框
		$("#searchbox").searchbox({ 
			 menu:"#mm", 
			 prompt :'模糊查询',
		    searcher:function(value,name){   
		    	var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
	            var obj = eval('('+str+')');
		    	//alert(obj)
	           $dg.datagrid('reload',$.parseJSON(str)); 
		    }
		   
		}); 
	});
	//删除
	function delRows(){
		var row = $dg.datagrid('getSelected');
		if(row){
			var rowIndex = $dg.datagrid('getRowIndex', row);
			$dg.datagrid('deleteRow', rowIndex);
			$.ajax({
				url : "bugController.do?method=delBug",
				data: "bugId="+row.bugId,
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
				title : '编辑BUG',
				width : 600,
				height : 400,
				href : "jsp/bugManager/bugEditDlg.jsp",
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
	//弹窗增加
	function addRowsOpenDlg() {
		parent.$.modalDialog({
			title : '添加BUG',
			width : 600,
			height : 400,
			href : "jsp/bugManager/bugEditDlg.jsp",
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
	</script>
  </head>
  <body>
<%-- 	info=<c:out value="${params.dg.title}"></c:out> --%>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong><c:out value="${params.notice}"></c:out></strong></span>进行管理!建议上传压缩包。直接上传图片会导致预览变形！可以直接把文件拖入编辑器中,完成上传！最大上传文件为10M!
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="bugAdd">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="bugEdit">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="bugDel">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
						</shiro:hasPermission>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="bugName">BUG名称</div>
				<div name="attachmentUrl">BUG简要</div>
				<div name="description">BUG附件描述</div>
		</div>
		<table id="dg" title="<c:out value='${params.dg.title}'></c:out>"></table>
  	</div>	
  </body>
  
</html>
