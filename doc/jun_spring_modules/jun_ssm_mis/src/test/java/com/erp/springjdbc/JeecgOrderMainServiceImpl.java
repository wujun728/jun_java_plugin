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
import com.erp.jee.entity.JeecgOrderCustomEntity;
import com.erp.jee.entity.JeecgOrderMainEntity;
import com.erp.jee.entity.JeecgOrderProductEntity;
import com.erp.jee.page2.JeecgOrderCustomPage;
import com.erp.jee.page2.JeecgOrderMainPage;
import com.erp.jee.page2.JeecgOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOrderMainServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.SQLUtil;
/**   
 * @Title: ServiceImpl
 * @Description: 订单主数据
 * @author Wujun
 * @date 2011-12-31 16:23:01
 * @version V1.0   
 *
 */
@Service("jeecgOrderMainService")
public class JeecgOrderMainServiceImpl extends BaseServiceImpl implements JeecgOrderMainServiceI {

	//SQL 使用JdbcDao
	@Autowired
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOrderMainEntity> jeecgOrderMainEntityDao;
	@Autowired
	private IBaseDao<JeecgOrderCustomEntity> jeecgOrderCustomEntityDao;
	@Autowired
	private IBaseDao<JeecgOrderProductEntity> jeecgOrderProductEntityDao;
	public IBaseDao<JeecgOrderMainEntity> getJeecgOrderMainEntityDao() {
		return jeecgOrderMainEntityDao;
	}
	@Autowired
	public void setJeecgOrderMainEntityDao(IBaseDao<JeecgOrderMainEntity> jeecgOrderMainEntityDao) {
		this.jeecgOrderMainEntityDao = jeecgOrderMainEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOrderMainPage jeecgOrderMainPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOrderMainPage)));
		j.setTotal(total(jeecgOrderMainPage));
		return j;
	}

	private List<JeecgOrderMainPage> getPagesFromEntitys(List<JeecgOrderMainEntity> jeecgOrderMainEntitys) {
		List<JeecgOrderMainPage> jeecgOrderMainPages = new ArrayList<JeecgOrderMainPage>();
		if (jeecgOrderMainEntitys != null && jeecgOrderMainEntitys.size() > 0) {
			for (JeecgOrderMainEntity tb : jeecgOrderMainEntitys) {
				JeecgOrderMainPage b = new JeecgOrderMainPage();
				BeanUtils.copyProperties(tb, b);
				jeecgOrderMainPages.add(b);
			}
		}
		return jeecgOrderMainPages;
	}

	private List<JeecgOrderMainEntity> find(JeecgOrderMainPage jeecgOrderMainPage) {
		String hql = "from JeecgOrderMainEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderMainPage, hql, values);

		if (jeecgOrderMainPage.getSort() != null && jeecgOrderMainPage.getOrder() != null) {
			hql += " order by " + jeecgOrderMainPage.getSort() + " " + jeecgOrderMainPage.getOrder();
		}
		return jeecgOrderMainEntityDao.find(hql, jeecgOrderMainPage.getPage(), jeecgOrderMainPage.getRows(), values);
	}

	private Long total(JeecgOrderMainPage jeecgOrderMainPage) {
		String hql = "select count(*) from JeecgOrderMainEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderMainPage, hql, values);
		return jeecgOrderMainEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOrderMainPage jeecgOrderMainPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		JeecgOrderMainEntity jeecgOrderMainEntity = new JeecgOrderMainEntity();
		BeanUtils.copyProperties(jeecgOrderMainPage, jeecgOrderMainEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOrderMainEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOrderMainPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOrderMainPage.getCcreatedatetimeStart());
		}
		if (jeecgOrderMainPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOrderMainPage.getCcreatedatetimeEnd());
		}
		if (jeecgOrderMainPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOrderMainPage.getCmodifydatetimeStart());
		}
		if (jeecgOrderMainPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOrderMainPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOrderMainPage jeecgOrderMainPage) {
		if (jeecgOrderMainPage.getObid() == null || jeecgOrderMainPage.getObid().trim().equals("")) {
			jeecgOrderMainPage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderMainEntity t = new JeecgOrderMainEntity();
		BeanUtils.copyProperties(jeecgOrderMainPage, t);
		jeecgOrderMainEntityDao.save(t);
	}

	
	/**
	 * 保存：一对多
	 */
	public void addMain(JeecgOrderMainPage jeecgOrderMainPage,List<JeecgOrderCustomPage> jeecgOrderCustomList,List<JeecgOrderProductPage> jeecgOrderProductList)  throws Exception{
		//[1].主表保存
		if (jeecgOrderMainPage.getObid() == null || jeecgOrderMainPage.getObid().trim().equals("")) {
			jeecgOrderMainPage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderMainEntity t = new JeecgOrderMainEntity();
		BeanUtils.copyProperties(jeecgOrderMainPage, t);
		jeecgOrderMainEntityDao.save(t);
		//[2].明细数据保存
		//订单客户明细
		for(JeecgOrderCustomPage page:jeecgOrderCustomList){
			JeecgOrderCustomEntity jeecgOrderCustom = new JeecgOrderCustomEntity();
			BeanUtils.copyProperties(page, jeecgOrderCustom);
			
			//外键设置
			jeecgOrderCustom.setGorderObid(t.getObid());
			//外键设置
			jeecgOrderCustom.setGoOrderCode(t.getGoOrderCode());
			
			jeecgOrderCustom.setObid(UUID.randomUUID().toString());
			jeecgOrderCustomEntityDao.save(jeecgOrderCustom);
		}
		//订单产品明细
		for(JeecgOrderProductPage page:jeecgOrderProductList){
			JeecgOrderProductEntity jeecgOrderProduct = new JeecgOrderProductEntity();
			BeanUtils.copyProperties(page, jeecgOrderProduct);
			
			//外键设置
			jeecgOrderProduct.setGorderObid(t.getObid());
			//外键设置
			jeecgOrderProduct.setGoOrderCode(t.getGoOrderCode());
			
			jeecgOrderProduct.setObid(UUID.randomUUID().toString());
			jeecgOrderProductEntityDao.save(jeecgOrderProduct);
		}
	}
	/**
	 * 修改：一对多
	 */
	public void editMain(JeecgOrderMainPage jeecgOrderMainPage,List<JeecgOrderCustomPage> jeecgOrderCustomList,List<JeecgOrderProductPage> jeecgOrderProductList)  throws Exception{
		//[1].主表保存
		JeecgOrderMainEntity t = jeecgOrderMainEntityDao.get(JeecgOrderMainEntity.class, jeecgOrderMainPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderMainPage, t);
		}
	    
	    
	    //获取参数
		String obid0 = jeecgOrderMainPage.getObid();
		String goOrderCode0 = jeecgOrderMainPage.getGoOrderCode();
		String obid1 = jeecgOrderMainPage.getObid();
		String goOrderCode1 = jeecgOrderMainPage.getGoOrderCode();
	    
	    //-------------------------------------------------------------------
	    //[1].查询明细订单客户明细
	    String hql0 = "from JeecgOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
	    List<JeecgOrderCustomEntity> jeecgOrderCustomOldList = jeecgOrderCustomEntityDao.find(hql0,obid0,goOrderCode0);
	    
	    //[2].删除明细订单客户明细
		String delhql0 = "delete from JeecgOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		jeecgOrderMainEntityDao.executeHql(delhql0,obid0,goOrderCode0);
	    //-------------------------------------------------------------------
	    //[1].查询明细订单产品明细
	    String hql1 = "from JeecgOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
	    List<JeecgOrderProductEntity> jeecgOrderProductOldList = jeecgOrderProductEntityDao.find(hql1,obid1,goOrderCode1);
	    
	    //[2].删除明细订单产品明细
		String delhql1 = "delete from JeecgOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		jeecgOrderMainEntityDao.executeHql(delhql1,obid1,goOrderCode1);
		//-------------------------------------------------------------------
		//[3].明细数据保存
		//订单客户明细
		JeecgOrderCustomEntity jeecgOrderCustomEntity = null;
		for(JeecgOrderCustomPage page:jeecgOrderCustomList){
			for(JeecgOrderCustomEntity c:jeecgOrderCustomOldList){
				if(c.getObid().equals(page.getObid())){
					jeecgOrderCustomEntity = c;
					break;
				}
			}
			//-----------------------------------------------------
			if(jeecgOrderCustomEntity==null){
				jeecgOrderCustomEntity = new JeecgOrderCustomEntity();
			}
			jeecgOrderCustomEntityDao.evict(jeecgOrderCustomEntity);
			BeanUtil.copyBeanNotNull2Bean(page, jeecgOrderCustomEntity);
			jeecgOrderCustomEntity.setObid(UUID.randomUUID().toString());
			//外键设置
			jeecgOrderCustomEntity.setGorderObid(t.getObid());
			jeecgOrderCustomEntity.setGoOrderCode(t.getGoOrderCode());
			    
//			Constants.setUpdateMessage(jeecgOrderCustomEntity);
			jeecgOrderCustomEntityDao.save(jeecgOrderCustomEntity);
			jeecgOrderCustomEntity = null;
			//-----------------------------------------------------
		}
		//订单产品明细
		JeecgOrderProductEntity jeecgOrderProductEntity = null;
		for(JeecgOrderProductPage page:jeecgOrderProductList){
			for(JeecgOrderProductEntity c:jeecgOrderProductOldList){
				if(c.getObid().equals(page.getObid())){
					jeecgOrderProductEntity = c;
					break;
				}
			}
			//-----------------------------------------------------
			if(jeecgOrderProductEntity==null){
				jeecgOrderProductEntity = new JeecgOrderProductEntity();
			}
			jeecgOrderProductEntityDao.evict(jeecgOrderProductEntity);
			BeanUtil.copyBeanNotNull2Bean(page, jeecgOrderProductEntity);
			jeecgOrderProductEntity.setObid(UUID.randomUUID().toString());
			//外键设置
			jeecgOrderProductEntity.setGorderObid(t.getObid());
			jeecgOrderProductEntity.setGoOrderCode(t.getGoOrderCode());
			    
//			Constants.setUpdateMessage(jeecgOrderProductEntity);
			jeecgOrderProductEntityDao.save(jeecgOrderProductEntity);
			jeecgOrderProductEntity = null;
			//-----------------------------------------------------
		}
	}
	
	
	public void update(JeecgOrderMainPage jeecgOrderMainPage) throws Exception {
		JeecgOrderMainEntity t = jeecgOrderMainEntityDao.get(JeecgOrderMainEntity.class, jeecgOrderMainPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderMainPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOrderMainEntity t = jeecgOrderMainEntityDao.get(JeecgOrderMainEntity.class, id);
				if (t != null) {
					jeecgOrderMainEntityDao.delete(t);
						//获取参数
					    String obid0 = t.getObid();
					    String goOrderCode0 = t.getGoOrderCode();
					    String obid1 = t.getObid();
					    String goOrderCode1 = t.getGoOrderCode();
					    //删除明细订单客户明细
						String delhql0 = "delete from JeecgOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
						jeecgOrderMainEntityDao.executeHql(delhql0,obid0,goOrderCode0);
					    //删除明细订单产品明细
						String delhql1 = "delete from JeecgOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
						jeecgOrderMainEntityDao.executeHql(delhql1,obid1,goOrderCode1);
				}
			}
		}
	}

	public JeecgOrderMainEntity get(JeecgOrderMainPage jeecgOrderMainPage) {
		return jeecgOrderMainEntityDao.get(JeecgOrderMainEntity.class, jeecgOrderMainPage.getObid());
	}

	public JeecgOrderMainEntity get(String obid) {
		return jeecgOrderMainEntityDao.get(JeecgOrderMainEntity.class, obid);
	}
	public List<JeecgOrderMainEntity> listAll(JeecgOrderMainPage jeecgOrderMainPage) {
		String hql = "from JeecgOrderMainEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderMainPage, hql, values);
		List<JeecgOrderMainEntity> list = jeecgOrderMainEntityDao.find(hql,values);
		return list;
	}
	
	/**根据主表Key,查询子表明细：订单客户明细*/
	public List<JeecgOrderCustomPage> getJeecgOrderCustomListByFkey(String obid,String goOrderCode) {
		
		List<JeecgOrderCustomPage> rs = new ArrayList<JeecgOrderCustomPage>();
		String hql = "from JeecgOrderCustomEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		List<JeecgOrderCustomEntity> list = jeecgOrderCustomEntityDao.find(hql,obid,goOrderCode);
		
		for(JeecgOrderCustomEntity po:list){
			JeecgOrderCustomPage page = new JeecgOrderCustomPage();
			BeanUtils.copyProperties(po, page);
			rs.add(page);
		}
		return rs;
	}
	/**根据主表Key,查询子表明细：订单产品明细*/
	public List<JeecgOrderProductPage> getJeecgOrderProductListByFkey(String obid,String goOrderCode) {
		
		List<JeecgOrderProductPage> rs = new ArrayList<JeecgOrderProductPage>();
		String hql = "from JeecgOrderProductEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		List<JeecgOrderProductEntity> list = jeecgOrderProductEntityDao.find(hql,obid,goOrderCode);
		
		for(JeecgOrderProductEntity po:list){
			JeecgOrderProductPage page = new JeecgOrderProductPage();
			BeanUtils.copyProperties(po, page);
			rs.add(page);
		}
		return rs;
	}
}
