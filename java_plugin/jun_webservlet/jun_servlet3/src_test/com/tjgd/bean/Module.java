package com.tjgd.bean;
/**
 * 模块
 * @author yeqing
 *
 */
public class Module {
	//-------声明私有属性--------------------------
	private int id;  //模块ID
	private String moduleName;  //模块名称
    //-------各个属性的getXxx()和setXxx()方法-------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	@Override
	public String toString() {
		return "Module [id=" + id + ", moduleName=" + moduleName + "]";
	}
	
}