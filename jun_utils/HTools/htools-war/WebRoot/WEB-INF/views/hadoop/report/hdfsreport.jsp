<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop图形报表  - Hadoop集群列表</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/report/list">图形报表</a><span class="divider">/</span></li>
  <li class="active"> Hadoop集群主机:${hostname}</li>
</ul>
<div>
	<div id="main" style="height:400px;border:1px solid #ccc;padding:10px;"></div>
</div>
<script type="text/javascript" src="/static/echarts/js/require.js"></script>
<script type="text/javascript">
require.config({
    paths:{ 
        echarts:'/static/echarts/js/echarts',
        'echarts/chart/bar' : '/static/echarts/js/echarts',
        'echarts/chart/line': '/static/echarts/js/echarts',
        'echarts/chart/pie': '/static/echarts/js/echarts'
    }
});
require(
    [
        'echarts',
        'echarts/chart/bar',
        'echarts/chart/line',
        'echarts/chart/pie'
    ],
    function(ec) {
        var dfsChart = ec.init(document.getElementById('main'));
        	//Hadoop HDFS使用情况饼图
            option = {
            	    title : {
            	        text: 'Hadoop HDFS使用情况',
            	        subtext: '',
            	        x:'center'
            	    },
            	    tooltip : {
            	        trigger: 'item',
            	        formatter: "{a} <br/>{b} : {c}B ({d}%)"
            	    },
            	    legend: {
            	        orient : 'vertical',
            	        x : 'left',
            	        data:['DFS使用量','非DFS使用量','DFS剩余量']
            	    },
            	    toolbox: {
            	        show : true,
            	        feature : {
            	            mark : true,
            	            dataView : {readOnly: false},
            	            restore : true,
            	            saveAsImage : true
            	        }
            	    },
            	    calculable : true,
            	    series : [
            	        {
            	            name:'HDFS使用情况',
            	            type:'pie',
            	            radius : [0, 110],
            	            center: [,225],
            	            data:[
            	                {value:${report.dfsUsedByte}, name:'DFS使用量'},
            	                {value:${report.dfsUnUsedByte}, name:'非DFS使用量'},
            	                {value:${report.remainingSumByte}, name:'DFS剩余量'}
            	            ]
            	        }
            	    ]
            	};
        dfsChart.setOption(option);
    }
);
</script>
</body>
</html>