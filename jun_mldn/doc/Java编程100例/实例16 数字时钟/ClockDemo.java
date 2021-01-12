import java.awt.*;
import java.util.*;
import javax.swing.*;

//数字时钟
public class ClockDemo extends JFrame implements Runnable{
	Thread clock;	
	
	public ClockDemo(){
		super("数字时钟");	//调用父类构造函数	
		setFont(new Font("Times New Roman",Font.BOLD,60));	//设置时钟的显示字体
		start(); //开始进程
		setSize(280,100);  //设置窗口尺寸
		setVisible(true);  //窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}
	
	public void start(){ //开始进程
		if (clock==null){ //如果进程为空值
			clock=new Thread(this); //实例化进程
			clock.start(); //开始进程
		}
	}
	
	public void run(){  //运行进程
		while (clock!=null){ 
			repaint(); //调用paint方法重绘界面
			try{
				Thread.sleep(1000);  //线程暂停一秒(1000毫秒)
			}
			catch (InterruptedException ex){
				ex.printStackTrace();  //输出出错信息
			}
		}	
	}
	
	public void stop(){  //停止进程
		clock=null;
	}
	
	public void paint(Graphics g){  //重载组件的paint方法
		Graphics2D g2=(Graphics2D)g;  //得到Graphics2D对象
		Calendar now=new GregorianCalendar(); //实例化日历对象
		String timeInfo=""; //输出信息
		int hour=now.get(Calendar.HOUR_OF_DAY); //得到小时数
		int minute=now.get(Calendar.MINUTE);   //得到分数
		int second=now.get(Calendar.SECOND);  //得到秒数
		
		if (hour<=9) 
			timeInfo+="0"+hour+":"; //格式化输出
		else 
			timeInfo+=hour+":";
		if (minute<=9)
			timeInfo+="0"+minute+":";
		else
			timeInfo+=minute+":";
		if (second<=9)
			timeInfo+="0"+second;
		else
			timeInfo+=second;

		g.setColor(Color.white);  //设置当前颜色为白色
		Dimension dim=getSize();  //得到窗口尺寸
		g.fillRect(0,0,dim.width,dim.height);  //填充背景色为白色
		g.setColor(Color.orange);  //设置当前颜色为橙色
		g.drawString(timeInfo,20,80);  //显示时间字符串
	}

	public static void main(String[] args){
		new ClockDemo();
	}
}