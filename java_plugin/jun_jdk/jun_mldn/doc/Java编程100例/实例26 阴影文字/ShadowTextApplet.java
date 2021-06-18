import java.awt.*;
import java.applet.*;
import java.util.Random;

//跳动文字

public class ShadowTextApplet extends Applet implements Runnable{	
   String message;  //待显示的文本信息
   Thread thread;  //实现文字运动的线程
   int fontHeight,speed,baseline; //字体高度,运动速度和基线
   Color textColor,bgColor,shadomColor; //文字颜色、背景颜色与阴影颜色
   Image newImage;  //实现跳动的Image对象
   Graphics newGraphics;  //实现跳动的Graphics对象
   boolean normal; //文字是否跳动的标志
   Font font; //显示字体
   FontMetrics fontMetric; //显示字体的FontMetrics对象

   public void init(){ //初始化
		Graphics graphics = getGraphics(); //得到graphics对象
	   Dimension dim=getSize(); //得到尺寸
	   fontHeight=dim.height-10; //根据Applet尺寸设置文字高度
	   newImage=createImage(dim.width,dim.height); //创建newImage对象
	   newGraphics = newImage.getGraphics(); //得到Graphics对象
	   message=getParameter("text"); //得到显示文字
	   if (message==null){        
	     	message="阴影文字";	//设置默认文字
	   }
	   
	   int textWidth=dim.width-(message.length() + 1)*5-10; //设置文字宽度
	   do{
	   	graphics.setFont(new Font("TimesRoman", 1, fontHeight)); //设置显示字体
	      fontMetric = graphics.getFontMetrics(); //得到FontMetric对象
	      if(fontMetric.stringWidth(message)>textWidth) //根据文字宽度调整其高度
	         fontHeight--;
	   }
	   while(fontMetric.stringWidth(message) > textWidth);{
	   	baseline = getSize().height - fontMetric.getMaxDescent(); //调整显示基线位置
	   }
	   font = new Font("TimesRoman", 1, fontHeight); //得到字体实例
	   
	   String param; //参数字符串
	   if((param = getParameter("TEXTCOLOR")) == null) //得到文本颜色
	   	textColor = Color.black; //设置默认文本颜色
	   else
	      textColor = new Color(Integer.parseInt(param));  //设置文本颜色
	   if((param = getParameter("BGCOLOR")) == null)  //得到背景颜色
	       bgColor = Color.white;  //设置默认背景颜色
	   else
	       bgColor = new Color(Integer.parseInt(param)); 
	   if((param = getParameter("SHADOMCOLOR")) == null)  //得到阴影颜色
	       shadomColor = Color.lightGray;  //设置默认阴影颜色
	   else
	       shadomColor = new Color(Integer.parseInt(param)); 
	   if((param = getParameter("NORMAL")) != null) //是否是静态文本
	       normal = (Integer.valueOf(param).intValue()!=0); //参数值不为零,则为静态文本
	   setBackground(bgColor); //设置背景颜色
	   if((param = getParameter("SPEED")) != null) //得到运动速度
	       speed = Integer.valueOf(param).intValue();
	   if(speed == 0)
	       speed = 200;  //设置默认运动速度	  
     	thread = new Thread(this); //实例化运动文字线程
    }

    public void start(){ //开始运行线程
        if(thread == null) {
        		thread = new Thread(this); //实例化线程
        }
        thread.start(); //线程运行
    }

    public void run(){  //线程运行主体
        while(thread!=null) { 
            try{
                Thread.sleep(speed); //线程休眠,即跳动间隔时间
            }
            catch(InterruptedException ex) {}
            repaint();  //重绘屏幕
        }
        System.exit(0);  //退出程序
    }


    public void paint(Graphics g) {  //绘制Applet
        if(normal) {  //如果是静态文本
            g.setColor(bgColor);  //设置当前颜色
            g.fillRect(0, 0, getSize().width, getSize().height);  //绘制填充矩形
            g.setColor(textColor); //设置当前颜色
            g.setFont(font); //设置当前字体
            g.drawString(message, (getSize().width - fontMetric.stringWidth(message)) / 2, baseline); //绘出字符串
        }
    }

    public void update(Graphics g){  //更新Applet
        newGraphics.setColor(bgColor); //设置当前颜色
        newGraphics.fillRect(0, 0, getSize().width, getSize().height); //绘制填充矩形
        newGraphics.setColor(textColor); //设置当前颜色
        newGraphics.setFont(font); //设置字体
        if(!normal){ //如果是跳动文字
        		java.util.Random r=new java.util.Random();	
            int xpoint = r.nextInt(fontMetric.stringWidth(message));  //生成随机X坐标
            
            font = new Font("TimesRoman",Font.BOLD,30); //设置字体
			newGraphics.setFont(font);  //设置当前字体
			    
		    newGraphics.setColor(shadomColor); //设置当前颜色
		    newGraphics.drawString(message,xpoint+3,baseline +3); //绘制阴影
		    
		    newGraphics.setColor(textColor); //设置文本颜色
		    newGraphics.drawString(message,xpoint,baseline); //绘字符串
			    
        } 
        else {  //如果是静态文本
            font = new Font("TimesRoman",Font.BOLD,30); //设置字体
			newGraphics.setFont(font);  //设置当前字体
			    
		    newGraphics.setColor(shadomColor); //设置当前颜色
		    newGraphics.drawString(message,xpoint+3,baseline +3); //绘制阴影
		    
		    newGraphics.setColor(textColor); //设置文本颜色
		    newGraphics.drawString(message,xpoint,baseline); //绘字符串
     	  }
        g.drawImage(newImage, 0, 0, this); //绘制Image
    }
}
