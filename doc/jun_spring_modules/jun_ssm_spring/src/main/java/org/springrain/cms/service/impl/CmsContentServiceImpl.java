package org.springrain.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.cms.directive.CmsContentListDirective;
import org.springrain.cms.entity.CmsChannelContent;
import org.springrain.cms.entity.CmsContent;
import org.springrain.cms.entity.CmsLink;
import org.springrain.cms.entity.CmsProperty;
import org.springrain.cms.service.ICmsChannelService;
import org.springrain.cms.service.ICmsContentService;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.cms.service.ICmsPropertyService;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.cms.util.ContentConstant;
import org.springrain.cms.util.DirectiveUtils;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.Enumerations.OrgType;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.LuceneUtils;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.User;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.ITableindexService;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;




/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:19
 * @see org.springrain.cms.entity.demo.service.impl.CmsContent
 */
@Service("cmsContentService")
public class CmsContentServiceImpl extends BaseSpringrainServiceImpl implements ICmsContentService {

	@Resource
	private ITableindexService tableindexService;
	
	@Resource
	private ICmsSiteService cmsSiteService;
	
	@Resource
	private ICmsLinkService cmsLinkService;
	@Resource
	private ICmsChannelService cmsChannelService;
	@Resource
	private ICmsPropertyService cmsPropertyService;
	

	

	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object queryBean) throws Exception {
		CmsContent q=(CmsContent) queryBean;
		
	   finder=new Finder("SELECT c.*, re.siteId siteId,re.channelId channelId,link.link link  FROM ");
	   finder.append(Finder.getTableName(CmsContent.class)).append(" c,")
	         .append(Finder.getTableName(CmsChannelContent.class)).append(" re,")
	         .append(Finder.getTableName(CmsLink.class)).append(" link ");
	   
	   finder.append(" WHERE c.id=re.contentId and c.id=link.businessId and  re.siteId=link.siteId ");
	   q.setFrameTableAlias("c");
	   
	   super.getFinderWhereByQueryBean(finder, q);
	   
	   finder.append(" order by c.createDate desc ");
	   
	   
	   super.queryForList(finder, clazz, page);
	   
		
		return (List<T>) super.queryForList(finder, clazz, page);
	}
	
    @Override
	public String  saveContent(CmsContent cmsContent ) throws Exception{
    	if(cmsContent==null){
    		return null;
    	}
    	String siteId=cmsContent.getSiteId();
    	if(StringUtils.isBlank(siteId)){
    		return null;
    	}
    	
    	Integer siteType=cmsSiteService.findSiteTypeById(cmsContent.getSiteId());
    	if(siteType==null){
         	return null;
    	}
	   String id= tableindexService.updateNewId(CmsContent.class);
	 
	   // 创建人
	   if(StringUtils.isBlank(cmsContent.getCreatePerson())){
		   cmsContent.setCreatePerson(SessionUser.getUserId());
	   }
	   
	   cmsContent.setId(id);
	   cmsContent.setCreateDate(new Date());
	   if(cmsContent.getSortno() == null) {
        cmsContent.setSortno(Integer.parseInt(id.substring(2)));
    }
	   super.save(cmsContent);
	   try {
		   LuceneUtils.saveDocument(getLuceneDir(siteId), cmsContent);
	   } catch (Exception e) {
		   logger.error(e.getMessage(),e);
	   }
	   
	   
	   List<CmsProperty> propertyList = cmsContent.getPropertyList();
	   if(CollectionUtils.isNotEmpty(propertyList)){//有扩展属性
		   int listSize = propertyList.size();
		   for (int i = 0; i < listSize; i++) {
			 
			   CmsProperty cmsProperty = propertyList.get(i);
			   String pvalue = cmsProperty.getPvalue();
			   CmsProperty tmpProperty = cmsPropertyService.findCmsPropertyById(cmsProperty.getId());
			   
			   if(tmpProperty==null){
				   continue;
			   }
			   
			   
			   //BeanUtils.copyProperties(cmsProperty, tmpProperty);
			   tmpProperty.setId(null);
			   tmpProperty.setBusinessId(id);
			   tmpProperty.setPvalue(pvalue);
			   propertyList.set(i, tmpProperty);
			   
			   
		   }
	   }
	   cmsPropertyService.save(propertyList);
	   
	   //保存中间对应
	   CmsChannelContent ccc=new CmsChannelContent();
	   ccc.setChannelId(cmsContent.getChannelId());
	   ccc.setSortno(cmsContent.getSortno());
	   ccc.setActive(cmsContent.getActive());
	   ccc.setContentId(id);
	   ccc.setSiteId(cmsContent.getSiteId());
	   super.save(ccc);
	   
	   
	   //保存 相应的 link 链接
	    CmsLink cmsLink=new CmsLink();
	    
	    cmsLink.setBusinessId(id);
	    cmsLink.setSiteId(cmsContent.getSiteId());
	    cmsLink.setName(cmsContent.getTitle());
	    cmsLink.setJumpType(0);
	    cmsLink.setLookcount(1);
	    cmsLink.setStatichtml(0);//默认不静态化
	    cmsLink.setActive(cmsContent.getActive());//默认可以使用
	    cmsLink.setSortno(cmsContent.getSortno());
	    cmsLink.setModelType(Enumerations.CmsLinkModeType.前台内容.getType());
	    //首页默认
	    String _index="/f/"+OrgType.getOrgType(siteType).name()+"/"+siteId+"/"+id;
	    cmsLink.setDefaultLink(_index);
	    cmsLink.setLink(_index);
	    //设置模板路径
	    //此处生成的为微信端前台的ftl
	    CmsLink cmsLinks = cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, cmsContent.getChannelId(),Enumerations.CmsLinkModeType.站长后台列表.getType());
	    cmsLink.setFtlfile(cmsLinks.getNodeftlfile());
	    cmsLink.setLoginuser(cmsContent.getLoginuser());
	    cmsLinkService.save(cmsLink);
	    
	    //清除缓存
	    super.cleanCache(cmsContent.getSiteId());
	   
	    //添加新缓存
	    return id;
	}

  
	private String getLuceneDir(String siteId) {
		return GlobalStatic.rootDir+"/lucene/"+siteId;
	}

	@Override
    public Integer updateCmsContent(CmsContent cmsContent) throws Exception{
		if(cmsContent==null){
    		return null;
    	}
		
		CmsLink link = cmsLinkService.findLinkBySiteBusinessIdModelType(cmsContent.getSiteId(), cmsContent.getId(),Enumerations.CmsLinkModeType.前台内容.getType());
		if(link!=null){
			link.setLoginuser(cmsContent.getLoginuser());
			cmsLinkService.update(link);
			String cacheKey="findLinkBySiteBusinessId_"+cmsContent.getSiteId()+"_"+cmsContent.getId();
			super.evictByKey(cmsContent.getSiteId(), cacheKey);
		}
		Integer update = super.update(cmsContent,true);
		try {
			LuceneUtils.updateDocument(getLuceneDir(cmsContent.getSiteId()), CmsContent.class);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	    
		List<CmsProperty> propertyList = cmsContent.getPropertyList();
		if(CollectionUtils.isNotEmpty(propertyList)){//有扩展属性
			
			for (CmsProperty cmsProperty : propertyList) {
				if(StringUtils.isBlank(cmsProperty.getPvalue())) {
                    cmsProperty.setPvalue("");
                }
			}
			
			cmsPropertyService.update(propertyList,true);
		}
		
		
		
	    super.cleanCache(cmsContent.getSiteId());
	    
	    
	    return update;
    }
    @Override
	public CmsContent findCmsContentById(String siteId,String id) throws Exception{
		if(StringUtils.isBlank(siteId)||StringUtils.isBlank(id)){
			return null;
		}
		
		String key="cmsContentService_findCmsContentById_"+id;
			
		CmsContent content= getByCache(siteId, key, CmsContent.class);
			
		if(content!=null){
			return content;
		}
			
		content= findById(id,CmsContent.class);
			
		putByCache(siteId, key, content);
		return content;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmsContent> findListBySiteId(String siteId, Page page) throws Exception {
		if(page.getPageIndex() == 1){
			List<CmsContent> contentList = getByCache(siteId, "cmsContentService_findListBySiteId_"+siteId, List.class);
			if(CollectionUtils.isEmpty(contentList)){
				Finder finder = new Finder("SELECT c.*,d.link FROM cms_site a INNER JOIN cms_channel_content b ON a.id=b.siteId INNER JOIN cms_content c ON c.id=b.contentId INNER JOIN cms_link d ON d.businessId = c.id WHERE a.id=:siteId");
				finder.setParam("siteId", siteId);
				contentList = super.queryForList(finder, CmsContent.class, page);
				putByCache(siteId, "cmsContentService_findListBySiteId_"+siteId, contentList);
			}
			return contentList;
		}else{
			Finder finder = new Finder("SELECT c.*,d.link FROM cms_site a INNER JOIN cms_channel_content b ON a.id=b.siteId INNER JOIN cms_content c ON c.id=b.contentId INNER JOIN cms_link d ON d.businessId = c.id WHERE a.id=:siteId");
			finder.setParam("siteId", siteId);
			return super.queryForList(finder, CmsContent.class, page);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmsContent> findContentByChannelId(String siteId,String channelId, Page page) throws Exception {
		List<CmsContent> contentList;
	
		String cacheKey="cmsContentService_findContentByChannelId_"+siteId+"_"+channelId;
		
		if(page.getPageIndex()==1){
			contentList = getByCache(siteId, cacheKey, List.class,page);
			if(CollectionUtils.isNotEmpty(contentList)){
				return contentList;
			}
		}
		
		Finder finder = new Finder("SELECT c.*,d.link FROM cms_channel a INNER JOIN cms_channel_content b ON a.id=b.channelId INNER JOIN cms_content c ON c.id=b.contentId INNER JOIN cms_link d ON c.id=d.businessId WHERE a.id=:channelId");
		finder.setParam("channelId", channelId);
		contentList = super.queryForList(finder, CmsContent.class, page);
	
		putByCache(siteId, cacheKey, contentList,page);
		
		return contentList;
	}

	@Override
	public void deleteById(String id, String siteId) throws Exception {
		super.cleanCache(siteId);
		
		// 逻辑删除
		Finder finder = Finder.getUpdateFinder(CmsContent.class);
		finder.append(" active = 0 where id = :id ").setParam("id", id);
		super.update(finder);
		
		// 逻辑删除cms_link 
		Finder finder_cms_link = Finder.getUpdateFinder(CmsLink.class);
		finder_cms_link.append(" active = 0 where siteId = :siteId and businessId = :businessId ")
			.setParam("siteId", siteId).setParam("businessId", id);
		super.update(finder_cms_link);
		
		/*//清除缓存
	    super.cleanCache(siteId);
		super.deleteById(id, CmsContent.class);
		
		// 删除cms_link中的链接
		Finder finder = Finder.getSelectFinder(CmsLink.class);
		finder.append(" where siteId = :siteId and businessId = :businessId ")
			.setParam("siteId", siteId).setParam("businessId", id);
		List<CmsLink> cmsLinkList = super.queryForList(finder, CmsLink.class);
		if(cmsLinkList!=null && cmsLinkList.size()>0){
			List<String> ids = new ArrayList<>();
			for(CmsLink cl : cmsLinkList){
				ids.add(cl.getId());
			}
			super.deleteByIds(ids, CmsLink.class);
		}
		
		
		LuceneUtils.deleteDocument(getLuceneDir(siteId), id, CmsContent.class);*/
	}
	@Override
	public void deleteByIds(List<String> ids,String siteId) throws Exception {
		super.cleanCache(siteId);
		
		// 逻辑删除
		Finder finder = Finder.getUpdateFinder(CmsContent.class);
		finder.append(" active = 0 where id in (:ids) ").setParam("ids", ids);
		super.update(finder);
		
		// 逻辑删除cms_link 
		Finder finder_cms_link = Finder.getUpdateFinder(CmsLink.class);
		finder_cms_link.append(" active = 0 where siteId = :siteId and businessId in (:businessIds) ")
			.setParam("siteId", siteId).setParam("businessIds", ids);
		super.update(finder_cms_link);
		
//		super.deleteByIds(ids, CmsContent.class);
//		LuceneUtils.deleteListDocument(getLuceneDir(siteId), ids, CmsContent.class);
	}

	@Override
	public List<CmsContent> findListByIdsForTag(List<String> idList, int orderBy) throws Exception{
		Finder finder = Finder.getSelectFinder(CmsContent.class);
		finder.append(" where id in (:ids)").setParam("ids", idList);
		finder.append(getOrderSql(orderBy));
		
		List<CmsContent> cmsContentList = super.queryForList(finder,CmsContent.class);
		// 创建人设置
		if(cmsContentList!=null && cmsContentList.size()>0){
			List<String> createUserIdList = new ArrayList<>();
			for(CmsContent cm : cmsContentList){
				if(!createUserIdList.contains(cm.getCreatePerson())){
					createUserIdList.add(cm.getCreatePerson());
				}
			}
			if(createUserIdList.size()>0){
				Finder finder_c = Finder.getSelectFinder(User.class);
				finder_c.append(" where id in (:ids)").setParam("ids", createUserIdList);
				List<User> userList = super.queryForList(finder_c, User.class);
				for(CmsContent cm : cmsContentList){
					for(User user : userList){
						if(user.getId().equals(cm.getCreatePerson())){
							cm.setCreatePersonName(user.getName());
						}
					}
				}
			}
		}
		
		return cmsContentList;
	}
	
	@Override
	public List<CmsContent> findListForTag(Map params, Environment env, String siteId,String channelId) throws Exception {
		Finder finder = Finder.getSelectFinder(CmsContent.class);
		finder.append(" where 1=1 ");
		if(StringUtils.isNotBlank(siteId) && StringUtils.isNotBlank(channelId)){
			finder.append(" and id in ( select contentId from ").append(Finder.getTableName(CmsChannelContent.class))
				.append(" where siteId = :siteId and channelId = :channelId )")
				.setParam("siteId", siteId).setParam("channelId", channelId);
		}else{
			if(StringUtils.isNotBlank(siteId)){
				finder.append(" and id in ( select contentId from ").append(Finder.getTableName(CmsChannelContent.class))
					.append(" where siteId = :siteId )").setParam("siteId", siteId);
			}
			if(StringUtils.isNotBlank(channelId)){
				finder.append(" and id in ( select contentId from ").append(Finder.getTableName(CmsChannelContent.class))
					.append(" where channelId = :channelId )").setParam("channelId", channelId);
			}
		}
		
		// 常用参数（表与实体类直接对应的字段）
		CmsContent ccParams = new CmsContent();
		BeanUtils.populate(ccParams, params);
		
//		if(StringUtils.isNotBlank(ccParams.getChannelId())){
//			finder.append(" and channelId=:channelId").setParam("channelId", ccParams.getChannelId());
//		}
//		finder.append(")");
		super.getFinderWhereByQueryBean(finder, ccParams);
		
		// 分页
		StringModel stringModel = (StringModel) params.get("page");
		Page page = null;
		if(stringModel!=null){
			page = (Page) stringModel.getAdaptedObject(Page.class);
			
			// 修改分页大小
			Integer pageSize = DirectiveUtils.getInt("pageSize", params);
			if(pageSize!=null){
				page.setPageSize(pageSize);
			}
			
			String pageSort = DirectiveUtils.getString("pageSort", params);
			if(pageSort != null) {
                page.setSort(pageSort);
            }
			
			String pageOrder = DirectiveUtils.getString("pageOrder", params);
			if(pageOrder != null) {
                page.setOrder(pageOrder);
            }
			
		}
		
		// 排序
		//finder.append(getOrderSql(CmsContentListDirective.getOrderBy(params)));
		
		List<CmsContent> cmsContentList = super.queryForList(finder, CmsContent.class, page);
		
		// 创建人设置
		if(cmsContentList!=null && cmsContentList.size()>0){
			List<String> createUserIdList = new ArrayList<>();
			for(CmsContent cm : cmsContentList){
				if(!createUserIdList.contains(cm.getCreatePerson())){
					createUserIdList.add(cm.getCreatePerson());
				}
			}
			if(createUserIdList.size()>0){
				Finder finder_c = Finder.getSelectFinder(User.class);
				finder_c.append(" where id in (:ids)").setParam("ids", createUserIdList);
				List<User> userList = super.queryForList(finder_c, User.class);
				for(CmsContent cm : cmsContentList){
					for(User user : userList){
						if(user.getId().equals(cm.getCreatePerson())){
							cm.setCreatePersonName(user.getName());
						}
					}
				}
			}
		}
		
		return cmsContentList;
	}

	/**
	 * 获取排序sql
	 * @param orderBy
	 * @return
	 */
	private String getOrderSql(int orderBy){
		String orderSql = "";
		switch (orderBy) {
		case 0: // 默认排序
			orderSql = " order by sortno ";
			break;
		case 1: // title 标题 升序
			orderSql = " order by title asc ";
			break;
		case 2: // title 标题 降序
			orderSql = " order by title desc ";
			break;
		case 3: // lookcount 打开次数 升序
			orderSql = " order by lookcount asc ";
			break;
		case 4: // lookcount 打开次数 降序
			orderSql = " order by lookcount desc ";
			break;
		case 5: // createDate 创建时间 升序
			orderSql = " order by createDate asc ";
			break;
		case 6: // createDate 创建时间 降序
			orderSql = " order by createDate desc ";
			break;
		case 7: // status 审核状态 升序
			orderSql = " order by status asc ";
			break;
		case 8: // status 审核状态 降序
			orderSql = " order by status desc ";
			break;
		case 9: // active 是否可用 升序
			orderSql = " order by active asc ";
			break;
		case 10: // active 是否可用 降序
			orderSql = " order by active desc ";
			break;
		case 11: // commentPerm 评论开关 升序
			orderSql = " order by commentPerm asc ";
			break;
		case 12: // commentPerm 评论开关 降序
			orderSql = " order by commentPerm desc ";
			break;
		default:
			break;
		}
		return orderSql;
	}

	@Override
	public CmsContent findCmsContentSide(String id, String siteId, String channelId, boolean next) throws Exception {
		if(StringUtils.isBlank(siteId) && StringUtils.isBlank(channelId)){
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsContent.class);
		finder.append(" where 1=1 and id in ( select contentId from ").append(Finder.getTableName(CmsChannelContent.class))
			.append(" where 1=1 ");
		if(siteId != null ){
			finder.append(" and siteId = :siteId ").setParam("siteId", siteId);
		}
		/*if(channelId != null ){
			finder.append(" and channelId = :channelId ").setParam("channelId", channelId);
		}*/
		finder.append(" )");
		
		if(next){
			// 下一篇
			finder.append(" and createDate > ( select createDate from cms_content where id = :id )")
				.setParam("id", id)
				.append(" and active = :active ").setParam("active", ContentConstant.CONTENT_ACTIVE_YES)
				.append(" order by createDate asc limit 0,1 ");
		}else{
			// 上一篇
			finder.append(" and createDate < ( select createDate from cms_content where id = :id )")
				.setParam("id", id)
				.append(" and active = :active ").setParam("active", ContentConstant.CONTENT_ACTIVE_YES)
				.append(" order by createDate desc limit 0,1 ");
		}
		
		CmsContent cms = super.queryForObject(finder, CmsContent.class);
		
		return cms;
	}
	
	@Override
	public List<CmsContent> findListBySiteIdAndBusinessId(String siteId,
			String businessId, Page page, Map<String,String> params) throws Exception {
		List<CmsContent> resList = null;
		if(StringUtils.isEmpty(siteId) || StringUtils.isEmpty(businessId)){
			return resList;
		}
		
		Finder finder = Finder.getSelectFinder(CmsContent.class);
		finder.append(" where id in ( select contentId from ").append(Finder.getTableName(CmsChannelContent.class))
			.append(" where siteId = :siteId and channelId = :channelId ) ")
			.setParam("siteId", siteId).setParam("channelId", businessId);
		
		if(params != null){
			String next = params.get("next");
			if("true".equals(next)){
				page.setPageIndex(Integer.valueOf(params.get("pageIndex")));
				page.setPageSize(Integer.valueOf(params.get("pageSize")));
			}
			
			String orderBy = params.get("orderBy");
			if(StringUtils.isNotBlank(orderBy)){
				finder.append(this.getOrderSql(Integer.valueOf(orderBy)));
			}
		}
		
		resList = super.findListDataByFinder(finder, page, CmsContent.class, params);
		
		return resList;
	}

	@Override
	public List<CmsContent> findCmsContentList(CmsContent cmsContent,
			String siteId, String businessId, Page page) throws Exception {
		Finder finder = Finder.getSelectFinder(CmsContent.class);
		finder.append(" where 1=1 ");
		finder.append(" and id in ( select contentId from ").append(Finder.getTableName(CmsChannelContent.class))
			.append(" where siteId = :siteId and channelId = :channelId )")
			.setParam("siteId", siteId).setParam("channelId", businessId);
		if(cmsContent!=null){
			super.getFinderWhereByQueryBean(finder, cmsContent);
		}
		
		// 排序
		finder.append(" order by createDate desc ");
		
		List<CmsContent> cmsContentList = super.queryForList(finder, CmsContent.class, page);
		
		// 创建人设置
		if(cmsContentList!=null && cmsContentList.size()>0){
			List<String> createUserIdList = new ArrayList<>();
			for(CmsContent cm : cmsContentList){
				if(!createUserIdList.contains(cm.getCreatePerson())){
					createUserIdList.add(cm.getCreatePerson());
				}
			}
			if(createUserIdList.size()>0){
				Finder finder_c = Finder.getSelectFinder(User.class);
				finder_c.append(" where id in (:ids)").setParam("ids", createUserIdList);
				List<User> userList = super.queryForList(finder_c, User.class);
				for(CmsContent cm : cmsContentList){
					for(User user : userList){
						if(user.getId().equals(cm.getCreatePerson())){
							cm.setCreatePersonName(user.getName());
						}
					}
				}
			}
		}
		
		return cmsContentList;
	}
	
}
