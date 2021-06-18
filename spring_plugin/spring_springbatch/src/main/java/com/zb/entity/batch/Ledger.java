package com.zb.entity.batch;

import java.io.Serializable;
import java.util.Date;

public class Ledger implements Serializable {

    private static final long serialVersionUID = 330845117663832170L;
    private int id;
    private Date receiptDate;
    private String memberName;
    private String checkNumber;
    private Date checkDate;
    private String paymentType;
    private double depositAmount;
    private double paymentAmount;
    private String comments;

    public Ledger() {
        super();
    }

    public Ledger(int id, Date receiptDate, String memberName,
            String checkNumber, Date checkDate, String paymentType,
            double depositAmount, double paymentAmount, String comments) {
        super();
        this.id = id;
        this.receiptDate = receiptDate;
        this.memberName = memberName;
        this.checkNumber = checkNumber;
        this.checkDate = checkDate;
        this.paymentType = paymentType;
        this.depositAmount = depositAmount;
        this.paymentAmount = paymentAmount;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Ledger [id=" + id + ", receiptDate=" + receiptDate
                + ", memberName=" + memberName + ", checkNumber=" + checkNumber
                + ", checkDate=" + checkDate + ", paymentType=" + paymentType
                + ", depositAmount=" + depositAmount + ", paymentAmount="
                + paymentAmount + ", comments=" + comments + "]";
    }

}
