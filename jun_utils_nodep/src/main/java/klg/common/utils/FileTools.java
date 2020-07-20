package klg.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * 
 * 在JDK7 中只要实现了AutoCloseable 或Closeable 接口的类或接口，<br>
 * 都可以使用try-with-resource 来实现异常处理和资源关闭
 * 
 * @author klguang
 *
 */
public class FileTools {

	public static void saveObject(File file, Object obj) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);

			// 不要吞掉，close exception。jdk1.7中资源close是幂等的，多次关闭结果是相同的
			fos.close();
			oos.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public static Object readObject(File file) throws IOException, ClassNotFoundException {

		if (!file.exists()) {
			return null;
		}
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			fis.close();
			ois.close();

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

		return obj;
	}

	/**
	 * 文件查找
	 * 
	 * @param srcFile
	 * @param fileNameRegex
	 *            正则表达式。
	 * @return
	 */
	public static Collection<File> searchFileName(File rootFile, String fileNameRegex, boolean recursive) {
		if (recursive) {
			return FileUtils.listFiles(rootFile, new RegexFileFilter(fileNameRegex), DirectoryFileFilter.INSTANCE);
		} else {
			return FileUtils.listFiles(rootFile, new RegexFileFilter(fileNameRegex), null);
		}
	}

	public static Collection<File> listAllFiles(File file) {
		return FileUtils.listFiles(file, null, true);
	}

	/**
	 * 取得文件的后缀名
	 * 
	 * @param file
	 * @return 不带"."的后缀名
	 */

	public static String getSuffix(File file) {
		String name = file.getName();
		if (name.contains(".")) {
			return name.substring(name.lastIndexOf(".") + 1, name.length());
		} else
			return "";
	}

	public static String createUUIDName(File file) {
		return UUID.randomUUID().toString() + "." + getSuffix(file);
	}
}
