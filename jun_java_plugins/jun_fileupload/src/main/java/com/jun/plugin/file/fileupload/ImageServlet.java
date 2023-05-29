package com.jun.plugin.file.fileupload;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	
	

	// ���С�?
	private final int WIDTH = 120; // ctrl+shift+x / y
	private final int HEIGHT = 30;

	public void CheckImageServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ������Ҫ���ڴ��й���һ��ͼƬ����
		BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = (Graphics2D) bf.getGraphics();

		// ���ñ�����ɫ
		Color color = new Color(203, 222, 225);
		graphics.setColor(color);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);

		String base = "ABCDEFGHIJKLMN";
		Random random = new Random();

		// ״̬��: ��������ù�֮ǰ�����?, �Ƕ�.����, ��С�ȵ���Щ��Ϣ,
		// ��ô����������,�����û��? ��ȥ ���ö�Ӧapi ȥ�����Щ�?,
		// ��ô��Ȼ������֮ǰ�����ù��״�?

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("����", Font.BOLD, 18));
		int m = 13;

		StringBuilder sb = new StringBuilder();

		// ��4 ���ַ�
		for (int i = 0; i < 4; i++) {

			int index = random.nextInt(base.length());
			char charAt = base.charAt(index);

			// -30 --- 30 15
			int jiaodu = random.nextInt(60) - 30;

			// �Ƕȱ� ����:�� ���е�ʱ��ѧ��
			double theta = jiaodu * Math.PI / 180;

			// ����theta Ҫ���� ����,
			graphics.rotate(theta, m, 15);
			graphics.drawString(charAt + "", m, 19);
			sb.append(charAt);

			graphics.rotate(-theta, m, 15);
			m += 20;
		}

		// �� sb �浽 session ��������?
		request.getSession().setAttribute("checkcode_session", sb.toString());

		// �� 4 ��������
		graphics.setColor(Color.BLUE);
		for (int i = 0; i < 4; i++) {
			// �������? ����
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
	


	public void GetImageServlet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获得�?张图�?
		
		// 创建图片 -- 在内存中
		int width = 80;
		int height = 40;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//创建图层，获得画�?
		Graphics g = image.getGraphics();
		//确定画笔颜色
		g.setColor(Color.BLACK);
		//填充�?个矩�?
		g.fillRect(0, 0, width, height);
		//只需要一个边�?
		//设置颜色
		g.setColor(Color.WHITE);
		//填充�?个矩�?
		g.fillRect(1, 1, width -2, height -2);
		
		//填充字符
		String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		//设置字体
		g.setFont(new Font("宋体",Font.BOLD,30));
		
		//缓存随机生成的字�?
		StringBuffer buf = new StringBuffer();
		
		//随机获得4个字�?
		Random random = new Random();
		for(int i = 0 ; i < 4 ; i++){
			//设置随机颜色
			g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			//获得�?个随机字�?
			int index = random.nextInt(62);
			//截取字符�?
			String str = data.substring(index, index + 1);  //[)
			//�?要将随机的字符，写到图片�?
			g.drawString(str, 20 * i, 30);
			//缓存
			buf.append(str);
		}
		
		//将获得随机字符串，保存到session
		// * 获得session
		HttpSession session = request.getSession();
		// * 保存�?
		session.setAttribute("number", buf.toString());
		
		//干扰�?
		for(int i = 0 ; i < 10 ; i ++){
			//设置随机颜色
			g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			//随机画直�?
			g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
		}
		
		
		
		/**
		 * <extension>jpg</extension>
        <mime-type>image/jpeg</mime-type>
		 */
		//通知浏览器发送的数据时一张图�?
		response.setContentType("image/jpeg");
		//将图片发送给浏览�?
		ImageIO.write(image, "jpg", response.getOutputStream());
		
		

	}
	
	
	
	
	/**
	 * 验证码图片宽�? 
	 */
	private final int IMG_WIDTH = 90;
	/**
	 * 验证码图片高�? 
	 */
	private final int IMG_HEIGHT = 20;
	/**
	 * 验证码字�?
	 */
	private final String strs = "1234567890abcdefghijklmnopqrstuvwxyz";
