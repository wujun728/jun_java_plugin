package com.caland.common.web.session.id;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class JdkUUIDGenerator implements SessionIdGenerator {
	public String get() {
		return StringUtils.remove(UUID.randomUUID().toString(), '-');
	}

	public static void main(String[] args) {
		UUID.randomUUID();
		long time = System.currentTimeMillis();
		int count = 1;
		for (int i = 0; i < 100000; i++) {
			UUID uuid = UUID.randomUUID();
			System.out.println(count++ +":" + uuid.toString());
		}
		time = System.currentTimeMillis() - time;
		System.out.println(time);
	}
}
