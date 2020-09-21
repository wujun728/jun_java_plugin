<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="z" uri="/osmp-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!-- easyui控件 -->
<link rel="stylesheet" href="${BASE_PATH}/public/accordion/icons.css" type="text/css"></link>
<link rel="stylesheet" href="${BASE_PATH}/public/accordion/accordion.css" type="text/css"></link>
<link rel="stylesheet" href="${BASE_PATH}/public/easyui/themes/icon.css" type="text/css"></link>
<link id="easyuiTheme" rel="stylesheet" href="${BASE_PATH}/public/easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${BASE_PATH}/css/common.css" type="text/css"></link>
<script type="text/javascript" src="${BASE_PATH}/public/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/accordion/leftmenu.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/jquery.cookie.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/syUtil.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/curdtools.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/easycurd.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/easyui/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/zznode.js"></script>
<script>BASE_PATH = "${BASE_PATH}";</script>