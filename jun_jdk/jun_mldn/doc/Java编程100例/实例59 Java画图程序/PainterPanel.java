import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PainterPanel extends JPanel implements MouseListener{
	int shape=-1; //图案类型
	Point[] point=new Point[2]; //记录鼠标拖动的起始点和终点

	public PainterPanel(){
		super();	//调用父类构造函数		
		this.setBackground(Color.white); //设置背景颜色
	   point[0]=new Point(-1,-1); //初始化变量
	   point[1]=new Point(-1,-1);
		addMouseListener(this); //增加鼠标事件
	}
	

	public void mouseReleased(MouseEvent e){ //鼠标释放事件
		point[1]=new Point(e.getX(),e.getY());	 //设置终点位置
		repaint(); //重绘屏幕
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){  //鼠标按下时事件
		point[0]=new Point(e.getX(),e.getY());  //设置起始点位置
	}   

	
	public void paint(Graphics g){	
		super.paint(g);
		switch (shape){  //根据shape值绘制图形
			case 0:
				g.drawLine(point[0].x,point[0].y,point[1].x,point[1].y); //绘线
				break;
			case 1:
				int width=point[1].x-point[0].x;
				int height=point[1].y-point[0].y;
				g.drawOval(point[0].x,point[0].y,width,height); //绘椭圆
				break;
			case 2:
				width=point[1].x-point[0].x;
				height=point[1].y-point[0].y;
				g.drawRect(point[0].x,point[0].y,width,height);  //绘矩形
				break;	
		}		
	}
	
	public void drawShape(int shape){
		this.shape=shape;		
	}
}
