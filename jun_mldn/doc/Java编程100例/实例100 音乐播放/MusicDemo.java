import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import java.io.*; 

public class MusicDemo extends MIDlet {
	private Display display; // 设备的显示器
    private Player player = null; //播放器
    private TextBox textbox; //显示提示信息

    public MusicDemo() {
        textbox=new TextBox("title","playing...",1024, 0);  //实例化文本框
        try{
            InputStream is = this.getClass().getResourceAsStream("/music.wav");  //音乐文件输入流
            player = Manager.createPlayer(is,"audio/x-wav");  //实例化播放器
        }catch(Exception e){}
    }

    public void startApp() {
  	    display = Display.getDisplay(this); //取得设备的显示器
  	  
        if(player != null){
            try{
                player.start();  //开始播放音乐
                display.setCurrent(textbox);  //显示正在播放的信息
            }catch(MediaException e){
                textbox.setString("Errors");  //设置错误信息
                display.setCurrent(textbox);  //显示错误信息
            }      
        }
        else{
    	    textbox.setString("Errors");
            display.setCurrent(textbox); 
    	}
    }

    public void pauseApp() {}

    public void destroyApp(boolean unconditional) {}
}

