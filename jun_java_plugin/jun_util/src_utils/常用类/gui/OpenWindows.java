package book.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 开窗户游戏。当你点击游戏界面中的一个正方形按钮时，
 * 它周围的正方形按钮的颜色就会发生变化，当全部正方形按钮的颜色变为一色时，你就胜利了
 */
public class OpenWindows {
	public static void main(String[] args) {
		JFrame frame = new JFrame("开窗户游戏");
		frame.getContentPane().add(new MainPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

/**
 * 主面板
 */
class MainPanel extends JPanel {
	SquarePanel pc = new SquarePanel();
	ControlPanel ps = new ControlPanel(pc);
	public MainPanel() {
		this.setLayout(new BorderLayout());
		this.add(pc, "Center");
		this.add(ps, "South");
	}
}

/**
 * 方块的面板
 */
class SquarePanel extends JPanel {
	//包括25个窗户按钮
	JButton[] winbutton = new JButton[25];
	Color c;
	public SquarePanel() {
		//面板采用网格布局管理器
		this.setLayout(new GridLayout(5, 5));
		for (int i = 0; i < 25; i++) {
			winbutton[i] = new JButton();
			winbutton[i].setActionCommand(String.valueOf(i));
			// 获得默认颜色
			c = winbutton[i].getBackground(); 
			winbutton[i].addActionListener(new OpenWindowListener());
			this.add(winbutton[i]);
		}
		//面板大小值为300*300
		this.setPreferredSize(new Dimension(300, 300));
	}

	/**
	 * 单击一个窗户按钮时引起开窗户事件
	 */
	class OpenWindowListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			//获取被点击窗户的ID
			int x = Integer.parseInt(a.getActionCommand());
			//设置被点击窗户被选。
			select(x);
			//判断是否胜利
			isWin();
		}

		// 当一个窗户被选中时进行的操作。需要改变周围窗户的颜色。
		private void select(int x) {
			if (x == 0) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			} else if (x > 0 && x < 4) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			} else if (x == 4) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 5]);
			} else if (x == 20) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x + 1]);
			} else if (x == 24) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
			} else if (x > 20 && x < 24) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 1]);
			} else if (x % 5 == 0) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			} else if (x % 5 == 4) {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 5]);
			} else {
				changeColor(winbutton[x]);
				changeColor(winbutton[x - 5]);
				changeColor(winbutton[x - 1]);
				changeColor(winbutton[x + 1]);
				changeColor(winbutton[x + 5]);
			}
		}

		// 改变周围颜色函数。
		private void changeColor(JButton winbutton) {
			//如果窗户的颜色是初始颜色，则变成白色。
			if (winbutton.getBackground() == c){
				winbutton.setBackground(Color.white);
			} else {
				//如果窗户的颜色不是初始颜色，则变成初始颜色
				winbutton.setBackground(c);
			}
		}

		// 判断是否胜出
		private void isWin() {
			int a = 1;
			//当25个窗户都变成白色时，获胜
			for (int i = 0; i < 25; i++){
				if (winbutton[i].getBackground() == Color.white){
					a++;
				}
			}
			if (a > 25){
				JOptionPane.showMessageDialog(null, "恭喜过关");
			}
		}
	}
}

/**
 * 控制面板
 */
class ControlPanel extends JPanel {
	JLabel label = new JLabel("开窗户游戏");
	//游戏重新开始按钮
	JButton restart = new JButton("重置");
	SquarePanel pc;
	/**
	 * 构造函数，参数为待控制的窗户面板
	 */
	public ControlPanel(SquarePanel pc) {
		this.pc = pc;
		restart.addActionListener(new Reset());
		this.add(label);
		this.add(restart);
	}

	/**
	 * 重设窗户面板，将所有窗户变成初始颜色
	 */
	class Reset implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			for (int i = 0; i < 25; i++) {
				pc.winbutton[i].setBackground(pc.c);
			}
		}
	}
}
