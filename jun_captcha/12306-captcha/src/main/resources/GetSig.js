var ws = new WebSocket("ws://127.0.0.1:9082/v2/getCheckWebScoket");
 
ws.onopen = function(evt) {
	 console.log("Connection open ...");
	
};
 
ws.onmessage = function(evt) {
	  var obj = evt.data.split("&");
	  var result = document.getElementById("pluginId").GetSig(obj[1]);
	  var ret = obj[0]+"&"+result;
	  console.log(ret)
	  ws.send(ret);
};
 
ws.onclose = function(evt) {
	console.log("Connection closed.");
};

ws.onclose = function () {
    reconnect();
};
ws.onerror = function () {
    reconnect();
};
