<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="//cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
	<script src="//echarts.baidu.com/dist/echarts.js"></script>
	<script type="text/javascript">
		var get=function(){
			var myChart = echarts.init(document.getElementById('main'));
			$.ajax({
				type:"post",
				url:"/ajax",
				contentType:"application/json",
				dataType:'json',
				date:{},
				success:function(msg){
					 // 指定图表的配置项和数据
					var option =  msg;
			        // 使用刚指定的配置项和数据显示图表。
			        myChart.setOption(option);
				}
			   })
		}
	</script>
</head>
<body>
	<div>
		<button onclick="get()">生成报表</button>
		 <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
   		 <div id="main" style="width: 600px;height:400px;"></div>
	</div>
</body>
</html>