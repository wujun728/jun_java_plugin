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
