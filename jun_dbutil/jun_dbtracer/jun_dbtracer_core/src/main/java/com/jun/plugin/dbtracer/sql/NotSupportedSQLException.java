package com.jun.plugin.dbtracer.sql;

public class NotSupportedSQLException extends Exception {

    private static final long serialVersionUID = -6049284272898573332L;

    public NotSupportedSQLException(ExceptionType exType, String sql) {
        super("Can not parse the sql,cause:" + exType.getMsg() + "\n sql:" + sql);
    }

    public enum ExceptionType {
        MULTIPLE_UPDATE_SQL("不支持批量更新SQL"),

        PK_NUMBER_OR_STRING_ONLY("主键只支持字符型或者数字型"),

        UPDATE_CONDITION_AND_OR_EQUAL_ONLY("where更新条件只支持主键=或者and操作符"),

        UPDATE_CONDITION_PK_NOT_EXIST("where更新条件不包括PK");

        private String msg;

        ExceptionType(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
