package com.jun.common.basiccode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.context.support.StaticApplicationContext;

import com.jun.util.SSHFactory;

/**
 * 基础数据辅助类
 * @author Wujun
 *
 */
@SuppressWarnings("unchecked")
public class CodeHelper {
	private static IBasicCodeService basicCodeService;
	public IBasicCodeService getBasicCodeService() {
		return basicCodeService;
	}

	public void setBasicCodeService(IBasicCodeService basicCodeService) {
		this.basicCodeService = basicCodeService;
	}
	
	/**
	 * 缓存装载标志,true表示将会从数据库重新查询所有的基础数据并放入缓存
	 */
	public static boolean CACHE_RELOAD = true;

	/**
	 * 所有基础数据的缓存容器
	 */
	private static Map codeCached = null;

	/**
	 * 缓存所有的基础数据
	 * @return
	 */
	public static Map getCodesInCache() {
		if (codeCached == null || CACHE_RELOAD) {
			codeCached = new HashMap();
			List<BasicCode> list = basicCodeService.findAllBasicCode();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BasicCode code = (BasicCode) iter.next();
				codeCached.put(code.getId().toString(), code);
			}
		}
		//改变装载标志
		CACHE_RELOAD = false;
		return codeCached;
	}

	/**
	 * 根据code获取基础数据列表
	 */
	public List getBasicCodeListByCode(String code) {
		List result = new ArrayList();
		for (Iterator iter = getCodesInCache().values().iterator(); iter.hasNext();) {
			BasicCode coder = (BasicCode) iter.next();
			if (coder.getCode().equalsIgnoreCase(code)) {
				result.add(coder);
			}
		}
		//根据value排序
		Collections.sort(result, new BeanComparator("value"));
		return result;
	}
	
	/**
	 * 根据基础数据的code及value获取基础数据
	 */
	public BasicCode getBasicCodeListByCodeAndValue(String code,String value) {
		for (Iterator iter = getCodesInCache().values().iterator(); iter.hasNext();) {
			BasicCode coder = (BasicCode) iter.next();
			if (coder.getCode().equalsIgnoreCase(code) && coder.getValue().equalsIgnoreCase(value)) {
				return coder;
			}
		}
		return null;
	}
	
	/**
	 * 根据基础数据的code及cName获取基础数据
	 */
	public BasicCode getBasicCodeListByCodeAndCname(String code,String cName) {
		for (Iterator iter = getCodesInCache().values().iterator(); iter.hasNext();) {
			BasicCode coder = (BasicCode) iter.next();
			if (coder.getCode().equalsIgnoreCase(code) && coder.getCname().equalsIgnoreCase(cName)) {
				return coder;
			}
		}
		return null;
	}

	/**
	 * 内容摘要: 根据ID查询基础数据
	 * @param id  基础数据ID
	 * @return BasicCode
	 */
	public BasicCode getBasicCodeByID(String id) {
		return (BasicCode) getCodesInCache().get(id);
	}
}
