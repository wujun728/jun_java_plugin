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
  <li class="active"> 集群数量</li>
</ul>
<form id="inputForm" name="optimization" action="${base}/hadoop/optimization/step3" method="post" >
	<div class="bs-docs-example">
		<div class="hero-unit">
			<h2>集群数量</h2>
			<label class="radio">
				<input type="radio" name="clusterSize" id="radios1" value="1" checked>
				10台以内
			</label>
			<label class="radio">
	  			<input type="radio" name="clusterSize" id="radios2" value="2">
	  			10到100台
			</label>
			<label class="radio">
	  			<input type="radio" name="clusterSize" id="radios3" value="3">
	  			100台以上
			</label>
			<div align="center"><a href="#">
		            <img src="${base}/static/images/temp5.png" title="集群数量">
		         </a>
		    </div>
			
			<input type="hidden" name="hostname" value="${optimization.hostname}">
			<input type="hidden" name="serverType" value="${optimization.serverType}">
			<input type="hidden" name="networkType" value="${optimization.networkType}">
			<div align="right">
				<input type="submit" value="下一步" class="btn btn-primary btn-large"/>
			</div>
			
		</div>
	</div>
</form>
</body>
</html>