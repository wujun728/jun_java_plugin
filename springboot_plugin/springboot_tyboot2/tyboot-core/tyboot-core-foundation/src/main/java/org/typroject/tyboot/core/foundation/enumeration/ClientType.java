package org.typroject.tyboot.core.foundation.enumeration;

public enum ClientType {
	iOS,
	Android,
	Wxpub,
	Wxxcx,
	Web,
	Postman,
	Sdk;
	
	public static ClientType getInstanceByName(String name){
		for(ClientType f: ClientType.values()){
			if(f.name().equals(name)){
				return f;
			}
		}
		return null;	
	}
}
