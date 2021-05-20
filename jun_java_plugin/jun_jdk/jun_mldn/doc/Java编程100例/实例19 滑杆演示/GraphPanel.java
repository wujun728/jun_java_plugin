import java.awt.*;
import javax.swing.*;

public class GraphPanel extends JPanel {
   int width=10;  //椭圆高度值
   int hight=10;  //椭圆宽度值

   public void paintComponent(Graphics g) {
      super.paintComponent(g);  //调用父类方法
      g.fillOval(5,5,width,hight);  //绘制椭圆
   }

   public void setW(int length){ //设置宽度
      width=(length>=0?length:10 ); 
      repaint(); //重绘组件
   }
   
   public void setH(int length){ //设置高度
      hight=(length>=0?length:10 );  
      repaint();  //重绘组件
   }   
}  