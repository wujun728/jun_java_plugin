<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${BASE_PATH}/public/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/highcharts/highcharts.js"></script>
<!--[if IE]>
	<script type="text/javascript" src="${BASE_PATH}/public/highcharts/excanvas.compiled.js"></script>
<![endif]-->	
<style type="text/css">
	.all{
		padding: 2px 0px 2px 2px;
	}
 	.cpu{
		border: 1px solid #ccc;
		width: 100%; 
		height: 350px;
	}
	.mem{
		margin-top:4px;
		border: 1px solid #ccc;
		width: 100%; 
		height: 350px;
	}
	.title{
		color: #595757;
		font-size: 14px;
		width:100%;
		margin-left:15px;
		font-family: Tahoma,Verdana,微软雅黑,新宋体;
	}
	.titleCon{
		padding-top:5px;
		width:100%;
		background-color: #77C7F1;
		height: 35px;
	}
	#cpuContain{
		margin: 0px 0px 2px 2px;
	}
</style>
<script type="text/javascript">
$(document).ready(function() {	
	 function render(renderTo, ytitle, xtitle, xcategories, series, title,subtitle ){
		var chart = new Highcharts.Chart({			
			chart: {
				renderTo: renderTo,
				height:300,
				defaultSeriesType: 'spline'
			},
			title: {
				text: title
			},
			subtitle: {
				text: subtitle
			},
			xAxis: {
				tickInterval:10,
	            categories : xcategories
			},
			yAxis: {
				title: {
					text: ytitle
				}
			},
			legend: {
				enabled: false
			},
			lang: {
	            noData: "No Data!"
	        },
	        noData: {
	            style: {
	                fontWeight: 'bold',
	                fontSize: '15px',
	                color: '#303030'
	            }
	        },
	        credits: {
	            enabled: false
	        },
			tooltip: {
				formatter: function() {
		                return  getTooltip(this);
				}
			},
			series: series
		});	
		
	}
	
	function getTooltip(obj){
		return '<b>服务器：'+ obj.point.series.name +'</b><br/><b>时间：'+obj.point.name+'</b><br/><b>数据值: '+ obj.point.y+'</b>';
	}
	
	function init(){
		var serverIp = $('#serverIp').val();
		$.getJSON("${BASE_PATH}/servers/getVmData.do?ip="+serverIp+"&_t="+new Date().getTime(),function(datas){
			
			var results = eval(datas);
			var xcategories = results.xcategories;
			var cpuUseTemp = results.cpuUse;
			var memUseTemp = results.memUse;
			var cpuUse = getMemCpuUseArr(cpuUseTemp, xcategories);
			var memUse = getMemCpuUseArr(memUseTemp, xcategories);
			var cpuSeries = [{
								name: serverIp,
				     	        data: cpuUse
			     	        }];
			var memSeries = [{
								name: serverIp,
		     	        		data: memUse
     	        		}];
			
			render('cpuContain','CPU占用率','Time',xcategories, cpuSeries, '');
			render('memContain','内存使用量(MB)','Time',xcategories, memSeries, '');
			
		})
		
		
		
	}
	
	function getMemCpuUseArr(obj, xcategories){
		var result=[];
		for (var i = 0; i < obj.length; i++) {
			var tempArr=[];
			tempArr[0] = xcategories[i];
			tempArr[1] = obj[i];
			result.push(tempArr);
		}
		return result;
	}
	
	init();
	window.setInterval(function(){
		init();
	}, 30*1000);
	window.onresize = function(){
		init();
	} 
});
</script>
</head>
<body>
	<input type="text" style="display:none;" id="serverIp" value="${serverIp }"/>
	<div class="all">
		<div class="top">
			<div class="cpu">
				<div class="titleCon">
					<span class="title">CPU</span>
				</div>
				<div id="cpuContain">
				</div>
			</div>
		</div>
		<div style="clear:both"></div>
		<div class="mem">
			<div class="titleCon"> 
				<span class="title">内存</span>
			</div>
			<div id="memContain">
			</div>
		</div>
	</div>
</body>
</html>
