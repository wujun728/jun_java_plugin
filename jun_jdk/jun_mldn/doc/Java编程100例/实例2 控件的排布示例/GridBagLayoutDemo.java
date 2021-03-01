	import java.awt.*;
	import javax.swing.*;

	public class GridBagLayoutDemo extends JFrame {

	   public GridBagLayoutDemo() {  //构造函数
	    Container contentPane = getContentPane();  //得到容器
	    contentPane.setLayout(new GridBagLayout());  //设置布局管理器

	    JLabel labelName=new JLabel("姓名");  //姓名标签
	    JLabel labelSex=new JLabel("性别");  //性别标签
	    JLabel labelAddress=new JLabel("住址");  //住址标签
	    JTextField textFieldName = new JTextField();  //性名文本域
	    JTextField textFieldAddress = new JTextField(); //地址文本域
	    JComboBox comboBoxSex = new JComboBox();  //性别组合框
	    comboBoxSex.addItem("男");   //增加选择项
	    comboBoxSex.addItem("女");
	    JButton buttonConfirm=new JButton("确定");  //确定按钮
	    JButton buttonCancel=new JButton("退出");  //退出按钮

	    //增加各个组件
	    contentPane.add(labelName,         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
	    contentPane.add(textFieldName,      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
	    contentPane.add(comboBoxSex,           new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
	    contentPane.add(labelSex,        new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
	    contentPane.add(buttonConfirm,      new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 3, 0), 0, 0));
	    contentPane.add(buttonCancel,     new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 3, 0), 0, 0));
	    contentPane.add(labelAddress,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
	    contentPane.add(textFieldAddress,     new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));

	    setTitle("GridBagLayout 演示");  //设置窗口标题
	    setSize(300,140);  //设置窗口尺寸
	    setVisible(true);  //设置窗口可见
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	   }

	   public static void main(String args[])   {
	      new GridBagLayoutDemo();
	   }
	}