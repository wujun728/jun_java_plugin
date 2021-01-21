package pojo;

import java.util.Date;

public class SysLog {
    private Integer id;

    private String log_title;

    private String log_content;

    private Date createtime;

    private String log_content_detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLog_title() {
        return log_title;
    }

    public void setLog_title(String log_title) {
        this.log_title = log_title;
    }

    public String getLog_content() {
        return log_content;
    }

    public void setLog_content(String log_content) {
        this.log_content = log_content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getLog_content_detail() {
        return log_content_detail;
    }

    public void setLog_content_detail(String log_content_detail) {
        this.log_content_detail = log_content_detail;
    }
}