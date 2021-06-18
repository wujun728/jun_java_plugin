import java.awt.*;
import java.applet.*;
import java.io.*;
import java.awt.image.*;

//图片翻折

public class TurnApplet extends Applet implements Runnable{
  Image images[],showImage;  //图像数组及当前显示图像
  MediaTracker imageTracker;  //加载图像的MediaTracker对象
  int imageWidth,imageHeight,currentImage,totalImage; //图像宽度,高度,当前图像编号,图像总的个数
  Thread thread; //图片翻折线程
  int delay;  //翻折延迟
  Image image;  //绘制图像的Image对象
  Graphics graphics;  //绘制图像的Grahpics对象
  
  public void init(){
  	totalImage = 5; //初始化参数
  	currentImage = 0;
    setBackground(Color.white);  //设置背景颜色   
    images = new Image[totalImage]; 
    imageTracker = new MediaTracker(this);   //得到MediaTracker实例
    String param ;
    for(int i=0; i<totalImage; i++) {
      param = getParameter("image" + (i+1)); //得到参数
      images[i] = getImage(getCodeBase(),param); //得到图像
      imageTracker.addImage(images[i],0); //增加图片到加载器中
    }
    try {
      imageTracker.waitForID(0); //加载图像
    }
    catch(InterruptedException e) {}
    
    param=getParameter("delay");  //得到延迟参数
    if(param== null){
      delay = 3000; //设置默认参数
    }
    else {
      delay = Integer.parseInt(param);
    }    
    imageWidth = images[0].getWidth(this);  //得到图像宽度
    imageHeight = images[0].getHeight(this);    //得到图像高度 
    image = createImage(imageWidth,imageHeight); //创建Image实例
    graphics = image.getGraphics();  //得到Graphics实例
  }
  
  public void start() {
    if(thread == null) {
      thread = new Thread(this);  //实例化线程
      thread.start(); //运行线程
    }
  }
  public void paint(Graphics g) {
    g.drawImage(image,0,0,this); //绘制图像
  }
  
  public void update(Graphics g) {
    paint(g);
  }
    
  public void run() {
    while(thread != null) {
      try{     
        for(int i=0; i<=(imageHeight/2); i++) {  //实现图片的翻折效果
          thread.sleep(30);  //线程休眠,实现图像的逐渐翻转
          graphics.setColor(Color.white); //设置当前颜色
          graphics.fillRect(0,0,imageWidth,imageHeight); //绘制填充矩形
          graphics.drawImage(images[currentImage],0,i,imageWidth,imageHeight-2*i,this); //以不同高度绘制图片
          repaint(); //重绘屏幕
        }
        
        currentImage = ((currentImage+1)%totalImage); //更改当前图像编号值
        
        for(int i=0; i<=(imageHeight/2); i++){ //实现图片的反向翻折效果
          thread.sleep(30);
          graphics.setColor(Color.white);
          graphics.fillRect(0,0,imageWidth,imageHeight);
          graphics.drawImage(images[currentImage],0,(imageHeight/2)-i,imageWidth,2*i,this);
          repaint();
        }
         thread.sleep(delay);  //线程休眠         
      }
      catch(InterruptedException e){}
    }
  }
}