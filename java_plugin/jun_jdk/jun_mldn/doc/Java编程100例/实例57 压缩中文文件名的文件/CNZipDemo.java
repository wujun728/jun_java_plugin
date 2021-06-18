import java.io.*;
import java.awt.event.*;
import java.util.zip.CNZipOutputStream;
import java.util.zip.*;
import javax.swing.*;

//压缩中文文件名的文件

public class CNZipDemo extends JFrame{
	JFileChooser fileChooser;	//文件选择器
	JTextField jtfSourceFile;	//源文件路径
	JTextField jtfTargetFile;	//目标文件路径
	JButton selectFile1;	//选择文件按钮
	JButton selectFile2;
	JButton jbZip;	//
	JButton jbUnZip;

	public CNZipDemo(){
		super("压缩中文文件名的文件");	//调用父类构造函数
		fileChooser=new JFileChooser();	//实例化文件选择器

		jtfSourceFile=new JTextField(16);	//实例化组件
		jtfTargetFile=new JTextField(16);
		selectFile1=new JButton("选择");
		selectFile2=new JButton("选择");
		jbZip=new JButton("压缩");
		jbUnZip=new JButton("解压");
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
		panel=new JPanel();
		panel.add(jbZip);
		panel.add(jbUnZip);
		box.add(panel);
		getContentPane().add(box); //增加Box到容器上

		jbZip.addActionListener(new ActionListener(){	//压缩按钮事件处理
			public void actionPerformed(ActionEvent event) {
				zipFile(jtfSourceFile.getText(),jtfTargetFile.getText());  //调用压缩文件方法
			}
		});

		jbUnZip.addActionListener(new ActionListener(){	//解压按钮事件处理
			public void actionPerformed(ActionEvent event) {
				unZipFile(jtfSourceFile.getText(),jtfTargetFile.getText());	//调用解压文件方法
			}
		});

		selectFile1.addActionListener(new SelectFileListener());	//设置选择文件的事件处理
		selectFile2.addActionListener(new SelectFileListener());

		setSize(330,150);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}

	public void zipFile(String source,String target){ //压缩文件
		try{
			FileInputStream fin=new FileInputStream(source);	//从源文件得到文件输入流
			FileOutputStream fout=new FileOutputStream(target);	//得到目标文件输出流
			CNZipOutputStream cnout=new CNZipOutputStream(fout,"GB2312");	//得到压缩输出流
			String filename=jtfSourceFile.getText();
			String entryname=filename.substring(filename.lastIndexOf("\\")+1); //得到文件名
			ZipEntry entry=new ZipEntry(entryname); //实例化条目列表
			cnout.putNextEntry(entry); //将ZIP条目列表写入输出流
			byte[] buf=new byte[1024]; //设定读入缓冲区尺寸
			int num;
			while ((num=fin.read(buf))!=-1){  //如果文件未读完
				cnout.write(buf,0,num);	//写入缓冲数据
			}
			cnout.close();	//关闭压缩输出流
			fout.close();	//关闭文件输出流
			fin.close();	//关闭文件输入流
			showMessage("压缩成功");	//显示操作信息

		}
		catch (Exception ex){
			ex.printStackTrace();	//打印出错信息
			showMessage("压缩失败");
		}
	}

	public void unZipFile(String source,String target){	//解压文件
		try{
			FileInputStream fin=new FileInputStream(source);	//得以文件输入流
			GZIPInputStream gzin=new GZIPInputStream(fin);	//得到压缩输入流
			FileOutputStream fout=new FileOutputStream(target);	//得到文件输出流
			byte[] buf=new byte[1024];	//缓冲区大小
			int num;
			while ((num=gzin.read(buf,0,buf.length))!=-1) { //如果文件未读完
				fout.write(buf,0,num);	//写入缓冲数据到输出流
			}
			gzin.close();	//关闭压缩输入流
			fout.close();  //关闭文件输出流
			fin.close();   //关闭文件输入流
			showMessage("解压成功");  //显示操作信息
		}
		catch (Exception ex){
			ex.printStackTrace();  //打印出错信息
			showMessage("解压成功");
		}
	}

	class SelectFileListener implements ActionListener {	//文件选择的事件处理
		public void actionPerformed(ActionEvent event) {
			if (fileChooser.showOpenDialog(CNZipDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
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

	private void showMessage(String message){
		JOptionPane.showMessageDialog(CNZipDemo.this,message); //显示信息
	}

	public static void main(String[] args){
		new CNZipDemo();
	}
}
