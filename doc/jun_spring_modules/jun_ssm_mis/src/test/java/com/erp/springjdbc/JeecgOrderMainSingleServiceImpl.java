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
import com.erp.jee.entity.JeecgOrderCustomSingleEntity;
import com.erp.jee.entity.JeecgOrderMainSingleEntity;
import com.erp.jee.entity.JeecgOrderProductSingleEntity;
import com.erp.jee.page2.JeecgOrderCustomSinglePage;
import com.erp.jee.page2.JeecgOrderMainSinglePage;
import com.erp.jee.page2.JeecgOrderProductSinglePage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOrderMainSingleServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.SQLUtil;
/**   
 * @Title: ServiceImpl
 * @Description: 订单主数据
 * @author Wujun
 * @date 2013-01-18 15:44:12
 * @version V1.0   
 *
 */
@Service("jeecgOrderMainSingleService")
public class JeecgOrderMainSingleServiceImpl extends BaseServiceImpl implements JeecgOrderMainSingleServiceI {

	//SQL 使用JdbcDao
	@Autowired
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOrderMainSingleEntity> jeecgOrderMainSingleEntityDao;
	@Autowired
	private IBaseDao<JeecgOrderCustomSingleEntity> jeecgOrderCustomSingleEntityDao;
	@Autowired
	private IBaseDao<JeecgOrderProductSingleEntity> jeecgOrderProductSingleEntityDao;
	public IBaseDao<JeecgOrderMainSingleEntity> getJeecgOrderMainSingleEntityDao() {
		return jeecgOrderMainSingleEntityDao;
	}
	@Autowired
	public void setJeecgOrderMainSingleEntityDao(IBaseDao<JeecgOrderMainSingleEntity> jeecgOrderMainSingleEntityDao) {
		this.jeecgOrderMainSingleEntityDao = jeecgOrderMainSingleEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOrderMainSinglePage)));
		j.setTotal(total(jeecgOrderMainSinglePage));
		return j;
	}

	private List<JeecgOrderMainSinglePage> getPagesFromEntitys(List<JeecgOrderMainSingleEntity> jeecgOrderMainSingleEntitys) {
		List<JeecgOrderMainSinglePage> jeecgOrderMainSinglePages = new ArrayList<JeecgOrderMainSinglePage>();
		if (jeecgOrderMainSingleEntitys != null && jeecgOrderMainSingleEntitys.size() > 0) {
			for (JeecgOrderMainSingleEntity tb : jeecgOrderMainSingleEntitys) {
				JeecgOrderMainSinglePage b = new JeecgOrderMainSinglePage();
				BeanUtils.copyProperties(tb, b);
				jeecgOrderMainSinglePages.add(b);
			}
		}
		return jeecgOrderMainSinglePages;
	}

	private List<JeecgOrderMainSingleEntity> find(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) {
		String hql = "from JeecgOrderMainSingleEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderMainSinglePage, hql, values);

		if (jeecgOrderMainSinglePage.getSort() != null && jeecgOrderMainSinglePage.getOrder() != null) {
			hql += " order by " + jeecgOrderMainSinglePage.getSort() + " " + jeecgOrderMainSinglePage.getOrder();
		}
		return jeecgOrderMainSingleEntityDao.find(hql, jeecgOrderMainSinglePage.getPage(), jeecgOrderMainSinglePage.getRows(), values);
	}

	private Long total(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) {
		String hql = "select count(*) from JeecgOrderMainSingleEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderMainSinglePage, hql, values);
		return jeecgOrderMainSingleEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOrderMainSinglePage jeecgOrderMainSinglePage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		JeecgOrderMainSingleEntity jeecgOrderMainSingleEntity = new JeecgOrderMainSingleEntity();
		BeanUtils.copyProperties(jeecgOrderMainSinglePage, jeecgOrderMainSingleEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOrderMainSingleEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOrderMainSinglePage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOrderMainSinglePage.getCcreatedatetimeStart());
		}
		if (jeecgOrderMainSinglePage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOrderMainSinglePage.getCcreatedatetimeEnd());
		}
		if (jeecgOrderMainSinglePage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOrderMainSinglePage.getCmodifydatetimeStart());
		}
		if (jeecgOrderMainSinglePage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOrderMainSinglePage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) {
		if (jeecgOrderMainSinglePage.getObid() == null || jeecgOrderMainSinglePage.getObid().trim().equals("")) {
			jeecgOrderMainSinglePage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderMainSingleEntity t = new JeecgOrderMainSingleEntity();
		BeanUtils.copyProperties(jeecgOrderMainSinglePage, t);
		jeecgOrderMainSingleEntityDao.save(t);
	}

	
	/**
	 * 保存：一对多
	 */
	public void addMain(JeecgOrderMainSinglePage jeecgOrderMainSinglePage,List<JeecgOrderCustomSinglePage> jeecgOrderCustomSingleList,List<JeecgOrderProductSinglePage> jeecgOrderProductSingleList)  throws Exception{
		//[1].主表保存
		if (jeecgOrderMainSinglePage.getObid() == null || jeecgOrderMainSinglePage.getObid().trim().equals("")) {
			jeecgOrderMainSinglePage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderMainSingleEntity t = new JeecgOrderMainSingleEntity();
		BeanUtils.copyProperties(jeecgOrderMainSinglePage, t);
		jeecgOrderMainSingleEntityDao.save(t);
		//[2].明细数据保存
		//订单客户明细
		for(JeecgOrderCustomSinglePage page:jeecgOrderCustomSingleList){
			JeecgOrderCustomSingleEntity jeecgOrderCustomSingle = new JeecgOrderCustomSingleEntity();
			BeanUtils.copyProperties(page, jeecgOrderCustomSingle);
			
			//外键设置
			jeecgOrderCustomSingle.setGorderObid(t.getObid());
			//外键设置
			jeecgOrderCustomSingle.setGoOrderCode(t.getGoOrderCode());
			
			jeecgOrderCustomSingle.setObid(UUID.randomUUID().toString());
			jeecgOrderCustomSingleEntityDao.save(jeecgOrderCustomSingle);
		}
		//订单产品明细
		for(JeecgOrderProductSinglePage page:jeecgOrderProductSingleList){
			JeecgOrderProductSingleEntity jeecgOrderProductSingle = new JeecgOrderProductSingleEntity();
			BeanUtils.copyProperties(page, jeecgOrderProductSingle);
			
			//外键设置
			jeecgOrderProductSingle.setGorderObid(t.getObid());
			//外键设置
			jeecgOrderProductSingle.setGoOrderCode(t.getGoOrderCode());
			
			jeecgOrderProductSingle.setObid(UUID.randomUUID().toString());
			jeecgOrderProductSingleEntityDao.save(jeecgOrderProductSingle);
		}
	}
	/**
	 * 修改：一对多
	 */
	public void editMain(JeecgOrderMainSinglePage jeecgOrderMainSinglePage,List<JeecgOrderCustomSinglePage> jeecgOrderCustomSingleList,List<JeecgOrderProductSinglePage> jeecgOrderProductSingleList)  throws Exception{
		//[1].主表保存
		JeecgOrderMainSingleEntity t = jeecgOrderMainSingleEntityDao.get(JeecgOrderMainSingleEntity.class, jeecgOrderMainSinglePage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderMainSinglePage, t);
		}
	    
	    
	    //获取参数
		String obid0 = jeecgOrderMainSinglePage.getObid();
		String goOrderCode0 = jeecgOrderMainSinglePage.getGoOrderCode();
		String obid1 = jeecgOrderMainSinglePage.getObid();
		String goOrderCode1 = jeecgOrderMainSinglePage.getGoOrderCode();
	    
	    //-------------------------------------------------------------------
	    //[1].查询明细订单客户明细
	    String hql0 = "from JeecgOrderCustomSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
	    List<JeecgOrderCustomSingleEntity> jeecgOrderCustomSingleOldList = jeecgOrderCustomSingleEntityDao.find(hql0,obid0,goOrderCode0);
	    
	    //[2].删除明细订单客户明细
		String delhql0 = "delete from JeecgOrderCustomSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		jeecgOrderMainSingleEntityDao.executeHql(delhql0,obid0,goOrderCode0);
	    //-------------------------------------------------------------------
	    //[1].查询明细订单产品明细
	    String hql1 = "from JeecgOrderProductSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
	    List<JeecgOrderProductSingleEntity> jeecgOrderProductSingleOldList = jeecgOrderProductSingleEntityDao.find(hql1,obid1,goOrderCode1);
	    
	    //[2].删除明细订单产品明细
		String delhql1 = "delete from JeecgOrderProductSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		jeecgOrderMainSingleEntityDao.executeHql(delhql1,obid1,goOrderCode1);
		//-------------------------------------------------------------------
		//[3].明细数据保存
		//订单客户明细
		JeecgOrderCustomSingleEntity jeecgOrderCustomSingleEntity = null;
		for(JeecgOrderCustomSinglePage page:jeecgOrderCustomSingleList){
			for(JeecgOrderCustomSingleEntity c:jeecgOrderCustomSingleOldList){
				if(c.getObid().equals(page.getObid())){
					jeecgOrderCustomSingleEntity = c;
					break;
				}
			}
			//-----------------------------------------------------
			if(jeecgOrderCustomSingleEntity==null){
				jeecgOrderCustomSingleEntity = new JeecgOrderCustomSingleEntity();
			}
			jeecgOrderCustomSingleEntityDao.evict(jeecgOrderCustomSingleEntity);
			BeanUtil.copyBeanNotNull2Bean(page, jeecgOrderCustomSingleEntity);
			jeecgOrderCustomSingleEntity.setObid(UUID.randomUUID().toString());
			//外键设置
			jeecgOrderCustomSingleEntity.setGorderObid(t.getObid());
			jeecgOrderCustomSingleEntity.setGoOrderCode(t.getGoOrderCode());
			    
//			Constants.setUpdateMessage(jeecgOrderCustomSingleEntity);
			jeecgOrderCustomSingleEntityDao.save(jeecgOrderCustomSingleEntity);
			jeecgOrderCustomSingleEntity = null;
			//-----------------------------------------------------
		}
		//订单产品明细
		JeecgOrderProductSingleEntity jeecgOrderProductSingleEntity = null;
		for(JeecgOrderProductSinglePage page:jeecgOrderProductSingleList){
			for(JeecgOrderProductSingleEntity c:jeecgOrderProductSingleOldList){
				if(c.getObid().equals(page.getObid())){
					jeecgOrderProductSingleEntity = c;
					break;
				}
			}
			//-----------------------------------------------------
			if(jeecgOrderProductSingleEntity==null){
				jeecgOrderProductSingleEntity = new JeecgOrderProductSingleEntity();
			}
			jeecgOrderProductSingleEntityDao.evict(jeecgOrderProductSingleEntity);
			BeanUtil.copyBeanNotNull2Bean(page, jeecgOrderProductSingleEntity);
			jeecgOrderProductSingleEntity.setObid(UUID.randomUUID().toString());
			//外键设置
			jeecgOrderProductSingleEntity.setGorderObid(t.getObid());
			jeecgOrderProductSingleEntity.setGoOrderCode(t.getGoOrderCode());
			    
//			Constants.setUpdateMessage(jeecgOrderProductSingleEntity);
			jeecgOrderProductSingleEntityDao.save(jeecgOrderProductSingleEntity);
			jeecgOrderProductSingleEntity = null;
			//-----------------------------------------------------
		}
	}
	
	
	public void update(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) throws Exception {
		JeecgOrderMainSingleEntity t = jeecgOrderMainSingleEntityDao.get(JeecgOrderMainSingleEntity.class, jeecgOrderMainSinglePage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderMainSinglePage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOrderMainSingleEntity t = jeecgOrderMainSingleEntityDao.get(JeecgOrderMainSingleEntity.class, id);
				if (t != null) {
					jeecgOrderMainSingleEntityDao.delete(t);
						//获取参数
					    String obid0 = t.getObid();
					    String goOrderCode0 = t.getGoOrderCode();
					    String obid1 = t.getObid();
					    String goOrderCode1 = t.getGoOrderCode();
					    //删除明细订单客户明细
						String delhql0 = "delete from JeecgOrderCustomSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
						jeecgOrderMainSingleEntityDao.executeHql(delhql0,obid0,goOrderCode0);
					    //删除明细订单产品明细
						String delhql1 = "delete from JeecgOrderProductSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
						jeecgOrderMainSingleEntityDao.executeHql(delhql1,obid1,goOrderCode1);
				}
			}
		}
	}

	public JeecgOrderMainSingleEntity get(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) {
		return jeecgOrderMainSingleEntityDao.get(JeecgOrderMainSingleEntity.class, jeecgOrderMainSinglePage.getObid());
	}

	public JeecgOrderMainSingleEntity get(String obid) {
		return jeecgOrderMainSingleEntityDao.get(JeecgOrderMainSingleEntity.class, obid);
	}
	public List<JeecgOrderMainSingleEntity> listAll(JeecgOrderMainSinglePage jeecgOrderMainSinglePage) {
		String hql = "from JeecgOrderMainSingleEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderMainSinglePage, hql, values);
		List<JeecgOrderMainSingleEntity> list = jeecgOrderMainSingleEntityDao.find(hql,values);
		return list;
	}
	
	/**根据主表Key,查询子表明细：订单客户明细*/
	public List<JeecgOrderCustomSinglePage> getJeecgOrderCustomSingleListByFkey(String obid,String goOrderCode) {
		
		List<JeecgOrderCustomSinglePage> rs = new ArrayList<JeecgOrderCustomSinglePage>();
		String hql = "from JeecgOrderCustomSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		List<JeecgOrderCustomSingleEntity> list = jeecgOrderCustomSingleEntityDao.find(hql,obid,goOrderCode);
		
		for(JeecgOrderCustomSingleEntity po:list){
			JeecgOrderCustomSinglePage page = new JeecgOrderCustomSinglePage();
			BeanUtils.copyProperties(po, page);
			rs.add(page);
		}
		return rs;
	}
	/**根据主表Key,查询子表明细：订单产品明细*/
	public List<JeecgOrderProductSinglePage> getJeecgOrderProductSingleListByFkey(String obid,String goOrderCode) {
		
		List<JeecgOrderProductSinglePage> rs = new ArrayList<JeecgOrderProductSinglePage>();
		String hql = "from JeecgOrderProductSingleEntity where 1 = 1 AND gorderObid = ?  AND goOrderCode = ? ";
		List<JeecgOrderProductSingleEntity> list = jeecgOrderProductSingleEntityDao.find(hql,obid,goOrderCode);
		
		for(JeecgOrderProductSingleEntity po:list){
			JeecgOrderProductSinglePage page = new JeecgOrderProductSinglePage();
			BeanUtils.copyProperties(po, page);
			rs.add(page);
		}
		return rs;
	}
}
