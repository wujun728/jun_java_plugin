package com.jun.plugin.util4j.common.game.env;

import java.io.File;

public abstract class Environment {
	
	public enum EnvironmentType {
		/**
		 * 正常运行环境
		 */
		Run(1,RunEnvironment.class),
		/**
		 * maven开发环境
		 */
		Dev_Maven(2,Dev_MavenEnvironment.class), 	
		/**
		  *webapp运行环境
		  */
		 Run_WebApp(3,Run_WebAppEnvironment.class), 
		;
		
		private int value;
		private Class<?extends Environment> envImpl;
		private EnvironmentType(int value,Class<?extends Environment> impl) {
			this.value=value;
			this.envImpl=impl;
		}
		public int value()
		{
			return this.value;
		}
		
		public Class<? extends Environment> getEnvImpl() {
			return envImpl;
		}
		public void setEnvImpl(Class<? extends Environment> envImpl) {
			this.envImpl = envImpl;
		}
		public static EnvironmentType valueOf(int value)
		{
			for(EnvironmentType gt:values())
			{
				if(gt.value==value)
				{
					return gt;
				}
			}
			return null;
		}
	}
	
	private String rootDir;
	
	protected Environment() {
	}
	
	
	protected final void setRootDir(String rootDir)
	{
		this.rootDir=rootDir;
	}
	
	/**
	 * 根目录
	 * @return
	 */
	public final String getRootDir(){
		return rootDir;
	}
	
	private static final Environment findEnv()
	{
		for(EnvironmentType et:EnvironmentType.values())
		{
			try {
				Environment env=et.envImpl.newInstance();
				if(env.initEnv())
				{
					return env;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	public final String info()
	{
		StringBuffer sb=new StringBuffer();
		sb.append("**********CurrentEnvironment**********"+"\n");
		sb.append("*[RootDir]=["+getRootDir()+"]*"+"\n");
		sb.append("*[JobClassDir]=["+getJobClassDir()+"]*"+"\n");
		sb.append("*[ConfDir]=["+getConfDir()+"]*"+"\n");
		return sb.toString();
	}


	public String getLogConfigFile()
	{
		return getConfDir()+File.separatorChar+"log4j2.xml";
	}


	/**
	 * 获取本地数据目录
	 * @return
	 */
	public String getLoaclDataDir(){
		String filePath=getRootDir()+File.separatorChar+"localData";
		File f=new File(filePath);
		if(!f.exists())
		{
			f.mkdirs();
		}
		return f.getAbsolutePath();
	}


	/**
	 * 脚本类目录
	 * @return
	 */
	public abstract String getJobClassDir();
	
	/**
	 * 获取配置文件目录
	 * @return
	 */
	public abstract String getConfDir();
	
	public abstract boolean initEnv();
	
	public abstract boolean initEnv(String rootDir);
	
	private static Environment env;
	
	public static Environment getInstance()
	{
		synchronized (Environment.class) {
			if(env==null)
			{
				env=findEnv();
				if(env==null)
				{
					System.err.println("Not Found Environment");
				}else
				{
					System.err.println(env.info());
				}
			}
		}
		return env;
	}
	
	public static Environment getInstance(EnvironmentType type)
	{
		synchronized (Environment.class) {
			if(env==null)
			{
				try {
					env=type.envImpl.newInstance();
					env.initEnv();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				System.err.println(env.info());
			}
		}
		return env;
	}
}
