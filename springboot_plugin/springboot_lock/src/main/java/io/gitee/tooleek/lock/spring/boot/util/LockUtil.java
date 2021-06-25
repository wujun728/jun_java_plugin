package io.gitee.tooleek.lock.spring.boot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 工具类
 * @author Wujun
 *
 */
public class LockUtil {
	
	/**
	 * 生成随机key
	 * @return
	 */
	public static String generateRandomKey() {
		String dateKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		Random random=new Random();
		int randomKey = random.ints(100000, 999999).limit(1).findFirst().getAsInt();
		return new StringBuilder(dateKey).append(randomKey).toString();
	}

}
