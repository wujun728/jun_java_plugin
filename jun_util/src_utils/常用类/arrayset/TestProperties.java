package book.arrayset;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 属性集合类 Properties
 */
public class TestProperties {

	public static void main(String[] args) throws IOException {
		//新建一个Properties对象
		Properties props = new Properties();
		
		//往Properties中存放数据，格式位<key, value>
		//key 和 value都是字符串
		props.setProperty("name", "ZhangSan");
		props.setProperty("gender", "male");
		props.setProperty("age", "30");
		props.setProperty("telNO", "01088888888");
		props.setProperty("address", "xxxxxxxx");
		
		//从Properties中获取数据。必须提供key
		System.out.println("name: " + props.getProperty("name"));
		System.out.println("gender: " + props.getProperty("gender"));
		System.out.println("age: " + props.getProperty("age"));
		System.out.println("telNO: " + props.getProperty("telNO"));
		System.out.println("address: " + props.getProperty("address"));
		//可以位返回值提供一个缺省值，当Properties中没有该key时，用默认值返回
		System.out.println("other: " + props.getProperty("other", "none"));
		
		//将Properties中的数据保存到输出流，比如文件输出流
		String fileName = "c:/test.properties";
		FileOutputStream out = new FileOutputStream(fileName);
		props.store(out, "test");
		out.close();
		//打开c:/test.properties文件，可以看见里面的内容，注意到，顺序是改变了的。
		
		Properties newProps = new Properties();
		newProps.setProperty("type", "newProps");
		//可以从输入流中获取加载数据，比如文件输入流
		//从properties文件中加载数据
		FileInputStream in = new FileInputStream(fileName);
		newProps.load(in);
		in.close();
		System.out.println();
		System.out.println("type: " + newProps.getProperty("type"));
		System.out.println("name: " + newProps.getProperty("name"));
		System.out.println("gender: " + newProps.getProperty("gender"));
		
		//将Properties中的数据输出到一个输出流
		System.out.println();
		props.list(System.out);
	}
}
