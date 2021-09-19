package book.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * 几乎所有时髦的应用都有一个欢迎屏幕。欢迎屏幕既是宣传产品的方法之一，
 * 而且在长时间的应用启动过程中，欢迎屏幕还用来表示应用正在准备过程中。
 */


/**
 * 本例子实现一个欢迎屏幕，常用作应用软件的启动画面。
 */
public class SplashWindow extends JWindow {
	/**
	 * 构造函数
	 * @param filename	欢迎屏幕所用的图片
	 * @param frame		欢迎屏幕所属的窗体
	 * @param waitTime	欢迎屏幕显示的事件
	 */
	public SplashWindow(String filename, JFrame frame, int waitTime) {
		super(frame);
		
		// 建立一个标签，标签中显示图片。
		JLabel label = new JLabel(new ImageIcon(filename));
		// 将标签放在欢迎屏幕中间
		getContentPane().add(label, BorderLayout.CENTER);
		pack();
		// 获取屏幕的分辨率大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 获取标签大小
		Dimension labelSize = label.getPreferredSize();
		// 将欢迎屏幕放在屏幕中间
		setLocation(screenSize.width / 2 - (labelSize.width / 2),
				screenSize.height / 2 - (labelSize.height / 2));
		// 增加一个鼠标事件处理器，如果用户用鼠标点击了欢迎屏幕，则关闭。
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		final int pause = waitTime;
		/**
		 * Swing线程在同一时刻仅能被一个线程所访问。一般来说，这个线程是事件派发线程（event-dispatching thread）。 
		 * 如果需要从事件处理（event-handling）或绘制代码以外的地方访问UI，
		 * 那么可以使用SwingUtilities类的invokeLater()或invokeAndWait()方法。
		 */
		// 关闭欢迎屏幕的线程
		final Runnable closerRunner = new Runnable() {
			public void run() {
				setVisible(false);
				dispose();
			}
		};
		// 等待关闭欢迎屏幕的线程
		Runnable waitRunner = new Runnable() {
			public void run() {
				try {
					// 当显示了waitTime后，尝试关闭欢迎屏幕
					Thread.sleep(pause);
					SwingUtilities.invokeAndWait(closerRunner);
//					SwingUtilities.invokeLater(closerRunner);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		setVisible(true);
		//启动等待关闭欢迎屏幕的线程
		Thread splashThread = new Thread(waitRunner, "SplashThread");
		splashThread.start();
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("欢迎屏幕");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SplashWindow splash = new SplashWindow("C:/temp/scenery.jpg", frame, 60000);
		frame.pack();
		frame.setVisible(true);
	}
}