import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;

//HTTP连接与浏览

public class HTTPBrowser extends JFrame{
	JTextField jtfAddress; //输入html文件地址或网址
	JTextPane jtpShow; //显示页面	
	JTextArea jtaSource; //显示HTML源文件

	public HTTPBrowser(){
		super("HTTP连接与浏览"); //调用父类构造函数
		jtfAddress=new JTextField(30); //实例化地址输入框
		jtpShow=new JTextPane(); //实例化显示内容框
		jtaSource=new JTextArea(); 
		
		JPanel p1=new JPanel(); //实例化面板
		JSplitPane spane=new JSplitPane(JSplitPane.VERTICAL_SPLIT); //实例化分隔面板
		p1.add(new JLabel("地址")); //增加组件到面板上
		p1.add(jtfAddress);
		spane.add(new JScrollPane(jtpShow),JSplitPane.TOP);
		spane.add(new JScrollPane(jtaSource),JSplitPane.BOTTOM);
		spane.setDividerLocation(130); //设置分隔位置
		spane.setDividerSize(2); //设置分隔栏尺寸
		Container container=getContentPane(); //得到容器
		container.add(p1,BorderLayout.NORTH); //增加组件到容器上
		container.add(spane,BorderLayout.CENTER);
		
		jtfAddress.addActionListener(new ShowHTMLListener());  //输入地址文本域事件处理	
		
		setSize(380,300);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 //关闭窗口时退出程序
	}
	
	class ShowHTMLListener implements ActionListener {	 
		public void actionPerformed(ActionEvent event) {
			try{
				URL address=new URL(jtfAddress.getText());  //得到HTML页面的URL地址
				jtpShow.setContentType("text/html"); //设置内容格式
				jtpShow.setPage(address); //设置显示页面
				
				BufferedReader in= new BufferedReader(new InputStreamReader(address.openStream())); //获取输入流
				String line;
				StringBuffer content = new StringBuffer(); //文件内容
				while ((line = in.readLine()) != null) { //读取文件
					content.append(line+"\n");
				}					
				jtaSource.setText(new String(content)); //设置显示文本
				in.close(); //关闭输入流
			}
			catch (Exception ex){
				ex.printStackTrace(); //输出出错信息
			}
		}
	}
		
	public static void main(String[] args){
		new HTTPBrowser();
	}
}