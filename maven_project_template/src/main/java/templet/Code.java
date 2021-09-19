package templet;

import java.util.List;
import java.util.Map;

import entity.Table;
import entity.Templet;

/**
 * 代码相关操作类
 * @author Administrator
 *
 */
public class Code {
	
	/**
	 * 代码生成
	 * @param pathMap 路径参数封装
	 * @param publicMap  全局替换符
	 */
	public static void create(Map<String,String> pathMap,Map<String,String> publicMap)
	{
		//变量定义
		String projectTempletPath=pathMap.get("projectTempletPath");//框架模板所在目录
		String tablleTempletPath=pathMap.get("tablleTempletPath");//表级模板所在目录
		String columnTempletPath=pathMap.get("columnTempletPath");//列级模板所在目录		
		String xmlPath=pathMap.get("xmlPath");//数据库信息文件
		String codePath=pathMap.get("codePath");//代码输出目录
		
		//追加到全局替换符******************		
		Map<String, String> propertyMap = xml.DatabaseXml.readProperty(xmlPath);
		publicMap.putAll(propertyMap);
		//******************************
		
		List<Table> tableList=xml.DatabaseXml.readDatabaseXml(xmlPath);//得到表的列表
		
		Map<String ,String > tableTempletMap= ClientTempletUtil.getTempletList(tablleTempletPath);//表级模板MAP
		Map<String ,String > columnTempletMap= ClientTempletUtil.getTempletList(columnTempletPath);//列级模板MAP		
		List<Templet> list=TempletUtil.getTempletList(projectTempletPath);
		//循环所有模板
		for(Templet t:list  )
		{
			System.out.println("处理模板："+ t.getPath()+"   --- "+t.getFileName());
			
			//如果是模板类文件			
			if( FileUtil.isTemplatFile( t.getAllPath()) ){
			
				//读取模板文件内容
				String content=FileUtil.getContent(t.getAllPath());
				
				//替换表级模板部分
				content= ClientTempletUtil.createContentForTable(content, tableTempletMap, tableList);
				
				//如果文件名包含表替换符号则循环输出
				if(t.getFileName().indexOf("[table]")>=0  || t.getFileName().indexOf("[Table]")>=0  || 
						t.getFileName().indexOf("[table2]")>=0  || t.getFileName().indexOf("[Table2]")>=0){
					
					for(Table table:tableList)
					{						
						//输出的文件名
						String outFile=t.getFileName().replace("[table]", table.getName());
						outFile=outFile.replace("[Table]", Utils.getClassName(table.getName()) );	
						
						outFile=outFile.replace("[Table2]", Utils.getClassName(table.getName2()) );	
						outFile=outFile.replace("[table2]", table.getName2() );	
						
						//得到模板内容
						String outContent=content;	
						
						//替换列级模板部分
						outContent= ClientTempletUtil.createContent(outContent, columnTempletMap, table);
						
						//全局替换符嵌套替换符处理********
						
						System.out.println("全局替换符嵌套替换符处理********"+table.getName());
						/*for(String k:publicMap.keySet() ){
							String value = publicMap.get(k).replace("[table]", table.getName());
							publicMap.put(k,value );							
							System.out.println("key:"+k+"  value:"+value);
						}		*/			
						
						//全局替换
						outContent= ClientTempletUtil.createContent(outContent, publicMap);
							
						outContent=outContent.replace("[table]", table.getName());
						
						//输出的路径(经过全局替换)
						String outPath= ClientTempletUtil.createContent(codePath+"/"+ t.getPath()+"/"+outFile, publicMap)  ;						
						//在新的文件中去掉模板文件标记***********(TFILE)
						outPath=outPath.replace("(TFILE)", "");					
						//写入文件						
						FileUtil.setContent(outPath, outContent);
					}								
				}else //不用循环的文件
				{
					String outPath= ClientTempletUtil.createContent(codePath+"/"+ t.getPath()+"/"+t.getFileName(), publicMap); 	
					//在新的文件中去掉模板文件标记***********(TFILE)
					outPath=outPath.replace("(TFILE)", "");
					
					content= ClientTempletUtil.createContent(content, publicMap);
					FileUtil.setContent(outPath, content);//写入文件
				}				
			}else  //非模板文件直接拷贝
			{
				String newPath= ClientTempletUtil.createContent( codePath+"/"+ t.getPath()+"/"+t.getFileName(), publicMap); 
				
				FileUtil.copyFile(t.getAllPath(), newPath);
			}			
		}
		System.out.println("代码成功生成!");
	}

}
