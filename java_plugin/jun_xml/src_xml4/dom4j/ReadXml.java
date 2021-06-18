package dom4j;

import org.dom4j.*;
import org.dom4j.io.*;
import java.util.*;
import java.io.*;

public class ReadXml {

  public static void main(String args[]) throws DocumentException {
    String fileName = 
      "src/dom4j/book.xml";
    ReadXml test = new ReadXml();
    //获得Document对象
    Document doc = test.read(fileName);
    //获得根元素
    Element root = test.getRootElement(doc);
    //遍历根元素下的计算机书籍
    test.list(root);
  }

  // 从文件读取XML，输入文件名，返回XML文档
  public Document read(String fileName) 
  throws DocumentException {
    SAXReader reader = new SAXReader();
    Document document = 
      reader.read(new File(fileName));
    return document;
  }

  public Element getRootElement(Document doc) {
    return doc.getRootElement();
  }

  public void list(Element root) {
    // 遍历名称为计算机书籍的节点
    for (Iterator i = 
      root.elementIterator("计算机书籍"); 
    i.hasNext();) {
      Element book = (Element) i.next();
      // 遍历计算机书籍的属性
      for (Iterator it = 
        book.attributeIterator(); 
      it.hasNext();) {
        Attribute attribute = 
          (Attribute) it.next();
        System.out.println(
            attribute.getName() 
            + ":" + attribute.getValue());
      }

      String bookname = 
        book.elementText("书名");
      String author = 
        book.elementText("作者");
      String price = 
        book.elementText("价格");
      
      /*String sql = "insert into book" + 
       *" values(?,?,?)";
       *stmt = conn.prepareStatement(sql);
       *stmt.setString(1, bookname);
       *stmt.setString(2, author);
       *stmt.setString(3, price);
       *stmt.executeUpdate();
      */
      
      
      
      System.out.println(bookname);
      System.out.println(book.elementText("价格"));
      System.out.println(book.elementText("作者"));

      // 遍历计算机书籍的子节点“作者”的属性
      for (Iterator it = 
        book.element("作者").attributeIterator(); 
      it.hasNext();) {
        Attribute attribute = 
          (Attribute) it.next();
        System.out.println(attribute.getName() 
            + ":" + attribute.getValue());
      }
      System.out.println(book.elementText("简介"));
    }
  }

}
