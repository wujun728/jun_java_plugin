import java.io.* ;
public class IODemo13
{
	public static void main(String args[]) throws Exception
	{
		String str = "LXHMLDN" ;
		// 字符串都是大写的，现在要求可以将字符串变位小写
		byte[] b = str.getBytes() ;
		ByteArrayInputStream in = new ByteArrayInputStream(b) ;
		ByteArrayOutputStream out = new ByteArrayOutputStream() ;
		// 程序中现在要求是将大写变为小写
		// 只能一个一个字节的读
		int c = 0 ;
		// 如果没有读到低，则不会是-1
		while((c=in.read())!=-1)
		{
			int ch = (int)Character.toLowerCase((char)c) ;
			// 向输出流中写
			out.write(ch) ;
		}

		b = out.toByteArray() ;
		System.out.println("内容为："+new String(b)) ;
	}
};