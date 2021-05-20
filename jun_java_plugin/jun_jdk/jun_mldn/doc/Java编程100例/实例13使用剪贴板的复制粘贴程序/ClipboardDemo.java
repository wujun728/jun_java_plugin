import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;

//剪贴板演示

public class ClipboardDemo extends JFrame implements ClipboardOwner{
	Clipboard clipboard;  //剪贴板
	JTextArea jtaCopyTo=new JTextArea(5,10);	//用于拷贝的文本框
	JTextArea jtaPaste=new JTextArea(5,10);	//用于粘贴的文本框
	
	public ClipboardDemo(){
		super("使用剪贴板的复制/粘贴程序");	//调用父类构造函数
				
		clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();	//获得系统剪贴板
		
		JButton btCopy=new JButton("拷贝");	//拷贝按钮
		JButton btPaste=new JButton("粘贴");	//粘贴按钮
		jtaCopyTo.setLineWrap(true);	//设置换行
		jtaPaste.setLineWrap(true);
		jtaCopyTo.setBorder(BorderFactory.createTitledBorder("复制到系统剪切板"));	//设置边界
		jtaPaste.setBorder(BorderFactory.createTitledBorder("从系统剪切板粘贴"));
		
		Container container=getContentPane();	//得到容器
		JToolBar toolBar=new JToolBar();	//实例化工具栏
		toolBar.add(btCopy);	//增加工具栏按钮
		toolBar.add(btPaste);		
		btCopy.addActionListener(new CopyListener());	//按钮事件处理
		btPaste.addActionListener(new PasteListener());		
		Box box=new Box(BoxLayout.X_AXIS);	//实例化Box
		box.add(jtaCopyTo);	//增加文本框到Box上
		box.add(jtaPaste);		
		container.add(toolBar,BorderLayout.NORTH);	//增加工具栏到容器
		container.add(box,BorderLayout.CENTER);	//增加Box到容器
	
		setSize(320,180);	//设置窗口尺寸
		setVisible(true);	//设置窗口为可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//关闭窗口时退出程序
	}
	
	class CopyListener implements ActionListener {	//拷贝数据处理
		public void actionPerformed(ActionEvent event) {
			StringSelection contents=new StringSelection(jtaCopyTo.getText());	//用拷贝文本框文本实例化StringSelection对象
			clipboard.setContents(contents, ClipboardDemo.this);	//设置系统剪贴板内容
		}
	}
	
	class PasteListener implements ActionListener {	//粘贴数据处理
		public void actionPerformed(ActionEvent event) {
			Transferable contents=clipboard.getContents(this);	//得到剪贴板内容
				if(contents!=null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {	//判断内容是否为空,是否为字符串
					try{
						String string= (String) contents.getTransferData(DataFlavor.stringFlavor);	//转换内容到字符串
						jtaPaste.append(string);	//插入字符串到粘贴文本框
					}catch (Exception ex){
						ex.printStackTrace();	//错误处理
					}
			}
		}
	}
	
	public void lostOwnership(Clipboard clip,Transferable transferable) {	//实现ClipboardOwner接口中的方法
	}

	public static void main(String[] args){
		new ClipboardDemo();	
	}
}