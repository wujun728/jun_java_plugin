package demo.others;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyJApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		// 在init方法中接收来自html页面上的参数
		//String message = getParameter("MESSAGE");
		add(new JLabel("welcome to touch's blog", JLabel.CENTER));
	}

	// 用main方法运行JApplet
	public static void main(String[] args) {
		JFrame frame = new JFrame("Applet is in the frame");
		MyJApplet myJApplet = new MyJApplet();
		// main方法里创建一个框架来放置applet，applet单独运行时，
		// 要完成操作必须手动调用init和start方法
		frame.add(myJApplet, BorderLayout.CENTER);
		myJApplet.init();

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
