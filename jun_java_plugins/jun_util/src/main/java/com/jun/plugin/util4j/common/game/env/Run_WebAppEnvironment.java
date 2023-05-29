package com.jun.plugin.util4j.common.game.env;

import java.io.File;

import org.apache.commons.lang.StringUtils;

/**
 * webapp环境
 * @author Administrator
 */
class Run_WebAppEnvironment extends Environment{

	@Override
	public String getJobClassDir() {
		return getRootDir()+File.separator+"WEB-INF"+File.separator+"classes";
	}

	@Override
	public String getConfDir() {
		return getJobClassDir()+File.separator+"conf";
	}
	
	@Override
	public boolean initEnv() {
		String ClassDir=getClass().getClassLoader().getResource("/").getPath();
		File file=new File(ClassDir);
		File webinf=file.getParentFile();
		File webContext=webinf.getParentFile();
		if(file.exists() && webinf.exists() && webContext.exists())
		{
			String rootDir=webinf.getAbsolutePath();
			File file1=new File(rootDir+File.separator+"classes");
			File file2=new File(rootDir+File.separator+"lib");
			File file3=new File(rootDir+File.separator+"web.xml");
			if(file1.isDirectory() && file2.isDirectory()&& file3.isFile())
			{
				setRootDir(webContext.getAbsolutePath());//以webContext为根目录
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean initEnv(String rootDir) {
		if(StringUtils.isEmpty(rootDir))
		{
			return true;
		}
		File webContext=new File(rootDir);
		if(webContext.isDirectory())
		{
			File webinf=new File(webContext.getAbsolutePath()+File.separator+"WEB-INF");
			if(webinf.isDirectory())
			{
				File file1=new File(rootDir+File.separator+"classes");
				File file2=new File(rootDir+File.separator+"lib");
				File file3=new File(rootDir+File.separator+"web.xml");
				if(file1.isDirectory() && file2.isDirectory()&& file3.isFile())
				{
					setRootDir(rootDir);
					return true;
				}
			}
		}
		return false;
	}
	
	public String getWebInfDir()
	{
		return getRootDir()+File.separator+"WEB-INF";
	}
	
	/**
	 * 获取本地数据目录
	 * @return
	 */
	public String getLoaclDataDir(){
		String filePath=getWebInfDir()+File.separator+"localData";
		File f=new File(filePath);
		if(!f.exists())
		{
			f.mkdirs();
		}
		return f.getAbsolutePath();
	}
}
