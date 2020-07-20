<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 步骤【4】</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">Hadoop集群配置向导<span class="divider">/</span></li>
  <li class="active">步骤【4】</li>
</ul>
		<form id="inputForm" name="master" action="${base}/hadoop/guide/step5" method="post" class="well">
			<fieldset>
	    		<div class="pagination">
		    		<legend>
			    		<ul>
							<li><a href="#">步骤【1】</a></li>
							<li><a href="#">步骤【2】</a></li>
							<li><a href="#">步骤【3】</a></li>
							<li><a href="#">步骤【4】</a></li>
							<li class="disabled"><a href="#">步骤【5】</a></li>
							<li class="disabled"><a href="#">完成</a></li>
						</ul>
					</legend>
				</div>
				<div class="control-group">
			      <label class="control-label">请您确认信息的正确性，系统为您获取到${master.hdfsFullPath}的文件信息，如果确认点击【下一步】，否则点击【上一步】重新配置。
			      <hr>
				      <div class="controls">
				       		<table class="table table-bordered table-striped table-hover">
								<thead>
									<tr><th>序号</th><th>文件名</th><th>文件大小</th><th>块大小</th><th>创建时间</th><th>权限</th></tr>
								</thead>
								<tbody>
									<c:forEach var="file" items="${files}" varStatus="index">
										<c:if test="${index.index<=7}">
											<tr>
												<td>${index.index+1}</td>
												<td>
												<c:if test="${file.dir==true}"><i class="icon-folder-open"></i></c:if>
												<c:if test="${file.dir==false}"><i class="icon-file"></i></c:if>
													${file.fileName}
												</td>
												<td><fmt:formatNumber value="${file.len}" pattern="###,###"/></td>
												<td><fmt:formatNumber value="${file.blockSize}" pattern="###,###"/></td>
												<td><fmt:formatDate value="${file.modifyTime}" pattern="yyyy年MM月dd日 HH时mm分"/></td>
												<td>${file.permission}</td>
											</tr>
										</c:if>
										<c:if test="${index.index==8}">
											<tr>
												<td colspan="6">...其它HDFS文件信息已省略，共有${fn:length(files)}个文件和目录。</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
				      </div>
			      </label>
			    </div>
				<p>
					<hr>
					<a class="btn btn-large" href="${base}/hadoop/guide/step3" title="点击上一步不会保存当前配置">上一步</a>&nbsp;&nbsp;
					<input type="submit" value="下一步" class="btn btn-primary btn-large" data-loading-text="提交中..."/>
				</p>
			</fieldset>
		</form>
</body>
</html>