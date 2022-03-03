package com.jun.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.common.Globarle;

public class CreateVCode extends HttpServlet {

	/**
	 * 产生随机颜色
	 * @param fc
	 * @param bc
	 * @return Color
	 */
	@SuppressWarnings("unused")
	private Color getRandColor(int fc,int bc){
		Random rand = new Random();
		if(fc>255)
			fc=255;
		if(bc>255)
			bc=255;
		
		int r =	fc+ rand.nextInt(bc-fc);
		int g = fc+ rand.nextInt(bc-fc);
		int b = fc+ rand.nextInt(bc-fc);
		return new Color(r,g,b);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("image/*");
		//动态建立图片
		//在内存中创建图像
		int width = 60;
		int height = 20;
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random rand = new Random();
//		 设定背景色
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);
//		 设定字体
		g.setFont(new Font("Times   New   Roman", Font.PLAIN, 18));
//		 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160,200));
		for (int i = 0; i < 155; i++) {
			int x1 = rand.nextInt(width);
			int y1 = rand.nextInt(height);
			int x2 = rand.nextInt(12);
			int y2 = rand.nextInt(12);
			g.drawLine(x1, y1,x1 + x2,y1 + y2);
		}
//		 取随机产生的认证码(4位数字)
		String sRand = "";
		
		for (int i = 0; i < 4; i++) {
			String temp = "";
			if (i%2==0) {
				int temp2 = rand.nextInt(2);
				if (temp2%2==0) {
					temp = String.valueOf((char)(rand.nextInt(26)+((int)'a')));
				}else{
					temp = String.valueOf((char)(rand.nextInt(26)+((int)'A')));
				}
			}else{
				temp =String.valueOf(rand.nextInt(10));
			}
			sRand+=temp;
//			 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));
			g.drawString(temp, 13 * i + 6, 16);
			
		}
		
		//将认证码存入SESSION
		request.getSession().setAttribute(Globarle.VALIDATE_CODE, sRand.toLowerCase());
		// 图象生效
		g.dispose();
//		 输出图象到页面
		ServletOutputStream outStream = response.getOutputStream();
		ImageIO.write(image, "JPEG", outStream);
		outStream.flush();
		outStream.close();
	}
}
