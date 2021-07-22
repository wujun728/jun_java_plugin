package com.jun.plugin.lucene.service;

import com.jun.plugin.lucene.bean.JGoods;
import com.jun.plugin.lucene.bean.Jpage;
import com.jun.plugin.lucene.bean.QueryConfig;

/**
 * Lucene 服务
 * @author Administrator
 *
 */
public interface ILuceneService {
	/**
	 * 创建索引
	 * @param goods
	 */
	public void createIndex(JGoods goods) throws Exception;
	/**
	 * 更新索引
	 * @param goods
	 */
	public void updateIndex(JGoods goods)throws Exception;

	/**
	 * 删除索引
	 * @param goodsId
	 */
	public void deleteIndex(JGoods goodsId)throws Exception;
	/**
	 * 搜索
	 * @param q	查询关键词
	 * @param pageNmber 当前页
	 * @param pageSize 也大小
	 * @return
	 * @throws Exception
	 */
	public Jpage<JGoods> search(String q,int pageNmber,int pageSize) throws Exception;
	/**
	 * 高级搜索
	 * @param queryConfig
	 * @return
	 * @throws Exception
	 */
	public Jpage<JGoods> search(QueryConfig<JGoods> queryConfig)throws Exception;
}
