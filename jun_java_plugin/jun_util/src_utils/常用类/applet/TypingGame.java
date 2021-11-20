package book.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * 用Applet实现的打字游戏。同时有多个字母方块不断的落下，
 * 需要按下字母方块上的字母，才能得分。
 */
public class TypingGame extends Applet implements Runnable {

	// 游戏画面的宽度和高度
	int width, height;

	// 游戏的画布和画笔
	public Image img;
	Graphics off;

	// 键盘和鼠标事件处理器
	MouseListener mouseListener = new MyMouseAdapter();
	KeyListener keyListener = new MyKeyAdapter();

	// 右键的弹出菜单
	JPopupMenu popupMenu;

	// 字母制造器，不断的产生字母。
	LetterMaker myLetterMaker;

	// 玩家击中的字母的次数
	int rightTypedSum;
	// 玩家漏掉的字母数
	int omittedSum;
	// 玩家按错的次数
	int wrongTypedSum;
	// 正确率
	float percent;
	
	// 游戏的刷新率
	public int FPS;

	// 游戏的后台线程和游戏的运行状态
	public Thread gameThread;
	public static boolean running;

	/**
	 * 初始化Applet
	 */
	public void init() {
		this.setBackground(Color.pink);
		this.setLayout(new FlowLayout());
		// 从Applet的配置HTML中读取参数
		FPS = Integer.parseInt(getParameter("FPS"));
		rightTypedSum = omittedSum = wrongTypedSum = 0;
		percent = 0f;
		// 读取Applet的大小参数
		width = this.getWidth();
		height = this.getHeight();
		// 将游戏背景大小设置为Applet大小
		img = this.createImage(width, height);
		off = img.getGraphics();

		running = false;
		// 添加组件
		addComponents();
	}

	// 为Applet增加组件
	private void addComponents() {
		// 为Applet增加鼠标和键盘事件处理器
		this.addKeyListener(keyListener);
		this.addMouseListener(mouseListener);

		// 构造右键的弹出菜单
		popupMenu = new JPopupMenu();
		// 开始游戏菜单
		JMenuItem menuItem = new JMenuItem("开始游戏");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightTypedSum = omittedSum = wrongTypedSum = 0;
				running = true;
				start();
			}
		});
		popupMenu.add(menuItem);
		// 结束游戏菜单
		menuItem = new JMenuItem("结束游戏");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				running = false;
			}
		});
		popupMenu.add(menuItem);
		// 增加字母数字菜单
		popupMenu.addSeparator();
		menuItem = new JMenuItem("增加字母数字");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myLetterMaker.currentLetterNumOnScreen < 9){
					myLetterMaker.currentLetterNumOnScreen++;
				}
				myLetterMaker.refreshLetters();
			}
		});
		popupMenu.add(menuItem);
		// 加快下落速度菜单
		menuItem = new JMenuItem("加快下落速度");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speedUp();
			}
		});
		popupMenu.add(menuItem);
		popupMenu.addSeparator();
		// 减少字母数字
		menuItem = new JMenuItem("减少字母数字");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myLetterMaker.currentLetterNumOnScreen > 1){
					myLetterMaker.currentLetterNumOnScreen--;
				}
				myLetterMaker.refreshLetters();
			}
		});
		popupMenu.add(menuItem);
		// 减缓下落速度
		menuItem = new JMenuItem("减缓下落速度");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speedDown();
			}
		});
		popupMenu.add(menuItem);
	}

	/**
	 * 启动Applet
	 */
	public void start() {
		// 新建游戏线程
		gameThread = new Thread(this);
		gameThread.start();
		// 新建字母制造器
		myLetterMaker = new LetterMaker(this, off);
		myLetterMaker.refreshLetters();
	}

	public void stop() {
		running = false;
		gameThread = null;
	}

	public void run() {
		while (running) {
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}

	public void paint(Graphics g) {
		int sum;
		int showPercent = 0;
		// 使用双缓冲技术，首先将图形画在Image对象上，再将Image对象画在屏幕上。
		if (running) {
			//off画笔属于Image对象的，所以它画的所有东西都在Image上
			off.setColor(this.getBackground());
			off.fillRect(0, 0, width, height);
			myLetterMaker.paintLetters(g);
			off.setColor(Color.blue);
			// 计算玩家的各种指标
			sum = rightTypedSum + wrongTypedSum + omittedSum;
			if (sum == 0) {
				percent = 0f;
				showPercent = 0;
			} else {
				percent = (float) rightTypedSum / sum;
				showPercent = (int) (percent * 100);
			}
			off.drawString("击中" + rightTypedSum + "  错击" + wrongTypedSum + "  漏掉"
					+ omittedSum + "  正确率" + showPercent + "%", 200, 200);
			// g画笔属于Applet，把Image对象画在Applet上。
			g.drawImage(img, 0, 0, width, height, this);
		} else {
			off.setColor(this.getBackground());
			off.fillRect(0, 0, width, height);
			off.drawString("击中" + rightTypedSum + "  错击" + wrongTypedSum + "  漏掉"
					+ omittedSum + "  正确率" + showPercent + "%", 200, 200);
			g.drawImage(img, 0, 0, width, height, this);
		}
	}

	public void update(Graphics g) {
		this.paint(g);
	}
	/**
	 * 减缓字母下落速度
	 */
	public void speedDown(){
		Letter letter = null;
		for (int i = 0; i < myLetterMaker.currentLetterNumOnScreen; i++) {
			letter = (Letter)myLetterMaker.currentLetters.get(i);
			if (letter.speed > 1){
				letter.speed --;
			}
		}
	}
	/**
	 * 提高字母下落速度
	 */
	public void speedUp(){
		Letter letter = null;
		for (int i = 0; i < myLetterMaker.currentLetterNumOnScreen; i++) {
			letter = (Letter)myLetterMaker.currentLetters.get(i);
			if (letter.speed < 8){
				letter.speed ++;
			}
		}
	}

	// 鼠标事件侦听器
	class MyMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		// 如果在Applet上点击了右键，则弹出菜单
		public void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				// 显示菜单
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	// 键盘事件侦听器
	class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			// 获得被按的键的字符
			char key = e.getKeyChar();
			Letter letter = null;
			for (int i = 0; i < myLetterMaker.currentLetterNumOnScreen; i++) {
				letter = (Letter)myLetterMaker.currentLetters.get(i);
				if ((char) (key - 32) == letter.getChar()) {
					rightTypedSum++;
					myLetterMaker.resetLetter(i);
					return;
				}
			}
			wrongTypedSum++;
		}
	}
}

