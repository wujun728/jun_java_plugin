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
    //Document
    Document doc = test.read(fileName);
    //øԪ
    Element root = test.getRootElement(doc);
    //Ԫµļ鼮
    test.list(root);
  }

  // ļȡXMLļXMLĵ
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
    // Ϊ鼮Ľڵ
    for (Iterator i = 
      root.elementIterator("鼮"); 
    i.hasNext();) {
      Element book = (Element) i.next();
      // 鼮
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
        book.elementText("");
      String author = 
        book.elementText("");
      String price = 
        book.elementText("۸");
      
      /*String sql = "insert into book" + 
       *" values(?,?,?)";
       *stmt = conn.prepareStatement(sql);
       *stmt.setString(1, bookname);
       *stmt.setString(2, author);
       *stmt.setString(3, price);
       *stmt.executeUpdate();
      */
      
      
      
      System.out.println(bookname);
      System.out.println(book.elementText("۸"));
      System.out.println(book.elementText(""));

      // 鼮ӽڵ㡰ߡ
      for (Iterator it = 
        book.element("").attributeIterator(); 
      it.hasNext();) {
        Attribute attribute = 
          (Attribute) it.next();
        System.out.println(attribute.getName() 
            + ":" + attribute.getValue());
      }
      System.out.println(book.elementText(""));
    }
  }

}
