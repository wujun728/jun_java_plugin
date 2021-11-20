package com.jun.plugin.file.fileupload;


import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class FileUtilTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File dir  = new File("e:\\demodir");
//		dir.delete();
		removeDir(dir);
	}

	public static void main11(String[] args) {
		
		File  f = new File("D:"+File.separator+"hello");
		try{
			f.mkdir();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void removeDir(File dir) {
		
		File[] files = dir.listFiles();
		
		for(File file : files){
			
			if(file.isDirectory()){
				removeDir(file);
			}else{
				System.out.println(file+":"+file.delete());
			}
		}
		System.out.println(dir+":"+dir.delete());
	}
	
	
	public static void mainDirList(String[] args) {
		File path = new File(".");
		String[] list;
		if (args.length == 0) {
			list = path.list();
		} else {
			// ���ｫ����Ĳ�����Ϊ��������?
			list = path.list(new DirFilter(args[0]));
		}
		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i]);
		}
	}

	  private static void usage() {
	    System.err.println(
	      "Usage:MakeDirectories path1 ...\n" +
	      "Creates each path\n" +
	      "Usage:MakeDirectories -d path1 ...\n" +
	      "Deletes each path\n" +
	      "Usage:MakeDirectories -r path1 path2\n" +
	      "Renames from path1 to path2");
	    System.exit(1);
	  }
	  
	  //��ʾ�ļ�����Ŀ¼����ϸ��Ϣ
	  private static void fileData(File f) {
	    System.out.println(
	      "Absolute path: " + f.getAbsolutePath() +
	      "\n Can read: " + f.canRead() +
	      "\n Can write: " + f.canWrite() +
	      "\n getName: " + f.getName() +
	      "\n getParent: " + f.getParent() +
	      "\n getPath: " + f.getPath() +
	      "\n length: " + f.length() +
	      "\n lastModified: " + f.lastModified());
	    if(f.isFile())
	      System.out.println("It's a file");
	    else if(f.isDirectory())
	      System.out.println("It's a directory");
	  }
	  
	  public static void main33(String[] args) {
	    if(args.length < 1) usage();
	    if(args[0].equals("-r")) {
	      if(args.length != 3) usage();
	      File
	        old = new File(args[1]),
	        rname = new File(args[2]);
	      old.renameTo(rname);
	      fileData(old);
	      fileData(rname);
	      return; 
	    }
	    int count = 0;
	    boolean del = false;
	    if(args[0].equals("-d")) {
	      count++;
	      del = true;
	    }
	    count--;
	    while(++count < args.length) {
	      File f = new File(args[count]);
	      if(f.exists()) {
	        System.out.println(f + " exists");
	        if(del) {
	          System.out.println("deleting..." + f);
	          f.delete();
	        }
	      }
	      else { // Ŀ¼����������Ҫ����
	        if(!del) {
	          f.mkdirs();
	          System.out.println("created " + f);
	        }
	      }
	      fileData(f);
	    }
	  }
	  
	  
	  
	  
	  
	  
	   
	  
	  
	  
	  
	  
	  
	  
	  
	  

		public static void main44(String[] args) {
			File path = new File(".");
			String[] list;
//			if(args.length==0)
//				list = path.list();
//			else
//				list = path.list(new DirFilter(args[0]));
			System.out.println(path.getAbsolutePath()+" size:"+fileSize(path)+"b");
			list = path.list(new DirFilter("[a-z]*"));
			Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
			for(String dirItem : list)
				System.out.println(dirItem);
		}
		public static long fileSize(File file){
			long size = 0;
			if(file.isDirectory()){
				for(File f : file.listFiles())
					size += fileSize(f);
			}else{
				size = file.length();
			}
			return size;
		}
	}

 

class DirFilter implements FilenameFilter {
	private Pattern pattern;

	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	public boolean accept(File dir, String name) {
		return pattern.matcher(new File(name).getName()).matches();
	}
}
