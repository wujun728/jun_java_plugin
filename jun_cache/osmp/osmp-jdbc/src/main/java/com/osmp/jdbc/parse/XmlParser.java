/*   
 * Project: OSMP
 * FileName: XmlParser.java
 * version: V1.0
 */
package com.osmp.jdbc.parse;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * xml解析工具类
 * @author heyu
 *
 */
public class XmlParser {
    private File xmlFile;
    
    private XPath xpath;
    
    private Document document;
    
    private XmlParser(File xmlFile) throws FileNotFoundException, Exception {
        this.xmlFile = xmlFile;
        xpath = XPathFactory.newInstance().newXPath();
        document = createDocument();
    }
    
    private Document createDocument() throws Exception {
        try {
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

          factory.setNamespaceAware(true);
          factory.setIgnoringComments(true);
          factory.setIgnoringElementContentWhitespace(true);
          factory.setCoalescing(false);
          //忽略dtd校验
          factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

          DocumentBuilder builder = factory.newDocumentBuilder();
          //解析本地dtd
//          builder.setEntityResolver(new LocalDtdResolver());
          builder.setErrorHandler(new ErrorHandler() {
            public void error(SAXParseException exception) throws SAXException {
              throw exception;
            }

            public void fatalError(SAXParseException exception) throws SAXException {
              throw exception;
            }

            public void warning(SAXParseException exception) throws SAXException {
            }
          });
          return builder.parse(xmlFile);
        } catch (Exception e) {
            throw e;
        }
      }
    
    public static XmlParser createSqlXmlParser(File xmlFile) throws FileNotFoundException, Exception {
        XmlParser xmlParser = new XmlParser(xmlFile);
        return xmlParser;
    }
    
    public Node evalRootNode(String expression) throws XPathExpressionException {
        return (Node)xpath.evaluate(expression, document, XPathConstants.NODE);
    }
    
    public Node evalNode(Object root, String expression) throws XPathExpressionException {
        return (Node)xpath.evaluate(expression, root, XPathConstants.NODE);
    }
    
}
