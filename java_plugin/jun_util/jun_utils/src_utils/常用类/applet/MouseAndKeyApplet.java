package book.applet;

import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;
/**
 * 本实例介绍Applet中的鼠标和键盘操作
 */
public class MouseAndKeyApplet extends Applet {

	// 鼠标在进行各种操作时的信息
	String mouseDownStr = null;
	String mouseUpStr = null;
	String mouseDragStr = null;

	// 键盘在进行各种操作时的信息
	String keyUpStr = null;
	String keyDownStr = null;
	String keyboardStateStr = null;

	// 矩形按钮的坐标和长度、宽度
	int button_X;
	int button_Y;
	int buttonHeight;
	int buttonWidth;

	//初始化
	public void init() {
		button_X = 100;
		button_Y = 80;
		buttonHeight = 35;
		buttonWidth = 60;
		
		clearInfos();
	}
	
	public void start(){
	}
	public void stop(){
	}
	public void destroy(){
	}
	// 清空各类信息
	public void clearInfos(){
		mouseDownStr = "";
		mouseUpStr = "";
		mouseDragStr = "";
		keyDownStr = "";
		keyUpStr = "";
		keyboardStateStr = "";
	}
	
	public void paint(Graphics g) {
		// 在Applet上画字符串
		g.drawString("MouseInfo", 20, 20);
		g.drawString("KeyInfo", 200, 20);
		if (mouseDownStr != null){
			g.drawString(mouseDownStr, 20, 50);
		}
		if (mouseUpStr != null){
			g.drawString(mouseUpStr, 20, 50);
		}
		if (mouseDragStr != null){
			g.drawString(mouseDragStr, 20, 50);
		}
		if (keyUpStr != null){
			g.drawString(keyUpStr, 200, 50);
		}
		if (keyDownStr != null){
			g.drawString(keyDownStr, 200, 50);
		}

		clearInfos();

		// 画一个矩形按钮
		g.drawRect(button_X, button_Y, buttonWidth, buttonHeight);
		// 画矩形上的标签
		g.drawString("EXIT", button_X + 15, button_Y + 20);
	}

	/**
	 * 鼠标松开事件，x、y为鼠标的坐标
	 */
	public boolean mouseUp(Event event, int x, int y) {
		mouseUpStr = "mouseUp: " + x + ", " + y;
		repaint();
		// 判断鼠标位置是否在按钮上
		if ((x >= button_X) && (x <= button_X + buttonWidth)
				&& (y >= button_Y) && (y <= button_Y + buttonHeight)) {
			mouseUpStr = "EXIT Button Selected";
			repaint();
		}
		return true;
	}

	/**
	 * 鼠标按下事件
	 */
	public boolean mouseDown(Event event, int x, int y) {
		mouseDownStr = "mouseDown: " + x + ", " + y;
		repaint();
		// 判断鼠标位置是否在按钮上
		if ((x >= button_X) && (x <= button_X + buttonWidth)
				&& (y >= button_Y) && (y <= button_Y + buttonHeight)) {
			mouseDownStr = "EXIT Button Selected";
			repaint();
		}
		return true;
	}

	/**
	 * 鼠标拖动事件
	 */
	public boolean mouseDrag(Event event, int x, int y) {
		mouseDragStr = "mouseDrag: " + x + ", " + y;
		repaint();
		if ((x >= button_X) && (x <= button_X + buttonWidth)
				&& (y >= button_Y) && (y <= button_Y + buttonHeight)) {
			mouseDragStr = "EXIT Button Selected";
			repaint();
		}

		return true;
	}

	/**
	 * 键盘松开事件
	 */
	public boolean keyUp(Event event, int letter) {
		// 判断组合键
		if ((event.modifiers & Event.SHIFT_MASK) != 0){
			keyboardStateStr += "Shift ";
		}
		if ((event.modifiers & Event.CTRL_MASK) != 0){
			keyboardStateStr += "Ctrl ";
		}
		if ((event.modifiers & Event.ALT_MASK) != 0){
			keyboardStateStr += "Alt ";
		}
		// 判断功能键
		if (event.id == Event.KEY_ACTION) {//has problem in this line?
			keyUpStr = "Function key released";
		}else {
			// 普通键
			if (letter == 27){ //ESC key
				keyUpStr = keyboardStateStr + "Esc key released";
			} else {
				keyUpStr = "KeyUp: " + keyboardStateStr + (char) letter;
			}
		}
		repaint();
		return true;
	}

	/**
	 * 键盘按下事件
	 */
	public boolean keyDown(Event event, int letter) {
		if (event.id == Event.KEY_ACTION){
			keyDownStr = "Function key pressed";
		} else {
			if (letter == 27){ //ESC key
				keyDownStr = "Esc key pressed";
			} else {
				keyDownStr = "KeyDown: " + (char) letter;
			}
		}
		repaint();
		return true;
	}
}