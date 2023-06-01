package com.reger.tcc.core;

public interface TccConst {

	String TCC_TRANSACTION_ID = "TCC_TRANSACTION_ID";
	String TCC_TRANSACTION_DEPTH = "TCC_TRANSACTION_DEPTH";

	ThreadLocal<String> TCC_THREADLOCAL = new ThreadLocal<String>();
	ThreadLocal<Integer> TCC_DEPTH_THREADLOCAL = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 0;
		};
	};
}
