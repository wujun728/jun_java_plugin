package mine.util.io;

import java.io.File;
import java.util.List;

public class TestSearchFile {
	public static void main(String[] args) {
		System.out.println("-------- 指定目录中所有文件及子目录-------");
		List<File> list = (List<File>) new SearchFile(
				"G:/java/workspace/test/file").files();
		for (File file : list)
			System.out.println(file.getName());
		System.out.println("--------指定目录中以txt为后缀的文件------");
		list = (List<File>) new SearchFile("G:/java/workspace/test/file",
				".*\\.txt").files();
		for (File file : list)
			System.out.println(file.getName());
		System.out.println("--------以该目录为根目录的所有文件及子目录--");
		list = (List<File>) new SearchFile("G:/java/workspace/test")
				.allFilesAndDirectory();
		for (File file : list)
			System.out.println(file.getName());
	}
}
