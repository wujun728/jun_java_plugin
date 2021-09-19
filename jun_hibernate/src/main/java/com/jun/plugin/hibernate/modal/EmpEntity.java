package com.jun.plugin.hibernate.modal;

import java.util.Date;
import java.util.Objects;

public class EmpEntity {
    private Short empno;
    private String empName;
    private String job;
    private Long mgr;
    private Date hiredate;
    private Long sal;
    private Double comm;

    public Short getEmpno() {
        return empno;
    }

    public void setEmpno(Short empno) {
        this.empno = empno;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getMgr() {
        return mgr;
    }

    public void setMgr(Long mgr) {
        this.mgr = mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Long getSal() {
        return sal;
    }

    public void setSal(Long sal) {
        this.sal = sal;
    }

    public Double getComm() {
        return comm;
    }

    public void setComm(Double comm) {
        this.comm = comm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpEntity empEntity = (EmpEntity) o;
        return Objects.equals(empno, empEntity.empno) &&
                Objects.equals(empName, empEntity.empName) &&
                Objects.equals(job, empEntity.job) &&
                Objects.equals(mgr, empEntity.mgr) &&
                Objects.equals(hiredate, empEntity.hiredate) &&
                Objects.equals(sal, empEntity.sal) &&
                Objects.equals(comm, empEntity.comm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, empName, job, mgr, hiredate, sal, comm);
    }
}
