package com.erp.springjdbc;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.admin.LogFactory;
import com.jun.plugin.utils.DateUtil;
import com.jun.plugin.utils.JdbcUtil;
import com.jun.plugin.utils.StringUtil;


public class JdbcTemplateSupport {
    
    @Autowired(required = false)
    @Qualifier("jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    
    /**
     * 查询函数
     * 
     * @param sql
     * @return
     */
    @SuppressWarnings("unchecked")
    public List query(String sql) {
        LogFactory.getLogger().info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        JdbcTemplate jt = jdbcTemplate;
        List list = new ArrayList();
        list = jt.queryForList(sql);
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return list;
    }
    
    /**
     * 查询函数
     * 
     * @param sql
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map queryForMap(String sql) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        JdbcTemplate jt = jdbcTemplate;
        Map map = new HashMap();
        try {
            map = jt.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            LogFactory.getLogger().info("执行queryForMap查询记录为空,返回空的map");
        }
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return map;
    }
    
    /**
     * 带参的查询
     * 
     * @param sql
     * @return
     */
    @SuppressWarnings("unchecked")
    public List query(String sql, Object parms[]) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        List list = new ArrayList();
        JdbcTemplate jt = jdbcTemplate;
        list = jt.queryForList(sql, parms);
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return list;
    }
    
