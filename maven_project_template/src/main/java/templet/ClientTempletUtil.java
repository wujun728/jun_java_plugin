package templet;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Column;
import entity.Table;

/**
 * 模板处理类 用于替换内容
 * @author Administrator
 *
 */
public class ClientTempletUtil {
	
	/**
	 * 根据目录查找所有子模板
	 * @param basePath
	 * @return
	 */
	public static Map<String ,String > getTempletList(String basePath)
	{
		Map<String ,String> map=new HashMap();
		
		//递归显示C盘下所有文件夹及其中文件
		File root = new File(basePath);
		try {
			map=showAllFiles(basePath,root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	
	 static Map<String ,String > showAllFiles( String basePath, File dir) throws Exception
	 {
		
		Map<String ,String> map=new HashMap();
		
		File[] fs = dir.listFiles();
		for(int i=0; i<fs.length; i++){
		   File file=new File(fs[i].getAbsolutePath());
		   //将读取的子模板的内容放在map集合中
		  
		   if(fs[i].isDirectory()){
			    try{
			    	map.putAll(  showAllFiles(basePath,fs[i])  );
			    }catch(Exception e)
			    {	
			    	
			    }
		   }else
		   {			   
			   map.put(file.getName(), FileUtil.getContent(fs[i].getAbsolutePath()) )  ; 
		   }
		}
		return map;
	 }
	
	
	 /**
	  * 替换列级模板
	  * @param oldContent 原文本
	  * @param map 子替换符号列表
	  * @return
	  */
	 public static String createContent(String oldContent,  Map<String,String> map ,Table table)
	 {
		//循环所有子替换符  
		for(String ks :map.keySet() )
		{
			String thf="<"+ks+">";//替换符号
			
			if(oldContent.indexOf(thf)>=0)
			{
				String foreachContent=map.get(ks);//循环体
				
				StringBuilder createContent= new StringBuilder();
				
				for( Column column : table.getColumns() )
				{
					
					boolean b=true;//控制开关
					//只循环主键
					if(ks.indexOf(".key")>=0)
					{
						if(!column.getColumnKey().equals("PRI"))
						{
							b=false;//不是主键
						}						
					}
					//只循环非主键
					if(ks.indexOf(".nokey")>=0)
					{
						if(column.getColumnKey().equals("PRI"))
						{
							b=false;//不是主键
						}						
					}
					
					//只循环String 类型
					if(ks.indexOf(".String")>=0)
					{
						if(!column.getColumnType().equals("String"))
						{
							b=false;//不是String
						}						
					}

					//只循环String 类型
					if(ks.indexOf(".Integer")>=0)
					{
						if(!column.getColumnType().equals("Integer"))
						{
							b=false;//不是String
						}
					}

					//只循环Long类型
					if(ks.indexOf(".Long")>=0)
					{
						if(!column.getColumnType().equals("Long"))
						{
							b=false;//不是String
						}
					}

					//只循环Date 类型
					if(ks.indexOf(".Date")>=0)
					{
						if(!column.getColumnType().equals("java.util.Date"))
						{
							b=false;//不是String
						}
					}
					//根据模板生成新内容					
					if(b)
					{
						System.out.println("替换符号内容："+foreachContent);
						String newContent= foreachContent.replace("[column]", column.getColumnName());
						newContent=newContent.replace("[Column]", Utils.getClassName(column.getColumnName()) );
						
						newContent=newContent.replace("[column2]", column.getColumnName2() );
						newContent=newContent.replace("[Column2]", Utils.getClassName(column.getColumnName2()) );
						
						newContent=newContent.replace("[type]", column.getColumnType());//java类型
						newContent=newContent.replace("[dbtype]", column.getColumnDbType());//数据库类型
						if(column.getColumnComment()==null || column.getColumnComment().equals("")){
							column.setColumnComment(column.getColumnName());//设置为名称
						}
						newContent= newContent.replace("[columnComment]", column.getColumnComment());//备注
						createContent.append(newContent);
						System.out.println("替换后内容："+newContent);
					}
				}
				oldContent=oldContent.replace(thf, createContent.toString());//替换主体内容
			}
		}
		
		oldContent= oldContent.replace("[table]", table.getName());
		oldContent= oldContent.replace("[Table]", Utils.getClassName(table.getName()) );	
		oldContent= oldContent.replace("[table2]", table.getName2());
		oldContent= oldContent.replace("[Table2]", Utils.getClassName(table.getName2()) );

		if(table.getComment()==null || table.getComment().equals("")){
			table.setComment(table.getName2());
		}
		oldContent= oldContent.replace("[comment]", table.getComment());//备注


		if(table.getKey()!=null){
			oldContent= oldContent.replace("[key]", table.getKey());// 主键
		}
		if(table.getKey2()!=null){
			oldContent= oldContent.replace("[key2]", table.getKey2());//主键驼峰
		}
		if(table.getKeyType()!=null){
			oldContent= oldContent.replace("[keyType]", table.getKeyType());//主键类型
		}
		 if(table.getKey2Upper()!=null){
			 oldContent= oldContent.replace("[Key2]", table.getKey2Upper());//大写主键
		 }
		return oldContent;
	 }
		
		
	 /**
	  * 替换表级模板
	  * @param oldContent 原文本
	  * @param map 子替换符号列表
	  * @return 
	  */
	 public static String createContentForTable(String oldContent,  Map<String,String> map ,List<Table> tables )
	 {
		//循环所有子替换符  
		for(String ks :map.keySet())
		{
			String thf="<"+ks+">";//替换符号			
			if(oldContent.indexOf(thf)>=0)
			{
				String foreachContent=map.get(ks);//循环体				
				StringBuilder createContent= new StringBuilder();				
				for( Table table : tables )
				{					
					boolean b=true;//控制开关										
					//根据模板生成新内容					
					if(b)
					{
						String newContent= foreachContent.replace("[table]", table.getName());
						newContent=newContent.replace("[Table]", Utils.getClassName(table.getName()) );
						
						newContent=newContent.replace("[table2]", table.getName2() );
						newContent=newContent.replace("[Table2]", Utils.getClassName(table.getName2()));

						if(table.getKey()!=null){
							oldContent= oldContent.replace("[key]", table.getKey());//备注
						}
						if(table.getKey2()!=null){
							oldContent= oldContent.replace("[key2]", table.getKey2());//备注
						}
						if(table.getKeyType()!=null){
							oldContent= oldContent.replace("[keyType]", table.getKeyType());//备注
						}
						if(table.getKey2Upper()!=null){
							oldContent= oldContent.replace("[Key2]", table.getKey2Upper());//大写主键
						}
						if(table.getComment()==null || table.getComment().equals("")){
							table.setComment(table.getName2());
						}
						newContent= newContent.replace("[comment]", table.getComment());//备注
						createContent.append(newContent);
					}
				}				
				oldContent=oldContent.replace(thf, createContent.toString());//替换主体内容
			}
		}
		
		 
		return oldContent;
	 }

	 
	 /**
	  * 替换全局替换符
	  * @param oldContent
	  * @param map
	  * @return
	  */
	 public static String createContent(String oldContent,Map<String,String> map)
	 {
		//循环所有子替换符  
		for(String ks :map.keySet())
		{
			oldContent= oldContent.replace("["+  ks + "]", map.get(ks));
		}
		
		return oldContent;
	 }
	
}
