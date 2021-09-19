package com.jun.plugin.commons.util.constant;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public enum HibernateConf {
	mysql("com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQLDialect",
			"jdbc:mysql://%s:%s/%s"), sqlserver2005(
			"net.sourceforge.jtds.jdbc.Driver",
			"org.hibernate.dialect.SQLServer2005Dialect",
			"jdbc:jtds:sqlserver://%s:%s/%s");
	private final String driverClass;
	private final String dialect;
	private final String urlFormat;// 联接地址格式，顺序为：ip/port/databasename

	private HibernateConf(String driverClass, String dialect, String urlFormat) {
		this.driverClass = driverClass;
		this.dialect = dialect;
		this.urlFormat = urlFormat;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public String getDialect() {
		return dialect;
	}

	public String getUrl(String ip, int port, String databasename,
			String... params) {
		String url = String.format(urlFormat, ip, port, databasename);
		if (ArrayUtils.isNotEmpty(params)) {
			url = url + "?";
			int count = params.length / 2;
			for (int i = 0; i < count; i++) {
				if (i == count - 1) {
					url = url
							+ String.format("%s=%s", params[2 * i],
									params[2 * i + 1]);
				} else {
					url = url
							+ String.format("%s=%s", params[2 * i],
									params[2 * i + 1]) + "&";
				}

			}
		}
		return url;
	}

	public static HibernateConf get(String type) {
		HibernateConf retobj = mysql;// 默认是mysql
		if (StringUtils.isNotBlank(type)) {
			for (HibernateConf ele : HibernateConf.values()) {
				if (ele.name().equalsIgnoreCase(type)) {
					retobj = ele;
					break;
				}
			}
		}
		return retobj;
	}

}
