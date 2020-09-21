package org.coody.framework.core.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.coody.framework.core.util.StringUtil;

public class ConfigBuilder {
	
	private String[] dirs;

	public ConfigBuilder(String ...dirs){
		this.dirs=dirs;
	}
	
	public void build() throws IOException, URISyntaxException {
		for(String dir:dirs){
			Enumeration<URL> urls=this.getClass().getClassLoader().getResources(dir);
			if(StringUtil.isNullOrEmpty(urls)){
				return;
			}
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				File file=new File(url.toURI());
				loadPropertByDir(file);
			}
		}
	}
	private void loadPropertByDir(File file) throws URISyntaxException, IOException{
		if(!file.isDirectory()){
			FileInputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();  
			prop.load(inStream);  
			Enumeration<Object> keys=prop.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value=prop.getProperty(key);
				if(StringUtil.hasNull(key,value)){
					continue;
				}
				System.setProperty(key, value);
			}
			return;
		}
		String[] files=file.list();
		if(StringUtil.isNullOrEmpty(files)){
			return;
		}
		for(String filePath:files){
			String path=file.getPath()+"/"+filePath;
			File childFile=new File(path);
			loadPropertByDir(childFile);
		}
	}
}
