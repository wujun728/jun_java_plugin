package com.jun.plugin.poi.test.excel.exception;

import org.xml.sax.SAXException;

/**
 * @author Wujun
 *
 * @date 2016-2-2
 * Description:  this only a mark exception ，when you want to stop the sax
 * 
 */
public class BingSaxReadStopException  extends SAXException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public BingSaxReadStopException(String message) {
		super(message);
		
	}

}
