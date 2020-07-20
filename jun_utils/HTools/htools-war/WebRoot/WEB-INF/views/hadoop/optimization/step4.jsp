<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>自选式优化  - 优化向导</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/optimization/list">自选式优化</a><span class="divider">/</span></li>
  <li class="active"> 常用文件类型</li>
</ul>
<form id="inputForm" name="optimization" action="${base}/hadoop/optimization/step4" method="post" >
	<div class="bs-docs-example">
		<div class="hero-unit">
			<h2>常用文件类型</h2>
			<label class="radio">
				<input type="radio" name="fileType" id="radios1" value="1" checked>
				散碎文件
			</label>
			<label class="radio">
	  			<input type="radio" name="fileType" id="radios2" value="2">
	  			G级别文件
			</label>
			<label class="radio">
	  			<input type="radio" name="fileType" id="radios3" value="3">
	  			T级别文件
			</label>
			<div align="center"><a href="#">
		            <img src="${base}/static/images/temp6.png" title="常用文件类型">
		         </a>
		    </div>
			
			<input type="hidden" name="hostname" value="${optimization.hostname}">
			<input type="hidden" name="serverType" value="${optimization.serverType}">
			<input type="hidden" name="networkType" value="${optimization.networkType}">
			<input type="hidden" name="clusterSize" value="${optimization.clusterSize}">
			<div align="right">
				<input type="submit" value="开始优化" class="btn btn-primary btn-large"/>
			</div>
			
		</div>
	</div>
</form>
</body>
</html>