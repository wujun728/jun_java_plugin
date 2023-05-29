package demo.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * DataInputStream继承自FilterInputStream(FilterInputStream继承自InputStream)
 * 用来装饰InputStream，提供可移植方式从流读取基本数据类型
 * DataOutputStream继承自FilterOutputStream(FilterOutputStream继承自OutputStream)
 * 用来装饰OutputStream，提供可移植方式向流写入基本数据类型
 * DataInputStream/DataOutputStream可以实现数据的存储与恢复
 */
public class DataInputStreamDemo {
	public static void main(String[] args) {
		DataOutputStream dataOutStream = null;
		try {
			dataOutStream = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream("file/aa.data")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {// 写入文件
			dataOutStream.writeChar('a');
			dataOutStream.writeInt(3);
			dataOutStream.writeDouble(5.5);
			dataOutStream.writeFloat(3.2f);
			dataOutStream.writeUTF("nihaoma");
			dataOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(new BufferedInputStream(
					new FileInputStream("file/aa.data")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {// 读取文件
			System.out.println(dataInputStream.readChar());
			System.out.println(dataInputStream.readInt());
			System.out.println(dataInputStream.readDouble());
			System.out.println(dataInputStream.readFloat());
			System.out.println(dataInputStream.readUTF());
			dataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
