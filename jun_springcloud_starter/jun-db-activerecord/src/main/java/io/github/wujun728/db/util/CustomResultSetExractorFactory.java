package io.github.wujun728.db.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 */
public class CustomResultSetExractorFactory {
    /**
     * 两列结果值得映射Mapper类
     */
    private static final ResultSetExtractor DOUBLE_COLUMN_VALUE_RESULTSETEXRACTOR = (rs) -> {
        Map<Object, Object> result = new LinkedHashMap<>();
        while (rs.next()) {
            result.put(rs.getObject(1), rs.getObject(2));
        }
        return result;
    };


    /**
     * 创建两列结果值得Map集合的RowMapper
     * 注意：这个方法可能会导致类型转换BUG，已过时，建议使用重载方法
     *
     * @return ResultSetExtractor 两列结果值得映射抽取器
     */
    @Deprecated
    public static ResultSetExtractor createDoubleColumnValueResultSetExractor() {
        return DOUBLE_COLUMN_VALUE_RESULTSETEXRACTOR;
    }

    public static <A, B> ResultSetExtractor createDoubleColumnValueResultSetExtractor(Class<A> kCls, Class<B> vCls) {
        return new ResultSetExtractor() {
            Map result = new LinkedHashMap<>(1);

            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    result.put(getColumVal(kCls, rs, 1), getColumVal(vCls, rs, 2));
                }
                return result;
            }
        };
    }


    public static <T> Object getColumVal(Class<T> cls, ResultSet rs, int i) throws SQLException {
        if (String.class.equals(cls)) {
            return rs.getString(i);
        } else if (int.class.equals(cls) || Integer.class.equals(cls)) {
            return rs.getInt(i);
        } else if (double.class.equals(cls) || Double.class.equals(cls)) {
            return rs.getDouble(i);
        } else if (Date.class.isAssignableFrom(cls)) {
            return rs.getDate(i);
        } else if (long.class.equals(cls) || Long.class.equals(cls)) {
            return rs.getLong(i);
        } else if (float.class.equals(cls) || Float.class.equals(cls)) {
            return rs.getFloat(i);
        } else if (boolean.class.equals(cls) || Boolean.class.equals(cls)) {
            return rs.getBoolean(i);
        } else if (short.class.equals(cls) || Short.class.equals(cls)) {
            return rs.getShort(i);
        } else if (byte.class.equals(cls) || Byte.class.equals(cls)) {
            return rs.getByte(i);
        } else if (BigDecimal.class.equals(cls)) {
            return rs.getBigDecimal(i);
        } else {
            return rs.getObject(i);
        }
    }

}
