package com.zt.entity;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wujun
 * @since 2018-04-06
 */
@KeySequence(value = "seq_dept",clazz = Integer.class)
public class Dept extends Model<Dept> {

    private static final long serialVersionUID = 1L;

    @TableId("DEPTNO")
    private Integer deptno;
    @TableField("DNAME")
    private String dname;
    @TableField("LOC")
    private String loc;

    public Dept() {
    }

    public Dept(Integer deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    protected Serializable pkVal() {
        return this.deptno;
    }

    @Override
    public String toString() {
        return "Dept{" +
        ", deptno=" + deptno +
        ", dname=" + dname +
        ", loc=" + loc +
        "}";
    }
}
