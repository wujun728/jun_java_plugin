package com.jun.plugin.creator.impl;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.AbstractFileCreator;

/**
 * 创建业务接口类
 * @author Wujun
 */
public class ServiceClassCreator extends AbstractFileCreator {
	private static ServiceClassCreator creator;

	private ServiceClassCreator() {
		super();
	}

	private ServiceClassCreator(Config conf) {
		super();
		init(conf);
	}

	public static synchronized ServiceClassCreator getInstance(Config conf) {
		if (null == creator) {
			creator = new ServiceClassCreator(conf);
		}
		return creator;
	}

	@Override
	public String getFileName(TableInfo tableInfo) {
		return  tableInfo.getBeanName() + conf.getServiceName() + Constants.JAVA_SUFFIX;
	}

	@Override
	public String getTempletName() {
		return ModuleEnum.Service.name() + Constants.TEMPLET_SUFFIX;
	}

	@Override
	public String getDirPath() {
		return javaPath + conf.getService_dir();
	}

	@Override
	public void setPackageName(TableInfo tableInfo) {
		String servicePackage = conf.getBasePackage() + Constants.PACKAGE_SEPARATOR + conf.getServicePackage();
		tableInfo.setServicePackage(servicePackage);
	}

}
