/*
 * Copyright (c) 2015-2016, AlexGao
 * http://git.oschina.net/wolfsmoke/WSHttpHelper
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ws.httphelper.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;
import org.ws.httphelper.exception.WSException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 16-1-2.
 */
public class XmlParser {
    private static final Log log = LogFactory.getLog(XmlParser.class);
    protected String encoding = "UTF-8";
    /**
     * Load external DTD feature id
     * (http://apache.org/xml/features/nonvalidating/load-external-dtd).
     */
    protected static final String LOAD_EXTERNAL_DTD_FEATURE_ID = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    /**
     * 文档的<!DOCTYPE部分
     */
    private HashMap<String, DocumentType> docAttr = new HashMap<String, DocumentType>();

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    /**
     * 解析文档
     *
     * @throws org.ws.httphelper.exception.WSException
     */
    public void parser(Map data, InputStream is, String dtdFile) throws WSException {
        InputSource sc = new InputSource(is);
        if (encoding != null) {
            sc.setEncoding(encoding);
        }

        parser(data, sc, dtdFile);
    }

    /**
     * 解析文档
     *
     * @param doc
     * @throws org.ws.httphelper.exception.WSException
     */
    public void parser(Map data, byte[] doc, String dtdFile) throws WSException {
        ByteArrayInputStream in = new ByteArrayInputStream(doc);
        InputSource sc = new InputSource(in);
        if (encoding != null) {
            sc.setEncoding(encoding);
        }

        // 解析文件
        try {
            parser(data, sc, dtdFile);
        } finally {
            try{
                in.close();
            }
            catch( NullPointerException e ){
                // is = null
            }
            catch( Throwable e ){
                log.error( "关闭文件时错误", e );
            }
        }
    }

    /**
     * 解析MXL文档
     *
     * @param sc
     * @return
     * @throws org.ws.httphelper.exception.WSException
     */
    private void parser(Map data, InputSource sc, String dtdFile) throws WSException {
        // 设置DTD
        if (dtdFile != null && dtdFile.length() != 0) {
            sc.setSystemId(dtdFile);
        }

        Document doc = null;
        try {
            // 使用以下方法，可以不依赖任何XML包，但需要在CLASSPATH中指定顺序，Weblogic的包不支持GBK
            // DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            // DocumentBuilder proc = docFactory.newDocumentBuilder();
            // doc = proc.parse( sc );

            // 生成解析器
            DOMParser proc = new DOMParser();

            // 设置属性
            try {
                proc.setFeature(LOAD_EXTERNAL_DTD_FEATURE_ID, false);
            } catch (SAXException e) {
                log.warn("设置属性禁止DTD装载时错误");
            }

            // 解析文件
            proc.parse(sc);
            doc = proc.getDocument();
        } catch (Exception e) {
            throw new WSException(e.getMessage(),e);
        }

        parser(data, doc);
    }

