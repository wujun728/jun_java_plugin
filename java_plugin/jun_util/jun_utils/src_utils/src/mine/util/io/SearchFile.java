package mine.util.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * FileDirectory类用于查找指定根目录下的所有文件和目录 可以通过正则表达式对要查找的 文件及目录进行筛选
 * 
 * @author Touch
 */
public final class SearchFile {
	// 存放文件
	private List<File> fileList = new ArrayList<File>();
	// 存放目录
	private List<File> directoryList = new ArrayList<File>();
	// 存放文件和目录
	private List<File> list = new ArrayList<File>();
	private File file;// 目录
	private String regex;// 正则表达式

	public SearchFile(String path) {
		file = new File(path);
		this.regex = ".*";
	}

	public SearchFile(File file) {
		this.file = file;
		this.regex = ".*";
	}

	public SearchFile(String path, String regex) {
		file = new File(path);
		this.regex = regex;
	}

	public SearchFile(File file, String regex) {
		this.file = file;
		this.regex = regex;
	}

	// 返回当前目录下的所有文件及子目录
	public List<File> files() {
		File[] files = file.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : files)
			if (f.getName().matches(regex))
				list.add(f);
		return list;
	}

	// 返回该根目录下的所有文件
	public List<File> allFiles() {
		if (list.isEmpty())
			search(file);
		return fileList;
	}

	// 返回该根目录下的所有子目录
	public List<File> allDirectory() {
		if (list.isEmpty())
			search(file);
		return directoryList;
	}

	// 返回该根目录下的所有文件及子目录
	public List<File> allFilesAndDirectory() {
		if (list.isEmpty())
			search(file);
		return list;
	}

	// 递归搜索当前目录下的所有文件及目录
	private void search(File file) {
		File[] files = file.listFiles();
		if (files == null || files.length == 0)
			return;
		for (File f : files) {
			if (f.getName().matches(regex))
				list.add(f);
			if (f.isFile() && f.getName().matches(regex))
				fileList.add(f);
			else {
				if (f.getName().matches(regex))
					directoryList.add(f);
				search(f);
			}
		}
	}
}
