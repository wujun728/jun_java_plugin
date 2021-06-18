package demo.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * File代表文件和目录,静态域有：与系统有关的路径分隔符、与系统有关的默认名称分隔符。
 * 主要操作有：创建文件或目录、删除文件、给文件设定属性、返回指定目录下的文件列表、
 *          返回过滤后的文件列表、 检测文件是否存在、是否隐藏、是否是目录还是文件、
 *          返回文件名称和路径
 * 
 * @author Touch
 *
 */
public class FileDemo {
	/*
	 * 查找指定路径下的匹配regex的文件
	 */
	public String[] find(String path, final String regex) {
		File file = new File(path);
        //匿名内部类
		return file.list(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return pattern.matcher(name).matches();
			}
		});
	}

	public static void main(String[] args) {
		String path = null;
		String key = null;
		String regex = null;
		int choice = 1;
		Scanner scanner = new Scanner(System.in);
		System.out.println("please input the file path:");
		path = scanner.next();
		System.out.println("please input key:");
		key = scanner.next();
		System.out.println("choise:\n0:匹配以" + key + "为后缀的文件\n1：匹配包含" + key
				+ "的文件");
		if ((choice = scanner.nextInt()) == 0)
			regex = ".*\\." + key;
		else
			regex = ".*" + key + ".*";
		String[] list;
		list = new FileDemo().find(path, regex);
		System.out.println(Arrays.deepToString(list));
		//返回指定路径下的目录列表
		File[] fileList = new File(path).listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				list = new FileDemo().find(file.getPath(), regex);
				System.out.println(Arrays.deepToString(list));
			}
		}
	}

}
