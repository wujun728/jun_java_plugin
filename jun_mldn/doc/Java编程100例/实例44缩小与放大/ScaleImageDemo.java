import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.color.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

// 图像缩小与放大演示

public class ScaleImageDemo extends JFrame {
	private JPanel panel = new JPanel(); //面板panel用于容纳图像放大、缩小、还原按钮
	private JButton jbFile = new JButton("打开图像文件"); //打开图像文件按钮
	private JButton jbZoomIn = new JButton("放大"); //图像放大按钮
	private JButton jbZoomOut = new JButton("缩小"); //图像缩小按钮
	private JButton jbReset = new JButton("还原"); //图像还原按钮
	ScalePane showImagePane = new ScalePane(); //创建showImagePane对象用于绘制图像
	Container content = getContentPane(); //获得窗口的容器

    //构造函数
	public ScaleImageDemo() {
		super("图像的缩小与放大"); //调用父类构造器设置窗口标题栏
		//为按钮添加动作监听器
		jbFile.addActionListener(new ButtonActionListener());
		jbZoomIn.addActionListener(new ButtonActionListener());
		jbZoomOut.addActionListener(new ButtonActionListener());
		jbReset.addActionListener(new ButtonActionListener());		

		//把图像放大按钮、图像缩小按钮、图像还原按钮加入panel面板
		panel.add(jbZoomIn);
		panel.add(jbZoomOut);
		panel.add(jbReset);
		//把showImagePane文件选择组合框、控制面板、状态栏标签加入到窗口内容窗格
		content.add(showImagePane, BorderLayout.CENTER);
		content.add(jbFile, BorderLayout.NORTH);
		content.add(panel, BorderLayout.SOUTH);		
		
		setSize(260, 200); //设置窗口大小
		setVisible(true); //设置窗口可见
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
    //使用文件选择器载入图像
	public void fileSelect() {
		JFileChooser chooser = new JFileChooser(); //实例化文件选择器
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//模式为仅打开文件
		chooser.setCurrentDirectory(new File(".")); //设置文件选择器当前目录
		//设置图像文件过滤器
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File file) {  //可接受的文件类型
				String name = file.getName().toLowerCase(); //获取文件名
				return name.endsWith(".gif")
					|| name.endsWith(".jpg")
					|| name.endsWith(".jpeg")
					|| file.isDirectory();
			}
			public String getDescription() {
				return "图像文件"; //文件描述
			}
		});
		int result = chooser.showOpenDialog(this); //显示文件选择对话框
		if (result == JFileChooser.APPROVE_OPTION) { //得到用户行为
			String fileName = chooser.getSelectedFile().getAbsolutePath();	//得到选择的文件名
			showImagePane.loadImage(fileName); //截入图像并显示
		}		
	}
	
	public static void main(String[] args) {
		new ScaleImageDemo();
	}
	
	//按钮事件处理类
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			if(button==jbFile) {
				fileSelect();			
			} else if(button==jbZoomIn) {
				showImagePane.scaleX *= 1.25; //图像x轴方向放大因子
				showImagePane.scaleY *= 1.25; //图像y轴方向放大因子
				showImagePane.applyFilter(); //过滤图像
				showImagePane.repaint(); //重绘showImagePane面板	
				jbReset.setEnabled(true);				
			} else if(button==jbZoomOut) {
				showImagePane.scaleX *= 0.8; //图像x轴方向缩小因子
				showImagePane.scaleY *= 0.8; //图像y轴方向缩小因子
				showImagePane.applyFilter(); //过滤图像
				showImagePane.repaint(); //重绘showImagePane面板
				jbReset.setEnabled(true);
			} else if(button==jbReset) {
				showImagePane.scaleX = 1.0; //图像x轴方向放大因子还原为1.0
				showImagePane.scaleY = 1.0; //图像y轴方向放大因子还原为1.0
				showImagePane.applyFilter(); //过滤图像
				showImagePane.repaint(); //重绘showImagePane面板
				jbReset.setEnabled(false);
			}
		}
	}
		
	//显示图像的面板
	class ScalePane extends JPanel {
		Image image;
		BufferedImage bufImage; //用于显示的缓冲区图像
		BufferedImage originalBufImage; //原始缓冲区图像
		Graphics2D bufImageG; //缓冲区图像的图形环境	
		double scaleX = 1.0; //图像水平方向的缩放因子
		double scaleY = 1.0; //图像竖直方向的缩放因子
	
		//截入图像
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
	    public void applyFilter() {
			if (bufImage == null)
				return; //如果bufImage为空则直接返回
			BufferedImage filteredBufImage =new BufferedImage((int) (image.getWidth(this) * scaleX),(int) (image.getHeight(this) * scaleY),BufferedImage.TYPE_INT_ARGB); //过滤后的图像
			AffineTransform transform = new AffineTransform(); //仿射变换对象
			transform.setToScale(scaleX, scaleY); //设置仿射变换的比例因子	
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
				g2.drawImage(bufImage,(this.getWidth() - bufImage.getWidth()) / 2,(this.getHeight() - bufImage.getHeight()) / 2,this);	//绘制图片
			}
		}
	}
}

