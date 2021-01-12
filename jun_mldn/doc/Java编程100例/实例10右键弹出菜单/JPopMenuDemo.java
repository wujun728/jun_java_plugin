import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//左键弹出菜单

public class JPopMenuDemo extends JFrame {
   JRadioButtonMenuItem items[]; //菜单项
   Color[] colors={Color.blue,Color.pink,Color.yellow,Color.red,Color.orange}; //颜色数组
   JPopupMenu popupMenu; //弹出菜单

   public JPopMenuDemo()
   {
      super( "右键弹出菜单" ); //调用父类构造函数

      ChangeColorAction action = new ChangeColorAction(); //菜单项事件处理
      String[] str = {"Blue","Pink","Yellow","Red","Orange"}; //菜单项名称
      ButtonGroup colorGroup=new ButtonGroup(); //实例化按钮组
      popupMenu=new JPopupMenu(); //实例化弹出菜单
      items=new JRadioButtonMenuItem[5]; //初始化数组
      for (int i=0;i<items.length;i++) { 
         items[i]=new JRadioButtonMenuItem(str[i]); //实例化菜单项
         popupMenu.add(items[i]); //增加菜单项到菜单上
         colorGroup.add(items[i]); //增加菜单项到按钮组
        items[i].addActionListener(action); //菜单项事件处理
      }     

      addMouseListener(new MouseAdapter(){  //窗口的鼠标事件处理
        public void mousePressed( MouseEvent event ) {  //点击鼠标
           triggerEvent(event);  //调用triggerEvent方法处理事件
        } 

        public void mouseReleased( MouseEvent event ) { //释放鼠标
           triggerEvent(event); 
        } 

        private void triggerEvent(MouseEvent event) { //处理事件
           if (event.isPopupTrigger()) //如果是弹出菜单事件(根据平台不同可能不同)
              popupMenu.show(event.getComponent(),event.getX(),event.getY());  //显示菜单
        }
    }); 

	getContentPane().setBackground(Color.white); //窗口的默认背景色为白色
    setSize(230,160); //设置窗口大小
    setVisible(true); //设置窗口为可视
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE ); //关闭窗口时退出程序
   }

   class ChangeColorAction implements ActionListener { //菜单项事件处理
      public void actionPerformed(ActionEvent event)   {
         for (int i=0;i<items.length;i++)
            if (event.getSource()==items[i]) { //判断事件来自于哪个菜单项
               getContentPane().setBackground(colors[i]); //设置窗口背景
               repaint(); //重绘窗口
               return;
         }
      }
   }  
   
   public static void main( String args[])   {
      new JPopMenuDemo();      
   }
} 

