	import java.awt.*;

	import javax.swing.*;

	public class BorderLayoutDemo extends JFrame{

	   public static void main( String args[] ){  //构造函数
	      Container container = getContentPane();  //得到容器
	      container.setLayout( new BorderLayout() ); //设置布局管理器为Borderlayout

	      container.add(new JButton("North"), BorderLayout.NORTH);  //增加按钮
	      container.add(new JButton("South"), BorderLayout.SOUTH);
	      container.add(new JButton("East"), BorderLayout.EAST);
	      container.add(new JButton("West"), BorderLayout.WEST);
	      container.add(new JButton("Center"), BorderLayout.CENTER);

	      setTitle("BorderLayout 演示");  //设置窗口标题
	      setSize(280,200);  //设置主窗口尺寸
	      setVisible(true);  //设置主窗口可视
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	   }

	   public static void main( String args[] ){
	     new BorderLayoutDemo();
	   }
	}