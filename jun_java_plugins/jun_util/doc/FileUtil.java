package org.myframework.util;


import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.util.StringUtils;


public class FileUtil {
	private static final Log log = LogFactory.getLog(FileUtil.class);
	public static String FILE ="FILE_" ;
	/**创建目录
	 * @param filePath 
	 */
	public static void createDir(String filePath) {
		if (!new File(filePath).mkdirs())
			log.error("创建目录 fail");
		else
			log.warn("创建目录 success");
	}

	/**根据文件绝对路径来创建文件
	 * @param file
	 * @throws Exception
	 */
	public static void createFile(String file) throws Exception {
		File f = new File(file);
		if (f.exists()) {
			log.debug("文件名：" + f.getAbsolutePath());
			log.debug("文件大小：" + f.length());
		} else {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
	}

	/**根据全类名获得文件路径
	 * @param className 类名
	 * @return
	 */
	public static String getJavaFileName(String className) {
		log.debug("className :" +className);
		String javaFileName = StringUtils.getPackageAsPath(getPackage(className)) +getClassName(className)+ ".java";
		return javaFileName;
	}
	
	/**根据全类名获得JAVA文件所在目录
	 * @param className
	 * @return
	 */
	public static String getPackage(String className) {
		return className.substring(0, className.lastIndexOf("."));
	}
	
	/**根据全类名获得简短类名
	 * @param className
	 * @return
	 */
	public static String getClassName(String className) {
		return className.substring( className.lastIndexOf(".")+1);
	}
	
	/**根据文件对象获取扩展 名
	 * @param f
	 * @return
	 */
	public static String getExtension(File f) {
        return (f != null) ? getExtension(f.getName()) : "";
    }

    /**根据文件名获取扩展名（默认方法）
     * @param filename
     * @return
     */
    public static String getExtension(String filename) {
        return getExtension(filename, "");
    }

    /**根据文件名获取扩展名，无扩展名时返回默认扩展名
     * @param filename
     * @param defExt
     * @return
     */
    public static String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i >-1) && (i < (filename.length() - 1))) {
                return filename.substring(i + 1);
            }
        }
        return defExt;
    }

    /**去除给定文件名的扩展名部分
     * @param filename
     * @return
     */
    public static String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i >-1) && (i < (filename.length()))) {
                return filename.substring(0, i);
            }
        }
        return filename;
    }
    
	/**利用时间戳重命名中文文件
	 * @param fileName
	 * @return
	 */
    public static String renameFile(String prefix,String fileName) {
    	StringBuffer newFileName = new StringBuffer(prefix!=null?prefix:FILE);
		newFileName.append(DateUtil.format(new Date(), "yyyyMMddHHmmssms"))
				.append(".").append(getExtension(fileName));
		return newFileName.toString();
	}
    
    /**利用时间戳重命名中文文件
	 * @param fileName
	 * @return
	 */
    public static String renameFile( String fileName) {
		return renameFile(null,fileName);
	}

	/**利用时间戳重命名中文文件
	 * @param file
	 * @return
	 */
	public static String renameFile(String prefix,File file) {
		StringBuffer newFileName = new StringBuffer(prefix!=null?prefix:FILE);
		newFileName.append(DateUtil.format(new Date(), "yyyyMMddHHmmssms"))
				.append(".").append(getExtension(file));
		return newFileName.toString();
	}


	public static void main(String[] args) throws Exception {
		String className = "ims.tools.web.action.taskAction";
		String fileName = FileUtil.getJavaFileName(className);
		log.debug(DateUtil.getNowTime());
		log.debug(renameFile("abc.txt.xls"));
//		System.out.println(fileName);
//		FileUtils.createFile("D:/"+ fileName);
	}
	
	
}
