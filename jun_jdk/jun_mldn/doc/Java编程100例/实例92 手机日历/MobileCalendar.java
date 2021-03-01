import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import java.util.*;

//手机日历

public class MobileCalendar  extends MIDlet implements CommandListener
{
	// 退出命令
    private final static Command EXIT = new Command("Exit", Command.EXIT, 1);
    private Form form;  // 用于容纳日期和时间域的表单
    
    public MobileCalendar() {
        form = new Form("日期和时间信息");
    }

	// 重载抽象类MIDlet的抽象方法startApp()
    protected void startApp() {	    
	    Date dd = new Date();  // 表示当前的日期和时间	
	    TimeZone tz = TimeZone.getTimeZone("GMT+08:00");   // 指定时区为东八区	 
	    DateField dateAndTime = new DateField("日期和时间", DateField.DATE_TIME,tz);  // 创建包含日期字段和时间字段的DateField对象dateAndTime   	      	    
	    dateAndTime.setDate(dd);   // 设置初始日期和时间
	    form.append(dateAndTime);  // 添加dateAndTime到表单form
	   
		DateField date = new DateField("日期", DateField.DATE);  // 创建包含日期字段的DateField对象date
		date.setDate(dd);   // 设置dateAndTime的初始日期
	    form.append(date);  // 添加date到表单form
	    
	    DateField time = new DateField("时间", DateField.TIME); // 创建包含时间字段的DateField对象time
	    form.append(time);  // 添加time到表单form
	            
	    form.addCommand(EXIT);  // 添加退出命令到表单form
	    form.setCommandListener(this);  // 为表单form设置命令监听者
        Display.getDisplay(this).setCurrent(form);  // 显示表单form
    }    

	// 重载抽象类MIDlet的抽象方法destroyApp()
    protected void destroyApp(boolean unconditional) { }    

	// 重载抽象类MIDlet的抽象方法pauseApp()
    protected void pauseApp() {}   
    
	// 实现接口CommandListener的方法
    public void commandAction(Command c, Displayable d) {
        if (c == EXIT) {
            destroyApp(false);  // 销毁程序
            notifyDestroyed();
        }
    }    
}
