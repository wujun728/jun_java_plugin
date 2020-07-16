package com.jun.plugin.utils2.bloomFilter.filter;

import com.jun.plugin.utils2.util.HashUtil;

public class PJWFilter extends AbstractFilter {

	public PJWFilter(long maxValue, int machineNum) {
		super(maxValue, machineNum);
	}

	public PJWFilter(long maxValue) {
		super(maxValue);
	}

	@Override
	public long hash(String str) {
		return HashUtil.pjwHash(str) % size;
	}

}
