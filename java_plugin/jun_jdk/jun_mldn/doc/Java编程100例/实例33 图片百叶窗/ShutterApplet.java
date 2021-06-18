import java.awt.*;
import java.applet.*;
import java.awt.image.*;

public class ShutterApplet extends Applet implements Runnable{
	Image images[],showImage; //待显示的图像数组及当前显示的图像
	MediaTracker imageTracker; //媒体装载器
	int imageWidth,imageHeight,totalImage = 5,currentImage,nextImage; //图像宽度,高度,总的图像数量,当前图像编号及下一个图像编号
	Thread thread; //图片切换效果的线程
	int delay; //切换延迟
	int totalPix,pix1[],pix2[],pix3[],pix4[],pix5[],pixA[],pixB[]; //图像点阵数及像素点数组
	
	public void init(){
		setBackground(Color.black);	//设置Applet的背景颜色
		images = new Image[totalImage]; //初始化数组
		imageTracker = new MediaTracker(this); //实例化媒体装载器
		String param = new String(""); //参数字符串
		for(int i=0; i<totalImage; i++)	{ //得到所有图片
			param = getParameter("image" + i); //得到参数
			images[i] = getImage(getCodeBase(),param); //得到图像
			imageTracker.addImage(images[i],0); //加入图像到媒体装载器
		}
		try {
			imageTracker.waitForID(0); //装载图像
		}
		catch(InterruptedException e){}		
		
		param=getParameter("delay"); //得到延迟参数
		if(param!= null){
			delay = Integer.parseInt(param);			
		}
		else{
			delay = 3000; //设置默认延迟参数
		}
		
		imageWidth = images[0].getWidth(this); //得到图像宽度
		imageHeight = images[0].getHeight(this); //得到图像高度
		totalPix = imageWidth*imageHeight; //图像的总的像素点数量
		
		pix1 = new int[totalPix];
		PixelGrabber pg1 = new PixelGrabber(images[0],0,0,imageWidth,imageHeight,pix1,0,imageWidth); //获取图像像素数据
		try{
			pg1.grabPixels(); //抓取像素
		}
		catch(InterruptedException ex){}
		pix2 = new int[totalPix];
		PixelGrabber pg2 = new PixelGrabber(images[1],0,0,imageWidth,imageHeight,pix2,0,imageWidth);
		try{
			pg2.grabPixels();
		}
		catch(InterruptedException ex){}
		
		pix3 = new int[totalPix];
		PixelGrabber pg3 = new PixelGrabber(images[2],0,0,imageWidth,imageHeight,pix3,0,imageWidth);
		try	{
			pg3.grabPixels();
		}
		catch(InterruptedException ex){}
		
		pix4 = new int[totalPix];
		PixelGrabber pg4 = new PixelGrabber(images[3],0,0,imageWidth,imageHeight,pix4,0,imageWidth);
		try	{
			pg4.grabPixels();
		}
		catch(InterruptedException ex){}
		
		pix5 = new int[totalPix];
		PixelGrabber pg5 = new PixelGrabber(images[4],0,0,imageWidth,imageHeight,pix5,0,imageWidth);
		try	{
			pg5.grabPixels();
		}
		catch(InterruptedException ex){}
		
		currentImage = 0;
		pixA = new int[totalPix];
		pixB = new int[totalPix];
		showImage = images[0];	
	}
	
	public void start(){
    if(thread == null){
      thread = new Thread(this);  //实例化线程
      thread.start();  //运行线程
    }
  }
  
	
	public void paint(Graphics g){
		g.drawImage(showImage,0,0,this); //绘制当前图像
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void run(){
		while(true)
		{
			try
			{
				thread.sleep(delay); //线程休眠
				
				nextImage = ((currentImage+1)%totalImage); //更改下一张图像编号
				if (currentImage ==0){
					System.arraycopy(pix1,0,pixA,0,totalPix); //数组拷贝
					System.arraycopy(pix2,0,pixB,0,totalPix);
					showImage = createImage(new MemoryImageSource(imageWidth,imageHeight,pixA,0,imageWidth)); //转换像素数组到图像
					repaint(); //重绘屏幕
				}
				else if (currentImage ==1){
					System.arraycopy(pix2,0,pixA,0,totalPix);
					System.arraycopy(pix3,0,pixB,0,totalPix);
					showImage = createImage(new MemoryImageSource(imageWidth,imageHeight,pixA,0,imageWidth));
					repaint();
				}
				else if (currentImage ==2){
					System.arraycopy(pix3,0,pixA,0,totalPix);
					System.arraycopy(pix4,0,pixB,0,totalPix);
					showImage = createImage(new MemoryImageSource(imageWidth,imageHeight,pixA,0,imageWidth));
					repaint();
				}
				else if (currentImage ==3){
					System.arraycopy(pix4,0,pixA,0,totalPix);
					System.arraycopy(pix5,0,pixB,0,totalPix);
					showImage = createImage(new MemoryImageSource(imageWidth,imageHeight,pixA,0,imageWidth));
					repaint();
				}
				else if (currentImage ==4){
					System.arraycopy(pix5,0,pixA,0,totalPix);
					System.arraycopy(pix1,0,pixB,0,totalPix);
					showImage = createImage(new MemoryImageSource(imageWidth,imageHeight,pixA,0,imageWidth));
					repaint();
				}
				while(true)
				{
					for(int i=0; i<(int)(imageHeight/10);i++){
						try{
							thread.sleep(50); //线程休眠
							for(int j=0; j<imageHeight; j+=(int)(imageHeight/10)){
								for(int k=0; k<imageWidth; k++)	{
									pixA[imageWidth*(j+i) + k] = pixB[imageWidth*(j+i) + k]; 
								}
							}
						}
						catch(InterruptedException e){}
						showImage = createImage(new MemoryImageSource(imageWidth,imageHeight,pixA,0,imageWidth)); //更新当前显示Image
						repaint();						
					}
					break;
				}				
				currentImage = nextImage;
				repaint();
			}
			catch(InterruptedException e){}
		}
	}
}