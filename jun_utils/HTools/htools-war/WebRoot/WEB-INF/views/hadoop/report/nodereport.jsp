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
        var nodeChart = ec.init(document.getElementById('main'));
      //节点健康状态线形图
    	option = {
        		
    		    title : {
    		        text: '节点健康状态图',
    		        subtext: ''
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['正常节点','故障节点']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : true,
    		            dataView : {readOnly: false},
    		            magicType:['line', 'bar'],
    		            restore : true,
    		            saveAsImage : true
    		        }
    		    },
    		    calculable : true,
    		    xAxis : [
    		        {
    		            type : 'category',
    		            axisLabel : {
    		                formatter: '{value} 时'
    		            },
    		            boundaryGap : false,
    		            data : [<c:forEach var="rep" items="${rep}">${rep.hours},</c:forEach>]
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
    		            splitArea : {show : true}
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'正常节点',
    		            type:'line',
    		            itemStyle: {
    		                normal: {
    		                    lineStyle: {
    		                    	shadowColor : 'rgba(0,0,0,0.4)'
    		                    }
    		                }
    		            },
    		            data:[<c:forEach var="rep" items="${rep}">${rep.healthnodes},</c:forEach>]
    		        },
    		        {
    		            name:'故障节点',
    		            type:'line',
    		            itemStyle: {
    		                normal: {
    		                    lineStyle: {
    		                    	shadowColor : 'rgba(0,0,0,0.4)'
    		                    }
    		                }
    		            },
    		            data:[<c:forEach var="rep" items="${rep}">${rep.badnodes},</c:forEach>]
    		        }
    		    ]
    		};
        nodeChart.setOption(option);
    }
);
</script>
</body>
</html>