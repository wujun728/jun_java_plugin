import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.io.IOException;

// Canvas绘图程序

public class CanvasDemo extends MIDlet implements CommandListener {

	private Command exitCommand = new Command("退出", Command.EXIT, 1);  //退出命令
	private Command backCommand = new Command("返回", Command.BACK, 3);  //返回命令
    private Display display;  // 设备的显示器
    private List menuList;  //图片名的主菜单列表
	private DrawingCanvas canvas;  //显示绘制的图形
	
	String[] itemNames = {  //显示列表名称
        "直线",
        "弧",
        "矩形",
        "圆角矩形",
        "三角形",
        "文字"
    };    

	public CanvasDemo() {
		display = Display.getDisplay(this); //取得设备的显示器
	}
	
	// 重载抽象类MIDlet的抽象方法startApp()
	protected void startApp() {
		canvas = new DrawingCanvas();  // 创建DrawingCanvas对象canvas
		canvas.addCommand(exitCommand); // 为canvas加上退出命令
		canvas.addCommand(backCommand); // 为canvas加上返回命令
		canvas.setCommandListener(this); // 为canvas设置命令监听者
	
		
        int num = itemNames.length;  // 菜单项个数
        Image[] imageArray = new Image[num]; // 列表的图标数组
        try {
            Image icon = Image.createImage("/Icon.png"); // 创建列表的图标
	        for(int i=0;i<num;i++) {
	        imageArray[i] =icon;	
	        }            
        } catch (java.io.IOException err) {
       	}

        menuList = new List("Canvas绘图程序", Choice.IMPLICIT, itemNames, imageArray);
        menuList.addCommand(exitCommand); // 为主菜单列表加上退出命令
        menuList.setCommandListener(this); // 为主菜单列表设置命令监听器
        display.setCurrent(menuList);  // 显示主菜单列表		
	}
	
	// 重载抽象类MIDlet的方法pauseApp()
	protected void pauseApp() {
	}
	
	// 重载抽象类MIDlet的方法destroyApp()
	protected void destroyApp(boolean unconditional) {
	}
	
	// 实现接口CommandListener的方法
    public void commandAction(Command c, Displayable d) {
        if (d.equals(menuList)) {
            if (c == List.SELECT_COMMAND) {
            	int select = ((List)d).getSelectedIndex();  //得到选中的菜单项
            	switch(select) {
            		case 0:
            			canvas.drawLine();  // 绘制直线
            			display.setCurrent(canvas);
            			break;
            		case 1:
            			canvas.drawArc();  // 绘制弧
            			display.setCurrent(canvas);
            			break;
            		case 2:
            			canvas.drawRect();  // 绘制矩形
            			display.setCurrent(canvas);
            			break;
            		case 3:
            			canvas.drawRoundRect();  // 绘制圆角矩形
            			display.setCurrent(canvas);
            			break;
            		case 4:
            			canvas.drawTriangle();  // 绘制三角形
            			display.setCurrent(canvas);
            			break;
            		case 5:
            			canvas.drawText();  // 绘制文字
            			display.setCurrent(canvas);
            	}
            }
        }
        
        if (c == backCommand) {
                display.setCurrent(menuList);
        } else if (c == exitCommand) {
            destroyApp(false);  // 销毁程序
            notifyDestroyed();
        }
    }
	
	// 绘制图形的画布
	public class DrawingCanvas extends Canvas {
		int w = getWidth();  // 画布的宽度
		int h = getHeight();  // 画布的高度
		Image buffer = Image.createImage(w, h);  // 用于绘图的缓冲图像
		Graphics gc = buffer.getGraphics();  // 获取缓冲图像的图形环境
		
		// 清除画布
		public void clearScreen() {
			gc.setColor(255,255,255);  // 设置绘图颜色为白色
			gc.fillRect(0,0,w,h);  // 把缓冲图像填充为白色
			gc.setColor(255,0,0);  // 设置绘图颜色为红色
		}
		
		// 绘制直线
		public void drawLine() {
			setTitle("直线");  // 设置画布的标题
			clearScreen();  // 清除画布
			gc.drawLine(10,10,w-20,h-20);  // 绘制黑色直线
			gc.setColor(0,0,255);  // 设置绘图颜色为蓝色
			gc.drawLine(10,h/2,w-10,h/2);  // 绘制蓝色直线
		}
		
		// 绘制弧
		public void drawArc() {
			setTitle("弧线和填充弧");
			clearScreen();
			gc.drawArc(5,5,w/2-20,h/2-20,60,216);  // 绘制弧线
			gc.drawArc(5,h/2-10,w/2-20,h/2-20,0,360);  // 绘制圆
			gc.setColor(0,0,255);
			gc.fillArc(w/2,5,w/2-20,h/2-20,60,216);  // 绘制填充弧线
			gc.fillArc(w/2,h/2-10,w/2-20,h/2-20,0,360);  // 绘制填充圆
		}
		
		// 绘制矩形
		public void drawRect() {
			setTitle("矩形和填充矩形");
			clearScreen();
			gc.drawRect(25,25,w/2-30,h/2-30);  // 绘制矩形
			gc.fillRect(w/2+25,25,w/2-30,h/2-30);  // 绘制填充矩形
		}
		
		// 绘制圆角矩形
		public void drawRoundRect() {
			setTitle("圆角矩形和填充圆角矩形");
			clearScreen();
			gc.drawRoundRect(5,5,w-5-30,h/2-30,20,20);  // 绘制圆角矩形
			gc.setColor(0,0,255);
			gc.fillRoundRect(5,h/2,w-30,h/2-30,20,20);  // 绘制填充圆角矩形
		}
		
		// 绘制三角形
		public void drawTriangle() {
			setTitle("填充三角形");
			clearScreen();
			gc.fillTriangle(w/2, h/6, w/6, h/2, w/2, h/2);
		}
		
		// 绘制文字
		public void drawText() {
			setTitle("文字"); //设置标题
			clearScreen();
			gc.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_SMALL));  // 设置字体
			gc.drawString("Hello World!",0,0,gc.TOP|gc.LEFT);  // 使用当前字体绘制文字
			gc.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD|Font.STYLE_UNDERLINED,Font.SIZE_LARGE));
			gc.drawString("Hello World!",0,h/3,gc.TOP|gc.LEFT);
		}

		public void paint(Graphics g) {
			g.drawImage(buffer, 0, 0, 0);  // 把缓冲区图像的内容绘制到画布上
		}
	}
}
	