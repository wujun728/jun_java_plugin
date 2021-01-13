package book.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 使用SAX处理XML文档。SAX是Simple API for XML的缩写。
 * 与DOM比较而言，SAX是一种轻量型的方法。我们知道，在处理DOM的时候，我们需要读入整个的XML文档，然后在内存中创建DOM树，生成DOM树上的每个Node对象。当文档比较小的时候，这不会造成什么问题，但是一旦文档大起来，处理DOM就会变得相当费时费力。特别是其对于内存的需求，也将是成倍的增长，以至于在某些应用中使用DOM是一件很不划算的事（比如在applet中）。这时候，一个较好的替代解决方法就是SAX。
 * SAX在概念上与DOM完全不同。首先，不同于DOM的文档驱动，它是事件驱动的，也就是说，它并不需要读入整个文档，而文档的读入过程也就是SAX的解析过程。所谓事件驱动，是指一种基于回调（callback）机制的程序运行方法。
 */
public class SaxXML {

	public static List readXML(String fileName) throws Exception {
		// 创建SAX解析器工厂对象
		SAXParserFactory spf = SAXParserFactory.newInstance();
		// 使用解析器工厂创建解析器实例
		SAXParser saxParser = spf.newSAXParser();

		// 创建SAX解析器要使用的事件侦听器对象
		StudentSAXHandler handler = new StudentSAXHandler();
		// 开始解析文件
		saxParser.parse(new File(fileName), handler);

		// 获取结果
		return handler.getResult();
	}

	public static void main(String[] args) {

		String filename = "students.xml";
		List studentBeans = null;
		try {
			studentBeans = SaxXML.readXML(filename);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if (studentBeans != null) {
			System.out.println("解析student.xml文档得到的学生信息：");
			for (int i = 0; i < studentBeans.size(); i++) {
				System.out.println(studentBeans.get(i).toString());
			}
		}
	}

	/**
	 * SAX的事件侦听器，当处理特定的XML文件的时候，
	 * 就需要为其创建一个实现了ContentHandler的类来处理特定的事件，
	 * 可以说，这个实际上就是SAX处理XML文件的核心。
	 */
	static class StudentSAXHandler extends DefaultHandler {
		// 保存已经读到过但还没有关闭的标签。
		java.util.Stack tagsStatck = new java.util.Stack();
		List studentBeans = new ArrayList();
		StudentBean bean = null;

		/**
		 * 当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作
		 */
		public void startDocument() throws SAXException {
			System.out.println("------Parse begin--------");
		}

		/**
		 * 当文档结束的时候，调用这个方法，可以在其中做一些善后的工作
		 */
		public void endDocument() throws SAXException {
			System.out.println("------Parse end--------");
		}
		
		/**
		 * 当读到一个开始标签的时候，会触发这个方法.
		 * namespaceURI就是名域，localName是标签名，qName是标签的修饰前缀，
		 * atts是这个标签所包含的属性列表。通过atts，可以得到所有的属性名和相应的值.
		 * <name="">
		 */
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
				throws SAXException {
			tagsStatck.push(qName);
			
			// 如果新的标签是“学生”，则表示接下来要读取学生。这里之所以需要bean为空，是因为放置学生标签的子标签也有“学生”
			if (bean == null) {
				if (qName.equals("学生")){
					System.out.println("------Processing a student--------");
					bean = new StudentBean();
					bean.setGender(atts.getValue("性别"));
				}
			}
		}

		/**
		 * 在遇到结束标签的时候，调用这个方法
		 */
		public void endElement(String namespaceURI, String localName, String qName)
				throws SAXException {
			// 将最近读取的标签弹出
			String currenttag = (String)tagsStatck.pop();
			// 最近读到的标签应该与即将关闭的标签一样。
			if (!currenttag.equals(qName)){
				throw new SAXException("XML文档格式不正确，标签不匹配！");
			}
			// 如果关闭的是"学生"标签，则表示一个StudentBean已经构造完毕了。
			if (qName.equals("学生")){
				System.out.println("------Processing a student end--------");
				// 将bean实例放入学生列表中，同时置空，等待构造下一个实例
				studentBeans.add(bean);
				bean = null;
			}
		}

		/** 
		 * 处理在XML文件中读到字符串
		 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
		 */
		public void characters(char[] chs, int start, int length) throws SAXException {
			//	从栈中得到当前节点的信息
			String tag = (String) tagsStatck.peek();
			String value = new String(chs, start, length);
			
			if (tag.equals("姓名")){
				// 如果最近读到的标签是姓名，则把字符串当作姓名的值
				bean.setName(value);
			} else if (tag.equals("年龄")){
				bean.setAge(Integer.parseInt(value));
			} else if (tag.equals("电话")){
				bean.setPhone(value);
			}
		}
		
		public List getResult(){
			return studentBeans;
		}
	}
}