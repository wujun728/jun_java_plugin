import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//控件的相互控制与消息传递

public class ActionDemo extends JFrame{
	JTextField jtfName;  //名字输入域
	JTextArea jtaChat;  //显示聊天信息
	JTextArea jtaInput;  //输入消息
	JButton jbSend;  //发送消息按钮
	JButton jbClear;  //清除输入消息域

	public ActionDemo(){
		super("控件的相互控制");  //调用父类构造函数

		Container container=this.getContentPane();  //得到容器
		JPanel p=new JPanel();  //初始化一个面板
		jtfName=new JTextField(10);  //初始化名字输入域
		p.add(new JLabel("主题：Write Once, Run Anywhere.     "));  //增加聊天题标签
		p.add(new JLabel("昵称"));  //增加昵称标签
		p.add(jtfName);  //增加名字输入域
      container.add(p,BorderLayout.NORTH);  //在容器上增加面板

      jtaChat=new JTextArea();  //初始化信息显示文本框
   	container.add(new JScrollPane(jtaChat),BorderLayout.CENTER);  //在容器上增加信息显示文本框

		Box box=new Box(BoxLayout.X_AXIS);  //初始化一个Box
		jtaInput=new JTextArea(3,20);  //初始化消息输入域
		jbSend=new JButton();  //初始化发送按钮
		jbClear=new JButton();   //初始化清除按钮
		jbClear.setText("清除");  //设置按钮文本
		box.add(new JScrollPane(jtaInput));  //增加消息输入域
		box.add(jbClear);  //增加按钮
		box.add(jbSend);
		container.add(box,BorderLayout.SOUTH);  //在容器上增加box

		jbClear.addActionListener(new ActionListener(){  //清除按钮事件处理
      	public void actionPerformed(ActionEvent e){
      		jtaInput.setText("");  //点击清除按钮时清除输入域的信息
         }
      });

	   Action sendMessage = new AbstractAction() {  //发送消息Action
	   	public void actionPerformed(ActionEvent e){
	   		replaceMessage();  //更新消息显示框
	    	}
		};
		jtaInput.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"send");  //键盘事件处理,按受回车事件
		jtaInput.getActionMap().put("send",sendMessage);  //回车时的处理(调用发送消息Action)

		jbSend.setAction(sendMessage);  //设置命令为发送消息
		jbSend.setText("发送");  //设置按钮文本

	   setSize(400,200);  //设置窗口尺寸
	   setVisible(true);  //设置窗口可视
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
	}

	private void replaceMessage(){
	   String message=jtfName.getText()+"> "+jtaInput.getText()+"\n";  //得到消息文本
	   jtaChat.insert(message,jtaChat.getDocument().getLength());  //插入消息到显示域未端
	   jtaInput.setText("");  //清空输入消息域
	}

   public static void main(String[] args){
      new ActionDemo();
   }
}