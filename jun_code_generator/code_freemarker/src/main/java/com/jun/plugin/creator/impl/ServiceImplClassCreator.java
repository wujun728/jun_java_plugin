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
public class ServiceImplClassCreator extends AbstractFileCreator {
	private static ServiceImplClassCreator creator;

	private ServiceImplClassCreator() {
		super();
	}

	private ServiceImplClassCreator(Config conf) {
		super();
		init(conf);
	}

	public static synchronized ServiceImplClassCreator getInstance(Config conf) {
		if (null == creator) {
			creator = new ServiceImplClassCreator(conf);
		}
		return creator;
	}

	@Override
	public String getFileName(TableInfo tableInfo) {
		return tableInfo.getBeanName() + conf.getServiceName() + conf.getImplSuffix() + Constants.JAVA_SUFFIX;
	}

	@Override
	public String getTempletName() {
		return ModuleEnum.ServiceImpl.name() + Constants.TEMPLET_SUFFIX;
	}

	@Override
	public String getDirPath() {
		return javaPath + conf.getServiceimpl_dir();
	}

	@Override
	public void setPackageName(TableInfo tableInfo) {
		String serviceImplPackage = conf.getBasePackage() + Constants.PACKAGE_SEPARATOR + conf.getServicePackage()
				+ Constants.PACKAGE_SEPARATOR + conf.getServiceImplPackage();
		tableInfo.setServiceImplPackage(serviceImplPackage);
	}

}
