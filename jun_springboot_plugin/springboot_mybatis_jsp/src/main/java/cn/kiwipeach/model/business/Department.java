package cn.kiwipeach.model.business;

import java.math.BigDecimal;

/**
 * Create Date: 2017/10/31
 * Description: 部门实体类
 *
 * @author Wujun
 */
public class Department {
    /**
     * 部门编号
     */
    private BigDecimal deptno;
    /**
     * 部门名称
     */
    private String dname;
    /**
     * 部门地址
     */
    private String loc;

    public BigDecimal getDeptno() {
        return deptno;
    }

    public void setDeptno(BigDecimal deptno) {
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
}
