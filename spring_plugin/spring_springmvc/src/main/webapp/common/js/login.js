var NUC={
		load:function(){
			$.get("/getusers",function(data){
				var tbody=$("table>tbody");
				$.each(data,function(i,item){
					var tr=$("<tr></tr>");
					$("<td></td>").html(item.id).appendTo(tr);
					$("<td></td>").html(item.name).appendTo(tr);
					$("<td></td>").html(item.money).appendTo(tr);
					$("<td></td>").html(item.生日).appendTo(tr);
					$("<td></td>").html(
						$("<a></a>").html("删除").attr("href","/user/"+item.id+"?_method=DELETE")		
					).appendTo(tr);
					//tbody.append(tr);
					tr.appendTo(tbody);
				})
			},"json")
		$("form>input[name=username]").blur(function(e){
			$.get("/jackson",{username:$(this).val()},function(data){
				$(".username_inf").html(data.message).css("color",data.code==200? "green":"red");
			},"json")
		})
		
		/* 参数较少的情况 */
		 /* $("form>input[type=button]").click(
				function(e){
					var username=$("input[name=username]").val();
					var password=$("input[name=password]").val(); */
					/* var param={"username":username,"password":password} */
					/* var param="username="+username+"&password="+password; */
					/* var param=$("form").serialize();//返回 key=value格式
					var param=$("form").serializeArray();//返回json数组对象 */
					/* var param = '{"username":"kangli","password":"123"}' */
					/* $.post("/register",param,function(data){
						alert(data.message);
					},"json")
				})  */
		$("form>input[type=button]").click(
				function(e){
					var param = $("form").serializeJson();
					/*var param = '{"username":"kangli","password":"123"}'*/
					$.ajax({
						url:NUC.url.geturl(),
						type:"post",
						dataType:"json",
						data:param,
						contentType:"application/json",
						success:function(data){
							alert(data.message);
						}
					})
				})
	},
	url:{
		geturl:function(){
			return "/register";
		}
	}
}