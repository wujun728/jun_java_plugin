interface Window{
	public void open() ;	// 打开窗口
	public void close() ;	// 关闭窗口
	public void icon() ;	// 最小化
	public void unicon() ;	// 最大化
}
abstract class WindowAdapter implements Window{
	public void open(){}
	public void close(){}
	public void icon(){}
	public void unicon(){}
};
class MyWindow extends WindowAdapter{
	public void open(){
		System.out.println("打开窗口！") ;
	}
};
public class AdpaterDemo{
	public static void main(String args[]){
		Window win = new MyWindow() ;
		win.open() ;
	}
}