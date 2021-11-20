package com.techsoft.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.techsoft.ConnectionPool.DatabaseType;
import com.techsoft.container.DataServer;

public class ConfigProperties {
	private static String configfile = "conf/config.xml";
	private File home;
	private Document doc = null;
	private Element root = null;

	public ConfigProperties(File webHome) {
		home = webHome;
		File file = new File(home, configfile);
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(file);
			root = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void writeDocument() {
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream(home
					+ configfile));
			writer.write(doc);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDriver() {
		return root.elementText("driver");
	}

	public void setDriver(String driver) {
		root.element("driver").setText(driver);
		this.writeDocument();
	}

	public String getURL() {
		return root.elementText("URL");
	}

	public void setURL(String URL) {
		root.element("URL").setText(URL);
		this.writeDocument();
	}

	public String getUser() {
		return root.elementText("User");
	}

	public void setUser(String User) {
		root.element("User").setText(User);
		this.writeDocument();
	}

	public String getPassword() {
		return root.elementText("Password");
	}

	public void setPassword(String Password) {
		root.element("Password").setText(Password);
		this.writeDocument();
	}

	public String getMaxPool() {
		return root.elementText("MaxPool");
	}

	public void setMaxPool(String MaxPool) throws Exception {
		Integer maxpool = -1;
		try {
			maxpool = Integer.valueOf(MaxPool);
		} catch (Exception e) {
			throw new Exception("设置的最大连接池参数不是一个有效的整数， 请检查!");
		}
		root.element("MaxPool").setText(MaxPool);
		this.writeDocument();

		DataServer.getInstance().getPool().setMaxPool(maxpool);
	}

	public String getMinPool() {
		return root.elementText("MinPool");
	}

	public void setMinPool(String MinPool) throws Exception {
		Integer minpool = -1;
		try {
			minpool = Integer.valueOf(MinPool);
		} catch (Exception e) {
			throw new Exception("设置的最小连接池参数不是一个有效的整数， 请检查!");
		}
		root.element("MinPool").setText(MinPool);
		this.writeDocument();

		DataServer.getInstance().getPool().setMinPool(minpool);
	}

	public String getDefaultPool() {
		return root.elementText("DefaultPool");
	}

	public void setDefaultPool(String DefaultPool) throws Exception {
		Integer defaultpool = -1;
		try {
			defaultpool = Integer.valueOf(DefaultPool);
		} catch (Exception e) {
			throw new Exception("设置的初始连接池参数不是一个有效的整数， 请检查!");
		}
		root.element("DefaultPool").setText(DefaultPool);
		this.writeDocument();
		DataServer.getInstance().getPool().setInitPool(defaultpool);
	}

	public String getisDebug() {
		return root.elementText("isDebug");
	}

	public void setisDebug(String isDebug) {
		Boolean bool = Boolean.valueOf(isDebug);
		root.element("isDebug").setText(bool.toString());
		this.writeDocument();

		DataServer.getInstance().setDebug(bool);
		DataServer.getInstance().getPluginsManager().setIsDebug(bool);
	}

	public DatabaseType getDbtype() {
		return DatabaseType.valueOf(root.elementText("Dbtype").toUpperCase());
	}

	public void setDBType(DatabaseType type) {
		root.element("Dbtype").setText(type.name());
		this.writeDocument();
	}

	public String getSessionURL() {
		return root.elementText("sessionurl");
	}
	
	public String getGOperUserId(){
		return root.elementText("GUserId");
	}
	
	public void setGOperUserId(String gUserId){
		root.element("GUserId").setText(gUserId);
		this.writeDocument();
	}
	
}
