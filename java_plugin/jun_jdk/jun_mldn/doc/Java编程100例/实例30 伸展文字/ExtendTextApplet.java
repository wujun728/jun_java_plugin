import java.awt.*;
import java.applet.*;

//伸展文字

public class ExtendTextApplet extends Applet implements Runnable{
	Image image; //该Applet的Image对象
	Graphics graphics;  //该Applet的Graphics对象
	int appletWidth,appletHeight; //Applet的高度,宽度
	String message; //显示文本
	Thread thread; //动画线程
	int ypoint=0, xheight; //显示文本的Y坐标和高度
	int phase = 0; //状态参数
	Font font; //显示文本的字体
	
	public void init(){
		font=new Font("TimesRoman",Font.BOLD,30); //实例化字体
		appletWidth = getSize().width; //得到Applet的宽度
		appletHeight = getSize().height;  //得到Applet的高度
		xheight = appletHeight / 3; //显示文字高度值
		ypoint = xheight; //显示文字的Y坐标值
		message = getParameter("Text"); //得到显示信息
		if(message==null)
			message = "伸展文字"; //设置默认信息
		image = createImage(appletWidth,appletHeight);  //得到Image实例
		graphics = image.getGraphics();  //得到Graphics实例
	}  
  
	public void start(){
		if(thread == null){
			thread = new Thread(this);  //实例化线程
			thread.start();  //运行线程
		}
	}     
   
	public void update(Graphics g){
		paint(g); //绘制屏幕
	}
   
	public void paint(Graphics g){
		g.drawImage(image, 0, ypoint, appletWidth, xheight ,this);  //绘制Image对象
	}
   
	public void run()	{
		try{
			while(true){ 
				ypoint = 0; 
				xheight = appletHeight;
				graphics.setColor(Color.white); //设置当前颜色
				graphics.fillRect(0 ,0, appletWidth, appletHeight); //填充背景
				repaint(); //重绘屏幕
				thread.sleep(100); //线程休眠100毫秒
				if(phase==0){ 
					graphics.setColor(Color.orange); //设置当前颜色
					for(int i = appletWidth; i>=0; i--)	{ //线条的伸展效果
						graphics.fillRect(i, appletHeight / 3 ,appletWidth, appletHeight /10);  //填充矩形
						repaint(); //重绘屏幕
						thread.sleep(10); //线程休眠10毫秒
					}
				}
				else if(phase == 1){
					graphics.setColor(Color.pink); //设置当前颜色
					for(int i = 0; i<=appletWidth; i++)	{ 
						graphics.fillRect(0, appletHeight / 3 ,i, appletHeight /10); //填充矩形
						repaint(); //重绘屏幕
						thread.sleep(10); //线程休眠10毫秒
					}
				}
				ypoint = appletHeight/3; 
				xheight = appletHeight/3;
				for(int i = appletHeight / 3; i>=0; i--){
					ypoint--;
					xheight += 2;
					if(phase == 0){
						graphics.setColor(Color.orange); //设置当前颜色
						graphics.fillRect(0,0, appletWidth,appletHeight); //填充矩形
						graphics.setFont(font); //设置当前字体
						graphics.setColor(Color.white);  //设置当前颜色
						graphics.drawString(message,0,35);  //绘制字符串
						phase++; //改变状态参数
					}
					else if(phase == 1){
						graphics.setColor(Color.pink); //设置当前颜色
						graphics.fillRect(0,0, appletWidth,appletHeight); //填充矩形
						graphics.setFont(font); //设置当前字体
						graphics.setColor(Color.black);  //设置当前颜色
						graphics.drawString(message,0,35); //绘制字符串          
						phase=0; //改变状态参数
					}				
					repaint(); //重绘屏幕
					thread.sleep(100); //线程休眠100毫秒
				}
				thread.sleep(2000);  //线程休眠2500毫秒       
			}
		}
		catch(InterruptedException e){}
	}	
}