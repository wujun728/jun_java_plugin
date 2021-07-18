

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //��ȡһ��SAXParserFactory��ʵ��
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //ͨ��factory��ȡSAXParserʵ��
        try {
            SAXParser parser = factory.newSAXParser();
            //����SAXParserHandler����
            SAXParserHandler handler = new SAXParserHandler();
            parser.parse("books.xml", handler);
            System.out.println("~��~��~������" + handler.getBookList().size()
                    + "����");
            for (Book book : handler.getBookList()) {
                System.out.println(book.getId());
                System.out.println(book.getName());
                System.out.println(book.getAuthor());
                System.out.println(book.getYear());
                System.out.println(book.getPrice());
                System.out.println(book.getLanguage());
                System.out.println("----finish----");
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class SAXParserHandler extends DefaultHandler {
    String value = null;
    Book book = null;
    private ArrayList<Book> bookList = new ArrayList<Book>();
    public ArrayList<Book> getBookList() {
        return bookList;
    }

    int bookIndex = 0;
    /**
     * ������ʶ������ʼ
     */
    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.startDocument();
        System.out.println("SAX������ʼ");
    }
    
    /**
     * ������ʶ��������
     */
    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
        System.out.println("SAX��������");
    }
    
    /**
     * ����xmlԪ��
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        //����DefaultHandler���startElement����
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("book")) {
            bookIndex++;
            //����һ��book����
            book = new Book();
            //��ʼ����bookԪ�ص�����
            System.out.println("======================��ʼ����ĳһ���������=================");
            //��֪��bookԪ�������Ե������Լ���������λ�ȡ�������Լ�����ֵ
            int num = attributes.getLength();
            for(int i = 0; i < num; i++){
                System.out.print("bookԪ�صĵ�" + (i + 1) +  "���������ǣ�"
                        + attributes.getQName(i));
                System.out.println("---����ֵ�ǣ�" + attributes.getValue(i));
                if (attributes.getQName(i).equals("id")) {
                    book.setId(attributes.getValue(i));
                }
            }
        }
        else if (!qName.equals("name") && !qName.equals("bookstore")) {
            System.out.print("�ڵ����ǣ�" + qName + "---");
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //����DefaultHandler���endElement����
        super.endElement(uri, localName, qName);
        //�ж��Ƿ����һ�����Ѿ���������
        if (qName.equals("book")) {
            bookList.add(book);
            book = null;
            System.out.println("======================��������ĳһ���������=================");
        }
        else if (qName.equals("name")) {
            book.setName(value);
        }
        else if (qName.equals("author")) {
            book.setAuthor(value);
        }
        else if (qName.equals("year")) {
            book.setYear(value);
        }
        else if (qName.equals("price")) {
            book.setPrice(value);
        }
        else if (qName.equals("language")) {
            book.setLanguage(value);
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        value = new String(ch, start, length);
        if (!value.trim().equals("")) {
            System.out.println("�ڵ�ֵ�ǣ�" + value);
        }
    }
}