	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;

	public class JColorChooserDemo extends JFrame {
	   private Container container;  //容器
	   private JPanel colorPanel; //用于反映颜色变化的面板

	   public JColorChooserDemo() {  //构造函数
	      super( "调色板演示" );  //调用JFrame的构造函数
	      container = getContentPane();  //得到容器
	      colorPanel=new JPanel();  //初始化面板

	      JButton selectColorButton = new JButton( "选取颜色" );  //初始化颜色选择按钮
	      selectColorButton.addActionListener(  //为颜色选择按钮增加事件处理
	         new ActionListener() {
	            public void actionPerformed( ActionEvent event )
	            {
	            	JColorChooser chooser=new JColorChooser();	//实例化颜色选择器
	               Color color=chooser.showDialog(JColorChooserDemo.this,"选取颜色",Color.lightGray );  //得到选择的颜色
	               if (color==null)  //如果未选取
	                  color=Color.gray;  //则设置颜色为灰色
	               colorPanel.setBackground(color);  //改变面板的背景色
				}

	      });
	      container.add(selectColorButton,BorderLayout.NORTH);  //增加组件
	      container.add(colorPanel,BorderLayout.CENTER);  //增加组件
	      setSize( 400, 130 );  //设置窗口尺寸
	      setVisible(true);  //设置窗口可见
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );  //关闭窗口时退出程序
	   }

	   public static void main(String args[]) {
	      new JColorChooserDemo();
	   }
	}