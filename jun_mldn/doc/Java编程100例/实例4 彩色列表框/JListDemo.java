import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

//彩色列表框示例

public class JListDemo extends JFrame{
	Container container;  //容器
	JTextField selectedText;  //文本域,反映选择的颜色值
	JList list;  //列表框
	JPanel selectedColor;  //Panel,以选择的颜色为背景绘制

   public JListDemo(){  //构造函数
   	container=getContentPane();  //得到容器
   	container.setLayout(new BorderLayout());  //设置布局管理器,不是必须的,Container默认为BorderLayout

   	Color[] colors={Color.orange,Color.pink,Color.red,Color.black,Color.blue,Color.cyan,Color.green,Color.lightGray};  //列表框内容
   	list=new JList(colors);
		JScrollPane scrollPane = new JScrollPane(list);  //以list初始化滚动窗格
		selectedText=new JTextField(20);
		selectedColor=new JPanel();
		selectedColor.setPreferredSize(new Dimension(20,20));  //设置panel的首选尺寸
		container.add(selectedText,BorderLayout.NORTH);  //增加组件到容器上
   	container.add(scrollPane,BorderLayout.CENTER);
   	container.add(selectedColor,BorderLayout.SOUTH);

      list.setCellRenderer(new ColorRenderer());  //设置Renderer
      list.addListSelectionListener(  //事件处理
      	new ListSelectionListener(){
      	   public void valueChanged(ListSelectionEvent event){  //选择值有改变
      	   	Color c=(Color)list.getSelectedValue();  //得到选择的颜色
      	      selectedText.setText("选择颜色："+" R="+c.getRed()+" G ="+c.getGreen()+" B="+c.getBlue());  //设置文本域文本
      	      selectedColor.setBackground(c);  //设置panel的颜色
      	   }
      });

   	setSize(300,200);  //设置窗口尺寸
   	setVisible(true);  //设置窗口可视
   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
   }


   public static void main(String[] args){
      new JListDemo();
   }
}