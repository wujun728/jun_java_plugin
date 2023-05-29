package cn.ipanel.apps.portalBackOffice.util.md5;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * MD5 的加密
 * 
 * @author tianwl
 * @datetime 2010-8-17 下午05:11:29
 */
public class MD5 {
	private static Logger logger = Logger.getLogger(MD5.class);
	/**
	 * 對字符串進行MD5加密
	 * @author tianwl
	 * @param s
	 * @return
	 */
	public final static String getStringMD5(String s) {
		byte[] strTemp = s.getBytes();
		return MD5.getByteArrayMD5(strTemp);
	}
	/**
	 * 對byte数组進行MD5加密
	 * @author tianwl
	 * @param source
	 * @return
	 */
	public final static String getByteArrayMD5(byte[] source) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(source);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			logger.error(e,e);
			return null;
		}
	}
	/**
	 * 得到图片的MD5，也可以用于二级栏目的MD5
	 * @author tianwl
	 * @param pic 图片的字符串 
	 * @param url
	 * @return
	 */
	public static String getPictureMD5(String pic,String url){
		String md5 = "";
		StringBuffer sb = new StringBuffer(pic);
		sb.append(url);
		md5 = MD5.getStringMD5(new String(sb));
		return md5;
	}
	
	/**
	 * 生成重庆的二级菜单的resourceNameMD5
	 * @author tianwl
	 * @param name
	 * @param url
	 * @param channelType
	 * @param services 如果没有此数据 输入 ""
	 * @return
	 */
	public static String getMenuMD5(String name ,String url,String channelType ,String services){
		String md5 = "";
		StringBuffer sb = new StringBuffer(name);
		sb.append(url);
		sb.append(channelType);
		if(services != null && !services.equals("")){
			sb.append(services);
		}
		md5 = MD5.getStringMD5(new String(sb));
		return md5;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.getStringMD5("aaaaaaaaaaaaaaaaaaa"));
		byte[] source = "aaaaaaaaaaaaaaaaaaa".getBytes();
		System.out.println(MD5.getByteArrayMD5(source));
		logger.info(MD5.getPictureMD5("asdfasdfadf", "www.163.com"));
	}
}
