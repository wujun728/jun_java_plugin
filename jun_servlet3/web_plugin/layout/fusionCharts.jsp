<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>主页面</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../layout/script.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionCharts.jqueryplugin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionChartsExportComponent.js"></script>
	<script type="text/javascript" src="js/extend/jquery.portal.js"></script>
  	<link rel="stylesheet" href="js/extend/portal.css" type="text/css"></link>
	<script type="text/javascript">
	var $pp;
	$(function(){
		var height=$(document).height();
		var width=$(document).width();
		
		
		$pp=$("#pp");
		$pp.portal({
			width:parseInt($(this).width()*0.9),
			height:$(this).height(),
			border:false,
			fit:true
		});
		/*var $day=$("#everDay");
		$day.panel({
			title:"每日订单量",
			content:'<div style="padding:5px;">Content'+(i+1)+'</div>',
			height:200,
			closable:true,
			collapsible:true
		});
		$('#pp').portal('add', {
			panel:$day,
			columnIndex:"day"
		});*/
		//add();
		$("#chartContainer").css("height",parseInt(height*0.4));
		$("#chartContainer").css("width",parseInt(width*0.5)-2);
		$("#chartContainer2").css("height",parseInt(height*0.4));
		$("#chartContainer2").css("width",parseInt(width*0.5)-22);
		$("#chartContainer3").css("height",parseInt(height*0.4));
		$("#chartContainer3").css("width",parseInt(width*0.5)-22);
		$("#chartContainer4").css("height",parseInt(height*0.4));
		$("#chartContainer4").css("width",parseInt(width*0.5)-22);
		var dataString ='<chart  palette="5" exportEnabled="1" showExportDialog="1" exportAction="download" exportHandler="FCExporter" exportFileName="exportFile" caption="Product Sales" xAxisName="Month" yAxisName="Sales" numberPrefix="$"  rotateValues="1" placeValuesInside="1" forceYAxisValueDecimals="1"  yAxisValueDecimals="2">\n\
			<categories>\n\
				<category label="January" />\n\
				<category label="February" />\n\
				<category label="March" />\n\
				<category label="April" />\n\
				<category label="May" />\n\
				<category label="June" />\n\
			</categories>\n\
			<dataset seriesname="Product A" color="F0807F" showValues="1">\n\
				<set value="8343" />\n\
				<set value="6983" />\n\
				<set value="7658" />\n\
				<set value="8345" />\n\
				<set value="8195" />\n\
				<set value="7684"/>\n\
			</dataset>\n\
			<dataset seriesname="Product B" color="F1C7D2"  showValues="1">\n\
				<set value="2446" />\n\
				<set value="3935" />\n\
				<set value="3452"  />\n\
				<set value="4424" />\n\
				<set value="4925" />\n\
				<set value="4328" />\n\
			</dataset>\n\
		</chart>';
		$("#chartContainer").insertFusionCharts({
			swfUrl: "js/FusionCharts/Column2D.swf",
			//dataSource: "js/FusionCharts/Data.xml",
			//dataFormat: "xmlurl",
			width: parseInt($(this).width()*0.5)-80,
			height: parseInt($(this).height()*0.4),
			id: "myChartJanID",
			dataFormat: "json",
			dataSource: {
			"chart": {
				"exportFormats":"PDF=导出PDF|JPEG=导出JPEG|PNG=导出PNG",
				"exportEnabled" : "1" ,
				"showExportDialog":"1",
				"exportDialogMessage":"正在导出.....",
				"exportDialogColor":"e1f5ff",
				"exportDialogBorderColor":"0372ab",
				"exportDialogFontColor":"0372ab",
				"exportDialogPBColor":"0372ab",
				"exportHandler":"FCExporter",
				"exportFileName":"exportFile",
				//"exportCallback":"FC_Exported", 
				"exportEnabled":"1",
				"exportAtClient":"0",
				"exportAction":"download",
				"caption" : "每周销售额" ,
				"xAxisName" : "Week",
				"yAxisName" : "Sales",
				"numberPrefix" : "￥",
				"useRoundEdges":"1" ,
				"bgColor":"FFFFFF,FFFFFF",
				"showBorder":"0"
				},
			"data" :
				[
				{ "label" : "Week 1", "value" : "14400" },
				{ "label" : "Week 2", "value" : "19600" },
				{ "label" : "Week 3", "value" : "24000" },
				{ "label" : "Week 4", "value" : "15700" }
				]
			}
			});
		 var myExportComponent = new FusionChartsExportObject("fcExporter1", "js/FusionCharts/FCExporter.swf");  
		 myExportComponent.Render("fcexpDiv");
		$("#chartContainer2").insertFusionCharts({
			swfUrl: "js/FusionCharts/MSColumn3D.swf",
			//dataSource: "js/FusionCharts/Data.xml",
			//dataFormat: "xmlurl",
			width: parseInt($(this).width()*0.5)-80,
			height: parseInt($(this).height()*0.4),
			id: "myChartJsID",
			dataFormat: "xml",
			dataSource: dataString
			});
	});
	function add(){
		for(var i=0; i<1; i++){
			var p = $('<div/>').appendTo('body');
			p.panel({
				title:'Title'+i,
				content:'<div style="padding:5px;">Content'+(i+1)+'</div>',
				height:200,
				closable:true,
				collapsible:true
			});
			$('#pp').portal('add', {
				panel:p,
				columnIndex:i
			});
		}
		$('#pp').portal('resize');
	}
	function remove(){
		$('#pp').portal('remove',$('#pgrid'));
		$('#pp').portal('resize');
	}
	function openPDF(){
		window.open('<%=basePath%>jsp/a.pdf','打印标签','height=300,width=400,top=0,left=0,toolbar=yes,menubar=yes,scrollbars=no,resizable=yes,location=no,status=no');
	}
	</script>
  </head>
  <body>
	 <div id="pp" style="position:relative;">
			<div >
			 <div title="每日订单量" id="chartContainer"  closable="true" style="height:400px">
				
			    </div>
			</div>
			<div>
			   <div title="每日交易额" id="chartContainer2" closable="true" >
				</div>
			</div>
			<!--<div style="width:40%;">
				 <div title="每日订单完成量"  collapsible="true" closable="true" style="height:400px;padding:5px;">
			    
			    </div>
			</div>
			<div style="width:30%;">
				<div title="搜索" iconCls="icon-search" closable="true" style="height:200px;padding:10px;">
					<a href="javascript:void(0);" onclick="openPDF();" class="easyui-linkbutton">PDF</a>
					<a href="javascript:void(0);" class="easyui-linkbutton">Search</a>
				</div>
				
				<div title="日历" closable="true" style="border:0px;width:280px;text-align:center;">
					<div class="easyui-calendar" style="width:280px;height:250px;"></div>
				</div>
				
			</div>-->
	</div> 
</body>