    /**
     * 查询数据并返回指定的对象
     * 
     * @param sql
     * @param requiredType
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object query(String sql, Class requiredType) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        JdbcTemplate jt = jdbcTemplate;
        Object obj = null;
        obj = jt.queryForObject(sql, requiredType);
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return obj;
    }
    
    /**
     * 更新函数
     * 
     * @param sql
     * @return
     */
    public int update(String sql) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        JdbcTemplate jt = jdbcTemplate;
        int row = -1;
        row = jt.update(sql);
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return row;
    }
    
    /**
     * 批量更新
     * 
     * @param sql
     * @return
     */
    public int[] batchUpdate(String sql[]) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        JdbcTemplate jt = jdbcTemplate;
        int row[] = null;
        row = jt.batchUpdate(sql);
        return row;
    }
    
    /**
     * 批量更新
     * 
     * @param sql
     * @return
     */
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        JdbcTemplate jt = jdbcTemplate;
        int row[] = jt.batchUpdate(sql, pss);
        return row;
    }
    
    /**
     * 带参数的更新(表不存在大对象字段的处理)
     * 
     * @param sql
     * @param param
     * @return
     */
    public int update(String sql, Object param[]) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        LogFactory.getLogger().info("执行参数：" + Arrays.toString(param));
        Date startDate = new Date();
        JdbcTemplate jt = jdbcTemplate;
        int row = -1;
        row = jt.update(sql, param);
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return row;
    }
    
    /**
     * 带参数的更新(表存在大对象CLOB字段的处理)
     * 
     * @param sql
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public int updateLob(String sql, List list) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        Connection conn = null;
        PreparedStatement ps = null;
        int row = -1;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < list.size(); i++) {
                String lobStr = list.get(i).toString();
                ps.setCharacterStream(i + 1, new StringReader(lobStr), lobStr
                        .length());
            }
            row = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return row;
    }
    
    /**
     * 调用分页存储过程
     * 
     * @param sql
     * @param page
     * @param limit
     * @return 
     *         查询的结果集，结果集的第一个字段（iRecCount）返回的是查询结果的总条数。一般用于json结构里的totalCount字，第二个字段
     *         (iRowId)行ID，
     */
    @SuppressWarnings("unchecked")
    public List call(String sql, int start, int limit) {
        LogFactory.getLogger()
                .info("************************************************************************************************");
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        LogFactory.getLogger().info("执行路径：" + CallStack.toString());
        LogFactory.getLogger().info("执行脚本：" + sql);
        Date startDate = new Date();
        int driverType = getDriverType();
        List list = new ArrayList();
        try {
            if (driverType == 3) {
                JdbcTemplate jt = jdbcTemplate;
                list = jt.queryForList("{call PROC_GET_DATABYPAGE(?,?,?)}",
                        new Object[] { sql, (start - 1) * limit, limit });
            } else if (driverType == 4) {
                JdbcTemplate jt = jdbcTemplate;
                list = jt.queryForList("call PROC_GET_DATABYPAGE('"
                        + sql.replaceAll("'", "''") + "'," + (start - 1)
                        * limit + "," + limit + ")");
            } else if (driverType == 2) {
                Connection conn = null;
                CallableStatement stmt = null;
                ResultSet rs = null;
                try {
                    conn = jdbcTemplate.getDataSource().getConnection();
                    stmt = (CallableStatement) conn
                            .prepareCall("{call PROC_GET_DATABYPAGE(?, ?, ?, ?)}");
                    stmt.setString(1, sql);
                    stmt.setInt(2, (start - 1) * limit);
                    stmt.setInt(3, limit);
//                    stmt.registerOutParameter(4, OracleTypes.CURSOR);
                    stmt.execute();
                    rs = (ResultSet) stmt.getObject(4);
                    ResultSetMetaData rsMetaData = rs.getMetaData();
                    int colCount = rsMetaData.getColumnCount();
                    while (rs.next()) {
                        Map map = new HashMap();
                        for (int j = 1; j <= colCount; j++) {
                            String columnName = rsMetaData.getColumnName(j);
                            int len = rsMetaData.getScale(j);
                            if (len > 0) {
                                map.put(columnName, StringUtil.toString(rs
                                        .getBigDecimal(columnName)));
                            } else {
                                map.put(columnName, rs.getString(columnName));
                            }
                            map.put(columnName, rs.getString(columnName));
                        }
                        list.add(map);
                        map = null;
                    }
                } catch (CannotGetJdbcConnectionException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                } finally {
                    dsClose(rs, stmt, conn);
                }
            } else if (driverType == 5) {
                Connection conn = null;
                CallableStatement stmt = null;
                ResultSet rs = null;
                try {
                    conn = jdbcTemplate.getDataSource().getConnection();
                    stmt = (CallableStatement) conn
                            .prepareCall("{call PROC_GET_DATABYPAGE(?, ?, ?)}");
                    stmt.setString(1, sql);
                    stmt.setInt(2, (start - 1) * limit);
                    stmt.setInt(3, limit);
                    stmt.execute();
                    rs = stmt.getResultSet();
                    list = JdbcUtil.extractData(rs);
                } catch (CannotGetJdbcConnectionException e) {
                    throw e;
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                } finally {
                    dsClose(rs, stmt, conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date endDate = new Date();
        LogFactory.getLogger().info("运行时间："
                + DateUtil.getTimeInMillis(startDate, endDate));
        return list;
    }
    
    /**
     * 通用存储过程调用
     * 
     * @param procName
     *            存储过程名称
     * @param params
     *            参数
     */
    public void callProcedure(String procName, final Object[] params) {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("(");
        for (int i = 0; i < params.length; i++) {
            paramStr.append("?,");
        }
        if (paramStr.length() == 1) {
            paramStr.delete(0, paramStr.length() - 1);
        } else {
            paramStr.delete(paramStr.length() - 1, paramStr.length());
            paramStr.append(")");
        }
        JdbcTemplate jt = jdbcTemplate;
        jt.execute("{call " + procName + paramStr.toString() + "}",
                new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement arg0)
                            throws SQLException, DataAccessException {
                        for (int i = 0; i < params.length; i++) {
                            arg0.setObject(i + 1, params[i]);
                        }
                        arg0.execute();
                        return null;
                    }
                });
    }
    
    /**
     * 日期入库处理
     * 
     * @param timeValue
     * @param timeFormat
     * @return
     */
    public String getDateByDriver(String timeValue, int flag) {
        int driverType = getDriverType();
        String strSql = null;
        if (timeValue != null && !timeValue.equals("")) {
            if (driverType == 2) {
                String timeFormat = "yyyy-mm-dd hh24:mi:ss";
                if (flag == 0) {
                    timeFormat = "yyyy-mm-dd";
                }
                strSql = "to_date('" + timeValue + "', '" + timeFormat + "')";
            } else {
                String timeFormat = "yyyy-MM-dd";
                if (flag == 1) {
                    timeFormat = "yyyy-MM-dd HH:mm:ss";
                    if (timeValue.length() == 10)
                        timeValue = timeValue + " 00:00:00";
                }
                try {
                    SimpleDateFormat df = new SimpleDateFormat(timeFormat);
                    Date ds = df.parse(timeValue);
                    String returnValue = df.format(ds);
                    strSql = "'" + returnValue + "'";
                } catch (ParseException e) {
                    strSql = null;
                }
            }
        }
        return strSql;
    }
    
    /**
     * 获取当前数据库驱动类型
     * 
     * @return 1:Sybase; 2:Oracle; 3:TYPE_SQLSERVER; 4:MySql; 5:DB2
     */
    public int getDriverType() {
        int driverType = 0;
//        try {
//            QueryHelper query = QueryHelper.getDefault("ptx_DS");
//            ConnectionMetaData meta = query.getMetaData();
//            StringBuffer sql = new StringBuffer();
//            sql.append("select 1");
//            if (meta.getDriverType() == ConnectionMetaData.TYPE_SYBASE) {
//                driverType = 1;
//            } else if (meta.getDriverType() == ConnectionMetaData.TYPE_ORACLE) {
//                driverType = 2;
//                sql.append(" from dual");
//            } else if (meta.getDriverType() == ConnectionMetaData.TYPE_SQLSERVER) {
//                driverType = 3;
//            } else if (meta.getDriverType() == ConnectionMetaData.TYPE_MYSQL) {
//                driverType = 4;
//            } else if (meta.getDriverType() == ConnectionMetaData.TYPE_DB2) {
//                driverType = 5;
//                sql.append(" from sysibm.sysdummy1");
//            }
//            query.executeQuery(sql.toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return 2;
    }
    
    /**
     * 获取Connection
     * 
     * @return
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    
    /**
     * 关闭数据库相关对象
     */
    public void dsClose(ResultSet rs, CallableStatement stmt, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}