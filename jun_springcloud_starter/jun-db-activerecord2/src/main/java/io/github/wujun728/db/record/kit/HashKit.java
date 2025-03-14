/**
 * Copyright (c) 2011-2023, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wujun728.db.record.kit;

import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;

public class HashKit {
	
	public static final long FNV_OFFSET_BASIS_64 = 0xcbf29ce484222325L;
	public static final long FNV_PRIME_64 = 0x100000001b3L;
	
	// private static final java.security.SecureRandom random = new java.security.SecureRandom();
	private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
	private static final char[] CHAR_ARRAY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public static long fnv1a64(String key) {
		long hash = FNV_OFFSET_BASIS_64;
		for(int i=0, size=key.length(); i<size; i++) {
			hash ^= key.charAt(i);
			hash *= FNV_PRIME_64;
		}
		return hash;
	}
	
	public static String md5(String srcStr){
		return hash("MD5", srcStr);
	}
	
	public static String sha1(String srcStr){
		return hash("SHA-1", srcStr);
	}
	
	public static String sha256(String srcStr){
		return hash("SHA-256", srcStr);
	}
	
	public static String sha384(String srcStr){
		return hash("SHA-384", srcStr);
	}
	
	public static String sha512(String srcStr){
		return hash("SHA-512", srcStr);
	}
	
	public static String hash(String algorithm, String srcStr) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
			return toHex(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toHex(byte[] bytes) {
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i=0; i<bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
	
	/**
	 * md5 128bit 16bytes
	 * sha1 160bit 20bytes
	 * sha256 256bit 32bytes
	 * sha384 384bit 48bytes
	 * sha512 512bit 64bytes
	 */
	public static String generateSalt(int saltLength) {
		StringBuilder salt = new StringBuilder(saltLength);
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i=0; i<saltLength; i++) {
			salt.append(CHAR_ARRAY[random.nextInt(CHAR_ARRAY.length)]);
		}
		return salt.toString();
	}
	
	public static String generateSaltForSha256() {
		return generateSalt(32);
	}
	
	public static String generateSaltForSha512() {
		return generateSalt(64);
	}
	
	// 生成随机字符串
	public static String generateRandomStr(int strLength) {
		return generateSalt(strLength);
	}
	
	public static boolean slowEquals(byte[] a, byte[] b) {
		if (a == null || b == null) {
			return false;
		}
		
		int diff = a.length ^ b.length;
		for(int i=0; i<a.length && i<b.length; i++) {
			diff |= a[i] ^ b[i];
		}
		return diff == 0;
    }
}




