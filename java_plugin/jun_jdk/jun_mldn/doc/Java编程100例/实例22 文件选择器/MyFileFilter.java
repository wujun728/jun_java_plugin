import java.io.File;
import javax.swing.filechooser.FileFilter;

//文件过滤器

public class MyFileFilter extends FileFilter {

	String ends;  //文件后缀
	String description;  //文件描述文字

	public MyFileFilter(String ends, String description) { //构造函数
		this.ends = ends;  //设置文件后缀
		this.description=description;  //设置文件描述文字
	}

	public boolean accept(File file) {  //重载FileFilter中的accept方法
		if (file.isDirectory())  //如果是目录,则返回true
			return true;
		String fileName = file.getName();  //得到文件名称
	    if (fileName.toUpperCase().endsWith(ends.toUpperCase()))  //把文件后缀与可接受后缀转成大写后比较
	      return true;
        else
          return false;
   }

   public String getDescription() {  //返回文件描述文字
      return description;
   }
}