<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Spring连接mongodb</title>
    <script src="/Spring-mongoDB/js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="/Spring-mongoDB/css/jquery.bootgrid.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/Spring-mongoDB/js/jquery.bootgrid.min.js"></script>
  </head>
  <body>
    <div class="container">
  	<div class="row">
  		<div class="col-md-12">
<nav class="navbar navbar-inverse" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Spring-mongoDB</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="index">MongoDB</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

  		</div>
  	</div>
  	<div class="row">
  		<div class="col-md-12">
  		<h2>对MongoDB进行CRUD</h2>
  	<button type="button" id="add" class="btn btn-primary"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add</button>
  	<br>
  	<div class="row">
  	<form class="form-horizontal" role="form">
  <div class="form-group">
    <label for="filename" class="col-sm-2 control-label">filename</label>
    <div class="col-sm-2">
      <input type="text" class="form-control" id="filename2" placeholder="filename">
    </div>
    <button type="button" id="search" class="btn btn-primary">search<span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
  </div>
</form>
  	</div>
  	<table id="grid-data" class="table table-condensed table-hover table-striped">
        <thead>
            <tr>
                <th data-column-id="id"  data-identifier="true">id</th>
                <th data-column-id="filename" data-sortable="false">filename</th>
                <th data-column-id="path" data-sortable="false">path</th>
                <th data-column-id="size" data-sortable="false">size</th>
                <th data-column-id="commands" data-formatter="commands" data-sortable="false">modify</th>
            </tr>
        </thead>
    </table>
  		</div>
  	</div>
  </div>
<script>
$(document).ready(function(){
    var grid =$("#grid-data").bootgrid({
    ajax:true,
    url:"pictures",
    navigation:2,
    formatters: {
    "commands": function(column, row)
     {
         return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-size=\""+row.size+"\" data-filename=\""+row.filename+"\" data-path=\""+row.path+"\" data-row-id=\"" + row.id + "\">编辑<span class=\"glyphicon glyphicon-pencil\"></span></button> "+ 
                "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.id + "\">删除<span class=\"glyphicon glyphicon-trash\"></span></button>";
     }
    },
    ajaxSettings: {
        method: "GET",
        cache: false
    }
    }).on("loaded.rs.jquery.bootgrid", function()
	{
	    grid.find(".command-edit").on("click", function(e)
	    {
	        $(".modal").modal();
	        if($("#id").length <= 0)//不存在隐藏域
	        {
	        	$("form").append('<input type="hidden" id="id" name="id" />  ');
	        }
	        $("#filename").val($(this).data("filename"));  
	        $("#path").val($(this).data("path"));  
	        $("#size").val($(this).data("size")); 
	        $("#id").val($(this).data("row-id"));  
	        
	    }).end().find(".command-delete").on("click", function(e)
    {
         $.ajax({url:"picture/"+$(this).data("row-id"),type:"DELETE",success:function(result){
       		 alert("删除成功");
       		 history.go(0);
    	 }});
    });
  	});
  	$("#search").click(function(){
  		$("#grid-data").bootgrid("search", $("#filename2").val());
  	});
  	
  	
  });
  function trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}
  </script>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">save or update</h4>
      </div>
      <form method="post" action="picture">
      <div class="modal-body">
        <div class="form-group">
    		<label for="rental_date2">filename</label>
    		<input type="text" name="filename" class="form-control" id="filename">
  		</div>
  		<div class="form-group">
    		<label for="path">path</label>
    		<input type="text" name="path" class="form-control" id="path">
  		</div>
  		<div class="form-group">
    		<label for="size">size</label>
    		<input type="text" name="size" class="form-control" id="size">
  		</div>
      </div>
      <input type="hidden" id="id" name="id" />  
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" id="save" class="btn btn-primary">Save</button>
      </div>
      </form>
    </div>
  </div>
</div>  
  </body>
</html>
<script type="text/javascript">
  	 $("#add").click(function(){
  	 	$(".modal").modal();
  	 	//注意添加时，要移除id的隐藏域，它是给update操作用的
  	 	$("#id").remove();
  	 });
  </script>