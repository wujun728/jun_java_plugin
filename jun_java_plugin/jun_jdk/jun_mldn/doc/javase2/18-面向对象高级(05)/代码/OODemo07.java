interface USB
{
	// 开始工作
	public void start() ;
	// 停止工作
	public void stop() ;
}
class Mp3 implements USB
{
	public void start()
	{
		System.out.println("Mp3开始工作了。。。") ;
	}
	public void stop()
	{
		System.out.println("Mp3停止工作了。。。") ;
	}
};

class UDisk implements USB
{
	public void start()
	{
		System.out.println("U盘开始工作了。。。") ;
	}
	public void stop()
	{
		System.out.println("U盘停止工作了。。。") ;
	}
};
// 假设现在要修改子类，则要修改mian方法，是一个程序的客户端
class Factory
{
	public static USB getUSBInstance()
	{
		return new UDisk() ;
	}
};
public class OODemo07
{
	public static void main(String args[])
	{
		USB u = Factory.getUSBInstance() ;
		u.start() ;
		u.stop() ;
	}
};
