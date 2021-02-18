<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '001.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
	
			//alert('hi...');
			 
			 
			 $(function(){
				  //alert(11);
				  //$.messager.alert('提示信息','我是信息内容!');
				  //alert(11);
				  //$.messager.alert('提示信息','我是信息内容!','error');
				  /*
				  $.messager.confirm('标题内容' , '确认么?' ,function(r){
					  	if(r){
					  		alert('点击确认');
					  	} else {
					  		alert('点击取消');
					  	}
				  });
				  */
				  /*
				  $.messager.prompt('提示信息' , '请输入内容:' ,function(val){
					  alert(val);
				  });
				  */
				  /*
				  $.messager.progress({
					  title: '我是进度条' ,
					  msg:'文本内容' ,
					  text: '正在加载..' ,
					  interval:1000
				  });
				  */
				  $.messager.show({
					  title: '提示信息' , 
					  msg: '我是内容'
				  });
			 });
			
	</script>
  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
