import java.io.* ;
import java.util.* ;
public class Demo
{
	public static void main(String args[]) throws Exception
	{
		Properties p = new Properties() ;
		/*
		p.setProperty("a","org.lxh.demo.Apple") ;
		p.setProperty("o","org.lxh.demo.Orange") ;
		p.setProperty("b","org.lxh.demo.Banana") ;
		p.setProperty("c","org.lxh.demo.Cherry") ;
		// 在文件中保存一段数据，此数据为以上的数据内容
		p.storeToXML(new FileOutputStream("lxh.xml"),"MLDN --> LXH") ;
		*/
		// 需要从文件中读取要Properties中的属性
		p.loadFromXML(new FileInputStream("lxh.xml")) ;
		System.out.println(p) ;
	}
};