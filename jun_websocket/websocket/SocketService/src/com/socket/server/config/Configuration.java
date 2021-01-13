package com.socket.server.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.socket.util.Constants;

/**
 * 
 * @author luo
 *
 */
public class Configuration implements ContainerConfig{
	
	private List<ConfigInfo> configInfoList;
	
	private final static String CONGIF_PATH = "config.xml";
	
	private String configFile = CONGIF_PATH;
	
	public Configuration(){
		this(CONGIF_PATH);
	}
	
	public Configuration(String configFile){
		this.configFile = configFile;
		initConfig();
	}
	
	public String getConfigFile() {
		return configFile;
	}

	public void initConfig(){
		try {
			resolveXML();
		} catch (DocumentException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * resolve xml file,init serviceMap
	 * @throws DocumentException 
	 */
	private void resolveXML() throws DocumentException{
		File file = new File(configFile);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        
        configInfoList = new ArrayList<ConfigInfo>();
        
        Element root = doc.getRootElement();
        List<Element> servers = root.elements();
        
        for(Element server : servers){
        	ConfigInfo info = new ConfigInfo();
        	
        	//get server config
        	String ip = server.attributeValue(Constants.SERVER_IP);
        	if(null != ip)
        		info.setIp(ip);
        	String port = server.attributeValue(Constants.SERVER_PORT);
        	if(null != port)
        		info.setPort(Integer.parseInt(port));
        	String connetion = server.attributeValue(Constants.SERVER_CONNECTION);
        	if(null != connetion)
        		info.setMaxConnection(Integer.parseInt(connetion));
        	String maxThreads = server.attributeValue(Constants.THREAD_POOL_MAX_SIZE);
        	if(null != maxThreads)
        		info.setMaxThreads(Integer.parseInt(maxThreads));
        	String timeout = server.attributeValue(Constants.TIME_OUT);
        	if(null != timeout)
        		info.setTimeout(Long.parseLong(timeout));
        	
        	//get service config
        	List<Element> servicesList = server.elements("services");
        	if(null == servicesList && servicesList.isEmpty())
        		continue;
        	List<Element> services = servicesList.get(0).elements("service");
        	
        	Map<String,String> serviceMap = new HashMap<String,String>();
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
        	info.setServiceMap(serviceMap);
        	configInfoList.add(info);
        }
	}
	

	@Override
	public List<ConfigInfo> getConfigInfos() {
		return configInfoList;
	}
}
