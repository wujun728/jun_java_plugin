import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import sun.net.ftp.*;

//FTP连接与浏览

public class FtpClientDemo extends JFrame{
	
	JTextField jtfServer;  //输入Ftp服务器地址
	JTextField jtfUser;  //输入用户名
	JPasswordField jtfPass;  //输入密码
	JButton jbConnect;  //连接按钮
	JButton jbDisconnect;  //断开按钮
	JTextArea jtaShowFiles;  //显示服务器目录与文件
	FtpClient ftpClient; 
	
	public FtpClientDemo(){
		super("FTP连接与浏览");  //调用父类构造函数
				
		jtfServer=new JTextField(13);  //实例化组件
		jtfUser=new JTextField(8);
		jtfPass=new JPasswordField(8);
		jbConnect=new JButton("连接");
		jbDisconnect=new JButton("断开");
		jtaShowFiles=new JTextArea();		

		Container container=getContentPane(); //得到容器
		JPanel panel=new JPanel();  //实例化面板
		panel.add(new JLabel("地址")); //增加组件到面板上
		panel.add(jtfServer);
		panel.add(new JLabel("用户名"));
		panel.add(jtfUser);
		panel.add(new JLabel("密码"));
		panel.add(jtfPass);
		container.add(panel,BorderLayout.NORTH);  //增加组件到容器上
		JScrollPane jsp=new JScrollPane(jtaShowFiles);
		container.add(jsp,BorderLayout.CENTER);
		panel=new JPanel();
		panel.add(jbConnect);
		panel.add(jbDisconnect);
		container.add(panel,BorderLayout.SOUTH);

		jbConnect.addActionListener(new ActionListener(){  //连接按钮事件处理
			public void actionPerformed(ActionEvent ent){
				connectServer();  //调用连接到服务器的方法
			}	
		});		
		
		jbDisconnect.addActionListener(new ActionListener(){  //断开按钮事件处理
			public void actionPerformed(ActionEvent ent){
				disconnectServer();  //调用断开与服务器连接的方法
			}	
		});	
		
		setSize(460,260);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public void connectServer(){
		try{
			ftpClient = new FtpClient();  //实例化FtpClient对象
			String serverAddr=jtfServer.getText();  //得到服务器地址
			String user=jtfUser.getText();  //得到用户名
			String pass=jtfPass.getPassword().toString();  //得到密码
			ftpClient.openServer(serverAddr);  //连接到服务器
			ftpClient.login(user,pass);  //在服务器上注册
			InputStream is=ftpClient.list();  //得到服务器目录与文件列表输入流
			StringBuffer info=new StringBuffer();  //实例化StringBuffer对象,用于输出信息
			int ch;
			while ((ch=is.read())>=0){  //未读完列表,则继续
				info.append((char)ch); //增加信息
			}
			jtaShowFiles.setText(new String(info)); //显示信息
			is.close(); //关闭输入流
		}
		catch (IOException ex){
			JOptionPane.showMessageDialog(FtpClientDemo.this,ex.getMessage()); //显示提示信息
			ex.printStackTrace();  //在命令行窗口输出出错信息
		}
		
	}
	
	public void disconnectServer(){
		try{
			ftpClient.closeServer(); //关闭与服务器的连接
		}
		catch (IOException ex){
			ex.printStackTrace();
		}	
	}
	
	public static void main(String[] args){
		new FtpClientDemo();	
	}
}