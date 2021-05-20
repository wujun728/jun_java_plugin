import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

//文件选择器演示

public class JFileChooserDemo extends JFrame {
	private JFileChooser chooser;  //文件选择器
	private JButton button;  //选择文件按钮
	private JComboBox comboBox;  //用于设定文件对话框作用(打开还是保存文件)

	public JFileChooserDemo() {
		super("JFileChooser 演示");  //调用父类构造函数
		Container contentPane = getContentPane();  //得到容器
		contentPane.setLayout(new FlowLayout());  //设置布局管理器为Flowlayout
        chooser=new JFileChooser();  //初始化文件选择器
        button = new JButton("选择文件");  //初始化按钮
        comboBox=new JComboBox();  //初始化组合框
        comboBox.addItem("打开");  //增加组合框列表内容
        comboBox.addItem("保存");
        contentPane.add(comboBox);  //增加组件到容器
		contentPane.add(button);

		button.addActionListener(new ActionListener() {  //按钮事件处理
		    public void actionPerformed(ActionEvent e) {
			    int state;  //文件选择器返回状态
			    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());  //移去所有文件过滤器
			    chooser.addChoosableFileFilter(new MyFileFilter("gif","图像文件"));   //增加文件过滤器,接爱gif文件

			    if (comboBox.getSelectedIndex()==0)  //组合框为"打开"
			       state=chooser.showOpenDialog(null);   //显示打开文件对话框
			    else
			       state=chooser.showSaveDialog(null);  //显示保存文件对话框
			    File file = chooser.getSelectedFile();  //得到选择的文件

			    if(file != null && state == JFileChooser.APPROVE_OPTION) {  //选择了文件并点击了打开可保存按钮
			       JOptionPane.showMessageDialog(null, file.getPath()); //显示提示信息
			    }
			    else if(state == JFileChooser.CANCEL_OPTION) {  //点击了撤销按钮
			      JOptionPane.showMessageDialog(null, "退出!");  //显示提示信息
			    }
			    else if(state == JFileChooser.ERROR_OPTION) {
			      JOptionPane.showMessageDialog(null, "错误!");  //显示提示信息
			    }
		}
		});

		this.setSize(200,100);  //设置窗口大小
		this.setVisible(true);  //设置窗口可见
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}

	public static void main(String args[]) {
		new JFileChooserDemo();
	}
}
