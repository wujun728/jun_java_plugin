package com.erp.springjdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.jee.entity.GbuyOrderCustomEntity;
import com.erp.jee.entity.GbuyOrderEntity;
import com.erp.jee.entity.GbuyOrderProductEntity;
import com.erp.jee.page2.GbuyOrderCustomPage;
import com.erp.jee.page2.GbuyOrderPage;
import com.erp.jee.page2.GbuyOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.GbuyOrderServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.SQLUtil;
/**   
 * @Title: ServiceImpl
 * @Description: 订单抬头
 * @author Wujun
 * @date 2011-12-19 13:09:34
 * @version V1.0   
 *
 */
@Service("gbuyOrderService")
public class GbuyOrderServiceImpl extends BaseServiceImpl implements GbuyOrderServiceI {

	//SQL 使用JdbcDao
	@Autowired
	private JdbcDao jdbcDao;
	private IBaseDao<GbuyOrderEntity> gbuyOrderEntityDao;
	@Autowired
	private IBaseDao<GbuyOrderCustomEntity> gbuyOrderCustomEntityDao;
	@Autowired
	private IBaseDao<GbuyOrderProductEntity> gbuyOrderProductEntityDao;
	public IBaseDao<GbuyOrderEntity> getGbuyOrderEntityDao() {
		return gbuyOrderEntityDao;
	}
	@Autowired
	public void setGbuyOrderEntityDao(IBaseDao<GbuyOrderEntity> gbuyOrderEntityDao) {
		this.gbuyOrderEntityDao = gbuyOrderEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(GbuyOrderPage gbuyOrderPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(gbuyOrderPage)));
		j.setTotal(total(gbuyOrderPage));
		return j;
	}

	private List<GbuyOrderPage> getPagesFromEntitys(List<GbuyOrderEntity> gbuyOrderEntitys) {
		List<GbuyOrderPage> gbuyOrderPages = new ArrayList<GbuyOrderPage>();
		if (gbuyOrderEntitys != null && gbuyOrderEntitys.size() > 0) {
			for (GbuyOrderEntity tb : gbuyOrderEntitys) {
				GbuyOrderPage b = new GbuyOrderPage();
				BeanUtils.copyProperties(tb, b);
				gbuyOrderPages.add(b);
			}
		}
		return gbuyOrderPages;
	}

	private List<GbuyOrderEntity> find(GbuyOrderPage gbuyOrderPage) {
		String hql = "from GbuyOrderEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderPage, hql, values);

		if (gbuyOrderPage.getSort() != null && gbuyOrderPage.getOrder() != null) {
			hql += " order by " + gbuyOrderPage.getSort() + " " + gbuyOrderPage.getOrder();
		}
		return gbuyOrderEntityDao.find(hql, gbuyOrderPage.getPage(), gbuyOrderPage.getRows(), values);
	}

	private Long total(GbuyOrderPage gbuyOrderPage) {
		String hql = "select count(*) from GbuyOrderEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderPage, hql, values);
		return gbuyOrderEntityDao.count(hql, values);
	}

	private String addWhere(GbuyOrderPage gbuyOrderPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		GbuyOrderEntity gbuyOrderEntity = new GbuyOrderEntity();
		BeanUtils.copyProperties(gbuyOrderPage, gbuyOrderEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, gbuyOrderEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (gbuyOrderPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(gbuyOrderPage.getCcreatedatetimeStart());
		}
		if (gbuyOrderPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(gbuyOrderPage.getCcreatedatetimeEnd());
		}
		if (gbuyOrderPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(gbuyOrderPage.getCmodifydatetimeStart());
		}
		if (gbuyOrderPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(gbuyOrderPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(GbuyOrderPage gbuyOrderPage) {
		if (gbuyOrderPage.getObid() == null || gbuyOrderPage.getObid().trim().equals("")) {
			gbuyOrderPage.setObid(UUID.randomUUID().toString());
		}
		GbuyOrderEntity t = new GbuyOrderEntity();
		BeanUtils.copyProperties(gbuyOrderPage, t);
		gbuyOrderEntityDao.save(t);
	}

	
	/**
	 * 保存：一对多
	 */
	public void addMain(GbuyOrderPage gbuyOrderPage,List<GbuyOrderCustomPage> gbuyOrderCustomList,List<GbuyOrderProductPage> gbuyOrderProductList)  throws Exception{
		//[1].主表保存
		if (gbuyOrderPage.getObid() == null || gbuyOrderPage.getObid().trim().equals("")) {
			gbuyOrderPage.setObid(UUID.randomUUID().toString());
		}
		GbuyOrderEntity t = new GbuyOrderEntity();
		BeanUtils.copyProperties(gbuyOrderPage, t);
		gbuyOrderEntityDao.save(t);
		//[2].明细数据保存
		//订单客户明细
		for(GbuyOrderCustomPage page:gbuyOrderCustomList){
			GbuyOrderCustomEntity gbuyOrderCustom = new GbuyOrderCustomEntity();
			BeanUtils.copyProperties(page, gbuyOrderCustom);
			
			//外键设置
			gbuyOrderCustom.setGorderObid(t.getObid());
			//外键设置
			gbuyOrderCustom.setGoOrderCode(t.getGoOrderCode());
			
			gbuyOrderCustom.setObid(UUID.randomUUID().toString());
			gbuyOrderCustomEntityDao.save(gbuyOrderCustom);
		}
		//订单产品明细
		for(GbuyOrderProductPage page:gbuyOrderProductList){
			GbuyOrderProductEntity gbuyOrderProduct = new GbuyOrderProductEntity();
			BeanUtils.copyProperties(page, gbuyOrderProduct);
			
			//外键设置
			gbuyOrderProduct.setGorderObid(t.getObid());
			//外键设置
			gbuyOrderProduct.setGoOrderCode(t.getGoOrderCode());
			
			gbuyOrderProduct.setObid(UUID.randomUUID().toString());
			gbuyOrderProductEntityDao.save(gbuyOrderProduct);
		}
	}
	/**
	 * 修改：一对多
	 */
	public void editMain(GbuyOrderPage gbuyOrderPage,List<GbuyOrderCustomPage> gbuyOrderCustomList,List<GbuyOrderProductPage> gbuyOrderProductList)  throws Exception{
		//[1].主表保存
		GbuyOrderEntity t = gbuyOrderEntityDao.get(GbuyOrderEntity.class, gbuyOrderPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(gbuyOrderPage, t);
		}
	    
	    
	    //获取参数
		String obid0 = gbuyOrderPage.getObid();
		String goOrderCode0 = gbuyOrderPage.getGoOrderCode();
		String obid1 = gbuyOrderPage.getObid();
		String goOrderCode1 = gbuyOrderPage.getGoOrderCode();
	    
	    //-------------------------------------------------------------------
	    //[1].查询明细订单客户明细
	    String hql0 = "from GbuyOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
	    List<GbuyOrderCustomEntity> gbuyOrderCustomOldList = gbuyOrderCustomEntityDao.find(hql0,obid0,goOrderCode0);
	    
	    //[2].删除明细订单客户明细
		String delhql0 = "delete from GbuyOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		gbuyOrderEntityDao.executeHql(delhql0,obid0,goOrderCode0);
	    //-------------------------------------------------------------------
	    //[1].查询明细订单产品明细
	    String hql1 = "from GbuyOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
	    List<GbuyOrderProductEntity> gbuyOrderProductOldList = gbuyOrderProductEntityDao.find(hql1,obid1,goOrderCode1);
	    
	    //[2].删除明细订单产品明细
		String delhql1 = "delete from GbuyOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		gbuyOrderEntityDao.executeHql(delhql1,obid1,goOrderCode1);
		//-------------------------------------------------------------------
		//[3].明细数据保存
		//订单客户明细
		GbuyOrderCustomEntity gbuyOrderCustomEntity = null;
		for(GbuyOrderCustomPage page:gbuyOrderCustomList){
			for(GbuyOrderCustomEntity c:gbuyOrderCustomOldList){
				if(c.getObid().equals(page.getObid())){
					gbuyOrderCustomEntity = c;
					break;
				}
			}
			//-----------------------------------------------------
			if(gbuyOrderCustomEntity==null){
				gbuyOrderCustomEntity = new GbuyOrderCustomEntity();
			}
			gbuyOrderCustomEntityDao.evict(gbuyOrderCustomEntity);
			BeanUtil.copyBeanNotNull2Bean(page, gbuyOrderCustomEntity);
			gbuyOrderCustomEntity.setObid(UUID.randomUUID().toString());
			//外键设置
			gbuyOrderCustomEntity.setGorderObid(t.getObid());
			gbuyOrderCustomEntity.setGoOrderCode(t.getGoOrderCode());
			    
//			Constants.setUpdateMessage(gbuyOrderCustomEntity);
			gbuyOrderCustomEntityDao.save(gbuyOrderCustomEntity);
			gbuyOrderCustomEntity = null;
			//-----------------------------------------------------
		}
		//订单产品明细
		GbuyOrderProductEntity gbuyOrderProductEntity = null;
		for(GbuyOrderProductPage page:gbuyOrderProductList){
			for(GbuyOrderProductEntity c:gbuyOrderProductOldList){
				if(c.getObid().equals(page.getObid())){
					gbuyOrderProductEntity = c;
					break;
				}
			}
			//-----------------------------------------------------
			if(gbuyOrderProductEntity==null){
				gbuyOrderProductEntity = new GbuyOrderProductEntity();
			}
			gbuyOrderProductEntityDao.evict(gbuyOrderProductEntity);
			BeanUtil.copyBeanNotNull2Bean(page, gbuyOrderProductEntity);
			gbuyOrderProductEntity.setObid(UUID.randomUUID().toString());
			//外键设置
			gbuyOrderProductEntity.setGorderObid(t.getObid());
			gbuyOrderProductEntity.setGoOrderCode(t.getGoOrderCode());
			    
//			Constants.setUpdateMessage(gbuyOrderProductEntity);
			gbuyOrderProductEntityDao.save(gbuyOrderProductEntity);
			gbuyOrderProductEntity = null;
			//-----------------------------------------------------
		}
	}
	
	
	public void update(GbuyOrderPage gbuyOrderPage) throws Exception {
		GbuyOrderEntity t = gbuyOrderEntityDao.get(GbuyOrderEntity.class, gbuyOrderPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(gbuyOrderPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				GbuyOrderEntity t = gbuyOrderEntityDao.get(GbuyOrderEntity.class, id);
				if (t != null) {
					gbuyOrderEntityDao.delete(t);
						//获取参数
					    String obid0 = t.getObid();
					    String goOrderCode0 = t.getGoOrderCode();
					    String obid1 = t.getObid();
					    String goOrderCode1 = t.getGoOrderCode();
					    //删除明细订单客户明细
						String delhql0 = "delete from GbuyOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
						gbuyOrderEntityDao.executeHql(delhql0,obid0,goOrderCode0);
					    //删除明细订单产品明细
						String delhql1 = "delete from GbuyOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
						gbuyOrderEntityDao.executeHql(delhql1,obid1,goOrderCode1);
				}
			}
		}
	}

	public GbuyOrderEntity get(GbuyOrderPage gbuyOrderPage) {
		return gbuyOrderEntityDao.get(GbuyOrderEntity.class, gbuyOrderPage.getObid());
	}

	public GbuyOrderEntity get(String obid) {
		return gbuyOrderEntityDao.get(GbuyOrderEntity.class, obid);
	}
	public List<GbuyOrderEntity> listAll(GbuyOrderPage gbuyOrderPage) {
		String hql = "from GbuyOrderEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderPage, hql, values);
		List<GbuyOrderEntity> list = gbuyOrderEntityDao.find(hql,values);
		return list;
	}
	
	/**根据主表Key,查询子表明细：订单客户明细*/
	public List<GbuyOrderCustomPage> getGbuyOrderCustomListByFkey(String obid,String goOrderCode) {
		
		List<GbuyOrderCustomPage> rs = new ArrayList<GbuyOrderCustomPage>();
		String hql = "from GbuyOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		List<GbuyOrderCustomEntity> list = gbuyOrderCustomEntityDao.find(hql,obid,goOrderCode);
		
		for(GbuyOrderCustomEntity po:list){
			GbuyOrderCustomPage page = new GbuyOrderCustomPage();
			BeanUtils.copyProperties(po, page);
			rs.add(page);
		}
		return rs;
	}
	/**根据主表Key,查询子表明细：订单产品明细*/
	public List<GbuyOrderProductPage> getGbuyOrderProductListByFkey(String obid,String goOrderCode) {
		
		List<GbuyOrderProductPage> rs = new ArrayList<GbuyOrderProductPage>();
		String hql = "from GbuyOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		List<GbuyOrderProductEntity> list = gbuyOrderProductEntityDao.find(hql,obid,goOrderCode);
		
		for(GbuyOrderProductEntity po:list){
			GbuyOrderProductPage page = new GbuyOrderProductPage();
			BeanUtils.copyProperties(po, page);
			rs.add(page);
		}
		return rs;
	}
}
