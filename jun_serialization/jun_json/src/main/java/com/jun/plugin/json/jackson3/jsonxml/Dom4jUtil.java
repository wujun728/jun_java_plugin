package com.jun.plugin.json.jackson3.jsonxml;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import static com.jun.plugin.json.jackson3.jsonxml.Lz4Util.lz4Compress;
import static com.jun.plugin.json.jackson3.jsonxml.Lz4Util.lz4Decompress;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

public class Dom4jUtil {
  private final static String defaultChar = "GBK";

	public static void writeDocToOut(Document doc,String sCharSet,OutputStream out) {
	  writeDocToOut(doc, sCharSet, out,false);
	}

	public static void writeDocToOut(Document doc,String sCharSet,OutputStream out,boolean bZipSpace) {
    OutputFormat fmt = new OutputFormat(bZipSpace ? "" : "  ",bZipSpace?false:true,sCharSet);
    try {
      org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(out,fmt);
      doc.setXMLEncoding(sCharSet);
      xmlWriter.write(doc);
      xmlWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

	public static void writeOneXMLObjToOut(Object oneXmlObj,String sCharSet,OutputStream out,boolean bZipSpace) {
		OutputFormat fmt = new OutputFormat(bZipSpace ? "" : "  ",bZipSpace?false:true,sCharSet);
		try {
			org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(out,fmt);
			Document doc = null;
			if (oneXmlObj instanceof Document) {
				doc = (Document)oneXmlObj;
				doc.setXMLEncoding(sCharSet);
				xmlWriter.write(doc);
			} else if (oneXmlObj instanceof Element){
				doc = ((Element)oneXmlObj).getDocument();
				doc.setXMLEncoding(sCharSet);
				xmlWriter.write((Element)oneXmlObj);
			}
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  public static byte[] writeDocToBytes(Document doc,String sCharSet){
    return writeDocToBytes(doc, sCharSet, false);
  }

  public static byte[] writeDocToBytes(Document doc,String sCharSet,boolean bZipSpace){
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    writeDocToOut(doc, sCharSet, bout,bZipSpace);
    byte[] rtn = bout.toByteArray();
    try {
      bout.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return rtn;
  }

  public static byte[] writeDocToBytes(Document doc){
    return writeDocToBytes(doc, defaultChar);
  }

	public static byte[] writeOneXmlObjToBytes(Object oneXmlObj,String sCharSet,boolean bZipSpace){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		writeOneXMLObjToOut(oneXmlObj,sCharSet,bout,bZipSpace);
		byte[] rtn = bout.toByteArray();
		try {
			bout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public static void writeDocToFile(Document doc,String sCharSet,String sFN) {
	   FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(sFN);
			writeDocToOut(doc, sCharSet, fout);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fout);
    }
	}

	public static String writeDocToStr(Document doc,String sCharSet){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		writeDocToOut(doc, sCharSet, bout);
		try {
			return new String(bout.toByteArray(),sCharSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document getDocFromStream(InputStream in) {
		try {
//      InputStreamReader irer = new InputStreamReader(in,defaultChar);
//      return new SAXReader().read(irer);
      return new SAXReader().read(in); //由XML中指定的编码进行解析
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document getDocFromStream(InputStream in,boolean fromReq) {
		if (fromReq){
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			try {
				IOUtils.copy(in,bout);
				return getDocFromBytes(bout.toByteArray(),"UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else return getDocFromStream(in);
		return null;
	}

	public static Document getDocFromStr(String s) {
		return getDocFromStr(s, defaultChar);
	}

	public static Document getJustDoc(String rootElmtName,String sCharSet){
		return getDocFromStr(MessageFormat.format("<?xml version=''1.0'' encoding=''{0}''?><{1}/>",new String[]{sCharSet,rootElmtName}));
	}

	public static Document getJustDoc(String rootElmtName){
		return getJustDoc(rootElmtName,defaultChar);
	}

  public static Document getDocFromBytes(byte[] dat,String sCharSet){
    InputStream is = new ByteArrayInputStream(dat);
    return getDocFromStream(is,sCharSet);
  }

  public static Document getDocFromBytes(byte[] dat){
    return getDocFromBytes(dat,defaultChar);
  }

	public static Document getDocFromStr(String s,String sCharSet) {
		try {
			ByteArrayInputStream bin = new ByteArrayInputStream(s.getBytes(sCharSet));
			return new SAXReader().read(bin,sCharSet);
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return null;
	}

	public static Document getDocFromStream(InputStream in,String sCharSet) {
		try {
			InputStreamReader irer = new InputStreamReader(in,sCharSet);
			return new SAXReader().read(irer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document getDocFromFile(String sFn) {
		try {
		  SAXReader reader =new SAXReader();
//		  System.out.println("reader.isStripWhitespaceText()="+reader.isStripWhitespaceText());
//		  reader.setStripWhitespaceText(true);
//      reader.setStringInternEnabled(false);
//      System.out.println("reader.isStringInternEnabled()="+reader.isStringInternEnabled());
		  if (sFn.startsWith("file:///"))
		    return reader.read(sFn);
		  else
		    return reader.read(new File(sFn));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map parseXMLMap(String xml) throws SAXException, IOException, ParserConfigurationException, FactoryConfigurationError{
		Map result = new HashMap();

		if (StringUtils.isNotEmpty(xml)){
			Document doc = getDocFromStr(xml);

			Element root = doc.getRootElement();
			List rootChilds = root.elements("Attribute");
			Element attribute = null;
			String key = null;
			String value = null;
			for (Iterator iter = rootChilds.iterator(); iter.hasNext();) {
				attribute = (Element) iter.next();
				key = attribute.attributeValue("Key");
				value=attribute.attributeValue("Value");
				result.put(key,value);
			}
		}
		return result;
	}

	public static String serializeMapToXML(Map map) {
		String result = "";
		if (map != null){
			Document doc = getDocFromStr("<?xml version='1.0' encoding='UTF-8'?><AttrDats></AttrDats>");
			Element root = doc.getRootElement();
			Element attribute = null;

			String keyStr = null;
			String valueStr = null;

			for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ){
				keyStr = (String) iter.next();
				valueStr = (String) map.get(keyStr);
				attribute = root.addElement("Attribute");
				attribute.addAttribute("Key",keyStr);
				attribute.addAttribute("Value",valueStr);
			}
			result = writeDocToStr(doc,"UTF-8");
		}
		return result;
	}

	public static Element doClone(Element sourEl,Element destPEl,boolean bIncludeSourEl){
    if (destPEl==null || sourEl==null)return null;
    Element elDest = (bIncludeSourEl) ? destPEl.addElement( sourEl.getName() ) : destPEl;
    if (bIncludeSourEl) elDest.appendAttributes( sourEl );
    elDest.appendContent( (Element)sourEl ); //.clone()
    return elDest;
  }

	public static Element doClone(Element sourEl,Element destPEl,boolean bIncludeSourEl,boolean autoClearDestPEl){
    if (destPEl==null || sourEl==null)return null;
    Element elDest = (bIncludeSourEl) ? destPEl.addElement( sourEl.getName() ) : destPEl;
    if (bIncludeSourEl) elDest.appendAttributes( sourEl );

    if (autoClearDestPEl){
			ArrayList<Element> reverseList = new ArrayList<Element>();
			for (Iterator iterator = elDest.nodeIterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				if (node instanceof Element){
					reverseList.add((Element) node);
				}
			}

			for (Iterator it2 =  reverseList.iterator();it2.hasNext();){
				((Element)it2.next()).detach();
			}
		}

    elDest.appendContent( (Element)sourEl ); //.clone()
    return elDest;
  }

	public static void doMove(Element sourEl,Element destPEl,boolean bIncludeSourEl){
	  if (destPEl==null || sourEl==null)return;
    if (bIncludeSourEl) {
      sourEl.setParent(null);
      destPEl.add( sourEl );
    } else {
      for (Iterator iterator = sourEl.nodeIterator(); iterator.hasNext();) {
        Node node = (Node) iterator.next();
        node.setParent(null);
        destPEl.add(node);
      }
    }
	}

	public static void doInsertBefore(Element pEl, Element sibEl, Element newEl) {
    if (sibEl == null)
      pEl.add(newEl);
    else {
      List list = pEl.elements();
      list.add(list.indexOf(sibEl), newEl);
    }
  }

  public static void doInsert(Element pEl, Element preEl, Element newEl) {
    if (preEl == null)
      pEl.add(newEl);
    else {
      List list = pEl.elements();
      int idx = list.indexOf(preEl);
      if (idx==-1 || idx >= list.size()-1)
        pEl.add(newEl);
      else
        list.add(idx+1,newEl);
    }
  }

  public static void copyAttrs(Element sourEl,Element destEl){
    for (Iterator iterator = sourEl.attributeIterator(); iterator.hasNext(); ) {
      Attribute attr = (Attribute) iterator.next();
      destEl.addAttribute(attr.getName(),attr.getValue());
    }
  }
  
  public static Document copyDoc(Document doc0,String sCharSet ){
    return getDocFromBytes(writeDocToBytes(doc0,sCharSet),sCharSet);
  }

  public static Document copyDoc(Document doc0 ){
    return getDocFromBytes(writeDocToBytes(doc0,defaultChar),defaultChar);
  }

  public static int getNodeLev(Element el){
    if (el==null) return -1;
    int iR=0;
    Element pEl=el.getParent();
    while (pEl!=null){
      el = pEl;
      pEl = el.getParent();
      iR++;
    }
    return iR;
  }



  public static Document parseW3cDoc(org.w3c.dom.Document doc) throws Exception {
    if (doc == null) {
      return (null);
    }
    org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
    return (xmlReader.read(doc));
  }

  /*
  parentTerm : 判断目标父亲节点的标准
   */
	public static Element getParentEl(Element aEl,String parentTerm , int lev) {
		StringBuffer sb= new StringBuffer(aEl.valueOf("@name"));
		Element rootEl = aEl.getDocument().getRootElement();
		Element pEl = aEl.getParent();
		int iLev = 0;
		while (pEl!=rootEl && (iLev++<lev)){
			if (pEl.getParent().selectSingleNode(parentTerm)==pEl) {
				return pEl;
			}
			pEl = pEl.getParent();
		}
		return null;
	}

	public static String getElFullName(Element el,String attrName){
		Document doc = el.getDocument();
			StringBuffer sb= new StringBuffer(el.valueOf("@"+attrName));
			Element rootEl = doc.getRootElement();
			Element pEl = el.getParent();
			while (pEl!=rootEl){
				sb.insert(0, ".").insert(0,pEl.valueOf("@"+attrName)) ;
				pEl = pEl.getParent();
			}
			return sb.toString();
	}

	public static Document getDocFromZipBytes(byte[] zipDats) {
		return getDocFromZipBytes(zipDats, defaultChar);
	}

	public static Document getDocFromZipBytes(byte[] zipDats, String sCharSet) {
		try {
			byte[] dats = lz4Decompress(zipDats, LZ4_BLOCKSIZE);
			return getDocFromBytes(dats, sCharSet);
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] writeDocToZipBytes(Document doc) {
		return writeDocToZipBytes(doc, defaultChar);
	}

	final static int LZ4_BLOCKSIZE = 512 * 1024;

	public static byte[] writeDocToZipBytes(Document doc, String sCharSet) {
		byte[] dats = writeDocToBytes(doc, sCharSet, false);
		try {
			return lz4Compress(dats, LZ4_BLOCKSIZE);
		} catch (IOException e) {
			return null;
		}
	}

	// test code

  // 选择叶子节点： docOrg.selectNodes("//Node[count(Node)=0]"
      
  //测试在文档之间拷贝元素
	private static void testCopyElmtOfDoc(){
		String sSourXML="<?xml version='1.0' encoding='gbk'?><DATAPACKET><METADATA><FIELDS><FIELD attrname='ID' fieldtype='fixed' WIDTH='20'/></FIELDS></METADATA></DATAPACKET>";
		Document doc = getDocFromStr(sSourXML);
		Document doc1 = getDocFromStr("<?xml version='1.0' encoding='UTF-8'?><DATAPACKET><aaa></aaa></DATAPACKET>");
		Element elmt = (Element)doc.selectSingleNode("/DATAPACKET/METADATA");
		elmt.setParent(null);
		((Element)doc1.selectSingleNode("/DATAPACKET/aaa")).add(elmt);
		System.out.println(doc1.asXML());
	}

  //测试在文档之间移动元素
  private static void testDoMove(){
    String sSourXML="<?xml version='1.0' encoding='gbk'?><DATAPACKET><METADATA><FIELDS><FIELD attrname='ID' fieldtype='fixed' WIDTH='20'/></FIELDS></METADATA></DATAPACKET>";
    Document doc = getDocFromStr(sSourXML);
    Document doc1 = getDocFromStr("<?xml version='1.0' encoding='UTF-8'?><DATAPACKET><aaa></aaa></DATAPACKET>");
    Element elmt = (Element)doc.selectSingleNode("/DATAPACKET/METADATA");
//    elmt.setParent(null);
    Element elmt1 =(Element)doc1.selectSingleNode("/DATAPACKET/aaa");
    doMove(elmt, elmt1,false);
    System.out.println(doc1.asXML());
  }

  
  private static void testCopyAttrs (){
    String sSourXML="<?xml version='1.0' encoding='gbk'?><DATAPACKET><METADATA><FIELDS><FIELD attrname='ID' fieldtype='fixed' WIDTH='20'/></FIELDS></METADATA></DATAPACKET>";
    Document doc = getDocFromStr(sSourXML);
    Element el1 = (Element)doc.selectSingleNode("/DATAPACKET/METADATA/FIELDS/FIELD");
    Element elabc = el1.getParent().addElement("abc");
    copyAttrs(el1,elabc);
//    for (Iterator iterator = el1.attributeIterator(); iterator.hasNext(); ) {
//      Attribute attr = (Attribute) iterator.next();
//      elabc.addAttribute(attr.getName(),attr.getValue());
//    }
    System.out.println(doc.asXML());
  }
  
	public static void main(String[] args) {
//		testCopyElmtOfDoc();
//		testDoMove();
		testCopyAttrs();
//		System.out.println(getJustDoc("aa").asXML());
  }
}
