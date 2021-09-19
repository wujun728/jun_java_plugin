package templet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import entity.Templet;

public class TempletUtil {
	
	/**
	 * 根据目录查找所有模板文件
	 * @param basePath
	 * @return
	 */
	public static List<Templet> getTempletList(String basePath)
	{
		List<Templet> list=null;
		
		//递归显示C盘下所有文件夹及其中文件
		File root = new File(basePath);
		try {
			list=showAllFiles(basePath,root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
	final static List<Templet> showAllFiles( String basePath, File dir) throws Exception{
		List<Templet> list=new ArrayList();
		
		File[] fs = dir.listFiles();
		for(int i=0; i<fs.length; i++){
		   		   
		   Templet templet=new Templet();
		   templet.setAllPath(fs[i].getAbsolutePath());//原目录
		   
		   File file=new File(fs[i].getAbsolutePath());
		   
		   templet.setPath(file.getParent().replace(basePath, "") ); //相对目录
		   templet.setFileName(file.getName());//文件名
		   		   
		   if(fs[i].isDirectory()){
			    try{
			    	list.addAll(  showAllFiles(basePath,fs[i])  );
			    }catch(Exception e)
			    {	
			    	
			    }
		   }else
		   {			   
			   list.add(templet);
		   }
		}
		return list;
	}
}
