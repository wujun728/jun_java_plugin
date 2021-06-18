interface USB
{
	// 开始工作
	public void start() ;
	// 停止工作
	public void stop() ;
}
// 对于PC机上认的是一个USB接口
class PC
{
	public static void plugin(USB u) 
	{
		u.start() ;
		u.stop() ;
	}
};
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

public class OODemo05
{
	public static void main(String args[])
	{
		PC.plugin(new UDisk()) ;
	}
};