package com.jun.plugin.xml;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ���ã��ӿڶ�Ӧ��ʵ����
 * @author luo
 *
 */
public class Configuration {
	
	private Map<String,String> serviceMap = new HashMap<String,String>();
	
	private final static String CONGIF_PATH = "config.xml";
	
	private String configFile = CONGIF_PATH;
	
	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
	
	protected void initConfig() throws Exception{
		paserXML();
	}
	
	/**
	 * �򵥽���xml�ļ�,��ʼ��serviceMap
	 * @throws DocumentException 
	 */
	private void paserXML() throws Exception{
		File file = new File(configFile);
        SAXReader reader = new SAXReader();
        
        // ��ȡXML�ļ�
        Document doc = reader.read(file);
        Element root = doc.getRootElement();
        List<Element> services = root.elements();
        for (Element serviceNode : services) {
        	String service = "";
        	String serviceImpl = "";
        	List<Element> propertys = serviceNode.elements();
        	for(Element property : propertys){
        		if("service".equals(property.attributeValue("name"))){
        			service = property.attributeValue("value");
        		}else{
        			serviceImpl = property.attributeValue("value");
        		}
        	}
        	serviceMap.put(service, serviceImpl);
        }
	}
	
	public String getServiceImpl(String serviceApi){
		return serviceMap.get(serviceApi);
	}

}
