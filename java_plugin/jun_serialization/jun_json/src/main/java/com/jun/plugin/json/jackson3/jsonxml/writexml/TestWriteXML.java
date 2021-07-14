package com.jun.plugin.json.jackson3.jsonxml.writexml;

import org.apache.commons.lang3.time.StopWatch;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.helpers.AttributesImpl;

import com.jun.plugin.json.jackson3.jsonxml.Dom4jUtil;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dzy on 2018/9/3
 * 测试 Sax ，Dom4j的方式生成XML Doc，最终发现速度基本一致
 * Ref : https://www.cnblogs.com/9513-/p/7466808.html
 */
public class TestWriteXML {

  /**
   * 以Sax的方式生成XML Doc
   */
  private static void sax(){
    SAXTransformerFactory tff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
    TransformerHandler handler;
    try{
      handler = tff.newTransformerHandler();
      Transformer tr = handler.getTransformer();;
      tr.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
      tr.setOutputProperty(OutputKeys.INDENT,"yes");
      File file = new File("booksBySax.xml");
      if (!file.exists()){
        file.createNewFile();
      }
      Result result = new StreamResult(new FileOutputStream(file));
      handler.setResult(result);
      handler.startDocument();
      AttributesImpl attr = new AttributesImpl();
      handler.startElement("","","bookStore",attr);
      attr.clear();
      attr.addAttribute("","","id","","1");
      attr.addAttribute("","","desc","","test desc");
      handler.startElement("","","book",attr);
      attr.clear();

      handler.startElement("", "", "name", attr);
      handler.characters("冰与火之歌".toCharArray(), 0, "冰与火之歌".length());
      handler.endElement("", "", "name");
      handler.endElement("", "", "book");
      handler.endElement("", "", "bookStore");
      handler.endDocument();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void dom4j() {
    org.dom4j.Document document = DocumentHelper.createDocument();
    org.dom4j.Element bookstore = document.addElement("bookStore");
    org.dom4j.Element book = bookstore.addElement("book");
    book.addAttribute("id", "1");
    org.dom4j.Element name = book.addElement("name");
    name.setText("冰与火之歌");
    OutputFormat format = OutputFormat.createPrettyPrint();
    format.setEncoding("UTF-8");
    File file = new File("booksByDom4j.xml");
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

  private static void test1(){
    System.out.println("性能测试：");
    // 测试DOM4J性能
    long start = System.currentTimeMillis();
    dom4j();
    System.out.println("DOM4J:"+(System.currentTimeMillis()-start));

    // 测试SAX性能
    start =System.currentTimeMillis();
    sax();
    System.out.println("SAX:"+(System.currentTimeMillis()-start));
  }

  public static void main(String[] args) {
//    test1();
    test3();
  }

  private static void test2() {
    StopWatch sw = new StopWatch();
    sw.start();
    sax();
    sw.stop();
    System.out.println(" sax used time: "+sw.getTime());

    sw.reset();
    sw.start();
    dom4j();
    sw.stop();
    System.out.println(" dom4j used time: "+sw.getTime());
  }

  private static void test3(){
    Document doc = Dom4jUtil.getDocFromFile("v:\\TreeOrgan2.xml");
    byte[] zipDats = Dom4jUtil.writeDocToZipBytes(doc);

    Document doc2 = Dom4jUtil.getDocFromZipBytes(zipDats);
    System.out.println(doc2.getRootElement().elements().size());
  }
}
