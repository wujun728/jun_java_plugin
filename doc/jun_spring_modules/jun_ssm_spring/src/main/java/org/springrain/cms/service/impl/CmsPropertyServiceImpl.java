package org.springrain.cms.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.cms.entity.CmsProperty;
import org.springrain.cms.service.ICmsPropertyService;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.service.BaseSpringrainServiceImpl;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2016-11-10 11:55:20
 * @see org.springrain.cms.entity.demo.service.impl.CmsProperty
 */
@Service("cmsPropertyService")
public class CmsPropertyServiceImpl extends BaseSpringrainServiceImpl implements
		ICmsPropertyService {

	@Override
	public String save(Object entity) throws Exception {
		CmsProperty cmsProperty = (CmsProperty) entity;
		return super.save(cmsProperty).toString();
	}

	@Override
	public String saveorupdate(Object entity) throws Exception {
		CmsProperty cmsProperty = (CmsProperty) entity;
		return super.saveorupdate(cmsProperty).toString();
	}

	@Override
	public Integer update(IBaseEntity entity) throws Exception {
		CmsProperty cmsProperty = (CmsProperty) entity;
		return super.update(cmsProperty);
	}

	@Override
	public CmsProperty findCmsPropertyById(String id) throws Exception {
		return super.findById(id, CmsProperty.class);
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		return super.findListDataByFinder(finder, page, clazz, o);
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl,
			Page page, Class<T> clazz, Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	public void saveupdate(CmsProperty en) throws Exception {
		if (StringUtils.isNotBlank(en.getId())) {
			super.update(en);
		} else {
			super.save(en);
		}

	}


	@Override
	public List<CmsProperty> findByBusinessId(String businessId, String state)
			throws Exception {
		if (StringUtils.isBlank(businessId)) {
			return null;
		}
		// 查询本身字段
		Finder finder = Finder.getSelectFinder(CmsProperty.class);
		finder.append(" where businessId=:businessId ").setParam("businessId",
				businessId);
		if (StringUtils.isNotBlank(state)) {
			finder.append(" and state=:state").setParam("state", state);
		}
		finder.append(" order by sortno");
		List<CmsProperty> list = super.queryForList(finder, CmsProperty.class);
		return list;
	}
}
