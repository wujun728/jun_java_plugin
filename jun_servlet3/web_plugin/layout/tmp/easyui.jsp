	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<%
		String easyuiThemeName="metro";
		Cookie cookies[] =request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for(Cookie cookie : cookies){
				if (cookie.getName().equals("cookiesColor")) {
					easyuiThemeName = cookie.getValue();
					break;
				}
			}
		}
	%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/xheditor/jquery-1.8.0.min.js"></script>
<!-- 	<script src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js" charset="UTF-8" type="text/javascript"></script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/xheditor/xheditor-1.1.14-zh-cn.min.js" charset="utf-8"></script>
	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/<%=easyuiThemeName %>/easyui.css">
<!-- <link id="easyuiTheme" href="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/themes/<%=easyuiThemeName %>/easyui.css" rel="stylesheet" type="text/css" media="screen"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/icon.css">
<!-- <link href="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/themes/icon2.css" rel="stylesheet" type="text/css" media="screen"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ext.css">
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/loadmask/jquery.loadmask.css"> -->
	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jquery.easyui.min.js"></script>
<!-- 	<script src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/easyui-lang-zh_CN.js"></script>
<!-- <script src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script> -->

<script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionCharts.jqueryplugin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionChartsExportComponent.js"></script>

<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/loadmask/jquery.loadmask.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script> -->
<script language="javascript"  src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>js/validateBox.js"></script> -->
<script src="<%=request.getContextPath()%>/js/jquery.cookie.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/syUtils.js" charset="UTF-8" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqueryUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
