package book.graphic.painter2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 2D图形画板的主界面
 */
public class PaintMainFrame extends JFrame {
	// 存放命令的面板，放置画圆、画线等命令的按钮
	JPanel commandPanel = new JPanel();
	// 存放颜色的面板。
	JPanel colorPanel = new JPanel();
	// 存放命令和颜色面板的面板
	JPanel mainPanel = new JPanel();
	
	// 颜色按钮，有红色、绿色、蓝色
	JButton btnRed = new JButton("Red");
	JButton btnGreen = new JButton("Green");
	JButton btnBlue = new JButton("Blue");

	// 命令按钮，画线、画矩形、画圆、撤销、恢复、退出
	JButton btnLine = new JButton("Line");
	JButton btnRectangle = new JButton("Rectangle");
	JButton btnCircle = new JButton("Circle");
	JButton btnUndo = new JButton("Undo");
	JButton btnRedo = new JButton("Redo");
	JButton btnExit = new JButton("Exit");
	
	// 画板，在画板上画图形
	PaintBoard paintBoard = new PaintBoard();

	// 构造方法
	public PaintMainFrame() {
		// 设置布局管理器
		this.getContentPane().setLayout(new BorderLayout());

		// 为命令按钮添加事件处理器
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLine_actionPerformed(e);
			}
		});
		btnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRectangle_actionPerformed(e);
			}
		});
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCircle_actionPerformed(e);
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_actionPerformed(e);
			}
		});
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUndo_actionPerformed(e);
			}
		});
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRedo_actionPerformed(e);
			}
		});
		
		// 设置命令面板的布局管理器
		commandPanel.setLayout(new FlowLayout());
		// 添加命令按钮到命令面板中
		commandPanel.add(btnLine);
		commandPanel.add(btnRectangle);
		commandPanel.add(btnCircle);
		commandPanel.add(btnUndo);
		commandPanel.add(btnRedo);
		commandPanel.add(btnExit);

		// 为颜色按钮添加事件处理器
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRed_actionPerformed(e);
			}
		});
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGreen_actionPerformed(e);
			}
		});
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBlue_actionPerformed(e);
			}
		});

		// 设置颜色面板的布局管理器
		colorPanel.setLayout(new FlowLayout());
		// 添加颜色按钮到颜色面板
		colorPanel.add(btnRed, null);
		colorPanel.add(btnGreen, null);
		colorPanel.add(btnBlue, null);

		// 主面板
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(commandPanel, BorderLayout.NORTH);
		mainPanel.add(colorPanel, BorderLayout.CENTER);

		// 将主面板和画板加到JFrame的容器中
		this.getContentPane().add(mainPanel, BorderLayout.SOUTH);
		this.getContentPane().add(paintBoard, BorderLayout.CENTER);

		// 设置初始的命令为画线，将被选中的命令按钮的前景色用红色标示
		btnLine.setForeground(Color.red);
		paintBoard.setCommand(Command.LINE);
		
		// 设置初始的颜色为画红色，将被选中的颜色按钮的前景色用红色标示
		btnRed.setForeground(Color.red);
		paintBoard.setColor(Color.red);
	}

	/******事件处理****/
	void btnExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	void btnUndo_actionPerformed(ActionEvent e) {
		paintBoard.undo();
	}

	void btnRedo_actionPerformed(ActionEvent e) {
		paintBoard.redo();
	}
	void btnLine_actionPerformed(ActionEvent e) {
		// 如果选中了画线，则将画线的命令按钮前景色用红色标示
		btnLine.setForeground(Color.red);
		// 其他画圆和画矩形按钮前景色用黑色表示
		btnRectangle.setForeground(Color.black);
		btnCircle.setForeground(Color.black);
		paintBoard.setCommand(Command.LINE);
	}

	void btnRectangle_actionPerformed(ActionEvent e) {
		btnLine.setForeground(Color.black);
		btnRectangle.setForeground(Color.red);
		btnCircle.setForeground(Color.black);
		paintBoard.setCommand(Command.RECTANGLE);
	}

	void btnCircle_actionPerformed(ActionEvent e) {
		btnLine.setForeground(Color.black);
		btnRectangle.setForeground(Color.black);
		btnCircle.setForeground(Color.red);
		paintBoard.setCommand(Command.CIRCLE);
	}

	void btnRed_actionPerformed(ActionEvent e) {
		btnRed.setForeground(Color.red);
		btnGreen.setForeground(Color.black);
		btnBlue.setForeground(Color.black);
		paintBoard.setColor(Color.red);
	}

	void btnGreen_actionPerformed(ActionEvent e) {
		btnRed.setForeground(Color.black);
		btnGreen.setForeground(Color.red);
		btnBlue.setForeground(Color.black);
		paintBoard.setColor(Color.green);
	}

	void btnBlue_actionPerformed(ActionEvent e) {
		btnRed.setForeground(Color.black);
		btnGreen.setForeground(Color.black);
		btnBlue.setForeground(Color.red);
		paintBoard.setColor(Color.blue);
	}

	public static void main(String[] args) {
		PaintMainFrame paintApp = new PaintMainFrame();
		paintApp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		paintApp.setSize(600, 500);
		paintApp.setTitle("我的2D画板");
		paintApp.setVisible(true);
	}
}
