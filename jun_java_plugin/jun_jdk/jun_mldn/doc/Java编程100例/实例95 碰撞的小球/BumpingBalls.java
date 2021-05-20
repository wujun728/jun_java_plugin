// 碰撞的小球
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class BumpingBalls extends MIDlet implements CommandListener {

    Display display;  // 设备的显示器
    BallCanvas canvas;  // 用于显示小球的屏幕 
    private Command exitCommand = new Command("Exit", Command.EXIT, 1);  //退出命令

    public BumpingBalls() {
		display = Display.getDisplay(this);	 //取得设备的显示器
		canvas = new BallCanvas(display);  // 实例化canvas对象
		canvas.addCommand(exitCommand); // 为canvas加上退出命令
		canvas.setCommandListener(this); // 为canvas设置命令监听者
    }

	// 重载抽象类MIDlet的抽象方法startApp()
    public void startApp() throws MIDletStateChangeException {
		canvas.start();
    }    

	// 重载抽象类MIDlet的方法destroyApp()
    public void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		canvas.destroy();
    }
    
	// 重载抽象类MIDlet的方法pauseApp()
    public void pauseApp(){}

	// 实现接口CommandListener的方法
    public void commandAction(Command c, Displayable s) {
			if (c == exitCommand) {
		    try {
				destroyApp(false);  // 销毁程序
				notifyDestroyed();  // 通知销毁程序
		    } catch (MIDletStateChangeException ex) {
		    }
		}
    }
}
