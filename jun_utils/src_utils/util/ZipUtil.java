package cn.ipanel.apps.portalBackOffice.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;



/**
 *
 * @create 2009-7-25 下午06:17:33
 * @since
 */
public class ZipUtil {

	public static final String FILE_SEPARATOR = System
	.getProperty("file.separator");
	
	
	
	/**
	 * 将指定的文件解压缩到指定的文件夹，解压后的文件夹目录和给定的压缩文件名相同.
	 * 
	 * @param zipFilePath
	 *            全路径
	 * @param unZipDirectory
	 *            全路径
	 * @return 解压缩文件是否成功.
	 * @throws IOException
	 */
	public static boolean unZipFile(String zipFilePath, String unZipDirectory)
			throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		Enumeration<?> entries = zipFile.getEntries();
		if (zipFile == null) {
			return false;
		}
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();			
			File f = new File(unZipDirectory + FILE_SEPARATOR
					+ zipEntry.getName());
			if (zipEntry.isDirectory()) 
			{
				if (!f.exists() && !f.mkdirs())
					throw new IOException("Couldn't create directory: " + f);
			} else {
				BufferedInputStream is = null;
				BufferedOutputStream os = null;
				try {
					is = new BufferedInputStream(zipFile
							.getInputStream(zipEntry));
					File destDir = f.getParentFile();
					if (!destDir.exists() && !destDir.mkdirs()) {
						throw new IOException("Couldn't create dir " + destDir);
					}
					os = new BufferedOutputStream(new FileOutputStream(f));
					int b = -1;
					while ((b = is.read()) != -1) {
						os.write(b);
					}
				} finally {
					if (is != null)
						is.close();
					if (os != null)
						os.close();
				}
			}
		}
		zipFile.close();
		return true;
	}
	
	
	/**
	 *  压缩一个文件
	 * @param filePath
	 * @param zipPath
	 * @return
	 */
	public static boolean zipFile(String filePath,String zipPath){
	    BufferedReader in=null;
	    org.apache.tools.zip.ZipOutputStream out=null;   
		try{
			File file=new File(filePath);
             in=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"ISO-8859-1"));
             FileOutputStream f=new FileOutputStream(zipPath);
             CheckedOutputStream ch=new CheckedOutputStream(f,new CRC32());
             out=new org.apache.tools.zip.ZipOutputStream(new BufferedOutputStream(ch));
      
            int c;
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(file.getName()));
            while((c=in.read())!=-1)
                out.write(c);              
            }
		 
         catch(Exception e){
             e.printStackTrace();
             return false;
            }
         finally{
        	 try {
				 if(in!=null) in.close();				      
				 if(out!=null)  out.close();
			} catch (IOException e) {				
				e.printStackTrace();				
			}            
         }
         return true;
	}
	
	
	/**
	 * 压缩一个目录
	 * @param dir
	 * @param zipPath
	 * @return
	 */
	public static boolean zipDirectory(String dir,String zipPath ){
		org.apache.tools.zip.ZipOutputStream out=null;   
			try{
				File dirFile=new File(dir);
				if(!dirFile.isDirectory())return false;				
				FileOutputStream fo=new FileOutputStream(zipPath);
	            CheckedOutputStream ch=new CheckedOutputStream(fo,new CRC32());
	            out=new org.apache.tools.zip.ZipOutputStream(new BufferedOutputStream(ch));
	            zip(out,dirFile,"");
	            
			}            
	         catch(Exception e){
	             e.printStackTrace();
	             return false;
	            }
	         finally{
	        	 try {						      
					 if(out!=null)  out.close();				 
				} catch (IOException e) {				
					e.printStackTrace();				
				}            
	         }
	         return true;
	}
	
	public static void zip(org.apache.tools.zip.ZipOutputStream out,File f,String base)throws Exception{
//		System.out.println("Zipping  "+f.getName());
		if (f.isDirectory()) {
			File[] fl=f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base+"/"));
			base=base.length()==0?"":base+"/";
			for (int i=0;i<fl.length ;i++ )	{
				zip(out,fl[i],base+fl[i].getName());
			}
		}
		else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream is=new FileInputStream(f);
			BufferedInputStream in = new BufferedInputStream(is);//修改BUG!二进制输出采用buffered
			int b;
			while ((b=in.read()) != -1)
				out.write(b);
			in.close();
		}

	}
	
		
	
	 public static void main(String[] args){
       //  boolean f=zipFile("e:/red100.txt","e:/red.zip");
         //boolean f=zipDirectory("e:/red","e:/red2.zip");
		 try {
		unZipFile("D:\\portal_rtb.zip", "D:\\wzb");
			//zipDirectory("F:\\list","F:\\list.zip");
		} catch (Exception e) {			
			e.printStackTrace();			
		}
         
	 }
    


}

