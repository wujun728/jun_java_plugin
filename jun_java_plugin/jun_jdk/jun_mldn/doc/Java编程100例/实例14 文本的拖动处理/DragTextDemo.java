import java.awt.*;
import javax.swing.*;
import javax.swing.JSplitPane;
import java.awt.dnd.*;

//文本的拖动处理

public class DragTextDemo extends JFrame{
	
	public DragTextDemo(){
		super("文本的拖动处理"); //调用父类构造函数
		
		String[] data = {"one", "two", "three", "four"}; //字符数组,用于构造列表框		
		DragList list=new DragList(data); //列表框实例
		JTextArea jta=new JTextArea(8,20); //文本框实例
		DragLabel label=new DragLabel("拖动目标"); //标签实例
		
		jta.setLineWrap(true); //设置自动换行
		jta.setDragEnabled(true); //文本框可拖动
		new DropTarget(label,DnDConstants.ACTION_COPY,label); //实例化拖动目标为标签
		
		Container container=getContentPane(); //得到容器
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); //实例化分隔面板
		split.setDividerLocation(140); //设置分隔位置
		split.add(list); //增加组件到分隔面板
		split.add(jta);
		container.add(split,BorderLayout.CENTER);  //增加组件到容器上		
		container.add(label,BorderLayout.SOUTH);
		
		setSize(300,150);  //设置窗口尺寸
		setVisible(true);  //设置窗口为可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}
	
	public static void main(String[] args){
		new DragTextDemo();
	}
}
