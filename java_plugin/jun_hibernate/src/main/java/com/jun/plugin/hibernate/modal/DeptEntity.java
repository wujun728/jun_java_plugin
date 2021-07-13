package com.jun.plugin.hibernate.modal;

import java.util.Objects;

public class DeptEntity {
    private Byte deptno;
    private String deptName;
    private String loc;

    public Byte getDeptno() {
        return deptno;
    }

    public void setDeptno(Byte deptno) {
        this.deptno = deptno;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptEntity that = (DeptEntity) o;
        return Objects.equals(deptno, that.deptno) &&
                Objects.equals(deptName, that.deptName) &&
                Objects.equals(loc, that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptno, deptName, loc);
    }
}
