<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	var flag=false;
	$(document).ready(function(e) {
       	$("#imgForm").submit(function(){
       		if($("#tu").val()==""){
       			alert("请选择图片！");
       			return false;
       		}
       		
       		if(!flag){
       			alert("上传的必须是图片");
       			return false;
       		}
       	});
       	
       $("#tu").change(function(){
       		var value=$(this).val();
       		var hou=value.substring(value.lastIndexOf("."));
       		if(hou==".jpg" || hou==".png"|| hou==".gif"|| hou==".bmp"){
       			flag=true;
       		}else{
       			alert("必须是图片!");
       			flag=false;
       		}
       	});
    });
    
    
</script>
</head>

<body>
  <form id="logForm" action="file" method="post" enctype="multipart/form-data">
       	 文件1：<input type="file" name="myfile" id="userName"/><br/>
       	 文件2：<input type="file" name="myfile" id="userName"/><br/>
       	 文件3：<input type="file" name="myfile" id="userName"/><br/>
       	 文件4：<input type="file" name="myfile" id="userName"/><br/>
       	 描述：<textarea name="desc" rows="5" cols="20">
       	 </textarea><br/>
       	 &nbsp;&nbsp;<input type="submit" value="提交"/>
  </form>
  
  
  <h2>添加图片</h2>
  <form id="imgForm" action="file" method="post" enctype="multipart/form-data">
       	 图片：<input type="file" name="myfile" id="tu"/><br/>
       	 描述：<textarea name="desc" rows="5" cols="20">
       	 </textarea><br/>
       	 &nbsp;&nbsp;<input type="submit" value="提交"/>
  </form>
</body>
</html>
