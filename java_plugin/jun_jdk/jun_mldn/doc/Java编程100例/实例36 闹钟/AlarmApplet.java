import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

//闹钟

public class AlarmApplet extends Applet implements Runnable{
	
	TextField tfHour,tfMinute,tfSecond,tfNowHour,tfNowMinute,tfNowSecond; //显示和输入信息的文本域
	Button btStart,btStop; //打开和关闭闹钟按钮
	Thread alarm; //闹钟线程
	boolean turnOn; //是否打开
	
	public void init(){
		turnOn=true; //初始化参数
		
		Panel panel2=new Panel(); //实例化面板
		Panel panel3=new Panel();
		Panel panel4=new Panel();
		tfHour=new TextField(1); //增加组件到面板上
		tfMinute=new TextField(1);
		tfSecond=new TextField(1);
		tfNowHour=new TextField(1);
		tfNowMinute=new TextField(1);
		tfNowSecond=new TextField(1);
		btStart=new Button("开");
		btStop=new Button("关");	
		
		panel2.add(new Label("当前时间："));
		panel2.add(tfNowHour);
		panel2.add(new Label("时"));
		panel2.add(tfNowMinute);
		panel2.add(new Label("分"));
		panel2.add(tfNowSecond);
		panel2.add(new Label("秒"));
		panel3.add(new Label("闹钟时间："));				
		panel3.add(tfHour);
		panel3.add(new Label("时"));
		panel3.add(tfMinute);
		panel3.add(new Label("分"));
		panel3.add(tfSecond);
		panel3.add(new Label("秒"));

		panel4.add(new Label("闹钟设置"));
		panel4.add(btStart);
		panel4.add(btStop);
		
		add(panel2); //增加组件到Applet上
		add(panel3);
		add(panel4);
		 
		btStart.addActionListener(new ActionListener(){  //打开闹钟按钮事件处理
			public void actionPerformed(ActionEvent event){
				turnOn=true; //设置打开标志为True
			}
		});
		
		btStop.addActionListener(new ActionListener(){  //关闭闹钟按钮事件处理
			public void actionPerformed(ActionEvent event){
				turnOn=false; //设置打开标志为false
			}
		});	
	}	
	
	public void start(){
		if (alarm==null){
			alarm=new Thread(this);  //实例化线程
			alarm.start(); //运行线程
		}
	
	}

	public void run(){
		while (alarm!=null){
			try{
				alarm.sleep(1000); //线程休眠一秒
			}
			catch (InterruptedException ex){
			}
			runAlarm(); //运行闹钟
		}
		
	}
	
	private void runAlarm(){
		Calendar now=new GregorianCalendar();	//得到日历对象
		int hour=now.get(Calendar.HOUR_OF_DAY); //得到小时数
		int minute=now.get(Calendar.MINUTE);   //得到分数
		int second=now.get(Calendar.SECOND);   //得到分数
		
		tfNowHour.setText(Integer.toString(hour)); //更新时间显示文本域
		tfNowMinute.setText(Integer.toString(minute));
		tfNowSecond.setText(Integer.toString(second));
		if (turnOn){ //如果闹钟是打开的
			int alarmHour=-1,alarmMinute=-1; 
			String time=tfHour.getText(); //得到设置的闹钟小时数
			if (time!=null && !time.equals(""))
				alarmHour=Integer.parseInt(time);
			time=tfMinute.getText();
			if (time!=null && !time.equals(""))  //得到设置的闹钟分钟数
				alarmMinute=Integer.parseInt(time);		
			
			if (alarmHour!=-1 && alarmMinute!=-1){			
				if (hour==alarmHour){  //比较时间
					if (minute==alarmMinute){
        				AudioClip sound=getAudioClip(getDocumentBase(),"alarm.wav");  //取得声音文件
        				sound.play(); //播放声音
        				turnOn=false; 
					}
				}
			}
		}
	}
}