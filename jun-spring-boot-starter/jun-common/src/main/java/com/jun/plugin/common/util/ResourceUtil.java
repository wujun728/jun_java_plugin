
package com.jun.plugin.common.util;

import cn.hutool.core.io.IoUtil;
import org.springframework.core.io.*;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * 资源工具类
 *
 * @author L.cm
 */
public class ResourceUtil extends org.springframework.util.ResourceUtils {
	public static final String FTP_URL_PREFIX = "ftp:";

	/**
	 * 获取资源
	 * <p>
	 * 支持一下协议：
	 * <p>
	 * 1. classpath:
	 * 2. file:
	 * 3. ftp:
	 * 4. http: and https:
	 * 6. C:/dir1/ and /Users/lcm
	 * </p>
	 *
	 * @param resourceLocation 资源路径
	 * @return {Resource}
	 * @throws IOException IOException
	 */
	public static Resource getResource(String resourceLocation) throws IOException {
		Assert.notNull(resourceLocation, "Resource location must not be null");
		if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
			String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
			return new ClassPathResource(path);
		}
		if (resourceLocation.startsWith(FTP_URL_PREFIX)) {
			return new UrlResource(resourceLocation);
		}
		if (StringUtil.isHttpUrl(resourceLocation)) {
			return new UrlResource(resourceLocation);
		}
		if (resourceLocation.startsWith(FILE_URL_PREFIX)) {
			return new FileUrlResource(resourceLocation);
		}
		return new FileSystemResource(resourceLocation);
	}

	/**
	 * 读取资源文件为字符串
	 *
	 * @param resourceLocation 资源文件地址
	 * @return 字符串
	 */
	public static String getAsString(String resourceLocation) {
		try {
			Resource resource = getResource(resourceLocation);
			return IoUtil.read(resource.getInputStream(), Charsets.UTF_8);
//			return IoUtil.readToString(resource.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
