/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.spring.mapper.MapperScannerConfigurer;

import com.osmp.web.utils.PackageUtils;
import com.osmp.web.utils.Utils;

/**
 * Spring Mybatis整合
 * 
 * 通过通配符方式配置basePackage
 * 
 * @author wangkaiping
 * @version 1.0 2013-2-26
 */
public class PackagesMapperScannerConfigurer extends MapperScannerConfigurer {

	private final static Logger log = Logger.getLogger(PackagesMapperScannerConfigurer.class);

	@Override
	public void setBasePackage(String basePackage) {
		List<String> list = PackageUtils.getPackages(basePackage);
		if (list != null && list.size() > 0) {
			super.setBasePackage(Utils.join(list.toArray(), ","));
		} else {
			log.warn("参数basePackage:" + basePackage + "，未找到任何包");
		}

	}
}
