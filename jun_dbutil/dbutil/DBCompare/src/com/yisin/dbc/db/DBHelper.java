package com.yisin.dbc.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.yisin.dbc.entity.DbTable;
import com.yisin.dbc.entity.DbTableColumn;
import com.yisin.dbc.entity.MysqlDbColumn;
import com.yisin.dbc.util.CommonUtils;
import com.yisin.dbc.util.IniReader;

public class DBHelper {

    private static DBConnection dbconn1 = new DBConnection();
    private static DBConnection dbconn2 = new DBConnection();
    public static String sections = "mysql_conn_info";

    public static String connName1 = "";
    public static String ip1 = "";
    public static String port1 = "";
    public static String schema1 = "";
    public static String encode1 = "utf-8";
    public static String userName1 = "";
    public static String pwd1 = "";
    
    public static String connName2 = "";
    public static String ip2 = "";
    public static String port2 = "";
    public static String schema2 = "";
    public static String encode2 = "utf-8";
    public static String userName2 = "";
    public static String pwd2 = "";
    
    public static DBConnection getDBConn(int state){
        if(state == 1){
            return dbconn1;
        } else {
            return dbconn2;
        }
    }
    
    public static String getSchema(int state){
        if(state == 1){
            return schema1;
        } else {
            return schema2;
        }
    }

