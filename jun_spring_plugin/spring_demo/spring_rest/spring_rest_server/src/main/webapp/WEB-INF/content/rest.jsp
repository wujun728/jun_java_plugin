<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>actors</title>
<script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/jquery.bootgrid.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="./js/jquery.bootgrid.min.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
  <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" type="text/css"></link>
  </head>
  <body>
  <div class="container">
  	<div class="row">
  		<div class="col-md-12">
  			<div class="well">
  				<span class="label label-success">GET</span><br><br>
  				<code>http://localhost:8080/Spring-REST-Server/actors</code><br><br>
  				<button type="button" id="getall" class="btn btn-success">获取所有</button><br><br>
  				<code>http://localhost:8080/Spring-REST-Server/actors/1</code><br><br>
  				<button type="button" id="getone" class="btn btn-success">获取单个</button>
  			</div>
  			<div class="well">
  				<span class="label label-primary">POST</span><br><br>
  				<code>http://localhost:8080/Spring-REST-Server/actors</code><br><br>
  				<form class="form-horizontal" action="actors" method="post">
			        <div class="form-group">
			    		<label for="name" class="col-sm-2 control-label">name</label>
			    		<div class="col-sm-6">
			    		<input type="text" name="name" class="form-control" id="name">
			    		</div>
			  		</div>
			        <div class="form-group">
			    		<label for="age" class="col-sm-2 control-label">age</label>
			    		<div class="col-sm-6">
			    		<input type="text" name="age" class="form-control" id="age">
			    		</div>
			  		</div>
			        <button type="button" id="add" class="btn btn-primary col-md-offset-3">新增</button>
			      </form>
  			</div>
  			<div class="well">
  				<span class="label label-info">PUT</span><br><br>
  				<code>http://localhost:8080/Spring-REST-Server/actors/1</code><br><br>
  				<form id="updateform" class="form-horizontal" action="actors" method="post">
			        <div class="form-group">
			    		<label for="name2" class="col-sm-2 control-label">name</label>
			    		<div class="col-sm-6">
			    		<input type="text" name="name2" class="form-control" id="name2">
			    		</div>
			  		</div>
			        <div class="form-group">
			    		<label for="age2" class="col-sm-2 control-label">age</label>
			    		<div class="col-sm-6">
			    		<input type="text" name="age2" class="form-control" id="age2">
			    		<input type="hidden" name="_method" value="put" />
			    		</div>
			  		</div>
			        <button type="button" id="update" class="btn btn-primary col-md-offset-3">修改</button>
			      </form>
  			</div>
  			<div class="well">
  				<span class="label label-danger">DELETE</span><br><br>
  				<code>http://localhost:8080/Spring-REST-Server/actors/3</code><br><br>
  				<button type="button" id="delete" class="btn btn-danger">删除单个</button>
  			</div>
  			<div class="well">
  				<span class="label label-success">file upload</span><br><br>
  				<code>http://localhost:8080/Spring-REST-Server/actors/upload</code><br><br>
  				<form id="myform"  method= "post" enctype ="multipart/form-data">
  				<input type="file" name="uploadfile" class="form-control" id="fileupload"><br>
  				<button type="button" id="file" class="btn btn-success">文件上传</button>
  				</form>
  				
  			</div>
  		</div>
  	</div>
  </div>
  </body>
</html>
<script>
	$(document).ready(function(){
	
	 	
		$("#file").click(function(){
		var formData = new FormData(document.getElementById('myform')); 
	 	 
	 	formData.append("bz", "this is bz");
			$.ajax({  
	          url: 'actors/upload' ,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (returndata) {  
	              alert(returndata.status);  
	          },  
	          error: function (returndata) {  
	              alert(returndata);  
	          }  
     });  
		});
	
		$("#getall").click(function(){
		  	$.get("actors",function(data){
		  		alert("获取结果 "+ JSON.stringify(data));
		  	});
		});
		
		$("#getone").click(function(){
		  $.get("actors/1",function(data){
		  		alert("获取结果 "+ JSON.stringify(data));
		  	});
		});
		
		$("#delete").click(function(){
			$.ajax({
			    url: 'actors/3',
			    type: 'DELETE',
			    success: function(result) {
			        alert("删除成功");
			    }
			});
		});
		
		$("#add").click(function(){
			if($("#name").val()==""){
				alert("name不能空");
				return false;
			}
			if($("#age").val()==""){
				alert("age不能空");
				return false;
			}
			var json={"name":$("#name").val(),"age":$("#age").val()};
		   $.ajax({  
            url : "actors",  
            type : "POST",  
            data : JSON.stringify(json), //转JSON字符串  
            dataType: 'json',  
            contentType:'application/json;charset=UTF-8', //contentType很重要     
            success : function(result) {  
                alert("添加成功"+JSON.stringify(result));  
            }  
     });  
		  
		});
		
		$.get("actors/1",function(data){
		  		$("#name2").val(data.name);
		  		$("#age2").val(data.age);
		});
		
		$("#update").click(function(){
			if($("#name2").val()==""){
				alert("name不能空");
				return false;
			}
			if($("#age2").val()==""){
				alert("age不能空");
				return false;
			}
			var json={"name":$("#name2").val(),"age":$("#age2").val()};
			$.ajax({
		        url:"actors/1",
		        type:"PUT",
		        data:JSON.stringify(json),
		        dataType: 'json',  
            	contentType:'application/json;charset=UTF-8', //contentType很重要     
		        success: function(data) {
			        alert("更新成功，为"+JSON.stringify(data));
			    }      
		      });
			});
		
	});
</script>