import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//显示多种风格的窗口

public class LookAndFeelDemo extends JFrame {

   public LookAndFeelDemo(){
     super("多种风格的窗口");  //调用父类构造函数

     Container container=getContentPane();  //得到容器

	 JMenu menuTheme=new JMenu("窗口风格");  //初始化菜单
     JMenuItem itemNative=new JMenuItem("系统平台风格");  //初始化菜单项
     JMenuItem itemMotif=new JMenuItem("Motif风格");
     JMenuItem itemMetal=new JMenuItem("跨平台风格");
     menuTheme.add(itemNative);  //增加菜单项
     menuTheme.add(itemMotif);
     menuTheme.add(itemMetal);
     itemNative.addActionListener(new ActionListener(){  //菜单项事件处理
     	public void actionPerformed(ActionEvent event){
     		changeLookAndFeel("Native");  //调用方法,改变窗口风格
        }
     });
     itemMotif.addActionListener(new ActionListener(){
     	public void actionPerformed(ActionEvent event){
     		changeLookAndFeel("Motif");
        }
     });
     itemMetal.addActionListener(new ActionListener(){
     	public void actionPerformed(ActionEvent event){
     		changeLookAndFeel("Metal");
        }
     });

     JMenuBar menuBar=new JMenuBar();  //初始化菜单栏
     menuBar.add(menuTheme);  //增加菜单到菜单栏
     setJMenuBar(menuBar);  //设置菜单

     JPanel panel=new JPanel();  //初始化一个JPanel
     panel.setBorder(BorderFactory.createTitledBorder("组件样式"));  //设置边界
     panel.add(new JTextField("文本框：Look and feel测试 "));  //增加组件到panel上
     panel.add(new JCheckBox("粗体"));
     panel.add(new JCheckBox("斜体"));
     panel.add(new JCheckBox("下划线"));
     panel.add(new JButton("确定"));
     panel.add(new JButton("退出"));
     container.add(panel);  //增加panel到容器上

     setSize(220,200);  //设置窗口尺寸
     setVisible(true);  //设置窗口可见
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
   }

   //改变窗口样式
   public void changeLookAndFeel(String type){
      try{
		 if (type.equals("Native")) {  //判断来自于哪个菜单项
		 	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  //设置界面样式
		 }
		 else if (type.equals("Motif")) {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		 }
		 else if (type.equals("Metal")) {UIManager.setLookAndFeel(
			UIManager.getCrossPlatformLookAndFeelClassName());
		 }
		 javax.swing.SwingUtilities.updateComponentTreeUI(this);  //更新界面
	 }
	 catch(Exception ex){  //捕捉错误
       ex.printStackTrace();  //输出错误
     }
   }

   public static void main(String[] args){
      new LookAndFeelDemo();
   }
}