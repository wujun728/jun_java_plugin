<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	request.setCharacterEncoding("GBK") ;// 设置的是统一编码
	Enumeration enu = request.getParameterNames() ;
%>
<table border="1">
	<tr>
		<td>参数名称</td>
		<td>参数内容</td>
	</tr>
<%
	while(enu.hasMoreElements()){
		String paramName = (String) enu.nextElement() ;
%>
		<tr>
			<td><%=paramName%></td>
			<td>
<%
			if(paramName.startsWith("**")){		// 是以**开头
				String paramValue[] = request.getParameterValues(paramName) ;
				for(int x=0;x<paramValue.length;x++){
%>
					<%=paramValue[x]%>、
<%
				}
			} else {
				String paramValue = request.getParameter(paramName) ;
%>
				<%=paramValue%>
<%
			}
%>
			</td>
		</tr>
<%
	}
%>
</table>
</body>
</html>