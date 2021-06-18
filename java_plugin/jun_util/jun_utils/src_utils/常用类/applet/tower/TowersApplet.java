package book.applet.tower;

import java.applet.Applet;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 汉诺塔游戏的Applet
 */
public class TowersApplet extends Applet implements ActionListener,
		MouseListener {

	// 游戏自动执行的玩家
	private AutoPlayer autoPlayer;
	// 游戏场景
	public GameGroup theGameGroup;

	// 是否重新开始游戏标签
	private boolean restartFlag;
	// 游戏中塔上的盘数
	private TextField diskNumTF;

	// 重新游戏按钮
	public Button newButton;
	// 单步游戏按钮
	public Button stepButton;
	// 自动游戏按钮
	public Button autoRunButton;
	
	public TowersApplet() {
		restartFlag = false;
		diskNumTF = new TextField("4", 5);
	}
	
	// 初始化Applet
	public void init() {
		// 添加按钮、文本输入框等AWT组件
		addMouseListener(this);
		setLayout(new FlowLayout());
		Panel panel = new Panel();
		add(panel);
		panel.setLayout(new FlowLayout());
		Panel panel1 = new Panel();
		panel.add(panel1);
		panel1.setLayout(new FlowLayout(0));
		newButton = new Button("New");
		panel1.add(newButton);
		newButton.addActionListener(this);
		stepButton = new Button("Step");
		panel1.add(stepButton);
		stepButton.addActionListener(this);
		autoRunButton = new Button("Run");
		panel1.add(autoRunButton);
		autoRunButton.addActionListener(this);
		Panel panel2 = new Panel();
		panel.add(panel2);
		panel2.setLayout(new FlowLayout(2));
		panel2.add(new Label("Enter number: "));
		panel2.add(diskNumTF);
		theGameGroup = new GameGroup(Constants.INIT_DISK_NUMS);
		repaint();
	}

	public void start() {
	}

	public void stop() {
		if (autoPlayer != null) {
			autoPlayer.running = false;
		}
	}
	// 重画屏幕时，刷新游戏场景
	public void paint(Graphics g) {
		theGameGroup.draw(g);
	}
	public void update(Graphics g) {
		paint(g);
	}

	// 处理按钮事件
	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getSource() == newButton) {
			theGameGroup.setStep(false);
			//新建游戏，根据输入的盘数，重新构造游戏场景
			// 只有连续两次点击该按钮，才新建游戏，其中按第一次是用来提醒用户的，第二次才生效
			if (restartFlag) {
				restartFlag = false;
				// 判断输入的盘数是否为整数
				String s = diskNumTF.getText();
				boolean isNumber = true;
				int GPNumber = 0;
				try {
					GPNumber = Integer.parseInt(s);
				} catch (NumberFormatException _ex) {
					isNumber = false;
				}
				// 如果输入的不是整数，则打出错误信息
				if (!isNumber || GPNumber > 10 || GPNumber < 1){
					theGameGroup.creationError();
				} else {
					// 否则新建游戏场景
					theGameGroup = new GameGroup(GPNumber);
				}
			} else {
				// 第一次点击该按钮时，请用户确认是否新游戏
				restartFlag = true;
				theGameGroup.warningNew();
			}
			
		} else if (actionevent.getSource() == stepButton) {
			// 单步执行游戏
			restartFlag = false;
			theGameGroup.step();
			
		} else if (actionevent.getSource() == autoRunButton) {
			// 自动执行游戏，启动一个线程。 
			restartFlag = false;
			theGameGroup.setStep(false);
			if (theGameGroup.canAutoRun()){
				theGameGroup.setDone(false);
				autoPlayer = new AutoPlayer(this);
				autoPlayer.start();
			}
		}
		repaint();
		// 避免连续恶意的点击按钮引起的开销。
		try {
			Thread.sleep(10);
			return;
		} catch (InterruptedException _ex) {
			return;
		}
	}

	public void mousePressed(MouseEvent mouseevent) {
		// 先取得鼠标按下的坐标
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		// 设置新游戏标签为false，因为这已经不是连续点击新游戏按钮了。
		restartFlag = false;
		// 如果是单击，则开始移动盘
		if (mouseevent.getClickCount() == 1){
			theGameGroup.startDrag(i, j);
		}
	}
	public void mouseReleased(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		// 松开按钮时，停止移动
		theGameGroup.endDrag(i, j);
		repaint();
	}
	public void mouseEntered(MouseEvent mouseevent) {
	}
	public void mouseExited(MouseEvent mouseevent) {
	}
	public void mouseClicked(MouseEvent mouseevent) {
	}
}

/**
 * 游戏自动玩家，模拟用户玩汉诺塔游戏
 */
class AutoPlayer extends Thread{
	TowersApplet towers;
	public boolean running = false;
	public AutoPlayer(TowersApplet towers){
		this.towers = towers;
	}
	public void run(){
		running = true;
		// 自动玩游戏时将所有按钮失效
		this.towers.stepButton.setEnabled(false);
		this.towers.newButton.setEnabled(false);
		this.towers.autoRunButton.setEnabled(false);
		// 如果游戏没有成功，则一直走下一步
		while (running && !this.towers.theGameGroup.isDone()){
			this.towers.theGameGroup.step();
			this.towers.repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException _ex) {
			}
		}
		// 自动玩游戏结束是将按钮恢复
		this.towers.stepButton.setEnabled(true);
		this.towers.newButton.setEnabled(true);
		this.towers.autoRunButton.setEnabled(true);
	}
}