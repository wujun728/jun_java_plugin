package cn.springmvc.model;

import cn.springmvc.common.base.model.BaseModel;

public class Archivemessages extends BaseModel {

    private static final long serialVersionUID = 577999890554017584L;

    private Long messageid;

    private Long time;

    private String direction;

    private String type;

    private String subject;

    private Long conversationid;

    private String body;

    private String ownerJid;

    private String withJid;

    public Long getMessageid() {
        return messageid;
    }

    public void setMessageid(Long messageid) {
        this.messageid = messageid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getConversationid() {
        return conversationid;
    }

    public void setConversationid(Long conversationid) {
        this.conversationid = conversationid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOwnerjid() {
        return ownerJid;
    }

    public void setOwnerjid(String ownerJid) {
        this.ownerJid = ownerJid;
    }

    public String getWithjid() {
        return withJid;
    }

    public void setWithjid(String withJid) {
        this.withJid = withJid;
    }

}