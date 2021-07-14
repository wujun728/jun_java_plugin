package book.applet;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.text.DateFormat;
import java.util.Date;

/**
 * 本例实现一个Applet时钟，演示Applet基本生命周期。
 */
public class Clock extends Applet implements Runnable {
	// 显示时间的标签
    Label time; 
    // 时间的格式
    DateFormat timeFormat;
    // 更新时间的线程
    Thread timer; 
    // 线程是否运行的标志位
    boolean running;

    /**
     * 初始化Applet，在第一次加载Applet时使用
     **/
    public void init() {
    	// 创建时间标签
        time = new Label();
        time.setFont(new Font(null, Font.BOLD, 15));
        time.setBounds(0,0, 100, 50);
        time.setAlignment(Label.CENTER);
        // 将标签加入到Applet容器
        setLayout(new BorderLayout());
        add(time, BorderLayout.CENTER);
        timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
    }
    
    /**
     * 启动Applet时调用此方法，在这里启动了线程
     */
    public void start() {
        running = true;  
        if (timer == null) { 
            timer = new Thread(this);
            timer.start();
        }
    }

    /**
     * 线程的运行体，不断的刷新时间标签的值
     */
    public void run() {
		while (running) {
			time.setText(timeFormat.format(new Date()));
			// 休眠1秒钟
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		timer = null;
	}

    /**
     * 浏览器停止Applet时调用该方法
     */
    public void stop() {
    	// 关闭线程
    	running = false;
	} 
    /**
     * 浏览器清除Applet时调用该方法
     */
    public void destroy() {
	} 
}
