package book.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 制作一个圆形的按钮时，需要做两件事：
 * 第一件事是重载一个适当的绘画方法以画出一个圆形。
 * 第二件事是设置一些事件使得只有当你点击圆形按钮的范围中的时侯按钮才会作出响应
 */
public class CircleButton extends JButton {
	// 构造函数
	public CircleButton(String label) {
		super(label);

		// 获取按钮的最佳大小
		Dimension size = getPreferredSize();
		// 调整按钮大小，使之变成一个方形。
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		//使JButton不画背景，即不显示方形背景，而允许我们画一个圆的背景。
		setContentAreaFilled(false);
	}

	// 画圆的按钮的背景和标签
	protected void paintComponent(Graphics g) {
		// getModel方法返回鼠标的模型ButtonModel。
		// 如果鼠标按下按钮，则ButtonModel的armed属性为真
		if (getModel().isArmed()) {
			// 点击按钮时，用lightGray颜色显示按钮
			g.setColor(Color.lightGray);
		} else {
			// 其他事件用默认的背景色显示按钮
			g.setColor(getBackground());
		}
		// fillOval方法画一个矩形的内切椭圆，并且使用填充这个椭圆，
		// 当矩形为正方形时，画出的椭圆便是圆
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		// 调用父类的paintComponent画按钮的标签和焦点所在的小矩形。
		super.paintComponent(g);
	}

	//用简单的弧画按钮的边界。
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		// drawOval方法画矩形的内切椭圆，但不填充。只画出一个边界
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	//shape对象用于保存按钮的形状，有助于侦听点击按钮事件
	Shape shape;
	
	// 判断鼠标是否点在按钮上
	public boolean contains(int x, int y) {
		//如果按钮边框、位置发生改变，则产生一个新的形状对象。
		if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
			// 构造一个椭圆形对象
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		// 判断鼠标的x、y坐标是否落在按钮形状内。
		return shape.contains(x, y);
	}

	public static void main(String[] args) {
		// 产生一个圆形按钮。    
		JButton button = new CircleButton("Click me");
		// 设置背景颜色为绿色
		button.setBackground(Color.green);

		// 产生一个框架以显示这个按钮。
		JFrame frame = new JFrame("圆形按钮");
		frame.getContentPane().setBackground(Color.yellow);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(button);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * 现在我们需要画一个圆的背景。这是通过重载paintComponent()方法实现的。
	 * 使用Graphics.fillOval()方法画一个实心的圆。
	 * 然后paintComponent()方法调用super.paintComponent()在这个实心圆的上面画了一个标签。
	 * 
	 * 这个例子还重载了paintBorder()方法以在圆形按钮的边界上画一个边。
	 * 如果你不想要边框，你也可以不重载这个方法。
	 * 这个方法调用了Graphics.drawOval()方法以在圆的边界上画一个细的边框。
	 */
}