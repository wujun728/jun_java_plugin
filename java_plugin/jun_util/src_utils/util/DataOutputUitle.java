package cn.ipanel.apps.portalBackOffice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.ipanel.apps.portalBackOffice.dao.IResourceDataDao;
import cn.ipanel.apps.portalBackOffice.dao.IResourcesDao;
import cn.ipanel.apps.portalBackOffice.define.Defines;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalApp;
import cn.ipanel.apps.portalBackOffice.jibx.pojo.PortalStruct;
import cn.ipanel.apps.portalBackOffice.parser.OutputStringParser;
import cn.ipanel.apps.portalBackOffice.parser.PortalSubstanceParseXml;

public class DataOutputUitle {	
    public static boolean htmlparser(String path,PortalApp pa,PortalStruct ps,
    		IResourcesDao resourcedao,IResourceDataDao datadao){
    	String html = path+"index-final.jsp";
    	File file = new File(html);
    	if(!file.exists()){
    		html = path+"index-final.htm";
    	}
    	Map<String,List<String>> sbumenumap = PortalSubstanceParseXml.getSubMenuResourceID(pa,ps);
        List<String> mainmenu = PortalSubstanceParseXml.getMainMenuResourceId(pa, ps);
        List<List<String>> hblist = PortalSubstanceParseXml.getHBResourceIdList(pa, ps);
        String city = PropertyManager.getConfigProperty("city");
		String menu = "";
		if(city.equals("beijing"))
		   menu = OutputStringParser.ParserMenu(resourcedao, datadao, path, mainmenu, sbumenumap);
		else if(city.equals("chongqing"))
		   menu = OutputStringParser.ParserCQMenu(resourcedao, datadao, path, mainmenu, sbumenumap);
		else{
			System.out.println("城市配置错误,无法进行数据处理........");
			return false;
		}
		
		if(!paserFile(html, "<!--mainMenu-start-->", 
				"<!--mainMenu-end-->", menu))return false;
		
		String hb = OutputStringParser.parseHB(resourcedao, datadao, path, hblist);
		if(!paserFile(html, "<!--movieList-start-->", 
				"<!--movieList-end-->", hb))return false;
		
		List<List<String>> mainmarquelist = PortalSubstanceParseXml.getMarqueeResourceIdList(pa, ps, "mainmenu");
		String mianmar = OutputStringParser.parerMarquee(resourcedao, datadao, mainmarquelist, "mainmenu");
		if(!paserFile(html, "<!--mainMenuMarqueeList-start-->", 
				"<!--mainMenuMarqueeList-end-->", mianmar))return false;
		
		List<List<String>> submarquelist = PortalSubstanceParseXml.getMarqueeResourceIdList(pa, ps, "submenu");
		String submar = OutputStringParser.parerMarquee(resourcedao, datadao, submarquelist, "submenu");
		if(!paserFile(html, "<!--subMenuMarqueeList-start-->", 
				"<!--subMenuMarqueeList-end-->", submar))return false;
		
		List<List<String>> hbmarquelist = PortalSubstanceParseXml.getMarqueeResourceIdList(pa, ps, "hb");
		String hbmar = OutputStringParser.parerMarquee(resourcedao, datadao, hbmarquelist, "hb");
		if(!paserFile(html, "<!--movieMarqueeList-start-->", 
				"<!--movieMarqueeList-end-->", hbmar))return false;
		return true;
    }
    public static boolean paserFile(String path,String start,String end,String contens){
    	StringBuffer out = new StringBuffer();  	
    	File f = new File(path);
    	int length = new Long(f.length()).intValue();
    	byte[] b = new byte[length];
    	FileInputStream fis =null;
    	try {
			fis = new FileInputStream(f);
		    fis.read(b, 0, length);	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		String str = new String(b);
        String[] array1 = str.split(start);
        if(array1==null||array1.length!=2){
        	System.out.println("找不到开始标签"+start+"或标记错误...");
        	return false;
        }
        String[] array2 = array1[1].split(end);
        if(array2==null||array2.length!=2){
        	System.out.println("找不到结束标签"+end+"或标记错误...");
        	return false;
        }
        out.append(array1[0]+"\n");
        out.append(start+"\n");
        out.append(contens+"\n");
        out.append(end+"\n");
        out.append(array2[1]);
        byte[] bi =  out.toString().getBytes();
        outputFile(f,bi);
    	return true;
    }
    public static void outputFile(File path,byte[] b){
    	File parent = path.getParentFile();
    	if(!parent.exists())
				parent.mkdir();
    	FileOutputStream  fos = null;
    	try {
		   fos = new FileOutputStream(path);			
			fos.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos!=null)
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    }
    public static boolean delDir(File f){
    	if(f.isDirectory()&&f.listFiles()!=null){
    		File[] files = f.listFiles();
    		for(int i=0;i<files.length;i++){
    			File file = files[i];
    			if(file.isDirectory())delDir(file);
    			else file.delete();
    		}
    	}else if(f.isFile()||(f.isDirectory()&&f.listFiles()==null)){
    		f.delete();
    	}else return false;
    	f.delete();
    	return true;
    }
	public static String getbaseJosn(String key,String[] value){
		StringBuffer sb = new StringBuffer();
		sb.append(key+":[");
		for(int i=0;i<value.length;i++){
			sb.append("\""+value[i]+"\"");
			if(i<value.length-1)
				sb.append(",");
			if(i==value.length-1)
				sb.append("]");
		}
		return sb.toString();
	}
	public static String getbaseJosn(String key,String value){
		StringBuffer sb = new StringBuffer();
		sb.append(key+":\"");
		sb.append(value+"\"");
		return sb.toString();	
	}
	public static String getRootJosn(String key,String[] value){
		StringBuffer sb = new StringBuffer();
		sb.append(key+":[");
		for(int i=0;i<value.length;i++){
			sb.append(value[i]);
			if(i<value.length-1)
				sb.append(",\n");
			if(i==value.length-1)
				sb.append("]");
		}
		return sb.toString();
	}
	public static String getAppendJosn(String[] basejosn){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(int i=0;i<basejosn.length;i++){
			sb.append(basejosn[i]);
			if(i<basejosn.length-1)
				sb.append(",");
			if(i==basejosn.length-1)
				sb.append("}");
		}
		return sb.toString();
	}
	public static void main(String[] asd){
		/*String  key1 = "name";
		String[] value1 = {"123","222","333"};
		String  key2 = "info";
		String[] value2 = {"123","222","333"};
		String  key3 = "all";
		String[] one = new String[2];
		String[] two = new String[2];
		two[0] = DataOutputUitle.getbaseJosn(key1, value1);
		//System.out.print(two[0]); 
		two[1] = DataOutputUitle.getbaseJosn(key2, value2);
		one[0] = DataOutputUitle.getAppendJosn(two);
		System.out.println("---"+one[0]);
		one[1] = DataOutputUitle.getAppendJosn(two);*/
		//String contents = "movieList[0] = [{ name:\"\", pic:\"8_1.jpg\", url:\"http://172.16.252.158:8080/PortalMangerService/index.htm?smid=\"+CAID },{ name:\"\", pic:\"8_2.jpg\", url:\"ui://systemSetting.htm?3\" }];";
		//String contents = "adfasdadsf";
		String path = "D:\\wzb\\8_2.jpg";
 		String start = "<!---movieList-start-->";
 		String end = "<!---movieList-end-->";
 	    String contens="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";
 		File f= new File(path);
 	//	DataOutputUitle.outputFile(f, contens);		 
		   System.out.println("完成");
		
	}
}
