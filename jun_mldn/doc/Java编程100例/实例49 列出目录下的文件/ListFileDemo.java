import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//列出目录下的文件

public class ListFileDemo extends JFrame{
	JTextField jtfPath;	//路径输入文本域
	JTextArea jtfShow;	//显示目录下的内容

	public ListFileDemo(){
		super("列出目录下的文件");
		Container container=getContentPane();	//得到容器
		jtfPath=new JTextField(16);	//实例化路径输入文本框
		JButton jbGo=new JButton("转到");	//实例化"转到"按钮
		jtfShow=new JTextArea();	//实例化显示内容文本框
		jtfPath.addActionListener(new ShowDirListener());	//增加事件处理
		jbGo.addActionListener(new ShowDirListener());

		JPanel panel=new JPanel();	//实例化面板,用于增加路径输入和按钮
		panel.add(jtfPath);
		panel.add(jbGo);
		container.add(panel,BorderLayout.NORTH);	//增加组件到容器
		JScrollPane jsp=new JScrollPane(jtfShow);
		jsp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//设置边界
		container.add(jsp,BorderLayout.CENTER);

		setSize(300,200);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}

	class ShowDirListener implements ActionListener {	//取得目录内容的事件处理
		public void actionPerformed(ActionEvent event) {
			showDirContent(jtfPath.getText());	//调用显示目录内容方法
		}
	}

	public void showDirContent(String path){	//该方法实现取得目录内容
		File file=new File(path);	//用路径实例化一个文件对象
		File[] files=file.listFiles();	//重点:取得目录内所有文件列表
		StringBuffer message=new StringBuffer();	//实例化一个StringBuffer,用于处理显示的字符串
		message.append(path);	//增加信息
		message.append(" 内容如下：\n");
		for (int i=0;i<files.length;i++){
			if (files[i].isDirectory()){	//如果这是一个目录
				message.append("<dir>\t");	//增加目录标识
			}
			else{
				message.append("\t");
			}
			message.append(files[i].getName());	//增加文件或目录名
			message.append("\n");
		}
		jtfShow.setText(new String(message));	//显示消息
	}

	public static void main(String[] args){
		new ListFileDemo();
	}
}