import java.io.* ;

public class IODemo07
{
	public static void main(String args[]) throws Exception
	{
		// 随机读取
		RandomAccessFile raf1 = new RandomAccessFile("f:\\mldn.txt","rw") ;
		// 随机读取有一个限制，就是说如果要进行操作，则必须指定好数据的存储长度
		// 保存姓名（8位字符串）和年龄（int 4）：
		String name = "zhangsan" ;
		int age = 20 ;
		raf1.write(name.getBytes()) ;
		raf1.writeInt(age) ;
		

		name = "lisi    " ;
		age = 30 ;
		raf1.write(name.getBytes()) ;
		raf1.writeInt(age) ;

		name = "wangwu   " ;
		age = 33 ;
		raf1.write(name.getBytes()) ;
		raf1.writeInt(age) ;

		raf1.close() ;

		RandomAccessFile raf2 = new RandomAccessFile("f:\\mldn.txt","r") ;
		// 读取第二个人的数据
		raf2.skipBytes(12) ;
		byte b[] = new byte[8] ;
		raf2.read(b) ;
		int age2 = raf2.readInt() ;
		System.out.println(new String(b)+" --> "+age2) ;

	}
};