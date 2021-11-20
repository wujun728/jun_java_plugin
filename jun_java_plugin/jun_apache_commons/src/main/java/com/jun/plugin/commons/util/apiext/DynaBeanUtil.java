package com.jun.plugin.commons.util.apiext;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.ArrayUtils;

public abstract class DynaBeanUtil {
	public static DynaBean proBean(String... params) {
		DynaBean retbean = new LazyDynaBean();
		if (ArrayUtils.isEmpty(params)) {
			return retbean;
		}
		int len = params.length / 2 + ((params.length % 2 > 0) ? 1 : 0);
		for (int i = 0; i < len; i++) {
			String value = ((2 * i + 1) > params.length) ? ""
					: params[2 * i + 1];
			retbean.set(params[2 * i], value);
		}
		return retbean;
	}
}
