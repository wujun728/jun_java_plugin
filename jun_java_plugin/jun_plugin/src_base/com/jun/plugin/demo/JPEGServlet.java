package com.jun.plugin.demo;
//import javax.servlet.*;
//import javax.servlet.http.*;
//import java.io.*;
//import com.sun.image.codec.jpeg.*;
//import java.awt.image.*;
//import java.awt.*;
//
//public class JPEGServlet extends HttpServlet{
//	
//	//����HTTP Get����
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//	                   throws ServletException,IOException{
//	    response.setContentType("image/jpeg");
//	    ServletOutputStream out= response.getOutputStream();
//	    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//	    Graphics g = image.getGraphics();
//	    g.setColor(Color.green);
//	    g.fillRect(0,0,100,100);
//	    g.setColor(Color.red);
//	    g.drawOval(0,0,100,100);
//	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//	    encoder.encode(image);
//	    out.close();
//	}
//	
//	// ����HTTP Post����
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//	        throws ServletException, IOException {
//	    doGet(request,response);
//	}
//	
//	// �õ�Servlet��Ϣ
//	public String getServletInfo() {
//		return "JPEGServlet Information";
//	}
//}
//	
//	