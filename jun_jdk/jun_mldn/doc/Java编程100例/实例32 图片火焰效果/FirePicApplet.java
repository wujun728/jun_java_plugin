import java.applet.*;
import java.awt.*;
import java.net.*;

//图片火焰效果

public class FirePicApplet extends Applet implements Runnable{
  private Image backImage,foreImage; //前景和背景Image对象
  private Image image,fireImage; //Applet和绘制火焰的效果的Image对象
  private Graphics graphics,fireGraphics; //Applet和绘制火焰的效果的Graphics对象
  private Thread thread; //火焰效果的线程
  private MediaTracker imageTracker;  //装载图片
  private int height,width; //Applet的高度,宽度
  
  public void init() {
  	Dimension dim=getSize(); //得到Applet的尺寸   
    width = dim.width; //得到宽度值
    height = dim.height; //得到高度值
    backImage = getImage(getDocumentBase(),"back.jpg");  //得到图片
    foreImage = getImage(getDocumentBase(),"image1.gif");  
    imageTracker = new MediaTracker(this); //实例化MediaTracker对象
    imageTracker.addImage(backImage,0); //增加图片到图片装载器
    imageTracker.addImage(foreImage,0);
    
    try{
      imageTracker.waitForID(0); //装载图片
    }
    catch(InterruptedException e){}
    
    image = createImage(width,height); //得到Image对象实例
    graphics = image.getGraphics();  //得到Graphics对象实例
    
    fireImage=createImage(width*2,height*2);
    fireGraphics=fireImage.getGraphics();       
  }
  
  public void start(){
    if(thread == null){
      thread = new Thread(this);  //实例化线程
      thread.start();  //运行线程
    }
  }
  
  
  
  public void run(){
    int x= 0, y=0;  //绘制火焰效果的X坐标,Y坐标  
    int tileWidth = backImage.getWidth(this);  //背景图片的宽度
    int tileHeight= backImage.getHeight(this); //背景图片的高度
    while(thread != null) {
		x=fireImage.getWidth(this)-width; //得到X坐标,Y坐标值
		y=fireImage.getHeight(this)-height;
	    for(;(x>0)&&(y>0); x--,y--)	{
	      if((x==0)||(y==0)) { //坐标回到零时,重新设置
	        x=fireImage.getWidth(this)-width;
	        y=fireImage.getHeight(this)-height;
	      }
	      
	      for(int j=0; j < fireImage.getHeight(this); j = j + tileHeight){
	        for(int i=0; i < fireImage.getWidth(this); i = i + tileWidth){
	          fireGraphics.drawImage(backImage, i, j, this); //绘制背景图片
	        }       
	      }   
	      
	      fireGraphics.drawImage(foreImage, x, y,width,height,this); //绘制前景图片
	      graphics.drawImage(fireImage,-x,-y,this);  //绘制火焰效果Image
	      repaint(); //重绘屏幕
	    }      
	 }
  }
  
  public void update(Graphics g){
    paint(g);
  }
  
  public void paint(Graphics g){
    g.drawImage(image,0,0,this); //绘制Image
  }
}