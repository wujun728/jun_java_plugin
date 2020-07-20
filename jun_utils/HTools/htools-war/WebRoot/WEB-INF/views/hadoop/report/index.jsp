<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop图形报表  - 选择报表</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/report/list">图形报表</a><span class="divider">/</span></li>
  <li class="active"> Hadoop集群主机:${hostname}</li>
</ul>
<div class="bs-docs-example">
	<div class="hero-unit">
		<h2>选择报表</h2>

		<div class="row-fluid">
		      <ul class="thumbnails">
		        <li class="span6">
		          <a href="${base}/hadoop/report/hdfs_report/${hostname}" class="thumbnail">
		            <img src="${base}/static/images/temp1.jpg" title="HDFS使用情况报表">
		          </a>
		            <div align="center"><p>HDFS使用情况报表</p></div>
		        </li>
		        <li class="span6">
		          <a href="${base}/hadoop/report/node_report/${hostname}" class="thumbnail">
		            <img src="${base}/static/images/temp2.jpg" title="节点健康情况报表">
		          </a>
		            <div align="center"><p>节点健康情况报表</p></div>
		        </li>
		        
		      </ul>
		 </div>
	</div>
	
</div>
</body>
</html>