package mine.util.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 此类用于对象的读写
 * 
 * @author Touch
 */
public class ObjectFile {
	// 把一个对象写入文件，isAppend为true表示追加方式写，false表示重新写
	public static void write(String filePath, Object o, boolean isAppend) {
		if (o == null)
			return;
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath, isAppend));
			try {
				out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 把一个对象数组写入文件，isAppend为true表示追加方式写，false表示重新写
	public static void write(String filePath, Object[] objects, boolean isAppend) {
		if (objects == null)
			return;
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath, isAppend));
			try {
				for (Object o : objects)
					out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 读取对象，返回一个对象
	public static Object read(String filePath) {
		Object o = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				o = in.readObject();
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	// 读取对象，返回一个对象数组，count表示要读的对象的个数
	public static Object[] read(String filePath, int count) {
		Object[] objects = new Object[count];
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				for (int i = 0; i < count; i++) {
					//第二次调用in.readObject()就抛出异常，这是为什么？
					objects[i] = in.readObject();
				}
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}
}
