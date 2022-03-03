/**   
 * @Title: ResourceLoader.java 
 * @package com.sysmanage.common.tools.core 
 * @Description: 
 * @author Wujun
 * @date 2011-8-9 上午11:04:53 
 * @version V1.0   
 */

package com.jun.plugin.poi;

import java.io.File;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.jun.plugin.utils.WebUtil;

/**
 * @ClassName: ResourceLoader
 * @Description: 资源文件加载
 * @author Wujun
 * @date 2011-8-9 上午11:04:53
 * 
 */
public class TgResourceLoader extends DefaultResourceLoader {
	private final String E3_URL_PREFIX = "e3:";

	public Resource getResource(String location) {
		Assert.notNull(location, "location is required");

		if (location.startsWith(E3_URL_PREFIX)) {
			return getE3Resource(location);
		} else {
			return super.getResource(location);
		}
	}

	protected String getWebRoot() {
		return "WebRoot/";
	}

	protected Resource getE3Resource(String location) {
		String webPath = WebUtil.getWebAppRoot();
		if (webPath == null) {
			webPath = getWebRoot();// 不在web环境下
		}
		File dir = new File(webPath);
		if (dir.exists() == false) {// WebRoot不存在.
			webPath = "";
		}
		int index = location.indexOf(":");
		final String noPrefixLocation = location.substring(index + 1);
		String filePath = webPath + noPrefixLocation;
		File file = new File(filePath);
		if (file.exists() == false) {
			return super.getResource(noPrefixLocation);
		} else {
			return new FileSystemResource(filePath);
		}
	}

	public TgResourceLoader() {
		super();
	}

	public TgResourceLoader(ClassLoader arg0) {
		super(arg0);
	}
}
