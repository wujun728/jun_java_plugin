package book.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import book.basic.HelloWorld;

/**
 *从jar包中读资源文件，如配置文件等
 */
public class ResourceReader {

	/**
	 * 第一种方法读Jar包中的资源信息，首先读取资源的URL，再读取URL对应的文件信息
	 * @param class1	类
	 * @param fileName	文件的相对路径
	 */
	public static void readFromJarA(Class class1, String fileName) {
		//getResource得到一个URL对象来定位资源
		URL fileURL = class1.getResource(fileName);
		System.out.println("资源的URL: " + fileURL);
		try {
			//打开fileURL对应的文件流
			InputStream inputstream = fileURL.openStream();
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream));
			String str;
			
			while ((str = bufferedreader.readLine()) != null) {
				System.out.println(str);
			}
			inputstream.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}
	/**
	 * 第二种方法读Jar包中的资源信息。
	 * @param class1	类
	 * @param fileName	文件的相对路径
	 */
	public static void readFromJarB(Class class1, String fileName) {
		//getResourceAsStream取得该资源输入流的引用，保证程序可以从正确的位置抽取数据
		InputStream inputstream = class1.getResourceAsStream(fileName);
		if (inputstream != null) {
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream));
			String str;
			try {
				while ((str = bufferedreader.readLine()) != null) {
					System.out.println(str);
				}
				inputstream.close();
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Class class1 = HelloWorld.class;
		//如果文件相对路径前没有"/"，则表示相对于该class文件的位置
		String filePath = "config0.conf";
		ResourceReader.readFromJarA(class1, filePath);
		System.out.println();
		filePath = "Resources/config1.conf";
		ResourceReader.readFromJarB(class1, filePath);
		System.out.println();
		
		//如果在文件相对路径前加上 "/"，则表示相对于jar文件的根目录位置。
		//如"/Resources"则表示jar文件的第一层目录，目录为Resources
		filePath = "/book/basic/Resources/config2.conf";
		ResourceReader.readFromJarA(class1, filePath);
	}
}
