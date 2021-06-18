package book.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 控制序列化就是有选择的序列化对象，而不是把所有对象的一切内容进行序列化。
 * SerializeObject演示了使用transit关键字可以控制变量不被序列化。
 * 本程序将演示通过实现Externalizable接口控制对象的序列化和反序列化。
 */
public class SerializeControl {
	/**
	 * 内部类，用于测试控制对象的序列化和反序列化。
	 * 被序列化的对象必须实现Externalizable接口，该接口有2个方法：
     * writeExternal方法、readExternal方法。
     * 当序列化对象的时候，writeExternal方法会自动执行，所有的对象写入操作由该方法控制。
     * 反序列化对象的时候，readExternal方法会自动执行，如果想访问writeExternal写入的内容，
     * 也只能在该方法里编写恢复对象的代码。
	 */
	static class MyClassControl implements Externalizable{
	     int serialClassInt;
	     int a=3, b=4;
	     public MyClassControl(){
	         System.out.println("MyClass constructor!");
	         this.serialClassInt = 9;
	     }
	     
	     public void show(){
	         System.out.println("serialClassInt: " + serialClassInt);
	     }
	    //当序列化对象的时候，该方法自动被调用
	    public void writeExternal(ObjectOutput out) throws IOException{
	        System.out.println("run writeExternal");
	        //可以在序列化时写非自身的变量，
	        Date d = new Date();
	        out.writeObject(d);
	        //只序列化serialClassInt变量，a、b两个变量不被序列化
	        out.writeInt(this.serialClassInt);        
	    }
	    // 当反序列化对象的时候， 该方法自动被调用
		public void readExternal(ObjectInput in) throws IOException,
				ClassNotFoundException {
			System.out.println("run readExternal");
			Date d = (Date) in.readObject();
			System.out.println(d);
			this.serialClassInt = in.readInt();
		} 
	}
	/**
	 * 序列化对象
	 */
    public static void serializable(String fileName) throws Exception{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        MyClassControl my1 = new MyClassControl();
        out.writeObject(my1);
        out.close();        
    }
    /**
     * 反序列化
     */
    public static void deserializable(String fileName)throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        MyClassControl my2 = (MyClassControl)in.readObject();
        my2.show();
        in.close();
    }

	public static void main(String[] args) throws Exception{
		String fileName = "c:/temp/MyClassControl.ser";
		SerializeControl.serializable(fileName);
		SerializeControl.deserializable(fileName);
	}
}
