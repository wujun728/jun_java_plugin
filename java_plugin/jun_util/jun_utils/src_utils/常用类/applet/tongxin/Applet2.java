package book.applet.tongxin;

import java.applet.Applet;
import java.awt.*;

public class Applet2 extends Applet {
	TextField text = new TextField("", 30);

	public void init() {
		setLayout(new FlowLayout());
		add(text);
	}

	// 声明为公开方法以便applet1调用
	public void AppendText(String msg) {
		text.setText("我收到了!是\"" + msg + "\"");
	}
}
