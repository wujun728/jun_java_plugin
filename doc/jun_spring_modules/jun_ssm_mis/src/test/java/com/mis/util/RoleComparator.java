package com.mis.util;

import java.util.Comparator;

import org.apache.log4j.Logger;

import com.erp.model2.Syrole;

/**
 * 角色排序
 * 
 * @author Wujun
 * 
 */
public class RoleComparator implements Comparator<Syrole> {

	private static final Logger logger = Logger.getLogger(RoleComparator.class);

	public int compare(Syrole o1, Syrole o2) {
		int i1 = o1.getSeq() != null ? o1.getSeq().intValue() : -1;
		int i2 = o2.getSeq() != null ? o2.getSeq().intValue() : -1;
		logger.debug("i1[" + i1 + "]-i2[" + i2 + "]=" + (i1 - i2));
		return i1 - i2;
	}

}
