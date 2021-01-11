package com.jun.plugin.dbtracer.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * TODO 通过XSD检查xml合法性
 * 
 * @author: guhai
 */
public class XmlParser {

    private final String Element_TABLE = "table";

    private File xmlFile;

    public XmlParser(String xmlPath) {
        String file = Class.class.getResource("/").getPath() + xmlPath;
        xmlFile = new File(file);
    }

    public Map<String, TableConfiguration> parse() {
        Map<String, TableConfiguration> tblName2Config = new HashMap<String, TableConfiguration>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(xmlFile);
            Element root = doc.getRootElement();
            for (Iterator<Element> i = root.elementIterator(Element_TABLE); i.hasNext();) {
                TableConfiguration tblConfig = new TableConfiguration();
                Element foo = (Element) i.next();
                Attribute nameAttr = foo.attribute("name");
                String tableName = nameAttr.getValue();
                tblConfig.setName(tableName);
                
                Attribute keyAttr = foo.attribute("key");
                String keyName=keyAttr.getValue();
                tblConfig.setKey(keyName);

                Iterator<Element> filedElements = foo.elementIterator("field");
                while (filedElements.hasNext()) {
                    Element filedElement = filedElements.next();
                    TableField field = parseFiledElement(filedElement);
                    tblConfig.getFiledName2Config().put(field.getNameEn(), field);
                }

                // TODO 表名配置重复就抛异常
                tblName2Config.put(tableName, tblConfig);
                System.out.println(tblConfig);
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return tblName2Config;
    }

    private TableField parseFiledElement(Element filedElement) {
        TableField filed = new TableField();
        String nameEN = filedElement.attribute("nameEN").getValue();
        filed.setNameEn(nameEN);
        String nameCN = filedElement.attribute("nameCN").getValue();
        filed.setNameCn(nameCN);

        Iterator<Element> propertyElements = filedElement.elementIterator("property");
        while (propertyElements.hasNext()) {
            Element propertyElement = propertyElements.next();
            String value = propertyElement.attribute("value").getValue();
            String description = propertyElement.attribute("description").getValue();
            filed.getValue2Description().put(value, description);
        }

        return filed;
    }
}
