import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;

//从网络取得图象

public class GetImageDemo extends JFrame{
	JTextField jtfUrl;  //输入图像地址url
	JButton jbGetImage;  //取图像按钮
	Image image; //获取的图像
	Toolkit toolKit;  //Toolkit对象,用于获取图像
	
	public GetImageDemo(){
		super("从网络取得图象");  //调用父类构造函数
		
		Container container=getContentPane();	//得到容器
		jtfUrl=new JTextField(18); //实例化地址输入框
		jbGetImage=new JButton("取图像");  //实例化按钮
		container.setLayout(new FlowLayout()); //设置布局管理器
		container.add(jtfUrl);  //增加组件到容器上
		container.add(jbGetImage);
		toolKit=getToolkit(); //得到工具包
		
		jbGetImage.addActionListener(new ActionListener(){  //按钮事件处理
			public void actionPerformed(ActionEvent ent){
				try{
					String urlStr=jtfUrl.getText();    //得到图像的URL地址
					URL url=new URL(urlStr);
					image=toolKit.getImage(url); //获取图像
					repaint(); //重绘屏幕
				}
				catch(MalformedURLException ex){
					ex.printStackTrace(); //输出出错信息
				}
			}
		});
	
		setSize(320,160);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}
	
	public void paint(Graphics g){
		super.paint(g);
		if (image!=null){
			g.drawImage(image,100,70,this); //在组件上绘制图像
		}
	}
	
	public static void main(String[] args){
		new GetImageDemo();
	}
}