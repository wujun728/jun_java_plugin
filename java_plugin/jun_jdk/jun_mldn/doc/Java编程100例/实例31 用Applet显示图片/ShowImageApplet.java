import java.awt.*; 
import java.applet.*; 
import java.awt.image.ImageObserver; 
import java.net.URL; 

//用Applet显示图片

public class ShowImageApplet extends Applet implements Runnable{ 
	Image[] images;  //Applet的Image对象
	int xpoint=10;  //显示图片的X坐标
	int ypoint=10;  //显示图片的Y坐标
	Thread thread;  //图片切换的线程
	int currentImage;  //当前显示的图片号

	public void init(){
		setBackground(Color.white); //设置背景色 
		setForeground(Color.blue); //设置前景色 
		currentImage=0;	 //初始化参数
		xpoint=10;	
		ypoint=10;
		
		images=new Image[5];
		MediaTracker tracker = new MediaTracker(this);  //实例化媒体装载器
		for (int i=0;i<images.length;i++){
			URL imgURL=getDocumentBase(); //路径与html所在文件夹相同
			images[i]=getImage(imgURL,"image"+i+".jpg");  //得到图像
			tracker.addImage(images[i],i); //增加待装图像
		}
		
		try {
      		tracker.waitForID(0); //加载图像
    	}
    	catch(InterruptedException e) {}	
		
	} 
	
	public void start(){
      if(thread == null){
         thread =  new Thread(this);  //实例化线程
         thread.start(); //运行线程
      }
   }
   
   public void run(){
      while(true){         
         try{
            Thread.sleep(1000); //线程休眠1000毫秒
         }catch (InterruptedException e) {}
         repaint(); //重绘屏幕
      }      
   }
   
	public void paint(Graphics g){		
		g.drawImage(images[currentImage],xpoint,ypoint,this); //显示图像
		currentImage=(currentImage+1)%5;  //更改当前显示图片号
	}
}

