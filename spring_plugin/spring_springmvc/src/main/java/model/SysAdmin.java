package model;

public class SysAdmin {
    private Integer adminid;

    private Integer roleid;

    private String adminname;

    private String adminpwd;

    private String admintruename;

    private Integer adminstate;

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getAdminpwd() {
        return adminpwd;
    }

    public void setAdminpwd(String adminpwd) {
        this.adminpwd = adminpwd;
    }

    public String getAdmintruename() {
        return admintruename;
    }

    public void setAdmintruename(String admintruename) {
        this.admintruename = admintruename;
    }

    public Integer getAdminstate() {
        return adminstate;
    }

    public void setAdminstate(Integer adminstate) {
        this.adminstate = adminstate;
    }
}