public class APIDemo04
{
	public static void main(String args[]) throws Exception
	{
		Runtime r = Runtime.getRuntime() ;
		// 运行程序
		Process p = r.exec("notepad.exe") ;
		Thread.sleep(3000) ;
		// 让进程销毁 --> 关闭
		p.destroy() ;
	}
};