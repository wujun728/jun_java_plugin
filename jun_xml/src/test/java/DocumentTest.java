

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest {
	private SAXReader reader = null;

	@Before
	public void init() {
		reader = new SAXReader();
	}

	@After
	public void destory() {
		reader = null;
		System.gc();
	}

	public void fail(Object o) {
		if (o != null)
			System.out.println(o);
	}

	// �ĵ���ʽ���£�
	// <?xml version="1.0" encoding="UTF-8"?>
	// <catalog>
	// <!--An XML Catalog-->
	// <?target instruction?>
	// <journal title="XML Zone" publisher="IBM developerWorks">
	// <article level="Intermediate" date="December-2001">
	// <title>Java configuration with XML Schema</title>
	// <author>
	// <firstname>Marcello</firstname>
	// <lastname>Vitaletti</lastname>
	// </author>
	// </article>
	// </journal>
	// </catalog>

	@Test
	public void createDocument() throws IOException {
		// ����һƪ�ĵ�
		Document doc = DocumentHelper.createDocument();
		// ���һ��Ԫ��
		Element root = doc.addElement("catalog");
		// ΪrootԪ�����ע��
		root.addComment("An XML Catalog");
		// ��ӱ��
		root.addProcessingInstruction("target", "instruction");
		// ����Ԫ��
		Element journalEl = new BaseElement("journal");
		// �������
		journalEl.addAttribute("title", "XML Zone");
		journalEl.addAttribute("publisher", "IBM developerWorks");
		root.add(journalEl);
		// ���Ԫ��
		Element articleEl = journalEl.addElement("article");
		articleEl.addAttribute("level", "Intermediate");
		articleEl.addAttribute("date", "December-2001");
		Element titleEl = articleEl.addElement("title");
		// �����ı�����
		titleEl.setText("Java configuration with XML Schema");
		// titleEl.addText("Java configuration with XML Schema");
		Element authorEl = articleEl.addElement("author");
		authorEl.addElement("firstname").setText("Marcello");
		authorEl.addElement("lastname").addText("Vitaletti");
		// ����ʹ�� addDocType() ��������ĵ�����˵����
		// doc.addDocType("catalog", null, "file://c:/Dtds/catalog.dtd");
		fail(doc.getRootElement().getName());
		// ��xmlת�����ı�
		fail(doc.asXML());
		// д�뵽�ļ�
		XMLWriter  output = new XMLWriter(new FileWriter(new File("D:/catalog.xml"))); 
		output.write(doc); 
		output.close();
	}

	/**
	 * DocumentHelper��һ���ĵ������ࣨ�����ࣩ������������ĵ���Ԫ�ء��ı������ԡ�ע�͡�CDATA��Namespace��XPath�Ĵ������Լ�����XPath����ĵ��ı����ͽ��ı�ת����Document��
	 * parseText��ɽ�xml�ַ���ת����Doc�Ĺ��� Document doc =
	 * DocumentHelper.parseText("<root></root>");
	 * 
	 * createDocument����һ���ĵ� Document doc = DocumentHelper.createDocument();
	 * ����������ͻᴴ��һ�����и�Ԫ�ص��ĵ� createElement_x����һ��Ԫ�� Element el =
	 * DocumentHelper.createElement_x("el");
	 * 
	 * Document��addElement�������Ը���ǰ�ĵ����һ����Ԫ�� Element root =
	 * doc.addElement("catalog");
	 * 
	 * addComment�����������һ��ע�� root.addComment("An XML Catalog"); ΪrootԪ�����һ��ע��
	 * addProcessingInstruction���һ����� root.addProcessingInstruction("target",
	 * "instruction"); ΪrootԪ�����һ����� new BaseElement���Դ���һ��Ԫ�� Element journalEl =
	 * new BaseElement("journal"); addAttribute�������
	 * journalEl.addAttribute("title", "XML Zone");
	 * 
	 * add���һ��Ԫ�� root.add(journalEl); ��journalElԪ����ӵ�rootԪ����
	 * addElement���һ��Ԫ�أ������ص�ǰԪ�� Element articleEl =
	 * journalEl.addElement("article"); ��journalElԪ�����һ����Ԫ��article
	 * setText��addText��������Ԫ�ص��ı�
	 * authorEl.addElement("firstname").setText("Marcello");
	 * authorEl.addElement("lastname").addText("Vitaletti");
	 * 
	 * addDocType���������ĵ���DOCTYPE doc.addDocType("catalog",
	 * null,file://c:/Dtds/catalog.dtd);
	 * 
	 * asXML���Խ��ĵ���Ԫ��ת����һ��xml�ַ��� doc.asXML(); root.asXML();
	 * 
	 * XMLWriter����԰��ĵ�д�뵽�ļ��� output = new XMLWriter(new FileWriter(new
	 * File("file/catalog.xml"))); output.write(doc); output.close();
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void modifyDoc() {
		try {
			Document doc = reader.read(new File("D:/catalog.xml"));
			// �޸���������
			List list = doc.selectNodes("//article/@level");
			Iterator<Attribute> iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attr = iter.next();
				fail(attr.getName() + "#" + attr.getValue() + "#" + attr.getText());
				if ("Intermediate".equals(attr.getValue())) {
					// �޸�����ֵ
					attr.setValue("Introductory");
					fail(attr.getName() + "#" + attr.getValue() + "#" + attr.getText());
				}
			}
			list = doc.selectNodes("//article/@date");
			iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attr = iter.next();
				fail(attr.getName() + "#" + attr.getValue() + "#" + attr.getText());
				if ("December-2001".equals(attr.getValue())) {
					// �޸�����ֵ
					attr.setValue("December-2011");
					fail(attr.getName() + "#" + attr.getValue() + "#" + attr.getText());
				}
			}
			// �޸Ľڵ�����
			list = doc.selectNodes("//article");
			Iterator<Element> it = list.iterator();
			while (it.hasNext()) {
				Element el = it.next();
				fail(el.getName() + "#" + el.getText() + "#" + el.getStringValue());
				// �޸�titleԪ��
				Iterator<Element> elIter = el.elementIterator("title");
				while (elIter.hasNext()) {
					Element titleEl = elIter.next();
					fail(titleEl.getName() + "#" + titleEl.getText() + "#" + titleEl.getStringValue());
					if ("Java configuration with XML Schema".equals(titleEl.getTextTrim())) {
						// �޸�Ԫ���ı�ֵ
						titleEl.setText("Modify the Java configuration with XML Schema");
						fail(titleEl.getName() + "#" + titleEl.getText() + "#" + titleEl.getStringValue());
					}
				}
			}
			// �޸Ľڵ���Ԫ������
			list = doc.selectNodes("//article/author");
			it = list.iterator();
			while (it.hasNext()) {
				Element el = it.next();
				fail(el.getName() + "#" + el.getText() + "#" + el.getStringValue());
				List<Element> childs = el.elements();
				for (Element e : childs) {
					fail(e.getName() + "#" + e.getText() + "#" + e.getStringValue());
					if ("Marcello".equals(e.getTextTrim())) {
						e.setText("Ayesha");
					} else if ("Vitaletti".equals(e.getTextTrim())) {
						e.setText("Malik");
					}
					fail(e.getName() + "#" + e.getText() + "#" + e.getStringValue());
				}
			}
			// д�뵽�ļ�
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}