
package com.jun.plugin.common.util;

import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.net.URL;

/**
 * 用来获取各种目录
 *
 * @author L.cm
 */
public class PathUtil {

	/**
	 * 获取jar包运行时的当前目录
	 * @return {String}
	 */
	@Nullable
	public static String getJarPath() {
		try {
			URL url = PathUtil.class.getResource(StringPool.SLASH).toURI().toURL();
			return PathUtil.toFilePath(url);
		} catch (Exception e) {
			String path = PathUtil.class.getResource(StringPool.EMPTY).getPath();
			return new File(path).getParentFile().getParentFile().getAbsolutePath();
		}
	}

	@Nullable
	private static String toFilePath(@Nullable URL url) {
		if (url == null) { return null; }
		String protocol = url.getProtocol();
		String file = UriUtils.decode(url.getPath(), Charsets.UTF_8);
		if (ResourceUtils.URL_PROTOCOL_FILE.equals(protocol)) {
			return new File(file).getParentFile().getParentFile().getAbsolutePath();
		} else if (ResourceUtils.URL_PROTOCOL_JAR.equals(protocol)
			|| ResourceUtils.URL_PROTOCOL_ZIP.equals(protocol)) {
			int ipos = file.indexOf(ResourceUtils.JAR_URL_SEPARATOR);
			if (ipos > 0) {
				file = file.substring(0, ipos);
			}
			if (file.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
				file = file.substring(ResourceUtils.FILE_URL_PREFIX.length());
			}
			return new File(file).getParentFile().getAbsolutePath();
		}
		return file;
	}

}
