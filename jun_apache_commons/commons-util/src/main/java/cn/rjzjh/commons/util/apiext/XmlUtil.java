package cn.rjzjh.commons.util.apiext;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.rjzjh.commons.util.assistbean.NodeSax;
import cn.rjzjh.commons.util.exception.ExceptAll;
import cn.rjzjh.commons.util.exception.ProjectException;

public abstract class XmlUtil {
	public static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static final class XMLHandler extends DefaultHandler {
		private final NodeSax nodeSax;
		private boolean finded = false;

		public XMLHandler(String nodeName) {
			this.nodeSax = new NodeSax(nodeName);
		}

		public NodeSax getNodeSax() {
			return this.nodeSax;
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (qName.equals(nodeSax.getNodeName())) {
				nodeSax.setAttributes(attributes);
				finded = true;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (finded) {
				nodeSax.setNodeValue(new String(ch, start, length));
				throw new SAXException("已找到");
			}
		}
	}

	/******
	 * SAX解析得到指定的第一个XML节点,当文档中有很多元素时，startDocument到startElement事件会执行较长一段时间
	 * 
	 * @param input
	 * @param nodeName
	 * @return
	 */
	public static NodeSax getFirstNode(InputStream input, String nodeName) {
		XMLHandler handler = null;
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			handler = new XMLHandler(nodeName);
			parser.parse(input, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getNodeSax();
	}

	/**
	 * 解析XML文档
	 * 
	 * @param src
	 *            要解析的字符串
	 * */
	public static final Document parserDocment(String src) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(new InputSource(new StringReader(src)));
		} catch (Exception e) {
			logger.error("解析XML文档错误");
			return null;
		}
	}
	
}
