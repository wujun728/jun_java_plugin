import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//图片的拖动效果

public class DragPictureDemo extends JFrame {

   JLabel jlPic; //图片，用于拖动

   public DragPictureDemo() {
   	super("图片的拖动效果");  //调用父类构造函数
   	Icon image=new ImageIcon(this.getClass().getResource("1.jpg"));  //实例化图标
		jlPic = new JLabel(image);  //实例化带图片的标签
      getContentPane().add(jlPic);  //增加标签到容器上
      
      DragPicListener listener=new DragPicListener();  //鼠标事件处理
      jlPic.addMouseListener(listener);  //增加标签的事件处理
      jlPic.addMouseMotionListener(listener);       
      
      setSize(300,200);  //设置窗口尺寸
      setVisible(true);  //设置窗口为可视
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
   }
   
   class DragPicListener implements MouseInputListener{  //鼠标事件处理
      Point p=new Point(0,0); //坐标点
   	public void mouseMoved(MouseEvent e){}
   	public void mouseDragged(MouseEvent e){
       	Point newP=SwingUtilities.convertPoint(jlPic,e.getPoint(),jlPic.getParent()); //转换坐标系统
       	jlPic.setLocation(jlPic.getX()+(newP.x-p.x),jlPic.getY()+(newP.y-p.y)); //设置标签的新位置
      	p=newP; //更改坐标点
   	}
   	public void mouseReleased(MouseEvent e){}
   	public void mouseEntered(MouseEvent e){}
   	public void mouseExited(MouseEvent e){}
   	public void mouseClicked(MouseEvent e){}
   	public void mousePressed(MouseEvent e){
       	p=SwingUtilities.convertPoint(jlPic,e.getPoint(),jlPic.getParent()); //得到当前坐标点
   	}   
   }

    public static void main(String[] args) {
		new DragPictureDemo();
   } 
}
