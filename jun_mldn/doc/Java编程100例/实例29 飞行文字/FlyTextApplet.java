import java.awt.*;
import java.applet.*;

public class FlyTextApplet extends Applet implements Runnable{
   
   Image image; //该Applet的Image对象
   Graphics graphics; //该Applet的Graphics对象
   Font font; //显示字体
   String message; //显示文本
   Thread thread; //飞行运动线程
   int xpos, ypos, fontsize; //X坐标,Y坐标及字体大小
   
   public void init(){   
      image = createImage(getSize().width, getSize().height);   //得到Image实例
      graphics = image.getGraphics();  //得到Graphics实例
      message = getParameter("Text"); //得到文本参数
      if(message == null){ //如果显示文本为空
         message = "飞行文字"; //设置默认文本
       }
      font = new Font("TimesRoman", Font.BOLD, 10);  //实例化字体
   }
   
   public void start(){
      if(thread == null){
         thread =  new Thread(this);  //实例化线程
         thread.start(); //运行线程
      }
   }
   
   public void run(){
      while(thread != null){         
         if(fontsize >getSize().height) //如果字体尺寸过大
            fontsize = 0; //重设字体尺寸
         try{
            Thread.sleep(50); //线程休眠
         }catch (InterruptedException e) {}
         repaint(); //重绘屏幕
      }      
   }
   
   public void stop(){
   		thread=null;
   }
   
   public void update(Graphics g){
   	graphics.setColor(Color.black); //设置当前颜色
      graphics.fillRect(0,0,getSize().width, getSize().height); //填充背景
      font = new Font("TimesRoman", Font.BOLD, fontsize); //得到字体实例
      graphics.setFont(font); //设置字体
      graphics.setColor(Color.pink); //设置当前颜色
      FontMetrics fontMetrics = graphics.getFontMetrics(font); //得到字体的FontMetrics对象
      int fontheight = fontMetrics.getHeight(); //得到字体高度
      int width; //字体宽度
      int baseline = getSize().height / 2 + fontheight / 2;  //显示文本基线
      
      width = fontMetrics.stringWidth(message);  //字符串宽度   
      width = (getSize().width - width) / 2;  //显示字符串宽度
      
      graphics.drawString(message, width, baseline-=20);   //绘制字符串
      g.drawImage(image,0,0, this); //绘制Image对象
      fontsize++;  //增加字体尺寸      
   }
   
   public void paint(Graphics g){
      update(g);
   }  

}