package com.lyx;

/**
 * @author Lenovo
 */
public enum DataSourceLookupKey {

    LOCAL_DATASOURCE("本地数据库"), REMOTE_DATASOURCE("远程数据库"), THREAD_DATASOURCE(
            "THREAD数据库");

    private String value;

    private DataSourceLookupKey(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
