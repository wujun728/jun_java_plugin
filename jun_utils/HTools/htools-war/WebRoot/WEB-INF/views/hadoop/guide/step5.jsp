<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 步骤【5】</title>
<script src="${base}/static/fest/textext.core.js"></script>
<script src="${base}/static/fest/textext.plugin.arrow.js"></script>
<script src="${base}/static/fest/textext.plugin.clear.js"></script>
<script src="${base}/static/fest/textext.plugin.focus.js"></script>
<script src="${base}/static/fest/textext.plugin.prompt.js"></script>
<script src="${base}/static/fest/textext.plugin.tags.js"></script>
<link rel="stylesheet" href="${base}/static/fest/textext.core.css" type="text/css" />
<link rel="stylesheet" href="${base}/static/fest/textext.plugin.arrow.css" type="text/css" />
<link rel="stylesheet" href="${base}/static/fest/textext.plugin.clear.css" type="text/css" />
<link rel="stylesheet" href="${base}/static/fest/textext.plugin.focus.css" type="text/css" />
<link rel="stylesheet" href="${base}/static/fest/textext.plugin.prompt.css" type="text/css" />
<link rel="stylesheet" href="${base}/static/fest/textext.plugin.tags.css" type="text/css" />
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">Hadoop集群配置向导<span class="divider">/</span></li>
  <li class="active">步骤【5】</li>
</ul>
		<form id="inputForm" name="master" action="${base}/hadoop/guide/finish" method="post" class="well">
			<fieldset>
	    		<div class="pagination">
		    		<legend>
			    		<ul>
							<li><a href="#">步骤【1】</a></li>
							<li><a href="#">步骤【2】</a></li>
							<li><a href="#">步骤【3】</a></li>
							<li><a href="#">步骤【4】</a></li>
							<li><a href="#">步骤【5】</a></li>
							<li class="disabled"><a href="#">完成</a></li>
						</ul>
					</legend>
				</div>
				<div class="control-group">
			      <label class="control-label">在这里配置告警邮箱，如果您想使用自动邮件告警功能，我们会为您推荐相关可用邮箱，也可以自行添加或移除。
			      <hr>
				      <div class="controls">
				     		告警邮箱列表(回车确认添加,否则填写无效):<textarea style="width: 650px;font-size: 14px;" id="mails" name="mails" rows="10" cols="200"></textarea>
				      </div>
			      </label>
			    </div>
				<p>
					<hr>
					<a class="btn btn-large" href="${base}/hadoop/guide/step4" title="点击上一步不会保存当前配置">上一步</a>&nbsp;&nbsp;
					<input type="submit" value="完成" class="btn btn-primary btn-large" data-loading-text="提交中..." title="点击完成将结束配置"/>
				</p>
			</fieldset>
		</form>
<script>
	 $(document).ready(function() {
		$("#mails").focus();
	 });
	 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 $('#mails').textext({
        plugins : 'tags prompt focus ajax arrow',
        tagsItems : ${mails},
        prompt : '从这里填写邮箱',
        ext: {
            itemManager: {
                stringToItem: function(str)
                {
                	if (filter.test(str)){
	                    return $.fn.textext.ItemManager.prototype.stringToItem.apply(this, arguments);
                	}else{
                		$('#testModal').modal({
            				keyboard: false,
            				backdrop: true,
            				modal:true
            			});
                	}
                }
            }
        }
    });
</script>
	<div id="testModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="testModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="testModalLabel">验证错误</h3>
		  </div>
		  <div class="modal-body">
		  		<div class="control-group">
				      您填写的邮箱地址不正确，请重新填写!
			    </div>
		 </div>
		<div class="modal-footer">
			<a class="btn" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
		</div>	  	
	</div>	
</body>
</html>