import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import javax.swing.*;

//获取目录/文件信息

public class FileInfoDemo extends JFrame{
	JTextField jtfPath;	//文件路径输入框
	JTextArea jtaInfo;	//显示文件属性文本框

	public FileInfoDemo(){
		super("取得目录/文件信息");	//调用父类构造函数

		jtfPath=new JTextField(16);	//实例化文件输入框
		JButton jbSelectedFile=new JButton("选择");	//实例化文件选择按钮
		JPanel panel=new JPanel();	//面板,用于容纳输入框和文件选择按钮
		jtaInfo=new JTextArea();	//实例化文件信息显示框
		panel.add(jtfPath);	//增加组件到面板
		panel.add(jbSelectedFile);
		Container container=getContentPane();	//得到容器
		container.add(panel,BorderLayout.NORTH);	//增加组件到容器上
		JScrollPane jsp=new JScrollPane(jtaInfo);
		jsp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	//设置边界
		container.add(jsp,BorderLayout.CENTER);

		jbSelectedFile.addActionListener(new ActionListener(){	//选择文件按钮事件处理
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser=new JFileChooser();		//实例化文件选择器
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  //设置文件选择模式,此处为文件和目录均可
				if (fileChooser.showOpenDialog(FileInfoDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
					String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件或目录的绝对路径
					jtfPath.setText(fileName);	
					showFileInfo(jtfPath.getText());	//显示文件信息
				}
			}
		});

		jtfPath.addActionListener(new ActionListener(){	//文件路径输入框事件处理
			public void actionPerformed(ActionEvent event) {
				showFileInfo(jtfPath.getText());	//显示文件信息
			}
		});

		setSize(300,200);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}


	public void showFileInfo(String filename){
		jtaInfo.setText("");	//清空信息显示框
		File f=new File(filename);	//以得到的路径实例化文件对象
		jtaInfo.append(filename+":\n");	//在信息显示窗口增加显示文本
		if (f.isDirectory()){	//是否为目录
			jtaInfo.append("是一个目录");
		}
		else if (f.isFile()){	//是否为文件
			jtaInfo.append("是一个文件");
		}
		jtaInfo.append("\n 可读: "+f.canRead()); //得到可读属性
		jtaInfo.append("\n 可写: "+f.canWrite());	//得到可写属性
		jtaInfo.append("\n 隐藏: "+f.isHidden());  //是否是隐藏文件
		jtaInfo.append("\n 只读: "+f.setReadOnly());  //是否是只读文件
		long modifyDate = f.lastModified();	//得到最后修改日期
		if (modifyDate!=0){
			jtaInfo.append("\n 最后修改日期: "+new Date(modifyDate));
		}
		long length=f.length();	//得到文件长度(如果是目录,则为0)
		if (length!=0){
			jtaInfo.append("\n 文件长度: "+length);
		}
	}

	public static void main(String[] args){
		new FileInfoDemo();
	}
}
