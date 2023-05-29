package com.jun.plugin.util4j.common.game.env;

import java.io.File;

/**
 * 部署环境
 * @author Administrator
 */
class RunEnvironment extends Environment{
	
	@Override
	public String getJobClassDir() {
		return getRootDir()+File.separator+"bin";
	}

	@Override
	public String getConfDir() {
		return getRootDir()+File.separator+"conf";
	}
	
	@Override
	public boolean initEnv() {
		String currentRoot=System.getProperty("user.dir");
		File file1=new File(currentRoot+File.separator+"bin");
		File file2=new File(currentRoot+File.separator+"lib");
		if(file1.exists()||file2.exists())
		{
			setRootDir(currentRoot);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean initEnv(String rootDir) {
		File file1=new File(rootDir+File.separator+"bin");
		File file2=new File(rootDir+File.separator+"lib");
		if(file1.exists()||file2.exists())
		{
			setRootDir(rootDir);
			return true;
		}
		return false;
	}
}
