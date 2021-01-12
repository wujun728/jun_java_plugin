import java.io.* ;

public class IODemo24
{
	public static void main(String args[]) throws Exception
	{
		OutputStream out = null ;
		out = new FileOutputStream(new File("f:\\lxh.txt")) ;
		String str = "李兴华欢迎大家来MLDN学习。" ;
		out.write(str.getBytes("GB2312")) ;
		out.close() ;
	}
};