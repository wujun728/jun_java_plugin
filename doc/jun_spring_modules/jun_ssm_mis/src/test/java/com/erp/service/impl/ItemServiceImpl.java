package com.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Brand;
import com.erp.model.Item;
import com.erp.service.IItemService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;
@SuppressWarnings("unchecked")
@Service("itemService")
public class ItemServiceImpl implements IItemService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public List<Brand> findBrandList()
	{
		String hql="from Brand t where t.status='A'";
		return publicDao.find(hql);
	}
	
	/* (非 Javadoc) 
	* <p>Title: addBrands</p> 
	* <p>Description:增加品牌 </p> 
	* @param name
	* @return 
	* @see com.erp.service.ItemService#addBrands(java.lang.String) 
	*/
	public boolean addBrands(String name)
	{		
		Integer userId=Constants.getCurrendUser().getUserId();
		Brand b=new Brand();
		b.setCreated(new Date());
		b.setLastmod(new Date());
		b.setStatus(Constants.PERSISTENCE_STATUS);
		b.setName(name);
		b.setCreater(userId);
		b.setModifyer(userId);
		publicDao.save(b);
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public Item findItemByMyid(String myid,Integer suplierId)
	{
		String hql="from Item t where t.status='A' and t.myid='"+myid+"'";
		List<Item> list = publicDao.find(hql);
		if (list!=null&&list.size()!=0)
		{
			Item item = list.get(0);
			item.setImage(null);//不需要图片数据
			if (suplierId!=null&&!"".equals(suplierId))
			{
				String sql="SELECT t.PRICE  from ORDER_PURCHASE_LINE t LEFT JOIN ORDER_PURCHASE op on t.ORDER_PURCHASE_ID=op.ORDER_PURCHASE_ID where op.SUPLIER_ID="+suplierId+"  and   t.MYID='"+myid+"' and   t.LASTMOD=(\n" +
						" SELECT MAX(ot.LASTMOD) FROM ORDER_PURCHASE_LINE ot LEFT JOIN ORDER_PURCHASE tt on tt.ORDER_PURCHASE_ID=ot.ORDER_PURCHASE_ID\n" +
						" where tt.SUPLIER_ID="+suplierId+"  and  ot.STATUS='A' and ot.myid='"+myid+"' GROUP BY ot.myid\n" +
						") \n" +
						"GROUP BY t.LASTMOD";
				List list2 = publicDao.findBySQL(sql);
				if (list2!=null&&list2.size()!=0)
				{
					Double price = (list2.get(0)==null)?0:Double.parseDouble(list2.get(0).toString());
					item.setCost(price);
				}else {
					item.setCost(0.0);
				}
			}else {
				item.setCost(0.0);
			}
				
			return item;
		}else {
			return null;
		}
	}
	
	public List<Item> findItemList(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="from Item t where t.status='A'";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}
	
	public Long getCount(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="select count(*) from Item t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}
	
	public boolean persistenceItem(Item item)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		if (item.getItemId()==null||item.getItemId()==0||"".equals(item.getItemId()))
		{
			item.setCreated(new Date());
			item.setLastmod(new Date());
			item.setCreater(userId);
			item.setModifyer(userId);
			item.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(item);
		}else {
			item.setLastmod(new Date());
			item.setModifyer(userId);
			publicDao.update(item);
		}
		return true;
	}
	
	public boolean delItem(Integer itemId)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		Item i = (Item)publicDao.get(Item.class, itemId);
		i.setLastmod(new Date());
		i.setModifyer(userId);
		i.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(i);
		return true;
	}
}
