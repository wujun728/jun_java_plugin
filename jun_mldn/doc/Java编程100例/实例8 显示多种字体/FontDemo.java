import java.awt.*;
import javax.swing.*;

//显示多种字体,用JLabel实现

public class FontDemo extends JFrame {

   public FontDemo()
   {
      super("显示多种字体");  //调用父类构造函数

   	  Font[] fonts={new Font("Serif",Font.BOLD,12),
   	                new Font("Monospaced",Font.ITALIC,24),
   	                new Font("宋体",Font.PLAIN,18),
   	                new Font("黑体",Font.PLAIN,20),
   	                new Font("Serif",Font.BOLD + Font.ITALIC,18 )
   	  };  //字体数组
   	  String[] text={"Font Demo","Monospaced,斜体,24号","宋体字示例","黑体","Serif，粗体，斜体，18号"};  //显示的文本

      Container container=getContentPane();  //得到容器
      Box boxLayout=Box.createVerticalBox();  //创建一个垂直排列的Box
      boxLayout.setBorder(BorderFactory.createEmptyBorder(10,20,5,5));  //设置边界
      container.add(boxLayout);  //增加组件到容器上
      for (int i=0;i<5;i++){
      	JLabel fontLabel=new JLabel();  //得到一个JLabel的实例
      	fontLabel.setFont(fonts[i]);  //设置字体
      	fontLabel.setText(text[i]);  //设置显示文本
      	boxLayout.add(fontLabel);  //增加组件到Box上
      }

      setSize(380,180);  //设置窗口尺寸
      setVisible(true);  //设置窗口可ub视
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
   }


   public static void main(String args[]){
      new FontDemo();
   }
}