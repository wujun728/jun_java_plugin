package com.github.ghsea.dbtracer.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLParseResult {

    private String table;
    private String querySql;
    private List<String> updateColume;

    private Map<Integer, String> updateColIdx2Name;

    private String where;

    private String key;

    SQLParseResult() {
        updateColume = new ArrayList<String>();
        updateColIdx2Name = new HashMap<Integer, String>();
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public List<String> getUpdateColume() {
        return updateColume;
    }

    public void setUpdateColume(List<String> updateColume) {
        this.updateColume = updateColume;
    }

    public Map<Integer, String> getUpdateColIdx2Name() {
        return updateColIdx2Name;
    }

    public void setUpdateColIdx2Name(Map<Integer, String> updateColIdx2Name) {
        this.updateColIdx2Name = updateColIdx2Name;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
