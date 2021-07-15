package com.tjgd.dao;

import java.util.List;
import com.tjgd.bean.Module;

public interface IModuleDAO {
	//--------获取单个模块--------------------
	public Module findByID(int moduleId) ;
	//--------获取所有模块----------------
	public List<Module> listModules() ;
}
