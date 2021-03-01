import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;

//从压缩包中提取文件

public class ZipExtractDemo extends JFrame{
	JFileChooser fileChooser; //文件选择器
	JTextField jtfTarget; //待解压文件路径
	JButton jbSelected; //选择文件按钮
	JList files;  //信息显示列表框
	JButton jbExtract;  //解压按钮
	ZipFile zFile; 

	public ZipExtractDemo(){
		super("从压缩包中提取文件");	//调用父类构造函数

		fileChooser=new JFileChooser();  //实例化组件
		jtfTarget=new JTextField(13);
		jbSelected=new JButton("选择");
		jbExtract=new JButton("解压");
		files=new JList();
		JPanel panel=new JPanel(); //实例化面板
		panel.add(new JLabel("目标文件"));
		panel.add(jtfTarget);  //增加组件到面板上
		panel.add(jbSelected);
		panel.add(jbExtract);
		JScrollPane jsp=new JScrollPane(files);
		jsp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  //设置边界
		Container container=getContentPane();  //得以容器
		container.add(panel,BorderLayout.NORTH);  //增加组件到容器上
		container.add(jsp,BorderLayout.CENTER);

		jbSelected.addActionListener(new ActionListener(){	//文件选择按钮事件处理
			public void actionPerformed(ActionEvent event) {
				if (fileChooser.showOpenDialog(ZipExtractDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
					String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件的绝对路径
					jtfTarget.setText(fileName);  //设置目标文件名
					showFiles();  //显示压缩包内容
	      	}
			}
		});
		
		jbExtract.addActionListener(new ActionListener(){	//解村文件按钮事件处理
			public void actionPerformed(ActionEvent event) { 
					extractFile(files.getSelectedValue().toString());  //解压指定文件
	      	}
		});

		setSize(350,250);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}
	
	public void showFiles(){
		try{
		zFile=new ZipFile(jtfTarget.getText()); //得到压缩文件实例
		ZipEntry entry;
		Vector vec=new Vector(); //文件枚举
			Enumeration enu=zFile.entries(); //得到压缩条目的枚举对象
			while (enu.hasMoreElements()){  //依次枚举条目
				entry=(ZipEntry) enu.nextElement(); //得到压缩条目
				vec.add(entry.getName());	//增加文件到Vector内		
			}
			files.setListData(vec); //设置文件列表框数据
			files.getSelectedValues();
		}
		catch (Exception ex){
			ex.printStackTrace();  //输出错误信息
		}		
	}

	public void extractFile(String name){  //解压文件
		try{
		ZipEntry entry=zFile.getEntry(name); 
		entry.getComment();
		entry.getCompressedSize();
		entry.getCrc();
		entry.isDirectory();'
		entry.getSize();
		entry.getMethod();
		InputStream in=zFile.getInputStream(entry); //得到文件输入流
		File file=new File(name); //实例化文件对象
		FileOutputStream out=new FileOutputStream(file); //得到文件输出流
		byte[] buffer=new byte[1024]; //缓冲区大小
		int length;
		while((length = in.read(buffer)) != -1) {  //循环读取数据
			out.write(buffer, 0, length);  //写数据到输出流
		}
			JOptionPane.showMessageDialog(ZipExtractDemo.this,"解压成功,解压到："+file.getAbsolutePath());	
		in.close(); //关闭输入流
		out.close(); //关闭输出流	
		} catch (Exception ex){}
	}

	public static void main(String[] args){
		new ZipExtractDemo();
	}
}
