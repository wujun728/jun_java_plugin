package model;

import java.util.Date;

public class Courier {
    private Integer courieid;

    private String couriername;

    private String courietype;

    private String couriedesc;

    private Integer couriestate;

    private String couriefrom;

    private String courieto;

    private String couriearrived;

    private Date couriearrivetime;

    public Integer getCourieid() {
        return courieid;
    }

    public void setCourieid(Integer courieid) {
        this.courieid = courieid;
    }

    public String getCouriername() {
        return couriername;
    }

    public void setCouriername(String couriername) {
        this.couriername = couriername;
    }

    public String getCourietype() {
        return courietype;
    }

    public void setCourietype(String courietype) {
        this.courietype = courietype;
    }

    public String getCouriedesc() {
        return couriedesc;
    }

    public void setCouriedesc(String couriedesc) {
        this.couriedesc = couriedesc;
    }

    public Integer getCouriestate() {
        return couriestate;
    }

    public void setCouriestate(Integer couriestate) {
        this.couriestate = couriestate;
    }

    public String getCouriefrom() {
        return couriefrom;
    }

    public void setCouriefrom(String couriefrom) {
        this.couriefrom = couriefrom;
    }

    public String getCourieto() {
        return courieto;
    }

    public void setCourieto(String courieto) {
        this.courieto = courieto;
    }

    public String getCouriearrived() {
        return couriearrived;
    }

    public void setCouriearrived(String couriearrived) {
        this.couriearrived = couriearrived;
    }

    public Date getCouriearrivetime() {
        return couriearrivetime;
    }

    public void setCouriearrivetime(Date couriearrivetime) {
        this.couriearrivetime = couriearrivetime;
    }
}