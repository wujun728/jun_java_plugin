package com.jun.plugin.util4j.buffer.tool.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.buffer.tool.BufferBuilder;
import com.jun.plugin.util4j.file.FileUtil;

import io.netty.util.CharsetUtil;

public class BufferBuilderDemo{

	protected Logger log=LoggerFactory.getLogger(getClass());

	public static final String writeMethodName="writeTo";
	public static final String readMethodName="readFrom";
	
	/**
	 * (@Override)?([\s]+|[\r\n\t])(public)([\s]+|[\r\n|\r|\t])(void)([\s]+|[\r\n|\r|\t])(writeToSql)([\s]*|[\r\n|\r|\t])(\()([\s]*|[\r\n|\r|\t])(ByteBuffer)([\s]+|[\r\n|\r|\t])(buffer)([\s]*|[\r\n|\r|\t])(\))([\s]*|[\r\n|\r|\t])(\{)([\s\S]*)(\})
	 */
	public static final String MATCH_WRITE="(@Override)?([\\s]+|[\\r\\n\\t])(public)([\\s]+|[\\r\\n|\\r|\\t])(void)([\\s]+|[\\r\\n|\\r|\\t])("+writeMethodName+")([\\s]*|[\\r\\n|\\r|\\t])(\\()([\\s]*|[\\r\\n|\\r|\\t])(ByteBuffer)([\\s]+|[\\r\\n|\\r|\\t])(buffer)([\\s]*|[\\r\\n|\\r|\\t])(\\))([\\s]*|[\\r\n|\\r|\\t])(\\{)([\\s\\S]*)(\\})";
	public static final String MATCH_READ="(@Override)?([\\s]+|[\\r\\n\\t])(public)([\\s]+|[\\r\\n|\\r|\\t])(void)([\\s]+|[\\r\\n|\\r|\\t])("+readMethodName+")([\\s]*|[\\r\\n|\\r|\\t])(\\()([\\s]*|[\\r\\n|\\r|\\t])(ByteBuffer)([\\s]+|[\\r\\n|\\r|\\t])(buffer)([\\s]*|[\\r\\n|\\r|\\t])(\\))([\\s]*|[\\r\n|\\r|\\t])(\\{)([\\s\\S]*)(\\})";

	public static String BEGIN_FLAG = "//auto sql write begin";
	public static String END_FLAG = "//auto sql write end";
	
	public void build(String soruceRootDir,String pkg)throws Exception
	{
		BufferBuilder bb=new BufferBuilder("net.jueb.util4j.buffer.ArrayBytesBuff", "writeTo", "readFrom");
		//属性过滤器
		bb.addFieldSkipFilter((field)->{
			String name = field.getName();
			return name.contains("$SWITCH_TABLE$");
		});
		//其它类型
		bb.addTypeHandler((ctx)->{
			Class<?> type=ctx.varType();
			if(Date.class.isAssignableFrom(type))
			{
				String ClassName=type.getSimpleName();
				ctx.write().append("\t").append(ctx.varBuffer()+".writeLong("+ctx.varName()+".getTime());").append("\n");
				ctx.read().append("\t").append(ctx.varName() +"=new "+ClassName+"();").append("\n");
				ctx.read().append("\t").append(ctx.varName() + ".setTime("+ctx.varBuffer()+".readLong());").append("\n");
				return true;
			}
			return false;
		});
		
		List<Class<?>> fileList = getClassInfo(soruceRootDir, pkg);
		for(Class<?> clazz:fileList)
		{
			if(!Dto.class.isAssignableFrom(clazz))
			{
				continue;
			}
			StringBuilder writeSb=new StringBuilder();
			StringBuilder readSb=new StringBuilder();
			bb.build(clazz,writeSb,readSb);
			writeSb.append("\n");
			writeSb.append(readSb.toString());
			File javaSourceFile=findJavaSourceFile(soruceRootDir, clazz);
			String javaSource=fillCode(javaSourceFile, writeSb);
			FileUtils.writeByteArrayToFile(javaSourceFile,javaSource.getBytes(CharsetUtil.UTF_8));
		}
	}
	
	/**
	 * 查找类源码文件
	 * @param soruceRootDir 源码目录
	 * @param clazz 类
	 * @return
	 */
	public File findJavaSourceFile(String soruceRootDir,Class<?> clazz)
	{
		String url = StringUtils.replace(clazz.getPackage().toString().split(" ")[1], ".",File.separator);
		String sourceFilename = soruceRootDir+url +File.separator + clazz.getSimpleName() + ".java";
		File javaSourceFile=new File(sourceFilename);
		return javaSourceFile;
	}
	
	/**
	 * 获取源码目录下指定包下的类
	 * @param root
	 * @param pkg
	 * @return
	 * @throws Exception
	 */
	public List<Class<?>> getClassInfo(String root,String pkg) throws Exception
	{
		List<Class<?>> list=new ArrayList<Class<?>>();
		String suffix=".java";
		File rootDir=new File(root);
		Set<File> files=FileUtil.findFileByDirAndSub(rootDir,suffix);
		URLClassLoader loader=new URLClassLoader(new URL[]{rootDir.toURI().toURL()});
		try {
			// 获取路径长度
			int clazzPathLen = rootDir.getAbsolutePath().length() + 1;
			for(File file:files)
			{
				String className = file.getAbsolutePath();
				className = className.substring(clazzPathLen, className.length() - suffix.length());
				className = className.replace(File.separatorChar, '.');
				try {
					Class<?> clazz=loader.loadClass(className);
					String pkgName=clazz.getPackage().getName();
					if(pkgName.equals(pkg))
					{
						list.add(clazz);
					}
				} catch (Exception e) {
				}
			}
		} finally {
			loader.close();
		}
		return list;
	}
	
	/**
	 * 填充代码
	 * @param javaFile
	 * @param bufferCode
	 * @return
	 * @throws IOException
	 */
	public String fillCode(File javaFile,StringBuilder bufferCode) throws IOException
	{
		String javaSource=FileUtils.readFileToString(javaFile, CharsetUtil.UTF_8);
		int start=javaSource.indexOf(BEGIN_FLAG);
		int end=javaSource.indexOf(END_FLAG);
		if(start>0&& end>0)
		{
			String head=javaSource.substring(0, start+BEGIN_FLAG.length());
			String til=javaSource.substring(end, javaSource.length());
			javaSource=head+"\n"+bufferCode.toString()+"\n"+til;
		}
		return javaSource;
//		javaSource=javaSource.replaceAll(MATCH_READ,"");
//		System.out.println(javaSource);
//		javaSource=javaSource.replaceAll(MATCH_WRITE,"");
//		System.out.println(javaSource);
//		char[] array=javaSource.toCharArray();
//		int index=-1;
//		for(int i=array.length-1;i>=0;i--)
//		{
//			if(array[i]=='}')
//			{
//				index=i;break;
//			}
//		}
//		if(index>0)
//		{
//			String s1=javaSource.substring(0, index);
//			System.out.println(s1);
//			javaSource=s1+"\n"+bufferCode.toString()+"\n}";
//		}
//		return javaSource;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		String path=System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator;
		String pkg="net.jueb.util4j.buffer.tool.demo";
		BufferBuilderDemo buildUtils = new BufferBuilderDemo();
		buildUtils.build(path,pkg);
	}
}
