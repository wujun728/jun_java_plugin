package com.baijob.commonTools;

import org.junit.Assert;
import org.junit.Test;

public class SecureUtilTest {
	@Test
	public void getMd5Test() {
		String key = "abc";
		
		String md5 = SecureUtil.md5(key);
		String sha1 = SecureUtil.sha1(key);
		long crc32 = SecureUtil.crc32(key);
		
		Assert.assertEquals(md5, "kAFQmDzST7DWlj99KOF/cg==");
		Assert.assertEquals(sha1, "qZk+NkcGgWq6PiVxeFDCbJzQ2J0=");
		Assert.assertEquals(crc32, 891568578L);
	}
}
