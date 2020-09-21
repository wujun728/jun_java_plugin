package cn.jiangzeyin.database.util;

import java.util.HashMap;
import java.util.List;

/**
 * sql 处理后对象
 *
 * @author jiangzeyin
 */
public class SqlAndParameters {
    private String sql;
    private List<Object> parameters;
    private List<String> cloums;
    private HashMap<String, String> systemMap;

    public List<String> getCloums() {
        return cloums;
    }

    public void setCloums(List<String> cloums) {
        this.cloums = cloums;
    }

    public HashMap<String, String> getSystemMap() {
        return systemMap;
    }

    public void setSystemMap(HashMap<String, String> systemMap) {
        this.systemMap = systemMap;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(CharSequence sql) {
        this.sql = sql.toString();
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
