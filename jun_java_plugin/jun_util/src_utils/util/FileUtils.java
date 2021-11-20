package cn.ipanel.apps.portalBackOffice.util;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
  
import java.util.ArrayList;  
import java.util.List;  
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
public class FileUtils {  
	

	/**
	 * 获得项目的物理路径(最后一个字符是分隔符).
	 * @return String
	 * @author leigq
	 * @create 2010-11-12 上午10:09:58
	 */
	public static String getAbsPathOfProject() {
		String url = FileUtils.class.getClassLoader().getResource("").toString();
		// Win file:/E:/projects/Eclipse/workspace/SAS-Studio/WEB-INF/classes/
		// Linux file:/home/share/SAS-TOMCAT/webapps/SAS/WEB-INF/classes/
		String reg = "file:(.+)WEB-INF";
		Matcher mat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(url);
		if (mat.find()) {
			String path = mat.group(1);
			path = path.replaceAll("/", "\\" + File.separator);
			return File.separator.equals("\\") ? path.substring(1) : path;
		}
		return url;
	}
  
   public static String  doUpload(byte[] bytes, String fileName) throws Exception {  
       fileName = System.getProperty("java.io.tmpdir") + "/" + fileName;  
       File f = new File(fileName);  
       FileOutputStream fos = new FileOutputStream(f);  
       fos.write(bytes);  
       fos.close();  
       return "success";  
   }  
  
	public static List  getDownloadList(){  
		File dir = new File(System.getProperty("java.io.tmpdir"));  
		String[] children = dir.list();  
		List dirList = new ArrayList();  
		if (children == null) {  
			// Either dir does not exist or is not a directory  
		} else {  
			for (int i=0; i<children.length; i++) {  
			// Get filename of file or directory  
			dirList.add( children[i]);  
			}  
		}  
		return dirList;  
	}  
  
   public static byte[] doDownload(String fileName){  
       FileInputStream fis;  
       byte[] data =null;  
       FileChannel fc;  
  
       try {  
           fis = new FileInputStream(System.getProperty("java.io.tmpdir") + "/" + fileName);  
           fc = fis.getChannel();  
           data = new byte[(int)(fc.size())];  
           ByteBuffer bb = ByteBuffer.wrap(data);  
           fc.read(bb);  
       } catch (FileNotFoundException e) {  
           // TODO  
       } catch (IOException e) {  
           // TODO  
       }  
       return data;  
   }  

}  
