package cn.rjzjh.commons.util.apiext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.mvel2.MVEL;

import cn.rjzjh.commons.util.exception.ExceptAll;
import cn.rjzjh.commons.util.exception.ParamInfoBean;
import cn.rjzjh.commons.util.exception.ProjectException;

@SuppressWarnings("rawtypes")
public abstract class CollectionUtil {
	/**
	 * 把List通过分隔符进行分隔
	 * 
	 * @param fromList
	 *            要连接的List
	 * @param joinStr
	 *            连接的字符串
	 * @return String
	 */

	public static String listJoin(List<String> fromList, String joinStr)
			throws ProjectException {
		if (fromList == null)
			throw new ProjectException(ExceptAll.default_Param,
					new ParamInfoBean(1, "要连接的数据不能传空"), "zh");
		joinStr = StringUtil.isNull(joinStr) ? "," : joinStr;
		StringBuffer toList = new StringBuffer();
		Iterator iterator = fromList.iterator();
		if (iterator.hasNext())
			toList.append((String) iterator.next());
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			toList.append(joinStr);
			toList.append(string);
		}
		return toList.toString();
	}

	/**
	 * 把Array通过分隔符进行分隔
	 * 
	 * @param fromList
	 *            要连接的数组
	 * @param joinStr
	 *            连接的字符串
	 */
	public static String arrayJoin(Object[] fromList, String joinStr)
			throws ProjectException {
		if (fromList == null)
			throw new ProjectException(ExceptAll.default_Param,
					new ParamInfoBean(1, "要连接的数据不能传空"), "zh");
		joinStr = StringUtil.isNull(joinStr) ? "," : joinStr;
		StringBuffer toList = new StringBuffer();
		for (int i = 0; i < fromList.length; i++) {
			if (i == 0) {
				toList.append(fromList[i]);
			} else {
				toList.append(joinStr + fromList[i]);
			}
		}
		return toList.toString();
	}

	public static String[] arrayMerge(String[] a, String[] b) {
		if (ArrayUtils.isEmpty(a)) {
			return b;
		}
		if (ArrayUtils.isEmpty(b)) {
			return a;
		}
		String[] newArray = new String[a.length + b.length];
		System.arraycopy(a, 0, newArray, 0, a.length);
		System.arraycopy(b, 0, newArray, a.length, b.length);
		return newArray;
	}

	/**
	 * 把list分成sumPerRow一组
	 * 
	 * @param inputList
	 *            要分隔的List
	 * @param sumPerRow
	 *            第个List的个数
	 * */
	public static List<List<?>> splitList(List<?> inputList, int sumPerRow) {
		if (CollectionUtils.isEmpty(inputList) || sumPerRow == 0) {
			return null;
		}
		List<List<?>> returnList = new ArrayList<List<?>>();
		List<Object> addList = new ArrayList<Object>();
		for (int i = 0; i < inputList.size(); i++) {
			if (i >= sumPerRow && i % sumPerRow == 0) {
				returnList.add(addList);
				addList = new ArrayList<Object>();
			}
			addList.add(inputList.get(i));
		}
		returnList.add(addList);
		return returnList;
	}

	/**
	 * 通过List得到对象的单个列值
	 * 
	 * @param fromList
	 *            要操作的数据源
	 * @param colName
	 *            要提取的列名
	 * @return List 提取预定列的List
	 * */
	public static List<?> getColFromObj(List<?> fromList, String colName) {
		List<Object> retList = new ArrayList<Object>();
		if (CollectionUtils.isEmpty(fromList)) {
			return retList;
		}
		for (Object object : fromList) {
			Object result = null;
			if (ReflectAsset.isInterface(object.getClass(), "java.util.Map")) {
				Map tempObjMap = (Map) object;
				result = tempObjMap.get(colName);
			} else {
				result = MVEL.eval(colName, object);
			}
			retList.add(result);
		}
		return retList;
	}

	/**
	 * @param oriStr
	 *            要操作的源字符串
	 * @return Str 返回可用于查询的串
	 * */
	public static String getSQLStr(String oriStr) {
		if (StringUtil.isNull(oriStr)) {
			return null;
		}
		return ("'" + oriStr + "'").replaceAll(",", "','");
	}

	/**
	 * @param inputCollection
	 *            要操作的字符
	 * @param predicate
	 *            规则
	 * @return 返回符合条件的集合并把这个集合从inputCollection删除（即inputCollection只剩余不合条件的数据）
	 * */
	public static Collection selectFilter(Collection inputCollection,
			Predicate predicate) {
		Collection retCollection = (Collection) CollectionUtils.find(
				inputCollection, predicate);
		CollectionUtils.filter(inputCollection, predicate);
		return retCollection;
	}

	/***
	 * 过滤空值
	 * 
	 * @param orimap
	 */
	public static void filterNull(Map<String, String> orimap) {
		if (orimap == null) {
			return;
		}
		for (String key : orimap.keySet()) {
			if (orimap.get(key) == null) {
				orimap.remove(key);
			}
		}
	}

	/*****
	 * 过滤原始的　List
	 * 
	 * @param oriList
	 *            　原对象列表
	 * @param colName
	 *            对象列名
	 * @param include
	 *            允许的值
	 * @param exclude
	 *            排除的值
	 * @return
	 */
	public static List filter(List oriList, final String colName,
			String include, String exclude) {
		List retAry = new ArrayList();
		if (CollectionUtils.isEmpty(oriList)) {
			return retAry;
		}
		if (StringUtil.isNotNull(include)) {
			String[] iAry = include.split(",");
			for (int i = 0; i < iAry.length; i++) {
				String includeValue = iAry[i];
				for (Object eleObj : oriList) {
					try {
						if (ReflectAsset.isInterface(eleObj.getClass(),
								"java.util.Map")) {
							Map tempObj = (Map) eleObj;
							if (includeValue.equalsIgnoreCase(String
									.valueOf(tempObj.get(colName)))) {
								retAry.add(eleObj);
								break;// 退出本层循环
							}
						} else {
							Object tempObj = PropertyUtils.getProperty(eleObj,
									colName);
							if (includeValue.equalsIgnoreCase(String
									.valueOf(tempObj))) {
								retAry.add(eleObj);
								break;// 退出本层循环
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}

		if (StringUtil.isNotNull(exclude)) {
			final String[] eAry = exclude.split(",");
			retAry = CollectionUtils.isEmpty(retAry) ? oriList : retAry;
			CollectionUtils.filter(retAry, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					try {
						if (ReflectAsset.isInterface(object.getClass(),
								"java.util.Map")) {
							Map tempObj = (Map) object;
							if (ArrayUtils.contains(eAry,
									String.valueOf(tempObj.get(colName)))) {
								return false;
							}
						} else {
							Object tempObj = PropertyUtils.getProperty(object,
									colName);
							if (ArrayUtils.contains(eAry, tempObj)) {
								return false;
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					return true;
				}
			});
		}

		retAry = CollectionUtils.isEmpty(retAry) ? oriList : retAry;
		return retAry;

	}

	/***
	 * int数组转为List,因为Arrays.asList只支持对象的数组转成List
	 * 
	 * @param oriAry
	 * @return
	 */
	public static List<Integer> asList(int[] oriAry) {
		List<Integer> ret = new ArrayList<Integer>();
		if (org.apache.commons.lang3.ArrayUtils.isNotEmpty(oriAry)) {
			for (int integer : oriAry) {
				ret.add(integer);
			}
		}
		return ret;
	}

	/***
	 * 把string数据转成整形List
	 * 
	 * @param oriAry
	 * @return
	 */
	public static List<Integer> asList(String[] oriAry) {
		List<Integer> ret = new ArrayList<Integer>();
		if (org.apache.commons.lang3.ArrayUtils.isNotEmpty(oriAry)) {
			for (String ele : oriAry) {
				ret.add(Integer.parseInt(ele));
			}
		}
		return ret;
	}

	public static List<String> asList(List<?> oriList) {
		if (oriList == null) {
			return null;
		}
		List<String> ret = new ArrayList<String>();
		for (Object object : oriList) {
			ret.add(String.valueOf(object));
		}
		return ret;
	}

}
