package com.xiruibin;

import java.util.Map.Entry;

public class DBDriverAutoLoad {

	public static void load() {
		for (Entry<String, String> entry : DBInfo.DB_DRIVER_MAP.entrySet()) {
			if (tryload(entry.getValue())) {
				DBInfo.currentdbtype = entry.getKey();
				break;
			}
		}
		
	}
	
	public static boolean tryload(String driver) {
		try {
            Class.forName(driver);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
	}
}
