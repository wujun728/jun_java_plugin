import javax.swing.*;
import java.awt.*;
import java.net.*;

//程序启动界面

public  class JWindowDemo extends JWindow implements Runnable {
  Thread splashThread;  //进度条更新线程
  JProgressBar progress; //进度条
  
  public JWindowDemo() {
  	 Container container=getContentPane(); //得到容器
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  //设置光标
    URL url = getClass().getResource("login.jpg"); //图片的位置
    if(url != null){
      container.add(new JLabel(new ImageIcon(url)),BorderLayout.CENTER);  //增加图片
    }
    progress = new JProgressBar(1,100); //实例化进度条
    progress.setStringPainted(true); //描绘文字
    progress.setString("加载程序中,请稍候......");  //设置显示文字
    progress.setBackground(Color.white);  //设置背景色
    container.add(progress,BorderLayout.SOUTH);  //增加进度条到容器上

    Dimension screen = getToolkit().getScreenSize();  //得到屏幕尺寸
    pack(); //窗口适应组件尺寸
    setLocation((screen.width-getSize().width)/2,(screen.height-getSize().height)/2); //设置窗口位置
  }

  public void start(){
    this.toFront();  //窗口前端显示
    splashThread=new Thread(this);  //实例化线程
    splashThread.start();  //开始运行线程
  }

  public void run(){
    setVisible(true); //显示窗口
    try {
      for (int i=0;i<100;i++){
        Thread.sleep(100); //线程休眠
        progress.setValue(progress.getValue()+1); //设置进度条值
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    dispose(); //释放窗口
    showFrame(); //运行主程序
  }

  static void showFrame(){
    JFrame frame = new JFrame("程序启动界面演示"); //实例化JFrame对象
    frame.setSize(300,200);  //设置窗口尺寸
    frame.setVisible(true);  //窗口可视
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
  }

  public static void main(String[] args) {
    JWindowDemo splash = new JWindowDemo();
    splash.start();  //运行启动界面
  }
}
