<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<%-- <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%
	String easyuiThemeName = "metro";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("easyuiThemeName")) {
				easyuiThemeName = cookie.getValue();
				break;
			}
		}
	}
%>


<script type="text/javascript" src="<%=request.getContextPath()%>/js/xheditor/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/xheditor/xheditor-1.1.14-zh-cn.min.js" charset="utf-8"></script>
	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/<%=easyuiThemeName %>/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/icon.css">
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css"> -->
	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.cookie.js" charset="UTF-8" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/syUtils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqueryUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Map.js"></script>



<%-- <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="<%=basePath%>/main/include/js/common.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="<%=basePath%>/main/include/js/validatebox-myrule.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="<%=basePath%>/main/include/js/validatebox-duplicate.js" charset="utf-8"></script> --%>

<%-- <link rel="stylesheet" href="<%=basePath%>/js/jquery-easyui-portal/portal.css" type="text/css"></link> --%>
<%-- <link rel="stylesheet" href="<%=basePath%>/css/syCss.css?v=20120804" type="text/css"></link> --%>


