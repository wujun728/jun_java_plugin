<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/ftl/css/bootstrap.min.css">
<link rel="stylesheet" href="/ftl/css/laypage.css">

<script src="/ftl/js/vue/vue.min.js"></script>
<script src="/ftl/js/jquery/jquery.min.js"></script>
<script src="/ftl/js/laypage/laypage.js" charset="utf-8"></script>
<script src="/ftl/js/layer/layer.js" charset="utf-8"></script>
</head>
<body>
	<div id="app" class="container">
		${Session["user"].userName}，欢迎你
		
		<form class="form-inline bg-danger" role="form">			
		  查询条件：
		  <div class="form-group">
		    <label class="sr-only" for="username">用户名称</label>
		    <input type="text" class="form-control" id="username" placeholder="用户名称">
		  </div>
		  <div class="form-group">
		    <label class="sr-only" for="userage">用户年龄</label>
		    <input type="text" class="form-control" id="userage" placeholder="用户年龄">
		  </div>
		  <button type="button" id="findUser" class="btn btn-success">查询用户</button>
		  <button type="button" id="addUserBtn" class="btn btn-danger">增加用户</button>
		</form>
		
		<div class="table-responsive">		
		 	<table class="table">
		 		<thead>
					<tr class="success">						
						<td>用户</td>		
						<td>年龄</td>				
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<tr class="active" v-for="(item,index) in result">
 						<td>{{item.userName}}</td>
						<td>{{item.age}}</td>
						<td><a href="#" @click="editEvent(item.id)">编辑</a>    <a href="#" @click="delEvent(item.id)">删除</a></td>						
					</tr>
				</tbody>
				<tr>
					<td colspan="3"><div id="pagenav"></div></td>
				</tr>
			</table>
			
		</div>
	</div>
	<script>
		var app = new Vue({
			el : '#app',
			data : {
				result : []
			}
		});
	
	
		//查询用户数据
		var getUserPageList = function(curr) {
			$.ajax({
				type : "GET",
				dataType : "json",
				url : "/getPage",
				data : {
					pageNum:curr || 1,
					pageSize:5,
					userName:$("#username").val()
				},
				success : function(msg) {
					app.result=msg.page;
					laypage({
						cont : 'pagenav', //容器。值支持id名、原生dom对象，jquery对象,
						pages : msg.totalPage, //总页数
						first : '首页',
						last : '尾页',
						curr : curr || 1, //当前页

						jump : function(obj, first) { //触发分页后的回调

							if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr

								getUserPageList(obj.curr);
							}
						}
					});
					
					
					
				}
			});
		}
		
		getUserPageList();

		//查询用户事件

		$('#findUser').on('click', function() {
			getUserPageList();
		});

		
		//编辑用户事件

		var editEvent = function(id) {
			layer.open({
				type : 2,
				title : '编辑用户',
				fix : false,
				maxmin : true,
				shadeClose : true,
				area : [ '1100px', '600px' ],
				content : '/editpage?id='+id,
				end : function() {
					getUserPageList();
				}
			});
		}
	
		//增加用户事件

		$('#addUserBtn').on('click', function() {			
			layer.open({
				type : 2,
				title : '添加用户',
				fix : false,
				maxmin : true,
				shadeClose : true,
				area : [ '1100px', '600px' ],
				content : '/ftl/add.html',
				end : function() {
					getUserPageList();
				}
			});
			
		});
		
		
		//删除用户事件
		var delEvent = function(id) {
			//询问框
			layer.confirm('您确定要删除么？', {
				btn : [ '是', '否' ]
			//按钮
			}, function() {
					//是
					$.ajax({
						type : "GET",
						dataType : "json",
						url : "/del",
						data : {
							id:id
						},
						success : function(msg) {
							getUserPageList();
							layer.msg('已经成功删除记录' + id,{icon:5});
						}
					});
	
			}, function() {
				//否

			});
		}
		
		
		
		
	</script>
	
	
</body>
</html>