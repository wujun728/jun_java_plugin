import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//动画图标

public class AnimatorIcon extends JPanel implements ActionListener {

	ImageIcon[] images; //用于动画的图标数组
	Timer animationTimer; 
	int currentImage = 0; //当前图像编号
	int delay = 500;  //图像切换延迟
	int width; //图像宽度
	int height; //图像高度

	public AnimatorIcon() //构造函数
	{
		setBackground(Color.white);
		images = new ImageIcon[2]; //初始化数组
		for (int i=0;i<images.length;i++)
			images[i]=new ImageIcon(getClass().getResource("image"+i+".gif")); //实例化图标
		width = images[0].getIconWidth(); //初始化宽度值
		height = images[0].getIconHeight(); //初始化高度值
	}

	public void paintComponent(Graphics g) { //重载组件绘制方法
		super.paintComponent(g); //调用父类函数
		images[currentImage].paintIcon(this,g,70,0); //绘制图标
		currentImage=(currentImage+1)%2; //更改当前图像编号
	}

	public void actionPerformed(ActionEvent actionEvent) {
		repaint();
	}

	public void startAnimation() { //开始动画
		if (animationTimer==null) {
			currentImage=0; 
			animationTimer=new Timer(delay, this);  //实例化Timer对象
			animationTimer.start(); //开始运行
		} else if (!animationTimer.isRunning()) //如果没有运行
			animationTimer.restart(); //重新运行
	}

	public void stopAnimation() { 
		animationTimer.stop();  //停止动画
	}

	public static void main(String args[]) {
		AnimatorIcon animation = new AnimatorIcon(); //实例化动画图标
		JFrame frame = new JFrame("动画图标"); //实例化窗口对象
		frame.getContentPane().add(animation);  //增加组件到窗口上
		frame.setSize(200, 100); //设置窗口尺寸
		frame.setVisible(true); //设置窗口可视
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
		animation.startAnimation(); //开始动画
	}

}