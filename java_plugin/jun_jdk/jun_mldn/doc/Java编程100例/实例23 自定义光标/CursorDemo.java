import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//自定义光标

public class CursorDemo extends JFrame{
	
	public CursorDemo(){
		super("自定义光标");  //调用父类构造函数		
		int[] cursor={Cursor.DEFAULT_CURSOR,Cursor.HAND_CURSOR,Cursor.MOVE_CURSOR,Cursor.N_RESIZE_CURSOR,Cursor.W_RESIZE_CURSOR,Cursor.WAIT_CURSOR,Cursor.TEXT_CURSOR};	 //预定义光标数组	
		
		Container container=getContentPane(); //得到容器
		container.setLayout(new FlowLayout()); //设置容器布局管理器
		for (int i=0;i<cursor.length;i++){ 
			JTextArea jta=new JTextArea(3,6); //实例化一个文本框对象
			jta.setCursor(Cursor.getPredefinedCursor(cursor[i])); //设置文本框的光标
			container.add(jta); //增加组件到容器上
		}
				
		Toolkit toolkit=Toolkit.getDefaultToolkit(); //得到默认的ToolKit对象
		Image image=toolkit.getImage("cursor.gif"); //得到图像
		Cursor customCursor=toolkit.createCustomCursor(image,new Point(6,6),"MyCursor"); //实例化自定义光标对象
		JTextArea jta=new JTextArea(3,6); //实例化文本框
		jta.setCursor(customCursor);  //设置文本框光标
		container.add(jta);	 //增加组件
		 
		setSize(300,160); //设置窗口大小
		setVisible(true);  //窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	
	public static void main(String[] args){
		new CursorDemo();
	}
}