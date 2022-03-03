package com.jun.plugin.utils;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.erp.jee.pageModel.SessionInfo;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class SQLUtil {

	/**
	 * SQL文件路径获取参数
	 */
	private static final String KEY_01 = "service";
	private static final String KEY_02 = "impl";
	private static final String KEY_03 = "Impl.";
	private static final String PACKAGE_SQL = "sql";
	private static final String SUFFIX_SQL = ".sql";
	private static final String SUFFIX_D = ".";
	private static final String SUFFIX_X = "/";

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");
	private static Cache dictCache;
	static {
		if (dictCache == null) {
			dictCache = CacheManager.getInstance().getCache("dictCache");
		}
	}

	/**
	 * 从文件中读取文本内容, 读取时使用平台默认编码解码文件中的字节序列
	 * 
	 * @param file
	 *            目标文件
	 * @return
	 * @throws IOException
	 */
	private static String loadStringFromFile(File file) throws IOException {
		return loadStringFromFile(file, "UTF-8");
	}

	/**
	 * 从文件中读取文本内容
	 * 
	 * @param file
	 *            目标文件
	 * @param encoding
	 *            目标文件的文本编码格式
	 * @return
	 * @throws IOException
	 */
	private static String loadStringFromFile(File file, String encoding) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			StringBuilder builder = new StringBuilder();
			char[] chars = new char[4096];

			int length = 0;

			while (0 < (length = reader.read(chars))) {

				builder.append(chars, 0, length);

			}

			return builder.toString();

		} finally {

			try {

				if (reader != null)
					reader.close();

			} catch (IOException e) {

				throw new RuntimeException(e);

			}

		}

	}

	/**
	 * 读取SQL内容
	 * 
	 * @param args
	 * @throws IOException
	 */

	private static String getFlieTxt(String fileUrl) {
		System.out.println("---------------------------------------sql 路径 :" + fileUrl);
		String sql = null;
		try {
			sql = loadStringFromFile(new File(fileUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sql;
	}

	public static String getMethodSql(String methodUrl) {

		// [1].开发模式：dev SQL文件每次都加载
		if (Constants.MODE_DEVELOP.equals(bundle.getObject("sqlReadMode"))) {
			return getMethodSqlLogic(methodUrl);
		}
		// [2].发布模式：pub SQL文件只加载一次
		else if (Constants.MODE_PUBLISH.equals(bundle.getObject("sqlReadMode"))) {
			Element element = dictCache.get(methodUrl);
			if (element == null) {
				element = new Element(methodUrl, (Serializable) getMethodSqlLogic(methodUrl));
				// 永久缓存
				dictCache.put(element);
			}
			return element.getValue().toString();
		} else {
			return "";
		}
	}

	/**
	 * 新获取SQL路径方法,并读取文件获取SQL内容
	 * 
	 * @param methodUrl
	 * @return
	 */
	public static String getMethodSqlLogic(String methodUrl) {
		String head = methodUrl.substring(0, methodUrl.indexOf(KEY_01));
		String end = methodUrl.substring(methodUrl.indexOf(KEY_02) + KEY_02.length()).replace(KEY_03, "_");
		String sqlurl = head + PACKAGE_SQL + end;
		sqlurl = sqlurl.replace(SUFFIX_D, SUFFIX_X);
		sqlurl = sqlurl + SUFFIX_SQL;

		String projectPath = getAppPath(SQLUtil.class);
		sqlurl = projectPath + SUFFIX_X + sqlurl;
		System.out.println(sqlurl);
		return getFlieTxt(sqlurl);
	}

	/**
	 * 旧获取SQL路径方法
	 * 
	 * @param methodUrl
	 * @return
	 */
	@Deprecated
	public static String getMethodSqlLogicOld(String methodUrl) {
		// 从Spring中获取Request
		// HttpServletRequest request = ((ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes()).getRequest();
		// String projectPath =
		// ServletActionContext.getServletContext().getRealPath("/");
		// System.out.println(projectPath);
		// String projectPath =
		// request.getSession().getServletContext().getRealPath("/");
		methodUrl = methodUrl.substring(17).replace("Impl", "").replace(".", "/");
		String[] str = methodUrl.split("/");
		StringBuffer sb = new StringBuffer();
		int num = 2;
		int length = str.length;
		for (String s : str) {

			if (num < length) {
				sb.append(s);
				sb.append("/");
			} else if (num == length) {
				sb.append(s);
				sb.append("_");
			} else {
				sb.append(s);
			}
			num = num + 1;
		}
		String projectPath = getAppPath(SQLUtil.class);
		String fileUrl = projectPath + "/sun/sql/" + sb.toString() + ".sql";
		return getFlieTxt(fileUrl);
	}

	@SuppressWarnings("unchecked")
	public static String getAppPath(Class cls) {
		// 检查用户传入的参数是否为空
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = "";
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new java.lang.IllegalArgumentException("不要传送系统类！");
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		/*------------------------------------------------------------ 
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
		  中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
		  的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
		  中文及空格路径 
		-------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("realPath----->" + realPath);
		return realPath;
	}

	/**
	 * 返回当前执行的方法路径
	 * 
	 * @return
	 */
	public static String getMethodUrl() {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append(stacks[1].getClassName()).append(".").append(stacks[1].getMethodName());
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getAppPath(SQLUtil.class));
	}
	
	/*****************************************************************************************************************/
	/*****************************************************************************************************************/
	/*****************************************************************************************************************/

	
	private static final String SUFFIX_COMMA = ",";
	private static final String SUFFIX_KG = " ";
	/**模糊查询符号*/
	private static final String SUFFIX_ASTERISK = "*";
	private static final String SUFFIX_ASTERISK_VAGUE = "%%";
	/**不等于查询符号*/
	private static final String SUFFIX_NOT_EQUAL = "!";
	private static final String SUFFIX_NOT_EQUAL_NULL = "!NULL";
	
	static HttpServletRequest request;
	
	
	private static final Logger logger = Logger.getLogger(SQLUtil.class);
	
	/**
  	 * 给数据库实体赋值
  	 * 创建时间
  	 * 创建人
  	 * 创建人名称
  	 * @param bean
  	 */
	public static void setInsertMessage(Object bean){
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		try {
			//逻辑删除
			BeanUtil.setProperty(bean, "delflag",0);
			BeanUtil.setProperty(bean, "createDt",new Date());
			BeanUtil.setProperty(bean, "crtuser", sessionInfo.getUserId());
			BeanUtil.setProperty(bean, "crtuserName", sessionInfo.getRealName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
;		}
	}
	
	/**
  	 * 给数据库实体赋值
  	 * 创建时间
  	 * 创建人
  	 * 创建人名称
  	 * @param bean
  	 */
	public static void setUpdateMessage(Object bean){
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		try {
			BeanUtil.setProperty(bean, "modifyDt",new Date());
			BeanUtil.setProperty(bean, "modifier", sessionInfo.getUserId());
			BeanUtil.setProperty(bean, "modifierName", sessionInfo.getRealName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		} 
	}
	
	/**
  	 * 给数据库实体赋值
  	 * 创建时间
  	 * 创建人
  	 * 创建人名称
  	 * @param bean
  	 */
	public static void setDelMessage(Object bean){
		try {
			BeanUtil.setProperty(bean, "delflag",1);
			BeanUtil.setProperty(bean, "delDt", new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}
	
	
	/**
  	 * 自动生成查询条件HQL
  	 * 模糊查询
  	 * 【只对Integer类型和String类型的字段自动生成查询条件】
  	 * @param hql
  	 * @param values
  	 * @param searchObj
  	 * @throws Exception
  	 */
  	public static void createSearchParamsHql(StringBuffer hqlbf,List<Object> values,Object searchObj){
  		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(searchObj);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            String type = origDescriptors[i].getPropertyType().toString();
            if("class java.util.Date".equals(type)){
            	 continue; // No point in trying to set an object's class[不对时间类型处理，需要手工写]
            }
            if ("class".equals(name)||"ids".equals(name)||"page".equals(name)
            		||"rows".equals(name)||"sort".equals(name)||"order".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            try {
            if (PropertyUtils.isReadable(searchObj, name)) {
               if("class java.lang.String".equals(type)){
            	   Object value = PropertyUtils.getSimpleProperty(searchObj, name);
            	   String searchValue = null;
            	   if(value!=null){
            		    searchValue = value.toString().trim();
            	   }else{
            		   continue;
            	   }
            	   if(searchValue!=null&&!"".equals(searchValue)){
            		   //[1].In 多个条件查询{逗号隔开参数}
            		   if(searchValue.indexOf(SUFFIX_COMMA)>=0){
            			   //页面输入查询条件，情况（取消字段的默认条件）
            			   if(searchValue.indexOf(SUFFIX_KG)>=0){
            				   String val = searchValue.substring(searchValue.indexOf(SUFFIX_KG));
            				   hqlbf.append(" and  "+name+" = ? ");
                   			   values.add(val.trim());
            			   }else{
            				   String[] vs = searchValue.split(SUFFIX_COMMA);
                			   hqlbf.append(" and  "+name+" in ("+StringUtil.getStringSplit(vs)+") ");
            			   }
            		   }
            		   //[2].模糊查询{带有* 星号的参数}
            		   else if(searchValue.indexOf(SUFFIX_ASTERISK)>=0){
            			   //searchValue.replace(SUFFIX_ASTERISK, SUFFIX_ASTERISK_VAGUE);
            			   hqlbf.append(" and  "+name+" like ? ");
               			   values.add(searchValue.replace(SUFFIX_ASTERISK, SUFFIX_ASTERISK_VAGUE));
            		   }
            		   //[3].不匹配查询{等于！叹号}
            		   //(1).不为空字符串
            		   else if(searchValue.equals(SUFFIX_NOT_EQUAL)){
            			   hqlbf.append(" and  (LENGTH("+name+")>0 or "+name+" is null) ");
            		   }
            		   //(2).不为NULL
            		   else if(searchValue.toUpperCase().equals(SUFFIX_NOT_EQUAL_NULL)){
            			   hqlbf.append(" and  "+name+" is not null ");
            		   }
            		   //(3).正常不匹配
            		   else if(searchValue.indexOf(SUFFIX_NOT_EQUAL)>=0){
            			   hqlbf.append("and ("+name+" != ? or "+name+" is null)");
               			   values.add(searchValue.replace(SUFFIX_NOT_EQUAL, ""));
            		   }
            		   //[4].全匹配查询{没有特殊符号的参数}
            		   else{
            			   hqlbf.append(" and  "+name+" = ? ");
               			   values.add(searchValue);
            		   }
            	   }
               }else if("class java.lang.Integer".equals(type)){
            	   Object value = PropertyUtils.getSimpleProperty(searchObj, name);
            	   if(value!=null&&!"".equals(value)){
            		   hqlbf.append(" and  "+name+" = ? ");
           			   values.add(value);
            	   }
               }
            }
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
  	}
	/*****************************************************************************************************************/
	/*****************************************************************************************************************/
	/*****************************************************************************************************************/
}
