<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 步骤【3】</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">Hadoop集群配置向导<span class="divider">/</span></li>
  <li class="active">步骤【3】</li>
</ul>
		<form id="inputForm" name="master" action="${base}/hadoop/guide/step4" method="post" class="well">
			<fieldset>
	    		<div class="pagination">
		    		<legend>
			    		<ul>
							<li><a href="#">步骤【1】</a></li>
							<li><a href="#">步骤【2】</a></li>
							<li><a href="#">步骤【3】</a></li>
							<li class="disabled"><a href="#">步骤【4】</a></li>
							<li class="disabled"><a href="#">步骤【5】</a></li>
							<li class="disabled"><a href="#">完成</a></li>
						</ul>
					</legend>
				</div>
				<div class="control-group">
			      <label class="control-label">请您确认信息的正确性，系统为您获取到主节点为${master.ip}的相关节点信息，如果确认点击【下一步】，否则点击【上一步】重新配置。
			      <hr>
				      <div class="controls">
				       		<table class="table table-bordered table-striped table-hover">
								<thead>
									<tr><th>序号</th><th>节点类型</th><th>节点标识</th></tr>
								</thead>
								<%int index =1;%>
								<tbody>
									<tr>
										<td><%=index++ %></td>
										<td>作业调度节点</td>
										<td>${jt}</td>
									</tr>
									<c:forEach var="snn" items="${snns}">
										<tr>
											<td><%=index++ %></td>
											<td>主(备份)节点</td>
											<td>${snn}</td>
										</tr>
									</c:forEach>
									<c:forEach var="dn" items="${dns}">
										<%if(index<=8){%>
										<tr>
											<td><%=index++ %></td>
											<td>数据节点</td>
											<td>${dn}</td>
										</tr>
										<%}else{%>
										<tr>
											<td colspan="3">...其它数据节点信息已省略，共有${fn:length(dns)}个数据节点。</td>
										</tr>
										<%	break;
										}%>
									</c:forEach>
								</tbody>
							</table>
				      </div>
			      </label>
			    </div>
				<p>
					<hr>
					<a class="btn btn-large" href="${base}/hadoop/guide/step2" title="点击上一步不会保存当前配置">上一步</a>&nbsp;&nbsp;
					<input type="submit" value="下一步" title="确认无误后请点击下一步" class="btn btn-primary btn-large" data-loading-text="提交中..."/>
				</p>
			</fieldset>
		</form>
</body>
</html>