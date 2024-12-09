package io.github.wujun728.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * 数据库记录类
 */
public class Record implements Serializable{
    private static final long serialVersionUID = -6199525976049616932L;

    private Map<String, Object> columns = new HashMap<>();

    public Record clear() {
        this.getColumns().clear();
        return this;
    }

    public Record set(String column, Object value) {
        this.columns.put(column, value);
        return this;
    }

    public <T> T get(String column) {
        return (T)this.getColumns().get(column);
    }

    public <T> T get(String column, Object defaultValue) {
        Object result = this.getColumns().get(column);
        return result != null? (T)result: (T)defaultValue;
    }

    public String getStr(String column) {
        return (String)this.getColumns().get(column);
    }

    public Integer getInt(String column) {
        return (Integer)this.getColumns().get(column);
    }

    public Long getLong(String column) {
        return (Long)this.getColumns().get(column);
    }

    public BigInteger getBigInteger(String column) {
        return (BigInteger)this.getColumns().get(column);
    }

    public Date getDate(String column) {
        return (Date)this.getColumns().get(column);
    }

    public Time getTime(String column) {
        return (Time)this.getColumns().get(column);
    }

    public Timestamp getTimestamp(String column) {
        return (Timestamp)this.getColumns().get(column);
    }

    public Double getDouble(String column) {
        return (Double)this.getColumns().get(column);
    }

    public Float getFloat(String column) {
        return (Float)this.getColumns().get(column);
    }

    public Boolean getBoolean(String column) {
        return (Boolean)this.getColumns().get(column);
    }

    public BigDecimal getBigDecimal(String column) {
        return (BigDecimal)this.getColumns().get(column);
    }

    public byte[] getBytes(String column) {
        return (byte[])((byte[])this.getColumns().get(column));
    }

    public Number getNumber(String column) {
        return (Number)this.getColumns().get(column);
    }

    public Map<String, Object> getColumns() {
        return this.columns;
    }

    public Record setColumns(Map<String, Object> columns) {
        this.getColumns().putAll(columns);
        return this;
    }

    public Record setColumns(Record record) {
        this.getColumns().putAll(record.getColumns());
        return this;
    }

    public Record remove(String column) {
        this.getColumns().remove(column);
        return this;
    }

    public Record remove(String... columns) {
        if(columns != null) {
            String[] arr$ = columns;
            int len$ = columns.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String c = arr$[i$];
                this.getColumns().remove(c);
            }
        }

        return this;
    }

    public Record removeNullValueColumns() {
        Iterator it = this.getColumns().entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            if(e.getValue() == null) {
                it.remove();
            }
        }

        return this;
    }

    public String[] getColumnNames() {
        Set attrNameSet = this.getColumns().keySet();
        return (String[])attrNameSet.toArray(new String[attrNameSet.size()]);
    }

    public Object[] getColumnValues() {
        Collection attrValueCollection = this.getColumns().values();
        return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
    }
}
