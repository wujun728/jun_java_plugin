package org.coody.framework.core.util;

import java.lang.reflect.Array;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.coody.framework.core.entity.BaseModel;
import org.coody.framework.core.entity.BeanEntity;

/**
 * @remark json工具类,此工具类修改过N次。改的脖子好痛啊，总算走向成熟。
 * @author Coody
 * @email 644556636@qq.com
 * @blog 54sb.org
 */
public class JSONWriter {
	static char[] hex = "0123456789ABCDEF".toCharArray();

	public static String write(Object object) {
		return value(object);
	}

	public static String write(long n) {
		return write(new Long(n));
	}

	public static String write(double d) {
		return write(new Double(d));
	}

	public static String write(char c) {
		return write(new Character(c));
	}

	public static String write(boolean b) {
		return write(Boolean.valueOf(b));
	}

	private static String value(Object object) {
		if (object == null) {
			return add("\"\"");
		}
		if (object instanceof Class) {
			return string(object);
		}
		if (object instanceof Boolean) {
			return bool(((Boolean) object).booleanValue());
		}
		if (object instanceof Number) {
			return add(object);
		}
		if (object instanceof String) {
			return string(object);
		}
		if (object instanceof Character) {
			return string(object);
		}
		if (object instanceof java.util.Date) {
			return date(object);
		}
		if (object instanceof java.sql.Date) {
			return date(new Date(((java.sql.Date) object).getTime()));
		}
		if (object instanceof java.sql.Time) {
			return date(new Date(((java.sql.Time) (object)).getTime()));
		}
		if (object instanceof java.util.Date) {
			return date(new Date(((java.util.Date) (object)).getTime()));
		}
		if (object instanceof Map) {
			return map((Map<?, ?>) object);
		}
		if (object.getClass().isArray()) {
			return array(object);
		}
		if (object instanceof Iterable) {
			return array(((Iterable<?>) object).iterator());
		}
		if (object instanceof BaseModel) {
			return bean(object);
		}
		System.err.println(object.getClass().getName() + " Is Not BaseModel");
		return "";
	}

