/**
 * 
 */
package com.jun.web.biz.factory;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jun.web.biz.beans.User;

public class Bean {

	private static final String RT_1 = "\r\n";
	private static final String RT_2 = RT_1 + RT_1;
	private static final String BLANK_1 = " ";
	private static final String BLANK_4 = "    ";
	private static final String BLANK_8 = BLANK_4 + BLANK_4;

	private static final String ANNOTATION_AUTHOR_PARAMTER = "@author ";
	private static final String ANNOTATION_AUTHOR_NAME = "hongten(hongtenzone@foxmail.com)";
	private static final String ANNOTATION_AUTHOR = ANNOTATION_AUTHOR_PARAMTER + ANNOTATION_AUTHOR_NAME;
	private static final String ANNOTATION_DATE = "@date ";
	private static final String ANNOTATION = "/**" + RT_1 + BLANK_1 + "*" + BLANK_1 + ANNOTATION_AUTHOR + RT_1 + BLANK_1 + "*" + BLANK_1 + ANNOTATION_DATE
			+ getDate() + RT_1 + BLANK_1 + "*/" + RT_1;

	private static final String DAO_PATH = "com/jun/web/dao";
	private static final String DAO_IMPL_PATH = "com/jun/web/dao/impl";
	private static final String SERVICE_PATH = "com/jun/web/base/service";
	private static final String SERVICE_IMPL_PATH = "com/jun/web/base/service/impl";

	private static final String BEAN_URL = "com.jun.web.base.bean";
	private static final String DAO_URL = "com.jun.web.base.dao";
	private static final String DAO_IMPL_URL = "com.jun.web.base.dao.impl";
	private static final String SERVICE_URL = "com.jun.web.base.service";
	private static final String SERVICE_IMPL_URL = "com.jun.web.base.service.impl";

	private static final String BASE_DAO_NAME = DAO_URL + ".BaseDao";
	private static final String ABSTRACT_BASE_DAO_IMPL_NAME = DAO_IMPL_URL + ".AbstractBaseDaoImpl";
	private static final String BASE_SERVICE_NAME = SERVICE_URL + ".BaseService";
	private static final String ABSTRACT_BASE_SERVICE_IMPL_NAME = SERVICE_IMPL_URL + ".AbstractBaseServiceImpl";

	/**
	 * @param c
	 * @throws Exception
	 */
	public void createBeanDao(Class c) throws Exception {
		String cName = c.getName();
		String fileName = System.getProperty("user.dir") + "/src/" + DAO_PATH + "/" + getLastChar(cName) + "Dao.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write("package " + DAO_URL + ";" + RT_2 + ANNOTATION + "public interface " + getLastChar(cName) + "Dao extends " + BASE_DAO_NAME + " <" + cName
				+ "> {" + RT_2 + "}");
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * ����bean��Dao��ʵ����
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanDaoImpl(Class c) throws Exception {
		String cName = c.getName();
		String fileName = System.getProperty("user.dir") + "/src/" + DAO_IMPL_PATH + "/" + getLastChar(cName) + "DaoImpl.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write("package " + DAO_IMPL_URL + ";" + RT_2 + ANNOTATION + "public class " + getLastChar(cName) + "DaoImpl extends " + ABSTRACT_BASE_DAO_IMPL_NAME
				+ "<" + cName + "> implements " + DAO_URL + "." + getLastChar(cName) + "Dao{" + RT_2 + "}");
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * ����bean��service
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanService(Class c) throws Exception {
		String cName = c.getName();
		String fileName = System.getProperty("user.dir") + "/src/" + SERVICE_PATH + "/" + getLastChar(cName) + "Service.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write("package " + SERVICE_URL + ";" + RT_2 + ANNOTATION + "public interface " + getLastChar(cName) + "Service extends " + BASE_SERVICE_NAME + "<"
				+ cName + ">{" + RT_2 + "}");
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * ����bean��service��ʵ����
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void createBeanServiceImpl(Class c) throws Exception {
		String cName = c.getName();
		String fileName = System.getProperty("user.dir") + "/src/" + SERVICE_IMPL_PATH + "/" + getLastChar(cName) + "ServiceImpl.java";
		File f = new File(fileName);
		FileWriter fw = new FileWriter(f);
		fw.write("package " + SERVICE_IMPL_URL + ";" + RT_2 + ANNOTATION + "public class " + getLastChar(cName) + "ServiceImpl extends "
				+ ABSTRACT_BASE_SERVICE_IMPL_NAME + "<" + cName + "> implements " + SERVICE_URL + "." + getLastChar(cName) + "Service{" + RT_2 + BLANK_4
				+ "private " + DAO_URL + "." + getLastChar(cName) + "Dao " + getLowercaseChar(getLastChar(cName)) + "Dao;" + RT_2 + BLANK_4 + "public void set"
				+ getLastChar(cName) + "Dao(" + DAO_URL + "." + getLastChar(cName) + "Dao " + getLowercaseChar(getLastChar(cName)) + "Dao){" + RT_1 + BLANK_8
				+ "this." + getLowercaseChar(getLastChar(cName)) + "Dao = " + getLowercaseChar(getLastChar(cName)) + "Dao;" + RT_1 + BLANK_4 + "}" + RT_2
				+ BLANK_4 + "@Override" + RT_1 + BLANK_4 + "public " + DAO_URL + "." + "BaseDao<" + BEAN_URL + "." + getLastChar(cName) + "> getBaseDao(){"
				+ RT_1 + BLANK_8 + "return " + getLowercaseChar(getLastChar(cName)) + "Dao;" + RT_1 + BLANK_4 + "}" + RT_2 + "}");
		fw.flush();
		fw.close();
		showInfo(fileName);
	}

	/**
	 * ��ȡ·����������ַ�<br>
	 * �磺<br>
	 * <code>str = "com.java.base.bean.User"</code><br>
	 * <code> return "User";<code>
	 * 
	 * @param str
	 * @return
	 */
	public String getLastChar(String str) {
		if ((str != null) && (str.length() > 0)) {
			int dot = str.lastIndexOf('.');
			if ((dot > -1) && (dot < (str.length() - 1))) {
				return str.substring(dot + 1);
			}
		}
		return str;
	}

	/**
	 * �ѵ�һ����ĸ��ΪСд<br>
	 * �磺<br>
	 * <code>str = "UserDao";</code><br>
	 * <code>return "userDao";</code>
	 * 
	 * @param str
	 * @return
	 */
	public String getLowercaseChar(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * ��ʾ��Ϣ
	 * 
	 * @param info
	 */
	public void showInfo(String info) {
		System.out.println("�����ļ���" + info + "�ɹ���");
	}

	/**
	 * ��ȡϵͳʱ��
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}
	
	public static void main(String[] args) throws Exception{
		Bean beanUtils = new Bean();
		Bean.beanTool(beanUtils, User.class);
//		beanUtilTest.beanTool(beanUtils, People.class);
//		beanUtilTest.beanTool(beanUtils, Admin.class);
	}
	
	/**
	 * @param beanUtils
	 * @param c
	 * @throws Exception
	 */
	public static void beanTool(Bean beanUtils ,Class c)throws Exception{
		beanUtils.createBeanDao(c);
		beanUtils.createBeanDaoImpl(c);
		beanUtils.createBeanService(c);
		beanUtils.createBeanServiceImpl(c);
	}
}
