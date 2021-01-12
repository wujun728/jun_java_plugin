import java.awt.*;
import java.applet.*;
import java.util.Random;

//跳动文字

public class JumpTextApplet extends Applet implements Runnable{	
   String message;  //待显示的文本信息
   Thread jumpThread;  //实现跳动文字的线程
   int fontHeight,speed,baseline; //字体高度,跳动速度和基线
   Color textColor,bgColor; //文字颜色与背景颜色
   Image jumpImage;  //实现跳动的Image对象
   Graphics jumpGraphics;  //实现跳动的Graphics对象
   boolean normal; //文字是否跳动的标志
   Font font; //显示字体
   FontMetrics fontMetric; //显示字体的FontMetrics对象
   Color randomColors[]; //随机生成颜色
   boolean randomColor;  //是否是随机颜色

   public void init(){ //初始化
		Graphics graphics = getGraphics(); //得到graphics对象
	   Dimension dim=getSize(); //得到尺寸
	   fontHeight=dim.height-10; //根据Applet尺寸设置文字高度
	   jumpImage=createImage(dim.width,dim.height); //创建Image对象
	   jumpGraphics = jumpImage.getGraphics(); //得到Graphics对象
	   message=getParameter("text"); //得到显示文字
	   if (message==null){        
	     	message="跳动的文字";	//设置默认文字
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
	       bgColor = Color.lightGray;  //设置默认背景颜色
	   else
	       bgColor = new Color(Integer.parseInt(param)); 
	   setBackground(bgColor); //设置背景颜色
	   if((param = getParameter("SPEED")) != null) //得到跳动速度
	       speed = Integer.valueOf(param).intValue();
	   if(speed == 0)
	       speed = 200;  //设置默认跳动速度
	   if((param = getParameter("RANDOMCOLOR")) != null) //得到是否是随机颜色参数
	       randomColor = (Integer.valueOf(param).intValue()!=0); //参数值不为零,则为随机颜色
	   if((param = getParameter("NORMAL")) != null) //得到是否是随机颜色参数
	       normal = (Integer.valueOf(param).intValue()!=0); //参数值不为零,则为随机颜色
		if (randomColor){ //初始化随机颜色数组 
		   Random random=new Random(); //实例化Random对象
		   int r,g,b; //颜色的RGB值
		   for (int i=0;i<10;i++){
			  	r=random.nextInt(255);  //得到0到255之间的随机值
			  	g=random.nextInt(255);
			  	b=random.nextInt(255);
		   	Color randomc=new Color(r,g,b);	//生成颜色实例
		   	randomColors[i]=randomc; //设置数组值
		   }	 
	   } 
     	jumpThread = new Thread(this); //实例化跳动文字线程
    }

    public void start(){ //开始运行线程
        if(jumpThread == null) {
        		jumpThread = new Thread(this);
        }
        jumpThread.start();
    }

    public void run(){  //线程运行主体
        while(jumpThread!=null) { 
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
        jumpGraphics.setColor(bgColor); //设置当前颜色
        jumpGraphics.fillRect(0, 0, getSize().width, getSize().height); //绘制填充矩形
        jumpGraphics.setColor(textColor); //设置当前颜色
        jumpGraphics.setFont(font); //设置字体
        if(!normal){ //如果是跳动文字
            int xpoint = 0;
            for(int j = 0; j < message.length(); j++){
                if(randomColor){
                    Color color;
                    while(bgColor == (color = randomColors[Math.min(9, (int)(Math.random()*10))])); //得到颜色数组中与背景色不同的一个随机颜色                    
                    jumpGraphics.setColor(color);  //设置当前颜色
                }
                xpoint += (int)(Math.random() * 10); //单个字符的X坐标
                int ypoint = baseline - (int)(Math.random() * 10); //单个字符的Y坐标
                String s1 = message.substring(j, j + 1);
                jumpGraphics.drawString(s1, xpoint, ypoint);
                xpoint += fontMetric.stringWidth(s1);
            }
        } 
        else {  //如果是静态文本
            jumpGraphics.drawString(message, (getSize().width - fontMetric.stringWidth(message)) / 2, baseline); //绘制字符串
        }
        g.drawImage(jumpImage, 0, 0, this); //绘制Image
    }

    public JumpTextApplet() {  //构造函数
        speed = 100;  //初始速度
        normal = false; //初始时为动态文本
        randomColors = new Color[10]; //初始化随机颜色数组
        randomColor = false; 
    }
}
