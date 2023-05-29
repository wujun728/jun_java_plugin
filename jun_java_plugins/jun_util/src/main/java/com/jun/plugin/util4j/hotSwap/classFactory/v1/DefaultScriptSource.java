package com.jun.plugin.util4j.hotSwap.classFactory.v1;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.file.FileUtil;

public class DefaultScriptSource implements ScriptSource{

	protected final Logger log=LoggerFactory.getLogger(getClass());
	private final Set<ScriptSourceEventListener> listeners=new HashSet<>();
	private final String scriptDir;
	public static final long DEFAULT_UPDATE_INTERVAL=TimeUnit.SECONDS.toMillis(10);
	private final long updateInterval;
	private ScanFilter scanFilter;
	
	public DefaultScriptSource(String scriptDir) throws Exception {
		this(scriptDir, DEFAULT_UPDATE_INTERVAL, null);
	}
	public DefaultScriptSource(String scriptDir,long updateInterval) throws Exception {
		this(scriptDir, updateInterval, null);
	}
	
	public DefaultScriptSource(String scriptDir,long updateInterval,ScanFilter scanFilter) throws Exception {
		Objects.requireNonNull(scriptDir);
		File file=new File(scriptDir);
		if(!file.exists() || !file.isDirectory())
		{
			throw new IllegalArgumentException("scriptDir not dir:"+scriptDir);
		}
		this.scriptDir=scriptDir;
		if(updateInterval<=1000)
		{
			throw new IllegalArgumentException("updateInterval low 1000,updateInterval="+updateInterval);
		}
		this.updateInterval=updateInterval;
		this.scanFilter=scanFilter;
		init();
	}
	
	protected void init() throws Exception
	{
		FileAlterationObserver observer=buildFileAlterationObserver(scriptDir);
		observer.addListener(new FileListener());
		FileAlterationMonitor monitor=new FileAlterationMonitor(updateInterval);
		monitor.addObserver(observer);
		monitor.start();
		scanResources();
	}
	
	protected FileAlterationObserver buildFileAlterationObserver(String directory)
	{
	    //后缀过滤器
	    IOFileFilter suffixFileFilter=FileFilterUtils.or(FileFilterUtils.suffixFileFilter(".class"),FileFilterUtils.suffixFileFilter(".jar"));
	    //子目录变化
	    IOFileFilter rootAndSubFilefilter=FileFilterUtils.or(FileFilterUtils.directoryFileFilter(),suffixFileFilter);
	    return new FileAlterationObserver(directory,rootAndSubFilefilter);
	}
	
	private class FileListener implements FileAlterationListener{
		@Override
		public void onDirectoryChange(File paramFile) {
		}
		@Override
		public void onDirectoryCreate(File paramFile) {
		}
		@Override
		public void onDirectoryDelete(File paramFile) {
			if(paramFile.getPath().equals(scriptDir))
			{
				throwEvent(ScriptSourceEvent.Delete);
			}
		}
		@Override
		public void onFileChange(File paramFile) {
			scanResources();
			throwEvent(ScriptSourceEvent.Change);
		}
		@Override
		public void onFileCreate(File paramFile) {
			scanResources();
			throwEvent(ScriptSourceEvent.Change);
		}
		@Override
		public void onFileDelete(File paramFile) {
			scanResources();
			throwEvent(ScriptSourceEvent.Change);
		}
		@Override
		public void onStart(FileAlterationObserver paramFileAlterationObserver) {
		}
		@Override
		public void onStop(FileAlterationObserver paramFileAlterationObserver) {
		}
	}
	
	@Override
	public void addEventListener(ScriptSourceEventListener listener) {
		Objects.requireNonNull(listener);
		listeners.add(listener);
	}

	@Override
	public Set<ScriptSourceEventListener> getEventListeners() {
		return listeners;
	}

	@Override
	public void throwEvent(ScriptSourceEvent event) {
		listeners.stream().forEach(a->a.onEvent(event));
	}
	
	/**
	 * 扫描可用资源到缓存
	 */
	protected void scanResources()
	{
		try {
			File scriptDirFile=new File(scriptDir);
			if(scriptDirFile.exists() && scriptDirFile.isDirectory())
			{
				if(scanFilter==null || (scanFilter!=null && scanFilter.accpetJars()))
				{
					for(File file:FileUtil.findJarFileByDir(scriptDirFile))
					{//扫描jar
						cacheJars.add(file.toURI().toURL());
					}
				}
				if(scanFilter==null || (scanFilter!=null && scanFilter.accpetDirClass()))
				{
					HashMap<String, File> map=FileUtil.findClassByDirAndSub(scriptDirFile);
					if(!map.isEmpty())
					{//扫描class文件
						DefaultDirClassFile df=new DefaultDirClassFile(new ArrayList<>(map.keySet()),
								scriptDirFile.toURI().toURL());
						cacheDirClassFile.add(df);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private class DefaultDirClassFile implements DirClassFile{
		private final List<String> list;
		private final URL url;
		
		public DefaultDirClassFile(List<String> list, URL url) {
			super();
			this.list = list;
			this.url = url;
		}

		@Override
		public List<String> getClassNames() {
			return list;
		}

		@Override
		public URL getRootDir() {
			return url;
		}
	}

	private final List<URL> cacheJars=new CopyOnWriteArrayList<>();
	private final List<URLClassFile> cacheURLClassFile=new CopyOnWriteArrayList<>();
	private final List<DirClassFile> cacheDirClassFile=new CopyOnWriteArrayList<>();
	
	@Override
	public List<URL> getJars() {
		return Collections.unmodifiableList(cacheJars);
	}

	@Override
	public List<URLClassFile> getUrlClassFiles() {
		return Collections.unmodifiableList(cacheURLClassFile);
	}

	@Override
	public List<DirClassFile> getDirClassFiles() {
		return Collections.unmodifiableList(cacheDirClassFile);
	}
	
	public static interface ScanFilter{
		public boolean accpetJars();
		public boolean accpetDirClass();
	}
}
