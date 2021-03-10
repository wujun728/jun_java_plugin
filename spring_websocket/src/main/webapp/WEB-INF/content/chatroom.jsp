<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>聊天室</title>
    <script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<style>
body{
	margin-top:5px;
}
</style>
</head>
  <body>
    <div class="container">
    	<div class="row">
    		<div class="col-md-3">
    		<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title">当前登录用户</h3>
				  </div>
				  <div class="panel-body">
				    <div class="list-group">
					 <a href="#" class="list-group-item">你好，${sessionScope.username}</a>
					 <a href="logout" class="list-group-item">退出</a>
					</div>
				  </div>
				</div>
    			<div class="panel panel-primary" id="online">
				  <div class="panel-heading">
				    <h3 class="panel-title">当前在线的其他用户</h3>
				  </div>
				  <div class="panel-body">
				    <div class="list-group" id="users">
					</div>
				  </div>
				</div>
				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title">群发系统广播</h3>
				  </div>
				  <div class="panel-body">
				    <input type="text" class="form-control"  id="msg" /><br>
				    <button id="broadcast" type="button" class="btn btn-primary">发送</button>
				  </div>
				</div>
    		</div>
  			<div class="col-md-9">
  				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title" id="talktitle"></h3>
				  </div>
				  <div class="panel-body">
				    <div class="well" id="log-container" style="height:400px;overflow-y:scroll">
				    
				    </div>
				    	<input type="text" id="myinfo" class="form-control col-md-12" /> <br>
				    	<button id="send" type="button" class="btn btn-primary">发送</button>
				    </div>
				</div>
  			</div>
    	</div>
    </div> 
<script>
    $(document).ready(function() {
        // 指定websocket路径
        var websocket;
        if ('WebSocket' in window) {
			websocket = new WebSocket("ws://localhost:8080/Spring-websocket/ws?uid="+${sessionScope.uid});
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://localhost:8080/Spring-websocket/ws"+${sessionScope.uid});
		} else {
			websocket = new SockJS("http://localhost:8080/Spring-websocket/ws/sockjs"+${sessionScope.uid});
		}
        //var websocket = new WebSocket('ws://localhost:8080/Spring-websocket/ws');
        websocket.onmessage = function(event) {
       	 var data=JSON.parse(event.data);
            // 接收服务端的实时消息并添加到HTML页面中
            $("#log-container").append("<div class='bg-info'><label class='text-danger'>"+data.fromName+"&nbsp;"+data.date+"</label><div class='text-success'>"+data.text+"</div></div><br>");
            // 滚动条滚动到最低部
            scrollToBottom();
        };
        $.post("onlineusers",function(data){
    		for(var i=0;i<data.length;i++)
    			$("#users").append('<a href="#" onclick="talk(this)" class="list-group-item">'+data[i]+'</a>');
    	});
        
        $("#broadcast").click(function(){
        	$.post("broadcast",{"text":$("#msg").val()});
        });
        
        $("#send").click(function(){
        	$.post("getuid",{"username":$("body").data("to")},function(d){
        		var v=$("#myinfo").val();
        		
				if(v==""){
					return;
				}else{
					var data={};
					data["from"]="${sessionScope.uid}";
					data["fromName"]="${sessionScope.username}";
					data["to"]=d.uid;
					data["text"]=v;
					websocket.send(JSON.stringify(data));
					$("#log-container").append("<div class='bg-success'><label class='text-info'>我&nbsp;"+new Date()+"</label><div class='text-info'>"+v+"</div></div><br>");
					scrollToBottom();
					$("#myinfo").val("");
				}
        	});
        	
        });
        
    });
   
   function talk(a){
   	$("#talktitle").text("与"+a.innerHTML+"的聊天");
   	$("body").data("to",a.innerHTML);
   }
   function scrollToBottom(){
		var div = document.getElementById('log-container');
		div.scrollTop = div.scrollHeight;
	}
</script>    
    
  </body>
</html>
