<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/ftl/css/bootstrap.min.css">
<link rel="stylesheet" href="/ftl/css/laypage.css">

<script src="/ftl/js/jquery/jquery.min.js"></script>
<script src="/ftl/js/jquery.validation/1.14.0/jquery.validate.min.js"></script>
<script src="/ftl/js/jquery.validation/1.14.0/messages_zh.min.js"></script>
<style>
	input.error{
		border: 1px solid #E6594E;
	}
</style>
</head>
<body>
	  <div class="container">
	
		<form id="editUserForm">
		  <br/>
		  <div class="form-group">
			<label for="username">用户名称：</label>
			<input type="text" class="form-control" id="username" name="username" value="${data.userName}" placeholder="输入名称">
		  </div>
		  <div class="form-group">
			<label for="userage">用户年龄：</label>
			<input type="text" class="form-control" id="userage" name="userage" value="${data.age}" placeholder="输入年龄">
		  </div>
		  <div class="form-group">
		  <button type="button" id="saveBtn" class="btn btn-success">提交</button>
		  <button type="button" id="cancelBtn" class="btn btn-default">取消</button>
		  </div>
		</form>
    </div>

		<script>
			var editUser = function(){
				if(!check().form()){ 
					return;  
				}
				$.ajax({
					   type: "GET",
					   dataType: "json",
					   url: "/edit",
					   data: {
							"id": ${data.id},						
							"userName": $("#username").val(),
							"age":$("#userage").val()
					   },
					   success: function(msg){
							$('#cancelBtn').click();			
					   }
				});
			}
			
			$('#saveBtn').on('click', function(){
				editUser();
			});
			
			$('#cancelBtn').on('click', function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.getUserPageList();
				parent.layer.close(index);
			});

			
			//校验字段是否正确 
            function check(){ 
                /*返回一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/ 
                return $("#editUserForm").validate({ 
                            rules:{ 
                                username:{ 
                                    required:true
                                },
                                userage:{ 
                                    required:true                                   
                                }
                            }, 
                            messages:{ 
                                username:{ 
                                    required:""
                                },
                                userage:{ 
                                    required:""                                
                                }
                            }     
                        }); 
            } 
			
		</script> 	
	
</body>