    public static void initDbConfig() {
        try {
            IniReader reader = IniReader.getIniReader();
            HashMap<String, HashMap<String, String>> cfg = reader.getConfig(sections);
            if (cfg != null) {
                int state = 1;
                for(Map.Entry<String, HashMap<String, String>> entry : cfg.entrySet()){
                    setConfigValue(entry.getKey(), entry.getValue(), state++);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setConfigValue(String name, HashMap<String, String> cfg, int state) {
        if(state == 1){
            connName1 = name;
            ip1 = cfg.get("host");
            port1 = cfg.get("port");
            schema1 = cfg.get("db_name");
            encode1 = cfg.get("encode");
            userName1 = cfg.get("userName");
            pwd1 = cfg.get("password");
            dbconn1.setDbInfo(ip1, port1, schema1, encode1, userName1, pwd1);
        } else {
            connName2 = name;
            ip2 = cfg.get("host");
            port2 = cfg.get("port");
            schema2 = cfg.get("db_name");
            encode2 = cfg.get("encode");
            userName2 = cfg.get("userName");
            pwd2 = cfg.get("password");
            dbconn2.setDbInfo(ip2, port2, schema2, encode2, userName2, pwd2);
        }
    }

    public static List<DbTable> getDbTables(int state) {
        List<DbTable> tableList = null;
        String sql = "SELECT * FROM information_schema.tables WHERE table_schema = ?";
        try {
            PreparedStatement stmt = getDBConn(state).getPreparedStatement(sql);
            if (stmt != null) {
                stmt.setString(1, getSchema(state));
                ResultSet rs = stmt.executeQuery();
                if (rs != null) {
                    tableList = new ArrayList<DbTable>();
                    DbTable table = null;
                    while (rs.next()) {
                        table = new DbTable();
                        table.setTableSchema(getSchema(state)).setTableName(rs.getString("table_name"))
                                .setTableComment(rs.getString("table_comment"))
                                .setCreateTime(rs.getTimestamp("create_time"));
                        tableList.add(table);
                    }
                }
                getDBConn(state).close(rs, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    public static List<MysqlDbColumn> getTableColumns(String table, int state) {
        List<MysqlDbColumn> columnList = new ArrayList<MysqlDbColumn>();
        String sql = "SELECT DISTINCT * FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name = ? ORDER BY ORDINAL_POSITION";
        try {
            PreparedStatement stmt = getDBConn(state).getPreparedStatement(sql);
            if (stmt != null) {
                stmt.setString(1, getSchema(state));
                stmt.setString(2, table);
                ResultSet rs = stmt.executeQuery();
                if (rs != null) {
                    MysqlDbColumn column = null;
                    while (rs.next()) {
                        column = new MysqlDbColumn();
                        column.setTableSchema(getSchema(state)).setTableName(table).setColumnName(rs.getString("column_name"))
                                .setColumnType(rs.getString("column_type"))
                                .setColumnDefault(rs.getObject("column_default"))
                                .setColumnKey(rs.getString("column_key"))
                                .setColumnComment(rs.getString("column_comment")).setDataType(rs.getString("data_type"))
                                .setCharacterMaximumLength(rs.getLong("character_maximum_length"))
                                .setCharacterOctetLength(rs.getLong("character_octet_length"))
                                .setExtra(rs.getString("extra")).setIsNullable(rs.getString("is_nullable"))
                                .setPrivileges(rs.getString("privileges"))
                                .setOrdinalPosition(rs.getInt("ordinal_position"));
                        columnList.add(column);
                    }
                }
                getDBConn(state).close(rs, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }
    
    public static Map<String, List<DbTableColumn>> getMutilTableColumns(int state) {
    	Map<String, List<DbTableColumn>> tableMap = new HashMap<String, List<DbTableColumn>>();
        String sql = "SELECT DISTINCT * FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name in ("
        		+ "SELECT table_name FROM information_schema.tables WHERE table_schema = ?"
        		+ ") ORDER BY table_name, ORDINAL_POSITION";
        try {
            String schema = getSchema(state);
            PreparedStatement stmt = getDBConn(state).getPreparedStatement(sql);
            if (stmt != null) {
                stmt.setString(1, schema);
                stmt.setString(2, schema);
                ResultSet rs = stmt.executeQuery();
                if (rs != null) {
                    MysqlDbColumn column = null;
                    List<DbTableColumn> columnList = null;
                    String tempName = "", tname = "";;
                    while (rs.next()) {
                        column = new MysqlDbColumn();
                        tname = rs.getString("table_name");
                        if(!tempName.equals(tname)){
                        	if(columnList != null){
                        		tableMap.put(tempName, columnList);
                        	}
                        	tempName = tname;
                        	columnList = new ArrayList<DbTableColumn>();
                        }
                        column.setTableSchema(schema)
                        	.setTableName(tname)
                    		.setColumnName(rs.getString("column_name"))
                            .setColumnType(rs.getString("column_type"))
                            .setColumnDefault(rs.getObject("column_default"))
                            .setColumnKey(rs.getString("column_key"))
                            .setColumnComment(rs.getString("column_comment"))
                            .setDataType(rs.getString("data_type"))
                            .setCharacterMaximumLength(rs.getLong("character_maximum_length"))
                            .setCharacterOctetLength(rs.getLong("character_octet_length"))
                            .setExtra(rs.getString("extra"))
                            .setIsNullable(rs.getString("is_nullable"))
                            .setPrivileges(rs.getString("privileges"))
                            .setOrdinalPosition(rs.getInt("ordinal_position"));
                        columnList.add(column);
                    }
                    if(columnList != null){
                		tableMap.put(tempName, columnList);
                	}
                }
                getDBConn(state).close(rs, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableMap;
    }

    public static Map<String, Object> loadTableData(String sql, int state) throws SQLException {
        Map<String, Object> map = null;
        Vector<Vector<Object>> dataVector = null;
        PreparedStatement stmt = getDBConn(state).getPreparedStatement(sql);
        if (stmt != null) {
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                map = new HashMap<String, Object>();
                dataVector = new Vector<Vector<Object>>();
                Vector<Object> vector = null;
                Vector<Object> column = null;
                Object value = null;
                int size = 0;
                int row = 1;
                while (rs.next()) {
                    vector = new Vector<Object>();
                    vector.add(row);
                    row++;
                    try {
                        if(column == null){
                            column = resultSetMetaDataToVector(rs.getMetaData());
                            size =  column.size();
                        }
                        for (int i = 1; i < size; i++) {
                            value = rs.getObject(CommonUtils.objectToString(column.get(i)));
                            if (value == null) {
                                vector.add("null");
                            } else {
                                vector.add(value);
                            }
                        }
                    } catch (Exception e) {
                    }
                    dataVector.add(vector);
                }
                map.put("dataSet", dataVector);
                map.put("feildSet", column);
            }
            getDBConn(state).close(rs, stmt);
        }
        return map;
    }

    private static Vector<Object> resultSetMetaDataToVector(ResultSetMetaData rsData) {
        Vector<Object> vector = null;
        try {
            int size = rsData.getColumnCount();
            vector = new Vector<Object>();
            vector.add("序号");
            for (int i = 0; i < size; i++) {
                vector.add(rsData.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            vector = new Vector<Object>();
        }
        return vector;
    }

}
