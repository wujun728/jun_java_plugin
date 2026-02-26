package com.jun.plugin.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期类型转换器：处理Date与VARCHAR/ TIMESTAMP之间的转换
 * MappedTypes：指定Java类型
 * MappedJdbcTypes：指定JDBC类型
 */
@MappedTypes({Date.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.TIMESTAMP})
public class DateTypeHandler extends BaseTypeHandler<Date> {
    // 日期格式化器
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // ==== 将Java类型转换为JDBC类型（写入数据库） ====
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, sdf.format(parameter));
    }

    // ==== 将JDBC类型转换为Java类型（从数据库读取） ====
    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateStr = rs.getString(columnName);
        return convertStrToDate(dateStr);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dateStr = rs.getString(columnIndex);
        return convertStrToDate(dateStr);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dateStr = cs.getString(columnIndex);
        return convertStrToDate(dateStr);
    }

    // 辅助方法：字符串转Date
    private Date convertStrToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("日期转换失败", e);
        }
    }
}