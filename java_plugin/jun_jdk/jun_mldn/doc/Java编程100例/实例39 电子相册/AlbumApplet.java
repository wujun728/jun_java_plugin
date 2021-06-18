import java.awt.*;
import java.applet.*;
import java.io.*;

public class AlbumApplet extends Applet{
	Choice choicePhoto; //图片选择下拉框
	int totalImages; //图像总数量
	Image images[],showImage; //图像数组及当前显示图像
	Graphics graphics; //绘制图像的Graphics对象
	MediaTracker imagetracker; //媒体加载器
  
	public void init(){
		setBackground(Color.black); //设置Applet的背景颜色
		setLayout(null); //设置布局管理器
		choicePhoto = new Choice(); //实例化下拉框
		choicePhoto.setBounds(5,10,200,20); //设置下拉框边界与位置
		String param;
		param = getParameter("Amount"); //获取图像数量参数
		totalImages=Integer.parseInt(param); //得到图像数量
		images = new Image[totalImages]; 

		imagetracker = new MediaTracker(this); //实例化媒体加载器
		for(int i=0; i<totalImages; i++){
			param = getParameter("Name"+i);  //获取参数
			choicePhoto.addItem(param); //增加下拉框选项
			param = getParameter("Picture"+i);  //获取参数
			images[i] = getImage(getDocumentBase(),param); //得到图像
			imagetracker.addImage(images[i],i); //增加待加载的图像
		}
		try{ 
			imagetracker.waitForID(0); //等待第一张图片的加载完成
		}
		catch(InterruptedException e){}
		
		add(choicePhoto); //增加组件到Applet
		Dimension dim=getSize(); //得到Applet尺寸
		showImage = createImage(dim.width,dim.height-40); //创建Image实例
		graphics = showImage.getGraphics(); //得到Graphics实例
	}
  
	public void paint(Graphics g)	{
		g.drawImage(showImage,5,40,this); //绘制图像
	}

	public boolean action(Event e , Object o){
		if(e.target == choicePhoto)	{ //判断事件源
			int selected=choicePhoto.getSelectedIndex(); //得到选择图像编号
			graphics.drawImage(images[selected],0,0,this); //绘制图像
			repaint(); //重绘屏幕
		}
		return true;
	}
}