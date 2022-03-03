package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Brand;
import com.erp.model.Item;
import com.jun.plugin.utils.biz.PageUtil;

public interface IItemService
{

	List<Item> findItemList(Map<String, Object> param, PageUtil pageUtil );

	Long getCount(Map<String, Object> param, PageUtil pageUtil );

	boolean persistenceItem(Item item );

	boolean delItem(Integer itemId );

	List<Brand> findBrandList();

	boolean addBrands(String name );

	Item findItemByMyid(String myid, Integer suplierId );

}