    /**
     * 解析XML文档到数据总线
     *
     * @param doc 保存内容的DOM文档
     */
    private void parser(Map data, Document doc) throws WSException {
        if (doc == null || !doc.hasChildNodes()) {
            throw new WSException("文档没有节点信息");
        }

		/* 取根节点 */
        Node rootNode = doc.getFirstChild();
        while (rootNode != null) {
            if (rootNode.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
                DocumentType docType = (DocumentType) rootNode;
                docAttr.put(docType.getName(), docType);
            } else if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
				/* 解析数据 */
                parser(data, rootNode);
                return;
            }

            rootNode = rootNode.getNextSibling();
        }
        throw new WSException("文档没有内容节点");
    }

    /**
     * 解析数据到数据总线
     *
     * @param rootNode DOM文档保存内容的节点
     */
    private void parser(Map data, Node rootNode) throws WSException {
        if (!rootNode.hasChildNodes()) {
            return;
        }

        NamedNodeMap rootAttrs = rootNode.getAttributes();
        if (rootAttrs != null) {
            for (int ii = rootAttrs.getLength() - 1; ii >= 0; ii--) {
                Node nd = rootAttrs.item(ii);
                if (nd.getNodeType() == Node.ATTRIBUTE_NODE) {
                    String name = nd.getNodeName();
                    String value = nd.getNodeValue();
                    data.put(name, value);
                }
            }
        }

        // 解析节点
        String lastName = null;
        List<Map> list = null;
        NodeList nds = rootNode.getChildNodes();
        for (int ii = 0; ii < nds.getLength(); ii++) {
            Node detailNode = nds.item(ii);
            if (detailNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList nds1 = detailNode.getChildNodes();
                String name = detailNode.getNodeName();
                NamedNodeMap attrs = detailNode.getAttributes();

                // 取属性和子节点的数量
                int attrNum = (attrs == null) ? 0 : attrs.getLength();
                int nodeNum = nds1.getLength() + attrNum;

                if (nodeNum < 1) {
                    data.put(name, "");
                } else if (nodeNum == 1 && attrNum == 0) {
                    String value = nds1.item(0).getNodeValue();
                    if (value != null) {
                        if (lastName == null || lastName.compareTo(name) != 0) {
                            data.put(name, value);
                            lastName = name;
                            list = null;
                        } else {
                            if (list == null) {
                                // 第一个节点的内容
                                String v = String.valueOf(data.get(name));

                                list = new ArrayList<Map>();
                                data.put(name, list);

                                Map r = new HashMap();
                                r.put(name, v);
                                list.add(r);
                            }

                            Map r = new HashMap();
                            r.put(name, value);
                            list.add(r);
                        }
                    } else {
                        parserDetail(data, name, detailNode, attrNum, attrs);
                    }
                } else {
					/* 明细数据 */
                    parserDetail(data, name, detailNode, attrNum, attrs);
                }
            }
        }
    }

    /**
     * 解析明细数据到数据总线的指定节点下
     *
     * @param itemName 保存明细数据的节点名称
     * @param detailNode DOM文档中保存明细数据的节点
     * @param attrList
     */
    private void parserDetail(Map data, String itemName, Node detailNode, int attrSize,
                              NamedNodeMap attrList) throws WSException {

        Map subLine = null;
        if(data.containsKey(itemName)){
            Object obj = data.get(itemName);
            if(obj instanceof Map){
                List<Map> list = new ArrayList<Map>();
                list.add((Map)obj);
                subLine = new HashMap();
                list.add(subLine);
                data.put(itemName,list);
            }
            else if(obj instanceof List){
                List<Map> list = (List<Map>)data.get(itemName);
                subLine = new HashMap();
                list.add(subLine);
            }
        }
        else{
            subLine = new HashMap();
            data.put(itemName,subLine);
        }

        // 属性节点
        for (int ii = 0; ii < attrSize; ii++) {
            Node nd = attrList.item(ii);
            if (nd.getNodeType() == Node.ATTRIBUTE_NODE) {
                String name = nd.getNodeName();
                String value = nd.getNodeValue();
                subLine.put(name, value);
            }
        }

        // 包含节点
        if (detailNode != null) {
            NodeList childNodes = detailNode.getChildNodes();
            int num = childNodes.getLength();

            if (num > 0) {
                String text = null;
                for (int ii = 0; ii < num; ii++) {
                    Node nd = childNodes.item(ii);
                    short type = nd.getNodeType();
                    if (type == Node.ELEMENT_NODE) {
                        String name = nd.getNodeName();
                        NodeList nds1 = nd.getChildNodes();
                        NamedNodeMap attrs = nd.getAttributes();

                        // 取属性和子节点的数量
                        int attrNum = (attrs == null) ? 0 : attrs.getLength();
                        int nodeNum = nds1.getLength() + attrNum;
                        if (nodeNum < 1) {
                            subLine.put(name, "");
                        } else if (nodeNum == 1 && attrNum == 0) {
                            String value = nds1.item(0).getNodeValue();
                            subLine.put(name, value);
                        } else {
							/* 明细数据 */
                            parserDetail(subLine, name, nd, attrNum, attrs);
                        }
                    } else if (type == Node.TEXT_NODE) {
                        if (text == null) {
                            text = nd.getNodeValue();
                        } else {
                            text += nd.getNodeValue();
                        }
                    } else if (type == Node.CDATA_SECTION_NODE) {
                        text = nd.getNodeValue().trim();
                        if (attrSize == 0) {
                            data.put(itemName, text);
                        } else {
                            subLine.put(itemName, text);
                        }

                        return;
                    }
                }

                if (text != null) {
                    text = text.trim();
                    if (text.length() != 0) {
                        subLine.put(itemName, text);
                    }
                }
            }
        }
    }
}
