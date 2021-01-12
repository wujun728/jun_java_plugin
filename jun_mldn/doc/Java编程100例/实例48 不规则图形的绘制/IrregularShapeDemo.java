import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

//不规则图形的绘制

public class IrregularShapeDemo extends JFrame {
	
	GeneralPath gPath= new GeneralPath(); //GeneralPath对象实例
	Point aPoint; 
	
    //构造函数
    public IrregularShapeDemo() {
		super("不规则图形的绘制"); //调用父类构造函数
		enableEvents(AWTEvent.MOUSE_EVENT_MASK|AWTEvent.MOUSE_MOTION_EVENT_MASK); //允许事件
		
		setSize(300, 200); //设置窗口尺寸
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}

	public void paint(Graphics g) { //重载窗口组件的paint()方法
		Graphics2D g2D = (Graphics2D)g;	//获取图形环境
		g2D.draw(gPath); //绘制路径
	}

	public static void main(String[] args) {
		new IrregularShapeDemo();
	}
	
	protected void processMouseEvent(MouseEvent e) { //鼠标事件处理
		if(e.getID() == MouseEvent.MOUSE_PRESSED) {
			aPoint = e.getPoint(); //得到当前鼠标点
			gPath = new GeneralPath(); //重新实例化GeneralPath对象
			gPath.moveTo(aPoint.x,aPoint.y); //设置路径点
		}
	}
		
	protected void processMouseMotionEvent(MouseEvent e) { //鼠标运动事件处理
		if(e.getID() == MouseEvent.MOUSE_DRAGGED) {
			aPoint = e.getPoint(); //得到当前鼠标点
			gPath.lineTo(aPoint.x, aPoint.y); //设置路径
			gPath.moveTo(aPoint.x, aPoint.y);
			repaint(); //重绘组件
		}
	}	
}
