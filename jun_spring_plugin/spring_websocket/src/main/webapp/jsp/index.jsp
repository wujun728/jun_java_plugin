<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页[spring-websocket demo]</title>
<style type="text/css">
body {background: #E2E2E2; font-size:13.5px;}
div[message] { background: #5170ad; width: 400px; height: 600px; border: 1px solid #ccc; color: #fff; overflow-y: auto; }
ul, li { list-style-type: square;}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/sockjs.min.js"></script>
<script type="text/javascript">
	var PATH = "${pageContext.request.contextPath}";
	var websocket = null;
	if (window['WebSocket']) 
		// ws://host:port/project/websocketpath
		websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket');
	else
		websocket = new new SockJS(PATH + '/websocket/socketjs');
	
	websocket.onopen = function(event) {
		console.log('open', event);
	};
	websocket.onmessage = function(event) {
		console.log('message', event.data);
		$('div[message] > ul').append('<li>' + event.data + '</li>');
	};
</script>

</head>
<body>
    <div message>
        <ul>
        </ul>
    </div>
</body>
</html>
