<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 节点监控维护 - Hadoop集群列表</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/node/monitor/list">节点监控维护</a><span class="divider">/</span></li>
  <li class="active"> Hadoop集群列表</li>
</ul>
<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr><th>序号</th><th>主机名</th><th>IP地址</th><th>HDFS地址</th><th>Hadoop安装路径</th><th>请选择</th></tr>
			</thead>
			<tbody>
				<c:forEach var="config" items="${configs}" varStatus="index">
					<tr>
						<td>${index.index+1}</td>
						<td>${config.hostname}</td>
						<td>${config.ip}</td>
						<td>${config.hdfsFullPath}</td>
						<td>${config.hadoopSetupPath}</td>
						<td>
						<c:if test="${config.enabled==true||config.enabled=='TRUE'}">
							<a class="btn btn-primary dropdown-toggle" href="${base}/hadoop/node/monitor/hostname/${config.hostname}">进入</a>
							<a class="btn btn-warning dropdown-toggle" href="${base}/hadoop/node/monitor/pause/hostname/${config.hostname}">暂停监控</a>
						</c:if>
						<c:if test="${config.enabled==false||config.enabled=='FALSE'}">
							<a class="btn btn-success dropdown-toggle" href="${base}/hadoop/node/monitor/restore/hostname/${config.hostname}">恢复监控</a>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</body>
</html>