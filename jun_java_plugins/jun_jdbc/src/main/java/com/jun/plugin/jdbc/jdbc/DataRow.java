package com.jun.plugin.jdbc.jdbc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class DataRow extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public String getString(String key) {
		if (this.get(key) instanceof String) {
			return (String) this.get(key);
		}
		return "";
	}

	public Integer getInt(String key) {
		if (this.get(key) instanceof Integer) {
			return new Integer(this.get(key).toString());
		} else if (this.get(key) instanceof Long) {
			return new Long(this.get(key).toString()).intValue();
		} else if (this.get(key) instanceof BigDecimal) {
			return new BigDecimal(this.get(key).toString()).intValue();
		}
		return null;
	}

	public Long getLong(String key) {
		Object data = this.get(key);
		if (data instanceof Long || data instanceof Integer) {
			return new Long(data.toString());
		} else if (this.get(key) instanceof BigDecimal) {
			return new BigDecimal(data.toString()).longValue();
		}
		return null;
	}

	public Double getDouble(String key) {
		if (this.get(key) instanceof Double) {
			return new Double(this.get(key).toString());
		} else if (this.get(key) instanceof BigDecimal) {
			return new BigDecimal(this.get(key).toString()).doubleValue();
		}
		return null;
	}

	public Date getDate(String key) {
		Object data = this.get(key);
		if (data instanceof java.sql.Date) {
			return new Date(((java.sql.Date) data).getTime());
		} else if (data instanceof java.sql.Time) {
			return new Date(((java.sql.Time) data).getTime());
		} else if (data instanceof java.sql.Timestamp) {
			return new Date(((java.sql.Timestamp) data).getTime());
		}
		return null;
	}
}
