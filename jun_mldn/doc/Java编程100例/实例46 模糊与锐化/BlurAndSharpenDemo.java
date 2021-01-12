import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;

// 图像的模糊与锐化

public class BlurAndSharpenDemo extends JFrame implements ActionListener {
	private JPanel jPanel = new JPanel(); //面板jPanel用于容纳模糊、锐化、还原图像按钮
	private JButton buttonFile; //打开图像文件按钮
	private JButton buttonBlur; //模糊图像按钮
	private JButton buttonSharpen; //锐化图像按钮
	private JButton buttonReset; //还原图像按钮
	ImagePanel imagePanel = new ImagePanel(); //创建ImagePanel对象用于绘制图像

	public BlurAndSharpenDemo() {
		super("图像的模糊与锐化演示"); //调用父类构造函数
		Container contentPane = getContentPane(); //得到容器
		buttonFile = new JButton("打开图像文件"); //实例化组件
		buttonFile.addActionListener(this); //增加事件监听		
		buttonBlur = new JButton("模糊图像");
		buttonBlur.addActionListener(this);		
		buttonSharpen = new JButton("锐化图像");
		buttonSharpen.addActionListener(this);		
		buttonReset = new JButton("还原图像");
		buttonReset.addActionListener(this);
		
		jPanel.add(buttonBlur);  //增加组件到面板上
		jPanel.add(buttonSharpen);
		jPanel.add(buttonReset);
		contentPane.add(jPanel, BorderLayout.SOUTH); //增加组件到容器上
		contentPane.add(buttonFile, BorderLayout.NORTH);
		contentPane.add(imagePanel, BorderLayout.CENTER);
		//设置窗口
		this.setSize(280, 230); //设置窗口大小
		this.setVisible(true); //设置窗口可见
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public void actionPerformed(ActionEvent e) {
		 JButton button = (JButton)e.getSource(); //获取事件源		 
		//打开图像文件按钮buttonFile事件处理		 
		if(button==this.buttonFile) {
			JFileChooser chooser = new JFileChooser(); //实例化文件选择器
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //设置文件打开模式为仅打开文件
			chooser.setCurrentDirectory(new File(".")); //设置文件选择器当前目录
			//设置图像文件过滤器
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File file) { //可接受的文件类型
					String name = file.getName().toLowerCase();
					return name.endsWith(".gif")
						|| name.endsWith(".jpg")
						|| name.endsWith(".jpeg")
						|| file.isDirectory();
				}
				public String getDescription() { //文件描述
					return "图像文件";
				}
			});
			int result = chooser.showOpenDialog(this); //显示文件选择对话框
			if (result == JFileChooser.APPROVE_OPTION) { //得到用户行为
				String fileName = chooser.getSelectedFile().getAbsolutePath();	//得到选择的文件名
				imagePanel.loadImage(fileName); //截入图像并显示
			}		 	
		}
		//模糊图像按钮buttonBlur事件处理
		else if(button==this.buttonBlur) {
			imagePanel.blur(); //模糊图像
			buttonReset.setEnabled(true); //设置还原图像按钮可用
		}
		//锐化图像按钮buttonSharpen事件处理		
		else if(button==this.buttonSharpen) {
			imagePanel.sharpen(); //锐化图像
			buttonReset.setEnabled(true); //设置还原图像按钮可用
		}
		//还原图像按钮buttonBlur事件处理
		else if(button==this.buttonReset) {
			imagePanel.reset(); //还原图像
			buttonReset.setEnabled(false); //设置还原图像按钮不可用
		}		
	}

	public static void main(String[] args) {
		new BlurAndSharpenDemo();
	}
	
	public class ImagePanel extends JPanel {
		Image image;  //被处理的图像
		BufferedImage bufImage;  //用于显示的缓冲区图像
		BufferedImage originalBufImage;  //原始缓冲区图像
		Graphics2D g2D;  //图形环境
	
		//载入图像
		public void loadImage(String fileName) {
			image = this.getToolkit().getImage(fileName);  //获取图像
			MediaTracker mt = new MediaTracker(this); //实例化媒体加载器
			mt.addImage(image, 0); //增加待加载图像到媒体加载器
			try {
				mt.waitForAll(); //等待所有图像的加载完成
			} catch (Exception ex) { 
				ex.printStackTrace(); //输出出错信息
			}
			//创建原始缓冲区图像
			originalBufImage = new BufferedImage(image.getWidth(this),image.getHeight(this),BufferedImage.TYPE_INT_ARGB);
			g2D = originalBufImage.createGraphics();  //创建缓冲区图像的图形环境
			g2D.drawImage(image, 0, 0, this); //传输源图像数据到缓冲区图像中
			bufImage = originalBufImage;
			repaint();  //重绘组件
		}
	    //过滤图像
	    public void applyFilter(float[] data) {
			if (bufImage == null)
				return; //如果bufImage为空则直接返回
			Kernel kernel = new Kernel(3, 3, data);  
			ConvolveOp imageOp=new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP, null);	//创建卷积变换操作对象
			BufferedImage filteredBufImage = new BufferedImage(image.getWidth(this),image.getHeight(this),BufferedImage.TYPE_INT_ARGB);	//过滤后的缓冲区图像
			imageOp.filter(bufImage, filteredBufImage);//过滤图像，目标图像在filteredBufImage
			bufImage = filteredBufImage; //让用于显示的缓冲区图像指向过滤后的图像
			repaint(); //重绘组件
	
		}
		//模糊图像
	 	public void blur() {
			if (bufImage == null)
				return;
			float[] data = {
					0.0625f, 0.125f, 0.0625f,
					0.125f,	0.025f, 0.125f,
					0.0625f, 0.125f, 0.0625f 
			};
			applyFilter(data);
		}
		//锐化图像
		public void sharpen() {
			if (bufImage == null)
				return;
			float[] data = { 
			        -1.0f, -1.0f, -1.0f,
			        -1.0f, 9.0f, -1.0f,
			        -1.0f, -1.0f, -1.0f 
			};
			applyFilter(data);
		}
		//恢复图像
		public void reset() {
			if (bufImage == null)
				return;
			bufImage = originalBufImage;  //
			g2D.drawImage(image, 0, 0, this);
			repaint();  //调用paint()方法重绘组件
		}
	
		public void paint(Graphics g) {
			super.paintComponent(g);
			//如果bufImage非空，则在组件上绘制它
			if (bufImage != null) {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(bufImage,(this.getWidth() - bufImage.getWidth()) / 2,	(this.getHeight() - bufImage.getHeight()) / 2,this);
			}
		}
	}
}
