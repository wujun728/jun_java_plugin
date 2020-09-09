package com.github.ghsea.rpc.common.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.common.base.Preconditions;

public class PropertiesConfigHelper {

	private static Map<String, PropertiesConfiguration> file2Config = new HashMap<String, PropertiesConfiguration>();

	private static ReentrantLock lock = new ReentrantLock();

	private PropertiesConfigHelper() {

	}

	public static PropertiesConfiguration loadIfAbsent(String fileName) {
		Preconditions.checkArgument(fileName != null);
		if (lock.tryLock()) {
			try {
				if (!file2Config.containsKey(fileName)) {
					try {
						URL url = PropertiesConfigHelper.class.getClassLoader().getResource(fileName);
						PropertiesConfiguration config = new PropertiesConfiguration(url);
						file2Config.put(fileName, config);
					} catch (ConfigurationException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			} finally {
				lock.unlock();
			}
		}

		return file2Config.get(fileName);
	}

}
