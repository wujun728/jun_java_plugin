package book.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * 使用对话框。
 * 功能介绍：界面包括一个文本域，一个按钮，点击按钮弹出一个对话框，
 * 在对话框中输入的字符串将在文本域中显示。
 */
public class DialogWindow extends JFrame implements ActionListener {

	private SimpleDialog dialog;
	private JTextArea textArea;
	//文本域中行之间的分隔符
	String lineSeparator;

	public DialogWindow() {
		super("对话框示例");

		//添加一个不可修改的文本域，能显示5行30个字符的内容。
		textArea = new JTextArea(5, 30);
		textArea.setEditable(false);
		getContentPane().add("Center", new JScrollPane(textArea));
		
		//添加一个按钮，点击按钮弹出对话框
		JButton button = new JButton("添加内容");
		button.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(button);
		getContentPane().add("South", panel);
		//获取文本域中行之间的分隔符。这里调用了系统的属性
		lineSeparator = System.getProperty("line.separator");

		//调整窗体布局大小
		this.pack();
	}

	public void actionPerformed(ActionEvent event) {
		//点击按钮时显示对话框
		if (dialog == null) {
			dialog = new SimpleDialog(this, "输入对话框");
		}
		dialog.setVisible(true);
	}

	/**
	 * 添加内容到文本域的后面，每次都新起一行。
	 */
	public void setText(String text) {
		textArea.append(text + lineSeparator);
	}

	public static void main(String args[]) {
		DialogWindow window = new DialogWindow();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

/**
 * 自定义对话框
 * 对话框包括一个label、一个文本框和2个按钮。
 */
class SimpleDialog extends JDialog implements ActionListener {

	//文本框，用于输入字符串
	JTextField field;
	//对话框的父窗体。
	DialogWindow parent;
	//“确定”按钮
	JButton setButton;
	
	/**
	 * 构造函数，参数为父窗体和对话框的标题
	 */
	SimpleDialog(JFrame prentFrame, String title) {
		//调用父类的构造函数，
		//第三个参数用false表示允许激活其他窗体。为true表示不能够激活其他窗体
		super(prentFrame, title, false);
		parent = (DialogWindow) prentFrame;

		//添加Label和输入文本框
		JPanel p1 = new JPanel();
		JLabel label = new JLabel("请输入要添加的文本:");
		p1.add(label);
		field = new JTextField(30);
		field.addActionListener(this);
		p1.add(field);
		getContentPane().add("Center", p1);
		
		//添加确定和取消按钮
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton cancelButton = new JButton("取 消");
		cancelButton.addActionListener(this);
		setButton = new JButton("确 定");
		setButton.addActionListener(this);
		p2.add(setButton);
		p2.add(cancelButton);
		getContentPane().add("South", p2);

		//调整对话框布局大小
		pack();
	}
	/**
	 * 事件处理
	 */
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();
		if ((source == setButton)) {
			//如果确定按钮被按下，则将文本矿的文本添加到父窗体的文本域中
			parent.setText(field.getText());
		}
		field.selectAll();
		//隐藏对话框
		setVisible(false);
	}
}