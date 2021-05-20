import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;

//用ZIP压缩多个文件

public class ZipDemo extends JFrame{
	JFileChooser fileChooser; //文件选择器
	JList fileList;	//待压缩的文件列表
	Vector files;	//文件数据(待压缩文件)
	JButton jbAdd;	//增加文件按钮
	JButton jbDelete; //删除文件按钮
	JButton jbZip; //压缩按钮
	JTextField target; //目标文件文本域

	public ZipDemo(){
		super("用ZIP压缩多个文件");	//调用父类构造函数
		fileChooser=new JFileChooser();	//实例化文件选择器
		files=new Vector(); //实例化文件数据Vector
		fileList=new JList(files); //实例化已选择文件列表
		jbAdd=new JButton("增加"); //实例化按钮组件
		jbDelete=new JButton("删除");
		jbZip=new JButton("压缩");
		target=new JTextField(18);
		JPanel panel=new JPanel(); //实例化面板,用于容纳按钮
		panel.add(jbAdd);	//增加组件到面板上
		panel.add(jbDelete);
		panel.add(jbZip);
		JPanel panel2=new JPanel();
		panel2.add(new JLabel("目标文件"));
		panel2.add(target);
		JScrollPane jsp=new JScrollPane(fileList);
		Container container=getContentPane(); //得到容器
		container.add(panel2,BorderLayout.NORTH); //增加组件到容器
		container.add(jsp,BorderLayout.CENTER);
		container.add(panel,BorderLayout.SOUTH);
		jsp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  //设置边界

		jbAdd.addActionListener(new ActionListener(){	//增加文件按钮事件处理
			public void actionPerformed(ActionEvent event) {
				if (fileChooser.showOpenDialog(ZipDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
					String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件的绝对路径
					files.add(fileName);  //增加文件到Vector
					fileList.setListData(files); //设置文件选择列表的数据
      		}
			}
		});


		jbDelete.addActionListener(new ActionListener(){	//删除文件按钮事件处理
			public void actionPerformed(ActionEvent event) {
				files.remove(fileList.getSelectedValue());	//从Vector中移除选择文件
				fileList.setListData(files); //设置文件选择列表的数据
			}
		});

		jbZip.addActionListener(new ActionListener(){	//压缩按钮事件处理
			public void actionPerformed(ActionEvent event) {
				zipFiles(files.toArray(),target.getText()); //调用压缩文件方法
			}
		});

		setSize(330,250);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}

	public void zipFiles(Object[] sources,String target){ //压缩文件
		try{
			FileOutputStream fout=new FileOutputStream(target);	//得到目标文件输出流
			ZipOutputStream zout=new ZipOutputStream(fout);	//得到压缩输出流
			byte[] buf=new byte[1024];//设定读入缓冲区尺寸
			int num;
			FileInputStream fin=null;
			ZipEntry entry=null;
			for (int i=0;i<sources.length;i++){
				String filename=sources[i].toString(); //得到待压缩文件路径名
				String entryname=filename.substring(filename.lastIndexOf("\\")+1); //得到文件名
				entry=new ZipEntry(entryname); //实例化条目列表
				zout.putNextEntry(entry); //将ZIP条目列表写入输出流
				fin=new FileInputStream(filename); //从源文件得到文件输入流
				while ((num=fin.read(buf))!=-1){  //如果文件未读完
					zout.write(buf,0,num);	//写入缓冲数据
				}
			}
			zout.close();	//关闭压缩输出流
			fout.close();	//关闭文件输出流
			fin.close();	//关闭文件输入流
			showMessage("压缩成功");	//显示操作信息

		}
		catch (Exception ex){
			ex.printStackTrace();	//打印出错信息
			showMessage("压缩失败");
		}
	}


	class SelectFileListener implements ActionListener {	//文件选择的事件处理
		public void actionPerformed(ActionEvent event) {
			if (fileChooser.showOpenDialog(ZipDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
				String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件的绝对路径
      	}
		}
	}

	private void showMessage(String message){
		JOptionPane.showMessageDialog(ZipDemo.this,message); //显示信息
	}

	public static void main(String[] args){
		new ZipDemo();
	}
}