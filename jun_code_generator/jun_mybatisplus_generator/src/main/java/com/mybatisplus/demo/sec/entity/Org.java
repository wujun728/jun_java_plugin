package com.mybatisplus.demo.sec.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuxzh
 * @since 2018-12-20
 */
@TableName("sec_org")
public class Org extends Model<Org> {

    private static final long serialVersionUID = 1L;

    private String active;

    private String description;

    private String fullname;

    private String name;

    private String type;

    private Integer parentOrg;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Integer parentOrg) {
        this.parentOrg = parentOrg;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Org{" +
        "active=" + active +
        ", description=" + description +
        ", fullname=" + fullname +
        ", name=" + name +
        ", type=" + type +
        ", parentOrg=" + parentOrg +
        "}";
    }
}
