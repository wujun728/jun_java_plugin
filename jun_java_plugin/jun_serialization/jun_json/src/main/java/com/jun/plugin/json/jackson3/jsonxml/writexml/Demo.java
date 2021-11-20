package com.jun.plugin.json.jackson3.jsonxml.writexml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Created by dzy on 2018/9/3
 */

public class Demo {
  /**
   * DOM方式生成xml文档
   */
  public void DOMCreateXML() {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.newDocument();
      doc.setXmlStandalone(true);
      Element bookstore = doc.createElement("bookStore");
      Element book = doc.createElement("book");
      Element name = doc.createElement("name");
      name.setTextContent("冰与火之歌");
      book.appendChild(name);
      book.setAttribute("id", "1");
      bookstore.appendChild(book);
      doc.appendChild(bookstore);
      TransformerFactory tff = TransformerFactory.newInstance();
      Transformer tf = tff.newTransformer();
      tf.setOutputProperty(OutputKeys.INDENT, "yes");
      tf.transform(new DOMSource(doc), new StreamResult(new File("books1.xml")));
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }
  /**
   * SAX方式生成xml文档
   */
  public void SAXCreateXML() {
    SAXTransformerFactory tff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
    TransformerHandler handler;
    try {
      handler = tff.newTransformerHandler();
      Transformer tr = handler.getTransformer();
      tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      tr.setOutputProperty(OutputKeys.INDENT, "yes");
      File file = new File("books2.xml");
      if(!file.exists()){
        file.createNewFile();
      }
      Result result = new StreamResult(new FileOutputStream(file));
      handler.setResult(result);
      handler.startDocument();
      AttributesImpl attr = new AttributesImpl();
      handler.startElement("", "", "bookStore", attr);
      attr.clear();
      attr.addAttribute("", "", "id", "", "1");
      handler.startElement("", "", "book", attr);
      attr.clear();
      handler.startElement("", "", "name", attr);
      handler.characters("冰与火之歌".toCharArray(), 0, "冰与火之歌".length());
      handler.endElement("", "", "name");
      handler.endElement("", "", "book");
      handler.endElement("", "", "bookStore");
      handler.endDocument();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }
  }

  /**
   * DOM4J方式生成xml文档
   */
  public void DOM4JCreateXML() {
    org.dom4j.Document document = DocumentHelper.createDocument();
    org.dom4j.Element bookstore = document.addElement("bookStore");
    org.dom4j.Element book = bookstore.addElement("book");
    book.addAttribute("id", "1");
    org.dom4j.Element name = book.addElement("name");
    name.setText("冰与火之歌");
    OutputFormat format = OutputFormat.createPrettyPrint();
    format.setEncoding("UTF-8");
    File file = new File("books4.xml");
    XMLWriter writer;
    try {
      writer = new XMLWriter(new FileOutputStream(file), format);
      writer.setEscapeText(false);
      writer.write(document);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @Test
  public void testPerformance() throws Exception {
    // 测试结果：sax性能与dom4j其实差不多
    System.out.println("性能测试：");
    long start = 0;

    for (int i = 0; i < 2; i++) {

      // 测试DOM4J性能
      start = System.currentTimeMillis();
      DOM4JCreateXML();
      System.out.println("DOM4J:" + (System.currentTimeMillis() - start));

      // 测试DOM性能
      start = System.currentTimeMillis();
      DOMCreateXML();
      System.out.println("DOM:" + (System.currentTimeMillis() - start));

      // 测试SAX性能
      start = System.currentTimeMillis();
      SAXCreateXML();
      System.out.println("SAX:" + (System.currentTimeMillis() - start));
    }


  }
}
