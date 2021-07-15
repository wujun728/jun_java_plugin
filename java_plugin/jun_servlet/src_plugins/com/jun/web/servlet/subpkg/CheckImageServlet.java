package com.jun.web.servlet.subpkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*  
 * 		�����֤�� ͼƬ  : ��ȥѡ�� .
 * 
 */
public class CheckImageServlet extends HttpServlet {
	
	//���Сд 
	private final int WIDTH =120;  // ctrl+shift+x / y 
	private final int HEIGHT =30;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ������Ҫ���ڴ��й���һ��ͼƬ����
		BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D graphics = (Graphics2D) bf.getGraphics();
		
		//���ñ�����ɫ 
		Color color = new Color(203, 222, 225);
		graphics.setColor(color);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		String base ="ABCDEFGHIJKLMN";
		Random random = new Random();
		
		// ״̬��: ��������ù�֮ǰ����ɫ, �Ƕ�.����, ��С�ȵ���Щ��Ϣ, ��ô����������,�����û�� ��ȥ  ���ö�Ӧapi ȥ�����Щֻ, ��ô��Ȼ������֮ǰ�����ù��״̬
		
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("����",Font.BOLD,18));
		int m = 13;
		
		StringBuilder sb = new StringBuilder();
		
		// ��4 ���ַ� 
		for (int i = 0; i < 4; i++) {
			
			int index = random.nextInt(base.length());
			char charAt = base.charAt(index);
			
			//  -30 --- 30      15
			int jiaodu = random.nextInt(60)-30;
			
//			�Ƕȱ� ����:�� ���е�ʱ��ѧ�� 
			double theta = jiaodu*Math.PI/180;
			
			//����theta Ҫ���� ����, 
			graphics.rotate(theta,  m, 15);
			graphics.drawString(charAt+"", m, 19);
			sb.append(charAt);
			
			graphics.rotate(-theta,  m, 15);
			m+=20;
		}
		
		// �� sb �浽 session �������ȥ 
		request.getSession().setAttribute("checkcode_session", sb.toString());
		
		
		// �� 4 �������� 
		graphics.setColor(Color.BLUE);
		for (int i = 0; i < 4; i++) {
			//�������  ���� 
			int x1 = random.nextInt(WIDTH);
			int x2 = random.nextInt(WIDTH);
			int y1 = random.nextInt(HEIGHT);
			int y2 = random.nextInt(HEIGHT);
			graphics.drawLine(x1, y1, x2, y2);
		}
		
		// �ͷ���Դ
		graphics.dispose();
		
		
		ImageIO.write(bf, "png", response.getOutputStream());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
