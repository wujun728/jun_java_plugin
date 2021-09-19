package book.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 时钟程序
 */
public class Clock extends JFrame {
	// 今天的日期对象
	private GregorianCalendar now = new GregorianCalendar();
	// 时钟标签，上面画的是圆形时钟
	private ClockLabel clockLabel = new ClockLabel();
	// 星期标签，指示星期
	private JLabel weekLabel = new JLabel();
	// 日期标签，指示日期
	private JLabel dateLabel = new JLabel();
	// 品牌标签
	private JLabel remarkLabel = new JLabel();
	// 时间标签，指示时间
	private JLabel timeLabel = new JLabel();
	public Clock() {
		// 设置主界面属性
		setTitle("时钟");
		setSize(500, 480);
		setLocation(100, 100);
		init();
		setResizable(false);
	}

	private void init() {

		// 初始化品牌标签
		remarkLabel.setText("MyClock");
		remarkLabel.setLocation(225, 80);
		remarkLabel.setSize(100, 30);
		remarkLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		remarkLabel.setForeground(Color.darkGray);

		// 初始化星期标签
		weekLabel.setSize(60, 20);
		weekLabel.setLocation(315, 190);
		weekLabel.setForeground(Color.darkGray);
		weekLabel.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		// 为星期标签赋值
		int week = now.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1:
			weekLabel.setText("SUNDAY");
			break;
		case 2:
			weekLabel.setText("MONDAY");
			break;
		case 3:
			weekLabel.setText("TUESDAY");
			break;
		case 4:
			weekLabel.setText("WEDNESDAY");
			break;
		case 5:
			weekLabel.setText("THURSDAY");
			break;
		case 6:
			weekLabel.setText("FRIDAY");
			break;
		case 7:
			weekLabel.setText("SATURDAY");
			break;
		}

		// 初始化日期标签
		dateLabel.setSize(20, 20);
		dateLabel.setLocation(370, 190);
		dateLabel.setForeground(Color.darkGray);
		dateLabel.setFont(new Font("Fixedsys", Font.BOLD, 12));
		// 设置日期标签的值
		dateLabel.setText("" + now.get(Calendar.DATE));

		// 初始化时间标签
		timeLabel.setSize(500, 30);
		timeLabel.setLocation(100, 400);
		timeLabel.setForeground(new Color(0, 64, 128));
		timeLabel.setFont(new Font("Fixedsys", Font.PLAIN, 15));

