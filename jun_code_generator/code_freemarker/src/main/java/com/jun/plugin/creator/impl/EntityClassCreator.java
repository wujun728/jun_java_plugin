package com.jun.plugin.creator.impl;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.AbstractFileCreator;

/**
 * 创建bean
 * @author Wujun
 */
public class EntityClassCreator extends AbstractFileCreator {

	private static EntityClassCreator creator;

	private EntityClassCreator() {
		super();
	}

	private EntityClassCreator(Config conf) {
		super();
		init(conf);
	}

	public static synchronized EntityClassCreator getInstance(Config conf) {
		if (null == creator) {
			creator = new EntityClassCreator(conf);
		}
		return creator;
	}

	@Override
	public String getFileName(TableInfo tableInfo) {
		return tableInfo.getBeanName() + Constants.JAVA_SUFFIX;
	}

	@Override
	public String getTempletName() {
		return ModuleEnum.Entity.name() + Constants.TEMPLET_SUFFIX;
	}

	@Override
	public String getDirPath() {
		return javaPath + conf.getEntity_dir();
	}

	@Override
	public void setPackageName(TableInfo tableInfo) {
		String entityPackage = conf.getBasePackage() + Constants.PACKAGE_SEPARATOR + conf.getEntityPackage();
		tableInfo.setEntityPackage(entityPackage);
	}

}
