import java.awt.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.image.*;

// 半透明图片

public class HalfTransparentImageDemo extends JFrame {
	private Container content = getContentPane(); //获得窗口的容器
 	private JSlider jSlider = new JSlider(JSlider.HORIZONTAL,0,100,75); //改变图像的透明度	
	DisplayPanel displayPanel = new DisplayPanel(); //显示图形面板

	public HalfTransparentImageDemo() {
		super("半透明图片"); //调用父类构造器
		jSlider.setPaintTicks(true);  //绘制标志位	
		jSlider.setMajorTickSpacing(25); //设置标志尺寸
		jSlider.setMinorTickSpacing(5);
		jSlider.setPaintLabels(true); //绘制出数字
		jSlider.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "图像的透明度(%)")); //设置边框
		jSlider.addChangeListener(new ChangeListener() { //滑动条jSlider事件处理
			public void stateChanged(ChangeEvent ce) {
				float alpha =((float) ((JSlider) ce.getSource()).getValue()) / 100;
				displayPanel.alpha = alpha; //改变图像的透明度
				displayPanel.repaint(); //重绘displayPanel
			}
		});
		content.add(jSlider, BorderLayout.SOUTH); //增加组件到容器上
		content.add(displayPanel, BorderLayout.CENTER);
		setSize(300, 300); //设置窗口尺寸
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public static void main(String[] args) {
		new HalfTransparentImageDemo();
	}
	
	//显示图形的面板
	class DisplayPanel extends JPanel {
		String[] imageName = { "back.jpg", "girl.gif" }; //图像文件名数组
		BufferedImage bufImage1, bufImage2; //缓冲区图像
		float alpha = 0.75f; // 图像合成的alpha赋初值
		
		//根据Image对象创建一个缓冲区图像
		public BufferedImage loadBufImage(String fileName) {
			Image image = getToolkit().getImage(fileName); //获取Image对象
			MediaTracker mt = new MediaTracker(this); //实例化媒体加载器
			mt.addImage(image, 0); //增加待加载Image对象
			try {
				mt.waitForAll(); //等待图片加载
			} catch (Exception e) {
				e.printStackTrace(); //输出错误信息
			}					
			BufferedImage bufImage =new BufferedImage(image.getWidth(this),image.getHeight(this),BufferedImage.TYPE_INT_ARGB); //创建缓冲区图像	
     		Graphics bufImageG2D = bufImage.createGraphics(); //创建缓冲区图像的图形环境	
			bufImageG2D.drawImage(image, 0, 0, this); //在图形环境绘制图像
			return bufImage; //返回缓冲区图像对象
		}
	
		public DisplayPanel() {
			bufImage1=loadBufImage(imageName[0]);  //创建缓冲区图像1
			bufImage2=loadBufImage(imageName[1]);  //创建缓冲区图像2
		}
	
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bufImage1, 0, 0, this); //在图形环境绘制缓冲区图像1
			g2d.drawString("Destination",5,20);  //绘制文字			
			int compositeRule = AlphaComposite.SRC_OVER; //源排斥目标法合成规则			
			AlphaComposite alphaComposite=AlphaComposite.getInstance(compositeRule,alpha); //创建AlphaComposite对象
			g2d.setComposite(alphaComposite); //设置图形环境的合成方式			
			g2d.drawImage(bufImage2, 0, 0, this); //在图形环境绘制缓冲区图像2
			g2d.drawString("Source",150,20);  //绘制文字
		}	
	}
}


