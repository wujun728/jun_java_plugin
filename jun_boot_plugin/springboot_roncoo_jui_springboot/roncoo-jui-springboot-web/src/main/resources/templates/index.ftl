<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#assign base=request.contextPath />
<base href="${base}/dwz_jui/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>龙果开源-后台管理系统-JUI</title>

<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen" />
<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print" />
<link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<!--[if lt IE 9]>
<script src="js/speedup.js" type="text/javascript"></script><script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<![endif]-->
<!--[if gte IE 9]>
<!-->
<script src="js/jquery-2.1.4.min.js" type="text/javascript"></script>
<!--<![endif]-->

<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
<script src="xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="chart/raphael.js"></script>
<script type="text/javascript" src="chart/g.raphael.js"></script>
<script type="text/javascript" src="chart/g.bar.js"></script>
<script type="text/javascript" src="chart/g.line.js"></script>
<script type="text/javascript" src="chart/g.pie.js"></script>
<script type="text/javascript" src="chart/g.dot.js"></script>

<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)-->
<script src="bin/dwz.min.js" type="text/javascript"></script>
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("dwz.frag.xml", {
			//loginUrl : "${base}/timeout", loginTitle : "登录", // 弹出登录对话框
			loginUrl:"${base}/timeout",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, 
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			keys : {
				statusCode : "statusCode",
				message : "message"
			}, //【可选】
			ui : {
				hideMode : 'offsets'
			}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>

</head>

<body>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://www.roncoo.com" target="_blank">标志</a>
				<ul class="nav">
					<li id="switchEnvBox">
						<a href="javascript:">
							（<span>广州</span>）切换城市
						</a>
						<ul>
							<li>
								<a href="#">北京</a>
							</li>
							<li>
								<a href="#">上海</a>
							</li>
							<li>
								<a href="#">南京</a>
							</li>
							<li>
								<a href="#">深圳</a>
							</li>
							<li>
								<a href="#">广州</a>
							</li>
							<li>
								<a href="#">天津</a>
							</li>
							<li>
								<a href="#">杭州</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="http://git.oschina.net/roncoocom" target="_blank">OSChina</a>
					</li>
					<li>
						<a href="https://github.com/roncoo" target="_blank">GitHub</a>
					</li>
					<li>
						<a href="${base}/exit">退出</a>
					</li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default">
						<div class="selected">蓝色</div>
					</li>
					<li theme="green">
						<div>绿色</div>
					</li>

					<li theme="purple">
						<div>紫色</div>
					</li>
					<li theme="silver">
						<div>银色</div>
					</li>
					<li theme="azure">
						<div>天蓝</div>
					</li>
				</ul>
			</div>
		</div>

		<#include "menu.ftl" />

		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
							<li tabid="main" class="main">
								<a href="javascript:;">
									<span><span class="home_icon">我的主页</span></span>
								</a>
							</li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<div class="tabsRight">right</div>
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li>
						<a href="javascript:;">我的主页</a>
					</li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<#include "content.ftl" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		Copyright &copy; 2015- <span id="year"></span>
		<a href="http://www.roncoo.com" target="_blank">龙果学院</a> 粤ICP备16009964号
		<script language="javascript">var now=new Date();document.getElementById("year").innerHTML = now.getFullYear();</script>
	</div>
</body>
</html>