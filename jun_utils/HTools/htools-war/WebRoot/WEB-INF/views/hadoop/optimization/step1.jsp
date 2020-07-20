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
  <li class="active"> 硬件配置</li>
</ul>
<form id="inputForm" name="optimization" action="${base}/hadoop/optimization/step1" method="post" >
	<div class="bs-docs-example">
		<div class="hero-unit">
			<h2>硬件配置</h2>
			<label class="radio">
				<input type="radio" name="serverType" id="radios1" value="1" checked>
				服务器
			</label>
			<label class="radio">
	  			<input type="radio" name="serverType" id="radios2" value="2">
	  			普通PC机
			</label>
			<label class="radio">
	  			<input type="radio" name="serverType" id="radios3" value="3">
	  			虚拟机
			</label>
			<div align="center"><a href="#">
		            <img src="${base}/static/images/temp3.png" title="硬件配置">
		         </a>
		    </div>
			
			<input type="hidden" name="hostname" value="${hostname}">
			<div align="right">
				<input type="submit" value="下一步" class="btn btn-primary btn-large"/>
			</div>
			
		</div>
	</div>
</form>
</body>
</html>