$(document).ready(function(){
	////更新导航栏
	if($('#nav_activities')){
		var nav = $('#nav_activities');
		/** <li class="active">申请</li>
		  <li><a href="javascript:void(0)">手机号验证</a></li>
		  <li><a href="javascript:void(0)">身份证验证</a></li>**/
		$.ajax({
			url:"http://localhost:18083/jbpm-study/leave/tasks/aa",
			type:'GET',
			data:{},
			success:function(data){
				var active = data.active;
				var activities = data.activities;
				var tmpl = "";
				for(var i=0;i<activities.length;i++){
				    var ac = activities[i];
				    if(active==ac){
				    	tmpl += '<li class="active">'+ac+'</li>';
				    }else{
				    	tmpl += '<li><a href="javascript:void(0)">'+ac+'</a></li>';
				    }
				}
				
				nav.append(tmpl);
			}
		})
	}
	
	$("#end").click(function(){
		$.ajax({
			url:$(this).data("url"),
			type:'GET',
			data:{},
			success:function(data){
				console.log(data)
				window.parent.closeModel(true);
			}
		})
		
	});
	
	$('#photoButton').click(function(){
		var url = "http://localhost:18083/jbpm-study/leave/doManager";
		$.ajax({
			type : "GET",
			url:url,
			dataType:"json",
			data:$('#photoForm').serialize(),
			success:function(data){
				console.log(data)
				if(data.isEnd=="true"){
					window.parent.closeModel(true);
				}else{
					window.location.href = "http://localhost:18083/jbpm-study/" + data.url;
				}
			}
			
		});
	});
	
});


