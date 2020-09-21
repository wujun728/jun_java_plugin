package com.holder.typehelper;

import com.holder.Assert;

public abstract class InstanceCreator {
	@SuppressWarnings("unchecked")
	public static Object createInstance(Class cla) {
		Assert.notNull(cla);
		try {
			return cla.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
