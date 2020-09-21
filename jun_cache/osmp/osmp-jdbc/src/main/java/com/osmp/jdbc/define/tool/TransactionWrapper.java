/*   
 * Project: OSMP
 * FileName: TransactionWrapper.java
 * version: V1.0
 */
package com.osmp.jdbc.define.tool;

import org.springframework.transaction.TransactionStatus;

/**
 * 
 * Description:
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:37:09上午10:51:30
 */
public class TransactionWrapper {
    private TransactionStatus status;
    private int transactionCounts;
    private boolean rollback = false;
    public TransactionStatus getStatus() {
        return status;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    public int getTransactionCounts() {
        return transactionCounts;
    }
    public void setTransactionCounts(int transactionCounts) {
        this.transactionCounts = transactionCounts;
    }
    public boolean isRollback() {
        return rollback;
    }
    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }
    public void countUp(){
        this.transactionCounts++;
    }
    public void countDown(){
        this.transactionCounts--;
    }
    public TransactionWrapper(TransactionStatus status) {
        this.status = status;
        this.transactionCounts = 1;
        rollback = false;
    }
}
