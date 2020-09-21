package org.epibolyteam.frequency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 访问频率限制
 * 
 * @author darkidiot
 * 
 */
public class FreqCheckUtil {
	private final static int INVOKE_FRNQUENCY_TTL = 60 * 1000;
	private final static Map<String, LifeAccessFrequency> invokeBlacks = new ConcurrentHashMap<String, LifeAccessFrequency>();

	public static boolean isPermit(String key, int maxAllowedFreq) {
		if (isOverFreq(key, maxAllowedFreq)) {
			return false;
		}
		return true;
	}

	/**
	 * 是否超过最高频率限制
	 * 
	 * @param appId
	 * @param maxAllowedFreq
	 * @return
	 */
	public static boolean isOverFreq(String key, int maxAllowedFreq) {
		if (key == null || key.trim().isEmpty()) {
			return false;
		}
		LifeAccessFrequency currentFreq = invokeBlacks.get(key);
		if (currentFreq == null) {
			return false;
		}
		return currentFreq.getFrequency() >= maxAllowedFreq;
	}
}