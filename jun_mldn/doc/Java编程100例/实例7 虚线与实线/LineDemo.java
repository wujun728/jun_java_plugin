import java.awt.*;
import javax.swing.*;

public class LineDemo extends JFrame{

	public LineDemo(){
		super("实线与虚线"); //调用父类构造函数		
		setSize(300,200); //设置窗口尺寸
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public void paint(Graphics g){ //绘制组件方法
		Graphics2D g2=(Graphics2D)g; //得到2D图形
		Dimension dim = this.getSize(); //得到组件尺寸
    	g2.setColor(Color.white); //设置绘制颜色为白色
    	g2.fillRect(0, 0, dim.width, dim.height); //填充整个组件
    	g2.setColor(Color.black); //设置绘制颜色
		g2.drawLine(40,160,280,160); //绘制实线
		g2.drawLine(40,160,40,40);
		g2.drawString("0",30,165); //绘制字符串
		g2.drawString("100",16,50);
		g2.drawString("200",270,175);
		
		float[] dash={5,5}; //短划线图案
		BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,dash,0.0f); //实例化新画刷
		g2.setStroke(bs); //设置新的画刷		

		g2.drawLine(40,160,100,120); //用新的画刷绘制虚线
		g2.drawLine(100,120,160,120);
		g2.drawLine(160,120,280,40);

	}

	public static void main(String[] args){
		new LineDemo();
	} 
}