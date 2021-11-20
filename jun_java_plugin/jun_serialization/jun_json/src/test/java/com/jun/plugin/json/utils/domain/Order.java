package com.jun.plugin.json.utils.domain;

import java.util.Date;

public class Order {

    private long id;

    private int traceNo;

    private String createAt;

    private Date updateAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(int traceNo) {
        this.traceNo = traceNo;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", traceNo=" + traceNo +
                ", createAt='" + createAt + '\'' +
                ", updateAt=" + updateAt +
                '}';
    }
}
