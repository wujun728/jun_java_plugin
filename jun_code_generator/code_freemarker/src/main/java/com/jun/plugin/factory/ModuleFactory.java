package com.jun.plugin.factory;

import com.jun.plugin.bean.Config;
import com.jun.plugin.bean.ModuleEnum;
import com.jun.plugin.bean.TableInfo;
import com.jun.plugin.creator.FileCreator;
import com.jun.plugin.creator.impl.*;

/**
 * 生成代码
 */
public class ModuleFactory {
	private ModuleFactory() {
		super();
	}

	public static FileCreator create(String module, Config conf, TableInfo tableInfo) {

		FileCreator creator = null;
		if (module.equals(ModuleEnum.Entity.name())) {
			creator = EntityClassCreator.getInstance(conf);
		} else if (module.equals(ModuleEnum.Controller.name())) {
			creator = ControllerClassCreator.getInstance(conf);
		} else if (module.equals(ModuleEnum.Service.name())) {
			creator = ServiceClassCreator.getInstance(conf);
		} else if (module.equals(ModuleEnum.ServiceTest.name())) {
			creator = ServiceTestClassCreator.getInstance(conf);
		} else if (module.equals(ModuleEnum.ServiceImpl.name())) {
			creator = ServiceImplClassCreator.getInstance(conf);
		} else if (module.equals(ModuleEnum.Dao.name())) {
			creator = DaoClassCreator.getInstance(conf);
		} else if (module.equals(ModuleEnum.MapperXML.name())) {
			creator = XmlCreator.getInstance(conf);
		}
		return creator;

	}
}
