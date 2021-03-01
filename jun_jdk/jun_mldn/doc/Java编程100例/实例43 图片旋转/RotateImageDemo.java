import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import javax.swing.event.*;

//图像旋转演示

public class RotateImageDemo extends JFrame{
	JSlider jSlider = new JSlider(JSlider.HORIZONTAL,0,180,0);  //设定旋转角度
	ImagePane imagePane=new ImagePane(); //绘制图像的面板
	
	public RotateImageDemo(){
		super("图像旋转演示"); //调用父类构造函数
		jSlider.setPaintTicks(true);  //绘制标志位	
		jSlider.setMajorTickSpacing(45); //设置标志尺寸
		jSlider.setMinorTickSpacing(5);
		jSlider.setPaintLabels(true); //绘制出数字
		jSlider.setBorder(new javax.swing.border.TitledBorder(BorderFactory.createEmptyBorder(), "图像的旋转度")); //设置边框
		jSlider.addChangeListener(new ChangeListener() { //滑动条jSlider事件处理
			public void stateChanged(ChangeEvent ce) {
				int value=((JSlider) ce.getSource()).getValue(); //获取设置值
				double angle =(float)value/180*Math.PI ; //得到旋转角度（弧度制）
				imagePane.ratoteImage((float)angle); //旋转图像
			}
		});		
		
		Container container=getContentPane(); //得到窗口容器
		imagePane.loadImage("image0.jpg");	//装载图片
		container.add(imagePane,BorderLayout.CENTER); //增加组件到容器上
		container.add(jSlider,BorderLayout.SOUTH);
		
		setSize(250,250); //设置窗口尺寸
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 //关闭窗口时退出程序		
	}
	
	public static void main(String[] args){
		new RotateImageDemo();
	}
	
	class ImagePane extends JPanel {
		Image image;
		BufferedImage bufImage; //用于显示的缓冲区图像
		BufferedImage originalBufImage; //原始缓冲区图像
		Graphics2D bufImageG; //缓冲区图像的图形环境	
	
		public void loadImage(String fileName) {
			image = this.getToolkit().getImage(fileName); //取得图像
			MediaTracker mt = new MediaTracker(this); //实例化媒体加载器
			mt.addImage(image, 0); //增加图像到加载器中
			try {
				mt.waitForAll(); //等待图片加载
			} catch (Exception ex) {
				ex.printStackTrace();  //输出出错信息
			}
			originalBufImage =	new BufferedImage(image.getWidth(this),image.getHeight(this),BufferedImage.TYPE_INT_ARGB); 	//创建原始缓冲区图像
			bufImage = originalBufImage;
			bufImageG = bufImage.createGraphics(); //创建bufImage的图形环境
			bufImageG.drawImage(image, 0, 0, this); //传输源图像数据到缓冲区图像中
			repaint(); //重绘组件
		}
	    //过滤图像
	    public void ratoteImage(double angle) {
			if (bufImage == null)
				return; //如果bufImage为空则直接返回
			BufferedImage filteredBufImage =new BufferedImage(image.getWidth(this) ,image.getHeight(this),BufferedImage.TYPE_INT_ARGB); //过滤后的图像
			AffineTransform transform = new AffineTransform(); //仿射变换对象
			transform.rotate(angle,125,75); //旋转图像
			AffineTransformOp imageOp = new AffineTransformOp(transform, null);//创建仿射变换操作对象			
			imageOp.filter(originalBufImage, filteredBufImage);//过滤图像，目标图像在filteredBufImage
			bufImage = filteredBufImage; //让用于显示的缓冲区图像指向过滤后的图像
			repaint(); //重绘组件
		}
		
	    //重载容器的paintComponent()方法
		public void paint(Graphics g) {
			super.paintComponent(g);
			if (bufImage != null) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(bufImage,0,0,this);	//绘制图片
			}
		}
	}
}
