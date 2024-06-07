//package com.jun.plugin.groovy.util;
//
//import com.jun.plugin.groovy.common.model.Sql;
//import org.apache.commons.lang3.StringUtils;
//import org.w3c.dom.*;
//import org.xml.sax.SAXException;
//
//import com.jun.plugin.groovy.common.model.ApiDataSource;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//public class XmlParser {
//
//    public static Map<String, ApiDataSource> parseDatasource(String text) throws ParserConfigurationException, IOException, SAXException {
//        Map<String, ApiDataSource> map = new HashMap<>();
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        Document document = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(text.getBytes()));
//        Element documentElement = document.getDocumentElement();
//        NodeList children = documentElement.getChildNodes();
//
//        for (int i = 0; i < children.getLength(); i++) {
//            Node child = children.item(i);
//
//            if (child.getNodeType() == Node.ELEMENT_NODE) {
//                NamedNodeMap attributes = child.getAttributes();
//                Node idAttr = attributes.getNamedItem("id");
//                String id = idAttr.getTextContent();
//                ApiDataSource dataSource = new ApiDataSource();
//                dataSource.setId(id);
//
//                NodeList childNodes = child.getChildNodes();
//                for (int j = 0; j < childNodes.getLength(); j++) {
//                    Node node = childNodes.item(j);
//                    if (child.getNodeType() == Node.ELEMENT_NODE) {
//                        String nodeName = node.getNodeName();
//                        if ("url".equals(nodeName)) {
//                            dataSource.setUrl(node.getTextContent());
//                        } else if ("username".equals(nodeName)) {
//                            dataSource.setUsername(node.getTextContent());
//                        } else if ("password".equals(nodeName)) {
//                            dataSource.setPassword(node.getTextContent());
//                        }
//                    }
//                }
//
//                map.put(id, dataSource);
//
//            }
//        }
//        return map;
//    }
//
//    public static Map<String, Sql> parseSql(String text) throws ParserConfigurationException, IOException, SAXException, TransformerException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        Document document = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(text.getBytes()));
//        Element documentElement = document.getDocumentElement();
//        NodeList children = documentElement.getChildNodes();
//
//        Map<String, Sql> map = new HashMap<>();
//        String defaultDB = null;
//        for (int i = 0; i < children.getLength(); i++) {
//            Node child = children.item(i);
//            String nodeName = child.getNodeName();
//
//            if (child.getNodeType() == Node.ELEMENT_NODE) {
//                if (nodeName.equals("defaultDB")) {
//                    //默认DB配置
//                    defaultDB = child.getTextContent();
//                    if (StringUtils.isBlank(defaultDB)){
//                        throw new RuntimeException("defaultDB value empty");
//                    }
//                } else if (nodeName.equalsIgnoreCase("select") || nodeName.equalsIgnoreCase("update")
//                        || nodeName.equalsIgnoreCase("insert") || nodeName.equalsIgnoreCase("delete")) {
//
//                    NamedNodeMap attributes = child.getAttributes();
//                    Node idAttr = attributes.getNamedItem("id");
//                    String id = idAttr.getTextContent();
//
//                    Node dbAttr = attributes.getNamedItem("db");
//                    String txt = nodeContentToString(child);
//
//                    Sql sql = new Sql();
//
//                    if (dbAttr != null){
//                        String db = dbAttr.getTextContent();
//                        sql.setDatasourceId(db);
//                    }
//
//                    sql.setText(txt);
//                    sql.setId(id);
//                    sql.setType(nodeName);
//                    map.put(id, sql);
//                } else{
//                    throw new RuntimeException("tag not supported : " + nodeName);
//                }
//            }
//        }
//        String finalDefaultDB = defaultDB;
//        map.keySet().forEach(t->{
//            Sql sql = map.get(t);
//            if (StringUtils.isBlank(sql.getDatasourceId())){
//                sql.setDatasourceId(finalDefaultDB);
//            }
//        });
//        return map;
//
//    }
//
//    private static String nodeToString(Node node) throws TransformerException {
//        StringWriter sw = new StringWriter();
//
//        Transformer t = TransformerFactory.newInstance().newTransformer();
//        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//        t.setOutputProperty(OutputKeys.INDENT, "yes");
//        t.transform(new DOMSource(node), new StreamResult(sw));
//
//        return sw.toString();
//    }
//
//    private static String nodeContentToString(Node node) throws TransformerException {
//        NodeList nodes = node.getChildNodes();
//        StringBuilder sb = new StringBuilder();
//        for (int j = 0; j < nodes.getLength(); j++) {
//            Node n = nodes.item(j);
//            String txt = nodeToString(n);
//            sb.append(txt);
//        }
//        return sb.toString().trim();
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        String text = "<sql>\n" +
//                "    <defaultDB>local_mysql</defaultDB>\n" +
//                "\n" +
//                "    <select id=\"getUser\">\n" +
//                "        select * from user\n" +
//                "        <where>\n" +
//                "            <if test = \"id != null\">\n" +
//                "                id &lt;= #{id}\n" +
//                "            </if>\n" +
//                "        </where>\n" +
//                "    </select></sql>";
//        parseSql(text);
//
////        String text = "<datasource>\n" +
////                "    <ds id=\"mysql\">\n" +
////                "        <url>jdbc:mysql://localhost:3306/story?useSSL=false&amp;characterEncoding=UTF-8</url>\n" +
////                "        <username>root</username>\n" +
////                "        <password>root</password>\n" +
////                "    </ds>\n" +
////                "\n" +
////                "</datasource>";
////        parseDatasource(text);
//    }
//}
