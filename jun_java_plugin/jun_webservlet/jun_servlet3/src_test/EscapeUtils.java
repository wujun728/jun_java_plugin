

import org.apache.commons.lang.StringEscapeUtils;

public class EscapeUtils {

	/**
	 * 转义字符.
	 * 
	 * @param string 字符
	 * @param type 字符类型
	 */
	public static String escape(String string, String type) {
		String unescape = "不支持对" + type + "字符的转义";
		if (type.equals(LanguageUtils.CONST_HTML)) {
			unescape = StringEscapeUtils.escapeHtml(string);
		} else if (type.equals(LanguageUtils.CONST_XML)) {
			unescape = StringEscapeUtils.escapeXml(string);
		} else if (type.equals(LanguageUtils.CONST_SQL)) {
			unescape = StringEscapeUtils.escapeSql(string);
		} else if (type.equals(LanguageUtils.CONST_JAVA)) {
			unescape = StringEscapeUtils.escapeJava(string);
		} else if (type.equals(LanguageUtils.CONST_JavaScript)) {
			unescape = StringEscapeUtils.escapeJavaScript(string);
		} else if (type.equals(LanguageUtils.CONST_CSV)) {
			unescape = StringEscapeUtils.escapeCsv(string);
		}
		return unescape;
	}

	/**
	 * 还原转义字符.
	 * 
	 * @param string 转义字符
	 * @param type 字符类型
	 */
	public static String unescape(String string, String type) {
		String escape = "转义字符还原遇到错误";
		if (type.equals(LanguageUtils.CONST_HTML)) {
			escape = StringEscapeUtils.unescapeHtml(string);
		} else if (type.equals(LanguageUtils.CONST_XML)) {
			escape = StringEscapeUtils.unescapeXml(string);
		} else if (type.equals(LanguageUtils.CONST_SQL)) {
			escape = type + "转义字符不能进行还原";
		} else if (type.equals(LanguageUtils.CONST_JAVA)) {
			escape = StringEscapeUtils.unescapeJava(string);
		} else if (type.equals(LanguageUtils.CONST_JavaScript)) {
			escape = StringEscapeUtils.unescapeJavaScript(string);
		} else if (type.equals(LanguageUtils.CONST_CSV)) {
			escape = StringEscapeUtils.unescapeCsv(string);
		}
		return escape;
	}

}
