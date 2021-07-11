package com.km.db;

import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * <p>db操作类</p>
 * Created by zhezhiyong@163.com on 2017/9/25.
 */
public class DBOperation {

    private DruidPooledConnection con = null;//数据库连接

    private void open() throws SQLException {
        con = DBPoolConnection.getInstance().getConnection();
    }

    public void close() {
        if (this.con != null) {
            try {
                this.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * sql语句参数转化
     *
     * @param sql    sql语句
     * @param params 参数
     * @throws SQLException           sql异常
     * @throws ClassNotFoundException sql异常
     */
    private PreparedStatement setPres(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        if (null == params || params.size() < 1) {
            return null;
        }
        PreparedStatement pres = this.con.prepareStatement(sql);
        for (int i = 1; i <= params.size(); i++) {
            if (params.get(i) == null) {
                pres.setString(i, "");
            } else if (params.get(i).getClass() == Class.forName("java.lang.String")) {
                pres.setString(i, params.get(i).toString());
            } else if (params.get(i).getClass() == Class.forName("java.lang.Integer")) {
                pres.setInt(i, (Integer) params.get(i));
            } else if (params.get(i).getClass() == Class.forName("java.lang.Long")) {
                pres.setLong(i, (Long) params.get(i));
            } else if (params.get(i).getClass() == Class.forName("java.lang.Double")) {
                pres.setDouble(i, (Double) params.get(i));
            } else if (params.get(i).getClass() == Class.forName("java.lang.Flaot")) {
                pres.setFloat(i, (Float) params.get(i));
            } else if (params.get(i).getClass() == Class.forName("java.lang.Boolean")) {
                pres.setBoolean(i, (Boolean) params.get(i));
            } else if (params.get(i).getClass() == Class.forName("java.sql.Date")) {
                pres.setDate(i, java.sql.Date.valueOf(params.get(i).toString()));
            } else {
                return null;
            }
        }
        return pres;
    }

    /**
     * @param sql sql语句
     */
    public int executeUpdate(String sql) throws SQLException {
        this.open();
        Statement state = this.con.createStatement();
        return state.executeUpdate(sql);
    }

    /**
     * @param sql    sql语句
     * @param params 参数
     */
    public int executeUpdate(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        this.open();
        PreparedStatement pres = setPres(sql, params);
        if (null == pres) {
            return 0;
        }
        return pres.executeUpdate();
    }

    /**
     * @param sql sql语句
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        this.open();
        Statement state = this.con.createStatement();
        return state.executeQuery(sql);
    }

    /**
     * @param sql sql语句
     */
    public ResultSet executeQuery(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        this.open();
        PreparedStatement pres = setPres(sql, params);
        if (null == pres) {
            return null;
        }
        return pres.executeQuery();
    }


}
