package com.jun.plugin.creator.impl;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.AbstractFileCreator;

/**
 * 创建controller
 * @author Wujun
 */
public class ControllerClassCreator extends AbstractFileCreator {
	private static ControllerClassCreator creator;

	private ControllerClassCreator() {
		super();
	}

	private ControllerClassCreator(Config conf) {
		super();
		init(conf);
	}

	public static synchronized ControllerClassCreator getInstance(Config conf) {
		if (null == creator) {
			creator = new ControllerClassCreator(conf);
		}
		return creator;
	}

	@Override
	public String getFileName(TableInfo tableInfo) {
		return tableInfo.getBeanName() + conf.getControllerName() + Constants.JAVA_SUFFIX;
	}

	@Override
	public String getTempletName() {
		return ModuleEnum.Controller.name() + Constants.TEMPLET_SUFFIX;
	}

	@Override
	public String getDirPath() {
		return javaPath + conf.getController_dir();
	}

	@Override
	public void setPackageName(TableInfo tableInfo) {
		String controllerPackage = conf.getBasePackage() + Constants.PACKAGE_SEPARATOR + conf.getControllerPackage();
		tableInfo.setControllerPackage(controllerPackage);
	}

}
