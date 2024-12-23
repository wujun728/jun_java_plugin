package io.github.wujun728.admin.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 字符串工具类
 * @author hyz
 *
 */
public class StringUtil {
	private static String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万",
			"拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	/**
	 * 将首字母变大写
	 * @param str
	 * @return
	 */
	public static String toFirstUp(String str){
		return str.length() <= 1 ? str.toUpperCase() : String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
	}
	/**
	 * 将首字母变小写
	 * @param str
	 * @return
	 */
	public static String toFirstLow(String str){
		return str.length() <= 1 ? str.toLowerCase() :String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1);
	}
	/**
	 * 将数组转换成一个分隔符连接的字符串 
	 * @param str
	 * @param split
	 * @return
	 */
	public static String concatStr(String[] str,String split){
		if(str == null || str.length == 0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(String s :str){
			buf.append(s+split);
		}
		buf.delete(buf.lastIndexOf(split),buf.length());
		
		return buf.toString();
	}
	/**
	 * 将数组转换成一个分隔符连接的字符串  并且包含前缀后缀 比如单引号
	 * @param str
	 * @param split
	 * @param preSufix
	 * @return
	 */
	public static String concatStr(String[] str,String split,String preSufix){
		if(str == null || str.length == 0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(String s :str){
			buf.append(preSufix).append(s).append(preSufix).append(split);
		}
		buf.delete(buf.lastIndexOf(split),buf.length());
		return buf.toString();
	}
	
	public static String concatStr(Long[] str,String split){
		if(str == null || str.length == 0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(Long s :str){
			buf.append(s+split);
		}
		buf.delete(buf.lastIndexOf(split),buf.length());
		
		return buf.toString();
	}
	
	public static String concatStr(Collection<?> str,String split){
		if(str == null || str.size() == 0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(Object s :str){
			buf.append(s+split);
		}
		buf.delete(buf.lastIndexOf(split),buf.length());
		
		return buf.toString();
	}
	
	public static String concatStr(Collection<?> str,String split,String preSufix){
		if(str == null || str.size() == 0){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(Object s :str){
			buf.append(preSufix).append(s).append(preSufix).append(split);
		}
		buf.delete(buf.lastIndexOf(split),buf.length());
		return buf.toString();
	}
	
	public static String[] splitStr(String str,String split){
		if(str == null || str.equals("")){
			return new String[]{};
		}
		return str.split(split);
	}
	public static String format2Z(String str){
		return to$(str).replaceAll("￥", "");
	}
	public static String toPercent(String str){
		return to$(str).replaceAll("￥", "") + "%";
	}
	public static String to2Z(String str){
		return to$(str).replaceAll("￥|,", "");
	}
	/**
	 * 将字符串转换成金额的格式
	 * @param str
	 * @return
	 */
	public static String to$(String str){
		if(str == null || str.equals("")){
	        return "￥0.00";
	    }
	    if(str.lastIndexOf(".") <= 0){
	       //不含小数点
	       str = addStr$(str) + ".00";
	    }else{
	       //含小数点
	       String top = str.substring(0,str.indexOf("."));//整数部分
	       
	       if(top.length() > 1 && top.charAt(0) == '0'){
	            top = top.substring(1,top.length());
	       }
	       
	       String end = str.substring(str.indexOf(".") + 1,str.length());//小数部分
	       
	       if(end.length() > 2){
	           end = end.substring(0,2);
	       }else if(end.length() == 1){
	           end = end + "0";
	       }else if(end.length() == 0){
	           end = end + "00";
	       }
	       
	       str = addStr$(top) + "." + end;
	    }
	    
	    return "￥" + str;
	}
	
	
	private static String addStr$(String str){
		String buf = "";
	    while(true){
	        if(str.length() <=3){
	            break;
	        }
	        buf = "," + str.substring(str.length() - 3,str.length()) + buf;
	        
	        str = str.substring(0,str.length() - 3);
	    }
	    buf = str + buf;
	    if(buf.indexOf(",") == 0 ){
	        buf = buf.substring(1,buf.length());
	    }
	    
	    return buf;
	}
	/**
	 * 将字符串数组 转换成List  这里不使用Arrays.asList的原因 用这个转换之后 对List的操作就会报错 
	 *  	java.lang.UnsupportedOperationException
	 * @param arrs
	 * @return
	 */
	public static List<String> toList(String[] arrs){
		List<String> list = new ArrayList<String>();
		if(arrs != null){
			for(String s : arrs){
				list.add(s);
			}
		}
		return list;
	}
	/**
	 * 将List转换成Array
	 * @param list
	 * @return
	 */
	public static String[] toArray(Collection<String> list){
		String[] arrs = new String[list.size()];
		if(list != null){
			int i=0;
			for(String s : list){
				arrs[i++] = s;
			}
		}
		return arrs;
	}
	/**
	 * a,b,c + d,e => a,b,c,d,e
	 * @param src
	 * @param split
	 * @param strs
	 * @return
	 */
	public static String addStrs(String src,String split,String[] strs){
		if(src !=null && src.length()>0){
			src = src + split;
		}
		return src + StringUtil.concatStr(strs, split);
	}
	/**
	 * a,b,c + d => a,b,c,d
	 * @param src
	 * @param split
	 * @param str
	 * @return
	 */
	public static String addStr(String src,String split,String str){
		if(src !=null && src.length()>0){
			src = src + split;
		}
		return src + str;
	}
	/**
	 * 移除字符串 
	 * @param src
	 * @param split
	 * @param strs
	 * @return
	 */
	public static String removeStrs(String src,String split,String[] strs){
		String[] arr = StringUtil.splitStr(src, split);
		List<String> list = toList(arr);
		list.removeAll(Arrays.asList(strs));
		return StringUtil.concatStr(toArray(list),split);
	}
	/**
	 * 移除字符串  a,b,c,d   a =>> b,c,d
	 * @param src
	 * @param split
	 * @param str
	 * @return
	 */
	public static String removeStr(String src,String split,String str){
		String[] arr = StringUtil.splitStr(src, split);
		List<String> list = toList(arr);
		list.remove(str);
		return StringUtil.concatStr(toArray(list),split);
	}
	
	public static String getStr(String str){
		return str == null ? "" : str;
	}
	public static String getStr(Object str){
		return str == null ? "" : str.toString();
	}
	/**
	 * 规则 如果第二个字母是大写 则不变  ,如果第二个字母是小写 将第一个字母变成大写
	 * abcCCC ==>> AbcCCC
	 * ABCDdd==>> ABCDdd
	 * @param fieldName
	 * @return
	 */
	public static String getMethodNameByFieldName(String fieldName){
		if(fieldName.length() > 1 && fieldName.charAt(1) >= 'A' && fieldName.charAt(1) <= 'Z'){
			return fieldName;
		}else{
			return toFirstUp(fieldName);
		}
	}
	/**
	 * 规则 如果第二个字母是大写 则不变  ,如果第二个字母是小写 将第一个字母变成小写
	 * Name =>> name
	 * ABS =>> ABS
	 * @param methodName
	 * @return
	 */
	public static String getFieldNameByMethodName(String methodName){
		if(methodName.length() > 1 && methodName.charAt(1) >= 'A' && methodName.charAt(1) <= 'Z'){
			return methodName;
		}else{
			return toFirstLow(methodName);
		}
	}
	/**
	 * 转换成大写  decoProjectID ==>> DECO_PROJECT_ID
	 * 			 functionUrl ==>> FUNCTION_URL
	 * @param name
	 * @return
	 */
	public static String toSqlColumn(String name){
		StringBuffer sqlColumn = new StringBuffer();
		char[] array = name.toCharArray();
		for(int i=0;i<array.length;i++){
			char c = array[i];
			sqlColumn.append(String.valueOf(c).toUpperCase());
			if(i != array.length-1){
				char nextC = array[i+1];
				if((c>='a' && c<='z' && nextC>='A' && nextC<='Z') && !(nextC>='a' && nextC<='z' && c>='A' && c<='Z')){
					sqlColumn.append("_");
				}
			}
		}
		return sqlColumn.toString();
	}

	public static String toFieldColumn(String columnName){
		columnName = columnName.toLowerCase();
		StringBuffer name = new StringBuffer();
		char[] array = columnName.toCharArray();
		for(int i=0;i<array.length;i++){
			char c = array[i];
			if(c == '_' && array.length>i+1){
				i++;
				name.append((array[i]+"").toUpperCase());
			}else{
				name.append(array[i]);
			}
		}
		return name.toString();
	}
	
	public static boolean contains(String[] arr,String str){
		for(String s : arr){
			if(s.equals(str)){
				return true;
			}
		}
		return false;
	}
	//如果不包含就加上
	public static String[] notContainsAdd(String[] arr,String str){
		if(!contains(arr, str)){
			String[] _newArr = new String[arr.length + 1];
			for(int i=0;i<arr.length;i++){
				_newArr[i] = arr[i];
			}
			_newArr[arr.length] = str;
			arr = _newArr;
		}
		return arr;
	}
	//如果包含 就去除
	public static String[] containsRemove(String[] arr,String str){
		List<String> list = new ArrayList<String>();
		for(String s :arr){
			if(s.equals(str)){
				continue;
			}
			list.add(s);
		}
		return toArray(list);
	}
	
	//去除多余的0
	public static String removeLastZero(String str){
		if(str.contains(".")&&str.endsWith("0")){
			str = str.replaceAll("0+$", "");
			if(str.endsWith(".")){
				str = str.replaceAll("\\.", "");
			}
		}
		return str;
	}
	
	public static String[] combineArr(String[] arr1,String[] arr2){
		Set<String> set = new HashSet<String>();
		for(String s : arr1){
			set.add(s);
		}
		for(String s : arr2){
			set.add(s);
		}
		return toArray(set);
	}
	//求交集
	public static String[] retains(String[] arr1,String[] arr2){
		List<String> list1 = toList(arr1);
		List<String> list2 = toList(arr2);
		list1.retainAll(list2);
		return toArray(list1);
	}
	
	//trim
	public static String trim(String str){
		if(str == null){
			return "";
		}
		return str.trim();
	}
	/**
	 * 设置字符串前导字符
	 * @param source java.lang.String　待处理字符串
	 * @param addStr java.lang.String　前导字符串
	 * @param len int　长度
	 */
	public static String getAddCode(String source, String addStr, int len) {
		if (source == null) {
			return "";
		}
		StringBuffer _rtn = new StringBuffer(100);
		if (source.length() < len) {
			for (int i = 0; i < len - source.length(); i++) {
				_rtn.append(addStr);
			}
			return _rtn + source;
		} else {
			return source;
		}
	}

	/**
	 * 将货币转换为大写形式(类内部调用)
	 * 
	 * @param NumStr
	 * @return String
	 */
	private static String PositiveIntegerToHanStr(String NumStr) {
		// 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15) {
			return "数值过大!";
		}
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ') {
				continue;
			}
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9) {
				return "输入含非数字字符!";
			}

			if (n != 0) {
				if (lastzero) {
					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				}
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) )
				// 如十进位前有零也不发壹音用此行
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // 十进位处于第一位不发壹音
				{
					RMBStr += HanDigiStr[n];
				}
				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
				{
					RMBStr += HanDiviStr[i]; // “亿”或“万”
				}
			}
			if (i % 8 == 0) {
				hasvalue = false; // 万进位前有值标记逢亿复位
			}
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0) {
			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		}
		return RMBStr;
	}
	
	/**
	 * 将货币转换为大写形式
	 * 
	 * @param val
	 *            传入的数据
	 * @return String 返回的人民币大写形式字符串
	 */
	public static String numToRMBStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "负";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999) {
			return "数值位数过大!";
		}
		// 四舍五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0) {
				TailStr += "角";
			}
			// 零元后不写零几分
			if (integer == 0 && jiao == 0) {
				TailStr = "";
			}
			if (fen != 0) {
				TailStr += HanDigiStr[fen] + "分";
			}
		}
		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		// if( !integer ) return SignStr+TailStr;
		return SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;
	}
	/**
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 */
	public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) {
		String agent = request.getHeader("USER-AGENT");
		try {
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				pFileName = URLEncoder.encode(pFileName, "utf-8");
			} else {
				pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pFileName;
	}

	public static String getAlis(String tableName){
		tableName = tableName.toLowerCase();
		StringBuffer name = new StringBuffer();
		char[] array = tableName.toCharArray();
		name.append(array[0]);
		for(int i=0;i<array.length;i++){
			char c = array[i];
			if(c == '_' && array.length>i+1){
				i++;
				name.append(array[i]);
			}
		}
		String alis = name.toString().toLowerCase();
		if(MySQLUtil.isKeyword(alis)){
			alis += "_";
		}
		return alis;
	}
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getMethodNameByFieldName("URL"));
		System.out.println(getFieldNameByMethodName("UrL"));
		System.out.println(toSqlColumn("decoProjectInfoID"));
		String[] arr = new String[]{"a","b","c","dd"};
		String str = "a,b,c,dd";
		System.out.println(StringUtil.concatStr(arr, ","));
		System.out.println(StringUtil.removeStr(str, ",", "dd"));
		System.out.println(StringUtil.addStrs(str, ",", new String[]{"m","k","b"}));
		
		String[] arr1 = new String[]{"a","b","c"};
		String[] arr2 = new String[]{"a","d","e"};
		arr1 = retains(arr1, arr2);
		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(containsRemove(arr2, "e")));
		System.out.println(Arrays.toString(notContainsAdd(arr2, "k")));
		
		System.out.println(contains(arr1, "a"));
		
		System.out.println(removeLastZero("-100.123"));

		System.out.println(toFieldColumn("table_name"));
	}
}
