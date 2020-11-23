package com.jun.plugin.utils;

/* 
 * <p>Company: ������ www.elingke.com </p> 
 * @author Wujun
 * @version 1.0 
 * Created on 2004-11-29 
 * ��Դ�� elinkBSP ����Դ���� 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

public class XmlUtilw3c {
	public static synchronized Document newDocument() {
		Document doc = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = db.newDocument();
		} catch (Exception e) {
//			LogUtil.logException(e);
		}
		return doc;
	}

	public static synchronized Element createRootElement() {
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.newDocument();
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement;
	}

	public static synchronized Element getRootElement(String fileName) {
		if (fileName == null || fileName.length() == 0) {
			return null;
		}
		try {
			Element rootElement = null;
			FileInputStream fis = new FileInputStream(fileName);
			rootElement = getRootElement(fis);
			fis.close();
			return rootElement;
		} catch (Exception e) {
			return null;
		}
	}

	public static synchronized Element getRootElement(InputStream is) {
		if (is == null) {
			return null;
		}
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(is);
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement;
	}

	public static synchronized Element getRootElement(InputSource is) {
		if (is == null) {
			return null;
		}
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(is);
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootElement;
	}

	public static synchronized Element[] getChildElements(Element element) {
		if (element == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node instanceof Element) {
				childs.add((Element) node);
			}
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	public static synchronized Element[] getChildElements(Element element,
			String childName) {
		if (element == null || childName == null || childName.length() == 0) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node instanceof Element) {
				if (node.getNodeName().equals(childName)) {
					childs.add((Element) node);
				}
			}
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	public static synchronized Node[] getChildNodes(Node node) {
		if (node == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			childs.add((Element) n);
		}
		Node[] childNodes = new Element[childs.size()];
		childs.toArray(childNodes);
		return childNodes;
	}

	public static synchronized Element getChildElement(Element element,
			String childName) {
		if (element == null || childName == null || childName.length() == 0) {
			return null;
		}
		Element childs = null;
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node instanceof Element) {
				if (node.getNodeName().equals(childName)) {
					childs = (Element) node;
					break;
				}
			}
		}
		return childs;
	}

	public static synchronized Element getChildElement(Element element) {
		if (element == null) {
			return null;
		}
		Element childs = null;
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node instanceof Element) {
				childs = (Element) node;
				break;
			}
		}
		return childs;
	}

	public static synchronized String[] getElenentValues(Element element) {
		if (element == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node instanceof Text) {
				childs.add(node.getNodeValue());
			}
		}
		String[] values = new String[childs.size()];
		childs.toArray(values);
		return values;
	}

	public static synchronized String getElenentValue(Element element) {
		if (element == null) {
			return null;
		}
		String retnStr = null;
		for (Node node = element.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node instanceof Text) {
				String str = node.getNodeValue();
				if (str == null || str.length() == 0
						|| str.trim().length() == 0) {
					continue;
				} else {
					retnStr = str;
					break;
				}
			}
		}
		return retnStr;
	}

	public static synchronized Element findElementByName(Element e, String name) {
		if (e == null || name == null || name.length() == 0) {
			return null;
		}
		String nodename = null;
		Element[] childs = getChildElements(e);
		for (int i = 0; i < childs.length; i++) {
			nodename = childs[i].getNodeName();
			if (name.equals(nodename)) {
				return childs[i];
			}
		}
		for (int i = 0; i < childs.length; i++) {
			Element retn = findElementByName(childs[i], name);
			if (retn != null) {
				return retn;
			}
		}
		return null;
	}

	public static synchronized Element findElementByAttr(Element e,
			String attrName, String attrVal) {
		return findElementByAttr(e, attrName, attrVal, true);
	}

	public static synchronized Element findElementByAttr(Element e,
			String attrName, String attrVal, boolean dept) {
		if (e == null || attrName == null || attrName.length() == 0
				|| attrVal == null || attrVal.length() == 0) {
			return null;
		}
		String tmpValue = null;
		Element[] childs = getChildElements(e);
		for (int i = 0; i < childs.length; i++) {
			tmpValue = childs[i].getAttribute(attrName);
			if (attrVal.equals(tmpValue)) {
				return childs[i];
			}
		}
		if (dept) {
			for (int i = 0; i < childs.length; i++) {
				Element retn = findElementByAttr(childs[i], attrName, attrVal);
				if (retn != null) {
					return retn;
				}
			}
		}
		return null;
	}

	public static synchronized String formatXml(Element e) {
		return formatXml(e, 0);
	}

	/**
	 * ��ʽ��XML�����.
	 */
	public static synchronized String formatXml(Element e, int indent) {
		indent++;
		for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
			appendIndent(e, n, indent);
			if (!n.getNodeName().equals("#text")) {
				formatXml((Element) n, indent);
			}
		}
		indent--;
		appendIndent(e, indent);
		return e.toString();
	}

	/**
	 * ��ָ���Ľڵ�ǰ�����ʽ��ʾ.
	 */
	private static synchronized void appendIndent(Element e, Node pos,
			int indent) {
		Document doc = e.getOwnerDocument();
		if (indent == 0) {
			e.insertBefore(doc.createTextNode("\n"), pos);
		}
		for (int i = 0; i < indent; i++) {
			if (i == 0) {
				e.insertBefore(doc.createTextNode("\n\t"), pos);
			} else {
				e.insertBefore(doc.createTextNode("\t"), pos);
			}
		}
	}

	/**
	 * ׷�Ӹ�ʽ��ʾ.
	 */
	private static synchronized void appendIndent(Element e, int indent) {
		Document doc = e.getOwnerDocument();
		if (indent == 0) {
			e.appendChild(doc.createTextNode("\n"));
		}
		for (int i = 0; i < indent; i++) {
			if (i == 0) {
				e.appendChild(doc.createTextNode("\n\t"));
			} else {
				e.appendChild(doc.createTextNode("\t"));
			}
		}
	}

	public static synchronized void setAttribute(Element e, String name,
			String value) {
		if (e == null || name == null || name.length() == 0 || value == null
				|| value.length() == 0)
			return;
		else
			e.setAttribute(name, value);
	}

	public static synchronized String getAttribute(Element e, String name) {
		return getAttribute(e, name, null);
	}

	public static synchronized String getAttribute(Element e, String name,
			String defval) {
		if (e == null || name == null || name.length() == 0)
			return defval;
		else
			return e.getAttribute(name);
	}

	public void transformerWrite(Element doc, String filename) throws Exception {
		DOMSource doms = new DOMSource(doc);
		File f = new File(filename);
		StreamResult sr = new StreamResult(f);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, File file) throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(file);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, OutputStream outstream)
			throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outstream);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, Writer outwriter)
			throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outwriter);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(DOMSource doms, StreamResult sr)
			throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "GBK");
		t.transform(doms, sr);
	}
	
	
	
}