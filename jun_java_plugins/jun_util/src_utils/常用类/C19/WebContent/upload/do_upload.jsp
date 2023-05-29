<%@ page contentType="text/html; charset=gb2312" language="java" 
import="com.jspsmart.upload.*"%>
<html>
	<head>
		<title>文件上传处理页面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	</head>

	<body>
		<%// 新建一个SmartUpload对象
			SmartUpload su = new SmartUpload();
			// 上传初始化
			su.initialize(pageContext);
			// 设定上传限制
			// 1.限制每个上传文件的最大长度10MB
			su.setMaxFileSize(10 * 1024 * 1024);
			// 2.限制总上传数据的长度。
			su.setTotalMaxFileSize(30 * 1024 * 1024);
			// 3.设定允许上传的文件（通过扩展名限制）,仅允许txt,mp3,wmv文件。
			su.setAllowedFilesList("txt,mp3,wmv");
			// 4.设定禁止上传的文件（通过扩展名限制）,禁止上传带有exe,bat,
			// jsp,htm,html扩展名的文件和没有扩展名的文件。
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			// 上传文件
			su.upload();

			// 将上传文件全部保存到指定目录
			// 注意这个目录是虚拟目录，相对于Web应用的根目录
			int count = su.save("/upload");
			out.println(count + "个文件上传成功！<br>");

			// 利用Request对象获取参数之值
			out.println("<BR>上传帐户： "
							+ su.getRequest().getParameter("uploadername")
							+ "<BR><BR>");

			// 逐一提取上传文件信息，同时可保存文件。
			for (int i = 0; i < su.getFiles().getCount(); i++) {
				com.jspsmart.upload.File file = su.getFiles().getFile(i);
				// 若文件不存在则继续
				if (file.isMissing()) {
					continue;
				}

				// 显示当前文件信息
				out.println("<TABLE BORDER=1>");
				out.println("<TR><TD>表单项名（FieldName）</TD><TD>"
						+ file.getFieldName() + "</TD></TR>");
				out.println("<TR><TD>文件长度（Size）</TD><TD>" + file.getSize()
						+ " Byte</TD></TR>");
				out.println("<TR><TD>文件名（FileName）</TD><TD>"
						+ file.getFileName() + "</TD></TR>");
				out.println("<TR><TD>文件扩展名（FileExt）</TD><TD>"
						+ file.getFileExt() + "</TD></TR>");
				out.println("<TR><TD>文件全名（FilePathName）</TD><TD>"
						+ file.getFilePathName() + "</TD></TR>");
				out.println("</TABLE><BR>");

				// 将文件另存为
				// 路径是相对于Web应用的根目录
				//file.saveAs("/upload/saveas/" + file.getFileName());
				// 另存到以WEB应用程序的根目录为文件根目录的目录下，
				// SmartUpload.SAVE_VIRTUAL指定了采用虚拟路径存储
				file.saveAs("/upload/saveas/" + file.getFileName(),
						SmartUpload.SAVE_VIRTUAL);
				// 另存到操作系统的根目录为文件根目录的目录下
				// SmartUpload.SAVE_PHYSICAL指定了采用物理路径
				file.saveAs("c:/temp/upload/" + file.getFileName(),
						SmartUpload.SAVE_PHYSICAL);
			}
		%>
	</body>
</html>
