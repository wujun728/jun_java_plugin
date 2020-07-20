package com.jun.plugin.core.utils.bloomFilter.filter;

import com.jun.plugin.core.utils.util.HashUtil;

public class ELFFilter extends AbstractFilter {

	public ELFFilter(long maxValue, int MACHINENUM) {
		super(maxValue, MACHINENUM);
	}
	
	public ELFFilter(long maxValue) {
		super(maxValue);
	}
	
	@Override
	public long hash(String str) {
		return HashUtil.elfHash(str) % size;
	}

}
