package com.github.ghsea.dbtracer.xml;

import java.util.HashMap;
import java.util.Map;

import com.github.ghsea.dbtracer.Util;

public class TableField {
    /**
     * 中文名
     */
    private String nameEn;

    /**
     * 英文名
     */
    private String nameCn;

    /**
     * 该字段的值与描述间的映射关系
     */
    private Map<String, String> value2Description;

    public TableField() {
        value2Description = new HashMap<String, String>();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Map<String, String> getValue2Description() {
        return value2Description;
    }

    public void setValue2Description(Map<String, String> value2Description) {
        this.value2Description = value2Description;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("nameEn=").append(nameEn).append(",nameCn=").append(nameCn).append(",value2Description=")
                .append(Util.toString(value2Description));
        return sb.toString();
    }

}
