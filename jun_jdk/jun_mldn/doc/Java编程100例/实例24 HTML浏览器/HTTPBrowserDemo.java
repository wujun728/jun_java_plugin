import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

//html浏览器

public class HTTPBrowserDemo extends JFrame{
	JTextField jtfAddress; //输入html文件地址或网址
	JButton jbGo; //转到文件按钮
	JTextPane jtpShow; //显示文件
	JLabel jlInfo; //提示信息

	public HTTPBrowserDemo(){
		super("html浏览器"); //调用父类构造函数
		jtfAddress=new JTextField(20); //实例化地址输入框
		jbGo=new JButton("转到"); //实例化转向按钮
		jtpShow=new JTextPane(); //实例化显示内容框
		jlInfo=new JLabel(); //实例化信息提示标签
		
		JPanel panel=new JPanel(); //实例化面板
		panel.add(new JLabel("地址")); //增加组件到面板上
		panel.add(jtfAddress);
		panel.add(jbGo);
		JScrollPane jsp=new JScrollPane(jtpShow);	//实例化滚动窗体
		Container container=getContentPane(); //得到容器
		container.add(panel,BorderLayout.NORTH); //增加组件到容器上
		container.add(jsp,BorderLayout.CENTER);
		container.add(jlInfo,BorderLayout.SOUTH);
		
		jbGo.addActionListener(new ShowHTMLListener());  //事件处理,发生按钮点击时显示页面内容
		jtfAddress.addActionListener(new ShowHTMLListener());
		
		setSize(350,280);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 //关闭窗口时退出程序
	}
	
	class ShowHTMLListener implements ActionListener {	 //显示页面内容事件处理
		public void actionPerformed(ActionEvent event) {
			try{
				jlInfo.setText("正在连接...");  //显示提示信息
				URL address=new URL(jtfAddress.getText());  //得到HTML页面的URL地址
				jtpShow.setPage(address); //设置显示页面
				jlInfo.setText("完成");
			}
			catch (Exception ex){
				jlInfo.setText("连接出错");
				ex.printStackTrace(); //输出出错信息
			}
		}
	}
	
	public static void main(String[] args){
		new HTTPBrowserDemo();
	}
}