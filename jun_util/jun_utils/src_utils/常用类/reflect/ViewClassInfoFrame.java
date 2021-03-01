package book.reflect;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * 查看类的信息，在文本框中输入类名，下面的文本域中将显示类的信息，
 * 信息包括类的声明、类的属性、类的构造方法、类的方法。
 */
public class ViewClassInfoFrame extends JFrame implements ActionListener {

	// 输入类名的文本矿
	JTextField classNameField = new JTextField();
	// 查看类信息的按钮
	JButton viewInfoButton = new JButton();
	// 提示输入类名的标签
	JLabel hintLabel = new JLabel();
	// 显示类信息的文本域和滚动面板
	JTextArea infoTextArea = new JTextArea();
	JScrollPane infoScrollPane = new JScrollPane();
	// 一个标题边框，提示结果信息
	TitledBorder titledBorder;

	// 上下两个面板，存放上面的各个组件
	JPanel upPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	// 面板所使用的布局管理器
	BorderLayout mainFrameBorderLayout = new BorderLayout();
	BorderLayout centerPanelBorderLayout = new BorderLayout();
	BorderLayout upPanelBorderLayout = new BorderLayout();

	// 构造方法
	public ViewClassInfoFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		init();
		validate();
	}

	/**
	 * 初始化窗口
	 * @throws Exception
	 */
	private void init() {
		//初始化输入文本框
		classNameField.setFont(new java.awt.Font("Dialog", 0, 15));
		classNameField.setSelectedTextColor(Color.white);
		classNameField.setText("");
		// 初始化按钮和标签
		viewInfoButton.setFont(new java.awt.Font("Dialog", 0, 13));
		viewInfoButton.setText("查看类信息");
		viewInfoButton.addActionListener(this);
		hintLabel.setFont(new java.awt.Font("Dialog", 0, 13));
		hintLabel.setText("请输入完整的类名:");
		// 初始化文本域
		infoTextArea.setFont(new java.awt.Font("Dialog", 0, 14));
		infoTextArea.setEditable(false);
		infoTextArea.setText("");
		// 初始化标题边框
		titledBorder = new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "结果信息");
		infoScrollPane.setBorder(titledBorder);
		infoScrollPane.getViewport().add(infoTextArea, null);
		// 将容器放入面板
		upPanel.setLayout(upPanelBorderLayout);
		centerPanel.setLayout(centerPanelBorderLayout);
		upPanel.add(hintLabel, BorderLayout.NORTH);
		upPanel.add(classNameField, BorderLayout.CENTER);
		upPanel.add(viewInfoButton, BorderLayout.SOUTH);
		centerPanel.add(infoScrollPane);
		
		// 将面板放入窗体
		this.getContentPane().setLayout(mainFrameBorderLayout);
		this.setSize(new Dimension(450, 360));
		this.setTitle("使用反射机制查看Java类的信息");
		
		this.getContentPane().add(upPanel, BorderLayout.NORTH);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);

		this.getRootPane().setDefaultButton(viewInfoButton);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 实现ActionListener接口，处理鼠标事件
	 */
	public void actionPerformed(ActionEvent e) {
		// 从输入文本框中提取类名
		String className = classNameField.getText();
		StringBuffer buf = new StringBuffer();
		try {
			// 动态加载类，获得类的Class对象
			Class c = Class.forName(className);
			buf.append("   /** 类的声明 **/\n");
			buf.append(getClassStatement(c));
			buf.append("\n");
			
			buf.append("   /** 字段 **/\n");
			buf.append(getFields(c));
			
			buf.append("   /**  构造器 **/\n");
			buf.append(getConstructors(c));
			
			buf.append("   /**  方法 **/\n");
			buf.append(getMethods(c));
			buf.append("}\n");
		} catch (Exception et) {
			JOptionPane.showMessageDialog(this, "没找到该类:" + et.getMessage());
		}
		infoTextArea.setText(buf.toString());
	}
	
	/**
	 * 获取类的声明
	 * @param c
	 * @return
	 */
	private String getClassStatement(Class c){

		StringBuffer buf = new StringBuffer();
		
		if (c.getName().equals("java.lang.Object")){
			buf.append("public class Object {");
			return buf.toString();
		} else {
			// 得到该类的父类名
			String superName = c.getSuperclass().getName();
			// 
			buf.append("public class ").append(c.getName());
			buf.append(" extends ").append(superName).append(" {");
		}
		return buf.toString();
	}

	/**
	 * 获取类的属性
	 * @param c
	 * @return
	 */
	private String getFields(Class c) {
		StringBuffer buf = new StringBuffer();
		// 获得类声明的属性
		Field[] fields = c.getDeclaredFields();
		// 遍历属性，提取属性信息
		Field f = null;
		for (int i = 0; i < fields.length; i++) {
			f = fields[i];
			// 获取属性的访问修饰符
			buf.append(Modifier.toString(f.getModifiers())).append(" ");
			// 获取属性的类型
			Class type = f.getType();
			buf.append(type.getName()).append(" ");
			// 获取属性名
			buf.append(f.getName()).append(";\n");
		}
		return buf.toString();
	}

	/**
	 * 获取类的构造方法
	 * @param c
	 * @return
	 */
	private String getConstructors(Class c) {
		StringBuffer buf = new StringBuffer();
		// 获取类的否造方法
		Constructor[] cons = c.getDeclaredConstructors();
		Constructor con = null;
		// 遍历构造方法
		for (int i = 0; i < cons.length; i++) {
			con = cons[i];
			// 获取构造方法的访问修饰符
			buf.append(Modifier.toString(con.getModifiers())).append(" ");
			// 获取构造方法的名字
			buf.append(con.getName()).append("(");
			// 获取构造方法的参数类型
			Class[] paramTypes = con.getParameterTypes();
			for (int j = 0; j < paramTypes.length; j++) {
				if (j == (paramTypes.length - 1)){
					buf.append(paramTypes[j].getName());
				} else {
					buf.append(paramTypes[j].getName()).append(", ");
				}
			}
			buf.append(")");
			
			// 获取方法声明的异常
			Class[] excepTypes = con.getExceptionTypes();
			for (int j = 0; j < excepTypes.length; j++) {
				if (j == 0){
					buf.append(" throws ");
				}
				if (j == excepTypes.length - 1) {
					buf.append(excepTypes[j].getName());
				} else {
					buf.append(excepTypes[j].getName()).append(", ");
				}
			}
			buf.append("\n");
		}
		return buf.toString();
	}

	/**
	 * 获取类声明的方法
	 * @param c
	 * @return
	 */
	private String getMethods(Class c) {
		StringBuffer buf = new StringBuffer();
		// 获得类的所有方法， 注意，不能获得私有方法。
		Method[] methods = c.getMethods();
		// 遍历所有方法
		Method method = null;
		for (int i = 0; i < methods.length; i++) {
			method = methods[i];
			// 获取方法的访问修饰符
			buf.append(Modifier.toString(method.getModifiers())).append(" ");
			// 获取方法的返回类型
			Class returnType = method.getReturnType();
			buf.append(returnType.getName()).append(" ");
			// 获取方法名
			buf.append(method.getName()).append("(");
			
			// 获取方法的参数类型
			Class[] paramTypes = method.getParameterTypes();
			for (int j = 0; j < paramTypes.length; j++) {
				if (j == paramTypes.length - 1) {
					buf.append(paramTypes[j].getName());
				} else {
					buf.append(paramTypes[j].getName()).append(", ");
				}
			}
			buf.append(")");
			
			// 获取方法声明的异常
			Class[] excepTypes = method.getExceptionTypes();
			for (int j = 0; j < excepTypes.length; j++) {
				if (j == 0){
					buf.append(" throws ");
				}
				if (j == excepTypes.length - 1) {
					buf.append(excepTypes[j].getName());
				} else {
					buf.append(excepTypes[j].getName()).append(", ");
				}
			}
			buf.append("\n");
		}
		return buf.toString();
	}
	
	//Main method
	public static void main(String[] args) {
		try {
			//设置界面的外观，为系统外观
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ViewClassInfoFrame frame = new ViewClassInfoFrame();

		//获取屏幕的分辨率，使窗体居中显示
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}
}