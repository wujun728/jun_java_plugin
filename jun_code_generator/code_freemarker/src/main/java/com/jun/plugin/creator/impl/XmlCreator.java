package com.jun.plugin.creator.impl;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.Constants;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.AbstractFileCreator;

/**
 * 创建xml映射文件
 * @author Wujun
 */
public class XmlCreator extends AbstractFileCreator {
	private static XmlCreator creator;

	private XmlCreator() {
		super();
	}

	private XmlCreator(Config conf) {
		super();
		init(conf);
	}

	public static synchronized XmlCreator getInstance(Config conf) {
		if (null == creator) {
			creator = new XmlCreator(conf);
		}
		return creator;
	}

	@Override
	public String getFileName(TableInfo tableInfo) {
		return tableInfo.getBeanName() + conf.getMapperXmlName() + Constants.XML_SUFFIX;
	}

	@Override
	public String getTempletName() {
		return ModuleEnum.MapperXML.name() + Constants.TEMPLET_SUFFIX;
	}

	@Override
	public String getDirPath() {
		return resourcesbasePath + conf.getXml_dir();
	}

	@Override
	public void setPackageName(TableInfo tableInfo) {
	}

}
