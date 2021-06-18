package book.mutimedia.greadsnake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 	蛇的控制类，负责控制贪吃蛇。MVC中的Control
 */
public class SnakeControl implements KeyListener{ 
    
	// 待控制的贪吃蛇的模型对象
    private SnakeModel model;
    // 贪吃蛇的试图对象
    private SnakeView view;
    
    // 贪吃蛇的运行区域大小
    private int district_maxX;
    private int district_maxY;    
    
    // 默认构造方法
    public SnakeControl(){
        this.district_maxX = 30;
        this.district_maxY = 40;
        init();
    }
    
    // 带参数的构造方法
    public SnakeControl(int maxX, int maxY) {
    	// 判断参数是否合法，这里定义了蛇运行区域最大和最小范围
        if ((10 < maxX) && (maxX < 100) && (10 < maxY) && (maxY < 100)){
            this.district_maxX = maxX;
            this.district_maxY = maxY;
        } else {
        	System.out.println("初始化参数错误，用默认参数构造对象！");
            this.district_maxX = 30;
            this.district_maxY = 40;
        }
        init();
    }
    
    // 初始化
    private void init(){
    	// 创建贪吃蛇模型
        this.model = new SnakeModel(district_maxX, district_maxY);
        // 创建贪吃蛇视图
        this.view = new SnakeView(district_maxX, district_maxY, 500, 200);
        // 为模型添加试图，当模型改变时，能够通知到试视图
        this.model.addObserver(this.view);
        // 为视图添加键盘事件处理器
        this.view.addKeyListener(this);
        // 蛇开始运行
        (new Thread(this.model)).start();
    }
    
    // 处理键盘事件
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // 只有在贪吃蛇处于运行状态下，才处理的按键事件
        if (model.isRunning()) {
            switch (keyCode) {
            	// 处理蛇运行的方向
                case KeyEvent.VK_UP:
                    model.changeDirection(SnakeModel.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    model.changeDirection(SnakeModel.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    model.changeDirection(SnakeModel.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    model.changeDirection(SnakeModel.RIGHT);
                    break;
                // 处理蛇运行的速度
                case KeyEvent.VK_ADD:
                case KeyEvent.VK_PAGE_UP:
                    model.speedUp();
                    break;
                case KeyEvent.VK_SUBTRACT:
                case KeyEvent.VK_PAGE_DOWN:
                    model.speedDown();
                    break;
                // 控制蛇的暂停与恢复
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_P:
                    model.changePauseState();
                    break;
                default:
            }
        }

        // 控制游戏的重新开始
        if (keyCode == KeyEvent.VK_R || keyCode == KeyEvent.VK_S 
        		|| keyCode == KeyEvent.VK_ENTER) {
            model.reset();
        }
        // 停止游戏
        if (keyCode == KeyEvent.VK_Q) {
            model.setRunning(false);
        }
    }

    public void keyReleased(KeyEvent e) {
    	// do nothing
    }
    public void keyTyped(KeyEvent e) {
    	// do nothing
    }
}