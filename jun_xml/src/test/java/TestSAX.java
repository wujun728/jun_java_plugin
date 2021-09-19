

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Wujun
 */
public class TestSAX {

    public static void main(String[] args) {
        read();
        write();
    }
    
    public static void read() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            InputStream in = TestSAX.class.getClassLoader().getResourceAsStream("university.xml");
            parser.parse(in, new MyHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write() {
        System.err.println("纯SAX对于写操作无能为力");
    }
    
}

// 重写对自己感兴趣的事件处理方法
class MyHandler extends DefaultHandler {

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws IOException, SAXException {
        return super.resolveEntity(publicId, systemId);
    }

    @Override
    public void notationDecl(String name, String publicId, String systemId)
            throws SAXException {
        super.notationDecl(name, publicId, systemId);
    }

    @Override
    public void unparsedEntityDecl(String name, String publicId,
            String systemId, String notationName) throws SAXException {
        super.unparsedEntityDecl(name, publicId, systemId, notationName);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        System.err.println("开始解析文档");
    }

    @Override
    public void endDocument() throws SAXException {
        System.err.println("解析结束");
    }

    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        super.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        super.endPrefixMapping(prefix);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        System.err.print("Element: " + qName + ", attr: ");
        print(attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        super.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        super.processingInstruction(target, data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        super.skippedEntity(name);
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        super.warning(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        super.fatalError(e);
    }
    
    private void print(Attributes attrs) {
        if (attrs == null) return;
        System.err.print("[");
        for (int i = 0; i < attrs.getLength(); i++) {
            System.err.print(attrs.getQName(i) + " = " + attrs.getValue(i));
            if (i != attrs.getLength() - 1) {
                System.err.print(", ");
            }
        }
        System.err.println("]");
    }
}