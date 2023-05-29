package net.jueb.util4j.beta.tools.file;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitor {
	public static void main(String[] args) throws Exception{
		String dir="C:/Users/jaci/git/util4j/util4j/target";
		new FileMonitor().check2(dir);
        //Thread.sleep(30000);
        //monitor.stop();
    }

	/**
	 * 只监控文件发送变化,如果是子目录的文件改变,则目录会变,由于没有过滤目录发生变化,则目录下的文件改变不会监控到
	 * @param dir
	 * @throws Exception 
	 */
	public void check1(String dir) throws Exception
	{
		File directory = new File(dir);
	    // 轮询间隔 5 秒
	    long interval = TimeUnit.SECONDS.toMillis(5);
	    // 创建一个文件观察器用于处理文件的格式
	    IOFileFilter filter=FileFilterUtils.or(FileFilterUtils.suffixFileFilter(".class"),
	    		FileFilterUtils.suffixFileFilter(".jar"));
	    FileAlterationObserver observer = new FileAlterationObserver(directory,filter);
	    //设置文件变化监听器
	    observer.addListener(new MyFileListener());
	    FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
	    monitor.addObserver(observer);//文件观察
	    monitor.start();
	}
	
	public void check2(String dir) throws Exception
	{
		File directory = new File(dir);
	    // 轮询间隔 5 秒
	    long interval = TimeUnit.SECONDS.toMillis(5);
	    //后缀过滤器
	    IOFileFilter filefilter=FileFilterUtils.or(FileFilterUtils.suffixFileFilter(".class"),
	    		FileFilterUtils.suffixFileFilter(".jar"));
	    //子目录的后缀
	    IOFileFilter subFilefilter=FileFilterUtils.or(FileFilterUtils.directoryFileFilter(),filefilter);
	    //根目录和子目录变化
	    IOFileFilter filter = FileFilterUtils.or(filefilter,subFilefilter);
	    FileAlterationObserver observer = new FileAlterationObserver(directory,filter);
	    //设置文件变化监听器
	    observer.addListener(new MyFileListener());
	    FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
	    monitor.addObserver(observer);//文件观察
	    monitor.start();
//	    monitor.addObserver(observer);//文件观察,如果在start后面加,则会触发所有文件创建
	}
}

final class MyFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		return true;
	}
	
}

final class MyFileListener implements FileAlterationListener{
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor start scan files..");
    }


    @Override
    public void onDirectoryCreate(File file) {
        System.out.println(file.getName()+" director created.");
    }


    @Override
    public void onDirectoryChange(File file) {
        System.out.println(file.getName()+" director changed.");
    }


    @Override
    public void onDirectoryDelete(File file) {
        System.out.println(file.getName()+" director deleted.");
    }


    @Override
    public void onFileCreate(File file) {
        System.out.println(file.getName()+" created.");
    }


    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName()+" changed.");
    }


    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName()+" deleted.");
    }


    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        System.out.println("monitor stop scanning..");
    }
}