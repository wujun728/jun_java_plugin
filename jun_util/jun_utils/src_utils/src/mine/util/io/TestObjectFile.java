package mine.util.io;

import java.io.Serializable;

public class TestObjectFile {
	public static void main(String[] args) {
		ObjectFile.write("file/object1", new Person(), false);
		ObjectFile.write("file/object1", new Person(), true);
		ObjectFile.write("file/object1", new Person[] { new Person("Touch", 1),
				new Person("Rainbow", 0), new Person() }, true);
		for (Object o : ObjectFile.read("file/object1", 5))
			((Person) o).display();
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