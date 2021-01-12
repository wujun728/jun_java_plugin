import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//目录和文件的创建、删除和更名

public class FileUseDemo extends JFrame{

	JTextField jtfPath;

	public FileUseDemo(){
		super("目录和文件的创建、删除和更名");	//调用父类构造函数

		jtfPath=new JTextField(16);	//实例化文件路径输入框
		JButton jbFile=new JButton("选择");	//实例化文件选择按钮
		JPanel panel=new JPanel();	//实例化面板,用于容纳输入框和按钮
		panel.add(new JLabel("文件名: "));	//增加组件到面板
		panel.add(jtfPath);
		panel.add(jbFile);
		JPanel panel2=new JPanel();
		panel2.add(new JButton(new CreateFileAction()));  //实例化按钮,并增加到面板2上
		panel2.add(new JButton(new RenameFileAction()));
		panel2.add(new JButton(new DeleteFileAction()));

		jbFile.addActionListener(new ActionListener(){	//选择文件按钮事件处理
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser=new JFileChooser();		//实例化文件选择器
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  //设置文件选择模式,此处为文件和目录均可
				if (fileChooser.showOpenDialog(FileUseDemo.this)==JFileChooser.APPROVE_OPTION){	//弹出文件选择器,并判断是否点击了打开按钮
					String fileName=fileChooser.getSelectedFile().getAbsolutePath();	//得到选择文件或目录的绝对路径
					jtfPath.setText(fileName);
				}
			}
		});

		Container container=getContentPane();	//得到容器
		container.add(panel,BorderLayout.NORTH);	//增加组件到容器
		container.add(panel2,BorderLayout.CENTER);

		setSize(330,120);	//设置窗口尺寸
		setVisible(true);	//设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}

	class CreateFileAction extends AbstractAction {	//创建新文件
		public CreateFileAction() {
		    super("创建");	//调用父类构造函数
		}
		public void actionPerformed(ActionEvent e) {
			String filename=jtfPath.getText();	//从输入框得到文件名
			File sfile=new File(filename);	//实例化一个文件
			try{
				if (!sfile.exists()){	//如果文件不存在
					if (sfile.createNewFile()==true){	//创建文件成功
						showMessage(filename+" 新文件创建成功.");	//显示信息
					}
					else{
						showMessage(filename+" 新文件创建失败.");
					}
				}
				else{
					showMessage(filename+" 原文件已存在.");
				}
			}
			catch (Exception ex){
				ex.printStackTrace();	//打印错误信息
			}
		}
	}

	class RenameFileAction extends AbstractAction {	//重命名文件
		public RenameFileAction() {
		    super("重命名");
		}
		public void actionPerformed(ActionEvent e) {
			String filename=JOptionPane.showInputDialog("输入新文件名"); //输入新文件名对话框
			File sfile=new File(jtfPath.getText()); //源文件
			File f=new File(filename);	//新文件
			sfile.renameTo(f);	//重命名
			showMessage(sfile.getName()+" 重命名成功."); //显示信息
			jtfPath.setText(f.getAbsolutePath());	//更新输入框的文件名
		}
	}

	class DeleteFileAction extends AbstractAction {	//删除文件
		public DeleteFileAction() {
		    super("删除");
		}
		public void actionPerformed(ActionEvent e) {
			File sfile=new File(jtfPath.getText()); //源文件
			try{
				sfile.delete();	//删除文件
				showMessage(sfile.getName()+" 删除成功.");	//显示信息

			}
			catch(Exception ex){
				showMessage(sfile.getName()+" 删除失败.");
				ex.printStackTrace();
			}
		}
	}

	private void showMessage(String message){
		JOptionPane.showMessageDialog(FileUseDemo.this,message); //显示信息
	}

	public static void main(String[] args){
		new FileUseDemo();
	}
}
