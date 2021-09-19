package com.tanzhou.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Code_MarkerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 首先设置页面不缓存--最后加上
		response.setHeader("Pragma", "No-cache");  // 设置HTTP响应的头信息
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 定义图片的宽度和高度
		int width = 90, height = 40;
		// 创建一个图像对象
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		/**
		 * component类及其子类在新生成对象的时候会自动调用paint(Graphics g)方法
		 */
		Graphics g = image.createGraphics();// 得到图像的绘制环境
		
		// 用随机颜色填充图像背景
		g.setColor(getRandColor(180, 250));
		/**
		 * fillRect（x,y,w,h）函数的作用是：填充一个矩形区域，x、y为起始坐标（即左上角坐标）,
		 * 后面两个参数分别为：w、h,是矩形区域的宽和高,这里的20表示填充宽度20像素，15表示填充高度15像素。
如有其它不懂的地方可以在线问我。
		 */
		g.fillRect(0, 0, width, height);
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			g.setColor(getRandColor(50, 100));
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			/**
			 * fillRect（x,y,w,h）函数的作用是：填充一个矩形区域，x、y为起始坐标（即左上角坐标）,
			 * 后面两个参数分别为：w、h,是矩形区域的宽和高,这里的20表示填充宽度20像素，
			 * 15表示填充高度15像素。
			 */
			g.drawOval(x, y, 5, 5);
		}
		g.setFont(new Font("", Font.PLAIN, 40)); // 设置字体，下面准备画随机数
		String sRand = ""; // 随机数字符串
		for (int i = 0; i < 4; i++) {
			// 生成四个数字字符
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 生成随机颜色
			g.setColor(new Color(20 + random.nextInt(80), 20 + random
					.nextInt(100), 20 + random.nextInt(90)));
			// 将随机数字画在图像上
			
			/**
			 * drawString 
public abstract void drawString(AttributedCharacterIterator iterator, 
int x, 
int y) 
使用此图形上下文的当前颜色绘制由指定迭代器给定的文本。迭代器必须为每个字符指定字体。最左侧字符的基线位于此图形上下文坐标系统的 (x, y) 位置处。 

参数： 
iterator - 要绘制其文本的迭代器 
x - x 坐标。 
y - y 坐标。 
另请参见： 
drawBytes(byte[], int, int, int, int), drawChars(char[], int, int, int, int)
			 * 
			 */
			g.drawString(rand, (17 + random.nextInt(3)) * i + 8, 34);

			// 生成干扰线
			for (int k = 0; k < 12; k++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(9);
				int yl = random.nextInt(9);
				/**
				 * 这个方法是画一条直线，那么我们都知道两点确定一条直线，而在坐标中由横(X)、纵坐标(y)确定一个点，这四参数实际就是确定两个点，你要画的直线的起始点横纵坐标和终点的横纵坐标。
X1，Y1是确定直线的起始点，即横坐标为x1，纵坐标为y1的点。同理x2,y2确定直线的终点。
例A(x1,y1)  B(x2,y2) 就可以画出直线AB了
				 * 
				 * 
				 * 
				 * public abstract void drawLine(int x1,
                              int y1,
                              int x2,
                              int y2)在此图形上下文的坐标系统中，使用当前颜色在点 (x1, y1) 和 (x2, y2) 之间画一条线。 

参数：
x1 - 第一个点的 x 坐标。
y1 - 第一个点的 y 坐标。
x2 - 第二个点的 x 坐标。
y2 - 第二个点的 y 坐标。

譬如，你从(0,0, 100, 200),就是从画一条从(0,0)点开始向目标点(100,200）的直线
				 * 
				 * 
				 */
				g.drawLine(x, y, x + xl, y + yl);
			}
		}

		// 将生成的随机数字字符串写入session
		request.getSession().setAttribute("randomNumber", sRand);
		g.dispose(); // 使图像生效
		ImageIO.write(image, "JPEG", response.getOutputStream()); // 输出图像到页面
	}

	// 产生一个随机的颜色 其中，min:颜色分量最小值 ;max:颜色分量最大值
	public Color getRandColor(int min, int max) {
		Random random = new Random();
		if (min > 255) {
			min = 255;
		}
		if (max > 255) {
			max = 255;
		}
		int R = min + random.nextInt(max - min);
		int G = min + random.nextInt(max - min);
		int B = min + random.nextInt(max - min);
		/**
		 * 0~255   兰色：0~7 位       绿色：8~15位        红色：16~23位
		 */
		return new Color(R, G, B);
	}

}
