package book.mutimedia;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * 滚动消息——双缓冲技术
 */
public class RollingMessage extends Canvas implements Runnable {

	//使用双缓冲技术相关的变量
	Image offScreenImg; //存放备用屏幕的内容
	Graphics offScreenG; //备用屏幕的绘图上下文环境

	// 当前线程，以及是否需要停止线程的标志位
	Thread runThread;
	static boolean toStop;
	//每帧画面的时延（毫秒）	
	int delay; 
	
	//要显示的字符串及其长度
	String msg; 
	int msgLength; 
	//当前显示到第几个字符
	int x_character = 0; 

	//消息显示的字体
	Font wordFont;
	
	int width, height;

	public RollingMessage(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	public void init() {
		wordFont = new Font("TimesRoman", Font.PLAIN + Font.BOLD, 15);
		msg = "Welcome to Java world!";
		msgLength = msg.length();

		delay = 300;
		toStop = false;
		runThread = new Thread(this);
		runThread.start();
	}

	public void run() {
		while (!toStop) {
			if (x_character++ >= msgLength) {
				x_character = 0;
				// 当需要重新画时，将备用屏幕的绘图环境置为null
				offScreenG = null;
			}
			repaint();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}

	public void paint(Graphics g) {
		g.setFont(wordFont);
		g.setColor(Color.red);
		g.drawString(msg.substring(0, x_character), 10, height/2);
	}

	public void update(Graphics g) {
		// 如果不用双缓冲技术，可以采用下面的技术
		//		if (x_character == 0) {
		//			g.setColor(getBackground());
		//			g.fillRect(0, 0, 200, 100);
		//			g.setColor(getForeground());
		//		}
		//		paint(g);

		/**
		 * 如果有备用屏幕，就将备用屏幕的绘图上下文环境offScreenG传递给 paint()方法，
		 * paint()方法中所画的内容都将画在备用屏幕上。
		 * 然后再调用 drawImage()方法将备用屏幕offScreenImg中的内容画到当前屏幕上。
		 */
		if (offScreenG != null) {
			//在备用屏幕上画
			paint(offScreenG);
			g.drawImage(offScreenImg, 0, 0, this);
		} else {
			// 在当前屏幕上画
			paint(g);
			/**
			 * 建立备用屏幕
			 * 
			 * createImage方法是用来创建一个空的可以在上面进行绘图的Image对象，
			 * 它的两个整型参数分别表示所创建的该Image对象的宽度和高度，这里
			 * 就设为整个窗体的宽和高，以便将它作为一个备用屏幕。
			 */
			offScreenImg = createImage(width, height);//创建备用屏幕
			/**
			 * 调用Image对象 中的getGraphics( )方法，它是用来获取一个可以在该Image对象上进行绘图的绘图上下
			 * 文环境,后凡是调用offScreenG中的任何绘图 方法（如drawImage(
			 * )方法）都将作用在备用屏幕（offScreenImg）上
			 */
			offScreenG = offScreenImg.getGraphics(); //获取备用屏幕的绘图上下文环境
		}

	}

	public static void toStop() {
		toStop = true;
	}

	public static void main(String[] args) {
		int width = 200;
		int height = 100;
		RollingMessage test = new RollingMessage(width, height);
		JFrame frame = new JFrame("滚动的消息");

		frame.setLocation(0, 0);
		frame.setSize(width, height);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				RollingMessage.toStop();
				System.exit(0);
			}
		});
		frame.getContentPane().add(test);
		frame.setVisible(true);
	}
}

