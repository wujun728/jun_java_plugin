package com.sam.demo.spring.boot.jpa.config;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class AtomikosJtaPlatfom extends AbstractJtaPlatform {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6253215197706127385L;
	private static UserTransaction ut;
    private static TransactionManager tm;
    @Override
    protected TransactionManager locateTransactionManager() {
        return tm;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return ut;
    }

    public UserTransaction getUt() {
        return ut;
    }

    public void setUt(UserTransaction ut) {
        AtomikosJtaPlatfom.ut = ut;
    }

    public TransactionManager getTm() {
        return tm;
    }

    public void setTm(TransactionManager tm) {
        AtomikosJtaPlatfom.tm = tm;
    }
}
