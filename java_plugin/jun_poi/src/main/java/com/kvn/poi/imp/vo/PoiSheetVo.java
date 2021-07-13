package com.kvn.poi.imp.vo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * 作为excel解析结果的载体（单sheet）。<br/>
 * 分为两部分，head头信息 和 body内容
 * @author wzy
 * @date 2017年7月12日 上午9:54:22
 */
public class PoiSheetVo {
	/**
	 * 整个sheet的内容
	 */
	private List<List<Object>> content = Lists.newArrayList();

	public List<Object> getHead() {
		if(CollectionUtils.isEmpty(content)){
			return null;
		}
		
		return content.get(0); // 首行
	}
	
	
	public List<List<Object>> getRawBody(){
		if(CollectionUtils.isEmpty(content) || content.size() <= 1){
			return null;
		}
		// 除第一行以外的内容
		List<List<Object>> rawBody = content.subList(1, content.size() - 1);
		return rawBody;
	}

	public List<List<Object>> getContent() {
		return content;
	}

}
