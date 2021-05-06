<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 分页显示：开始 -->
  	
  	第${page.currentPageNum }页/共${page.totalPageNum }页&nbsp;&nbsp;
  	<a href="${pageContext.request.contextPath}${page.url}&num=1">首页</a>
  	<a href="${pageContext.request.contextPath}${page.url}&num=${page.prePageNum}">上一页</a>
  	
  	<c:forEach begin="${page.startPage}" end="${page.endPage}" var="num">
  		<a href="${pageContext.request.contextPath}${page.url}&num=${num}">${num}</a>
  	</c:forEach>
  	
  	
  	<a href="${pageContext.request.contextPath}${page.url}&num=${page.nextPageNum}">下一页</a>
  	<a href="${pageContext.request.contextPath}${page.url}&num=${page.totalPageNum }">尾页</a>
  	
  	&nbsp;&nbsp;
  	<input type="text" size="3" id="num" name="num" /><input type="button" id="bt1" value="跳转" onclick="jump()"/>
  	&nbsp;&nbsp;
  	<select name="selNum" onchange="jump1(this)">
  		<c:forEach begin="1" end="${page.totalPageNum }" var="num">
  			<option value="${num}" ${page.currentPageNum==num?'selected="selected"':'' } >${num}</option>
  		</c:forEach>
  	</select>
  	
  	<script type="text/javascript">
  		function jump(){
  			var numValue = document.getElementById("num").value;
  			//验证
  			if(!/^[1-9][0-9]*$/.test(numValue)){//验证是否是自然整数
  				alert("请输入正确的页码");
  				return;
  			}
  			if(numValue>${page.totalPageNum}){
  				alert("页码不能超过最大页数");
  				return;
  			}
  			window.location.href="${pageContext.request.contextPath}${page.url}&num="+numValue;
  		}
  		
  		function jump1(selectObj){
  			window.location.href="${pageContext.request.contextPath}${page.url}&num="+selectObj.value;
  		}
  	</script>
  	
  	<!-- 分页显示：结束 -->