//	private final String strs = "1234567890abcdefghijklmnopqrstuvwxyz�?二三四五六七八九十壹贰叁肆伍陆柒捌玖�?";
	
	protected void GetCodeServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BufferedImage bi = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		Random random = new Random();
		//设置字体
		g2d.setFont(new Font("宋体", Font.BOLD, 14));
		//填充白色矩形
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		
		int x1, y1, x2, y2;
		for(int i = 0; i < 50; i++){
			//随机�?
			g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
			x1 = random.nextInt(IMG_WIDTH);
			y1 = random.nextInt(IMG_HEIGHT);
			x2 = random.nextInt(3) + x1;
			y2 = random.nextInt(3) + y1;
			g2d.drawLine(x1, y1, x2, y2);
		}
		
		//4字符验证�?
		char[] charArray = strs.toCharArray();
		String code = "";
		for(int i = 0; i < 4; i++){
			g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
			String s = String.valueOf(charArray[random.nextInt(charArray.length)]);
			code += s;
			g2d.drawString(s, 15 * (i + 1), 15);
		}
		
		//将验证码值放入session中，以待验证。此Demo没有使用�?
		HttpSession session = req.getSession();
		session.setAttribute("code", code);
		// 禁止缓存
        resp.setHeader("Prama", "no-cache");
        resp.setHeader("Coche-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(bi, "jpeg", sos);
        sos.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	

	public static final int width = 120;
	public static final int heigth = 30;

	public void RandomImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("test/html;charset=UTF-8");
		// image���ʾͼ��ͼ��?,BufferedImage��image�����ࣻImageͨ��������ȡͼƬ��
		// BufferedImage���пɷ���ͼ����ݻ�����?; BufferedImageͨ�������޸�ͼƬ��С������ͼƬ�����ȣ�
		BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);// 8 λ RGB��ɫ������ͼ��
		// Graphics��ʾ�ɻ��Ƶ�ͼ�� Graphics2D�� Graphics�����࣬�ṩ������״�����ת������ɫ������ı����ֵȸ�Ϊ���ӵĿ��ơ�
		Graphics g = image.getGraphics();

		setBackGround(g);// ���ñ���ɫ
		setBorder(g);// ���ñ߿�
		drawRandomLine(g);// ��������

		// д�����?
		String randomImage = drawRandomNumber((Graphics2D) g);// ���������?
		request.getSession().setAttribute("randomImage", randomImage);

		response.setContentType("image/jpeg");// ����������򿪷��?

		// ������������ܻ����ͼƬ
		response.setHeader("expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		ImageIO.write(image, "jpg", response.getOutputStream());// ��ָ����ʽ��ͼƬд��ָ���������?
	}

	private String drawRandomNumber(Graphics2D g) {
		g.setColor(Color.RED);// ����ͼ�������ĵĵ�ǰ��ɫ����Ϊָ����ɫ��
		g.setFont(new Font("����", Font.BOLD, 20));
		String base = "\u8981\u8fc7\u53bb\uff0c\u4f46\u90a3\u771f\u6b63\u6e29\u6696\u7684\u6625\u5929\u8fd8\u8fdc\u8fdc\u5730\u6ca1\u6709\u5230\u6765\u3002\u5728\u8fd9\u6837\u96e8\u96ea\u4ea4\u52a0\u7684\u65e5\u5b50\u91cc\uff0c\u5982\u679c\u6ca1\u6709\u4ec0\u4e48\u7d27\u8981\u4e8b\uff0c\u4eba\u4eec\u5b81\u613f\u4e00\u6574\u5929\u8db3\u4e0d\u51fa\u6237\u3002\u56e0\u6b64\uff0c\u53bf\u57ce\u7684\u5927\u8857\u5c0f\u5df7\u5012\u4e5f\u6bd4\u5e73\u65f6\u5c11\u4e86\u8bb8\u591a\u5608\u6742\u3002\u8857\u5df7\u80cc\u9634\u7684\u5730\u65b9\u3002\u51ac\u5929\u6b8b\u7559\u7684\u79ef\u96ea\u548c\u51b0\u6e9c\u5b50\u6b63\u5728\u96e8\u70b9\u7684\u6572\u51fb\u4e0b\u8680\u5316\uff0c\u77f3\u677f\u8857\u4e0a\u5230\u5904\u90fd\u6f2b\u6d41\u7740\u80ae\u810f\u7684\u6c61\u6c34\u3002\u98ce\u4f9d\u7136\u662f\u5bd2\u51b7\u7684\u3002\u7a7a\u8361\u8361\u7684\u8857\u9053\u4e0a\uff0c\u6709\u65f6\u4f1a\u5076\u5c14\u8d70\u8fc7\u6765\u4e00\u4e2a\u4e61\u4e0b\u4eba\uff0c\u7834\u6be1\u5e3d\u62a4\u7740\u8111\u95e8\uff0c\u80f3\u818a\u4e0a\u633d\u4e00\u7b50\u5b50\u571f\u8c46\u6216\u841d\u535c\uff0c\u6709\u6c14\u65e0\u529b\u5730\u547c\u5524\u7740\u4e70\u4e3b\u3002\u5509\uff0c\u57ce\u5e02\u5728\u8fd9\u6837\u7684\u65e5\u5b50\u91cc\u5b8c\u5168\u4e27\u5931\u4e86\u751f\u6c14\uff0c\u53d8\u5f97\u6ca1\u6709\u4e00\u70b9\u53ef\u7231\u4e4b\u5904\u4e86\u3002\u53ea\u6709\u5728\u534a\u5c71\u8170\u53bf\u7acb\u9ad8\u4e2d\u7684\u5927\u9662\u575d\u91cc\uff0c\u6b64\u523b\u5374\u81ea\u6709\u4e00\u756a\u70ed\u95f9\u666f\u8c61\u3002\u5348\u996d\u94c3\u58f0\u521a\u521a\u54cd\u8fc7\uff0c\u4ece\u4e00\u6392\u6392\u9ad8\u4f4e\u9519\u843d\u7684\u77f3\u7a91\u6d1e\u91cc\uff0c\u5c31\u8dd1\u51fa\u6765\u4e86\u4e00\u7fa4\u4e00\u4f19\u7684\u7537\u7537\u5973\u5973\u3002\u4ed6\u4eec\u628a\u7897\u7b77\u6572\u5f97\u9707\u5929\u4ef7\u54cd\uff0c\u8e0f\u6ce5\u5e26\u6c34\u3001\u53eb\u53eb\u56b7\u56b7\u5730\u8dd1\u8fc7\u9662\u575d\uff0c\u5411\u5357\u9762\u603b\u52a1\u5904\u90a3\u4e00\u6392\u7a91\u6d1e\u7684\u5899\u6839\u4e0b\u8702\u6d8c\u800c\u53bb\u3002\u504c\u5927\u4e00\u4e2a\u9662\u5b50\uff0c\u970e\u65f6\u5c31\u88ab\u8fd9\u7eb7\u4e71\u7684\u4eba\u7fa4\u8e29\u8e0f\u6210\u4e86\u4e00\u7247\u70c2\u6ce5\u6ee9\u3002\u4e0e\u6b64\u540c\u65f6\uff0c\u90a3\u4e9b\u5bb6\u5728\u672c\u57ce\u7684\u8d70\u8bfb\u751f\u4eec\uff0c\u4e5f\u6b63\u4e09\u4e09\u4e24\u4e24\u6d8c\u51fa\u4e1c\u9762\u5b66\u6821\u7684\u5927\u95e8\u3002\u4ed6\u4eec\u6491\u7740\u96e8\u4f1e\uff0c\u4e00\u8def\u8bf4\u8bf4\u7b11\u7b11\uff0c\u901a\u8fc7\u4e00\u6bb5\u65e9\u5e74\u95f4\u7528\u6a2a\u77f3\u7247\u63d2\u8d77\u7684\u957f\u957f\u7684\u4e0b\u5761\u8def\uff0c\u4e0d\u591a\u65f6\u4fbf\u7eb7\u7eb7\u6d88\u5931\u5728\u57ce\u5e02\u7684\u5927\u8857\u5c0f\u5df7\u4e2d\u3002\u5728\u6821\u56ed\u5185\u7684\u5357\u5899\u6839\u4e0b\uff0c\u73b0\u5728\u5df2\u7ecf\u6309\u73ed\u7ea7\u6392\u8d77\u4e86\u5341\u51e0\u8def\u7eb5\u961f\u3002\u5404\u73ed\u7684\u503c\u65e5\u751f\u6b63\u5728\u5fd9\u788c\u5730\u7ed9\u4f17\u4eba\u5206\u996d\u83dc\u3002\u6bcf\u4e2a\u4eba\u7684\u996d\u83dc\u90fd\u662f\u6628\u5929\u767b\u8bb0\u597d\u5e76\u4ed8\u4e86\u996d\u7968\u7684\uff0c\u56e0\u6b64\u7a0b\u5e8f\u5e76\u4e0d\u590d\u6742\uff0c\u73b0\u5728\u503c\u65e5\u751f\u53ea\u662f\u6309\u996d\u8868\u4ed8\u7ed9\u6bcf\u4eba\u9884\u8ba2\u7684\u4e00\u4efd\u3002\u83dc\u5206\u7532\u3001\u4e59\u3001\u4e19\u4e09\u7b49\u3002\u7532\u83dc\u4ee5\u571f\u8c46\u3001\u767d\u83dc\u3001\u7c89\u6761\u4e3a\u4e3b\uff0c\u91cc\u9762\u6709\u4e9b\u53eb\u4eba\u5634\u998b\u7684\u5927\u8089\u7247\uff0c\u6bcf\u4efd\u4e09\u6bdb\u94b1\uff1b\u4e59\u83dc\u5176\u5b83\u5185\u5bb9\u548c\u7532\u83dc\u4e00\u6837\uff0c\u53ea\u662f\u6ca1\u6709\u8089\uff0c\u6bcf\u4efd\u4e00\u6bdb\u4e94\u5206\u94b1\u3002\u4e19\u83dc\u53ef\u5c31\u5dee\u8fdc\u4e86\uff0c\u6e05\u6c34\u716e\u767d\u841d\u535c\u2014\u2014\u4f3c\u4e4e\u53ea\u662f\u4e3a\u4e86\u63a9\u9970\u8fd9\u8fc7\u5206\u7684\u6e05\u6de1\uff0c\u624d\u5728\u91cc\u9762\u8c61\u5f81\u6027\u5730\u6f02\u4e86\u51e0\u70b9\u8fa3\u5b50\u6cb9\u82b1\u3002\u4e0d\u8fc7\uff0c\u8fd9\u83dc\u4ef7\u94b1\u5012\u4e5f\u4fbf\u5b9c\uff0c\u6bcf\u4efd\u4e94\u5206\u94b1\u3002\u5404\u73ed\u7684\u7532\u83dc\u53ea\u662f\u5728\u5c0f\u8138\u76c6\u91cc\u76db\u4e00\u70b9\uff0c\u770b\u6765\u5403\u5f97\u8d77\u8089\u83dc\u7684\u5b66\u751f\u6ca1\u6709\u51e0\u4e2a\u3002\u4e19\u83dc\u4e5f\u7528\u5c0f\u8138\u76c6\u76db\u4e00\u70b9\uff0c\u8bf4\u660e\u5403\u8fd9\u79cd\u4e0b\u7b49\u4f19\u98df\u7684\u4eba\u4e5f\u6ca1\u6709\u591a\u5c11\u3002\u53ea\u6709\u4e59\u83dc\u5404\u73ed\u90fd\u7528\u70e7\u74f7\u5927\u811a\u76c6\u76db\u7740\uff0c\u6d77\u6d77\u6f2b\u6f2b\u7684\uff0c\u663e\u7136\u5927\u90e8\u5206\u4eba\u90fd\u5403\u8fd9\u79cd\u65e2\u4e0d\u5962\u4f88\u4e5f\u4e0d\u5bd2\u9178\u7684\u83dc\u3002";
		int x = 5;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			// n����=n*180/��(��)
			int degree = new Random().nextInt() % 30; // �����?30����30�������?
			// ��ȡָ�����λ�õ��ַ�?
			String ch = base.charAt(new Random().nextInt(base.length())) + "";
			sb.append(ch);
			g.rotate(degree * Math.PI / 180, x, 25);
			g.drawString(ch, x, 25);// ���ַ�д��ָ��λ�ã� ������������½�Ϊ׼��?
			g.rotate(-degree * Math.PI / 180, x, 25);// д��֮��ԭ��ת�Ƕȣ�
			x += 30;
		}
		return sb.toString();
	}

	private void drawRandomLine(Graphics g) {
		g.setColor(Color.GREEN);
		for (int i = 0; i < 5; i++) {
			int x = new Random().nextInt(width);
			int y = new Random().nextInt(heigth);
			int x1 = new Random().nextInt(width);
			int y1 = new Random().nextInt(heigth);
			g.drawLine(x, y, x1, y1);
		}
	}

	private void setBorder(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(1, 1, width - 2, heigth - 2);
	}

	private void setBackGround(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, heigth);
	}



	public void ImageServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置响应类型
		resp.setContentType("image/jpeg");
		int width = 60;
		int height = 30;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("宋体", Font.BOLD, 18));
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			int code = r.nextInt(10);
			g.setColor(c);
			g.drawString("" + code, i * 15, 10 + r.nextInt(20));
		}
		for (int i = 0; i < 10; i++) {
			Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			g.setColor(c);
			g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60), r.nextInt(30));
		}
		// 图片生效
		g.dispose();
		// 写到
		ImageIO.write(img, "JPEG", resp.getOutputStream());
	}
}
