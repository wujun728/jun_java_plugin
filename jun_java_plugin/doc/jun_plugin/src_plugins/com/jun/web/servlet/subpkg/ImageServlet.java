package com.jun.web.servlet.subpkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置响应类型
		resp.setContentType("image/jpeg");
		int width=60;
		int height=30;
		BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0, width, height);
		g.setFont(new Font("宋体", Font.BOLD,18));
		Random r = new Random();
		for(int i=0;i<4;i++){
			Color c = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
			int code = r.nextInt(10);
			g.setColor(c);
			g.drawString(""+code,i*15,10+r.nextInt(20));
		}
		for(int i=0;i<10;i++){
			Color c = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
			g.setColor(c);
			g.drawLine(r.nextInt(60),r.nextInt(30),r.nextInt(60),r.nextInt(30));
		}
		//图片生效
		g.dispose();
		//写到
		ImageIO.write(img, "JPEG",resp.getOutputStream());
	}

}
