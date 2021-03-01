import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

// 文字跑马灯与信息窗口

public class MessageDemo extends MIDlet implements CommandListener {

	private Display display; // 设备的显示器
	private Form form; // 表单
	private Command cancel; // 取消命令
	private String message="随着越来越多手提电话和个人数字助理开始融入到信息高速公路之上，从移动设备上访问Web站点变得越来越重要。Java开创了消费设备中小型的储存容量的先河，它是用于开发手机、传呼机及其他微型设备应用程序的理想语言。";
	private StringItem messageItem; //信息显示Item


	public MessageDemo() {
		form = new Form("信息显示");
		cancel = new Command("取消", Command.CANCEL, 1);
		messageItem=new StringItem("",message);
	}

	// 重载抽象类MIDlet的抽象方法startApp()
	protected void startApp() {
		display = Display.getDisplay(this); //取得设备的显示器
		Ticker ticker = new Ticker("使用J2ME设计无线网络应用程序"); // 创建滚动条
		form.setTicker(ticker); // 把滚动条加到表单上
		messageItem.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_LARGE)); //设置显示字体
		form.append(messageItem); // 把显示信息添加到表单上
		form.addCommand(cancel); // 为表单加上取消命令
		form.setCommandListener(this); // 为表单设置命令监听器
		display.setCurrent(form); // 显示表单
	}

	// 重载抽象类MIDlet的抽象方法pauseApp()
	protected void pauseApp() {
	}

	// 重载抽象类MIDlet的抽象方法destroyApp()
	protected void destroyApp(boolean u) {
		notifyDestroyed();
	}

	// 实现接口CommandListener的方法
	public void commandAction(Command c, Displayable d) {
		if (c == cancel) {
			destroyApp(false); // 销毁程序
			notifyDestroyed();
		}

	}
}