/**
 * 游戏中字母的产生器。
 */
class LetterMaker {
	// 所属的游戏对象和画笔
	TypingGame game;
	Graphics off;

	// 屏幕上最多能同时显示的字母数
	final int MAX_LETTER_NUM = 9;
	// 当前屏幕上的字母数
	int currentLetterNumOnScreen;
	// 存放当前屏幕上的字母对象
	List currentLetters = new ArrayList();
	// 字母的颜色
	final Color[] letterColors = { Color.red, Color.green };

	// 字母在X方向上的基准坐标。即下落轨道的X值
	int trackXPosition[];
	// 随机数生成器
	Random random;
	
	/**
	 * 构造方法
	 * @param game    游戏对象
	 * @param off    画笔
	 */
	public LetterMaker(TypingGame game, Graphics off) {
		this.off = off;
		this.game = game;
		initTrack();
		// 一开始下落3个字母
		currentLetterNumOnScreen = 3;
		random = new Random();
	}

	/**
	 * 初始化字母的下落轨道
	 */
	private void initTrack(){
		//　初始化字母在X方向上轨道
		trackXPosition = new int[MAX_LETTER_NUM];
		// 需要根据Applet的宽度，均匀的分布轨道
		int width = game.getWidth();
		int interval = width / MAX_LETTER_NUM;
		int trackX = interval/2;
		for (int i = 0; i < MAX_LETTER_NUM; i++) {
			trackXPosition[i] = trackX;
			trackX += interval;
		}
	}
	
	/**
	 * 刷新当前屏幕上的字母对象，即重新生成一组字母对象。
	 */
	public void refreshLetters(){
		// 首先清空当前屏幕的字母对象
		currentLetters.clear();
		// 再创造currentLetterNumOnScreen个新对象
		for (int i=0; i<currentLetterNumOnScreen; i++){
			// 加入到currentLetters
			currentLetters.add(createNewLetter(null));
		}
	}
	/**
	 * 重新设置字母对象，字母对象会从头下落
	 * @param index
	 */
	public void resetLetter(int index){
		// 将被重置的字母取出来
		Letter letter = (Letter)currentLetters.remove(index);
		// 重新赋值后再放进去
		currentLetters.add(index, createNewLetter(letter));
	}
	/**
	 * 创建一个不重复的新字母对象。
	 * @param letter
	 * @return
	 */
	private Letter createNewLetter(Letter letter){
		int x = trackXPosition[random.nextInt(MAX_LETTER_NUM)];
		int y = random.nextInt(11) - 10;
		int speed = random.nextInt(8) + 1;
		String value = String.valueOf((char) (random.nextInt(26) + 65));
		if (letter == null){
			letter = new Letter(x, y, speed, value);
		} else {
			letter.x = x;
			letter.y = y;
			letter.speed = speed;
			letter.value = value;
		}
		while (currentLetters.contains(letter)){
			value = String.valueOf((char) (random.nextInt(26) + 65));
			letter.value = value;
		}
		return letter;
	}

	/**
	 * 用画笔画字母组
	 * @param g
	 */
	public void paintLetters(Graphics g) {
		Letter letter = null;
		for (int index = 0; index < currentLetterNumOnScreen; index++) {
			// 获得字母对象
			letter = (Letter)currentLetters.get(index);
			// 画字母对象
			letter.draw(off, letterColors[random.nextInt(2)]);
			// 每画一次字母就往下落，下落距离取决于它的速度
			letter.y += letter.speed;
			// 当字母到达Applet底部消失时
			if (letter.y > game.height){
				// 表示该字母被漏掉了
				game.omittedSum++;
				// 将该位置上的新建一个字母重新下落
				resetLetter(index);
			}
		}
	}
}
/**
 * 字母对象，定义了字母所在坐标、下落速度和字母值
 */
class Letter{
	int x;
	int y;
	int speed;
	String value;

	public Letter(int x, int y, int speed, String value){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.value = value;
	}
	
	public void draw(Graphics g, Color bgColor){
		// 设置画笔的颜色
		g.setColor(bgColor);
		// 画一个类似3D的正方形，并将字母写上去
		g.fill3DRect(this.x, this.y, 20, 20, true);
		g.setColor(Color.blue);
		g.drawString(this.value, this.x + 5, this.y + 15);
	}
	// 获得字母对象的字符
	public char getChar(){
		return this.value.charAt(0);
	}
	
	//为了方便判断字母是否出现过，这里定义了它的equals方法和hashCode方法。
	public boolean equals(Object obj){
		if (obj instanceof Letter){
			Letter objL = (Letter)obj;
			if (this.value.equals(objL.value)){
				return true;
			}
		}
		return false;
	}
	public int hashCode(){
		return this.value.hashCode();
	}
}