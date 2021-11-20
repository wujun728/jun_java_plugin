package book.applet.tongxin;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.TextField;

/**
 * 说明:Applet类中可以取得网页的上下文句柄,因此,同网页内的两个Applet是可以互相访问到的,
 * 在本例中注意Applet2的HTLM代码一定要加上name="Applet2",否则无法取得Applet2得句柄.
 * 编译的时候先编译Applet2,这样Applet1中才能引用Applet2这个类.
 */
public class Applet1 extends Applet {
	TextField tf = new TextField("Applet2,你能收到吗?", 20);

	//发送按钮
	Button b = new Button("发送到Applet2");

	public void init() {
		// 设置布局管理器为FlowLayout
		setLayout(new FlowLayout());
		add(tf);
		add(b);
	}

	// 处理按钮事件
	public boolean action(Event ev, Object obj) {
		// 如果事件是从按钮来的
		if (ev.target instanceof Button) {
			String msg = tf.getText();
			// 取Applet2的句柄
			Applet2 applet2 = (Applet2) getAppletContext().getApplet("Applet2");
			if (applet2 != null) {
				//	 调用applet2中的函数
				applet2.AppendText(msg);
				return true;
			} else {
				tf.setText("没有找到Applet2");
				return false;
			}
		}
		return false;
	}
}