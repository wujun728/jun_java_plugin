// 功能已合并到RecordUtil.formatSql()
/*
package io.github.wujun728.db.record;

import cn.hutool.core.util.StrUtil;
//import com.ruoyi.common.exception.base.BaseException;

import java.util.List;
import java.util.StringTokenizer;

/**
 * sql操作工具类
 * /
public class SqlUtil extends cn.hutool.db.sql.SqlUtil {
	/**
	 * 仅支持字母、数字、下划线、空格、逗号、点（支持多个字段排序）
	 * /
	public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

	/**
	 * 检查字符，防止注入绕过
	 * /
    public static String escapeOrderBySql(String value) {
        if (StrUtil.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new DbException("参数不符合规范，不能进行查询");
        }
        return value;
    }

	/**
	 * 验证 order by 语法是否符合规范
	 * /
	public static boolean isValidOrderBySql(String value) {
		return value.matches(SQL_PATTERN);
	}

	/**
	 * 替换参数字符串
	 * @param sql
	 * @param params
	 * /
	public static String getSql(String sql, Object[] params) {
		if(params == null || params.length == 0) {
			return sql;
		}

		int i = 0;
		StringTokenizer st = new StringTokenizer(sql, "?", true);
		StringBuffer bf= new StringBuffer("");
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			if(temp.equals("?")) {
				bf.append("'"+String.valueOf(params[i])+"'");
				i++;
			} else {
				bf.append(temp);
			}
		}

		return bf.toString();
	}

	/**
	 * 获取不定参数的sql语句
	 * @param sql
	 * @param list
	 * @return
	 * /
	public static String getSql(String sql, List<String> list) {
		return getSql(sql, list.toArray());
	}

	/**
     * 处理sql语句中in的占位符
     * @param inParams in的参数
     * @param paramList 参数list
     * @return 与参数个数相同数量、用逗号拼接的占位符字符串
     * /
    public static String rebuildInSql(String inParams, List<String> paramList){
        return rebuildInSql(inParams, paramList, ",");
    }

	/**
     * 处理sql语句中in的占位符
     * @param inParams in的参数
     * @param paramList 参数list
     * @param splitChart inParams的分隔符
     * @return 与参数个数相同数量、用逗号拼接的占位符字符串
     * /
    public static String rebuildInSql(String inParams, List<String> paramList, String splitChart){
        String[] paramArray = inParams.split(splitChart);
        String inSql = "";
        for (int i = 0; i < paramArray.length; i++) {
            inSql += ",?";
            paramList.add(paramArray[i]);
        }
        if (!inSql.equals("")) {
            inSql = inSql.substring(1);
        }
        return inSql;
    }

	/**
     * 处理sql语句中in的占位符
     * @param paramCount 参数个数
     * @return 与参数个数相同数量、用逗号拼接的占位符字符串
     * /
    public static String rebuildInSql(int paramCount){
        String inSql = "";
        for (int i = 0; i < paramCount; i++) {
            inSql += ",?";
        }
        if (!inSql.equals("")) {
            inSql = inSql.substring(1);
        }
        return inSql;
    }

	/**
     * 处理sql语句中in的占位符
     * @param inParams in的参数数组
     * @return 与参数个数相同数量、用逗号拼接的占位符字符串
     * /
    public static String rebuildInSql(String[] inParams, List<String> paramList){
        String inSql = "";
        for (int i = 0; i < inParams.length; i++) {
        	if(StrUtil.isNotBlank(inParams[i])) {
        		inSql += ",?";
                paramList.add(inParams[i]);
        	}
        }
        if (!inSql.equals("")) {
            inSql = inSql.substring(1);
        }
        return inSql;
    }




}
*/
