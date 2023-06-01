package cn.springmvc.mybatis.common.base.model;

import java.io.Serializable;

/**
 * 
 * 主键生成
 *
 */
public class Key implements Serializable {

    private static final long serialVersionUID = -7682316505620174086L;

    /** 主键 */
    private String id;

    /** 表名 */
    private String tableName;

    /** 机器码 */
    private String machineCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

}