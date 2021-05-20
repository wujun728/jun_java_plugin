package book.io;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
/**
 * 序列化和反序列化对象
 */
public class SerializeObject {
	//	一个内部类，用于被序列化和反序列化。
	//一定要实现Serializable才能够被序列化和反序列化。
	static class MyClass implements Serializable{
		//一般的实例变量会被序列化和反序列化
	    private int a,b;
	    //transient实例变量 不会 被序列化和反序列化
	    private transient int c; 
	    // 类变量 不会 被序列化和反序列化
	    private static int d;
	    public MyClass(){
	    }
	    public MyClass(int a, int b, int c, int d){
	        this.a = a;
	        this.b = b;
	        this.c = c;
	        MyClass.d = d;
	    }
	    public String toString(){
	    	return this.a + "  " + this.b + "  " + this.c + "  " + MyClass.d;
	    }
	}

	/**
	 * 序列化对象到文件
	 */
    public static void serialize(String fileName) throws Exception{
    	//创建一个对象输出流，将对象输出到文件
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        //序列化一个字符串对象到文件
        out.writeObject("Today:");
        //序列化当前日期对象到文件
        out.writeObject(new Date());
        //序列化一个MyClass对象
        MyClass my1 = new MyClass(5, 6, 7, 8);
        out.writeObject(my1);
        out.close();
    }
    /**
     * 从文件反序列化到对象
     */
    public static void deserialize(String fileName) throws Exception{
    	//创建一个对象输入流，从文件读取对象
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        //注意读对象时必须按照序列化对象时的顺序读，否则会出错
        //读取字符串对象
        String today = (String)(in.readObject());
        System.out.println(today);
        //读日期对象
        Date date = (Date)(in.readObject());
        System.out.println(date.toString());
        //读MyClass对象，并调用它的add方法。
        MyClass my1 = (MyClass)(in.readObject());
        System.out.println(my1.toString());
        in.close();
        //当恢复对象的时候，对象中的所有域被自动的恢复。如果不希望某个域被序列化，可以在它前面
        //加上transient关键字，例如下面的代码：transient int noSer = 0;
        //类似的，如果类中的某个域为静态，它不会被序列化。
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String fileName = "c:/temp/MyClass.ser";
		SerializeObject.serialize(fileName);
		//注释掉第二行，只运行下面一行，将会发现输出不同
		SerializeObject.deserialize(fileName);
	}
}

