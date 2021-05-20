import javax.microedition.lcdui.*;

public class BallCanvas extends javax.microedition.lcdui.Canvas implements Runnable{
	static java.util.Random random = new java.util.Random();
	
	int posX=5, posY=5; //小球显示位置
	int ballSize = 10; //小球尺寸
	Display display; //显示器
	
	public BallCanvas(Display display){  //构造函数
		super();
		this.display=display;
		
	}	
	
	public void run() { //线程的主方法
		while (true){
			this.posX = (random.nextInt()>>>1) % (this.getWidth()-20) + 10;  //生成小球位置X坐标
			this.posY = (random.nextInt()>>>1) % (this.getHeight()-20) + 10;  //生成Y坐标
			try {
				Thread.sleep(100);  //线程休眠
			} catch (InterruptedException e) {}
			repaint(); //重绘屏幕
		}
	}
	
	void start() {
	    display.setCurrent(this); //设置当前屏幕
	    Thread t = new Thread(this);
		t.start(); //开始线程运行 	    
		repaint();
	}
	
	void destroy() {
	}	
	
	protected void paint(Graphics g) {
		int x = g.getClipX(); //获取剪切区位置
        int y = g.getClipY();
        int w = g.getClipWidth(); //获取剪切区大小
        int h = g.getClipHeight();
		g.setColor(0xffffff); //设置当前颜色
		g.fillRect(x, y, w, h); //绘制填充矩形
		g.setColor(0); 
		g.fillArc(posX, posY, ballSize, ballSize, 0, 360); //绘制小球
	}
	
}