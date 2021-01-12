import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;

// 移动的遮照效果
public class ClipDemo extends JFrame {
		
	public ClipDemo() {		
		super("移动的遮照效果"); //调用父类构造函数
		Container content = getContentPane(); //获得窗口的容器
		DrawPanel drawPanel = new DrawPanel(); //创建DrawPanel对象用于绘制图形
		content.add(drawPanel, BorderLayout.CENTER); //增加组件到容器上		
		setSize(250, 160); //设置窗口尺寸
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}

	public static void main(String[] args) {
		new ClipDemo();
	}
	
	//显示图形的面板
	class DrawPanel extends JPanel implements Runnable {
		Thread thread;	
		Image image = this.getToolkit().getImage("image0.jpg"); //获取图像
		BufferedImage bufImage; //缓冲区图像
		Graphics2D bufImageG; //图形环境
		Ellipse2D aEllipse;  //用于遮照的椭圆
		int eW,eH;  //椭圆的宽度和高度
		int imageW,imageH;  //图像的宽度和高度
		int delay = 20;  //遮照显示的延迟时间
		
		public DrawPanel() {
			enableEvents(AWTEvent.MOUSE_EVENT_MASK|AWTEvent.MOUSE_MOTION_EVENT_MASK); //允许的事件
			MediaTracker mt = new MediaTracker(this); //实例化媒体加载器 
			mt.addImage(image, 0); //增加待加载的图像
			try {
				mt.waitForAll(); //等待图像的加载完成
			} catch (Exception ex) {
				ex.printStackTrace(); //输出出错信息
			}
			bufImage =new BufferedImage(image.getWidth(this),image.getHeight(this),BufferedImage.TYPE_INT_ARGB); //创建缓冲区图像
			bufImageG = bufImage.createGraphics(); //创建缓冲区图像的图形环境
			imageW = image.getWidth(this); //获取图像宽度
			imageH = image.getHeight(this); //获取图像高度
			eW = imageW/4;
			eH = imageH/4;						
			startThread(); //开始运行线程
		}
		
		protected void processMouseEvent(MouseEvent e) {
			if(e.getID() == MouseEvent.MOUSE_EXITED) { //鼠标退出时运行线程
				startThread();
			}
		}
		
		public void processMouseMotionEvent(MouseEvent e) {
			if(e.getID() == MouseEvent.MOUSE_MOVED) {
				stopThread(); //停止线程运行
				displayPicture(e.getX()-eW,e.getY()-eH); //遮照图片效果
			}				
		}
		//开始线程		
		public void startThread() {
			if(thread==null) {
				thread = new Thread(this);
				thread.start(); //线程运行
			}
		}
		public void stopThread() {
	        if(thread != null)
	        {
	            thread.interrupt(); //中断线程运行
	            thread = null;
	        }			
		}
		//实现接口Runnable的方法
		public void run() {
			double x=Math.random()*3/4*imageW,y=Math.random()*3/4*imageH; //随机运动的坐标点
			double xi=Math.random()*imageW/64,yi=Math.random()*imageH/64;
			try {
				while(true) {
					x +=xi;
					y +=yi;
					if(x>(3*imageW/4)) {
						xi = -Math.random()*imageW/64;
						x += xi;
						yi = (Math.random()-0.5)*imageW/32;
					}
					if(x<0) {
						xi = Math.random()*imageW/64;
						x +=xi;
						yi = (Math.random()-0.5)*imageW/32;
					}
					
					if(y>(3*imageH/4)) {
						yi = -Math.random()*imageW/64;
						y +=yi;
						xi = (Math.random()-0.5)*imageW/32;
					}
					if(y<0) {
						yi = Math.random()*imageW/64;
						y +=yi;
						xi = (Math.random()-0.5)*imageW/32;
					}
					displayPicture(x,y); //显示图片,遮照其余总分
					Thread.sleep(delay); //线程休眠
				}
			} catch(InterruptedException e) {}
		}
		
		//显示图像，遮照的显示位置为(x,y)
		public void displayPicture(double x,double y) {
			aEllipse = new Ellipse2D.Double(x,y,eW,eH); //得到椭圆的实例
			bufImageG.setColor(Color.black); //设置当前颜色
			bufImageG.fillRect(0,0,imageW,imageH); //绘制填充矩形,填充前景
			bufImageG.setClip(aEllipse); //设置剪裁区域
			bufImageG.drawImage(image, 0, 0, this); //在图形环境绘制缓冲区图像
			repaint();
		}
		
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bufImage, 0, 0, this); //在图形环境绘制缓冲区图像
		}
	}
}
