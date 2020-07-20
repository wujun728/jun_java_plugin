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
  <li class="active"> Hadoop集群主机:${hostname}</li>
</ul>
<div class="bs-docs-example">
	<div class="hero-unit">
		<h2>优化向导</h2>
		<p>第一步：确认硬件配置</p>
		<p>第二步：选择网络环境</p>
		<p>第三步：选择集群数量</p>
		<p>第四步：选择常用文件类型</p>
		<div align="center" style="margin: 10px;"><a href="#">
	            <img src="${base}/static/images/temp7.png" title="优化向导">
	         </a>
	    </div>
		<form id="inputForm" name="master" action="${base}/hadoop/optimization/start" method="post" >
			<input type="hidden" name="hostname" value="${hostname}">
			<div align="right">
				<input type="submit" value="开始优化" class="btn btn-primary btn-large"/>
			</div>
		</form>
	</div>
	
</div>
</body>
</html>