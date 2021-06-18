import java.awt.*;

import javax.swing.*;


public class RoundButton extends JButton {

    public RoundButton(String label) {
        super(label);  //调用父类构造函数
        setContentAreaFilled(false);   //不自行绘制按钮背景
    }


    //绘制圆和标签
    protected void paintComponent(Graphics g) {
       if (getModel().isArmed()) {  //鼠标点击时
            g.setColor(Color.lightGray);  //颜色为灰色
        } else {
            g.setColor(getBackground());  //置按钮颜色
        }
        g.fillOval(0, 0, getSize().width, getSize().height);  //绘制圆
        super.paintComponent(g);  //调用父类函数,绘制其余部分
    }


    //绘制边框
   protected void paintBorder(Graphics g) {
       g.setColor(getForeground());  //设置边框颜色
       g.drawOval(0, 0, getSize().width-1, getSize().height-1);  //在边界上绘制一个椭圆
   }

}

