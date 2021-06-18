import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JPasswordFieldDemo extends JFrame {
	JTextField username;  //用户名输入框
   JPasswordField password;  //密码输入框
  	JButton logonButton;  //登录按钮
  	JButton cancelButton;  //退出按钮

   public JPasswordFieldDemo() {  //构造函数

   	super("JPasswordField演示");  //调用父类构造函数
   	Container container=getContentPane();  //得到容器
   	container.setLayout(new GridLayout(3, 2, 2, 2));  //设置布局管理器

   	username=new JTextField(16);  //初始化文本输入框,宽度为16列
   	password=new JPasswordField(16);  //初始化密码输入框,宽度为16列
   	logonButton=new JButton("登录");  //初始化登录按钮
   	logonButton.addActionListener(  //登录按钮事件处理
   		new ActionListener(){
      	public void actionPerformed(ActionEvent evt){
      		char[] pw=password.getPassword();  //得到密码
         	String message="您的用户名："+username.getText()+"\n您的密码："+new String(pw);  //消息字符串
   			JOptionPane.showMessageDialog(JPasswordFieldDemo.this, message);  //显示消息
       }
    	});
	   	cancelButton=new JButton("退出");  //初始化退出按钮
	   	cancelButton.addActionListener(  //初始化按钮事件处理
	   		new ActionListener(){
	      	public void actionPerformed(ActionEvent evt){
	            System.exit(0);  //退出程序
	       }
	    });

	   container.add(new JLabel("      用户名:"));  //增加组件
	   container.add(username);
	   container.add(new JLabel("      密码:"));
	  	container.add(password);
	  	container.add(logonButton);
	  	container.add(cancelButton);
	  	setResizable(false);  //不允许用户改变窗口大小
	  	setSize(300,120);  //设置窗口尺寸
	  	setVisible(true);  //设置窗口可视
	  	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
 	}

 	public static void main(String[] args) {
   		new JPasswordFieldDemo();
  	}
}
