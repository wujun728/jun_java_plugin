<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>最人性化的Hadoop管理工具</title>
</head>
<body>
<ul class="breadcrumb">
  <li class="active">首页</li>
</ul>
   <div class="bs-docs-example">
       <div class="hero-unit">
         <h2>欢迎使用HTools管理工具</h2>
         <p>HTools是一款专业的Hadoop管理工具，不管您是非专业IT人士，还是多年经验的技术人员，本工具都会为您提供优质的管理服务和轻松的操作过程，释放无谓的工作压力，提高Hadoop的管理水平。我们以最权威的专家为您量身定做的Hadoop管理工具，本系统提供优秀的用户体验，让您能够轻松的管理Hadoop集群环境。</p>
         <div class="row-fluid">
		      <ul class="thumbnails">
		        <li class="span3">
		          <a href="#" class="thumbnail">
		            <img src="${base}/static/images/home1.jpg" title="资源占用">
		          </a>
		        </li>
		        <li class="span3">
		          <a href="#" class="thumbnail">
		            <img src="${base}/static/images/home2.jpg" title="负载能力">
		          </a>
		        </li>
		        <li class="span3">
		          <a href="#" class="thumbnail">
		            <img src="${base}/static/images/home3.jpg" title="HDFS">
		          </a>
		        </li>
		        <li class="span3">
		          <a href="#" class="thumbnail">
		            <img src="${base}/static/images/home4.jpg" title="节点分布">
		          </a>
		        </li>
		      </ul>
		 </div>
		 <h3 align="right" style="padding-right: 50px;"><span style="font-size: 18px;">官方QQ群：</span>302429837</h3>
         <c:if test="${IS_FIRST_USE != null && IS_FIRST_USE==true}">
         	<link href="${base}/static/scripts/grumble/css/grumble.min.css" type="text/css" rel="stylesheet" />
			<script src="${base}/static/scripts/grumble/js/jquery.grumble.min.js"></script>
         	<p style="padding-left: 50px;"><a id="first_button" class="btn btn-primary btn-large" href="${base}/hadoop/guide/index" title="第一次使用吧？点击我就可以了！"><i class="icon-th-large icon-white"></i>配置向导</a></p>
       		<script type="text/javascript">
	       		$(document).ready(function() {
	       			if(getCookie('grumble_show')!='hide'){
	       				$('#first_button').grumble({
	    						text: '<h4><p>第一次使用吗？</p><p>请点击这里!</p></h4>', 
	    						angle: 80, 
	    						distance: 60, 
	    						showAfter: 3456,
	    						hideAfter: 12345
		    			});
	       				setCookie('grumble_show','hide');
	       			}
	       		});
			</script>
       	 </c:if>
       </div>
  </div>
  
</body>
</html>