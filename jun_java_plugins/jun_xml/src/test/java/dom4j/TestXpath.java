package dom4j;

import org.dom4j.io.*;
import org.dom4j.*;
import java.util.*;

public class TestXpath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new TestXpath().findBooks();
		new TestXpath().findBooksByTitle();
	}

	public void findBooks() {
		SAXReader reader = new SAXReader();
		try {
			Document doc = 
        reader.read("src/books.xml");
			Node root = 
        doc.selectSingleNode("/bookstore");
      //root.selectNodes("book[author='']");
      //root.selectNodes("book[price<40]");
      //root.selectNodes("book[@category='WEB']");
			//root.selectNodes("book[title[@lang='zh']]");
			//root.selectNodes("book[author='ӹ' and price>50]");
			//root.selectNodes("book[title[@lang='zh'] and price>50]");
      //root.selectNodes("book[author='ӹ' or author='']");
      List list = 
        root.selectNodes("book[title='˲']");
			for (Object o : list) {
				Element e = (Element) o;//e = book
				System.out.println(e.elementText("title"));
				System.out.println(e.elementText("author"));
				System.out.println(e.elementText("year"));
				System.out.println(e.elementText("price"));
				String show = 
          e.element("title")
          .attributeValue("lang");
				System.out.println("lang = " 
            + show + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findBooksByTitle() {
		SAXReader reader = new SAXReader();
		try {
			Document doc = 
        reader.read("src/books.xml");
			Node root = 
        doc.selectSingleNode("/bookstore");
			List list = 
        root.
        selectNodes("book/title[@lang='zh']");
			for (Object o : list) {
				Element e = (Element) o;//e = title
				System.out.println(e.getStringValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
