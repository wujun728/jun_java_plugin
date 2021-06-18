import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class ScreenDemo extends MIDlet implements CommandListener {

	private Display display; // 设备的显示器
	private TextField userName; // 用于显示用户名的文本域
	private TextField password; // 用于显示密码的文本域
	private Form form; // 表单
	private Command cancel; // 取消命令
	private Command login; // 登录命令

	public ScreenDemo() {
		userName = new TextField("用户名", "", 10, TextField.ANY);  //实例化文本域
		password = new TextField("密   码", "", 10, TextField.PASSWORD);
		form = new Form("用户登录"); //实例化表单
		cancel = new Command("取消", Command.CANCEL, 1); //实例化命令
		login = new Command("登录", Command.OK, 2);
	}

	// 重载抽象类MIDlet的抽象方法startApp()
	protected void startApp() {
		display = Display.getDisplay(this); //取得设备的显示器
		Ticker ticker = new Ticker("请入用户名和密码"); // 创建滚动条
		form.setTicker(ticker); // 把滚动条加到表单上
		form.append(userName); // 把用户名文本添域加到表单上
		form.append(password); // 把密码文本域添加到表单上
		form.addCommand(cancel); // 为表单加上取消命令
		form.addCommand(login); // 为表单加上登录命令
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

	// 登录处理
	protected void validateUser(String name, String password) {
		if (name.equals("1") && password.equals("1")) {
			passed();
		} else {
			tryAgain();
		}
	}

	// 登录成功时，显示登录成功信息
	protected void passed() {
		// 登录成功警报
		Alert pass = new Alert("登录信息", "你已成功登录", null, AlertType.ERROR);
		pass.setTimeout(Alert.FOREVER); // 设置警报为模式警报
		display.setCurrent(pass, form); // 显示登登录成功警报，然后显示登录窗口
	}

	// 登录错误时，显示错误信息并返回登录窗口
	protected void tryAgain() {
		// 登录错误警报
		Alert error = new Alert("登录错误", "请重新输入用户名和密码", null, AlertType.ERROR);
		error.setTimeout(Alert.FOREVER); // 设置警报为模式警报
		userName.setString(""); // 重设用户名
		password.setString(""); // 重设密码
		display.setCurrent(error, form); // 显示登录错误警报，然后显示登录窗口
	}

	// 实现接口CommandListener的方法
	public void commandAction(Command c, Displayable d) {
		if (c == cancel) {
			destroyApp(false); // 销毁程序
			notifyDestroyed();
		} else if (c == login) {
			validateUser(userName.getString(), password.getString());
		}
	}
}
