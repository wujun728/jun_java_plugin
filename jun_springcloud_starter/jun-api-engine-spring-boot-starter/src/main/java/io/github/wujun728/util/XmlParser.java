package io.github.wujun728.util;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.entity.ApiDataSource;
import io.github.wujun728.entity.ApiSql;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class XmlParser {

    public static void main(String[] args) throws Exception {
        String text = "<sqls>\n" +
                "    <defaultDB>local_mysql</defaultDB>\n" +
                "\n" +
                "    <sql id=\"getUser\">\n" +
                "        select * from user\n" +
                "        <where>\n" +
                "            <if test = \"id != null\">\n" +
                "                id &lt;= #{id}\n" +
                "            </if>\n" +
                "        </where>\n" +
                "    </sql></sqls>";
        Map<String, ApiSql>  data = parseSql(text);
        StaticLog.info(JSONUtil.toJsonPrettyStr(data));

//        String text = "<datasource>\n" +
//                "    <ds id=\"mysql\">\n" +
//                "        <url>jdbc:mysql://localhost:3306/story?useSSL=false&amp;characterEncoding=UTF-8</url>\n" +
//                "        <username>root</username>\n" +
//                "        <password>root</password>\n" +
//                "    </ds>\n" +
//                "\n" +
//                "</datasource>";
//        parseDatasource(text);
    }

    public static Map<String, ApiDataSource> parseDatasource(String text) throws ParserConfigurationException, IOException, SAXException {
        Map<String, ApiDataSource> map = new HashMap<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(text.getBytes()));
        Element documentElement = document.getDocumentElement();
        NodeList children = documentElement.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if ("datasource".equals(child.getNodeName())) {
                    NamedNodeMap attributes = child.getAttributes();
                    Node idAttr = attributes.getNamedItem("id");
                    if (idAttr == null) {
                        throw new RuntimeException("id attribute of <datasource> tag is missing");
                    }
                    String id = idAttr.getTextContent();
                    if (StringUtils.isBlank(id)) {
                        throw new RuntimeException("id attribute of <datasource> tag is missing");
                    }

                    ApiDataSource dataSource = new ApiDataSource();
                    dataSource.setId(id);

                    NodeList childNodes = child.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node node = childNodes.item(j);
                        if (child.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = node.getNodeName();
                            if ("url".equals(nodeName)) {
                                dataSource.setUrl(node.getTextContent());
                            } else if ("username".equals(nodeName)) {
                                dataSource.setUsername(node.getTextContent());
                            } else if ("password".equals(nodeName)) {
                                dataSource.setPassword(node.getTextContent());
                            } else if ("driver".equals(nodeName)) {
                                dataSource.setDriver(node.getTextContent());
                            } else if ("#text".equals(nodeName)) {
                                //回车会解析成节点
                            } else {
                                throw new RuntimeException("tag not supported under <datasource> : " + nodeName);
                            }
                        }
                    }
                    map.put(id, dataSource);
                } else {
                    throw new RuntimeException("tag not supported : " + child.getNodeName());
                }

            }
        }
        return map;
    }

    public static Map<String, ApiSql> parseSql(String text) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(text.getBytes()));
        Element documentElement = document.getDocumentElement();
        NodeList children = documentElement.getChildNodes();

        Map<String, ApiSql> map = new HashMap<>();
        String defaultDB = null;
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            String nodeName = child.getNodeName();

            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (nodeName.equals("defaultDB")) {
                    //默认DB配置
                    defaultDB = child.getTextContent();
                    if (StringUtils.isBlank(defaultDB)) {
                        throw new RuntimeException("defaultDB value empty");
                    }
                } else if (nodeName.equalsIgnoreCase("sql")) {

                    NamedNodeMap attributes = child.getAttributes();
                    Node idAttr = attributes.getNamedItem("id");
                    if (idAttr == null) {
                        throw new RuntimeException("id attribute of <sql> tag is missing");
                    }
                    String id = idAttr.getTextContent();
                    if (StringUtils.isBlank(id)) {
                        throw new RuntimeException("id attribute of <sql> tag is missing");
                    }

                    Node dbAttr = attributes.getNamedItem("db");
                    String txt = nodeContentToString(child);

                    ApiSql sql = new ApiSql();

                    if (dbAttr != null) {
                        String db = dbAttr.getTextContent();
                        sql.setDatasourceId(db);
                    }

                    sql.setText(txt);
                    sql.setId(id);
                    sql.setType(nodeName);
                    map.put(id, sql);
                } else {
                    throw new RuntimeException("tag not supported : " + nodeName);
                }
            }
        }
        String finalDefaultDB = defaultDB;
        map.keySet().forEach(t -> {
            ApiSql sql = map.get(t);
            if (StringUtils.isBlank(sql.getDatasourceId())) {
                if (finalDefaultDB != null) {
                    sql.setDatasourceId(finalDefaultDB);
                } else {
                    throw new RuntimeException("db attribute of <sql> tag is missing, and <defaultDB> tag is missing meanwhile");
                }
            }
        });
        //TODO 检查datasource是否都已经配置
        return map;

    }

    private static String nodeToString(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));

        return sw.toString();
    }

    private static String nodeContentToString(Node node) throws TransformerException {
        NodeList nodes = node.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < nodes.getLength(); j++) {
            Node n = nodes.item(j);
            String txt = nodeToString(n);
            sb.append(txt);
        }
        return sb.toString().trim();
    }
}
