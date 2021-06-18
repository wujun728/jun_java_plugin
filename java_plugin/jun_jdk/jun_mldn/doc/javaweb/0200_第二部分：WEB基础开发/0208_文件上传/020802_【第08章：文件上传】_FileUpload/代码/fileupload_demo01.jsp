<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	DiskFileItemFactory factory = new DiskFileItemFactory() ;
	ServletFileUpload upload = new ServletFileUpload(factory) ;
	upload.setFileSizeMax(3 * 1024 * 1024) ;	// 只能上传3M
	List<FileItem> items = upload.parseRequest(request) ; // 接收全部内容
	Iterator<FileItem> iter = items.iterator() ;
	while(iter.hasNext()){
		FileItem item = iter.next() ;
		String fieldName = item.getFieldName() ;	// 取得表单控件的名称
%>
		<ul><h4><%=fieldName%> --> <%=item.isFormField()%></h4>
<%
		if(!item.isFormField()){		// 不是普通文本
			String fileName = item.getName() ;	// 取得文件的名称
			String contentType = item.getContentType() ;	// 文件类型
			long sizeInBytes = item.getSize() ;
%>
			<li>上传文件名称：<%=fileName%>
			<li>上传文件类型：<%=contentType%>
			<li>上传文件大小：<%=sizeInBytes%>
<%
		} else {
			String value = item.getString() ;
%>
			<li>普通参数：<%=value%>
<%
		}
%>		</ul>
<%
	}
%>
</body>
</html>