package com.jun.plugin.creator.impl;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.AbstractFileCreator;

/**
 * 创建业务接口实例类
 * @author Wujun
 */
public class ServiceTestClassCreator extends AbstractFileCreator {
	private static ServiceTestClassCreator creator;

	private ServiceTestClassCreator() {
		super();
	}

	private ServiceTestClassCreator(Config conf) {
		super();
		init(conf);
	}

	public static synchronized ServiceTestClassCreator getInstance(Config conf) {
		if (null == creator) {
			creator = new ServiceTestClassCreator(conf);
		}
		return creator;
	}

	@Override
	public String getFileName(TableInfo tableInfo) {
		return tableInfo.getBeanName() + conf.getServiceName() + conf.getTestSuffix() + Constants.JAVA_SUFFIX;
	}

	@Override
	public String getTempletName() {
		return ModuleEnum.ServiceTest.name() + Constants.TEMPLET_SUFFIX;
	}

	@Override
	public String getDirPath() {
		return javaPath + conf.getServicePackage();
	}

	@Override
	public void setPackageName(TableInfo tableInfo) {
		String serviceTestPackage = conf.getBasePackage() + Constants.PACKAGE_SEPARATOR + conf.getServicePackage();
		tableInfo.setServiceTestPackage(serviceTestPackage);
	}

}
