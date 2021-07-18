

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;  
  
public class DOM4jDemo222 {  
  
    public int createXMLFile(String filename) {  
  
        int returnValue = 0;  
  
        Document document = DocumentHelper.createDocument();  
  
        Element booksElement = document.addElement("books");  
  
        booksElement.addComment("This is a test for dom4j, holen, 2004.9.11");  
  
        Element bookElement = booksElement.addElement("book");  
        /** ����show�������� */  
        bookElement.addAttribute("show", "yes");  
        /** ����title�ڵ� */  
        Element titleElement = bookElement.addElement("title");  
        /** Ϊtitle�������� */  
        titleElement.setText("Dom4j Tutorials");  
  
        /** ���Ƶ���ɺ�����book */  
        bookElement = booksElement.addElement("book");  
        bookElement.addAttribute("show", "yes");  
        titleElement = bookElement.addElement("title");  
        titleElement.setText("Lucene Studing");  
        bookElement = booksElement.addElement("book");  
        bookElement.addAttribute("show", "no");  
        titleElement = bookElement.addElement("title");  
        titleElement.setText("Lucene in Action");  
  
        /** ����owner�ڵ� */  
        Element ownerElement = booksElement.addElement("owner");  
        ownerElement.setText("O'Reilly");  
        try {  
            /** ��document�е�����д���ļ��� */  
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)));  
            writer.write(document);  
            writer.close();  
            /** ִ�гɹ�,�践��1 */  
            returnValue = 1;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return returnValue;  
    }  
  
    /** 
     * �޸�XML�ļ�������,�����Ϊһ�����ļ� �ص�����dom4j�������ӽڵ�,�޸Ľڵ�,ɾ���ڵ� 
     *  
     * @param filename 
     *            �޸Ķ����ļ� 
     * @param newfilename 
     *            �޸ĺ����Ϊ���ļ� 
     * @return ���ز������, 0��ʧ��, 1��ɹ� 
     */  
    public int ModiXMLFile(String filename, String newfilename) {  
        int returnValue = 0;  
        try {  
            SAXReader saxReader = new SAXReader();  
            Document document = saxReader.read(new File(filename));  
            /** �޸�����֮һ: ���book�ڵ���show���Ե�����Ϊyes,���޸ĳ�no */  
            /** ����xpath���Ҷ��� */  
            List list = document.selectNodes("/books/book/@show");  
            Iterator iter = list.iterator();  
            while (iter.hasNext()) {  
                Attribute attribute = (Attribute) iter.next();  
                if (attribute.getValue().equals("yes")) {  
                    attribute.setValue("no");  
                }  
            }  
            /** 
             * �޸�����֮��: ��owner�����ݸ�ΪTshinghua 
             * ����owner�ڵ��м���date�ڵ�,date�ڵ������Ϊ2004-09-11,��Ϊdate�ڵ����һ������type 
             */  
            list = document.selectNodes("/books/owner");  
            iter = list.iterator();  
            if (iter.hasNext()) {  
                Element ownerElement = (Element) iter.next();  
                ownerElement.setText("Tshinghua");  
                Element dateElement = ownerElement.addElement("date");  
                dateElement.setText("2004-09-11");  
                dateElement.addAttribute("type", "Gregorian calendar");  
            }  
            /** �޸�����֮��: ��title����ΪDom4j Tutorials,��ɾ���ýڵ� */  
            list = document.selectNodes("/books/book");  
            iter = list.iterator();  
            while (iter.hasNext()) {  
                Element bookElement = (Element) iter.next();  
                Iterator iterator = bookElement.elementIterator("title");  
                while (iterator.hasNext()) {  
                    Element titleElement = (Element) iterator.next();  
                    if (titleElement.getText().equals("Dom4j Tutorials")) {  
                        bookElement.remove(titleElement);  
                    }  
                }  
            }  
            try {  
                /** ��document�е�����д���ļ��� */  
                XMLWriter writer = new XMLWriter(new FileWriter(new File(  
                        newfilename)));  
                writer.write(document);  
                writer.close();  
                /** ִ�гɹ�,�践��1 */  
                returnValue = 1;  
            } catch (Exception ex) {  
                ex.printStackTrace();  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return returnValue;  
    }  
  
    /** 
     * ��ʽ��XML�ĵ�,������������� 
     *  
     * @param filename 
     * @return 
     */  
    public int formatXMLFile(String filename) {  
        int returnValue = 0;  
        try {  
            SAXReader saxReader = new SAXReader();  
            Document document = saxReader.read(new File(filename));  
            XMLWriter writer = null;  
            /** ��ʽ�����,����IE���һ�� */  
            OutputFormat format = OutputFormat.createPrettyPrint();  
            /** ָ��XML���� */  
            format.setEncoding("GBK");  
            writer = new XMLWriter(new FileWriter(new File(filename)), format);  
            writer.write(document);  
            writer.close();  
            /** ִ�гɹ�,�践��1 */  
            returnValue = 1;  
  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
  
        return returnValue;  
  
    }  
  
    public static void main(String[] args) {  
  
        DOM4jDemo222 temp = new DOM4jDemo222();  
        System.out.println(temp.createXMLFile("f://holen.xml"));  
        System.out.println(temp.ModiXMLFile("f://holen.xml", "f://holen2.xml"));  
        System.out.println(temp.formatXMLFile("f://holen2.xml"));  
    }  
  
}  