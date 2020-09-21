/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import java.util.List;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.osmp.web.utils.PackageUtils;
import com.osmp.web.utils.Utils;

/**
 * Spring Mybatis整合 通过通配符方式配置typeAliasesPackage
 * 
 * @author wangkaiping
 * @version 1.0 2013-2-25
 */
public class PackagesSqlSessionFactoryBean extends SqlSessionFactoryBean {

	private final static Logger log = Logger.getLogger(PackagesSqlSessionFactoryBean.class);

	@Override
	public void setTypeAliasesPackage(String typeAliasesPackage) {
		List<String> list = PackageUtils.getPackages(typeAliasesPackage);
		if (list != null && list.size() > 0) {
			super.setTypeAliasesPackage(Utils.join(list.toArray(), ","));
		} else {
			log.warn("参数typeAliasesPackage:" + typeAliasesPackage + "，未找到任何包");
		}
	}

	@Override
	public void setPlugins(Interceptor[] plugins) {
		// TODO Auto-generated method stub
		super.setPlugins(plugins);
	}

}
