

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMTest {
    public static void main(String[] args) {
        //����һ��DocumentBuilderFactory�Ķ���
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //����һ��DocumentBuilder�Ķ���
        try {
            //����DocumentBuilder����
            DocumentBuilder db = dbf.newDocumentBuilder();
            //ͨ��DocumentBuilder�����parser��������books.xml�ļ�����ǰ��Ŀ��
            Document document = db.parse("books.xml");
            //��ȡ����book�ڵ�ļ���
            NodeList bookList = document.getElementsByTagName("book");
            //ͨ��nodelist��getLength()�������Ի�ȡbookList�ĳ���
            System.out.println("һ����" + bookList.getLength() + "����");
            //����ÿһ��book�ڵ�
            for (int i = 0; i < bookList.getLength(); i++) {
                System.out.println("=================���濪ʼ������" + (i + 1) + "���������=================");
                //ͨ�� item(i)���� ��ȡһ��book�ڵ㣬nodelist������ֵ��0��ʼ
                Node book = bookList.item(i);
                //��ȡbook�ڵ���������Լ���
                NamedNodeMap attrs = book.getAttributes();
                System.out.println("�� " + (i + 1) + "���鹲��" + attrs.getLength() + "������");
                //����book������
                for (int j = 0; j < attrs.getLength(); j++) {
                    //ͨ��item(index)������ȡbook�ڵ��ĳһ������
                    Node attr = attrs.item(j);
                    //��ȡ������
                    System.out.print("��������" + attr.getNodeName());
                    //��ȡ����ֵ
                    System.out.println("--����ֵ" + attr.getNodeValue());
                }
                //����book�ڵ���ӽڵ�
                NodeList childNodes = book.getChildNodes();
                //����childNodes��ȡÿ���ڵ�Ľڵ����ͽڵ�ֵ
                System.out.println("��" + (i+1) + "���鹲��" + 
                childNodes.getLength() + "���ӽڵ�");
                for (int k = 0; k < childNodes.getLength(); k++) {
                    //���ֳ�text���͵�node�Լ�element���͵�node
                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        //��ȡ��element���ͽڵ�Ľڵ���
                        System.out.print("��" + (k + 1) + "���ڵ�Ľڵ�����" 
                        + childNodes.item(k).getNodeName());
                        //��ȡ��element���ͽڵ�Ľڵ�ֵ
                        System.out.println("--�ڵ�ֵ�ǣ�" + childNodes.item(k).getFirstChild().getNodeValue());
                        //System.out.println("--�ڵ�ֵ�ǣ�" + childNodes.item(k).getTextContent());
                    }
                }
                System.out.println("======================����������" + (i + 1) + "���������=================");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
}