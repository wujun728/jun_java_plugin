package cn.kiwipeach.model.business;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Create Date: 2017/10/31
 * Description: 员工实体类
 *
 * @author Wujun
 */
public class Employ {
    /**
     * 员工编号
     */
    private BigDecimal empno;
    /**
     * 员工姓名
     */
    private String ename;
    /**
     * 员工工作
     */
    private String job;
    /**
     * 经理
     */
    private Integer mgr;
    /**
     * 雇佣日期
     */
    private Date hiredate;
    /**
     * 薪水
     */
    private double sal;
    /**
     * 奖金
     */
    private double comm;
    /**
     * 部门编号
     */
    private BigDecimal deptno;

    public Employ() {
    }

    public BigDecimal getEmpno() {
        return empno;
    }

    public void setEmpno(BigDecimal empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public BigDecimal getDeptno() {
        return deptno;
    }

    public void setDeptno(BigDecimal deptno) {
        this.deptno = deptno;
    }

}

