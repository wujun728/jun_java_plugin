<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件上传</title>
    <script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  </head>
  <body>
  <div class="container">
    	<div class="row">
  		<div class="col-md-12">
  		</div>
  	</div>
  	<div class="row">
  		<div class="col-md-12">
   <form id="upload" action="uploadfile" method="post" enctype="multipart/form-data">
        <div class="form-group">
    		<label for="fileupload">单文件上传</label>
    		<input type="file" name="uploadfile" class="form-control" id="fileupload">
  		</div>
        <button type="submit"  class="btn btn-primary">upload</button>
   </form>
  	 </div>
  	 </div>
   </div>
  </body>
  <script type="text/javascript">
  $(document).ready(function(){
	  $("#upload").submit(function(){
	    if($("#fileupload").val()=="")
	    {
	    	alert("请选择文件上传");
	    	return false;
	    }
	  });
  });
  </script>
</html>