		// 将各组件加入到主窗口中
		Container con = getContentPane();
		con.setBackground(Color.white);
		con.setLayout(null);
		con.add(weekLabel);
		con.add(dateLabel);
		con.add(remarkLabel);
		con.add(timeLabel);
		con.add(clockLabel);
	}

	public static void main(String[] args) {
		Clock clock = new Clock();
		clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clock.setVisible(true);
	}

	// 自定义时钟标签，画一个圆形的时钟
	class ClockLabel extends JLabel implements Runnable {
		// 时钟标签的宽度和高度
		private final int WIDTH = 500;
		private final int HEIGHT = 440;

		// 圆形时钟的X半径和Y半径
		private final int CIRCLE_X_RADIUS = 150;
		private final int CIRCLE_Y_RADIUS = 155;

		// 圆形时钟的原点
		private final int CIRCLE_X = 250;
		private final int CIRCLE_Y = 200;

		// 圆形时钟指针的长度
		private final int HOUR_LENGTH = 50;
		private final int MIN_LENGTH = 80;
		private final int SEC_LENGTH = 100;

		// 当前时针所处的角度
		double arcHour = 0;
		// 当前分针所处的角度
		double arcMin = 0;
		// 当前秒针所处的角度
		double arcSec = 0;

		// 颜色的透明度，
		// 当颜色的alpha值为1或者255时，表示不透明，其他值时透明
		int alpha = 50;
		// 标识颜色透明度变化的方向，
		// 为true时表示越来越透明，为false时表示月来越不透明
		boolean flag = false;
		// 背景图片的id，轮换显示背景图片时使用
		int imageID = 0;
		// 背景图片对象数组
		Image img[] = new Image[5];
		// 背景图片URL
		URL url[] = new URL[] { ClockLabel.class.getResource("image/1.jpg"),
				ClockLabel.class.getResource("image/2.jpg"),
				ClockLabel.class.getResource("image/3.jpg"),
				ClockLabel.class.getResource("image/4.jpg"),
				ClockLabel.class.getResource("image/5.jpg"),
				ClockLabel.class.getResource("image/6.jpg") };

		// 一个具有缓冲区的图像对象
		BufferedImage bufferedImage = null;
		int imageSize = 2 * Math.max(CIRCLE_X_RADIUS, CIRCLE_Y_RADIUS);
		// 为bufferedImage创建的Graphics对象，用于在bufferedImage上画图
		Graphics bufferedImageGraphics = null;
		// 时钟线程，因为时钟每秒钟都要动一次，所以用线程
		Thread clockThread = null;
		
		// 构造方法
		public ClockLabel() {

			// 设置时钟标签的大小
			this.setSize(WIDTH, HEIGHT);

			// 获取时针、分针、秒针当前的角度
			arcHour = now.get(Calendar.HOUR) * 30; // 一格30度
			arcMin = now.get(Calendar.MINUTE) * 6; // 一格6度
			arcSec = now.get(Calendar.SECOND) * 6; // 一个6度

			// 根据图片URL创建图片对象
			Toolkit tk = this.getToolkit();
			img[0] = tk.createImage(url[0]);
			img[1] = tk.createImage(url[1]);
			img[2] = tk.createImage(url[2]);
			img[3] = tk.createImage(url[3]);
			img[4] = tk.createImage(url[4]);
			try {
				// 使用MediaTracker加载图片对象
				// MediaTracker 类是一个跟踪多种媒体对象状态的实用工具类,
				// 媒体对象可以包括音频剪辑和图像，但目前仅支持图像.
				MediaTracker mt = new MediaTracker(this);
				mt.addImage(img[0], 0);
				mt.addImage(img[1], 0);
				mt.addImage(img[2], 0);
				mt.addImage(img[3], 0);
				mt.addImage(img[4], 0);
				// 加载媒体跟踪器中所有的图像。
				mt.waitForAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 创建一个有缓冲的Image对象
			bufferedImage = new BufferedImage(imageSize, imageSize,
					BufferedImage.TYPE_INT_ARGB);
			// 为BufferedImage创建Graphics2D对象，
			// 以后用该Graphics2D对象画的图都会画在BufferedImage中
			bufferedImageGraphics = bufferedImage.createGraphics();

			// 启动线程
			clockThread = new Thread(this);
			clockThread.start();
		}

		public void paint(Graphics g1) {
			// Graphics2D继承Graphics，比Graphics提供更丰富的方法
			Graphics2D g = (Graphics2D) g1;

			/** ***画圆形时钟的刻度，每6度便有一个刻度**** */
			for (int i = 0; i < 360; i = i + 6) {
				// 设置画笔的颜色为蓝色
				g.setColor(Color.blue);
				// 设置画笔的宽度为2
				g.setStroke(new BasicStroke(2));

				if (i % 90 == 0) {
					// 对于0，3，6，9点位置，使用一个大的蓝色刻度
					g.setStroke(new BasicStroke(5));// 画笔宽度为5
					// 当起点和终点一样时，画的就是点
					g.drawLine(CIRCLE_X 
						+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y 
						+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X 
						+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
						+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else if (i % 30 == 0) {
					// 如果角度处于小时的位置，而且还不在0，3，6，9点时，画红色的小刻度
					g.setColor(Color.red);
					g.drawLine(CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
								CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else {
					// 其他位置就画蓝色的小刻度
					g.drawLine(CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
								CIRCLE_X
							+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
								CIRCLE_Y
							+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				}
			}

			/** ****** 画时钟的指针 ******** */
			// 画时针
			Line2D.Double lh = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcHour - 90) * Math.PI / 180) * HOUR_LENGTH,
					CIRCLE_Y + Math.sin((arcHour - 90) * Math.PI / 180)
							* HOUR_LENGTH);
			// 设置画笔宽度和颜色
			g.setStroke(new BasicStroke(6));
			g.setColor(new Color(0, 128, 0));
			// 利用Graphics2D的draw方法画线
			g.draw(lh);

			// 画分针
			Line2D.Double lm = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcMin - 90) * Math.PI / 180) * MIN_LENGTH,
					CIRCLE_Y + Math.sin((arcMin - 90) * Math.PI / 180)
							* MIN_LENGTH);
			g.setStroke(new BasicStroke(3));
			g.setColor(new Color(0, 128, 192));
			g.draw(lm);

			// 画秒针
			Line2D.Double ls = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcSec - 90) * Math.PI / 180) * SEC_LENGTH,
					CIRCLE_Y + Math.sin((arcSec - 90) * Math.PI / 180)
							* SEC_LENGTH);
			g.setStroke(new BasicStroke(1));
			// 秒针的颜色随机，使动画效果明显。
			g.setColor(new Color((int) (Math.random() * 255), (int) (Math
					.random() * 255), (int) (Math.random() * 255)));
			g.draw(ls);

			/** **** 画时钟背景，并将其透明处理 ******* */
			// 所有使用bufferedImageGraphics画的图像，都画在bufferedImage上，
			// drawImage方法的参数含义分别是：背景图片对象、目标位置第一个角的X、Y坐标、目标位置第二个角的X、Y坐标、
			// 源位置第一个角的X、Y坐标、源位置第二个角的X、Y坐标、图像对象改变时的通知接受者
			bufferedImageGraphics.drawImage(img[imageID], 0, 0, imageSize,
					imageSize, 0, 0, imageSize + 10, imageSize + 10, this);
			// 将背景图片透明化
			for (int j = 0; j < imageSize; j++) {
				for (int i = 0; i < imageSize; i++) {
					// 获得背景图像中(i, j)坐标的颜色值
					int pix = bufferedImage.getRGB(i, j);
					Color c = new Color(pix);
					int r = c.getRed();
					int gr = c.getGreen();
					int b = c.getBlue();
					// 通过Color对象的alpha，使颜色透明。
					int newpix = new Color(r, gr, b, alpha).getRGB();
					// 重新设置背景图像该象素点的颜色
					bufferedImage.setRGB(i, j, newpix);
				}
			}

			/**以上画背景操作都是画在bufferedImage上的，这里要将bufferedImage画在ClockLabel**/
			// 将当前用户剪贴区 Clip 与椭圆区域相交，并将 Clip 设置为所得的交集
			g.clip(new Ellipse2D.Double(95, 45, imageSize, imageSize));
			g.setColor(Color.white);
			// 在用户剪贴区画bufferedImage
			g.drawImage(bufferedImage, 95, 45, this);
		}

		public void run() {
			try {
				// 
				while (clockThread != null) {
					// 获得完整的日期格式
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.FULL);
					// 格式化当前时间
					String s = df.format(new Date());
					timeLabel.setText(s);
					// 每动一次对时钟指针的角度进行调整
					arcSec += 6;
					arcMin += 0.1;
					arcHour += 0.1 / 60;
					// 当角度满一圈时，归0
					if (arcSec >= 360) {
						arcSec = 0;
					}
					if (arcMin >= 360) {
						arcMin = 0;
					}
					if (arcHour >= 360) {
						arcHour = 0;
					}
					// 实现背景透明度渐变
					// 从浅入深，再由深入浅。
					if (flag) {
						alpha += 10;
						if (alpha == 50) {
							flag = !flag;
						}
					} else {
						alpha -= 10;
						if (alpha == 0) {
							flag = !flag;
							// 当透明度变化一个轮回时，换一张背景图
							imageID++;
							if (imageID == 4) {
								imageID = 0;
							}
						}
					}
					// 重画时钟标签
					repaint();
					// 等待1秒钟
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}