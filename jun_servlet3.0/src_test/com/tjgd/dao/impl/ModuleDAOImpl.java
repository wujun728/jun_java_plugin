package com.tjgd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tjgd.DBHelper.DataSourceUtil;
import com.tjgd.bean.Module;
import com.tjgd.dao.IModuleDAO;

public class ModuleDAOImpl implements IModuleDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	DataSourceUtil ds = null;
	//--------构造函数中初始化---------------------------------------
	public ModuleDAOImpl() {
		try {
			ds = new DataSourceUtil();
			this.conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//-------获取单个模块---------------------------------------
	public Module findByID(int moduleId) {
		Module module = null;
		String sql = "select id,moduleName  from module where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, moduleId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				module = new Module();
				module.setId(rs.getInt("id"));
				module.setModuleName(rs.getString("moduleName"));
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}

		return module;
	}
	//--------获取所有模块--------------------------------------
	public List<Module> listModules() {
		List<Module> list = new ArrayList<Module>();
		String sql = "select id,moduleName from module";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Module module = new Module();
				module.setId(rs.getInt("id"));
				module.setModuleName(rs.getString("moduleName"));
				list.add(module);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
		return list;
	}
}
