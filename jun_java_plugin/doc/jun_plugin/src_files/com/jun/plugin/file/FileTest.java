package com.jun.plugin.file;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * File类测试
 * 
 * @author cxy @ www.cxyapi.com
 */
public class FileTest {
	public static void main(String[] args) throws Exception {
		String filePath = "D:" + File.separator + "fileTest" + File.separator;
		String fileName = "test.txt";
		File myFolder = new File(filePath);
		// 文件夹不存在时创建文件夹
		if (!myFolder.exists()) {
			// myFolder.mkdir(); //创建当前目录
			myFolder.mkdirs(); // 创建当前目录结构的所有目录
		}
		// 文件不存在时创建文件
		File myFile = new File(filePath + fileName);
		if (!myFile.exists()) {
			myFile.createNewFile();// 创建一个文件
		}
		System.out.println("------------------------");
		// 文件信息
		System.out.println("文件绝对路径:" + myFile.getAbsolutePath());
		System.out.println("文件名称:" + myFile.getName());
		System.out.println("文件父节点:" + myFile.getParent());
		System.out.println("是文件夹吗？:" + myFile.isDirectory());
		System.out.println("是文件吗？:" + myFile.isFile());
		System.out.println("是隐藏的吗？:" + myFile.isHidden());
		System.out.println("是可读的吗？:" + myFile.canRead());
		System.out.println("是可写的吗？:" + myFile.canWrite());
		System.out.println("是可执行的吗？:" + myFile.canExecute());
		System.out.println("最后一次修改时间？:" + new Date(myFile.lastModified()));
		System.out.println("文件的大小:" + myFile.length());
		System.out.println(myFile.toURI());
		System.out.println("------------------------");
		// 文件列表
		String[] allFileNameInPath = myFolder.list(); // 当前路径下所有文件和文件夹的名称
		System.out.println(Arrays.asList(allFileNameInPath));
		File[] allFileInPath = myFolder.listFiles(); // 当前路径下所有文件和文件夹数组
		File[] roots = File.listRoots(); // 所有磁盘根路径，可以使用上面的方法继续对其进行遍历
		for (File one : roots) {
			System.out.print(one + "  ");
		}
		System.out.println("");
		System.out.println("------------------------");
		// 删除文件 和 文件夹
		myFile.delete();
		if (!myFile.exists()) {
			System.out.println("文件删除成功");
		}
		myFolder.delete();
		if (!myFolder.exists()) {
			System.out.println("文件夹删除成功");
		}
		System.out.println("------------------------");
		// 临时文件相关操作
		File tempFilePath = new File("."); // 在当前项目路径下
		// 以temp开头，txt结尾的临时文件，如果不带最后一个参数那么临时文件将生成到当前操作系统的临时文件目录中
		File tempFile = File.createTempFile("temp", ".txt", tempFilePath);
		System.out.println("临时文件位置：" + tempFile.getAbsolutePath());
		tempFile.deleteOnExit(); // jvm结束的时候删除文件
	}
}