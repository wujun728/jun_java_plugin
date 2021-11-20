<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>rental table</title>
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
  		<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Mysql sakila database</a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">单表查询<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="showactor">actor</a></li>
            <li><a href="showrental">rental</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">跨表查询<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="country_city">country和city</a></li>
            <li><a href="staff_store">staff和store</a></li>
          </ul>
        </li>
        <li><a href="fileupload">fileupload</a></li>
        <li><a href="logout">logout</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
  		</div>
  	</div>
  	<div class="row">
  		<div class="col-md-12">
  		<h2>rental表的信息</h2>
  		<button type="button" id="add" class="btn btn-primary"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add</button>
  		    <table id="grid-data" class="table table-hover table-striped">
        <thead>
            <tr>
                <th data-column-id="id"  data-identifier="true" data-type="numeric">ID</th>
                <th data-column-id="rental_date">rental_date</th>
                <th data-column-id="inventory_id">inventory_id</th>
                <th data-column-id="customer_id">customer_id</th>
                <th data-column-id="return_date">return_date</th>
                <th data-column-id="staff_id">staff_id</th>
                <th data-column-id="last_update">last_update</th>
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
    url:"rentallist",
        formatters: {
        "commands": function(column, row)
        {
            return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\">编辑<span class=\"glyphicon glyphicon-pencil\"></span></button> "+ 
                "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\"" + row.id + "\">删除<span class=\"glyphicon glyphicon-trash\"></span></button>";
        }
    	}
    }).on("loaded.rs.jquery.bootgrid", function()
	{
	    grid.find(".command-edit").on("click", function(e)
	    {
	        $(".rentalmodal").modal();
	        $.post("getRentalInfo",{id:$(this).data("row-id")},function(data){
	        	$("#rental_date").val(data.rental.rental_date);
	        	$("#inventory_id").val(data.rental.inventory_id);
	        	$("#customer_id").val(data.rental.customer_id);
	        	$("#return_date").val(data.rental.return_date);
	        	$("#staff_id").val(data.rental.staff_id);
	        	$("#last_update").val(data.rental.last_update);
	        	$("#rentalid").val(data.rental.id);
	        });
	    }).end().find(".command-delete").on("click", function(e)
    {
        $.post("deleterental",{id:$(this).data("row-id")},function(){
        	alert("删除成功");
        	location.reload();
        });
    });
  	});
  });
  </script>
  <div class="modal fade rentalmodal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">update rental</h4>
      </div>
      <form action="updaterental" method="post">
      <div class="modal-body">
        <div class="form-group">
    		<label for="rental_date">rental_date</label>
    		<input type="text" name="rental.rental_date" class="form-control" id="rental_date">
  		</div>
  		<div class="form-group">
    		<label for="inventory_id">inventory_id</label>
    		<input type="text" name="rental.inventory_id" class="form-control" id="inventory_id">
  		</div>
  		<div class="form-group">
    		<label for="customer_id">customer_id</label>
    		<input type="text" name="rental.customer_id" class="form-control" id="customer_id">
  		</div>
  		<div class="form-group">
    		<label for="return_date">return_date</label>
    		<input type="text" name="rental.return_date" class="form-control" id="return_date">
  		</div>
        <div class="form-group">
    		<label for="staff_id">staff_id</label>
    		<input type="text" name="rental.staff_id" class="form-control" id="staff_id">
  		</div>
  		<div class="form-group">
    		<label for="last_update">last_update</label>
    		<input type="text" name="rental.last_update" class="form-control" id="last_update">
    		<input type="hidden" id="rentalid" name="rental.id" />
  		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Save changes</button>
      </div>
      </form>
    </div>
  </div>
</div>

  <div class="modal fade rentalinsert" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">insert rental</h4>
      </div>
      <form action="insertrental" method="post">
      <div class="modal-body">
        <div class="form-group">
    		<label for="rental_date2">rental_date</label>
    		<input type="text" name="rental.rental_date" class="form-control" id="rental_date2">
  		</div>
  		<div class="form-group">
    		<label for="inventory_id2">inventory_id</label>
    		<input type="text" name="rental.inventory_id" class="form-control" id="inventory_id2">
  		</div>
  		<div class="form-group">
    		<label for="customer_id2">customer_id</label>
    		<input type="text" name="rental.customer_id" class="form-control" id="customer_id2">
  		</div>
  		<div class="form-group">
    		<label for="return_date2">return_date</label>
    		<input type="text" name="rental.return_date" class="form-control" id="return_date2">
  		</div>
        <div class="form-group">
    		<label for="staff_id2">staff_id</label>
    		<input type="text" name="rental.staff_id" class="form-control" id="staff_id2">
  		</div>
  		<div class="form-group">
    		<label for="last_update2">last_update</label>
    		<input type="text" name="rental.last_update" class="form-control" id="last_update2">
  		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Save</button>
      </div>
      </form>
    </div>
  </div>
</div>
  </body>
  <script type="text/javascript">
  	 $("#rental_date").datetimepicker({format: 'yyyy-mm-ddThh:ii:ss'});
  	 $("#add").click(function(){
  	 	$(".rentalinsert").modal();
  	 });
  </script>
</html>
