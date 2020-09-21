<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
<script type="text/javascript">
    var displayAll = function() {
        $('#bundle').datagrid({
            queryParams : {
                displayAll : 'Y'
            }
        });
    }
</script>
</head>
<body>
    <div class="easyui-layout" fit="true">
        <div region="center" style="padding: 1px;">
            <table class="bundledetail" border="1">
                <thead>
                    <tr>
                        <th>Pro key</th>
                        <th>Pro value</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="propObj" items="${bundleInfo.props }">

                        <tr>
                            <td valign="top">${propObj.key }</td>
                            <td>${propObj.value }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
