package com.ibeetl.admin.core.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class FileDownloadUtil {
	public static OutputStream getDownLoad(HttpServletResponse response,String fileName) throws IOException {
		 response.setContentType("text/html; charset = UTF-8");  
		 response.setHeader("Content-Disposition", "attachment; filename="+fileName);  
	     return response.getOutputStream();
	}
}