	private static String bean(Object object) {
		try {
			StringBuilder buf = new StringBuilder();
			List<BeanEntity> list = PropertUtil.getBeanFields(object);
			if (StringUtil.isNullOrEmpty(list)) {
				return "";
			}
			buf.append(add("{"));
			BeanEntity entity = null;
			String tmp = null;
			if (!StringUtil.isNullOrEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					try {
						entity = list.get(i);
						if (StringUtil.isNullOrEmpty(entity) || StringUtil.isNullOrEmpty(entity.getFieldName())
								|| StringUtil.isNullOrEmpty(entity.getFieldValue())) {
							continue;
						}
						tmp = add(entity.getFieldName(), entity.getFieldValue());
						if (StringUtil.isNullOrEmpty(tmp)) {
							continue;
						}
						buf.append(tmp);
						if (i >= list.size() - 1) {
							continue;
						}
						buf.append(add(','));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				buf = removeLastStr(buf, ',');
			}
			buf.append(add("}"));
			return buf.toString();
		} catch (Exception e) {
			return "";
		}

	}

	private static StringBuilder removeLastStr(StringBuilder buf, char value) {
		try {
			if (buf.charAt(buf.length() - 1) == value) {
				buf.deleteCharAt(buf.length() - 1);
			}

		} catch (Exception e) {
		}
		return buf;
	}

	private static String add(String name, Object value) {

		try {
			StringBuilder buf = new StringBuilder();
			String context = value(value);
			String field = add(name);
			if (StringUtil.isNullOrEmpty(field)) {
				return "";
			}
			if (StringUtil.isNullOrEmpty(context)) {
				context = "\"\"";
			}
			buf.append(add('"'));
			buf.append(field);
			buf.append(add("\":"));
			buf.append(context);
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	private static String map(Map<?, ?> map) {
		try {
			StringBuilder buf = new StringBuilder();
			buf.append(add("{"));
			Iterator<?> it = map.keySet().iterator();
			Object key = null;
			String keyC = null, value = null;
			while (it.hasNext()) {
				try {
					key = it.next();
					if (map.get(key) == null) {
						continue;
					}
					keyC = value(key);
					value = value(map.get(key));
					if (StringUtil.findNull(keyC, value) > -1) {
						continue;
					}
					buf.append(keyC);
					buf.append(add(":"));
					buf.append(value);
					if (it.hasNext()) {
						buf.append(add(","));
					}
				} catch (Exception e) {
				}
			}
			buf.append(add("}"));
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	private static String array(Iterator<?> it) {
		StringBuilder buf = new StringBuilder();
		try {
			buf.append(add("["));
			String value = null;
			while (it.hasNext()) {
				try {
					value = value(it.next());
					if (StringUtil.isNullOrEmpty(value)) {
						continue;
					}
					buf.append(value);
					if (it.hasNext()) {
						buf.append(add(","));
					}
				} catch (Exception e) {
				}

			}
			buf.append(add("]"));
			return buf.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	private static String array(Object object) {
		StringBuilder buf = new StringBuilder();
		try {
			String value = null;
			buf.append(add("["));
			int length = Array.getLength(object);
			for (int i = 0; i < length; ++i) {
				try {
					value = value(Array.get(object, i));
					if (StringUtil.isNullOrEmpty(value)) {
						continue;
					}
					buf.append(value);
					if (i < length - 1) {
						add(',');
					}
				} catch (Exception e) {
				}
			}
			buf.append(add("]"));
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	private static String bool(boolean b) {
		return add(b ? "true" : "false");
	}

	private static String date(Object obj) {
		StringBuilder buf = new StringBuilder();
		try {
			String value = add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) obj));
			if (StringUtil.isNullOrEmpty(value)) {
				return "";
			}
			buf.append(add('"'));
			buf.append(value);
			buf.append(add('"'));
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	@SuppressWarnings("unused")
	private static String sqldate(Object obj) {
		StringBuilder buf = new StringBuilder();
		try {
			String value = add(new SimpleDateFormat("yyyy-MM-dd").format((Date) obj));
			if (StringUtil.isNullOrEmpty(value)) {
				return "";
			}
			buf.append(add('"'));
			buf.append(value);
			buf.append(add('"'));
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	@SuppressWarnings("unused")
	private static String sqltime(Object obj) {
		StringBuilder buf = new StringBuilder();
		try {
			String value = add(new SimpleDateFormat("HH:mm:ss.SSS").format((Date) obj));
			if (StringUtil.isNullOrEmpty(value)) {
				return "";
			}
			buf.append(add('"'));
			buf.append(value);
			buf.append(add('"'));
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	private static String string(Object obj) {
		try {
			StringBuilder buf = new StringBuilder();
			buf.append(add('"'));
			CharacterIterator it = new StringCharacterIterator(obj.toString());

			for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
				try {
					if (c == '"') {
						buf.append(add("\\\""));
					} else if (c == '\\') {
						buf.append(add("\\\\"));
					} else if (c == '\b') {
						buf.append(add("\\b"));
					} else if (c == '\f') {
						buf.append(add("\\f"));
					} else if (c == '\n') {
						buf.append(add("\\n"));
					} else if (c == '\r') {
						buf.append(add("\\r"));
					} else if (c == '\t') {
						buf.append(add("\\t"));
					} else if (Character.isISOControl(c)) {
						buf.append(unicode(c));
					} else {
						buf.append(add(c));
					}
				} catch (Exception e) {
				}
			}
			buf.append(add('"'));
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	private static String add(Object obj) {
		return obj.toString();
	}

	private static String add(char c) {
		return String.valueOf(c);
	}

	private static String unicode(char c) {
		try {
			StringBuilder buf = new StringBuilder();
			buf.append(add("\\u"));
			int n = c;
			for (int i = 0; i < 4; ++i) {
				try {
					int digit = (n & 0xf000) >> 12;

					buf.append(add(hex[digit]));
					n <<= 4;
				} catch (Exception e) {
				}

			}
			return buf.toString();
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 格式化
	 * 
	 * @param jsonStr
	 * @return
	 * @author lizhgb
	 * @Date 2015-10-14 下午1:17:35
	 */
	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '{':
			case '[':
				sb.append(current);
				sb.append('\n');
				indent++;
				addIndentBlank(sb, indent);
				break;
			case '}':
			case ']':
				sb.append('\n');
				indent--;
				addIndentBlank(sb, indent);
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\') {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}

		return sb.toString();
	}

	/**
	 * 添加space
	 * 
	 * @param sb
	 * @param indent
	 * @author lizhgb
	 * @Date 2015-10-14 上午10:38:04
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}

}
