<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="mytag" uri="mldn"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 此代码只是为了测试，实际中此部分应该由servlet传递
	List<String> all = new ArrayList<String>() ;
	all.add("www.MLDN.cn") ;
	all.add("www.MLDNJAVA.cn") ;
	all.add("www.JIANGKER.com") ;
	request.setAttribute("all",all) ;	// 将内容保存在标签执行
%>
<mytag:present name="all" scope="request">
	<mytag:iterate id="url" name="all" scope="request">
		<h3>网站：${url}</h3>
	</mytag:iterate>
</mytag:present>
</body>
</html>