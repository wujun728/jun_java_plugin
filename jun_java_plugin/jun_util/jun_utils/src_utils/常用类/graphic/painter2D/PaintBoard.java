package book.graphic.painter2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
/**
 * 画板，在画板上画图形，继承Canvas。
 * 提供了撤销和恢复的功能
 */
public class PaintBoard extends Canvas implements java.awt.event.MouseListener,
		java.awt.event.MouseMotionListener {
	// 记录鼠标点击的起始位置，x、y坐标
	int beginX = 0;
	int beginY = 0;
	// 记录鼠标点击的当前位置，x、y坐标
	int currentX = 0;
	int currentY = 0;

	// 表示当前鼠标是否被按下
	boolean bMousePressing = false;
	
	// 保存当前画板中的图形，在撤销和恢复时使用
	java.util.Stack vShapes = new java.util.Stack();
	// 保存已经删除过的图形
	java.util.Stack vDelShapes = new java.util.Stack();
	// 记录当前的命令，是画圆、画线、还是画矩形
	private int command = 0;
	// 画笔的颜色
	private Color color;
	/**
	 * 构造方法
	 */
	public PaintBoard() {
		// 添加事件处理器
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	/**
	 * 撤销操作，取消刚刚画的图形。
	 */
	public void undo() {
		if (vShapes.size() > 0) {
			// 将vShapes中的最后画的一个图形取出
			Object shape = vShapes.pop();
			// 放入到vDelShapes中
			vDelShapes.push(shape);
			// 重画画板
			repaint();
		}
	}
	/**
	 * 恢复操作，取消刚刚撤销的图形
	 */
	public void redo() {
		if (vDelShapes.size() > 0) {
			// 将vDelShapes中最后一个被删除的图形取出
			Object shape = vDelShapes.pop();
			// 放到vShapes中
			vShapes.push(shape);
			// 重画画板
			repaint();
		}
	}
	/**
	 * 设置命令，画线、画圆、画矩形
	 * @param command
	 */
	public void setCommand(int command) {
		this.command = command;
	}
	/**
	 * 设置画笔颜色
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 在java中repaint()是通过两个步骤来实现刷新功能的，首先它调用public void update()来刷新屏幕，
	 * 其次再调用paint(Graphcis g)来重画屏幕，这就容易造成闪烁，特别是一些需要重画背景的程序，
	 * 如果下一桢图象可以完全覆盖上一桢图象的话，便可以重写update函数如下来消除闪烁： 
	 * public void update(Graphics g){ paint(g) }同样调用repaint()重画屏幕。
	 * 或者直接重写不调用repaint，而用Graphics g=getGraphics(); paint(g)；来实现重画屏幕。
	 */
	/**
	 * 重写update方法以消除闪烁。
	 */
	public void update(Graphics g){
		paint(g);
	}
	/**
	 * 画画板
	 */
	public void paint(Graphics g) {
		/**** 画空画板 **/
		// 获得整个画板的区域
		Dimension size = getSize();
		int width = size.width;
		int height = size.height;
		// 设置画板的颜色为白色
		g.setColor(Color.white);
		// 用白色填充画板区域
		g.fillRect(0, 0, width, height);

		/*** 画画板上的图形 ***/
		Shape shape = null;
		// 一次取得画板中保存的图形
		java.util.Enumeration shapes = vShapes.elements();
		while (shapes.hasMoreElements()) {
			shape = (Shape) shapes.nextElement();
			// 调用图形的draw方法
			shape.draw(g);
		}
		
		// 如果当前鼠标还没有松开
		if (bMousePressing) {
			// 设置画笔颜色
			g.setColor(color);
			switch (command) {
			case Command.LINE:
				// 画线，在鼠标起始位置和当前位置之间画一条直线
				g.drawLine(beginX, beginY, currentX, currentY);
				break;
			case Command.RECTANGLE:
				// 画矩形
				if (currentX < beginX) {
					// 如果鼠标当前位置在起始位置的左上方，则以鼠标当前位置为矩形的左上角位置。
					if (currentY < beginY) {
						g.drawRect(currentX, currentY, beginX - currentX, beginY
								- currentY);
					} else {
						// 如果鼠标当前位置在起始位置的左下方，
						// 则以当前位置的横坐标和起始位置的纵坐标为矩形的左上角位置
						g.drawRect(currentX, beginY, beginX - currentX, currentY
								- beginY);
					}
				} else {
					// 如果鼠标当前位置在起始位置的右上方，
					// 则以鼠标起始位置的横坐标和当前的纵坐标为矩形的左上角位置
					if (currentY < beginY) {
						g.drawRect(beginX, currentY, currentX - beginX, beginY
								- currentY);
					} else {
						// 如果鼠标当前位置在起始位置的右下方，则以起始位置为矩形的左上角位置
						g.drawRect(beginX, beginY, currentX - beginX, currentY - beginY);
					}
				}
				break;
			case Command.CIRCLE:
				// 画圆，获取半径。半径等于a*a + b*b的平方根
				int radius = (int) java.lang.Math.sqrt((beginX - currentX)
						* (beginX - currentX) + (beginY - currentY)
						* (beginY - currentY));
				// 画圆弧
				g.drawArc(beginX - radius / 2, beginY - radius / 2, radius, radius, 0, 360);
				break;
			}//End switch(command)
		}

	}
	// MouseListener接口定义的方法，处理鼠标单击事件。
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}
	
	// MouseListener接口定义的方法，处理鼠标按下事件。
	public void mousePressed(MouseEvent e) {
		// 设置鼠标起始位置
		beginX = e.getX();
		beginY = e.getY();
		// 设置bMousePressing为真，表示鼠标按下了。
		bMousePressing = true;
	}
	
	//MouseListener接口定义的方法，处理鼠标松开事件。
	public void mouseReleased(MouseEvent e) {
		// 获取鼠标当前位置
		currentX = e.getX();
		currentY = e.getY();
		// 设置鼠标已经松开了。
		bMousePressing = false;
		
		// 松开鼠标时，将刚刚画的图形保存到vShapes中
		switch (command) {
		case Command.LINE:
			// 新建图形
			Line line = new Line(beginX, beginY, currentX, currentY, color);
			vShapes.push(line);
			break;
		case Command.RECTANGLE:
			Rectangle rectangle = null;
			if (currentX < beginX) {
				if (currentY < beginY) {
					rectangle = new Rectangle(currentX, currentY,
							beginX - currentX, beginY - currentY, color);
				} else {
					rectangle = new Rectangle(currentX, beginY, beginX - currentX,
							currentY - beginY, color);
				}
			} else {
				if (currentY < beginY) {
					rectangle = new Rectangle(beginX, currentY, currentX - beginX,
							beginY - currentY, color);
				} else {
					rectangle = new Rectangle(beginX, beginY, currentX - beginX,
							currentY - beginY, color);
				}
			}
			vShapes.push(rectangle);
			break;
		case Command.CIRCLE:
			int radius = (int) java.lang.Math.sqrt((beginX - currentX)
					* (beginX - currentX) + (beginY - currentY) * (beginY - currentY));
			Circle circle = new Circle(beginX - radius / 2, beginY - radius / 2, radius,
					color);
			vShapes.push(circle);
			break;
		} //End switch(command)
		
		//重画画板
		repaint();
	}
	
	//MouseListener接口定义的方法，处理鼠标移入事件。
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}
	
	//MouseListener接口定义的方法，处理鼠标移出事件。
	public void mouseExited(MouseEvent e) {
		// do nothing
	}
	
	//MouseListener接口定义的方法，处理鼠标按住不放拖动事件。
	public void mouseDragged(MouseEvent e) {
		// 按住鼠标拖动时，不断的获取鼠标当前位置，并重画画板
		currentX = e.getX();
		currentY = e.getY();
		repaint();
	}
	
	//MouseListener接口定义的方法，处理鼠标移动事件。
	public void mouseMoved(MouseEvent e) {
		// do nothing
	}
}