<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '013_tree.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <%@ include file="/common/common.jsp" %>
	
	<script type="text/javascript">
	
				$(function(){
					
					$('#t2').tree({
							url:'easyui/jsp/013_tree.json' , 
							animate:true ,
							checkbox:true ,
							cascadeCheck : false ,
							onlyLeafCheck: true ,
							dnd: true
					});
					
				});
				
				
				//获取根节点
				function getRoot(){
					console.info($('#t2').tree('getRoot'));
				}
				//获取子节点
				function getChildren(){
					var root = $('#t2').tree('getRoot');		//object
					console.info($('#t2').tree('getChildren',root.target));
				}
				//获取选中的节点
				function getChecked(){
					console.info($('#t2').tree('getChecked'));
				}
				//isLeaf
				function isLeaf(){
					var sel = $('#t2').tree('getSelected');
					console.info($('#t2').tree('isLeaf', sel.target));
				}
				//切换折叠状态
				function toggle(){
					var root = $('#t2').tree('getRoot');		//object
					$('#t2').tree('toggle' , root.target);
				}
				
				
	</script>
  </head>
  
  <body>
  
  			<!-- 1 html 形式 渲染 tree  -->
  			<!-- 
  			<ul id="t1" class="easyui-tree">
  					<li>
  						<span>根节点</span>
  						<ul>
  							<li><span>节点1</span></li>
  							<li><span>节点2</span></li>
  							<li><span>节点3</span></li>
  						</ul>	
  					</li>
  			</ul>
  			 -->	
  			 
  			 <!-- 2 js方式初始化 tree -->
  			 <ul id="t2"></ul> <br/>
  			 <input type="button" onclick="getRoot()" value="获取跟节点"/>
  			 <input type="button" onclick="getChildren()" value="获取子节点"/>
  			 <input type="button" onclick="getChecked()" value="获取勾选的节点"/>
  			 <input type="button" onclick="isLeaf()" value="是否是叶子节点"/>
  			 <input type="button" onclick="toggle()" value="切换节点状态"/>
  </body>
</html>
