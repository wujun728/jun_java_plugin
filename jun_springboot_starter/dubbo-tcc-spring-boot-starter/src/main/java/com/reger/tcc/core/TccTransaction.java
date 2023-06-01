package com.reger.tcc.core;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.reger.tcc.enums.TransactionStatus;

public class TccTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String APP_NAME;
	public static String VERSION;
	/**
	 * 当前事务的id
	 */
	private String id;
	/**
	 * 当前应用的名字
	 */
	private String appName;
	/**
	 * 当前应用的版本号
	 */
	private String version;

	/**
	 * 调用深度
	 */
	private Integer depth;

	/**
	 * 事务的开始时间
	 */
	private Date start;

	/**
	 * 事务提交时间
	 */
	private Date commit;

	/**
	 * 事务回滚时间
	 */
	private Date rollBack;
	/**
	 * 事务回滚附带的消息
	 */
	private String rollBackMessage;

	/**
	 * 事务的结束时间
	 */
	private Date end;

	/**
	 * 当前事务所属的父事务的id
	 */
	private String transactionId;

	/**
	 * 提交操作的名字
	 */
	private String commitName;

	/**
	 * 回滚操作的名字
	 */
	private String rollBackName;
	/**
	 * 事务状态
	 */
	private TransactionStatus status = TransactionStatus.create;

	/**
	 * 事务中拓展的数据，也用于存储
	 */
	private Map<String, Object> expand = new HashMap<String, Object>();

	public static TccTransaction create(String commitName, String rollBackName) {
		return create(APP_NAME, VERSION, commitName, rollBackName);
	}

	public static TccTransaction create(String appName, String version, String commitName, String rollBackName) {
		String transactionId = TccConst.TCC_THREADLOCAL.get();
		Integer depth = TccConst.TCC_DEPTH_THREADLOCAL.get();
		TccTransaction transaction = new TccTransaction();
		String id = UUID.randomUUID().toString().replace("-", "");
		if (StringUtils.isEmpty(transactionId)) {
			transactionId = id;
		}
		transaction.id = id;
		transaction.depth = depth;
		transaction.version = version;
		transaction.appName = appName;
		transaction.commitName = commitName;
		transaction.rollBackName = rollBackName;
		transaction.transactionId = transactionId;
		transaction.status = TransactionStatus.create;
		return transaction;
	}

	public TccTransaction start() {
		this.start = new Date();
		this.status = TransactionStatus.startIng;
		return this;
	}

	public TccTransaction commit() {
		this.commit = new Date();
		this.status = TransactionStatus.commitIng;
		return this;
	}

	public TccTransaction commitEd() {
		this.status = TransactionStatus.commitEd;
		this.end = new Date();
		return this;
	}

	public TccTransaction rollBack(String message) {
		this.status = TransactionStatus.RollBackIng;
		this.rollBack = new Date();
		this.rollBackMessage = message;
		return this;
	}

	public TccTransaction rollBackEd() {
		this.status = TransactionStatus.RollBackEd;
		this.end = new Date();
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getCommit() {
		return commit;
	}

	public void setCommit(Date commit) {
		this.commit = commit;
	}

	public Date getRollBack() {
		return rollBack;
	}

	public void setRollBack(Date rollBack) {
		this.rollBack = rollBack;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public Map<String, Object> getExpand() {
		return expand;
	}

	public void setExpand(Map<String, Object> expand) {
		this.expand = expand;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCommitName() {
		return commitName;
	}

	public void setCommitName(String commitName) {
		this.commitName = commitName;
	}

	public String getRollBackName() {
		return rollBackName;
	}

	public void setRollBackName(String rollBackName) {
		this.rollBackName = rollBackName;
	}

	public String getRollBackMessage() {
		return rollBackMessage;
	}

	public void setRollBackMessage(String rollBackMessage) {
		this.rollBackMessage = rollBackMessage;
	}

}
