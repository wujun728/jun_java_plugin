import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;



/**
 * 忽略Text的空白   测试无效
 * @author Wujun
 *
 */
public class JAXPAttr {

	/**
	 *主函数
	 */
	public static void main(String[] args) {
		GiveData3 give=new GiveData3();
		
		try {
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		    factory.setIgnoringElementContentWhitespace(true);  //忽略空白缩进
			DocumentBuilder domParser=factory.newDocumentBuilder();
			 InputStream in = TestDom.class.getClassLoader().getResourceAsStream("university.xml"); //读取src目录下文件
			Document document=domParser.parse(in);
			Element root=document.getDocumentElement();
			NodeList nodeList=root.getChildNodes();
			give.output(nodeList);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
}

class GiveData3{
	
	public   void output(NodeList nodelist){
		int size=nodelist.getLength();  //获取接点列表的长度
		for(int k=0;k<size;k++){
			Node node=nodelist.item(k); //获取节点列表中的一项 
			if(node.getNodeType()==node.TEXT_NODE){ //节点类型为TEXT
				Text textNode=(Text)node;
				String content=textNode.getWholeText();
				System.out.print(content);
			}
			if(node.getNodeType()==Node.ELEMENT_NODE){ //节点类型为ELEMENT
				Element elementNode=(Element)node;
				String name=elementNode.getNodeName();
				System.out.print(name);
				NamedNodeMap map=elementNode.getAttributes(); //获取属性节点集合
				/**
				 * 属性节点操作
				 */
				for(int m=0;m<map.getLength();m++){
					Attr attrNode=(Attr)map.item(m);
					String attrName=attrNode.getName(); //属性名称
					String attrValue=attrNode.getValue(); //属性值
					System.out.print(" "+attrName+"="+attrValue);
				}
				NodeList nodes=elementNode.getChildNodes();
				output(nodes);  //递归掉用该方法
			}
		}
		
	}
}













