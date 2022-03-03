package org.springrain.frame.util.property;

import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyFile {
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	private PropertyResourceBundle propertyResourceBundle;

	public PropertyFile(String fileName) {
		propertyResourceBundle = (PropertyResourceBundle) ResourceBundle
				.getBundle(fileName);
	}

	/**
	 * 根据key获得对应的value
	 * 
	 * @param strPropertyName
	 *            key
	 * @return String
	 */
	public String getString(String strPropertyName) {
		try {
			return propertyResourceBundle.getString(strPropertyName);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public String getString(String strPropertyName, Object... obj) {
		String str = propertyResourceBundle.getString(strPropertyName);
		if (str == null) {
			return null;
		}
		return MessageFormat.format(str, obj);
	}

	public PropertyResourceBundle getBundle() {
		return propertyResourceBundle;
	}

	

}
