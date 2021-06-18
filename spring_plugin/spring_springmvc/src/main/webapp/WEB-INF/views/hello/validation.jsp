<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="//cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
	<script type="text/javascript">
	/*  $(function(){
		$('ul li a').click(function(){
			e.preventDefault();
	        $("#choose").text(this.innerHTML);
	        $("#choose").attr("href",this);
	        self.location.href = $( this ).attr("href");
	   })
	
	}) */  
		change=function(a){
				window.location.href="/hello/validation?lang="+a.value;
		}
	</script>
	<style type="text/css">
		#local{
			display:none;
		}
		#default:hover #local{
			display:block;
		}
	</style>
</head>
<body>
	<center>User</center>
	<div id="default">	
			<c:choose>  
			   <c:when test="${lang=='en_US'}">英文 
			   </c:when>  
			   <c:when test="${lang=='zh_CN'}">中文 
			   </c:when> 
			   <c:otherwise> 
			   			默认(中文)
			   </c:otherwise>  
			</c:choose>  
		<div id="local">
			<ul >
				<li><a href="/hello/validation?lang=en_US">英文</a></li>
				<li><a href="/hello/validation?lang=zh_CN">中文</a></li>
				<li><a href="/hello/validation?lang=zh_CN">俄罗斯文</a></li>
				<li><a href="/hello/validation?lang=zh_CN">意大利文</a></li>
			</ul>
		</div>
	</div>
	<div>
			<select	name="lang" onchange="change(this)">
				<option value="zh_CN">中文</option>
				<option 
					<c:choose>  
						   <c:when test="${lang=='en_US'}">
						   	selected
						   </c:when>
					</c:choose>
					value="en_US">英文
				</option>
			</select>
	</div>
	<hr>
	<f:form action="validation" method="post" modelAttribute="user">
		<s:message code="id"></s:message>:
		<input type="text" name="id">
		<f:errors path="id"></f:errors>
		<s:message code="name"></s:message>:
		<input type="text" name="name">
		<f:errors path="name"></f:errors>
		<s:message code="money"></s:message>:
		<input type="text" name="money">
		<f:errors path="money"></f:errors>
		<s:message code="birthday"></s:message>:
		<input type="text" name="birthdaty">
		<f:errors path="birthday"></f:errors>
		<input type="submit" value="提交"/>
	</f:form> 
</body>
</html>