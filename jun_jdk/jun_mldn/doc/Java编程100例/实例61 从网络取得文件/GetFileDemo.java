import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

//从网络取得文件

public class GetFileDemo extends JFrame{
	JTextField jtfUrl;  //输入文件地址url
	JButton jbGetFile;  //取文件按钮
	JLabel jlInfo; //显示提示信息
	
	public GetFileDemo(){
		super("从网络取得文件");  //调用父类构造函数
		
		Container container=getContentPane();	//得到容器
		jtfUrl=new JTextField(18); //实例化地址输入框
		jbGetFile=new JButton("取文件");  //实例化按钮
		jlInfo=new JLabel(); 
		JPanel p=new JPanel();  //实例化一个面板,用于容纳地址输入框和取文件按钮
		p.add(jtfUrl); //增加组件到面板上
		p.add(jbGetFile);
		container.add(p,BorderLayout.NORTH);  //增加组件到容器上
		container.add(jlInfo,BorderLayout.CENTER);
		
		jbGetFile.addActionListener(new ActionListener(){  //按钮事件处理
			public void actionPerformed(ActionEvent ent){
				try{
					jlInfo.setText("正在读取");
					URL url=new URL(jtfUrl.getText());    //得到文件的URL地址
					InputStream in=url.openStream();  //得到文件输入流
					String outFilename=JOptionPane.showInputDialog(GetFileDemo.this,"输入保存文件名 "); //输入保存的文件名
					FileOutputStream out=new FileOutputStream(outFilename);  //得到文件输出流
					byte[] buffer=new byte[1024]; //缓冲区大小
					int length;
					while ((length=in.read(buffer))!=-1){  //读取数据
						out.write(buffer,0,length);  //写入数据到文件
					} 
					out.close(); //关闭文件输出流
					in.close();	 //关闭输入流			 
					jlInfo.setText("读取文件成功");  //显示提示信息
				}
				catch(Exception ex){
					ex.printStackTrace(); //输出出错信息
					jlInfo.setText("读取文件失败");
				}
			}
		});
	
		setSize(320,100);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}
	
	public static void main(String[] args){
		new GetFileDemo();
	}
}