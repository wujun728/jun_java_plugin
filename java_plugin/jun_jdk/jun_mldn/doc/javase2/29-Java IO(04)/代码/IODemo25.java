import java.io.* ;
public class IODemo25
{
	public static void main(String args[]) throws Exception
	{
		InputStream in1 = null ;
		InputStream in2 = null ;
		// 建立一个输出流
		OutputStream out = null ;

		in1 = new FileInputStream(new File("f:\\lxh1.txt")) ;
		in2 = new FileInputStream(new File("f:\\lxh2.txt")) ;
		out = new FileOutputStream(new File("f:\\lxhmldn.txt")) ;

		// 此处相当于将两个文件合并了
		SequenceInputStream seq = null ;
		seq = new SequenceInputStream(in1,in2) ;
		// 文件合并之后输出到：lxhmldn.txt文件之中
		int c = 0 ;
		while((c=seq.read())!=-1)
		{
			out.write(c) ;
		}
		in1.close() ;
		in2.close() ;
		out.close() ;
		seq.close() ;
	}
};