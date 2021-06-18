package jun_plugin_xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

public class Dom4jDemo {

	public Dom4jDemo() {

	}
	
	public static void main(String[] args) {
		new Dom4jDemo().createXMLFile("dom4j.xml");
	}

	/**
	 * 
	 * ����һ��XML�ĵ�,�ĵ����������������
	 * 
	 * @param filename
	 *            �轨�����ļ���
	 * 
	 * @return ���ز������, 0��ʧ��, 1��ɹ�
	 * 
	 */

	public int createXMLFile(String filename) {

		/** ���ز������, 0��ʧ��, 1��ɹ� */

		int returnValue = 0;

		/** ����document���� */

		Document document = DocumentHelper.createDocument();

		/** ����XML�ĵ��ĸ�books */

		Element booksElement = document.addElement("books");

		/** ����һ��ע�� */

		booksElement.addComment("This is a test for dom4j, holen, 2004.9.11");

		/** �����һ��book�ڵ� */

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
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");

			XMLWriter writer = new XMLWriter(new FileOutputStream(new File(filename)), format);

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
	 * 
	 * �޸�XML�ļ�������,�����Ϊһ�����ļ�
	 * 
	 * �ص�����dom4j�������ӽڵ�,�޸Ľڵ�,ɾ���ڵ�
	 * 
	 * @param filename
	 *            �޸Ķ����ļ�
	 * 
	 * @param newfilename
	 *            �޸ĺ����Ϊ���ļ�
	 * 
	 * @return ���ز������, 0��ʧ��, 1��ɹ�
	 * 
	 */

	public static int ModiXMLFile(String filename, String newfilename, ItemVo itemvo) {

		int returnValue = 0;

		try {

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new File(filename));

			/** �޸�����֮��: ��title����ΪDom4j Tutorials,��ɾ���ýڵ� */

			List list = document.selectNodes("/bcaster/item/@id");
			Iterator iter = list.iterator();
			int length = 0;
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
//				if (attribute.getValue().equals(itemvo.getId())) {
					length = attribute.getParent().attributes().size();
					if (length > 2) {
//						attribute.getParent().attribute(0).setValue(itemvo.getItem_url());
//						attribute.getParent().attribute(1).setValue(itemvo.getLink());
//						attribute.getParent().attribute(2).setValue(itemvo.getId());
					} else {
						document.getRootElement().remove(attribute.getParent());
						Element emp1Element = DocumentHelper.createElement("item");
//						emp1Element.addAttribute("item_url", itemvo.getItem_url());
//						emp1Element.addAttribute("link", itemvo.getLink());
//						emp1Element.addAttribute("id", itemvo.getId());
//						emp1Element.addAttribute("titleName", itemvo.getTitleName());
						document.getRootElement().add(emp1Element);
					}
				}


			try {

				/** ��document�е�����д���ļ��� */
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");

				XMLWriter writer = new XMLWriter(new FileOutputStream(new File(newfilename)), format);

				writer.write(document);

				writer.close();

				/** ִ�гɹ�,�践��1 */
				Dom4jDemo.formatXMLFile(newfilename);

				returnValue = 1;

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return returnValue;

	}

	/******
	 * ����xml�ļ��е�����
	 * 
	 * @param filename
	 * @param newfilename
	 * @param itemvo
	 * @return xml�ļ� ,�ڵ�λ�� ʹ��xPath����://book[@type='society'],�ڵ�����, �ڵ���ֵ
	 * @return ���ز������, 0��ʧ��, 1��ɹ�
	 */
	public static int updateXMLFileAttribute(String filePathAndName, String newfilePathAndName, String noteXPath,
			String[] attributeName, String[] attributeValue) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(filePathAndName));

			// ��ȡ�ڵ�λ��
			Element noteElement = (Element) document.selectSingleNode(noteXPath);

