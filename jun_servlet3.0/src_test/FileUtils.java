

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;


public final class FileUtils {

	/**
	 * 相同大小文件查找时用的Map.
	 * 
	 * <pre>
	 * key的格式："文件大小".
	 * </pre>
	 */
	public static Map<String, List<File>> sameSizeFilesMap;
	/**
	 * 重复文件查找时用的Map.
	 * 
	 * <pre>
	 * key的格式："文件大小:MD5".
	 */
	public static Map<String, List<File>> repeatFilesMap;
	/**
	 * 重复文件查找时用的Set.
	 * 
	 * <pre>
	 * 元素String的格式："文件大小:MD5".
	 * 判断repeatFilesMap的value List<File>.size()>1，则加入repeatFilesProp中.
	 * </pre>
	 */
	public static Set<String> repeatFilesProp;
	
	/**
	 * 同名文件查找时用的Map.
	 * 
	 * <pre>
	 * key的格式："文件名".
	 * </pre>
	 */
	public static Map<String, List<File>> sameNameFilesMap;
	/**
	 * 同名文件查找时用的Set.
	 * 
	 * <pre>
	 * 元素String的格式："文件名".
	 * 判断sameNameFilesMap的value List<File>.size()>1，则加入sameNameFilesProp中.
	 * </pre>
	 */
	public static Set<String> sameNameFilesProp;
   

	/**
	 * 迭代获取地址所有文件(夹)，包括file本身.
	 * 
	 * @param file
	 *            文件(夹)
	 * @param paramsMap
	 *            参数
	 * @return <code>List<File></code> 文(夹)件集合
	 */
	public static List<File> getAllFile(File file) {
		List<File> fileList = new ArrayList<File>();
		if (file.exists()) {
			fileList.add(file);
		}
		if (file.isDirectory()) {
			loopDirectory(file, fileList);
		}
		return fileList;
	}

	/**
	 * 迭代目录，将所有子文件或者子文件夹添加到fileList中.
	 * 
	 * @param directory
	 *            目录
	 * @param fileList
	 *            file集合
	 */
	public static void loopDirectory(File directory, List<File> fileList) {
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			fileList.add(file);
			if (file.isDirectory()) {
				loopDirectory(file, fileList);
			}
		}
	}
  

	/**
	 * 比较是否在时间之间.
	 */
	public static boolean ifInSideTime(long side, Long from, Long to) {
		boolean inSide = true;
		if (from != null && to == null) {
			inSide = side >= from;
		} else if (from == null && to != null) {
			inSide = side <= to;
		} else if (from != null && to != null) {
			inSide = side >= from && side <= to;
		}
		return inSide;
	}

	/**
	 * 比较是否在大小之间.
	 */
	public static boolean ifInSideSize(long side, Double from, Double to) {
		boolean inSide = true;
		if (from != null && to == null) {
			inSide = side >= from;
		} else if (from == null && to != null) {
			inSide = side <= to;
		} else if (from != null && to != null) {
			inSide = side >= from && side <= to;
		}
		return inSide;
	}

	/**
	 * 获取文件后缀名，小写.
	 */
	public static String getFileType(String fileName) {
		String fileType = "";
		int lIndex = fileName.lastIndexOf(".");
		if (lIndex > 0) {
			fileType = fileName.substring(lIndex + 1, fileName.length()).toLowerCase();
		}
		return fileType;
	}

	/**
	 * 判断文件(夹)名是否满足匹配.
	 */
	public static boolean ifMatchText(String fileName, String csText, String ncsText, boolean sRegex,
			Pattern csPattern, Pattern ncsPattern) {
		boolean match = true;
		String lFileName = fileName.toLowerCase();
		String lcsText = csText.toLowerCase();
		String lncsText = ncsText.toLowerCase();
		if (sRegex) {
			if (csText.length() != 0) {
				Matcher m = csPattern.matcher(fileName);
				match = m.find();
			}
			if (match && ncsText.length() != 0) {
				Matcher m = ncsPattern.matcher(fileName);
				match = !m.find();
			}
		} else {
			if (csText.length() != 0) {
				match = lFileName.contains(lcsText);
			}
			if (match && ncsText.length() != 0) {
				match = !lFileName.contains(lncsText);
			}
		}
		return match;
	}

}
