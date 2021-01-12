import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//滑杆演示

public class JSliderDemo extends JFrame {
   JSlider xSlider;  //用于改变椭圆宽度值
   JSlider ySlider;  //且于改变椭圆高度值
   GraphPanel panel; //绘圆的面板

   public JSliderDemo() 
   {
      super("滑杆演示");  //调用父类构造函数
      panel=new GraphPanel();  //实例化面板
      panel.setBackground(Color.orange); //设置面板背景色为橙色

      xSlider=new JSlider( SwingConstants.HORIZONTAL,0,200,10); //实例化滑杆
      xSlider.setMajorTickSpacing(10); //设置刻度值
      xSlider.setPaintTicks(true); //描绘刻度
      ySlider=new JSlider( SwingConstants.VERTICAL,0,200,10); 
      ySlider.setMajorTickSpacing(10);
      ySlider.setPaintTicks(true);
      ySlider.setInverted(true);  //设置拖动方向
      ValueChangeListener myListener=new ValueChangeListener(); //实例化滑杆事件处理
      xSlider.addChangeListener(myListener); //增加滑杆的事件处理
      ySlider.addChangeListener(myListener);

      Container container=getContentPane(); //得到容器
      container.add(xSlider,BorderLayout.SOUTH); //增加组件到容器上
      container.add(ySlider,BorderLayout.EAST);
      container.add(panel,BorderLayout.CENTER);

      setSize(220,200);  //设置窗口尺寸
      setVisible(true);  //设置窗口为可视
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );  //关闭窗口时退出程序
   }
   
   class ValueChangeListener implements ChangeListener{  //事件处理
   	  public void stateChanged(ChangeEvent e){
   	  	if (e.getSource()==xSlider){ //判断事件源
            panel.setW(xSlider.getValue()); //设置椭圆宽度
        }
        else{
        	panel.setH(ySlider.getValue()); //设置椭圆高度
        }
      }
   }

   public static void main(String args[]) {
      new JSliderDemo();
   }

}  