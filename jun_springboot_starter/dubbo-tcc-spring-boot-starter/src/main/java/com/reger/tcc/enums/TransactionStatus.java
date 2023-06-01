package com.reger.tcc.enums;

public enum TransactionStatus {
	create("事务刚创建"),startIng("事务进行中"),commitIng("事务提交中"),commitEd("事务提交完毕"),RollBackIng("事务回滚中"),RollBackEd("事务回滚完毕");
	
	private final String name;

	private TransactionStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
