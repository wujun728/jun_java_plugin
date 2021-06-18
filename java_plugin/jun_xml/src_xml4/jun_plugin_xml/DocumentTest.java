package jun_plugin_xml;

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

	// 文档格式如下：
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
		// 创建一篇文档
		Document doc = DocumentHelper.createDocument();
		// 添加一个元素
		Element root = doc.addElement("catalog");
		// 为root元素添加注释
		root.addComment("An XML Catalog");
		// 添加标记
		root.addProcessingInstruction("target", "instruction");
		// 创建元素
		Element journalEl = new BaseElement("journal");
		// 添加属性
		journalEl.addAttribute("title", "XML Zone");
		journalEl.addAttribute("publisher", "IBM developerWorks");
		root.add(journalEl);
		// 添加元素
		Element articleEl = journalEl.addElement("article");
		articleEl.addAttribute("level", "Intermediate");
		articleEl.addAttribute("date", "December-2001");
		Element titleEl = articleEl.addElement("title");
		// 设置文本内容
		titleEl.setText("Java configuration with XML Schema");
		// titleEl.addText("Java configuration with XML Schema");
		Element authorEl = articleEl.addElement("author");
		authorEl.addElement("firstname").setText("Marcello");
		authorEl.addElement("lastname").addText("Vitaletti");
		// 可以使用 addDocType() 方法添加文档类型说明。
		// doc.addDocType("catalog", null, "file://c:/Dtds/catalog.dtd");
		fail(doc.getRootElement().getName());
		// 将xml转换成文本
		fail(doc.asXML());
		// 写入到文件
		XMLWriter  output = new XMLWriter(new FileWriter(new File("D:/catalog.xml"))); 
		output.write(doc); 
		output.close();
	}

	/**
	 * DocumentHelper是一个文档助手类（工具类），它可以完成文档、元素、文本、属性、注释、CDATA、Namespace、XPath的创建，以及利用XPath完成文档的遍历和将文本转换成Document；
	 * parseText完成将xml字符串转换成Doc的功能 Document doc =
	 * DocumentHelper.parseText("<root></root>");
	 * 
	 * createDocument创建一个文档 Document doc = DocumentHelper.createDocument();
	 * 如果带参数就会创建一个带有根元素的文档 createElement_x创建一个元素 Element el =
	 * DocumentHelper.createElement_x("el");
	 * 
	 * Document的addElement方法可以给当前文档添加一个子元素 Element root =
	 * doc.addElement("catalog");
	 * 
	 * addComment方法可以添加一段注释 root.addComment("An XML Catalog"); 为root元素添加一段注释
	 * addProcessingInstruction添加一个标记 root.addProcessingInstruction("target",
	 * "instruction"); 为root元素添加一个标记 new BaseElement可以创建一个元素 Element journalEl =
	 * new BaseElement("journal"); addAttribute添加属性
	 * journalEl.addAttribute("title", "XML Zone");
	 * 
	 * add添加一个元素 root.add(journalEl); 将journalEl元素添加到root元素中
	 * addElement添加一个元素，并返回当前元素 Element articleEl =
	 * journalEl.addElement("article"); 给journalEl元素添加一个子元素article
	 * setText、addText可以设置元素的文本
	 * authorEl.addElement("firstname").setText("Marcello");
	 * authorEl.addElement("lastname").addText("Vitaletti");
	 * 
	 * addDocType可以设置文档的DOCTYPE doc.addDocType("catalog",
	 * null,file://c:/Dtds/catalog.dtd);
	 * 
	 * asXML可以将文档或元素转换成一段xml字符串 doc.asXML(); root.asXML();
	 * 
	 * XMLWriter类可以把文档写入到文件中 output = new XMLWriter(new FileWriter(new
	 * File("file/catalog.xml"))); output.write(doc); output.close();
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void modifyDoc() {
		try {
			Document doc = reader.read(new File("D:/catalog.xml"));
			// 修改属性内容
			List list = doc.selectNodes("//article/@level");
			Iterator<Attribute> iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attr = iter.next();
				fail(attr.getName() + "#" + attr.getValue() + "#" + attr.getText());
				if ("Intermediate".equals(attr.getValue())) {
					// 修改属性值
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
					// 修改属性值
					attr.setValue("December-2011");
					fail(attr.getName() + "#" + attr.getValue() + "#" + attr.getText());
				}
			}
			// 修改节点内容
			list = doc.selectNodes("//article");
			Iterator<Element> it = list.iterator();
			while (it.hasNext()) {
				Element el = it.next();
				fail(el.getName() + "#" + el.getText() + "#" + el.getStringValue());
				// 修改title元素
				Iterator<Element> elIter = el.elementIterator("title");
				while (elIter.hasNext()) {
					Element titleEl = elIter.next();
					fail(titleEl.getName() + "#" + titleEl.getText() + "#" + titleEl.getStringValue());
					if ("Java configuration with XML Schema".equals(titleEl.getTextTrim())) {
						// 修改元素文本值
						titleEl.setText("Modify the Java configuration with XML Schema");
						fail(titleEl.getName() + "#" + titleEl.getText() + "#" + titleEl.getStringValue());
					}
				}
			}
			// 修改节点子元素内容
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
			// 写入到文件
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}