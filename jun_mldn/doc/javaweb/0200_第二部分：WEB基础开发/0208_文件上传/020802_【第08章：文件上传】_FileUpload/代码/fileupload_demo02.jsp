<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="cn.mldn.lxh.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	DiskFileItemFactory factory = new DiskFileItemFactory() ;
	factory.setRepository(new File(this.getServletContext().getRealPath("/") + "uploadtemp")) ;		// 更准确的说是一个临时文件
	ServletFileUpload upload = new ServletFileUpload(factory) ;
	upload.setFileSizeMax(3 * 1024 * 1024) ;	// 只能上传3M
	List<FileItem> items = upload.parseRequest(request) ; // 接收全部内容
	Iterator<FileItem> iter = items.iterator() ;
	IPTimeStamp its = new IPTimeStamp(request.getRemoteAddr()) ;
	while(iter.hasNext()){
		FileItem item = iter.next() ;
		String fieldName = item.getFieldName() ;	// 取得表单控件的名称
%>
		<ul><h4><%=fieldName%> --> <%=item.isFormField()%></h4>
<%
		if(!item.isFormField()){		// 不是普通文本
			File saveFile = null ;
			InputStream input = null ;
			OutputStream output = null ;
			input = item.getInputStream() ;
			output = new FileOutputStream(new File(this.getServletContext().getRealPath("/")+"upload"+File.separator+its.getIPTimeRand()+"."+item.getName().split("\\.")[1])) ;
			int temp = 0 ;
			byte data[] = new byte[512] ;
			while((temp=input.read(data,0,512))!=-1){
				output.write(data) ;	// 分块保存
			}
			input.close() ;
			output.close() ;
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