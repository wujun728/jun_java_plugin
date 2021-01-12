import java.io.* ;
public class IODemo15
{
	public static void main(String args[]) throws Exception
	{
		File f = new File("f:\\lxh.txt") ;
		// 使用PrintWriter
		// PrintWriter out = new PrintWriter(new FileWriter(f)) ;
		PrintWriter out = new PrintWriter(System.out) ;
		// 具备了向文件中打印数据的能力
		out.println(true) ;
		out.println(30) ;
		out.println("HELLO MLDN") ;
		out.close() ;
	}
};