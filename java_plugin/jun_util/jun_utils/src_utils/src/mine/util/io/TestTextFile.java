package mine.util.io;

public class TestTextFile {
	public static void main(String[] args) {
		TextFile.write("file/textfile.txt", false,
				"这是一个文本文件的读写测试\nTouch\n刘海房\n男\n");
		TextFile.write("file/textfile.txt", true, "武汉工业学院\n软件工程");
		System.out.println(TextFile.read("file/textfile.txt"));
	}
}
