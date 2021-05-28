/**
 * showactors
 */
 $(document).ready(function(){
 var grid =$("#grid-data").bootgrid({
    ajax:true,
    url:"/actors",
     ajaxSettings: {
        method: "GET",
        cache: false
    },
        formatters: {
        "commands": function(column, row)
        {
            return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\">编辑<span class=\"glyphicon glyphicon-pencil\"></span></button> "
            +"<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\'" + row.id + "\'>删除<span class=\"glyphicon glyphicon-trash\"></span></button> "; 
        }
    	}
    }).on("loaded.rs.jquery.bootgrid", function()
	{
    /* Executes after data is loaded and rendered */
	    grid.find(".command-edit").on("click", function(e)
	    {
	        $(".actormodal").modal();
	        $.get("/actors/"+$(this).data("row-id"),function(str){
	        	$("#fname").val(str.first_name);
	        	$("#lname").val(str.last_name);
	        	$("#lupdate").val(str.last_update);
	        	$("#actorid").val(str.id);
	        });
	    }).end().find(".command-delete").on("click", function(e)
		    {
		        $.ajax({  
			        url : "/actors/"+$(this).data("row-id"),  
			        type : "DELETE",  
			        success : function(result) { 
			        	alert("删除成功");
		        		$("#grid-data").bootgrid("reload");
		        		}
		    	});
		    	});
		    	});
  
 
  		$("#add").click(function(){
  	 		$(".addmodal").modal();
  	 	});
  	 	
  	 	 $("#lupdate").datetimepicker({
  	 	 format: 'yyyy-mm-dd hh:ii:ss',
  	 	 autoclose: true
  	 	 });
  	 	 $("#lupdate").datetimepicker('setStartDate', '2012-01-01 0:0:0');
  	 	 
  	 	 $("#lupdate2").datetimepicker({
  	 	 format: 'yyyy-mm-dd hh:ii:ss',
  	 	 autoclose: true
  	 	 });
  	 	 $("#lupdate2").datetimepicker('setStartDate', '2012-01-01 0:0:0');
  });
 