package cn.org.rapid_framework.generator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 将xml解析成NodeData,NodeData主要是使用Map及List来装attribute
 * 
 * <pre>
 *        String nodeName;
 *        Map attributes = new HashMap();
 *        List<NodeData> childs = new ArrayList();
 * </pre>
 * @author badqiu
 */
public class XMLHelper {

	public static Document getLoadingDoc(String file) throws FileNotFoundException, SAXException, IOException{
		return getLoadingDoc(new FileInputStream(file));
	}
	
    static Document getLoadingDoc(InputStream in) throws SAXException,IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(false);
        dbf.setValidating(false);
        dbf.setCoalescing(false);  //convert CDATA nodes to Text FIXME 该节点与if(elm.getNodeType() == Node.CDATA_SECTION_NODE) 
        dbf.setIgnoringComments(false); //为false时与CDATA冲突
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            //ignore entity resolver
            db.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId,
                                                 String systemId)
                                                                 throws SAXException,
                                                                 IOException {
                    InputSource is = new InputSource(new StringReader(""));
                    is.setSystemId(systemId);
                    return is;
                }
            });
            
            InputSource is = new InputSource(in);
            return db.parse(is);
        } catch (ParserConfigurationException x) {
            throw new Error(x);
        }
    }
    
    public static class NodeData {
        public String nodeName;
        public String nodeValue;
        public String innerXML;
        public String outerXML;
//        public String innerText;
//        public String outerText;
        public Map<String,String> attributes = new HashMap<String,String>();
        public List<NodeData> childs = new ArrayList<NodeData>();
        
        public String toString() {
            return "nodeName="+nodeName+",attributes="+attributes+" nodeValue="+nodeValue+" child:\n"+childs;
        }
        
        public Map<String,String> nodeNameAsAttributes(String nodeNameKey) {
            Map map = new HashMap();
            map.putAll(attributes);
            map.put(nodeNameKey, nodeName);
            return map;
        }
        
        public List<Map<String,String>> childsAsListMap() {
        	List<Map<String,String>> result = new ArrayList();
            for(NodeData c : childs) {
            	Map map = new LinkedHashMap();
            	map.put(c.nodeName, c.nodeValue);
            	result.add(map);
            }
            return result;
        }
    }
    
    private static NodeData treeWalk(Element elm) {
        NodeData nodeData = new NodeData();
        nodeData.attributes = attrbiuteToMap(elm.getAttributes());
        nodeData.nodeName = elm.getNodeName();
        nodeData.childs = new ArrayList<NodeData>();
        nodeData.innerXML = childsAsText(elm, new StringBuffer(),true).toString();
        nodeData.outerXML = nodeAsText(elm,new StringBuffer(),true).toString();
        nodeData.nodeValue = getNodeValue(elm);
//        nodeData.innerText = childsAsText(elm, new StringBuffer(),false).toString();
//        nodeData.outerText = nodeAsText(elm,new StringBuffer(),false).toString();
        NodeList childs = elm.getChildNodes();
        for(int i = 0; i < childs.getLength() ; i++) {
            Node node = childs.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                nodeData.childs.add(treeWalk((Element)node));
            }
        }
        return nodeData;
    }

	private static StringBuffer childsAsText(Element elm, StringBuffer sb,boolean ignoreComments) {
		NodeList childs = elm.getChildNodes();
        for(int i = 0; i < childs.getLength() ; i++) {
            Node child = childs.item(i);
            nodeAsText(child,sb,ignoreComments);
        }
        return sb;
	}    	
    
    private static StringBuffer nodeAsText(Node elm,StringBuffer sb,boolean ignoreComments) {
    	 if(elm.getNodeType() == Node.CDATA_SECTION_NODE) {
    		 CDATASection cdata = (CDATASection)elm;
    		 sb.append("<![CDATA[");
    		 sb.append(cdata.getData());
    		 sb.append("]]>");
    		 return sb;
    	 }
    	 if(elm.getNodeType() == Node.COMMENT_NODE) {
    		 if(ignoreComments) {
    			 return sb;
    		 }
    		 Comment c = (Comment)elm;
    		 sb.append("<!--");
    		 sb.append(c.getData());
    		 sb.append("-->");
    		 return sb;
    	 }
    	 if(elm.getNodeType() == Node.TEXT_NODE) {
    		 Text t = (Text)elm;
    		 sb.append(StringHelper.escapeXml(t.getData(),"<&"));
    		 return sb;
    	 }
    	 NodeList childs = elm.getChildNodes();
    	 sb.append("<"+elm.getNodeName());
    	 attributes2String(elm, sb);
    	 if(childs.getLength() > 0) {
    	     sb.append(">");
             for(int i = 0; i < childs.getLength() ; i++) {
                 Node child = childs.item(i);
                 nodeAsText(child,sb,ignoreComments);
             }
             sb.append("</"+elm.getNodeName()+">");
    	 }else {
    	     sb.append("/>");
    	 }
    	 return sb; 
	}

	private static void attributes2String(Node elm, StringBuffer sb) {
		NamedNodeMap attributes = elm.getAttributes();
         if(attributes != null && attributes.getLength() > 0) {
        	 sb.append(" ");
             for(int j = 0; j < attributes.getLength(); j++) {
                 sb.append(String.format("%s=\"%s\"", attributes.item(j).getNodeName(), StringHelper.escapeXml(attributes.item(j).getNodeValue(),"<&\"")));
                 if(j < attributes.getLength() - 1) {
                	 sb.append(" ");
                 }
             }
         }
	}

	public static Map<String,String> attrbiuteToMap(NamedNodeMap attributes) {
        if(attributes == null) return new LinkedHashMap<String,String>();
        Map<String,String> result = new LinkedHashMap<String,String>();
        for(int i = 0; i < attributes.getLength(); i++) {
            result.put(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
        }
        return result;
    }
    
	   /**
     * Extract the text value from the given DOM element, ignoring XML comments. <p>Appends all CharacterData nodes and
     * EntityReference nodes into a single String value, excluding Comment nodes.
     *
     * @see CharacterData
     * @see EntityReference
     * @see Comment
     */
    public static String getTextValue(Element valueEle) {
        if(valueEle == null) throw new IllegalArgumentException("Element must not be null");
        StringBuilder sb = new StringBuilder();
        NodeList nl = valueEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node item = nl.item(i);
            if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
                sb.append(item.getNodeValue());
            }else if(item instanceof Element) {
                sb.append(getTextValue((Element)item));
            }
        }
        return sb.toString();
    }
    
    public static String getNodeValue(Node node) {
        if(node instanceof Comment){
            return null;
        }
        if(node instanceof CharacterData) {
            return ((CharacterData)node).getData();
        }
        if(node instanceof EntityReference) {
            return node.getNodeValue();
        }
        if(node instanceof Element) {
            return getTextValue((Element)node);
        }
        return node.getNodeValue();
    }
    
    public NodeData parseXML(InputStream in) throws SAXException, IOException {
        Document doc = getLoadingDoc(in);
        return treeWalk(doc.getDocumentElement());
    }

    public NodeData parseXML(File file) throws SAXException, IOException {
        FileInputStream in = new FileInputStream(file);
		try {return parseXML(in);}finally{in.close();}
    }
    
    public static String getXMLEncoding(InputStream inputStream) throws UnsupportedEncodingException, IOException {
    	return getXMLEncoding(IOHelper.toString("UTF-8", inputStream));
    }
    
    public static String getXMLEncoding(String s) {
    	if(s == null) return null;
    	Pattern p = Pattern.compile("<\\?xml.*encoding=[\"'](.*)[\"']\\?>");
    	Matcher m = p.matcher(s);
    	if(m.find()) {
    		return m.group(1);
    	}
    	return null;
    }

    public static String removeXmlns(File file) throws IOException {
        InputStream forEncodingInput = new FileInputStream(file);
        String encoding = XMLHelper.getXMLEncoding(forEncodingInput);
        forEncodingInput.close();
        
        InputStream input = new FileInputStream(file);
        String xml = IOHelper.toString(encoding,input);
        xml = XMLHelper.removeXmlns(xml);
        input.close();
        return xml;
    }
    
    //只移除default namesapce
    public static String removeXmlns(String s) {
    	if(s == null) return null;
    	s = s.replaceAll("(?s)xmlns=['\"].*?['\"]", "");
//    	s = s.replaceAll("(?s)xmlns:?\\w*=['\"].*?['\"]", "");
    	s = s.replaceAll("(?s)\\w*:schemaLocation=['\"].*?['\"]", "");
    	return s;
    }
    
    /**
     * 解析attributes为hashMap
     * @param attributes 格式： name='badqiu' sex='F'
     * @return
     */
    public static Map<String, String> parse2Attributes(String attributes) {
        Map result = new HashMap();
        Pattern p = Pattern.compile("(\\w+?)=['\"](.*?)['\"]");
        Matcher m = p.matcher(attributes);
        while(m.find()) {
            result.put(m.group(1),StringHelper.unescapeXml(m.group(2)));
        }
        return result;
    }
    
    public static void main(String[] args) throws FileNotFoundException, SAXException, IOException {
        String file = "D:/dev/workspaces/alipay/ali-generator/generator/src/table_test.xml";
        NodeData nd = new XMLHelper().parseXML(new FileInputStream(new File(file)));
        
        Map table = nd.attributes;
        List columns = nd.childs;
        System.out.println(table);
        System.out.println(columns);
//        System.out.println(nd);
    }

}
