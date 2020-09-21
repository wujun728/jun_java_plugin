package com.holder;

public final class Assert {
	private static final String MESSAGE_NOT_NULL = " the argument may not be null;";

	private Assert() {

	}

	public static void notNull(Object o) {
		if (o == null) {
			throw new IllegalArgumentException(MESSAGE_NOT_NULL);
		}
	}

	public static void notNull(Object... os) {
		for (Object o : os) {
			notNull(o);
		}
	}
	
	public static void notNull(Object o, String name){
		if (o == null) {
			throw new IllegalArgumentException(MESSAGE_NOT_NULL+" param name : "+name);
		}
	}
}
