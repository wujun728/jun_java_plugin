<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '002.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
	
			$(function(){
					// panel
					/*
					$('#mypanel').panel({
							width:600 ,
							height:300 ,
							iconCls: 'icon-edit' ,
							collapsible: true ,
							closable : true ,
							content: '我是面板的内容'
					});
					*/
					
					/*
					$('#mywin').window({
						content:'我是窗口的内容' ,
						onOpen: function(){
							//alert('窗口打开了');
						}
					});
					
					$('#btn').click(function(){
						$('#mywin').window('open');
					});
					*/
					
					
					//dialog 
					$('#dialog').dialog({
						title:'我是对话框' ,
						iconCls:'icon-ok' ,
						toolbar: [
							{
								text:'新增' ,
								iconCls:'icon-add' ,
								handler : function(){
									alert('新增了');
								}
							},
							{
								text:'修改',
								iconCls:'icon-edit'
							}
						] ,
						buttons:[
							{
								text:'确定' ,
								iconCls:'icon-ok' ,
								handler : function(){
									alert('确定啦');
								}
							},
							{
								text:'取消',
								iconCls:'icon-cancel'
							}
						]
					});
				
			});		
	
	</script>
	

  </head>
  
  <body>
  		<!-- 使用组件的方式： 1 html  2 js -->
  		<!-- 
    	<div id="mypanel" class="easyui-panel" minimizable=true  maximizable=true collapsible=true closable="true"  
    	 title="我的panel" iconCls="icon-add" style="width:300px;height:300px" >
    		我是panel的内容
    	</div>
    	 -->
    	 <!-- 
    	<div id="mypanel" title='我的面板2' ></div>
    	 -->
    	 <!-- 
    	<div id="mywin" class="easyui-window" closed=true shadow=true draggable=true  resizable=false title="我的窗口" style="width:300px;height:300px"  ></div> 
    	<a id="btn" class="easyui-linkbutton">点击我</a>
    	 -->
    	<div id="dialog" class="easyui-dialog" style="width:300px;height:300px"  ></div>
    	
  </body>
</html>
