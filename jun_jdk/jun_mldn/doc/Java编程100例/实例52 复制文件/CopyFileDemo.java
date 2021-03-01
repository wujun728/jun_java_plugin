import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//拷贝文件演示

public class CopyFileDemo extends JFrame{
	JFileChooser fileChooser;	//文件选择器
	JTextField jtfSourceFile;	//源文件路径
	JTextField jtfTargetFile;	//目标文件路径
	JButton selectFile1;	//选择文件按钮
	JButton selectFile2;
	JButton copyButton;	//拷贝按钮

	public CopyFileDemo(){
		super("拷贝文件演示");	//调用父类构造函数
		fileChooser=new JFileChooser();	//实例化文件选择器

		Container container=getContentPane();	//得到容器
		jtfSourceFile=new JTextField(16);	//实例化组件
		jtfTargetFile=new JTextField(16);
		selectFile1=new JButton("选择");
		selectFile2=new JButton("选择");
		copyButton=new JButton("拷贝");
		Box box=Box.createVerticalBox();	//实例化Box,用于容纳组件
		JPanel panel=new JPanel();
		panel.add(new JLabel("源  文 件"));	//增加组件到面板(panel)上
		panel.add(jtfSourceFile);
		panel.add(selectFile1);
		box.add(panel);	//增加组件到Box上
		panel=new JPanel();
		panel.add(new JLabel("目标文件"));
		panel.add(jtfTargetFile);
		panel.add(selectFile2);
		box.add(panel);
		box.add(copyButton);
		container.add(box);	//增加组件到容器上


		selectFile1.addActionListener(new SelectFileListener());	//设置选择文件的事件处理
		selectFile2.addActionListener(new SelectFileListener());
		copyButton.addActionListener(new ActionListener(){	//拷贝按钮事件处理
			public void actionPerformed(ActionEvent event) {
				String sourceFile=jtfSourceFile.getText();	//得到源文件路径
				String targetFile=jtfTargetFile.getText();	//得到目标文件路径
				if (copy(sourceFile,targetFile)){	//拷贝文件
					JOptionPane.showMessageDialog(CopyFileDemo.this,"拷贝成功");	//显示拷贝成功信息
				}
				else{
					JOptionPane.showMessageDialog(CopyFileDemo.this,"拷贝失败");	//发生错误
				}
			}
		});


		setSize(340,160);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}

	class SelectFileListener implements ActionListener {	//取得目录内容的事件处理
		public void actionPerformed(ActionEvent event) {
			if (fileChooser.showOpenDialog(CopyFileDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
				String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件的绝对路径
				if (event.getSource().equals(selectFile1)){	//判断事件来自于哪个按钮
					jtfSourceFile.setText(fileName);	//设置源文件路径
				}
				else{
					jtfTargetFile.setText(fileName);	//设置目标文件路径
				}
      	}
		}
	}

	public boolean copy(String file1,String file2){	//拷贝文件方法
		try{
			java.io.File fileIn=new java.io.File(file1);	//用路径名生成源文件
			java.io.File fileOut=new java.io.File(file2);	//用路径名生成目标文件
			FileInputStream fin=new FileInputStream(fileIn);	//得到文件输入流
			FileOutputStream fout=new FileOutputStream(fileOut);	//得到文件输出流
			byte[] bytes=new byte[1024];	//初始化字节数组,用于缓冲
			int c;
			while((c=fin.read(bytes))!=-1){	//如果文件未读完
				fout.write(bytes,0,c);	//将读取的字节数组写入目标文件输出流中
			}
			fin.close();	//关闭输入流
			fout.close();	//关闭输出流
			return true;
		}
		catch(Exception e){
			return false;
		}
	}

	public static void main(String[] args){
		new CopyFileDemo();
	}
}
