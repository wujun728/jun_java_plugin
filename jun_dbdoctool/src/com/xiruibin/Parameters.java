package com.xiruibin;

import java.util.ArrayList;
import java.util.List;

import com.xiruibin.db.util.StringUtils;

public final class Parameters {
	
	private String host = "192.168.1.170";
	private String user;
	private String port = "50001";// 默认值
	private String password;
	private String database;
	private String schema;
	private List<String> tables = new ArrayList<String>();
	private String path = "";

	public Parameters() {
	}

	public Parameters(String host, String user, String port, String password,
			String database, List<String> tables, String path) {
		this.host = host;
		this.user = user;
		this.port = port;
		this.password = password;
		this.database = database;
		this.tables = tables;
		this.path = path;
	}
	
	public Parameters(String host, String user, String port, String password,
			String database, String schema, List<String> tables, String path) {
		this.host = host;
		this.user = user;
		this.port = port;
		this.password = password;
		this.database = database;
		this.schema = schema;
		this.tables = tables;
		this.path = path;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if (StringUtils.hasLength(host)) {
			this.host = host;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		if (StringUtils.hasLength(port)) {
			this.port = port;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		if (tables != null && tables.size()>0) {
			this.tables = tables;
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		if (StringUtils.hasLength(path)) {
			this.path = path;
		}
	}

}
