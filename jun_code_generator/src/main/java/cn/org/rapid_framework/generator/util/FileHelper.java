package cn.org.rapid_framework.generator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.org.rapid_framework.generator.GeneratorProperties;
/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class FileHelper {
	/**
	 * 得到相对路径
	 */
	public static String getRelativePath(File baseDir,File file) {
		if(baseDir.equals(file))
			return "";
		if(baseDir.getParentFile() == null)
			return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()+1);
	}
	
	public static List<File> searchAllNotIgnoreFile(File dir) throws IOException {
		ArrayList arrayList = new ArrayList();
		searchAllNotIgnoreFile(dir,arrayList);
		Collections.sort(arrayList,new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
			}
		});
		return arrayList;
	}
	
	public static InputStream getInputStream(String file) throws FileNotFoundException {
		InputStream inputStream = null;
		if(file.startsWith("classpath:")) {
			inputStream = FileHelper.class.getClassLoader().getResourceAsStream(file.substring("classpath:".length()));
		}else {
			inputStream = new FileInputStream(file);
		}
		return inputStream;
	}

	public static void searchAllNotIgnoreFile(File dir,List<File> collector) throws IOException {
		collector.add(dir);
		if((!dir.isHidden() && dir.isDirectory()) && !isIgnoreFile(dir)) {
			File[] subFiles = dir.listFiles();
			for(int i = 0; i < subFiles.length; i++) {
				searchAllNotIgnoreFile(subFiles[i],collector);
			}
		}
	}
	
	public static File mkdir(String dir,String file) {
		if(dir == null) throw new IllegalArgumentException("dir must be not null");
		File result = new File(dir,file);
		parnetMkdir(result);
		return result;
	}

    public static File parentMkdir(String file) {
        if(file == null) throw new IllegalArgumentException("file must be not null");
        File result = new File(file);
        parnetMkdir(result);
        return result;
    }
	   
	public static void parnetMkdir(File outputFile) {
		if(outputFile.getParentFile() != null) {
			outputFile.getParentFile().mkdirs();
		}
	}
	
	public static File getFileByClassLoader(String resourceName) throws IOException {
		Enumeration<URL> urls = GeneratorProperties.class.getClassLoader().getResources(resourceName);
		while (urls.hasMoreElements()) {
			return new File(urls.nextElement().getFile());
		}
		throw new FileNotFoundException(resourceName);
	}

	
	public static List ignoreList = new ArrayList();
	static{
		ignoreList.add(".svn");
		ignoreList.add("CVS");
		ignoreList.add(".cvsignore");
		ignoreList.add(".copyarea.db"); //ClearCase
		ignoreList.add("SCCS");
		ignoreList.add("vssver.scc");
		ignoreList.add(".DS_Store");
		ignoreList.add(".git");
		ignoreList.add(".gitignore");
	}
	private static boolean isIgnoreFile(File file) {

		for(int i = 0; i < ignoreList.size(); i++) {
			if(file.getName().equals(ignoreList.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static  Set binaryExtentionsList = new HashSet();
	static {
		loadBinaryExtentionsList("binary_filelist.txt",true);
	    loadBinaryExtentionsList("cn/org/rapid_framework/generator/util/binary_filelist.txt",false);
	}
	
	public static void loadBinaryExtentionsList(String resourceName,boolean ignoreException) {
	    try {
	        InputStream input  = GeneratorProperties.class.getClassLoader().getResourceAsStream(resourceName);
			binaryExtentionsList.addAll(IOHelper.readLines(new InputStreamReader(input)));
			input.close();
	    }catch(Exception e) {
	        if(!ignoreException)throw new RuntimeException(e);
	    }
    }
	
	/** 检查文件是否是二进制文件 */
    public static boolean isBinaryFile(File file) {
    	if(file.isDirectory()) return false;
        return isBinaryFile(file.getName());
    }

    public static boolean isBinaryFile(String filename) {
    	if(StringHelper.isBlank(getExtension(filename))) return false;
        return binaryExtentionsList.contains(getExtension(filename).toLowerCase());
    }
	
    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.indexOf(".");
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
    
	   //-----------------------------------------------------------------------
    /**
     * Deletes a directory recursively. 
     *
     * @param directory  directory to delete
     * @throws IOException in case deletion is unsuccessful
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);
        if (!directory.delete()) {
            String message =
                "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Deletes a file, never throwing an exception. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
     * </ul>
     *
     * @param file  file or directory to delete, can be <code>null</code>
     * @return <code>true</code> if the file or directory was deleted, otherwise
     * <code>false</code>
     *
     * @since Commons IO 1.4
     */
    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception e) {
        }

        try {
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent){
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message =
                    "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
}
