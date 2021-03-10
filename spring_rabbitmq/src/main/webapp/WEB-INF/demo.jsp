<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Spring-rabbitMQ</title>
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
	  <div class="col-md-6">
	  	<div class="panel panel-danger">
			<div class="panel-heading">生产者-消费者模型，向队列生产消息</div>
			<div class="panel-body">
			<img src="./img/1.png" width="100%" alt="生产者-消费者模型" class="img-rounded">
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
	  <div class="col-md-6">
	  	<div class="panel panel-success">
			<div class="panel-heading">发布-订阅模型，向所有队列广播消息</div>
			<div class="panel-body">
			<img src="./img/2.png"  width="100%" alt="发布-订阅模型" class="img-rounded">
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
  
  <div class="row">
	  <div class="col-md-6">
	  	<div class="panel panel-info">
			<div class="panel-heading">直连交换机模型，向指定的routing key发送消息</div>
			<div class="panel-body">
			<img src="./img/3.png" width="100%" alt="发布-订阅模型" class="img-rounded">
						<form id="f3" class="form-horizontal" role="form">
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
								<label for="weight" class="col-sm-3 control-label">Routing key</label>
								<div class="col-sm-9">
									<select class="form-control" name="routingkey">
									  <option value="orange">orange</option>
									  <option value="black">black</option>
									  <option value="green">green</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button id="direct" type="button" class="btn btn-default">发布消息</button>
								</div>
							</div>
						</form>
					</div>
</div>
	  </div>
	  <div class="col-md-6">
	  	<div class="panel panel-default">
			<div class="panel-heading">topic交换机模型，向匹配的routing key发送消息</div>
			<div class="panel-body">
			<img src="./img/4.png" width="100%" alt="发布-订阅模型" class="img-rounded">
						<form id="f4" class="form-horizontal" role="form">
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
								<label for="weight" class="col-sm-3 control-label">Routing key</label>
								<div class="col-sm-9">
									<input type="text" name="routingkey" class="form-control" id="weight" placeholder="routingkey"/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button id="mytopic" type="button" class="btn btn-default">发布消息</button>
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
	  
	  $("#direct").click(function(){
	    $.post("direct",$("#f3").serialize(),function(){
	    	alert("发布成功");
	    });
	  });
	  
	  $("#mytopic").click(function(){
	    $.post("mytopic",$("#f4").serialize(),function(){
	    	alert("发布成功");
	    });
	  });
	});

</script>
  </body>
</html>