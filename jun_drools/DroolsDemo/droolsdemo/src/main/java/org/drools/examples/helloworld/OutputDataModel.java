package org.drools.examples.helloworld;

import java.util.HashMap;

public class OutputDataModel extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	
	public void addValue(String key, String value){
		this.put(key, value);
	}

}
