package book.graphic;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * 捕获屏幕，相当于Windows的PrintScreen键。
 * 主窗口
 */
public class ScreenCaptureFrame extends JFrame implements ActionListener {

	// 捕获屏幕的工具类，是一个自定义类。
	private ScreenCaptureUtil scrCaptureUtil = null;
	// 画布，自定义类，用于画捕获到的屏幕图像
	private PaintCanvas canvas = null;

	// 构造函数
	public ScreenCaptureFrame() {
		super("Screen Capture");
		init();
	}
	// 初始化方法
	private void init()	{
		// 新建抓屏工具
		scrCaptureUtil = new ScreenCaptureUtil();
		// 新建画布
		canvas = new PaintCanvas(scrCaptureUtil);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(canvas, BorderLayout.CENTER);
		// 添加一个按钮，用于触发抓屏事件
		JButton capButton = new JButton("抓 屏");
		c.add(capButton, BorderLayout.SOUTH);
		capButton.addActionListener(this);
		this.setSize(400, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	// 点击“抓屏”按钮时，在画布上画屏幕图像
	public void actionPerformed(ActionEvent e) {
		canvas.drawScreen();
	}

	public static void main(String[] args) {
		new ScreenCaptureFrame();
	}
}

/**
 * 抓屏工具类
 */
class ScreenCaptureUtil {
	// 抓屏的主要工具类
	private Robot robot = null;
	// 屏幕的矩形图像
	private Rectangle scrRect = null;

	// 构造函数
	public ScreenCaptureUtil() {
		try {
			// 新建一个抓屏工具
			robot = new Robot();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 获取屏幕的矩形图像
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		scrRect = new Rectangle(0, 0, scrSize.width, scrSize.height);
	}
	
	// 抓屏方法，返回一个图像
	public BufferedImage captureScreen() {
		BufferedImage scrImg = null;
		try {
			// robot的createScreenCapture方法实现抓屏
			// 参数指定了要抓屏的范围，由于scrRect是整个屏幕的矩形，所以，这里抓的是全屏图
			// 将抓取的屏幕图像返回
			scrImg = robot.createScreenCapture(scrRect);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return scrImg;
	}
}
/**
 * 画布类，用于显示抓屏得到的图像
 */
class PaintCanvas extends JPanel {
	// 抓屏工具
	private ScreenCaptureUtil scrCaptureUtil = null;
	// 待画的图像　
	private BufferedImage scrImg = null;
	
	//构造方法，传入抓屏工具对象
	public PaintCanvas(ScreenCaptureUtil screenUtil) {
		this.scrCaptureUtil = screenUtil;
	}
	// 重载JPanel的paintComponent，用于画背景
	protected void paintComponent(Graphics g) {
		if (scrImg != null) {
			int iWidth = this.getWidth();
			int iHeight = this.getHeight();
			g.drawImage(scrImg, 0, 0, iWidth, iHeight, 0, 0, scrImg.getWidth(),
					scrImg.getHeight(), null);
		}
	}
	// 画屏幕图像的方法
	public void drawScreen() {
		Graphics g = this.getGraphics();
		// 抓屏，获取屏幕图像
		scrImg = scrCaptureUtil.captureScreen();
		if (scrImg != null) {
			// 画图
			this.paintComponent(g);
		}
		// 释放资源
		g.dispose();
	}

}