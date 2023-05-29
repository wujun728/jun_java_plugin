package com.jun.plugin.resources.db.select;

import java.util.HashMap;
import java.util.Map;

import com.jun.plugin.resources.utils.ArrayUtils;

/**
 * 查询SQL构造器
 * Created By Hong on 2018/7/31
 **/
public final class Select {

    private StringBuilder builder = new StringBuilder();

    public Select() {
        builder.append("select  ");
    }

    /**
     * 设置表名
     *
     * @param name 表名
     * @return Column构造器
     */
    public SelectColumn table(String name) {
        if (judgeXSS(name)) {
            throw new IllegalArgumentException("Table name error.");
        }
        builder.append("from ")
                .append(name);
        return new SelectColumn(builder);
    }

    /**
     * 构造数据库列
     */
    public static class SelectColumn {

        private final StringBuilder builder;

        public SelectColumn(StringBuilder builder) {
            this.builder = builder;
        }

        /**
         * 指定列
         *
         * @param column 列数组
         * @return Column构造器
         */
        public SelectColumn column(String... column) {
            String var1 = ArrayUtils.toString(column);
            builder.insert(7, var1);
            return this;
        }

        /**
         * 全部列
         *
         * @return Column构造器
         */
        public SelectColumn columnAll() {
            String var1 = "*";
            builder.insert(7, var1);
            return this;
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * 判断参数是否含有攻击串（最简单粗暴的方式）
     *
     * @param value
     * @return
     */

    public boolean judgeXSS(String value) {
        if (value == null || "".equals(value)) {
            return false;
        }
        String xssStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'" +
                "|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|chr|" +
                "mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";
        String[] xssArr = xssStr.split("\\|");
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                return true;
            }
        }
        return false;
    }
}