			if (attributeName != null && attributeValue != null && attributeName.length == attributeValue.length) {
				for (int j = 0; j < attributeName.length; j++) {
					Attribute attribute = noteElement.attribute(attributeName[j]);
					// System.out.print("������:"+attribute.getName()+"
					// ����ֵ:"+attribute.getValue());
					// �Ƴ�����
					if (attribute != null) {
						noteElement.remove(attribute);
						noteElement.addAttribute(attributeName[j], attributeValue[j]);
					}
				}

			}

			/** ��document�е�����д���ļ��� */
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");

			XMLWriter writer = new XMLWriter(new FileOutputStream(new File(newfilePathAndName)), format);

			writer.write(document);

			writer.close();

			/** ִ�гɹ�,�践��1 */
			Dom4jDemo.formatXMLFile(newfilePathAndName);

			return 1;

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public static int deleteXMLFile(String filename, String newfilename, ItemVo itemvo) {

		int returnValue = 0;

		try {

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new File(filename));

			/** �޸�����֮��: ��title����ΪDom4j Tutorials,��ɾ���ýڵ� */

			List list = document.selectNodes("/bcaster/item/@id");
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
//				if (attribute.getValue().equals(itemvo.getId())) {
//					document.getRootElement().remove(attribute.getParent());
//				}

			}
			try {

				/** ��document�е�����д���ļ��� */
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");
				XMLWriter writer = new XMLWriter(new FileOutputStream(new File(newfilename)), format);
				writer.write(document);

				writer.close();

				/** ִ�гɹ�,�践��1 */
				Dom4jDemo.formatXMLFile(filename);

				returnValue = 1;

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return returnValue;

	}

	public static int addXMLFile(String filename, String newfilename, ItemVo itemvo) {

		int returnValue = 0;

		try {

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new File(filename));

			// ���Ա���ڵ� "employee"
			Element emp1Element = DocumentHelper.createElement("item");
//			emp1Element.addAttribute("item_url", itemvo.getItem_url());
//			emp1Element.addAttribute("link", itemvo.getLink());
//			emp1Element.addAttribute("id", itemvo.getId());
//			emp1Element.addAttribute("titleName", itemvo.getTitleName());
			document.getRootElement().add(emp1Element);

			try {

				/** ��document�е�����д���ļ��� */
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");
				XMLWriter writer = new XMLWriter(new FileOutputStream(new File(newfilename)), format);

				writer.write(document);

				writer.close();

				/** ִ�гɹ�,�践��1 */
				Dom4jDemo.formatXMLFile(filename);

				returnValue = 1;

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return returnValue;

	}

	public static List loadItemList(String filename) {

		List list = new ArrayList();

		try {

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new File(filename));

			List xmllist = document.selectNodes("/bcaster/item");
			for (int i = 0, n = xmllist.size(); i < n; i++)

			{
				ItemVo itemvo = new ItemVo();

				DefaultElement itemElement = (DefaultElement) xmllist.get(i);
				String item_url = itemElement.attributeValue("item_url");
				String link = itemElement.attributeValue("link");
				String id = itemElement.attributeValue("id");
				String titleName = itemElement.attributeValue("titleName");

//				itemvo.setId(id);
//				itemvo.setItem_url(item_url);
//				itemvo.setLink(link);
//				itemvo.setTitleName(titleName);
				list.add(itemvo);

				// System.out.println(item_url + "===========" + link);

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return list;

	}

	/**
	 * 
	 * ��ʽ��XML�ĵ�,�������������
	 * 
	 * @param filename
	 * 
	 * @return
	 * 
	 */

	public static int formatXMLFile(String filename) {

		int returnValue = 0;

		try {

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new File(filename));

			XMLWriter output = null;

			/** ��ʽ�����,����IE���һ�� */

			OutputFormat format = OutputFormat.createPrettyPrint();

			/** ָ��XML�ַ������� */

			format.setEncoding("UTF-8");

			output = new XMLWriter(new FileOutputStream(new File(filename)), format);

			output.write(document);

			output.close();

			/** ִ�гɹ�,�践��1 */

			returnValue = 1;

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return returnValue;

	}

}