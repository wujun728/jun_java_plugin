package book.io;
import java.io.File;
import java.util.Date;

/**
 * 获取文件的基本信息
 */
public class GetFileInfos {

	public static void println(String s){
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		//用文件路径新建一个文件对象。路径可以是绝对路径也可以是相对路径
		//传入的参数被当作为文件的抽象路径
		File file = new File("C:/temp/newTemp.txt");
		//获取文件的名字，不包括路径
		println("文件名:\t" + file.getName());
		//将抽象路径名中的文件分隔符用系统默认分隔符替换
		println("文件路径:\t" + file.getPath());
		//获取文件的绝对路径
		println("绝对路径:\t" + file.getAbsolutePath());
		//获取抽象路径名的父抽象路径
		println("父目录:\t" + file.getParent());
		println("文件是否存在:\t" + file.exists());
		println("是否可读:\t" + file.canRead());
		println("是否可写:\t" + file.canWrite());
		println("是否是隐藏文件:\t" + file.isHidden());
		println("是否是普通文件:\t" + file.isFile());
		println("是否是文件目录:\t" + file.isDirectory());
		println("文件路径是否是绝对路径:\t" + file.isAbsolute());
		println("文件路径的URI:\t" + file.toURI());
		println("文件最后修改时间:\t" + new Date(file.lastModified()));
		println("文件大小:\t" + file.length() + " bytes");
	}
}
