package com.wangxin.entity.mail;

import com.wangxin.entity.BaseEntity;

public class MailAddress extends BaseEntity {

    private static final long serialVersionUID = -4495564603053297179L;

    private String mailType;// 邮件类型：01=运营统计报表

    private String toAddress;// 收件人

    private String toCc;// 抄送

    private String toBcc;// 秘送

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToCc() {
        return toCc;
    }

    public void setToCc(String toCc) {
        this.toCc = toCc;
    }

    public String getToBcc() {
        return toBcc;
    }

    public void setToBcc(String toBcc) {
        this.toBcc = toBcc;
    }

}
