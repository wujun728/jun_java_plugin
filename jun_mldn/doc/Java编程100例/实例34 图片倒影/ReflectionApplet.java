import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

//图片倒影

public class ReflectionApplet extends Applet implements Runnable {

	Thread lakeThread; //图片倒影线程
	Graphics graphics; //该Applet的Graphics对象
	Graphics waveGraphics; //倒影的Graphics对象
	Image image; //Applet的Image对象
	Image waveImage; //倒影的Graphics对象
	int currentImage,imageWidth,imageHeight; 
	boolean imageLoaded; //图片是否装载
	String imageName; //图片名称
	
	public void init() {
		imageName= getParameter("image"); //得到图片名称
	}

	public void start() {
		if (lakeThread == null) {
			lakeThread= new Thread(this); //实例化线程
			lakeThread.start(); //运行线程
		}
	}

	public void run() {
		currentImage= 0;
		if (!imageLoaded) { //如果图片未装载
			repaint(); //得绘屏幕
			graphics= getGraphics(); //得到Graphics对象
			MediaTracker mediatracker= new MediaTracker(this); //实例化MediaTracker对象
			image= getImage(getDocumentBase(), imageName); //得到Image实例
			mediatracker.addImage(image, 0); //增加待加载的图片
			try {
				mediatracker.waitForAll();  //装载图片
				imageLoaded= !mediatracker.isErrorAny();  //是否有错误发生
			} catch (InterruptedException ex) {
			}
			if (!imageLoaded) {  //加载图片失败
				stop(); //Applet停止运行
				graphics.drawString("加载图片错误", 10, 40); //输出错误信息
				return;
			}
			imageWidth= image.getWidth(this); //得到图像宽度
			imageHeight= image.getHeight(this);  //得到图像高度
			createAnimation(); //创建动画效果
		}
		repaint();  //重绘屏幕
		try {
			while (true) {
				repaint(); //得绘屏幕
				currentImage++;
				if (currentImage == 12) 
					currentImage= 0;
				Thread.sleep(50); //线程休眠
			}
		} catch (InterruptedException ex) {
				stop();
		}
	}
	
	public void createAnimation() {
		Image img= createImage(imageWidth, imageHeight);  //以图像高度创建Image实例
		Graphics g= img.getGraphics(); //得到Image对象的Graphics对象
		g.drawImage(image, 0, 0, this);  //绘制Image
		for (int i= 0; i < imageHeight ; i++) {
			g.copyArea(0,imageHeight-1-i,imageWidth,1,0,-imageHeight+1+(i*2)); //拷贝图像区域
		}

		waveImage= createImage(13 * imageWidth, imageHeight); //得到波浪效果的Image实例
		waveGraphics= waveImage.getGraphics(); //得到波浪效果的Graphics实例
		waveGraphics.drawImage(img, 12 * imageWidth, 0, this); //绘制图像
		int j= 0;
		while (j<12){
			makeWaves(waveGraphics, j);
			j++;
		}
		g.drawImage(image, 0, 0, this);  //绘制图像
	}

	public void makeWaves(Graphics g, int i) { //波浪效果模拟
		double d= (6.2831853071795862 * i) / 12;
		int j= (12 - i) * imageWidth;
		int waveHeight=imageHeight / 16;
		for (int l= 0; l < imageHeight; l++) {			
			int k=(int) ((waveHeight* (l + 28)* Math.sin(waveHeight * (imageHeight - l)/ (l + 1)+ d))/imageHeight);
			if (l < -k)
				g.copyArea(12 * imageWidth, l, imageWidth, 1, -j, 0); //拷贝图像区域,形成波浪
			else
				g.copyArea(12 * imageWidth, l + k, imageWidth, 1, -j, -k);
		}
	}

	public void paint(Graphics g) {
		update(g);
	}
	
	public void update(Graphics g){
		if (waveImage != null) {
			g.drawImage(waveImage,-currentImage * imageWidth,imageHeight-1,	this);  //绘制图像
			g.drawImage(waveImage,(12 - currentImage) * imageWidth,	imageHeight-1,this);
		}
		g.drawImage(image, 0, 1, this);
	}
}
