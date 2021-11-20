<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>搜索</title>
    <script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/jquery.bootgrid.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="./js/jquery.bootgrid.min.js"></script>
  </head>
  <body>
    <div class="container" style="margin-top: 50px">
  	<div class="row">
  		<div class="col-md-6 col-md-offset-3">
  			<img src="./css/pic.jpg" />
  		</div>
  		<div class="col-md-10">
  			<input type="text" class="form-control" id="info" placeholder="请输入搜索内容">
  		</div>
  		<div class="col-md-2">
  		<button id="search" type="button" class="btn btn-primary">搜索</button>
  		</div>
  	</div>
  	<div class="row">
  		<div class="col-md-12" id="grid">
  		    <table id="grid-data" class="table table-condensed table-hover table-striped"><thead><tr>
	      	<th data-column-id="file">file</th><th data-column-id="size">size</th>
	       	<th data-column-id="commands" data-formatter="commands">content</th></tr></thead></table>
  		</div>
  	</div>
  </div>
<script>
$(document).ready(function(){
	$("#grid-data").bootgrid({
		    navigation:2,
		    url:"getdoclist",
		    ajax:true,
		    formatters: {
		        "commands": function(column, row)
		        {
		            return row.content.substr(0,1000); 
		        }
		    }
		    });
	$("#search").click(function(){
		if($("#info").val()=="")
		alert("请输入搜索词");
		else
		{
		    $("#grid-data").bootgrid("search", $("#info").val());
		}
	
   
    });
  });
  </script>
  </body>
  </body>
</html>
