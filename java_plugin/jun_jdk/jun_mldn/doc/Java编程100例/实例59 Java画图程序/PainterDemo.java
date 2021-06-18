import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PainterDemo extends JFrame{
	
	JToggleButton[] button=new JToggleButton[3];  //按钮组
	PainterPanel painter=new PainterPanel(); //绘图面板
	
	public PainterDemo(){
		super("Java画图程序"); //调用父类构造函数
		
		String[] buttonName={"直线","椭圆","矩形"}; //按钮文字
		DrawShapeListener buttonListener=new DrawShapeListener(); //按钮事件

		JToolBar toolBar=new JToolBar(); //实例化工具栏
		ButtonGroup buttonGroup=new ButtonGroup(); //实例化按钮组
		for (int i=0;i<button.length;i++){
			button[i]=new JToggleButton(buttonName[i]); //实例化按钮
			button[i].addActionListener(buttonListener); //增加按钮事件处理
			buttonGroup.add(button[i]); //增加按钮到按钮组
			toolBar.add(button[i]);	 //增加按钮到工具栏
		}

		Container container=getContentPane(); //得到窗口容器
		container.add(toolBar,BorderLayout.NORTH); //增加组件到容器上
		container.add(painter,BorderLayout.CENTER);			
		
		setSize(300,200);  //设置窗口尺寸
		setVisible(true);  //设置窗口为可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序		
	}
	
	class DrawShapeListener implements ActionListener{  //按钮事件处理
		public void actionPerformed(ActionEvent e){
			for (int i=0;i<button.length;i++){ 
				if (e.getSource()==button[i]){  //判断来自于哪个按钮
					painter.drawShape(i); //绘制图形
				}				
			}			
		}
	}
			
	public static void main(String[] args){
		new PainterDemo();
	}
}
