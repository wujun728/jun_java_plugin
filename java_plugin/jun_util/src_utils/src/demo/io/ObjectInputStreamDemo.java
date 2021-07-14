package demo.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/*
 * ObjectInputStream(ObjectOutputStream)用于对象的序列化
 * 直接对一个对象进行读写，该对象须实现Serializable
 */
public class ObjectInputStreamDemo {
	public static void writeObject(String fileName, Object o, boolean isAppend)
			throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				fileName, isAppend));
		out.writeObject(o);
		out.close();
	}

	public static Object readObject(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				fileName));
		Object o = in.readObject();
		in.close();
		return o;
	}

//	public static void main(String[] args) {
//		ObjectFile.write("file/objectfile2", new Person(), false);
//		ObjectFile.write("file/objectfile2", new Person(), true);
////		ObjectFile.write("file/objectfile2",
////				new Person[] { new Person("Touch", 1),
////						new Person("Rainbow", 0), new Person() }, true);
//		for (Object o : ObjectFile.read("file/objectfile2", 2))
//			((Person) o).display();
//	}

	public static void main(String[] args) {
		try {
			ObjectInputStreamDemo.writeObject("file/object", new Person(),
					false);
			ObjectInputStreamDemo
					.writeObject("file/object", new Person(), true);

			ObjectInputStreamDemo
					.writeObject("file/object", new Person(), true);
			((Person) ObjectInputStreamDemo.readObject("file/object"))
					.display();
			((Person) ObjectInputStreamDemo.readObject("file/object"))
					.display();
			((Person) ObjectInputStreamDemo.readObject("file/object"))
					.display();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = "刘海房";
	private int sex = 0;

	Person(String name, int sex) {
		this.name = name;
		this.sex = sex;
	}

	Person() {
	}

	void display() {
		System.out.println("my name is :" + name);
		String s = (sex == 0) ? "男" : "女";
		System.out.println("性别：" + s);
	}
}