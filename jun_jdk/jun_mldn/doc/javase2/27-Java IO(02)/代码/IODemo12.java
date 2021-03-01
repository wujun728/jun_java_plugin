import java.io.* ;
// 定义一个发送者
class SendDemo implements Runnable
{
	private PipedOutputStream out ;
	public SendDemo()
	{
		out = new PipedOutputStream() ;
	}
	public PipedOutputStream getOut()
	{
		return this.out ;
	}
	public void run()
	{
		String str = "Hello MLDN" ;
		try
		{
			out.write(str.getBytes()) ;
			out.close() ;
		}
		catch (Exception e)
		{
		}
		System.out.println("SendDemo --> 发送的内容："+str) ;
	}
};

class ReceDemo implements Runnable
{
	private PipedInputStream in = null ;
	public ReceDemo()
	{
		in = new PipedInputStream() ;
	}
	public PipedInputStream getIn()
	{
		return this.in ;
	}
	public void run()
	{
		byte b[] = new byte[1024] ;
		int len = 0 ;
		try
		{
			len = in.read(b) ;
			in.close() ;
		}
		catch (Exception e)
		{
			System.out.println(e) ;
		}
		System.out.println("ReceDemo --> 收到的内容是："+new String(b,0,len)) ;
	}
};

public class IODemo12
{
	public static void main(String args[])
	{
		SendDemo sd = new SendDemo() ;
		ReceDemo rd = new ReceDemo() ;
		Thread send = new Thread(sd) ;
		Thread rece = new Thread(rd) ;
		// 将两个线程进行连接
		PipedOutputStream out = sd.getOut() ;
		PipedInputStream in = rd.getIn() ;
		// 将输出连接到输入
		try
		{
			out.connect(in) ;
		}
		catch (Exception e)
		{
		}
		send.start() ;
		rece.start() ;
	}
};