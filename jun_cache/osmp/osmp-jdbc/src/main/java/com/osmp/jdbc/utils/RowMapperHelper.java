/*   
 * Project: OSMP
 * FileName: RowMapperHelper.java
 * version: V1.0
 */
package com.osmp.jdbc.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Description:RowMapperHelper工具类
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 下午2:09:54
 */

public class RowMapperHelper {
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
	private RowMapperHelper() {

	}
	
	 /**
     * pattern : yyyy-MM-dd
     * 
     * @param date
     * @return
     */
	public static String formartDate(Date date) {
        if (null == date) {
            return null;
        }
        return dateFormat.format(date);
    }

    /**
     * pattern : yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String formartDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return dateTimeFormat.format(date);
    }
    
    /**
     * pattern : HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String formartTime(Date date) {
        if (null == date) {
            return null;
        }
        return timeFormat.format(date);
    }
	
	public static Map<String, Object> convertToMap(Map<String, Integer> metaData, ResultSet rs) throws SQLException {
		fillRowNames(metaData, rs);
		Map<String, Object> row = new HashMap<String, Object>();
		for (Map.Entry<String, Integer> entry : metaData.entrySet()) {
			Object value = null;
			if (Types.DATE == entry.getValue()) {
				value = formartDate(rs.getDate(entry.getKey()));
			} else if (Types.TIMESTAMP == entry.getValue()) {
				value = formartDateTime(rs.getTimestamp(entry.getKey()));
			} else if (Types.TIME == entry.getValue()) {
				value = formartTime(rs.getTime(entry.getKey()));
			} else {
				value = rs.getObject(entry.getKey());
			}
			row.put(entry.getKey(), value);
		}
		return row;
	}

	private static void fillRowNames(Map<String, Integer> metaData, ResultSet rs) throws SQLException {
		Assert.notNull(metaData, "The column names can't be null.");
		if (metaData.isEmpty()) {
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				metaData.put(rs.getMetaData().getColumnName(i), rs.getMetaData().getColumnType(i));
			}
		}
	}

}
