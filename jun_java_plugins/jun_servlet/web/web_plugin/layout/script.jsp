<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" />

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="icon" href="do.ico" type="image/x-icon" />
<link rel="shortcut icon" href="do.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/easyui-icons/icon-all.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui-portal/portal.css" />



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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/xheditor/xheditor-1.1.14-zh-cn.min.js" charset="utf-8"></script>
	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/<%=easyuiThemeName %>/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/themes/icon.css">
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css"> -->
	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.cookie.js" charset="UTF-8" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wjUtils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqueryUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/Map.js"></script> --%>


<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-portal/jquery.portal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/extend/extend.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dbmap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/core.js"></script> 



<style type="text/css">
	@CHARSET "UTF-8";
	.well-small {
	    border-radius: 3px 3px 3px 3px;
	    padding: 9px;
	}
	.well {
	    background-color: #F5F5F5;
	    border: 1px solid #E3E3E3;
	    border-radius: 4px 4px 4px 4px;
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
	    margin-bottom: 5px;
	    /*min-height: 20px;*/
	    padding: 5px;
	}
	.badge {
		background-color: #B94A48;
	    border-radius: 9px 9px 9px 9px;
	    padding-left: 9px;
	    padding-right: 9px;
	    color: #FFFFFF;
	    display: inline-block;
	    font-size: 11.844px;
	    font-weight: normal; 
	    line-height: 14px;
	    padding: 2px 4px;
	    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	    vertical-align: baseline;
	    white-space: nowrap;
	}
	/* .label-info{
	    background-color: #3A87AD;
	    border-radius: 3px 3px 3px 3px;
		color: #FFFFFF;
	    display: inline-block;
	    font-size: 11.844px;
	    font-weight: bold;
	    line-height: 14px;
	    padding: 2px 4px;
	    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	    vertical-align: baseline;
	    white-space: nowrap;
	} */
	.unsupported-browser{
		width:100%;
		left: 0;
		top: 0;
		margin:0px;
		display:none;
		position: fixed;
		z-index: 10000;
		background-color: #F5F5F5;
	    border: 1px solid #E3E3E3;
	    color: #393939;
	    border-radius: 4px 4px 4px 4px;
	    margin-bottom: 18px;
   		padding: 8px 35px 8px 14px;
   		text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	}
	body {
	    font-family:helvetica,tahoma,verdana,sans-serif;
	    font-size:13px;
	    font-family:微软雅黑;
	    margin:0px 0px 0px 0px;
	    padding:0px 0px 0px 0px;
	}
	
	
	
	.easyui-textbox{
		height: 20px;
		width: 170px;
		line-height: 16px;
	    /*border-radius: 3px 3px 3px 3px;*/
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
	}
	
	textarea:focus, input[type="text"]:focus{
	    border-color: rgba(82, 168, 236, 0.8);
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
	    outline: 0 none;
		}
	table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
	}

	fieldset {
	    border: 0 none;
	    margin: 0;
	    padding: 0;
	}
	legend {
	    -moz-border-bottom-colors: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    border-color: #E5E5E5;
	    border-image: none;
	    border-style: none none solid;
	    border-width: 0 0 1px;
	    color: #999999;
	    line-height: 20px;
	    display: block;
	    margin-bottom: 10px;
	    padding: 0;
	    width: 100%;
	}
	input, textarea {
	    font-weight: normal;
	    font-family:微软雅黑;
	}
    table ,th,td{
		text-align:left;
		padding: 0px;
		font-size: 12px;
		font-family:微软雅黑;
		font-weight:normal;
	} 
</style>

<!-- <script src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js" charset="UTF-8" type="text/javascript"></script> -->
<!-- <script src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script> -->
<!-- <script src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script> -->
<!-- <link id="easyuiTheme" href="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/themes/<%=easyuiThemeName %>/easyui.css" rel="stylesheet" type="text/css" media="screen"> -->
<!-- <link href="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/themes/icon2.css" rel="stylesheet" type="text/css" media="screen"> -->

<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionCharts.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionCharts.jqueryplugin.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/FusionCharts/FusionChartsExportComponent.js"></script> -->

<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/loadmask/jquery.loadmask.css"> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/loadmask/jquery.loadmask.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script> -->
<!-- <script type="text/javascript" src="<%=request.getContextPath()%>js/validateBox.js"></script> -->