import java.applet.Applet;
import java.awt.*;

public class Text3DApplet extends Applet implements Runnable {

	Image image; //绘制文字的Image对象
	Graphics graphics; //绘制文字的Graphics对象
	Thread thread;  //显示三维文字线程
	int width,height; //显示宽度、高度
	String message; //显示信息
	int fontSize; //文字尺寸
	Font font; //字体

	public void init() {
		Dimension dim=getSize(); //得到Applet的尺寸
		width = dim.width; //得到宽度
		height = dim.height; //得到高度
		image = createImage(width, height); //得到Image实例
		graphics= image.getGraphics(); //得到Grahpics实例
		message = getParameter("text"); //从HTML文件中得到显示信息
		if (message == null) { //如果信息为空
			message="三维文字"; //设置默认信息
		}
		fontSize = 30; //设置字体大小
	}
	

	public void start() { 
		if (thread == null) {
			thread = new Thread(this);  //实例化线程
			thread.start(); //运行线程
		}
	}

	public void run() { //线程运行主体
		while (thread != null) {				
				try {
					Thread.sleep(50L); //线程休眠
				} catch (InterruptedException ex) {
				}
				repaint(); //重绘屏幕
			}
	}

	public void update(Graphics g) {		
		font = new Font("TimesRoman", 1, fontSize); //得到字体实例
		graphics.setFont(font);  //设置显示字体
		int j = (int) (255 * Math.random()); //变量,用于生成渐变颜色
		int k = (int) (255 * Math.random());
		int l = (int) (255 * Math.random());
		try {
			Thread.sleep(2000); //线程休眠
		} catch (InterruptedException ex) {
		}
		graphics.setColor(Color.orange); //设置当前颜色
		graphics.fillRect(0, 0, width, height); //填充背景
		for (int i = 0; i < 6; i++) { //三维深度
			graphics.setColor( //设置渐变颜色
				new Color(
					255 - ((255 - j) * i) / 10,
					255 - ((255 - k) * i) / 10,
					255 - ((255 - l) * i) / 10));
			graphics.drawString(message, 15 - i, height - 15-i); //绘制字符串
		}
		g.drawImage(image, 0, 0, this); //绘制Image到屏幕
	}

	public void paint(Graphics g) {
		update(g);
	}
}
