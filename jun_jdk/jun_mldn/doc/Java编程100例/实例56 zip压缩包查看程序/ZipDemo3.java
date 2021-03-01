import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;

//zip压缩包查看程序

public class ZipDemo3 extends JFrame{
	JFileChooser fileChooser; //文件选择器
	JTextField jtfTarget; //待查看文件路径
	JButton jbSelected; //选择文件按钮
	JTextArea jtaInfo;  //信息显示文本框

	public ZipDemo3(){
		super("zip压缩包查看程序");	//调用父类构造函数

		fileChooser=new JFileChooser();  //实例化组件
		jtfTarget=new JTextField(18);
		jbSelected=new JButton("选择");
		jtaInfo=new JTextArea();
		JPanel panel=new JPanel(); //实例化面板
		panel.add(new JLabel("目标文件"));
		panel.add(jtfTarget);  //增加组件到面板上
		panel.add(jbSelected);
		JScrollPane jsp=new JScrollPane(jtaInfo);
		jsp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  //设置边界
		Container container=getContentPane();  //得以容器
		container.add(panel,BorderLayout.NORTH);  //增加组件到容器上
		container.add(jsp,BorderLayout.CENTER);

		jbSelected.addActionListener(new ActionListener(){	//文件选择按钮事件处理
			public void actionPerformed(ActionEvent event) {
				if (fileChooser.showOpenDialog(ZipDemo3.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
					String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件的绝对路径
					jtfTarget.setText(fileName);  //设置目标文件名
					showFileInfo();  //显示文件内容
	      	}
			}
		});
		jtfTarget.addActionListener(new ActionListener(){	//压缩按钮事件处理
			public void actionPerformed(ActionEvent event) {
					showFileInfo();  //显示文件内容
	      	}
		});

		setSize(350,250);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}

	public void showFileInfo(){  //显示压缩文件内容
		try{
			jtaInfo.append("文件名\t文件大小\t创建日期\n"); //增加信息到显示文本框
			ZipFile zfile=new ZipFile(jtfTarget.getText());  //实例化压缩文件
			ZipEntry entry;
			Enumeration enu=zfile.entries(); //得到压缩条目的枚举对象
			while (enu.hasMoreElements()){  //依次枚举条目
				entry=(ZipEntry) enu.nextElement(); //得到压缩条目
				jtaInfo.append(entry.getName()+"\t"); //增加显示信息
				jtaInfo.append(entry.getSize()+"\t");
				jtaInfo.append(new Date(entry.getTime())+"\n");
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args){
		new ZipDemo3();
	}
}