<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<br/>
<hr/>
	<%
		Map map2 = new HashMap();
		map2.put("c001","2Java");
		map2.put("c002","2Core");
		Map map = new HashMap();
		map.put("b001","Java");
		map.put("b002","Core");
		map.put("b003","Thinking");
		map.put("b004","Cooking");
		map.put("maps",map2);
		//添加到作用域
		pageContext.setAttribute("mapObj",map);
	%>
	map11=<c:out value="${mapObj.maps.c001}"></c:out>
	</br>
	<%
		List list = new ArrayList();
		list.add("abc");
		list.add("123");
		list.add("456");
		list.add("wasd");
		list.add(map);
		pageContext.setAttribute("list",list,PageContext.PAGE_SCOPE);
	%>
	
	<c:forEach items="${list}" var="li"  varStatus="status">
		${status.count}-->${status.count%2==0?'1':'0'}-->${li} ** <br/>
	</c:forEach>
	<br/>
	
	map<br/>
	<c:forEach items="${mapObj}" var="m">
		${m.key} -- ${m.value} 
		<%-- <c:if test="${not empty requestScope.msg}">
			<c:forEach items="${m.value}" var="m2">
				&nbsp;&nbsp;&nbsp;&nbsp;${m2} ***
			</c:forEach>
		</c:if> --%>
		<br/>
	</c:forEach>
	
<hr/>
<hr/>
	<%
  		if(request.getAttribute("name")==null){
//   			System.out.println(request.getId());
  			request.setAttribute("name","Jack"+new Date());
  		}
  	%>
  	${name}
  	<a href="<c:url value='/index.jsp'/>">DDD</a>
  	<hr/>
   	<p>主页</p>
   	<c:choose>
   		<c:when test="${empty requestScope.user}">
   			<p>请登录</p>
   			<c:if test="${not empty requestScope.msg}">
   				<font color="red">
   					${requestScope.msg}
   				</font>
   				<c:remove var="msg" scope="request"/>
   			</c:if>
   			<form name="x" action="<c:url value='/LoginServlet'/>" method="post">
   				Name:<input type="text" name="name"/><br/>
   				<input type="submit"/>
   			</form>
   		</c:when>
   		<c:otherwise>
   			欢迎你:${user.name}
   			<br/>
   			<a href="<c:url value='/LoginServlet'/>">退出</a>
   		</c:otherwise>
   	</c:choose>
<hr/>
<hr/>
<%---该标签在构建URL时会自动为URL添加sessionID,非首次访问时则不会重写URL；--%>
	<c:url var="in" value="/jstl/2.jsp" />
	<a href="${in}">afafas</a>
	<br />----------------------------------------------
	<br />

	<%--没有var属性时，重写URL后直接在页面显示 --%>
	<c:url value="/jstl/2.jsp" />
	<br />----------------------------------------------
	<br />
	
	<%--为页面传递参数--%>
	<c:url var="va" value="/jstl/2.jsp">
		<c:param name="yang" value="14中国46"></c:param>
	</c:url>
	<a href="${va}">ffa</a>
	<br />----------------------------------------------
	<br />
	
<hr/> 
<hr/>

获得request.getContextPath();<br/>
1.java <br/>
111 <%=request.getContextPath() %> <br/>
222 <%=((HttpServletRequest)pageContext.getRequest()).getContextPath() %> <br/>

2.EL<br/>
${pageContext.request.contextPath} <br/>

<hr/>
\${headerValues} <br/>

${headerValues.cookie[0]}<br/>
${headerValues.cookie[1]}<br/>

${headerValues["user-agent"][0]}<br/>

<hr/>

\${5 / 2}  -- ${5 / 2} <br/>
\${6 &gt; 3 } -- ${6 gt 3} <br/>
\${empty user} -- ${empty user}

<hr/>

<%
	String ab = "abcde";
	pageContext.setAttribute("abObj",ab,PageContext.REQUEST_SCOPE);
%>
\${abObj} -- ${abObj} <br/>


<hr/>

	 
	<%-- <%
		//从作用域中获取信息 mapObj
		Map<String,String> m = (Map<String,String>)pageContext.getAttribute("mapObj");
		Set<Map.Entry<String,String>> set = m.entrySet();
		for(Map.Entry<String,String> entry : set){
			pageContext.setAttribute("entry",entry);
	%>
		${entry.key} -- ${entry.value} <br/>
	<%
		}
	%> --%>
	
	
</body>
</html>