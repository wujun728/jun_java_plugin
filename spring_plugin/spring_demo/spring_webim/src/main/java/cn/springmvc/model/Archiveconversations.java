package cn.springmvc.model;

import cn.springmvc.common.base.model.BaseModel;

public class Archiveconversations extends BaseModel {

    private static final long serialVersionUID = -5246640962746730468L;

    private Long conversationid;

    private Long starttime;

    private Long endtime;

    private String ownerjid;

    private String ownerresource;

    private String withjid;

    private String withresource;

    private String subject;

    private String thread;

    public Long getConversationid() {
        return conversationid;
    }

    public void setConversationid(Long conversationid) {
        this.conversationid = conversationid;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public String getOwnerjid() {
        return ownerjid;
    }

    public void setOwnerjid(String ownerjid) {
        this.ownerjid = ownerjid;
    }

    public String getOwnerresource() {
        return ownerresource;
    }

    public void setOwnerresource(String ownerresource) {
        this.ownerresource = ownerresource;
    }

    public String getWithjid() {
        return withjid;
    }

    public void setWithjid(String withjid) {
        this.withjid = withjid;
    }

    public String getWithresource() {
        return withresource;
    }

    public void setWithresource(String withresource) {
        this.withresource = withresource;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }
}