package book.xml;

import java.io.File;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 使用JAXP根据XSL转换XML文档
 * JAXP是Java API for XML Processing的英文字头缩写,
 * 中文含义是:用于XML文档处理的使用Java语言编写的编程接口。
 * JAXP支持DOM、SAX、XSLT等标准。
 */
public class JAXPTransform {

	/**
	 * 使用XSLT将XML文档转换成HTML
	 * @param xmlFileName	源XML文件名
	 * @param xslFileName	XSL文件名
	 * @param htmlFileName	输出的HTML文件名
	 * @return	返回HTML文件名
	 */
	public static String xml_xslt_html(String xmlFileName, String xslFileName, 
			String htmlFileName)throws Exception{
		// 创建XSLT引擎的工厂
		TransformerFactory tFactory = TransformerFactory.newInstance();
		// 创建XSLT引擎要使用的XSL文件源
		StreamSource source = new StreamSource(new File(xslFileName));
		// 创建XSLT引擎
		Transformer tx = tFactory.newTransformer(source);
		
		// 设置XSLT引擎的输出属性，使之输出为HTML格式，并且支持中文。
		Properties properties = tx.getOutputProperties(); 
		properties.setProperty(OutputKeys.ENCODING,"GB2312");
		properties.setProperty(OutputKeys.METHOD, "html");
		tx.setOutputProperties(properties); 
		
		// 创建XML文件源和HTML文件的结果流
		StreamSource xmlSource = new StreamSource(new File(xmlFileName));
		File targetFile = new  File(htmlFileName);
		StreamResult result = new StreamResult(targetFile);
		
		// 实现XSLT转换，根据XSL文件源将XML文件源转换成HTML结果流
		tx.transform(xmlSource,	result);
		
		return targetFile.getAbsolutePath();
	}
	
	public static void main(String[] args) throws Exception {
		
		String xmlFileName = "students.xml";
		String xslFileName = "students.xsl";
		String targetFileName = "students.html";
		
		JAXPTransform.xml_xslt_html(xmlFileName, xslFileName, targetFileName);
	}
}