import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

public class TimerDemo extends MIDlet {

    Display display; //设备的显示器
    NumberCanvas numCanvas = new NumberCanvas(); //绘制数字的Canvas对象
    NumberMovie mov = new NumberMovie(); 
    Timer  timer = new Timer();  //定时器

    public TimerDemo() {}   

    protected void startApp() {
	    display = Display.getDisplay( this );  //获取设备的显示器
        display.setCurrent( numCanvas ); //设置当前的绘图对象
        timer.schedule( mov, 2, 2 ); //设备定时器的运行时间
    }

    protected void pauseApp() {}

    protected void destroyApp( boolean u) {}

    public void exit(){
        timer.cancel();  //退出定时器
        destroyApp( true );
        notifyDestroyed();
    }

    class NumberMovie extends TimerTask {
        public void run(){
            numCanvas.scroll();  //滚动屏幕
        }
    }

    class NumberCanvas extends Canvas {
        int        height;
        int        width;
        int[]      stars;  //绘制数字的坐标点
        Random     random = new Random();
	    int ran; //用于生成0或1的随机数

        public NumberCanvas(){
            height      = getHeight(); 
            width       = getWidth();
            stars       = new int[ height ];
            for( int i = 0; i < height; ++i ){
                stars[i] = -1;
            }
        }

        public void scroll() {
            for( int i = height-1; i > 0; --i ){
                stars[i] = stars[i-1];
            }
            stars[0] =  random.nextInt() %  width ; //生成绘制数字的坐标点 
            if( stars[0] >= width ){
                stars[0] = -1;
            }
            repaint(); //重绘屏幕
        }

        protected void paint( Graphics g ){

            g.setColor( 0, 0, 0 ); //设置当前绘图颜色为黑色
            g.fillRect( 0, 0, width, height ); //填充背景

            g.setColor( 255, 255, 255 ); //设置当前绘图颜色为白色

            for( int y = 0; y < height; y++ ){
                int x = stars[y];
                if( x == -1 ) continue;
		        ran=random.nextInt()%2; //生成-1到1的随机数
		        if (ran<0)
		           ran=1;
                g.drawString(Integer.toString(ran),x,y,Graphics.BOTTOM|Graphics.LEFT); //绘制数字
		    }
        }

        protected void keypressed( int keycode ){
            exit(); //退出程序
        }
    }
}