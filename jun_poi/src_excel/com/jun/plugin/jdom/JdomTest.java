package com.jun.plugin.jdom;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ZhangHao
 * @date 2013-5-3
 * JDOM解析报文
 */
public class JdomTest {
	private static final Logger log = LoggerFactory.getLogger(JdomTest.class.getName());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {

		String message = "<root><MId>MD00001</MId><devSts><dev name=\"printer\" sts=\"-100\" time=\"2013-05-02 12:20:30\"/>"
				+ "<dev name=\"icreader\" sts=\"-101\" time=\"2013-05-02 11:40:35\"/></devSts></root>";

		JdomTest jt = new JdomTest();
		Map map = jt.unPack(message);
		System.out.println(map.get("MId"));
		List<Map> list = (List) map.get("devSts");
		System.out.println(list.size());
		for (Map map1 : list) {
			System.out.println(map1.get("name"));
		}
	}

	@SuppressWarnings("rawtypes")
	public Map unPack(String message) {
		Map msgmap = new HashMap();
		ByteArrayInputStream bais = null;
		Document document = null;
		Element xmlRoot = null; // 根结点
		try {
			bais = new ByteArrayInputStream(message.getBytes("UTF-8"));// 2011-9-6 发生错误 修改为UTF-8 解码
		} catch (UnsupportedEncodingException uee) {
			log.error(uee.getMessage());
			uee.printStackTrace();
		}

		if (bais != null) {
			try {
				SAXBuilder saxBuilder = new SAXBuilder();
				document = saxBuilder.build(bais);
				xmlRoot = document.getRootElement();
				List list = xmlRoot.getChildren();
				for (int i = 0; i < list.size(); i++) {
					Element element = (Element) list.get(i);
					if (element.getChildren().size() == 0) {
						msgmap.put(element.getName(), element.getText());
						log.debug("msgmap.put('" + element.getName() + "','" + element.getText() + "')");
					} else {
						Map elementmap = new HashMap();
						// System.out.println("该节点含有子节点数="+element.getChildren().size());
						List childlist = element.getChildren();
						List eleList = new ArrayList();
						for (int j = 0; j < childlist.size(); j++) {
							Element elementc = (Element) childlist.get(j);
							Map listMap = new HashMap();
							if (elementc.getChildren().size() == 0) {
								List list1 = elementc.getAttributes();
								for (int k = 0; k < list1.size(); k++) {
									Attribute att = (Attribute) list1.get(k);
									// System.out.println(att.getName());
									listMap.put(att.getName(), att.getValue());
								}
								eleList.add(listMap);

							} else {
								elementmap.put(elementc.getName(), elementc);
							}
						}
						log.debug("msgmap.put('" + element.getName() + "','" + elementmap + "')");
						msgmap.put(element.getName(), eleList);
					}
				}
				bais.close();
			} catch (IOException ex) {
				log.error(ex.getMessage());
				ex.printStackTrace();
			} catch (JDOMException ex) {
				log.error(ex.getMessage());
				ex.printStackTrace();
			}
		} else {
			log.error("输入流为空！");
			throw new NullPointerException("输入流为空！");
		}
		return msgmap;
	}
}
