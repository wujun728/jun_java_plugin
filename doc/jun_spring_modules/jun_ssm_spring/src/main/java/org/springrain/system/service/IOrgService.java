package org.springrain.system.service;

import java.util.List;

import org.springrain.system.entity.Org;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:02:58
 * @see org.springrain.springrain.service.Org
 */
public interface IOrgService extends IBaseSpringrainService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveOrg(Org entity) throws Exception;

	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateOrg(Org entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Org findOrgById(Object id) throws Exception;
	
	/**
	 * 查找Org的树形结构
	 * @return
	 * @throws Exception
	 */
	List<Org> findTreeOrg()throws Exception;
	
	
	/**
	 * 根据父类Id 查找Org的树形结构,根为 null
	 * @return
	 * @throws Exception
	 */
	List<Org> findTreeByPid(String pid)throws Exception;
	
	
	
	
	/**
	 * 删除部门Id以及下面的子部门
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	String deleteOrgById(String orgId)throws Exception;
	
	/**
	 * 根据id和pid生成部门的Comcode
	 * @param id
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	String findOrgNewComcode(String id,String pid) throws Exception ;

	/**
	 * 根据站长id查找cms站点所属的部门
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	Org findCmsOrgByUserId(String userId) throws Exception;
	
	
	
	
	
}
