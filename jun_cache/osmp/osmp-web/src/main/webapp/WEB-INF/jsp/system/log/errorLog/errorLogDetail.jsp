<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<div class="easyui-layout" fit="true">
        <div region="center" style="padding: 1px;">
            <table id="errorLogDetail" border="1" style=" border-collapse: collapse;border: none;">
                <thead>
                    <tr>
                        <th>Pro key</th>
                        <th>Pro value</th>
                    </tr>
                </thead>
                <tbody>
               		<tr>
                           <td valign="top">记录时间</td>
                           <td>${errorLogDetail.insertTime }</td>
                       </tr>
                       <tr>
                           <td>异常信息</td>
                           <td>${errorLogDetail.message }</td>
                       </tr>
                       <tr>
                           <td>异常堆栈</td>
                           <td>${errorLogDetail.exception }</td>
                       </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
