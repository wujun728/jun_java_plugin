<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Spring-activeMQ</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
<div class="container">
	<div class="row">
	  <div class="col-md-12">
	  		<div id="log-container" class="well" style="height:200px;color: #aaa;background: #333;overflow-y: scroll">
	  			<div>
	  			控制台输出:<br>
        		</div>
	  		</div>
	  </div>
	</div>
  <div class="row">
	  <div class="col-md-12">
	  	<div class="panel panel-success">
			<div class="panel-heading">生产者-向队列生产消息</div>
			<div class="panel-body">
						<form id="f1" class="form-horizontal" role="form">
							<div class="form-group">
								<label for="mailid" class="col-sm-2 control-label">mailid</label>
								<div class="col-sm-10">
									<input type="text" name="mailId" class="form-control" id="mailid" placeholder="mailid">
								</div>
							</div>
							<div class="form-group">
								<label for="country" class="col-sm-2 control-label">country</label>
								<div class="col-sm-10">
									<input type="text" name="country" class="form-control" id="country" placeholder="country">
								</div>
							</div>
							<div class="form-group">
								<label for="weight" class="col-sm-2 control-label">weight</label>
								<div class="col-sm-10">
									<input type="text" name="weight" class="form-control" id="weight" placeholder="weight">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button id="produce" type="button" class="btn btn-default">生产消息</button>
								</div>
							</div>
						</form>
					</div>
</div>
	  </div>
  </div>
  
  <div class="row">
	  <div class="col-md-12">
	  	<div class="panel panel-success">
			<div class="panel-heading">发布-向话题发布消息</div>
			<div class="panel-body">
						<form id="f2" class="form-horizontal" role="form">
							<div class="form-group">
								<label for="mailid" class="col-sm-2 control-label">mailid</label>
								<div class="col-sm-10">
									<input type="text" name="mailId" class="form-control" id="mailid" placeholder="mailid">
								</div>
							</div>
							<div class="form-group">
								<label for="country" class="col-sm-2 control-label">country</label>
								<div class="col-sm-10">
									<input type="text" name="country" class="form-control" id="country" placeholder="country">
								</div>
							</div>
							<div class="form-group">
								<label for="weight" class="col-sm-2 control-label">weight</label>
								<div class="col-sm-10">
									<input type="text" name="weight" class="form-control" id="weight" placeholder="weight">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button id="address" type="button" class="btn btn-default">发布消息</button>
								</div>
							</div>
						</form>
					</div>
</div>
	  </div>
  </div>
</div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
	  $("#produce").click(function(){
	    $.post("produce",$("#f1").serialize(),function(){
	    	alert("生产成功");
	    });
	  });
	  
	  $("#address").click(function(){
	    $.post("topic",$("#f2").serialize(),function(){
	    	alert("发布成功");
	    });
	  });
	});

</script>
<script>
    $(document).ready(function() {
        // 指定websocket路径
        var com.kind.mq.websocket = new WebSocket('ws://localhost:8080/Spring-activeMQ/ws');
        websocket.onmessage = function(event) {
       	 var data=JSON.parse(event.data);
            // 接收服务端的实时日志并添加到HTML页面中
            $("#log-container div").append(data.text + "<p> </p>");
            // 滚动条滚动到最低部
            $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
        };
    });
</script>
  </body>
</html>