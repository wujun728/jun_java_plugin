import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

// 常用图形的绘制与填充
public class GraphicsShapeDemo extends JFrame { //主窗口类

	GraphicsShapeDemo() {
		super("常用图形的绘制与填充"); //调用父类构造器设置窗口标题栏
		DrawPanel drawPanel = new DrawPanel(); //创建DrawPanel对象用于绘制图形
		Container content = getContentPane(); //获得窗口的内容窗格
		content.add(drawPanel, BorderLayout.CENTER); //把对象drawPanel加入内容窗格
		setSize(400, 300); //设置窗口大小
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	public static void main(String[] args) {
		new GraphicsShapeDemo(); //创建GraphicsShapeDemo对象
	}
	
	//显示图形的面板
	class DrawPanel extends JPanel {

		//重载paintComponent()方法
		public void paintComponent(Graphics g) {
	
			super.paintComponent(g); //调用父类的绘制组件方法
			Graphics2D g2D = (Graphics2D)g;
			setBackground(Color.white);
			setForeground(Color.black);
			
			int charH = 16; //最大字符高度		
			int gridW = getWidth() / 5; //绘图网格宽度
			int gridH = getHeight() / 4; //绘图网格高度
			int posX = 2; //各图形绘制位置的x坐标
			int posY = 2; //各图形位置的y坐标
			int strY = gridH - 7; //字符串绘制位置的y坐标
			int w = gridW - 2 * posX; //图形的宽度
			int h = strY - charH - posY; //图形的高度
			int cirlceD = Math.min(w, h); //圆的直径
			
			Shape[][] shape = new Shape[2][5];			
			shape[0][0] = new Line2D.Float(0,0,w,h); //直线
			shape[0][1] = new Rectangle2D.Double(0, 0, w, h); //矩形
			shape[0][2] = new RoundRectangle2D.Float(0, 0, w, h,20,20); //圆角矩形
			shape[0][3] = new Ellipse2D.Float(0,0,cirlceD, cirlceD); //圆
			shape[0][4] = new Ellipse2D.Float(0, 0, w, h); //椭圆
			shape[1][0] = new Arc2D.Float(0,0,w,h,45,225, Arc2D.OPEN); //开弧
			shape[1][1] = new Arc2D.Float(0,0,w,h,45,225, Arc2D.CHORD); //弓形
			shape[1][2] = new Arc2D.Float(0,0,w,h,45,225, Arc2D.PIE); //饼形
			shape[1][3] = new QuadCurve2D.Double(0,0,w,h/6,w,h); //二次曲线
			shape[1][4] = new CubicCurve2D.Double(0,0,w/2,h,w, h/2,w,h); //三次曲线
			
			//绘制几何图形的名称
			String[][] shapeName = {{"直线","矩形","圆角矩形","圆","椭圆"},
				  {"开弧","弓形","饼形","二次曲线","三次曲线"}};

			AffineTransform defaultAT = g2D.getTransform();

			for(int i=0; i<shapeName.length; i++) {
				for(int j=0; j<shape[i].length; j++) {
					g2D.setColor(Color.black);
					g2D.translate(posX,posY);  //坐标平移
					g2D.draw(shape[i][j]);
					g2D.setColor(Color.blue);
					g2D.drawString(shapeName[i][j], 2, strY); //绘制说明文字
					g2D.setTransform(defaultAT);
					posY += gridH;

					g2D.translate(posX,posY);
					g2D.setColor(Color.pink);
					g2D.fill(shape[i][j]);
					g2D.setColor(Color.blue);
					g2D.drawString("填充"+shapeName[i][j], 2, strY); //文字
					g2D.setTransform(defaultAT);
					posX += gridW;
					posY -= gridH;
				}
				posX = 2;
				posY += 2*gridH;
			}
		}
	}
}
