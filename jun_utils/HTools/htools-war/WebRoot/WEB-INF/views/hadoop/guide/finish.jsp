<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 完成</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">Hadoop集群配置向导<span class="divider">/</span></li>
  <li class="active">完成</li>
</ul>
			<fieldset>
	    		<div class="pagination">
		    		<legend>
			    		<ul>
							<li><a href="#">步骤【1】</a></li>
							<li><a href="#">步骤【2】</a></li>
							<li><a href="#">步骤【3】</a></li>
							<li><a href="#">步骤【4】</a></li>
							<li><a href="#">步骤【5】</a></li>
							<li><a href="#">完成</a></li>
						</ul>
					</legend>
				</div>
				<div class="well">
					<h3>恭喜您，配置成功，请点击下列按钮体验。如果还想使用配置向导，请在“一键式服务”中操作。</h3>
					<p>
						<a class="btn btn-large" href="${base}/hadoop/service/index"><i class="icon-hand-right"></i> 一键式服务</a>&nbsp;&nbsp;
						<a class="btn btn-large" href="${base}/hadoop/node/monitor"><i class="icon-eye-open"></i> 节点监控维护</a>&nbsp;&nbsp;
						<a class="btn btn-large" href="${base}/hadoop/hdfs/index"><i class="icon-folder-open"></i> HDFS管理</a>&nbsp;&nbsp;
					</p>
			    </div>
			</fieldset>
		
<script>
	$(document).ready(function() {
		$("#inputForm").validate({
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				if ( element.is(":checkbox") )
					error.appendTo(element.parent().next());
				else
					error.insertAfter(element);
			}
		});
	});
</script>

</body>
</html>