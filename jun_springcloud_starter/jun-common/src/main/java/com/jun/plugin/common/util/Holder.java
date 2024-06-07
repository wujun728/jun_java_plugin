
package com.jun.plugin.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 一些常用的单利对象
 *
 * @author L.cm
 */
public interface Holder {

	/**
	 * RANDOM
	 */
	Random RANDOM = new Random();

	/**
	 * SECURE_RANDOM
	 */
	SecureRandom SECURE_RANDOM = new SecureRandom();

}
