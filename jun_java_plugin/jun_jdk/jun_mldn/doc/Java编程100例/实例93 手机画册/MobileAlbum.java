import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

// 手机画册

public class MobileAlbum extends MIDlet implements CommandListener {
    // 创建退出命令和返回命令
    private final static Command EXIT = new Command("退出", Command.EXIT, 1);
    private final static Command BACK = new Command("返回", Command.BACK, 1);
    private Display display;  // 设备的显示器
    private List mainList;  // 用于图片名的主列表
    private Form form;  // 用于显示图像的表单
    String[] pictureName = {
        "Title", 
        "Girl", 
        "Love",
        "Cow"
    };    
    String[] fileName = {
        "/title.jpg", 
        "/girl.jpg", 
        "/love.jpg",
        "/cow.jpg" 
    };
    
    public MobileAlbum() {
        display = Display.getDisplay(this); //取得设备的显示器
    }

	// 重载抽象类MIDlet的抽象方法startApp()
    protected void startApp() {
        int num = pictureName.length;  // 图片个数
        Image[] imageArray = new Image[num]; // 列表的图标数组
        try {
            Image icon = Image.createImage("/icon.png"); // 创建图标
	        for(int i=0;i<num;i++) {
	        imageArray[i] =icon;
	        }            
        } catch (java.io.IOException err) {
       	}

        mainList = new List("手机画册",Choice.IMPLICIT,pictureName,imageArray);   // 实例化列表
        mainList.addCommand(EXIT); // 为列表加上退出命令
        mainList.setCommandListener(this); // 设置命令监听器
        display.setCurrent(mainList); // 显示列表
    }

	// 重载抽象类MIDlet的抽象方法destroyApp()
    protected void destroyApp(boolean unconditional) {
    }

	// 重载抽象类MIDlet的抽象方法pauseApp()
    protected void pauseApp() {
    }

	// 实现接口CommandListener的方法
    public void commandAction(Command c, Displayable d) {
        if (d.equals(mainList)) {
            if (c == List.SELECT_COMMAND) {
            	getImage(((List)d).getSelectedIndex());  // 取得图片放在表单上
                display.setCurrent(form); // 显示表单
            }
        } else {
            if (c == BACK) {
                display.setCurrent(mainList); // 显示主列表
            }
        }

        if (c == EXIT) {
            destroyApp(false);  // 销毁程序
            notifyDestroyed();
        }
    }
    
	// 装载并显示图像
    protected void getImage(int index) {
        Image image =null;
        try {
            image = Image.createImage(fileName[index]);  // 以指定文件创建一个固定图像
        } catch (java.io.IOException err) {}        
        ImageItem imageItem = new ImageItem(null,image,ImageItem.LAYOUT_CENTER,"img"); // 为图像image创建一个ImageItem对象imageItem
        form = new Form(pictureName[index]);  // 创建显示图像的表单
    	form.append(imageItem);  // 把imageItem加入表单
        form.addCommand(BACK); // 为表单加上返回命令
        form.addCommand(EXIT); // 为表单加上退出命令
        form.setCommandListener(this); // 为表单设置命令监听器
    }
}

