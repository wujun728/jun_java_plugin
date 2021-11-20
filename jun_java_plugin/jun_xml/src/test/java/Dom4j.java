import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;






public class Dom4j {
	
	
	
	/**
	 * 获取Document对象
	 * 此方法文件位于 项目根目录 不是src目录
	 * @param filename  项目根目录下的XML文件
	 * @return  document 
	 * 
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));  //读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}  
	
	
	/**
	 * 通过url路径获取 Document对象
	 * 此方式 xml文件位于远程服务器上
	 * @param url 远程url文件
	 * @return document对象
	 */
	public static Document load2(URL url) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(url);  //读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;

	}
	
	
	
	/**
	 * 获取根节点
	 * @param doc  document对象
	 * @return 根元素
	 */
	public static Element getRootElement(Document doc){
		Element root=null;
		root=doc.getRootElement(); //获取根节点
		return root;
		
	
	}
	
	
	
	

	
	
	/**
	 * 将document树输出到指定的文件
	 * @param document document对象
	 * @param filename 文件名
	 * @return 布尔值
	 */
	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			XMLWriter writer = new XMLWriter( new OutputStreamWriter(new FileOutputStream(filename),"UTF-8"));
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
			System.out.println(flag);
			return flag;
		}
	
	
	 
	
	/**
	 * 
	 * 
	 * 此方法在本类中无用 ，没有整合 有兴趣的可以自己动手整合一下
	 * 
	 * 
	 * Dom4j通过XMLWriter将Document对象表示的XML树写入指定的文件，
	 * 并使用OutputFormat格式对象指定写入的风格和编码方法。
	 * 调用OutputFormat.createPrettyPrint()方法可以获得一个默认的pretty print风格的格式对象。
	 * 对OutputFormat对象调用setEncoding()方法可以指定XML文件的编码方法。
	 * @param doc
	 * @param out
	 * @param encoding
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	/*public void writeTo(Document doc,OutputStream out, String encoding) throws UnsupportedEncodingException, IOException {
	       OutputFormat format = OutputFormat.createPrettyPrint();
	       format.setEncoding("gb2312");
	       XMLWriter writer = new XMLWriter(System.out,format);
	       writer.write(doc);
	       writer.flush();
	}*/
	
	
	
	/**
	 * 遍历根标记下的子元素
	 * @param args
	 */

	public static void read(Element root){	
		for(Iterator i=root.elementIterator();i.hasNext();){
			Element element=(Element)i.next();
			System.out.print(element.getName()+":"+element.getText());
			if(element.getNodeType()==Node.ELEMENT_NODE){
				read(element);
			}
		}

	}
	
	
	
	
	/**
	 * 写入操作
	 * @param fileName
	 */
	public static void write(String fileName){
		
		Document document=DocumentHelper.createDocument();//建立document对象，用来操作xml文件
		Element booksElement=document.addElement("books");//建立根节点
		booksElement.addComment("This is a test for dom4j ");//加入一行注释
		Element bookElement=booksElement.addElement("book");//添加一个book节点
		bookElement.addAttribute("show","yes");//添加属性内容
		Element titleElement=bookElement.addElement("title");//添加文本节点
		titleElement.setText("ajax in action");//添加文本内容
		try{
			
			XMLWriter writer=new XMLWriter(new FileWriter(new File(fileName))); 
			writer.write(document);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * 修改XML文件
	 */
	public static void modifyXMLFile() {
		String oldStr = "test.xml";
		String newStr = "test1.xml";
		Document document = null;
		//修改节点的属性
		try {
			SAXReader saxReader = new SAXReader(); // 用来读取xml文档
			document = saxReader.read(new File(oldStr)); // 读取xml文档
			List list = document.selectNodes("/books/book/@show");// 用xpath查找节点book的属性
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			if (attribute.getValue().equals("yes"))
			    attribute.setValue("no");
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		//修改节点的内容
		try {
			SAXReader saxReader = new SAXReader(); // 用来读取xml文档
			document = saxReader.read(new File(oldStr)); // 读取xml文档
			List list = document.selectNodes("/books/book/title");// 用xpath查找节点book的内容
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
			Element element = (Element) iter.next();
			element.setText("xxx");// 设置相应的内容
		}
		} catch (Exception e) {
		    e.printStackTrace();
		}

		
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(new File(newStr)));
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		Document doc=load("student2.xml");
		Element root=getRootElement(doc);
		read(root);
	write("test.xml");
		modifyXMLFile();
	}
	
	
	
	

}
