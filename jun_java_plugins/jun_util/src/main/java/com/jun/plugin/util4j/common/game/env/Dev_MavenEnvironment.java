package com.jun.plugin.util4j.common.game.env;

import java.io.File;

/**
 * maven开发环境
 * @author Administrator
 */
class Dev_MavenEnvironment extends Environment{

	@Override
	public String getJobClassDir() {
		return getRootDir()+File.separator+"target"+File.separator+"classes";
	}

	@Override
	public String getConfDir() {
		return getRootDir()+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"conf";
	}
	
	@Override
	public boolean initEnv() {
		//webapp环境判断
		String currentRoot=System.getProperty("user.dir");
		File file1=new File(currentRoot+"/target");
		File file2=new File(currentRoot+"/.project");
		if(file1.exists()||file2.exists())
		{
			setRootDir(currentRoot);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean initEnv(String rootDir) {
		File file1=new File(rootDir+"/target");
		File file2=new File(rootDir+"/.project");
		if(file1.exists()||file2.exists())
		{
			setRootDir(rootDir);
			return true;
		}
		return false;
	}
}
