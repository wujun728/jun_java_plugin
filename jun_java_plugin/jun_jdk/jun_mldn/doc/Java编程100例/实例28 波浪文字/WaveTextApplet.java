import java.awt.*;
import java.applet.*;

//波浪文字

public class WaveTextApplet extends Applet implements Runnable {

  String message;  //显示文本
  int direct,phase; //运动方向参数
  Thread thread; //波浪运动线程
  char words[]; //显示文本的字符数组
  Image image;  //Image对象
  Graphics graphics; //Graphics对象
  Color colors[]; //显示文本颜色
  private Font font; //显示字体
  private FontMetrics fontMetric; //显示字体的 FontMetrics对象

   public void init() {   
     direct=1;  //初始方向值
     phase = 0; 
     message = getParameter("Text"); //得以显示文本
     if (message==null){ //如果文本为空
     	message="波浪文字"; //设置默认文本
     }
     setBackground(Color.black); //设置背景色
     
     words =  new char [message.length()]; //初始化显示字符数组
     message.getChars(0,message.length(),words,0); 
     image = createImage(getSize().width,getSize().height); //得到Image实例
     graphics = image.getGraphics(); //得到Graphics实例
 
     font = new Font("TimesRoman",Font.BOLD,36); //设置显示字体
     fontMetric=getFontMetrics(font);  //得到字体的FontMetric对象
     graphics.setFont(font);  //设置显示字体
     
     float h;
     colors = new Color[message.length()]; //初始化颜色数组
     for (int i = 0; i < message.length(); i++) {
        h = ((float)i)/((float)message.length());
        colors[i] = new Color(Color.HSBtoRGB(h,1.0f,1.0f)); //填充颜色数组数据
     }

   }



   public void start() {
     if(thread == null) {
       thread = new Thread(this); //实例化线程
       thread.start(); //运行线程
     }
   }


  public void run() {
      while (thread != null) {
         try {
            Thread.sleep(200); //线程休眠
         }catch (InterruptedException e) {
         }
	 		repaint(); //重绘屏幕
      }
   }


   public void update(Graphics g) {
      int x, y; //显示字符的X坐标,Y坐标
      double ang; 
      int Hrad = 12;
  		int Vrad = 12;

      graphics.setColor(Color.black); //设置当前颜色
      graphics.fillRect(0,0,getSize().width,getSize().height); //填充背景
      phase+=direct; 
      phase%=8;
      for(int i=0;i<message.length();i++) {
         ang = ((phase-i*direct)%8)/4.0*Math.PI; //运动角度
         x = 20+fontMetric.getMaxAdvance()*i+(int)(Math.cos(ang)*Hrad); //字符的X坐标
         y = 60+  (int) (Math.sin(ang)*Vrad); //字符的Y坐标
         graphics.setColor(colors[(phase+i)%message.length()]); //设置文本颜色
         graphics.drawChars(words,i,1,x,y); //显示字符
      }
      g.drawImage(image,0,0,this); //绘制Image
   }

  public void paint(Graphics g) {
   update(g);
  }
}